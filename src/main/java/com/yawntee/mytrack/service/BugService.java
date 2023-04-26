package com.yawntee.mytrack.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yawntee.mytrack.entity.Bug;

import java.util.List;

/**
 * @author yawntee
 * @description 针对表【bug(bug表)】的数据库操作Service
 * @createDate 2023-02-25 17:30:45
 */
public interface BugService extends IService<Bug> {

    List<Bug> findAllByAssignee(String userId);

}
