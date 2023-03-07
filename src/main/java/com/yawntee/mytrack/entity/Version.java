package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yawntee.mytrack.enums.VersionStatus;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

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
    /**
     * 版本状态(0-开发中,1-测试中,2-已完成)
     */
    @TableField(value = "status")
    private VersionStatus status;
}