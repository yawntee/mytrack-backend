package com.yawntee.mytrack.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BugStatus {
    unsolved("未解决", 0),
    solved("已解决", 1),
    verified("已验证", 2);


    private final String name;

    @EnumValue
    private final int code;
}
