package com.yawntee.mytrack.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yawntee.mytrack.entity.Version;
import com.yawntee.mytrack.mapper.VersionMapper;
import com.yawntee.mytrack.service.VersionService;
import org.springframework.stereotype.Service;

/**
 * @author yawntee
 * @description 针对表【version(版本表)】的数据库操作Service实现
 * @createDate 2023-02-24 18:37:14
 */
@Service
public class VersionServiceImpl extends ServiceImpl<VersionMapper, Version>
        implements VersionService {

}




