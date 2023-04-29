package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Deletable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.Bug;
import com.yawntee.mytrack.entity.Project;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.entity.Version;
import com.yawntee.mytrack.enums.Role;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.BugService;
import com.yawntee.mytrack.service.ProjectService;
import com.yawntee.mytrack.service.VersionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController implements Modifiable<Project>, Deletable<Project> {

    @Getter
    private final ProjectService service;

    private final VersionService versionService;

    private final BugService bugService;

    @PostMapping
    Resp<?> insert(@AuthenticationPrincipal User user, @RequestBody Project data) {
        data.setCreatorId(user.getId());
        if (getService().save(data)) {
            return Resp.success();
        } else {
            return Resp.fail("插入失败");
        }
    }

    @PutMapping("/permit/{id}")
    public Resp<?> permit(@PathVariable Integer id) {
        return service.permit(id) ? Resp.success() : Resp.fail("审核失败");
    }

    @GetMapping
    public Resp<List<Project>> getList(@AuthenticationPrincipal User user) {
        var query = service.lambdaQuery().orderByDesc(Project::getId);
        if (!user.getRole().equals(Role.Admin)) {
            query.eq(Project::getEnable, true).or().eq(Project::getCreatorId, user.getId());
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
