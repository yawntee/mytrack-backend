package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableField(value = "assignee_id")
    private Integer assigneeId;
    /**
     * bug状态(0-未解决,1-已解决待验证,2-已验证)
     */
    @TableField(value = "status")
    private Integer status;
}