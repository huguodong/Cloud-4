package com.ssitcloud.view.node.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.node.service.SwitchViewService;

@Controller
@RequestMapping(value = { "switchController" })
public class SwitchViewController {
	@Resource
	private SwitchViewService switchServerService;

	@RequestMapping(value = { "switch" })
	@ResponseBody
	public ResultEntity switcher(HttpServletRequest request, String req) {
		return switchServerService.switcher(req);
	}
}
