/*
 * Copyright © 2010-2016 iusofts. All rights reserved.
 */
package com.iusofts.blades.sys.model;

import java.io.Serializable;

/**
 * 角色
 * 
 * @author  
 * @date 2016-03-07 18:38:06
 */
public class Role implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色别称
     */
    private String alias;

    /**
     * 角色代码
     */
    private String code;

    /**
     * 描述
     */
    private String description;

    /**
     * 有效性（1是，0否）
     */
    private String enbaled;

    /**
     * 排序号码
     */
    private Long orderNo;

    /**
     * 更新时间
     */
    private String updateTime;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnbaled() {
        return enbaled;
    }

    public void setEnbaled(String enbaled) {
        this.enbaled = enbaled;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}