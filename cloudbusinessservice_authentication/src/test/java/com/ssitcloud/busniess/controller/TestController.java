package com.ssitcloud.busniess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>2016年4月15日 上午10:14:04
 * @author hjc
 */
@Controller
public class TestController {
	@RequestMapping("/api")
	public String goAPI() {
		return "/apitest";
	}
}
