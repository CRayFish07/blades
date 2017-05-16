package com.iusofts.blades.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iusofts.blades.sys.common.util.FastJsonUtils;
import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.common.util.ServletUtil;
import com.iusofts.blades.sys.common.util.UUID;
import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.model.Dictionary;
import com.iusofts.blades.sys.service.DictionaryService;
import com.iusofts.blades.sys.web.permission.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.common.util.date.DateStyle;

@Permission("字典管理")
@Controller
@RequestMapping("/sys/dic")
public class DictionaryController extends BaseController {
	@Autowired
	private DictionaryService dictionaryService;

	@Permission("字典列表")
	@RequestMapping("/list")
	public ModelAndView list(@ModelAttribute Dictionary dic,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/dic/dic_list");
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
		Page<Dictionary> page = this.dictionaryService.getPage(dic, other,
				super.getPageNo(request), super.getPageSize(request));
		if(StringUtil.isNotBlank(dic.getPid())){
			mav.addObject("pdic",this.dictionaryService.get(dic.getPid()));
		}
		mav.addObject("idValues",request.getParameter("idValues"));
		mav.addObject("page", page);
		mav.addObject("dic", dic);
		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);
		return mav;
	}

	@Permission("字典保存页面")
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView toSave(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/dic/dic_save");
		String pid=request.getParameter("pid");
		if(StringUtil.isNotBlank(pid)){
			mav.addObject("pdic",this.dictionaryService.get(pid));
		}
		mav.addObject("tree", request.getParameter("tree"));
		return mav;
	}

	@Permission("字典保存操作")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void doSave(@ModelAttribute Dictionary dictionary,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> otherErrors = new HashMap<>();

		if (StringUtil.isNotBlank(dictionary.getName())
				&& this.dictionaryService.nameExist(dictionary.getName(),dictionary.getPid())) {
			otherErrors.put("name", "字典名称已存在！");
		}

		if (StringUtil.isNotBlank(dictionary.getCode())
				&& this.dictionaryService.codeExist(dictionary.getCode(),dictionary.getPid())) {
			otherErrors.put("code", "字典代码已存在！");
		}
		
		if (super.validate(dictionary, request, otherErrors)) {
			dictionary.setId(UUID.getUuid());
			this.dictionaryService.save(dictionary);
		}
	}

	@Permission("字典删除")
	@RequestMapping("/remove")
	public void remove(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		this.dictionaryService.remove(id);
	}

	@Permission("字典详情")
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam("id") String id,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/dic/dic_view");
		mav.addObject("dic", this.dictionaryService.get(id));
		return mav;
	}

	@Permission("字典编辑页面")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView toUpdate(@RequestParam("id") String id,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/dic/dic_update");
		Dictionary dic=this.dictionaryService.get(id);
		mav.addObject("dic", dic);
		mav.addObject("pdic", this.dictionaryService.get(dic.getPid()));
		mav.addObject("tree", request.getParameter("tree"));
		return mav;
	}

	@Permission("字典编辑操作")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void doUpdate(@ModelAttribute Dictionary dictionary,
			HttpServletRequest request, HttpServletResponse response) {
		Dictionary dic = this.dictionaryService.get(dictionary.getId());
		Map<String, String> otherErrors = new HashMap<>();

		if (StringUtil.isNotBlank(dictionary.getName())
				&& !dictionary.getName().equals(dic.getName())//比新增时多判断一步，允许和自己重复
				&& this.dictionaryService.nameExist(dictionary.getName(),dictionary.getPid())) {
			otherErrors.put("name", "字典名称已存在！");
		}

		if (StringUtil.isNotBlank(dictionary.getCode())
				&& !dictionary.getCode().toUpperCase()
						.equals(dic.getCode().toUpperCase())//比新增时多判断一步，允许和自己重复
				&& this.dictionaryService.codeExist(dictionary.getCode(),dictionary.getPid())) {
			otherErrors.put("code", "字典代码已存在！");
		}

		if (super.validate(dictionary, request, otherErrors)) {
			this.dictionaryService.update(dictionary);
		}
	}
	
	@Permission("树形字典管理")
	@RequestMapping(value = "/tree_manage", method = RequestMethod.GET)
	public ModelAndView treeManage(@RequestParam("id") String id,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/dic/dic_tree_manage");
		mav.addObject("dic", this.dictionaryService.get(id));
		return mav;
	}
	
	/**
	 * 字典树页面
	 * @param id
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年2月26日 下午2:42:36
	 */
	@Permission("字典树")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public ModelAndView toTree(@RequestParam("id") String id,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/dic/dic_tree");
		mav.addObject("dic", this.dictionaryService.get(id));
		return mav;
	}
	
	/**
	 * 字典树数据
	 * @param pid
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年2月26日 下午2:42:55
	 */
	@Permission("字典树数据")
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public void getTree(@RequestParam("pid") String pid,
			HttpServletRequest request,HttpServletResponse response) {
		String result="";
		Map<String,String> map=super.getParameter(request);
		map.put("root", ServletUtil.getBaseURL(request));
		List<Map<String,String>> list= this.dictionaryService.getTreeDataByMap(map);
		if(list!=null){
			result= FastJsonUtils.obj2json(list);
		}
		super.flushResponse(response, result);
	}
	
	/**
	 * 字典树页面
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年2月26日 下午2:42:36
	 */
	@Permission("字典树导航")
	@RequestMapping(value = "/tree_top")
	public ModelAndView toTreeTop(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/dic/dic_tree_top");
		mav.addObject("dic", this.dictionaryService.get(request.getParameter("id")));
		return mav;
	}
	
	/**
	 * 字典树列表页面
	 * @param dic
	 * @param request
	 * @return
	 * @author：Ivan
	 * @date：2016年2月26日 下午2:42:36
	 */
	@Permission("树形字典列表")
	@RequestMapping(value = "/tree_list")
	public ModelAndView treeList(@ModelAttribute Dictionary dic,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sys/dic/dic_tree_list");
		Map<String, String> other = new HashMap<String, String>();//其它查询条件（实体中不包含的属性）
		String startTime = request.getParameter("startTime");
		if (StringUtil.isNotBlank(startTime)) {
			other.put(
					"startTime",
					DateUtil.parseString(startTime, DateStyle.YYYYMMDDHHMMSS));
					
		}
		String endTime = request.getParameter("endTime");
		if (StringUtil.isNotBlank(endTime)) {
			other.put(
					"endTime",
					DateUtil.parseString(endTime,DateStyle.YYYYMMDDHHMMSS));
		}
		Page<Dictionary> page = this.dictionaryService.getPage(dic, other,
				super.getPageNo(request), super.getPageSize(request));
		if(StringUtil.isNotBlank(dic.getPid())){
			mav.addObject("pdic",this.dictionaryService.get(dic.getPid()));
		}
		mav.addObject("idValues",request.getParameter("idValues"));
		mav.addObject("page", page);
		mav.addObject("dic", dic);
		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);
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
	@Permission("批量删除字典")
	@RequestMapping(value = "/removes", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public void removes(@RequestBody List<String> ids,
			HttpServletResponse response) {
		this.dictionaryService.delsteByIds(ids);
	}
}
