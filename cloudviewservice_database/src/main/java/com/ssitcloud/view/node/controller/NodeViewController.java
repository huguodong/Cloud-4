package com.ssitcloud.view.node.controller;

/***********************************************************************
 * Module:  NodeViewController.java
 * Author:  dell
 * Purpose: Defines the Class NodeViewController
 ***********************************************************************/

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.node.entity.NodeEntity;
import com.ssitcloud.view.node.entity.page.NodePageEntity;
import com.ssitcloud.view.node.service.ContainerViewService;
import com.ssitcloud.view.node.service.NodeTypeViewService;
import com.ssitcloud.view.node.service.NodeViewService;

/** 节点管理 */
@Controller
@RequestMapping(value = { "node" })
public class NodeViewController extends BasicController {
	
	@Resource
	private NodeViewService nodeViewService;
	@Resource
	private NodeTypeViewService nodeTypeViewService;
	@Resource
	private ContainerViewService containerViewService;

	/** 分页查询方法 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ModelAndView queryNodeByPage(HttpServletRequest request, String req) {
		Map<String, Object> model=new HashMap<>();
		model.put("oper",getCurrentUser());
		return new ModelAndView("/page/nodemgmt/node-manage",model);
	}

	/** 根据条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryNodeByParam(HttpServletRequest request, String req) {
		NodePageEntity entity = nodeViewService.queryNodeByParam(req);
		ResultEntity result = new ResultEntity();
		if (entity != null) {
			result.setValue(true, "success", "", entity);
		} else {
			result.setState(false);
		}
		return result;
	}

	/** 根据ID查询 */
	@RequestMapping(value = { "queryById" })
	@ResponseBody
	public ResultEntity queryNodeById(HttpServletRequest request, String req) {
		NodeEntity entity = nodeViewService.queryNodeById(req);
		ResultEntity result = new ResultEntity();
		if (entity != null) {
			result.setValue(true, "success", "", entity);
		} else {
			result.setState(false);
		}
		return result;
	}

	/** 删除 */
	@RequestMapping(value = { "delete" })
	@ResponseBody
	public ResultEntity deleteNodeById(HttpServletRequest request, String req) {
		return nodeViewService.deleteNodeById(req);
	}

	/** 更新 */
	@RequestMapping(value = { "update" })
	@ResponseBody
	public ResultEntity updateNode(HttpServletRequest request, String req) {
		return nodeViewService.updateNode(req);
	}

	/** 新增 */
	@RequestMapping(value = { "add" })
	@ResponseBody
	public ResultEntity addNode(HttpServletRequest request, String req) {
		return nodeViewService.addNode(req);
	}
	
	/** 获取节点类型列表 */
	@RequestMapping(value = { "getTypeList" })
	@ResponseBody
	public ResultEntity getTypeList(HttpServletRequest request, String req) {
		return nodeTypeViewService.queryNodeTypeByPage(req);
	}
	
	/** 获取节点列表 */
	@RequestMapping(value = { "getNodeList" })
	@ResponseBody
	public ResultEntity getNodeList(HttpServletRequest request, String req) {
		return nodeViewService.queryNodeByPage(req);
	}
	
	/** 获取图书馆列表 */
	@RequestMapping(value = { "getLibList" })
	@ResponseBody
	public ResultEntity getLibList(HttpServletRequest request, String req) {
		return nodeViewService.getLibList(req);
	}
	
	/** 获取节点列表 */
	@RequestMapping(value = { "getContainerList" })
	@ResponseBody
	public ResultEntity getContainerList(HttpServletRequest request, String req) {
		return containerViewService.queryContainerByPage(req);
	}
	

}
