/*
 * Copyright (C) 2009-2017 Ivan All rights reserved
 * Author: Ivan Shen
 * Date: 2017/5/5
 * Description:RoleAuthParamVo.java
 */
package com.iusofts.blades.sys.web;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 角色授权参数
 *
 * @author Ivan Shen
 */
public class RoleAuthParamVo {

    /**
     * 角色ID
     */
    @NotBlank(message = "角色ID不能为空")
    private String roleId;

    /**
     * 资源ID集合
     */
    private List<String> resourceIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
