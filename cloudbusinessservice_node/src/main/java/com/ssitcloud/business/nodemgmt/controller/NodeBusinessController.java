package com.ssitcloud.business.nodemgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.nodemgmt.service.NodeBusinessService;
import com.ssitcloud.common.entity.ResultEntity;

/** 节点管理 */
@Controller
@RequestMapping(value = { "node" })
public class NodeBusinessController {
	
	@Resource
	private NodeBusinessService nodeBusinessService;

	/** 分页查询方法 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ResultEntity queryNodeByPage(HttpServletRequest request, String req) {
		return nodeBusinessService.queryNodeByPage(req);
	}

	/** 根据条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryNodeByParam(HttpServletRequest request, String req) {
		return nodeBusinessService.queryNodeByParam(req);
	}

	/** 根据ID查询 */
	@RequestMapping(value = { "queryById" })
	@ResponseBody
	public ResultEntity queryNodeById(HttpServletRequest request, String req) {
		return nodeBusinessService.queryNodeById(req);
	}

	/** 删除 */
	@RequestMapping(value = { "delete" })
	@ResponseBody
	public ResultEntity deleteNodeById(HttpServletRequest request, String req) {
		return nodeBusinessService.deleteNodeById(req);
	}

	/** 更新 */
	@RequestMapping(value = { "update" })
	@ResponseBody
	public ResultEntity updateNode(HttpServletRequest request, String req) {
		return nodeBusinessService.updateNode(req);
	}

	/** 新增 */
	@RequestMapping(value = { "add" })
	@ResponseBody
	public ResultEntity addNode(HttpServletRequest request, String req) {
		return nodeBusinessService.addNode(req);
	}
	
	/** 
	 * 查找节点的ip和端口 
	 * {"node_name":"cloudbusinessservice_node"}
	 */
	@RequestMapping(value = { "findAddressByNodeName" })
	@ResponseBody
	public ResultEntity findAddressByNodeName(HttpServletRequest request, String req) {
		return nodeBusinessService.findAddressByNodeName(req);
	}
	
	/** 
	 * 查找节点的ip和端口 
	 * {"node_name":"cloudbusinessservice_node","lib_idx":1}
	 */
	@RequestMapping(value = { "findAddressForSSO" })
	@ResponseBody
	public ResultEntity findAddressForSSO(HttpServletRequest request, String req) {
		return nodeBusinessService.findAddressForSSO(req);
	}
	
	@RequestMapping(value = { "queryNodeGroupByName" })
	@ResponseBody
	public ResultEntity queryNodeGroupByName(){
		return nodeBusinessService.queryNodeGroupByName();
	}
}
