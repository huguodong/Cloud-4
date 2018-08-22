package com.ssitcloud.business.database.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.database.service.ServerService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "server" })
public class ServerViewController{

	@Resource
	private ServerService serverService;
	
	@RequestMapping(value = { "getUserMenus" })
	@ResponseBody
	public ResultEntity getUserMenus(HttpServletRequest request, String req) {
		return serverService.getUserMenus();
	}
	
	@RequestMapping(value = { "getServerConfig" })
	@ResponseBody
	public ResultEntity getServerConfig(HttpServletRequest request, String req) {
		return serverService.getServerConfig();
	}
	
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
