package com.yawntee.mytrack.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.yawntee.mytrack.component.Modifiable;
import com.yawntee.mytrack.entity.Bug;
import com.yawntee.mytrack.entity.Project;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.entity.Version;
import com.yawntee.mytrack.enums.BugStatus;
import com.yawntee.mytrack.enums.Role;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.BugService;
import com.yawntee.mytrack.service.ProjectService;
import com.yawntee.mytrack.service.VersionService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.annotations.Insert;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
@Validated
public class ProjectController implements Modifiable<Project> {

    @Getter
    private final ProjectService service;

    private final VersionService versionService;

    private final BugService bugService;

    /**
     * 新增项目
     *
     * @param user 当前用户
     * @param data 项目内容
     * @return
     */
    @Secured({Role.ROLE_ADMIN, Role.ROLE_PM})
    @PostMapping
    Resp<?> insert(@AuthenticationPrincipal User user, @RequestBody @Validated(Insert.class) Project data) {
        data.setEnable(user.getRole().equals(Role.Admin));
        data.setCreatorId(user.getId());
        if (getService().save(data)) {
            return Resp.success();
        } else {
            return Resp.fail("插入失败");
        }
    }

    /**
     * 单项目审批通过
     *
     * @param id 项目ID
     * @return
     */
    @Secured(Role.ROLE_ADMIN)
    @PutMapping("/permit/{id}")
    public Resp<?> permit(@PathVariable @Min(1) Integer id) {
        return service.permit(id) ? Resp.success() : Resp.fail("审核失败");
    }

    /**
     * 获取主页项目列表
     *
     * @param user 当前用户
     * @return
     */
    @GetMapping
    public Resp<List<Project>> getList(@AuthenticationPrincipal User user) throws JsonProcessingException {
        var query = service.lambdaQuery();
        if (!user.getRole().equals(Role.Admin)) {
            query.eq(Project::getEnable, true) //只显示批准的项目
                    .or()
                    .eq(Project::getCreatorId, user.getId()); //项目经理可查看自己创建的未审批项目
        }
        query.orderByAsc(Project::getEnable)
                .orderByDesc(Project::getId);
        List<Project> rs = query.list();
        for (Project r : rs) {
            List<Integer> versionIds = versionService.lambdaQuery().select(Version::getId).eq(Version::getProjectId, r.getId()).list().stream().map(Version::getId).toList();
            if (versionIds.isEmpty()) {
                r.setBugStatus(Collections.emptyList());
                continue;
            }
            List<BugStatus> bugStatus = bugService.lambdaQuery().select(Bug::getStatus).in(Bug::getVersionId, versionIds).list().stream().map(Bug::getStatus).toList();
            Multiset<BugStatus> counter = HashMultiset.create(bugStatus);
            r.setBugStatus(counter.entrySet());
        }
        return Resp.success(rs);
    }

    /**
     * 获取单项目信息、版本、BUG记录
     *
     * @param id 项目ID
     * @return
     */
    @GetMapping("/{id}")
    public Resp<Project> getProject(@PathVariable @Min(1) Integer id) {
        Project project = service.getById(id);
        if (project == null) return Resp.fail("项目不存在");
        if (!project.getEnable()) return Resp.failBack("项目暂未通过审批");
        List<Version> versions = versionService.lambdaQuery().eq(Version::getProjectId, id).orderByDesc(Version::getId).list();
        versions.forEach(version -> version.setBugs(bugService.lambdaQuery().eq(Bug::getVersionId, version.getId()).orderByAsc(Bug::getStatus).list()));
        project.setVersions(versions);
        return Resp.success(project);
    }

    @DeleteMapping("/{id}")
    public Resp<?> delete(@AuthenticationPrincipal User user, @PathVariable @Min(1) Integer id) {
        Project project = service.getById(id);
        if (project == null) return Resp.fail("项目不存在");
        if (project.getEnable() && !user.getRole().equals(Role.Admin)) throw new AccessDeniedException("无权删除项目");
        if (getService().removeById(id)) {
            return Resp.success();
        } else {
            return Resp.fail("删除失败");
        }
    }
}
