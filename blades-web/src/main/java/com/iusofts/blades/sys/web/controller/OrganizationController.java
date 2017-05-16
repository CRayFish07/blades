package com.iusofts.blades.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iusofts.blades.sys.common.util.ServletUtil;
import com.iusofts.blades.sys.web.permission.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iusofts.blades.sys.common.util.FastJsonUtils;
import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.common.util.UUID;
import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.model.Organization;
import com.iusofts.blades.sys.service.OrganizationService;

@Permission("机构管理")
@Controller
@RequestMapping("/sys/org")
public class OrganizationController extends BaseController {
	@Autowired
	private OrganizationService organizationService;

	@Permission("机构列表")
	@RequestMapping("/list")
	public ModelAndView list(@ModelAttribute Organization org,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/org/org_list");
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
		Page<Organization> page = this.organizationService.getPage(org, other,
				super.getPageNo(request), super.getPageSize(request));
		if(StringUtil.isNotBlank(org.getPid())){
			mav.addObject("porg",this.organizationService.get(org.getPid()));
		}
		mav.addObject("idValues",request.getParameter("idValues"));
		mav.addObject("page", page);
		mav.addObject("org", org);
		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);
		return mav;
	}

	@Permission("机构保存页面")
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView toSave(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/org/org_save");
		String pid=request.getParameter("pid");
		if(StringUtil.isNotBlank(pid)){
			mav.addObject("porg",this.organizationService.get(pid));
		}
		mav.addObject("tree", request.getParameter("tree"));
		return mav;
	}

	@Permission("机构保存操作")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void doSave(@ModelAttribute Organization orgtionary,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> otherErrors = new HashMap<>();

		if (StringUtil.isNotBlank(orgtionary.getName())
				&& this.organizationService.nameExist(orgtionary.getName(),orgtionary.getPid())) {
			otherErrors.put("name", "组织机构名称已存在！");
		}

		if (StringUtil.isNotBlank(orgtionary.getCode())
				&& this.organizationService.codeExist(orgtionary.getCode(),orgtionary.getPid())) {
			otherErrors.put("code", "组织机构代码已存在！");
		}

		if (super.validate(orgtionary, request, otherErrors)) {
			orgtionary.setId(UUID.getUuid());
			this.organizationService.save(orgtionary);
		}
	}

	@Permission("机构删除")
	@RequestMapping("/remove")
	public void remove(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		this.organizationService.remove(id);
	}

	@Permission("机构详情")
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam("id") String id,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/org/org_view");
		mav.addObject("org", this.organizationService.get(id));
		return mav;
	}

	@Permission("机构编辑页面")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView toUpdate(@RequestParam("id") String id,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/org/org_update");
		mav.addObject("org", this.organizationService.get(id));
		mav.addObject("tree", request.getParameter("tree"));
		return mav;
	}

	@Permission("机构编辑操作")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void doUpdate(@ModelAttribute Organization orgtionary,
			HttpServletRequest request, HttpServletResponse response) {
		Organization org = this.organizationService.get(orgtionary.getId());
		Map<String, String> otherErrors = new HashMap<>();

		if (StringUtil.isNotBlank(orgtionary.getName())
				&& !orgtionary.getName().equals(org.getName())//比新增时多判断一步，允许和自己重复
				&& this.organizationService.nameExist(orgtionary.getName(),orgtionary.getPid())) {
			otherErrors.put("name", "组织机构名称已存在！");
		}

		if (StringUtil.isNotBlank(orgtionary.getCode())
				&& !orgtionary.getCode().toUpperCase()
						.equals(org.getCode().toUpperCase())//比新增时多判断一步，允许和自己重复
				&& this.organizationService.codeExist(orgtionary.getCode(),orgtionary.getPid())) {
			otherErrors.put("code", "组织机构代码已存在！");
		}

		if (super.validate(orgtionary, request, otherErrors)) {
			this.organizationService.update(orgtionary);
		}
	}
	
	@Permission("主页")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView treeManage(HttpServletRequest request) {
		String id=request.getParameter("id");
		if(StringUtil.isBlank(id))id="01";
		ModelAndView mav = new ModelAndView("/sys/org/org_manage");
		mav.addObject("org", this.organizationService.get(id));
		return mav;
	}
	
	/**
	 * 机构树页面
	 * @param id
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年2月26日 下午2:42:36
	 */
	@Permission("机构树页面")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public ModelAndView toTree(@RequestParam("id") String id,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/org/org_tree");
		mav.addObject("org", this.organizationService.get(id));
		return mav;
	}
	
	/**
	 * 机构树数据
	 * @param pid
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年2月26日 下午2:42:55
	 */
	@Permission("机构树数据")
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public void getTree(@RequestParam("pid") String pid,
			HttpServletRequest request,HttpServletResponse response) {
		String result="";
		Map<String,String> map=super.getParameter(request);
		map.put("root", ServletUtil.getBaseURL(request));
		List<Map<String,String>> list= this.organizationService.getTreeDataByMap(map);
		if(list!=null){
			result=FastJsonUtils.obj2json(list);
		}
		super.flushResponse(response, result);
	}
	
	/**
	 * 导航
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年2月26日 下午2:42:36
	 */
	@Permission("导航")
	@RequestMapping(value = "/top")
	public ModelAndView toTreeTop(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/org/org_top");
		mav.addObject("org", this.organizationService.get(request.getParameter("id")));
		return mav;
	}
	
	/**
	 * 
	 * 描述:批量删除
	 * @param ids
	 * @param response
	 * @author Asker_lve
	 * @date 2016年3月9日 下午4:30:02
	 */
	@Permission("批量删除机构")
	@RequestMapping(value = "/removes", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public void removes(@RequestBody List<String> ids,
			HttpServletResponse response) {
		this.organizationService.delsteByIds(ids);
	}
	
}
