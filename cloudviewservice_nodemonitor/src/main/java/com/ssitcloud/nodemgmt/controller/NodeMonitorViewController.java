package com.ssitcloud.nodemgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.controller.BasicController;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.nodemgmt.service.NodeMonitorViewService;

@Controller
@RequestMapping(value = { "nodeMonitor" })
public class NodeMonitorViewController extends BasicController {

	@Resource
	private NodeMonitorViewService nodeMonitorViewService;

	/** 根据页面查询 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ModelAndView queryNodeMonitorByPage(HttpServletRequest request, String req) {
		Map<String, Object> model=new HashMap<>();
		model.put("oper",getCurrentUser());
		return new ModelAndView("/page/nodemonitor/node",model);
	}

	/** 根据条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryNodeMonitorByParam(HttpServletRequest request, String req) {
		return nodeMonitorViewService.queryNodeMonitorByParam(req);
	}

	/** 根据id查询 */
	@RequestMapping(value = { "queryById" })
	@ResponseBody
	public ResultEntity queryNodeMonitorById(HttpServletRequest request, String req) {
		return nodeMonitorViewService.queryNodeMonitorById(req);
	}
	
	/** 获取节点类型列表 */
	@RequestMapping(value = { "getTypeList" })
	@ResponseBody
	public ResultEntity getTypeList(HttpServletRequest request, String req) {
		return nodeMonitorViewService.getTypeList(req);
	}


}
