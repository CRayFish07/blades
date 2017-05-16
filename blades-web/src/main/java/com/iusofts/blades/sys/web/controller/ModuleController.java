package com.iusofts.blades.sys.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iusofts.blades.sys.common.util.FastJsonUtils;
import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.model.Resource;
import com.iusofts.blades.sys.service.ResourceService;
import com.iusofts.blades.sys.web.permission.Permission;
import com.iusofts.blades.sys.web.permission.PermissionCluster;
import com.iusofts.blades.sys.web.permission.conf.PermissionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Permission("权限管理")
@Controller
@RequestMapping("/sys/module")
public class ModuleController extends BaseController {
	
	@Autowired
	private ResourceService resourceService;

	@Permission(value="主页")
	@RequestMapping("/manage")
	public String index() {
		return "/sys/module/module_manage";
	}
	
	@Permission(value="导航")
	@RequestMapping("/top")
	public String top() {
		return "/sys/module/module_top";
	}
	
	@Permission(value="权限树")
	@RequestMapping("/tree")
	public String tree() {
		return "/sys/module/module_tree";
	}
	
	@Permission("权限树数据")
	@RequestMapping(value="/tree",method=RequestMethod.POST)
	public void getResourceTreeJson(HttpServletRequest request,HttpServletResponse response) {
		List<Resource> list=this.resourceService.getList(new Resource());
		List<Map<String,Object>> treeList=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> map=new HashMap<String, Object>();
			Resource m=list.get(i);
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
			map.put("isParent", m.getIsParent()==1?"Y":"N");
			map.put("url", m.getUrl());
			map.put("orderNo", m.getOrderNo());
			map.put("childOuter", false);
			if(PermissionConfig.PROJECT_NAME.equals(m.getName()))map.put("open", true);
			treeList.add(map);
		}
		super.flushResponse(response, FastJsonUtils.obj2json(treeList));
	}

	@Permission("权限编辑")
	@RequestMapping("/edit")
	public String toEdit(){
		return "/sys/module/module_edit";
	}
	
	@Permission("更新权限")
	@RequestMapping("/updateAll")
	public void updateAll(HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalArgumentException, SecurityException, InterruptedException, ExecutionException{
		List<Resource> list= PermissionCluster.getResourceList();
		this.resourceService.updateByList(list);
	}
	
	@Permission("排序")
	@RequestMapping("/sort")
	public void sort(HttpServletRequest request,HttpServletResponse response) {
		if(StringUtil.isNotBlank(request.getParameter("ids"))){
			this.resourceService.sortByIds(request.getParameter("ids").split(","));
		}
	}
	
	@Permission("回收站-模块")
	@RequestMapping("/recycle")
	public ModelAndView recycle(@ModelAttribute Resource resource,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("/sys/module/module_recycle");
		Map<String, String> other = new HashMap<String, String>();//其它查询条件（实体中不包含的属性）
		String startTime = request.getParameter("startTime");
		if (StringUtil.isNotBlank(startTime)) {
			other.put(
					"startTime",
					DateUtil.parse14String(startTime));
		}
		String endTime = request.getParameter("endTime");
		if (StringUtil.isNotBlank(endTime)) {
			other.put(
					"endTime",
					DateUtil.parse14String(endTime));
		}
		resource.setEnabled(0);
		Page<Resource> page = this.resourceService.getPage(resource, other,
				super.getPageNo(request), super.getPageSize(request));
		mav.addObject("idValues",request.getParameter("idValues"));
		mav.addObject("page", page);
		mav.addObject("resource", resource);
		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);
		return mav;
	}
	
}