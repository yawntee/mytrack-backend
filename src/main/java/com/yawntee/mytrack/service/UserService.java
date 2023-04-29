package com.yawntee.mytrack.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yawntee.mytrack.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author yawntee
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-02-25 17:35:15
 */
public interface UserService extends IService<User>, UserDetailsService {

    boolean updateUser(Integer userid, String username, String password);

}
