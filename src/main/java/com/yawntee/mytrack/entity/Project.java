package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yawntee.mytrack.enums.ProjectStatus;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 项目表
 * @TableName project
 */
@TableName(value = "project")
@Data
public class Project implements Serializable {
    /**
     * 项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 项目名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 项目内容
     */
    @TableField(value = "content")
    private String content;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableField(value = "enable", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private Boolean enable;


    /**
     * 项目状态(0-开发中,1-测试中,2-已完成)
     */
    @TableField(value = "status", insertStrategy = FieldStrategy.NEVER)
    private ProjectStatus status;

    @TableField(exist = false)
    private List<Version> versions;
}