package com.yawntee.mytrack.component;

import com.yawntee.mytrack.pojo.Resp;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface Deletable<T> extends ServiceProvider<T> {

    @DeleteMapping("/{id}")
    default Resp<?> delete(@PathVariable Integer id) {
        if (getService().removeById(id)) {
            return Resp.success();
        } else {
            return Resp.fail("删除失败");
        }
    }

}
