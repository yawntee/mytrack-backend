package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 版本表
 *
 * @TableName version
 */
@TableName(value = "version")
@Data
public class Version implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 版本id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 所属项目id
     */
    @TableField(value = "project_id")
    private Integer projectId;
    /**
     * 版本号
     */
    @TableField(value = "code")
    private String code;

    @TableField(exist = false)
    private List<Bug> bugs;
}