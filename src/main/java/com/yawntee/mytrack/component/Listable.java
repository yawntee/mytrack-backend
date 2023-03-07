package com.yawntee.mytrack.component;

import com.yawntee.mytrack.pojo.Resp;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface Listable<T> extends ServiceProvider<T> {

    @GetMapping
    @Secured({"ROLE_admin"})
    default Resp<List<T>> list() {
        return Resp.success(getService().list());
    }

}
