package com.yawntee.mytrack.component;

import com.yawntee.mytrack.pojo.Resp;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface Listable<T> extends ServiceProvider<T> {

    @GetMapping
    default Resp<List<T>> list() {
        return Resp.success(getService().query().list());
    }

}
