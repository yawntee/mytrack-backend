package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.enums.Role;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController implements Insertable<User>, Modifiable<User> {

    @Getter
    private final UserService service;

    @GetMapping("/info")
    public Resp<User> getInfo(@AuthenticationPrincipal User user) {
        return Resp.success(service.getById(user.getId()));
    }

    @PutMapping("/info")
    public Resp<?> update(@AuthenticationPrincipal User user, @RequestBody User info) {
        return service.updateUser(user.getId(), info.getName(), info.getPassword()) ? Resp.success() : Resp.fail("修改失败");
    }

    @GetMapping
    public Resp<List<User>> listUsers(@AuthenticationPrincipal User user) {
        var query = service.lambdaQuery();
        if (!user.getRole().equals(Role.Admin)) {
            query.select(
                    User::getId,
                    User::getName,
                    User::getRole
            );
        }
        return Resp.success(query.list());
    }

    @DeleteMapping("/{id}")
    public Resp<?> delete(@AuthenticationPrincipal User user, @PathVariable int id) {
        if (user.getId() == id) {
            return Resp.fail("不能删除自身账号");
        }
        boolean result = service.removeById(id);
        return result ? Resp.success("删除成功") : Resp.fail("删除失败");
    }

    @PutMapping("/status")
    public Resp<?> changeStatus(@AuthenticationPrincipal User self, @RequestBody User user) {
        if (Objects.equals(user.getId(), self.getId())) {
            return Resp.fail("不能禁用自身账号");
        }
        if (service.lambdaUpdate().set(User::getBanned, user.getBanned()).eq(User::getId, user.getId()).update()) {
            return Resp.success();
        } else {
            return Resp.fail("修改用户状态失败");
        }
    }

    @Override
    public Resp<?> insert(User data) {
        if (hasUsername(data)) return Resp.fail("用户名已存在");
        return Insertable.super.insert(data);
    }

    @Override
    public Resp<?> modify(User data) {
        if (hasUsername(data)) return Resp.fail("用户名已存在");
        return Modifiable.super.modify(data);
    }

    private boolean hasUsername(User user) {
        return service.lambdaQuery().eq(User::getUsername, user.getUsername()).exists();
    }
}
