package com.yawntee.mytrack.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VersionStatus {
    developing("开发中", 0),
    testing("测试中", 1),
    finished("已完成", 2);


    private final String name;

    @EnumValue
    private final int code;
}
