package com.ssitcloud.view.database.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.database.service.ServerService;

@Controller
@RequestMapping(value = { "server" })
public class ServerViewController{

	@Resource
	private ServerService serverService;
	
	@RequestMapping(value = { "addDBConnect" })
	@ResponseBody
	public ResultEntity addDBConnect(HttpServletRequest request, String req) {
		return serverService.addDBConnect(req);
	}
	
	@RequestMapping(value = { "removeDBConnect" })
	@ResponseBody
	public ResultEntity removeDBConnect(HttpServletRequest request, String req) {
		return serverService.removeDBConnect(req);
	}
}
