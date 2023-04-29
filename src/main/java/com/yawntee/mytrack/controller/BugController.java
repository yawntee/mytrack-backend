package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Deletable;
import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.entity.Bug;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.enums.Role;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.BugService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bug")
@AllArgsConstructor
public class BugController implements Insertable<Bug>, Deletable<Bug> {

    @Getter
    private final BugService service;

    @GetMapping
    private Resp<List<Bug>> find(Integer versionId) {
        var query = service.lambdaQuery();
        if (versionId != null) {
            query.eq(Bug::getVersionId, versionId);
        }
        return Resp.success(query.list());
    }

    @PutMapping
    public Resp<?> modify(@AuthenticationPrincipal User user, @RequestBody Bug data) {
        if (user.getRole() != Role.Admin) {
            data.setStatus(null);
        }
        if (getService().updateById(data)) {
            return Resp.success();
        } else {
            return Resp.fail("修改失败");
        }
    }

    //获取待办列表
    @GetMapping("/todo")
    public Resp<List<Bug>> todo(@AuthenticationPrincipal User user) {
        return Resp.success(service.findAllByAssignee(user.getId()));
    }

    //进入下一阶段
    @PutMapping("/done/{id}")
    public Resp<?> done(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        int status;
        switch (user.getRole()) {
            case Dev -> status = 1;
            case Test -> status = 2;
            default -> {
                return Resp.fail("错误的角色");
            }
        }
        boolean result = service.lambdaUpdate().set(Bug::getStatus, status).eq(Bug::getId, id).update();
        return result ? Resp.success() : Resp.fail("操作失败");
    }

    //打回重改
    @PutMapping("/ret/{id}")
    public Resp<?> ret(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        if (user.getRole() != Role.Test) {
            return Resp.fail("错误的角色");
        }
        boolean result = service.lambdaUpdate().set(Bug::getStatus, 0).eq(Bug::getId, id).update();
        return result ? Resp.success() : Resp.fail("操作失败");
    }

}
