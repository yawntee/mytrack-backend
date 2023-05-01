package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Deletable;
import com.yawntee.mytrack.entity.Bug;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.enums.BugStatus;
import com.yawntee.mytrack.enums.Role;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.BugService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/bug")
@AllArgsConstructor
@Validated
public class BugController implements Deletable<Bug> {

    @Getter
    private final BugService service;


    @Secured({Role.ROLE_ADMIN, Role.ROLE_TEST})
    @PostMapping
    public Resp<?> insert(@AuthenticationPrincipal User user, @RequestBody @Validated(Insert.class) Bug bug) {
        //测试人员不能设置状态
        if (!user.getRole().equals(Role.Admin)) bug.setStatus(null);
        if (getService().save(bug)) {
            return Resp.success();
        } else {
            return Resp.fail("插入失败");
        }
    }

    @Secured({Role.ROLE_ADMIN, Role.ROLE_TEST})
    @Override
    public Resp<?> delete(Integer id) {
        return Deletable.super.delete(id);
    }

    /**
     * 获取BUG记录列表
     *
     * @param versionId 版本ID
     * @return
     */
    @GetMapping
    private Resp<List<Bug>> find(@Min(1) Integer versionId) {
        var query = service.lambdaQuery();
        if (versionId != null) {
            query.eq(Bug::getVersionId, versionId);
        }
        return Resp.success(query.list());
    }

    /**
     * 根据ID修改BUG记录
     *
     * @param user 当前用户
     * @param data BUG记录
     * @return
     */
    @Secured({Role.ROLE_ADMIN, Role.ROLE_TEST})
    @PutMapping
    public Resp<?> modify(@AuthenticationPrincipal User user, @RequestBody @Validated(Update.class) Bug data) {
        if (user.getRole() != Role.Admin) {
            data.setStatus(null);
        }
        if (getService().updateById(data)) {
            return Resp.success();
        } else {
            return Resp.fail("修改失败");
        }
    }

    /**
     * 开发人员获取待办列表
     *
     * @param user 当前用户
     * @return
     */
    @Secured(Role.ROLE_DEV)
    @GetMapping("/todo")
    public Resp<List<Bug>> todo(@AuthenticationPrincipal User user) {
        return Resp.success(service.findAllByAssignee(user.getId()));
    }

    /**
     * 开发人员与测试人员完成BUG修复/验证
     *
     * @param user 当前用户
     * @param id   BUG记录ID
     * @return
     */
    @Secured({Role.ROLE_DEV, Role.ROLE_TEST})
    @PutMapping("/done/{id}")
    public Resp<?> done(@AuthenticationPrincipal User user, @PathVariable @Min(1) Integer id) {
        val bug = service.getById(id);
        int status;
        switch (user.getRole()) {
            case Dev -> {
                if (!bug.getStatus().equals(BugStatus.unsolved)) return Resp.fail("无效的状态");
                if (!Objects.equals(bug.getAssigneeId(), user.getId()))
                    return Resp.fail("错误的负责人");
                status = 1;
            }
            case Test -> {
                if (!bug.getStatus().equals(BugStatus.solvedNotVerify)) return Resp.fail("无效的状态");
                status = 2;
            }
            default -> {
                return Resp.fail("错误的角色");
            }
        }
        boolean result = service.lambdaUpdate().set(Bug::getStatus, status).eq(Bug::getId, id).update();
        return result ? Resp.success() : Resp.fail("操作失败");
    }

    /**
     * 测试人员打回至开发人员重修
     *
     * @param newBug 新BUG记录
     * @return
     */
    @Secured(Role.ROLE_TEST)
    @PutMapping("/ret")
    public Resp<?> ret(@RequestBody @Validated(Update.class) Bug newBug) {
        val bug = service.getById(newBug.getId());
        if (bug == null) return Resp.fail("BUG记录不存在");
        if (!bug.getStatus().equals(BugStatus.solvedNotVerify)) return Resp.fail("无效的状态");
        boolean result = service.lambdaUpdate()
                .set(Bug::getSubject, newBug.getSubject())
                .set(Bug::getContent, newBug.getContent())
                .set(Bug::getAssigneeId, newBug.getAssigneeId())
                .set(Bug::getStatus, 0)
                .eq(Bug::getId, newBug.getId())
                .update();
        return result ? Resp.success() : Resp.fail("操作失败");
    }

}
