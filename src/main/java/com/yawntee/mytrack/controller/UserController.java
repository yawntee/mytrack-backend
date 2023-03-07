package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController implements Insertable<User>, Modifiable<User> {

    @Getter
    private final UserService service;

    @GetMapping
    public Resp<List<User>> query(String keyword) {
        return Resp.success(service.listUsers(keyword));
    }

    @PostMapping("/update")
    public Resp<?> update(@AuthenticationPrincipal User user, String name, String password) {
        return service.updateUser(user.getId(), name, password) ? Resp.success() : Resp.fail("修改失败");
    }

}
