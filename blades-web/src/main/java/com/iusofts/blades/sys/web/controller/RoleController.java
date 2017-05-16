package com.iusofts.blades.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.common.util.UUID;
import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.model.Role;
import com.iusofts.blades.sys.service.RoleService;
import com.iusofts.blades.sys.web.permission.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.iusofts.blades.sys.common.util.StringUtil;

/**
 * @描述:角色控制类
 * @author Asker_lve
 * @date 2016年3月4日 上午11:03:16
 */
@Permission("角色管理")
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	/**
	 * 
	 * 描述:到角色列表
	 * 
	 * @param role
	 * @param request
	 * @param map
	 *            用于存放回显对象
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月4日 上午11:15:49
	 */
	@Permission("角色列表")
	@RequestMapping("/list/{sort}")
	public String list(@PathVariable String sort, @ModelAttribute Role role,
			HttpServletRequest request, Map<String, Object> map) {
		// 其它查询条件（实体中不包含的属性）
		Map<String, String> other = new HashMap<String, String>();
		// 切换排序字段
		other.put("sort", sort);

		// 有效性
		String enable = request.getParameter("enable");
		other.put("enable", enable);

		Page<Role> page = this.roleService.getPage(role, other,
				super.getPageNo(request), super.getPageSize(request));

		map.put("page", page);
		map.put("role", role);
		map.put("sort", sort);
		map.put("idValues", request.getParameter("idValues"));
		map.put("enable", enable);
		return "/sys/role/role_list";
	}

	@Permission("添加角色页面")
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save(@ModelAttribute Role role, HttpServletRequest request,
			HttpServletResponse response) {

		return "/sys/role/role_save";
	}

	/**
	 * 
	 * 描述:角色添加
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @author Asker_lve
	 * @date 2016年3月4日 下午2:57:55
	 */
	@Permission("添加角色操作")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(@RequestBody Role role, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> otherErrors = new HashMap<>();

		if (StringUtil.isNotBlank(role.getName())
				&& this.roleService.nameExist(role.getName())) {
			otherErrors.put("name", "角色名称已存在！");
		}

		if (StringUtil.isNotBlank(role.getCode())
				&& this.roleService.codeExist(role.getCode())) {
			otherErrors.put("code", "角色代码已存在！");
		}

		if (super.validate(role, request, otherErrors)) {
			role.setId(UUID.getUuid());
			this.roleService.save(role);
		}
	}

	@Permission("查看角色页面")
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable String id, Map<String, Object> map) {
		map.put("role", this.roleService.get(id));
		return "/sys/role/role_view";
	}

	@Permission("修改角色页面")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable String id, Map<String, Object> map) {
		map.put("role", this.roleService.get(id));
		return "/sys/role/role_update";
	}

	/**
	 * 
	 * 描述:角色更新
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @author Asker_lve
	 * @date 2016年3月4日 下午3:28:46
	 */
	@Permission("修改角色操作")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void doUpdate(@ModelAttribute Role role, HttpServletRequest request,
			HttpServletResponse response) {
		Role ro = this.roleService.get(role.getId());
		Map<String, String> otherErrors = new HashMap<>();

		if (StringUtil.isNotBlank(role.getName())
				&& !role.getName().equals(ro.getName())// 比新增时多判断一步，允许和自己重复
				&& this.roleService.nameExist(role.getName())) {
			otherErrors.put("name", "组织机构名称已存在！");
		}

		if (StringUtil.isNotBlank(role.getCode())
				&& !role.getCode().toUpperCase()
						.equals(ro.getCode().toUpperCase())// 比新增时多判断一步，允许和自己重复
				&& this.roleService.codeExist(role.getCode())) {
			otherErrors.put("code", "组织机构代码已存在！");
		}

		if (super.validate(role, request, otherErrors)) {
			// 设置更新时间
			role.setUpdateTime(DateUtil.getCurrentTimeAs14String());
			this.roleService.update(role);
		}
	}

	@Permission("单一删除或取消角色有效性")
	@RequestMapping("/remove/{id}")
	public void remove(@PathVariable String id, HttpServletResponse response) {
		Role role = this.roleService.get(id);
		// 若为有效，则变更为无效
		if (role != null && "1".equals(role.getEnbaled().trim())) {
			this.roleService.updateEnable("0", id);
		} else {
			this.roleService.remove(id);
		}
	}

	/**
	 * 
	 * 描述:批量删除
	 * 
	 * @param ids
	 *            使用requestBody注解接收页面传递有id组成的json对象
	 * @param response
	 * @author Ivan
	 * @date 2016年3月7日 上午1:46:11
	 */
	@Permission("批量删除或取消角色有效性")
	@RequestMapping(value = "/removes", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public void removes(@RequestBody List<String> ids,
			HttpServletResponse response) {
		Role role = this.roleService.get(ids.get(0));
		// 若为有效，则变更为无效
		if (role != null && "1".equals(role.getEnbaled().trim())) {
			this.roleService.updateEnables("0", ids);
		} else {
			this.roleService.delsteByIds(ids);
		}
	}

	@Permission("恢复角色")
	@RequestMapping("/recovery/{id}")
	public void recovery(@PathVariable String id, HttpServletResponse response) {
		this.roleService.updateEnable("1", id);
	}

}
