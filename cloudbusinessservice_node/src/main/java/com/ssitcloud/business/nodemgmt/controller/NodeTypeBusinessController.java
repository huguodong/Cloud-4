package com.ssitcloud.business.nodemgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.nodemgmt.service.NodeTypeBusinessService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "nodeType" })
public class NodeTypeBusinessController {

	@Resource
	private NodeTypeBusinessService nodeTypeBusinessService;

	/** 分页查询方法 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ResultEntity queryNodeTypeByPage(HttpServletRequest request, String req) {
		return nodeTypeBusinessService.queryNodeTypeByPage(req);
	}

	/** 根据条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryNodeTypeByParam(HttpServletRequest request, String req) {
		return nodeTypeBusinessService.queryNodeTypeByParam(req);
	}

	/** 根据ID查询 */
	@RequestMapping(value = { "queryById" })
	@ResponseBody
	public ResultEntity queryNodeTypeById(HttpServletRequest request, String req) {
		return nodeTypeBusinessService.queryNodeTypeById(req);
	}

	/** 删除 */
	@RequestMapping(value = { "delete" })
	@ResponseBody
	public ResultEntity deleteNodeTypeById(HttpServletRequest request, String req) {
		return nodeTypeBusinessService.deleteNodeTypeById(req);
	}

	/** 更新 */
	@RequestMapping(value = { "update" })
	@ResponseBody
	public ResultEntity updateNodeType(HttpServletRequest request, String req) {
		return nodeTypeBusinessService.updateNodeType(req);
	}

	/** 新增 */
	@RequestMapping(value = { "add" })
	@ResponseBody
	public ResultEntity addNodeType(HttpServletRequest request, String req) {
		return nodeTypeBusinessService.addNodeType(req);
	}
}
