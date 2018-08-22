package com.ssitcloud.business.nodemgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.nodemgmt.service.HostBusinessService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "host" })
public class HostBusinessController {
	
	@Resource
	private HostBusinessService hostBusinessService;
	
	/** 分页查询方法 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ResultEntity queryHostByPage(HttpServletRequest request, String req) {
		return hostBusinessService.queryHostByPage(req);
	}

	/** 根据条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryHostByParam(HttpServletRequest request, String req) {
		return hostBusinessService.queryHostByParam(req);
	}

	/** 根据ID查询 */
	@RequestMapping(value = { "queryById" })
	@ResponseBody
	public ResultEntity queryHostById(HttpServletRequest request, String req) {
		return hostBusinessService.queryHostById(req);
	}

	/** 删除 */
	@RequestMapping(value = { "delete" })
	@ResponseBody
	public ResultEntity deleteHostById(HttpServletRequest request, String req) {
		return hostBusinessService.deleteHostById(req);
	}

	/** 更新 */
	@RequestMapping(value = { "update" })
	@ResponseBody
	public ResultEntity updateHost(HttpServletRequest request, String req) {
		return hostBusinessService.updateHost(req);
	}

	/** 新增 */
	@RequestMapping(value = { "add" })
	@ResponseBody
	public ResultEntity addHost(HttpServletRequest request, String req) {
		return hostBusinessService.addHost(req);
	}
}
