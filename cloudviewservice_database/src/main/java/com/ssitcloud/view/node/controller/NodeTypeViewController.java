package com.ssitcloud.view.node.controller;

/***********************************************************************
 * Module:  NodeTypeViewController.java
 * Author:  dell
 * Purpose: Defines the Class NodeTypeViewController
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
import com.ssitcloud.view.node.entity.NodeTypeEntity;
import com.ssitcloud.view.node.entity.page.NodeTypePageEntity;
import com.ssitcloud.view.node.service.NodeTypeViewService;

/** 节点类型 */
@Controller
@RequestMapping(value = { "nodeType" })
public class NodeTypeViewController extends BasicController {

	@Resource
	private NodeTypeViewService nodeTypeViewService;

	/** 分页查询 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ModelAndView queryNodeTypeByPage(HttpServletRequest request, String req) {
		//return nodeTypeViewService.queryNodeTypeByPage(req);
		Map<String, Object> model=new HashMap<>();
		model.put("oper",getCurrentUser());
		return new ModelAndView("/page/nodemgmt/node-type",model);
	}

	/** 条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryNodeTypeByParam(HttpServletRequest request, String req) {
		NodeTypePageEntity entity = nodeTypeViewService.queryNodeTypeByParam(req);
		ResultEntity result = new ResultEntity();
		if (entity != null) {
			result.setValue(true, "success", "", entity);
		} else {
			result.setState(false);
		}
		return result;
	}

	/** 根据id查询*/
	@RequestMapping(value = { "queryById" })
	@ResponseBody
	public ResultEntity queryNodeTypeById(HttpServletRequest request, String req) {
		NodeTypeEntity entity = nodeTypeViewService.queryNodeTypeById(req);
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
	public ResultEntity deleteNodeTypeById(HttpServletRequest request, String req) {
		return nodeTypeViewService.deleteNodeTypeById(req);
	}
	
	/** 更新 */
	@RequestMapping(value = { "update" })
	@ResponseBody
	public ResultEntity updateNodeType(HttpServletRequest request, String req) {
		return nodeTypeViewService.updateNodeType(req);
	}

	/** 新增 */
	@RequestMapping(value = { "add" })
	@ResponseBody
	public ResultEntity addNodeType(HttpServletRequest request, String req) {
		return nodeTypeViewService.addNodeType(req);
	}

}
