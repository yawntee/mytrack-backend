package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 项目表
 *
 * @TableName project
 */
@TableName(value = "project")
@Data
public class Project implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 项目id
     */
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
}