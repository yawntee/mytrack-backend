package com.yawntee.mytrack.controller;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.yawntee.mytrack.config.SecurityConfiguration;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 新增用户
     *
     * @param user 用户
     * @return 响应数据
     */
    @PutMapping
    public Resp<?> add(@RequestBody User user) {
        if (userService.save(user)) {
            return Resp.success();
        } else {
            return Resp.fail("插入失败");
        }
    }

    @PostMapping
    public Resp<?> mod(@RequestBody User user) {
        if (userService.updateById(user)) {
            return Resp.success();
        } else {
            return Resp.fail("修改失败");
        }
    }

    @GetMapping
    public Resp<List<User>> query(String keyword) {
        return Resp.success(userService.listUsers(keyword));
    }

    @DeleteMapping
    public Resp<?> del(int userId) {
        if (userService.removeById(userId)) {
            return Resp.success();
        } else {
            return Resp.fail("删除失败");
        }
    }

    @PostMapping("/update")
    public Resp<?> update(@AuthenticationPrincipal User user, String name, String password) {
        LambdaUpdateChainWrapper<User> sql = userService.lambdaUpdate();
        if (name != null) {
            sql.set(User::getUsername, name);
        }
        if (password != null) {
            sql.set(User::getPassword, SecurityConfiguration.encode(password));
        }
        sql.eq(User::getId, user.getId());
        if (sql.update()) {
            return Resp.success();
        } else {
            return Resp.fail("修改失败");
        }
    }

}
