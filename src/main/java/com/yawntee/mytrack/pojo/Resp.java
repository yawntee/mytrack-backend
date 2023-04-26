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

    public static <T> Resp<T> fail(String msg) {
        return fail(1, msg);
    }

    public static <T> Resp<T> fail(int code, String msg) {
        Resp<T> resp = new Resp<>();
        resp.code = code;
        resp.msg = msg;
        return resp;
    }

}
