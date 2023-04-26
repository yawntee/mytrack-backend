package com.yawntee.mytrack.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProjectStatus {
    developing("开发中", 0),
    testing("测试中", 1),
    finished("已完成", 2);


    private final String name;

    @JsonValue
    @EnumValue
    private final int code;
}
