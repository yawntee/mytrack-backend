package com.yawntee.mytrack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yawntee.mytrack.entity.User;

import java.util.List;

/**
 * @author yawntee
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2023-02-25 17:35:15
 * @Entity com.yawntee.mytrack.entity.User
 */
public interface UserMapper extends BaseMapper<User> {

    User findOne(String username);

    List<User> search(String keyword);

}




