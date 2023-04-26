package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Deletable;
import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.Bug;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.BugService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bug")
@AllArgsConstructor
public class BugController implements Insertable<Bug>, Modifiable<Bug>, Deletable<Bug> {

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

    @GetMapping("/all")
    public Resp<List<Bug>> myBugs(@AuthenticationPrincipal User user) {
        return Resp.success(service.findAllByAssignee(user.getId()));
    }

}
