package com.ssitcloud.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("help")
public class HelpController {

	
	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request){
		String url=request.getParameter("url");
		if(url==null){
			url="/page/common/help/devmonitor/devmonitor.jsp";
		}
		Map<String,Object> model=new HashMap<>();
		model.put("url", url);
		return new ModelAndView("/page/help-page",model);
	}
	
	
	
	
	
}
