/*
 * Copyright © 2010-2016 iusofts. All rights reserved.
 */
package com.iusofts.blades.sys.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 系统字典（项）
 * 
 * @author  
 * @date 2016-02-24 17:53:04
 */
public class Dictionary implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 名称：字典（项）
     */
    @NotBlank(message="字典名称不能为空")
    private String name;

    /**
     * 代码：字典（项）
     */
    @NotBlank(message="字典代码不能为空")
    private String code;
    
    /**
     * 外部代码（默认值=代码）
     */
    private String fcode;

    /**
     * 简称、别称
     */
    private String alias;

    /**
     * 排序号
     */
    private Long orderNo;

    /**
     * 采用标准：00无；01国标GB；02部标GA；03省标PB；04市标SB；05自定义标准CB
     */
    private String standard;

    /**
     * 分类：要素分类，暂时保留
     */
    private String category;

    /**
     * 类型：01普通字典；02树形字典
     */
    private String classify;

    /**
     * 有效性（是否，默认是）：0否，1是
     */
    private String enabled;

    /**
     * 系统启动加载（是否，默认否）
     */
    private String cacheAble;

    /**
     * 插入时间（更新时间）格式：yyyymmddhhmmss
     */
    private String updateTime;

    /**
     * 字典（项）描述
     */
    private String description;

    /**
     * 上级主键
     */
    private String pid;

    /**
     * 级别
     */
    private Integer levels;

    /**
     * 图标（路径）
     */
    private String icon;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getCacheAble() {
        return cacheAble;
    }

    public void setCacheAble(String cacheAble) {
        this.cacheAble = cacheAble;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}