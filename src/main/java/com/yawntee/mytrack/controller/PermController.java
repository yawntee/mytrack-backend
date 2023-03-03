package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.pojo.PermOperators;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.PermService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perm")
@AllArgsConstructor
public class PermController {

    private final PermService permService;

    @PostMapping
    public Resp<?> mod(@AuthenticationPrincipal User user, @RequestBody PermOperators ops) {
        if (permService.grantOrRevoke(user.getId(), ops.getGrant(), ops.getRevoke())) {
            return Resp.success();
        } else {
            return Resp.fail("修改权限失败");
        }
    }

}
