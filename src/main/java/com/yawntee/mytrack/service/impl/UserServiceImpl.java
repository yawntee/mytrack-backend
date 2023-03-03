package com.yawntee.mytrack.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.mapper.UserMapper;
import com.yawntee.mytrack.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yawntee
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-02-25 17:35:15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Override
    public User findUser(String username) {
        return baseMapper.findOne(username);
    }

    @Override
    public List<User> listUsers(String keyword) {
        return baseMapper.search(keyword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUser(username);
    }
}



