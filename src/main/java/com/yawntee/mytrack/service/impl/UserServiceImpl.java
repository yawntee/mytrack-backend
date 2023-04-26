package com.yawntee.mytrack.service.impl;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yawntee.mytrack.config.SecurityConfiguration;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.mapper.UserMapper;
import com.yawntee.mytrack.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author yawntee
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-02-25 17:35:15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public boolean updateUser(String userid, String name, String password) {
        LambdaUpdateChainWrapper<User> sql = lambdaUpdate();
        if (StringUtils.hasLength(name)) {
            sql.set(User::getName, name);
        }
        if (StringUtils.hasLength(password)) {
            sql.set(User::getPassword, SecurityConfiguration.encode(password));
        }
        sql.eq(User::getId, userid);
        return sql.update();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return lambdaQuery().eq(User::getUsername, username).one();
    }
}




