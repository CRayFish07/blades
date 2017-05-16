/*
 * Copyright (C) 2009-2017 Ivan All rights reserved
 * Author: Ivan Shen
 * Date: 2017/5/5
 * Description:AuthorizationController.java
 */
package com.iusofts.blades.sys.web.controller;

import com.iusofts.blades.sys.common.util.FastJsonUtils;
import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.model.Resource;
import com.iusofts.blades.sys.service.ResourceService;
import com.iusofts.blades.sys.web.permission.Permission;
import com.iusofts.blades.sys.service.AuthorizationService;
import com.iusofts.blades.sys.web.RoleAuthParamVo;
import com.iusofts.blades.sys.web.permission.conf.PermissionConfig;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 授权
 *
 * @author Ivan Shen
 */
@Permission("授权")
@Controller
@RequestMapping("/sys/auth")
public class AuthorizationController extends BaseController {

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private ResourceService resourceService;

	@Permission("角色授权")
	@RequestMapping("/roleAuth")
	public void roleAuth(@ModelAttribute RoleAuthParamVo paramVo, HttpServletRequest request) {
		if (super.validate(paramVo)) {
			this.authorizationService.roleAuth(paramVo.getRoleId(), paramVo.getResourceIds());
		}
	}

	@Permission("角色资源")
	@RequestMapping("/queryRoleResourceIds")
	@ResponseBody
	public List<String> queryRoleResourceIds(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		return this.authorizationService.queryroleResourceIds(roleId);
	}

	@Permission(value="权限树")
	@RequestMapping("/tree")
	public ModelAndView treeChoice(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/sys/auth/auth_tree");
		String roleId = request.getParameter("roleId");
		if(StringUtil.isNotBlank(roleId)){
			mv.addObject("roleId",roleId);
		}
		return mv;
	}

	@Permission("权限树数据")
	@RequestMapping(value="/tree",method= RequestMethod.POST)
	public void getResourceTreeJson(HttpServletRequest request,HttpServletResponse response) {
		
		//获取角色已有权限
		String roleId = request.getParameter("roleId");
		Set<String> roleResourceSet = new HashSet<>();
		if(StringUtil.isNotBlank(roleId)){
			List<String> resourceIds = this.authorizationService.queryroleResourceIds(roleId);
			if(!CollectionUtils.isEmpty(resourceIds)){
				roleResourceSet.addAll(resourceIds);
			}
		}
		
		List<Resource> list=this.resourceService.getList(new Resource());
		List<Map<String,Object>> treeList=new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> map=new HashMap<>();
			com.iusofts.blades.sys.model.Resource m=list.get(i);
			map.put("id", m.getId());
			map.put("pId", m.getPid());
			String name= StringUtil.isBlank(m.getName())?m.getAlias()+"(请备注)":m.getName();
			if(m.getIsCheck()==null || m.getIsCheck()==0){
				name="<font color=blue>"+name+"(免检)</font>";
				map.put("chkDisabled", m.getIsCheck()==0?true:false);
				if(!PermissionConfig.IS_SHOW_NOTCHECK){
					continue;
				}
			}
			map.put("name", name);
			map.put("name2", m.getName());
			map.put("alias", m.getAlias());
			map.put("mtype", m.getType());
			map.put("checked", roleResourceSet.contains(m.getId()));
			map.put("isParent", m.getIsParent()==1?"Y":"N");
			map.put("url", m.getUrl());
			map.put("orderNo", m.getOrderNo());
			map.put("childOuter", false);
			if(PermissionConfig.PROJECT_NAME.equals(m.getName()))map.put("open", true);
			treeList.add(map);
		}
		super.flushResponse(response, FastJsonUtils.obj2json(treeList));
	}

}
