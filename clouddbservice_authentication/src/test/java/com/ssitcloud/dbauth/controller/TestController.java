package com.ssitcloud.dbauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * 测试接口跳转
 * <p>2016年3月31日 下午4:19:30  
 * @author hjc 
 *
 */
@Controller
public class TestController {
	
	@RequestMapping("/api")
	public String goAPI(){
		return "/apitest";
	}
}
