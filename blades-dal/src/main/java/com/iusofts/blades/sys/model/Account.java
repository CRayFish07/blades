/*
 * Copyright © 2010-2016 iusofts. All rights reserved.
 */
package com.iusofts.blades.sys.model;

import java.io.Serializable;

import com.iusofts.blades.sys.common.util.validation.Phone;
import com.iusofts.blades.sys.common.util.validation.PhoneMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 系统用户
 * 
 * @author  
 * @date 2016-03-01 17:26:52
 */
public class Account implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    @NotBlank(message="账号不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message="密码不能为空")
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 注册时间
     */
    private String regTime;

    /**
     * 是否激活(1是；0否)
     */
    private String activated;

    /**
     * 是否有效(1是；0否[删除状态])
     */
    private String enabled;

    /**
     * 是否锁定(1是；0否)
     */
    private String locked;

    /**
     * 账号过期时间（yymmddhhmmss）
     */
    private String nonexpired;

    /**
     * 注册IP
     */
    private String regIp;

    /**
     * 邮箱
     */
    @Email(message="邮箱格式不正确")
    private String email;

    /**
     * 手机号
     */
    @Phone(value= PhoneMode.MOBILE,message="手机格式不正确")
    private String mobile;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getNonexpired() {
        return nonexpired;
    }

    public void setNonexpired(String nonexpired) {
        this.nonexpired = nonexpired;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}