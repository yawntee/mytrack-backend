package com.yawntee.mytrack.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yawntee.mytrack.entity.Bug;
import com.yawntee.mytrack.mapper.BugMapper;
import com.yawntee.mytrack.service.BugService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yawntee
 * @description 针对表【bug(bug表)】的数据库操作Service实现
 * @createDate 2023-02-25 17:30:45
 */
@Service
public class BugServiceImpl extends ServiceImpl<BugMapper, Bug>
        implements BugService {

    @Override
    public List<Bug> findAllByAssignee(Integer userId) {
        return lambdaQuery().eq(Bug::getAssigneeId, userId).list();
    }

}




