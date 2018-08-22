package com.ssitcloud.business.mobile.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.ssitcloud.business.mobile.service.DemoService;

/**
 * 测试demo
 *
 * <p>2017年2月22日 下午3:29:15  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
	@Resource
	private DemoService demoService;
	
	
	@RequestMapping("/demo")
	@ResponseBody
	public String demo(){
		demoService.demo();
		return "";
	}
	

}
