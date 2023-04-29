package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 项目表
 * @TableName project
 */
@TableName(value ="project")
@Data
public class Project implements Serializable {
    /**
     * 项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    /**
     * 
     */
    @TableField(value = "enable")
    private Boolean enable;

    /**
     * 项目状态(0-开发中,1-测试中,2-已完成)
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "creator_id")
    private Integer creatorId;


    @TableField(exist = false)
    private List<Version> versions;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}