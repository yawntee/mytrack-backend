package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Deletable;
import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.component.Listable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.Project;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController implements Insertable<Project>, Modifiable<Project>, Listable<Project>, Deletable<Project> {

    @Getter
    private final ProjectService service;

    @PostMapping("/grant")
    public Resp<?> grant(int id) {
        return service.grant(id) ? Resp.success() : Resp.fail("审核通过失败");
    }

}
