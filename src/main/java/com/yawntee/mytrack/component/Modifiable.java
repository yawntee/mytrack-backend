package com.yawntee.mytrack.component;

import com.yawntee.mytrack.pojo.Resp;
import org.apache.ibatis.annotations.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface Modifiable<T> extends ServiceProvider<T> {

    @PutMapping
    default Resp<?> modify(@RequestBody @Validated(Update.class) T data) {
        if (getService().updateById(data)) {
            return Resp.success();
        } else {
            return Resp.fail("修改失败");
        }
    }
}
