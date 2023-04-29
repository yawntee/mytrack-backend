package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yawntee.mytrack.enums.BugStatus;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * bug表
 *
 * @TableName bug
 */
@TableName(value = "bug")
@Data
public class Bug implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * bug id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 所属版本id
     */
    @TableField(value = "version_id")
    private Integer versionId;
    /**
     * 摘要
     */
    @TableField(value = "subject")
    private String subject;
    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;
    /**
     * 负责人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "assignee_id", updateStrategy = FieldStrategy.IGNORED)
    private Integer assigneeId;
    /**
     * bug状态(0-未解决,1-已解决待验证,2-已验证)
     */
    @TableField(value = "status")
    private BugStatus status;
}