/*
 * Copyright (C) 2009-2017 Ivan All rights reserved
 * Author: Ivan Shen
 * Date: 2017/5/5
 * Description:AuthorizationServiceImpl.java
 */
package com.iusofts.blades.sys.service.impl;

import com.iusofts.blades.sys.common.util.UUID;
import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.dao.RoleResourceMapper;
import com.iusofts.blades.sys.enums.DeleteFlag;
import com.iusofts.blades.sys.service.AuthorizationService;
import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.model.RoleResource;
import com.iusofts.blades.sys.model.RoleResourceExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Shen
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	@Resource
	private RoleResourceMapper roleResourceMapper;

	@Override
	public boolean roleAuth(String roleId, List<String> resourceIds) {
		if (StringUtil.isNotBlank(roleId)) {
			// 清除旧权限
			RoleResourceExample example = new RoleResourceExample();
			example.createCriteria().andRoleIdEqualTo(roleId);
			RoleResource roleResource = new RoleResource();
			roleResource.setDeleteFlag(DeleteFlag.DELETED.getCode());
			roleResource.setUpdateTime(DateUtil.getCurrentTimeAs14String());
			roleResourceMapper.updateByExampleSelective(roleResource, example);
			// 添加新权限
			if (resourceIds != null) {
				for (String resourceId : resourceIds) {
					if (StringUtil.isNotBlank(resourceId)) {
						roleResource = new RoleResource();
						roleResource.setId(UUID.getUuid());
						roleResource.setRoleId(roleId);
						roleResource.setResourceId(resourceId);
						roleResource.setCreateTime(DateUtil.getCurrentTimeAs14String());
						this.roleResourceMapper.insertSelective(roleResource);
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> queryroleResourceIds(String roleId) {
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andDeleteFlagEqualTo(DeleteFlag.NORMAL.getCode());
        List<RoleResource> roleResourceList = this.roleResourceMapper.selectByExample(example);
        List<String> resourceIds = new ArrayList<>();
        for (RoleResource roleResource :
                roleResourceList) {
            resourceIds.add(roleResource.getResourceId());
        }
        return resourceIds;
	}
}
