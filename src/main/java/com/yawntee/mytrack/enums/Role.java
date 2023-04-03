package com.yawntee.mytrack.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    Admin("管理员", "ROLE_ADMIN"),
    ProjectManager("项目管理员", "ROLE_PM"),
    Developer("开发人员", "ROLE_DEV"),
    Tester("测试人员", "ROLE_TEST");

    private final String name;
    @EnumValue
    private final String code;
}
