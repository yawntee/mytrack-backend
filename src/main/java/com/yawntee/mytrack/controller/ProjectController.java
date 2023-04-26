package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Deletable;
import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.Bug;
import com.yawntee.mytrack.entity.Project;
import com.yawntee.mytrack.entity.Version;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.BugService;
import com.yawntee.mytrack.service.ProjectService;
import com.yawntee.mytrack.service.VersionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController implements Insertable<Project>, Modifiable<Project>, Deletable<Project> {

    @Getter
    private final ProjectService service;

    private final VersionService versionService;

    private final BugService bugService;

    @PostMapping("/grant")
    public Resp<?> grant(int id) {
        return service.grant(id) ? Resp.success() : Resp.fail("审核通过失败");
    }

    @GetMapping
    public Resp<List<Project>> getList(String keyword) {
        var query = service.lambdaQuery().orderByDesc(Project::getId);
        if (StringUtils.hasLength(keyword)) {
            query.like(Project::getName, keyword).or()
                    .like(Project::getContent, keyword);
        }
        return Resp.success(query.list());
    }

    @GetMapping("/{id}")
    public Resp<Project> getProject(@PathVariable Integer id) {
        List<Version> versions = versionService.lambdaQuery().eq(Version::getProjectId, id).orderByDesc(Version::getId).list();
        versions.forEach(version -> version.setBugs(bugService.lambdaQuery().eq(Bug::getVersionId, version.getId()).orderByAsc(Bug::getStatus).list()));
        Project project = service.getById(id);
        if (project == null) return Resp.fail("仓库不存在");
        project.setVersions(versions);
        return Resp.success(project);
    }
}
