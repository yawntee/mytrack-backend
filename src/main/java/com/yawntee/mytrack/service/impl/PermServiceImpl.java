package com.yawntee.mytrack.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yawntee.mytrack.entity.Perm;
import com.yawntee.mytrack.mapper.PermMapper;
import com.yawntee.mytrack.service.PermService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yawntee
 * @description 针对表【perm(权限表)】的数据库操作Service实现
 * @createDate 2023-03-01 12:42:26
 */
@Service
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm>
        implements PermService {


    @Override
    public boolean grantOrRevoke(Integer userId, List<String> grant, List<String> revoke) {
        return
                //删除收回的权限
                lambdaUpdate().eq(Perm::getUserId, userId).in(Perm::getRole, revoke).remove()
                        //插入新增的权限
                        || saveBatch(grant.stream().map((type) -> {
                    Perm perm = new Perm();
                    perm.setUserId(userId);
                    perm.setRole(type);
                    return perm;
                }).toList());
    }
}




