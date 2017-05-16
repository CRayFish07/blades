package com.iusofts.blades.sys.web.controller;

import javax.servlet.http.HttpServletResponse;

import com.iusofts.blades.sys.web.permission.Permission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Permission("主页")
@Controller
@RequestMapping("/sys")
public class MainController {
	
	@Permission("首页")
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletResponse response){
		ModelAndView mav=new ModelAndView("/sys/main");
		return mav;
	}
	
	@Permission("导航")
	@RequestMapping(value="/top")
	public ModelAndView top(HttpServletResponse response){
		ModelAndView mav=new ModelAndView("/sys/top");
		return mav;
	}
	
	@Permission("菜单")
	@RequestMapping(value="/left")
	public ModelAndView left(HttpServletResponse response){
		ModelAndView mav=new ModelAndView("/sys/left");
		return mav;
	}
	
	@Permission("默认页面")
	@RequestMapping(value="/default")
	public ModelAndView toDefault(HttpServletResponse response){
		ModelAndView mav=new ModelAndView("/sys/default");
		return mav;
	}
	
	@Permission("版权页面")
	@RequestMapping(value="/footer")
	public ModelAndView footer(HttpServletResponse response){
		ModelAndView mav=new ModelAndView("/sys/footer");
		return mav;
	}
}
