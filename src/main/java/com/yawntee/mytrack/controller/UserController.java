package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.component.Listable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController implements Insertable<User>, Listable<User>, Modifiable<User> {

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

    @GetMapping("/developers")
    public Resp<List<User>> listDevelopers() {
        return Resp.success(
                service.lambdaQuery()
                        .select(
                                User::getId,
                                User::getName,
                                User::getRole
                        )
                        .eq(User::getRole, "ROLE_DEV")
                        .list()
        );
    }

}
