package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Deletable;
import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.Version;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.ProjectService;
import com.yawntee.mytrack.service.VersionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
@AllArgsConstructor
@Validated
public class VersionController implements Insertable<Version>, Modifiable<Version>, Deletable<Version> {

    @Getter
    private final VersionService service;

    private final ProjectService projectService;

    @Override
    public Resp<?> insert(Version data) {
        var project = projectService.getById(data.getProjectId());
        if (project == null) return Resp.fail("项目不存在");
        if (!project.getEnable()) return Resp.fail("项目未审批");
        return Insertable.super.insert(data);
    }
}
