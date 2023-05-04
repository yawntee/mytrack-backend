package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.Min;
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
    @Min(1)
    @Null(groups = {Insert.class})
    @NotNull(groups = {Update.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 所属项目id
     */
    @Min(1)
    @NotNull(groups = Insert.class)
    @TableField(value = "project_id")
    private Integer projectId;
    /**
     * 版本号
     */
    @NotBlank(groups = Insert.class)
    @TableField(value = "code")
    private String code;

    @TableField(exist = false)
    private List<Bug> bugs;
}