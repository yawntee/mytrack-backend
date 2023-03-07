package com.yawntee.mytrack.component;

import com.baomidou.mybatisplus.extension.service.IService;

public interface ServiceProvider<T> {

    IService<T> getService();

}
