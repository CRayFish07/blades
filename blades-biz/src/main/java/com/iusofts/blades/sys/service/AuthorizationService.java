/*
 * Copyright (C) 2009-2017 Ivan All rights reserved
 * Author: Ivan Shen
 * Date: 2017/5/5
 * Description:AuthorizationService.java
 */
package com.iusofts.blades.sys.service;

import java.util.List;

/**
 * 授权业务
 *
 * @author Ivan Shen
 */
public interface AuthorizationService {

    /**
     * 角色授权
     * @param roleId
     * @param resourceIds
     * @return
     */
    public boolean roleAuth(String roleId , List<String> resourceIds);

    /**
     * 查询角色资源
     * @param roleId
     * @return
     */
    public List<String> queryroleResourceIds(String roleId);
    
}
