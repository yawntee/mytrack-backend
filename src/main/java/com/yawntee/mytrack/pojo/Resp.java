package com.yawntee.mytrack.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public class Resp<T> {

    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    private Resp() {
    }

    public static <T> Resp<T> success() {
        return success(null);
    }

    public static <T> Resp<T> success(T data) {
        Resp<T> resp = new Resp<>();
        resp.code = 0;
        resp.data = data;
        return resp;
    }

    public static Resp<?> fail(String msg) {
        Resp<?> resp = new Resp<>();
        resp.code = 1;
        resp.msg = msg;
        return resp;
    }

}
