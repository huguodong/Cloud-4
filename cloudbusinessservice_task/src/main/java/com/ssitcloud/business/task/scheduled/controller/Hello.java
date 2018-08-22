package com.ssitcloud.business.task.scheduled.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.task.scheduled.service.SayHelloService;

/**
 * 测试demo
 *
 * <p>2017年2月22日 下午2:28:27  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/say")
public class Hello {
	@Resource
	private SayHelloService sayHelloService;
	
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		sayHelloService.sayHello();
		return "ss";
	}

}
