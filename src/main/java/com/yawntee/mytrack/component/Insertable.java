package com.yawntee.mytrack.component;

import com.yawntee.mytrack.pojo.Resp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface Insertable<T> extends ServiceProvider<T> {

    @PostMapping
    default Resp<?> insert(@RequestBody T data) {
        if (getService().save(data)) {
            return Resp.success();
        } else {
            return Resp.fail("插入失败");
        }
    }

}
