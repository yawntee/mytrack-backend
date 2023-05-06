package com.yawntee.mytrack.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yawntee.mytrack.enums.Role;
import com.yawntee.mytrack.handler.PasswordHandler;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable, UserDetails {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Min(1)
    @Null(groups = {Insert.class})
    @NotNull(groups = {Update.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    @NotBlank(groups = Insert.class)
    @TableField(value = "username")
    private String username;
    /**
     * 密码(bcrypt)
     */
    @NotBlank(groups = Insert.class)
    @Length(min = 5, max = 20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField(value = "password", typeHandler = PasswordHandler.class)
    private String password;
    /**
     * 用户昵称
     */
    @NotBlank(groups = Insert.class)
    @TableField(value = "name")
    private String name;

    /**
     * 用户角色
     */
    @NotNull(groups = Insert.class)
    @TableField(value = "role")
    private Role role;

    /**
     * 是否已封禁
     */
    @TableField(value = "banned", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private Boolean banned;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getCode()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return !banned;
    }
}