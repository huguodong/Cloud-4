package com.ssitcloud.business.nodemgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.nodemgmt.service.NodeMonitorBusinessService;

@Controller
@RequestMapping(value = { "nodeMonitor" })
public class NodeMonitorBusinessController {
	@Resource
	private NodeMonitorBusinessService nodeMonitorBusinessService;

	/** 分页查询方法 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public String queryNodeMonitorByPage(HttpServletRequest request, String req) {
		return nodeMonitorBusinessService.queryNodeMonitorByPage(req);
	}

	/** 根据条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public String queryNodeMonitorByParam(HttpServletRequest request, String req) {
		return nodeMonitorBusinessService.queryNodeMonitorByParam(req);
	}

	/** 根据ID查询 */
	@RequestMapping(value = { "queryById" })
	@ResponseBody
	public String queryNodeMonitorById(HttpServletRequest request, String req) {
		return nodeMonitorBusinessService.queryNodeMonitorById(req);
	}
	
	/** 获取节点类型列表 */
	@RequestMapping(value = { "getTypeList" })
	@ResponseBody
	public String getTypeList(HttpServletRequest request, String req) {
		return nodeMonitorBusinessService.getTypeList(req);
	}
}
