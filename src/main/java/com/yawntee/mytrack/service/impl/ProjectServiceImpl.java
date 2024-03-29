package com.yawntee.mytrack.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yawntee.mytrack.entity.Project;
import com.yawntee.mytrack.mapper.ProjectMapper;
import com.yawntee.mytrack.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yawntee
 * @description 针对表【project(项目表)】的数据库操作Service实现
 * @createDate 2023-02-24 18:37:14
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
        implements ProjectService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean permit(int projectId) {
        Project project = baseMapper.selectById(projectId);
        project.setEnable(true);
        project.setId(null);
        if (baseMapper.insert(project) > 0) {
            baseMapper.deleteById(projectId);
            return true;
        }
        return false;
    }
}




