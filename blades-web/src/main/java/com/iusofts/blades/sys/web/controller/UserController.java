package com.iusofts.blades.sys.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.common.util.ServletUtil;
import com.iusofts.blades.sys.common.util.UUID;
import com.iusofts.blades.sys.model.User;
import com.iusofts.blades.sys.service.AccountService;
import com.iusofts.blades.sys.service.UserOrgService;
import com.iusofts.blades.sys.service.UserService;
import com.iusofts.blades.sys.web.permission.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.common.util.ValidateUtil;
import com.iusofts.blades.sys.model.Account;
import com.iusofts.blades.sys.model.UserOrg;

@Permission("用户管理")
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserOrgService userOrgService;

	@Permission("用户列表")
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/user/user_list");
		Map<String, String> queryMap = super.getParameter(request);
		Page<Map<String, String>> page = this.userService.getPage(queryMap,
				super.getPageNo(request), super.getPageSize(request));
		mav.addObject("page", page);
		mav.addObject("map", queryMap);
		return mav;
	}

	@Permission("用户添加")
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView toSave(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/user/user_save");
		return mav;
	}

	@Permission("用户添加")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void doSave(@ModelAttribute User user,
			@ModelAttribute Account account, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, String> otherErrors = new HashMap<>();// 自定义错误信息

		/*if (StringUtil.isNotBlank(account.getUsername())
				&& this.accountService.isUserNameExist(account.getUsername()) > 0) {
			otherErrors.put("username", "用户名已存在!");
		}

		if (StringUtil.isNotBlank(user.getIdNo())
				&& this.userService.isIdNoExist(user.getIdNo()) > 0) {
			otherErrors.put("idNo", "身份证号已经存在!");
		}*/

		if (super.validate(user, request, otherErrors)) {
			user.setId(UUID.getUuid());
			account.setRegIp(ServletUtil.getIpAddr(request));
			List<UserOrg> orgList = getOrgList(request);
			this.userService.save(user, account, orgList);
		}

	}

	@Permission("用户详情")
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView("/sys/user/user_view");
		Account account = this.accountService.get(id);
		User user = this.userService.get(id);
		List<Map<String, String>> orgList = this.userOrgService.selectByUid(id);

		mav.addObject("account", account);
		mav.addObject("user", user);
		mav.addObject("orgList", orgList);
		return mav;
	}

	@Permission("修改用户页面")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable String id, Map<String, Object> map) {
		Account account = this.accountService.get(id);
		User user = this.userService.get(id);
		List<Map<String, String>> orgList = this.userOrgService.selectByUid(id);

		if (ValidateUtil.isValid(orgList)) {

			// 隶属单位
			StringBuffer ids1 = new StringBuffer();// 已选ids
			StringBuffer values1 = new StringBuffer();

			// 兼职单位（多个）
			StringBuffer ids2 = new StringBuffer();
			StringBuffer codes2 = new StringBuffer();
			StringBuffer values2 = new StringBuffer();

			// 分管单位（多个）
			StringBuffer ids3 = new StringBuffer();
			StringBuffer codes3 = new StringBuffer();
			StringBuffer values3 = new StringBuffer();

			// 初始化已选中的值,让其分别匹配
			for (Map<String, String> org : orgList) {
				String relation = org.get("RELATION");
				String orgId = org.get("ID");
				String pName = org.get("PNAME");
				String name = org.get("NAME");
				String code = org.get("CODE");
				if ("1".equals(relation)) {
					ids1.append(orgId + ",");
					values1.append(pName + "[" + name + "],");
				} else if ("2".equals(relation)) {
					ids2.append(orgId + ",");
					codes2.append(code + ",");
					values2.append(pName + "[" + name + "],");
				} else if ("3".equals(relation)) {
					ids3.append(orgId + ",");
					codes3.append(code + ",");
					values3.append(pName + "[" + name + "],");
				}
			}
			map.put("codes2", subString(codes2));
			map.put("codes3", subString(codes3));

			map.put("ids1", subString(ids1));
			map.put("ids2", subString(ids2));
			map.put("ids3", subString(ids3));

			map.put("values1", subString(values1));
			map.put("values2", subString(values2));
			map.put("values3", subString(values3));
		}

		map.put("account", account);
		map.put("user", user);
		map.put("orgList", orgList);
		return "/sys/user/user_update";
	}

	@Permission("修改用户操作")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void doUpdate(@ModelAttribute User user,
			@ModelAttribute Account account, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, String> otherErrors = new HashMap<>();// 自定义错误信息

		if (StringUtil.isNotBlank(account.getUsername())
				&& this.accountService.isUserNameExist(account.getUsername()) > 0
				&& !account.getUsername().equalsIgnoreCase(// 允许没做修改的重复
						this.accountService.get(account.getId()).getUsername())) {
			otherErrors.put("username", "用户名已存在!");
		}

		if (StringUtil.isNotBlank(user.getIdNo())
				&& this.userService.isIdNoExist(user.getIdNo()) > 0
				&& !user.getIdNo().equalsIgnoreCase(
						this.userService.get(user.getId()).getIdNo())) {
			otherErrors.put("idNo", "身份证号已经存在!");
		}

		if (super.validate(user, request, otherErrors)) {
			List<UserOrg> orgList = getOrgList(request);
			this.userService.update(user, account, orgList);
		}
	}

	/**
	 * 获取用户隶属、兼职、分管的组织机构
	 * 
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年3月3日 下午5:09:24
	 */
	private List<UserOrg> getOrgList(HttpServletRequest request) {
		List<UserOrg> orgList = new ArrayList<UserOrg>();
		// 获取隶属单位（单个）
		if (StringUtil.isNotBlank(request.getParameter("affiliation"))) {
			UserOrg userOrg = new UserOrg();
			userOrg.setOrgId(request.getParameter("affiliation"));
			userOrg.setRelation("1");
			orgList.add(userOrg);
		}
		// 获取兼职单位（多个）
		if (StringUtil.isNotBlank(request.getParameter("partTime"))) {
			String partTimes[] = request.getParameter("partTime").split(",");
			for (String p : partTimes) {
				UserOrg userOrg = new UserOrg();
				userOrg.setOrgId(p);
				userOrg.setRelation("2");
				orgList.add(userOrg);
			}

		}
		// 获取分管单位（多个）
		if (StringUtil.isNotBlank(request.getParameter("manage"))) {
			String manages[] = request.getParameter("manage").split(",");
			for (String m : manages) {
				UserOrg userOrg = new UserOrg();
				userOrg.setOrgId(m);
				userOrg.setRelation("3");
				orgList.add(userOrg);
			}
		}
		return orgList;
	}

	@Permission("单一删除或取消用户有效性")
	@RequestMapping("/remove/{id}")
	public void remove(@PathVariable String id, HttpServletResponse response) {
		Account account = this.accountService.get(id);
		// 若为有效，则变更为无效
		if (account != null && "1".equals(account.getEnabled().trim())) {
			this.userService.updateEnable("0", id);
		} else {
			this.userService.remove(id);
		}
	}

	@Permission("批量删除或取消用户有效性")
	@RequestMapping(value = "/removes", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public void removes(@RequestBody List<String> ids,
			HttpServletResponse response) {
		// 获取批量ids里的一个成员，若一个有效，则全都有效
		Account account = this.accountService.get(ids.get(0));
		// 若为有效，则变更为无效
		if (account != null && "1".equals(account.getEnabled().trim())) {
			this.userService.updateEnables("0", ids);
		} else {
			this.userService.delsteByIds(ids);
		}
	}

	@Permission("恢复用户")
	@RequestMapping("/recovery/{id}")
	public void recovery(@PathVariable String id, HttpServletResponse response) {
		this.userService.updateEnable("1", id);
	}
	
	@Permission("测试")
	@ResponseBody
	@RequestMapping("/test")
	public User test(){
		User user = new User();
		user.setAddress("南京");
		int n = 0;
		System.out.println(1/n);
		return user;
	}

	/**
	 * 
	 * 描述: 将字符串长度减1返回
	 * 
	 * @param str
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月11日 下午5:58:07
	 */
	private String subString(StringBuffer str) {
		if (str != null && str.length() > 1) {
			return str.substring(0, str.length() - 1);
		}
		return null;
	}

}
