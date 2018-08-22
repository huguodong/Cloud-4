package com.ssitcloud.business.nodemgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.nodemgmt.service.SwitchServerService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "switchServer" })
public class SwitchServerController {
	@Resource
	private SwitchServerService switchServerService;

	@RequestMapping(value = { "switch" })
	@ResponseBody
	public ResultEntity switcher(HttpServletRequest request, String req) {
		return switchServerService.switcher(req);
	}
}
