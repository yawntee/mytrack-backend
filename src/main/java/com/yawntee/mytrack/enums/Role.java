package com.yawntee.mytrack.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    Admin("管理员", "ROLE_ADMIN"),
    PM("项目管理员", "ROLE_PM"),
    Dev("开发人员", "ROLE_DEV"),
    Test("测试人员", "ROLE_TEST");

    private final String name;
    @EnumValue
    private final String code;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_PM = "ROLE_PM";
    public static final String ROLE_DEV = "ROLE_DEV";
    public static final String ROLE_TEST = "ROLE_TEST";
}
