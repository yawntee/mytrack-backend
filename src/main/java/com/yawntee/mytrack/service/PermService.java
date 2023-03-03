package com.yawntee.mytrack.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yawntee.mytrack.entity.Perm;

import java.util.List;

/**
 * @author yawntee
 * @description 针对表【perm(权限表)】的数据库操作Service
 * @createDate 2023-03-01 12:42:26
 */
public interface PermService extends IService<Perm> {

    boolean grantOrRevoke(Integer userId, List<String> grant, List<String> revoke);

}
