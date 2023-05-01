package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.enums.Role;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.UserService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController implements Insertable<User>, Modifiable<User> {

    @Getter
    private final UserService service;

    /**
     * 获取当前用户信息
     *
     * @param user 当前用户
     * @return
     */
    @GetMapping("/info")
    public Resp<User> getInfo(@AuthenticationPrincipal User user) {
        return Resp.success(service.getById(user.getId()));
    }

    /**
     * 更新当前用户信息
     *
     * @param user 当前用户
     * @param info 用户信息
     * @return
     */
    @PutMapping("/info")
    public Resp<?> update(@AuthenticationPrincipal User user, @RequestBody @Validated User info) {
        return service.updateUser(user.getId(), info.getName(), info.getPassword()) ? Resp.success() : Resp.fail("修改失败");
    }

    /**
     * 列出所有用户
     *
     * @param user 当前用户
     * @return
     */
    @GetMapping
    public Resp<List<User>> listUsers(@AuthenticationPrincipal User user) {
        var query = service.lambdaQuery();
        //仅管理员可拿到所有信息
        if (!user.getRole().equals(Role.Admin)) {
            query.select(
                    User::getId,
                    User::getName,
                    User::getRole
            );
        }
        return Resp.success(query.list());
    }

    /**
     * 删除用户
     *
     * @param user 当前用户
     * @param id   用户ID
     * @return
     */
    @Secured(Role.ROLE_ADMIN)
    @DeleteMapping("/{id}")
    public Resp<?> delete(@AuthenticationPrincipal User user, @PathVariable @Min(1) Integer id) {
        if (Objects.equals(user.getId(), id)) {
            return Resp.fail("不能删除自身账号");
        }
        boolean result = service.removeById(id);
        return result ? Resp.success("删除成功") : Resp.fail("删除失败");
    }

    /**
     * 更改用户封禁状态
     *
     * @param self 当前用户
     * @param user 用户ID与封禁状态
     * @return
     */
    @Secured(Role.ROLE_ADMIN)
    @PutMapping("/status")
    public Resp<?> changeStatus(@AuthenticationPrincipal User self, @RequestBody @Validated(Update.class) User user) {
        if (Objects.equals(user.getId(), self.getId())) {
            return Resp.fail("不能禁用自身账号");
        }
        if (service.lambdaUpdate().set(User::getBanned, user.getBanned()).eq(User::getId, user.getId()).update()) {
            return Resp.success();
        } else {
            return Resp.fail("修改用户状态失败");
        }
    }

    /**
     * 新增用户
     *
     * @param data 用户信息
     * @return
     */
    @Secured(Role.ROLE_ADMIN)
    @Override
    public Resp<?> insert(@RequestBody @Validated(Insert.class) User data) {
        System.out.println(data);
        if (hasUsername(data)) return Resp.fail("用户名已存在");
        return Insertable.super.insert(data);
    }

    /**
     * 更改用户信息
     *
     * @param data 用户信息
     * @return
     */
    @Secured(Role.ROLE_ADMIN)
    @Override
    public Resp<?> modify(User data) {
        if (hasUsername(data)) return Resp.fail("用户名已存在");
        return Modifiable.super.modify(data);
    }

    /**
     * 判断用户名是否已存在
     *
     * @param user 用户信息
     * @return
     */
    private boolean hasUsername(User user) {
        return service.lambdaQuery().eq(User::getUsername, user.getUsername()).exists();
    }
}
