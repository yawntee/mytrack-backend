package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yawntee.mytrack.annotation.Id;
import com.yawntee.mytrack.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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
    @Null(groups = {Insert.class})
    @NotNull(groups = {Update.class})
    @Id(groups = {Insert.class, Update.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名
     */
    @NotBlank(groups = Insert.class)
    @TableField(value = "name")
    private String name;

    /**
     * 项目内容
     */
    @NotBlank(groups = Insert.class)
    @TableField(value = "content")
    private String content;

    /**
     * 
     */
    @TableField(value = "enable", updateStrategy = FieldStrategy.NEVER)
    private Boolean enable;

    /**
     * 项目状态(0-开发中,1-测试中,2-已完成)
     */
    @TableField(value = "status")
    private ProjectStatus status;

    /**
     * 创建者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "creator_id", updateStrategy = FieldStrategy.NEVER)
    private Integer creatorId;


    @TableField(exist = false)
    private List<Version> versions;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}