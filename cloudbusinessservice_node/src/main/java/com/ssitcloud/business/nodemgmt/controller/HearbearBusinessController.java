package com.ssitcloud.business.nodemgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.PropertiesUtil;
import com.ssitcloud.business.common.util.StringUtils;
import com.ssitcloud.business.nodemgmt.service.HearbeatBusinessService;

@Controller
@RequestMapping(value = { "hearbeat" })
public class HearbearBusinessController {
	@Resource
	private HearbeatBusinessService hearbeatBusinessService;

	@RequestMapping(value = { "saveData" })
	@ResponseBody
	public String saveHearbeatData(HttpServletRequest request, String req) {
		if (!StringUtils.hasText(req)) {
			return "error";
		}
		String developer_model = PropertiesUtil.getValue("developer_model");
		if ("true".equals(developer_model)) {
			return "success";
		}
		return hearbeatBusinessService.saveData(req);
	}
}
