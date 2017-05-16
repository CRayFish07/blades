/*
 * Copyright © 2010-2016 iusofts. All rights reserved.
 */
package com.iusofts.blades.sys.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 组织机构
 * 
 * @author  
 * @date 2016-02-29 14:13:08
 */
public class Organization implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 机构名称
     */
    @NotBlank(message="机构名称不能为空")
    private String name;

    /**
     * 机构别称
     */
    private String alias;

    /**
     * 机构代码
     */
    @NotBlank(message="机构代码不能为空")
    private String code;

    /**
     * 外部代码（默认==机构代码）
     */
    private String fcode;

    /**
     * 图标路径
     */
    private String icon;

    /**
     * 排序号
     */
    private Long orderNo;

    /**
     * 级别
     */
    private Integer levels;

    /**
     * 机构类型（0机构，1单位，2部门，3岗位，4人员，9分组）
     */
    @NotBlank(message="请选择机构类型")
    private String orgType;

    /**
     * 有效性（1有效，0无效）
     */
    private String enabled;

    /**
     * 更新（新增）时间，格式：yyyymmddhhmmss
     */
    private String updateTime;

    /**
     * 上级主键
     */
    private String pid;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}