package com.yawntee.mytrack.component;

import com.yawntee.mytrack.pojo.Resp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BatchInsertable<T> extends ServiceProvider<T> {

    @PostMapping("/batch")
    default Resp<?> batchInsert(@RequestBody List<T> data) {
        if (getService().saveBatch(data)) {
            return Resp.success();
        } else {
            return Resp.fail("插入失败");
        }
    }

}