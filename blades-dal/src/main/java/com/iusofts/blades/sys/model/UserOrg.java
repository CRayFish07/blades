/*
 * Copyright © 2010-2016 iusofts. All rights reserved.
 */
package com.iusofts.blades.sys.model;

import java.io.Serializable;

/**
 * 用户-组织机构
 * 
 * @author  
 * @date 2016-03-01 14:51:10
 */
public class UserOrg implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 组织机构编号
     */
    private String orgId;

    /**
     * 所属关系（1隶属；2兼职，3分管）
     */
    private String relation;

    /**
     * 记录新增时间
     */
    private String createTime;

    /**
     * 排序号
     */
    private Integer orderNo;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}