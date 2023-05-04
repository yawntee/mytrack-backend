package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yawntee.mytrack.enums.BugStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

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
    @Min(1)
    @Null(groups = {Insert.class})
    @NotNull(groups = {Update.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 所属版本id
     */
    @Min(1)
    @NotNull(groups = Insert.class)
    @TableField(value = "version_id", updateStrategy = FieldStrategy.NEVER)
    private Integer versionId;
    /**
     * 摘要
     */
    @NotBlank(groups = Insert.class)
    @TableField(value = "subject")
    private String subject;
    /**
     * 内容
     */
    @NotBlank(groups = Insert.class)
    @TableField(value = "content")
    private String content;
    /**
     * 负责人
     */
    @Min(1)
    @NotNull(groups = Insert.class)
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "assignee_id")
    private Integer assigneeId;
    /**
     * bug状态(0-未解决,1-已解决待验证,2-已验证)
     */
    @TableField(value = "status")
    private BugStatus status;
}