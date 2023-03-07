package com.yawntee.mytrack.component;

import com.yawntee.mytrack.pojo.Resp;
import org.springframework.web.bind.annotation.DeleteMapping;

public interface Deletable<T> extends ServiceProvider<T> {

    @DeleteMapping
    default Resp<?> delete(int id) {
        if (getService().removeById(id)) {
            return Resp.success();
        } else {
            return Resp.fail("删除失败");
        }
    }

}
