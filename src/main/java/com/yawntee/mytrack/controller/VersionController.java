package com.yawntee.mytrack.controller;

import com.yawntee.mytrack.component.Deletable;
import com.yawntee.mytrack.component.Insertable;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.Bug;
import com.yawntee.mytrack.entity.Version;
import com.yawntee.mytrack.enums.BugStatus;
import com.yawntee.mytrack.enums.Role;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.BugService;
import com.yawntee.mytrack.service.ProjectService;
import com.yawntee.mytrack.service.VersionService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
@AllArgsConstructor
@Validated
@Secured({Role.ROLE_ADMIN, Role.ROLE_PM})
public class VersionController implements Insertable<Version>, Modifiable<Version>, Deletable<Version> {

    @Getter
    private final VersionService service;

    private final ProjectService projectService;

    private final BugService bugService;

    /**
     * 新增版本
     *
     * @param data 版本
     * @return
     */
    @Override
    public Resp<?> insert(Version data) {
        var project = projectService.getById(data.getProjectId());
        if (project == null) return Resp.fail("项目不存在");
        if (!project.getEnable()) return Resp.fail("项目未审批");
        return Insertable.super.insert(data);
    }

    @Override
    public Resp<?> modify(Version data) {
        Version version = service.getById(data.getId());
        if (version == null) return Resp.fail("版本不存在");
        if (version.getReleased()) return Resp.fail("版本已发布归档");
        return Modifiable.super.modify(data);
    }

    @Override
    public Resp<?> delete(Integer id) {
        Version version = service.getById(id);
        if (version == null) return Resp.fail("版本不存在");
        if (version.getReleased()) return Resp.fail("版本已发布归档");
        return Deletable.super.delete(id);
    }

    @PutMapping("/release/{id}")
    public Resp<?> release(@PathVariable @NotNull @Min(1) Integer id) {
        Version version = service.getById(id);
        if (version.getReleased()) return Resp.fail("该版本已发布");
        if (bugService.lambdaQuery()
                .eq(Bug::getVersionId, id)
                .lt(Bug::getStatus, BugStatus.verified.getCode())
                .exists()
        ) {
            return Resp.fail("存在未验证的BUG，无法发布");
        }
        if (service.lambdaUpdate().set(Version::getReleased, true).eq(Version::getId, id).update()) {
            return Resp.success();
        } else {
            return Resp.fail("发布失败");
        }
    }
}
