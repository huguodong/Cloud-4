package com.ssitcloud.business.nodemgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.nodemgmt.service.NodeInterfaceService;
import com.ssitcloud.common.entity.ResultEntity;


@Controller()
@RequestMapping("nodeInterface")
public class NodeInterfaceControll {
	
	@Autowired
	private NodeInterfaceService interfaceService;
	
	@RequestMapping("addNodeInterface")
	@ResponseBody
	public ResultEntity addNodeInterface(String req){
		return interfaceService.addNodeInterface(req);
	}
	
	@RequestMapping("editNodeInterface")
	@ResponseBody
	public ResultEntity editNodeInterface(String req){
		return interfaceService.editNodeInterface(req);
		
	}
	
	@RequestMapping("queryNodeInterfaceByPage")
	@ResponseBody
	public ResultEntity queryNodeInterfaceByPage(String req){
		return interfaceService.queryNodeInterfaceByPage(req);
	}
	
	@RequestMapping("deleteNodeInterface")
	@ResponseBody
	public ResultEntity deleteNodeInterface(String req){
		return interfaceService.deleteNodeInterface(req);
	}
	
	@RequestMapping("queryInterfaceByNodeName")
	@ResponseBody
	public String queryInterfaceByNodeName(String req){
		return interfaceService.queryInterfaceByNodeName(req);
	}
	
	
	@RequestMapping("clearNodeCache")
	@ResponseBody
	public ResultEntity clearNodeCache(String req){
		return interfaceService.clearNodeCache(req);
	}
	
	
	/**查找节点的上一个节点*/
	@RequestMapping("queryPreNodesByPage")
	@ResponseBody
	public ResultEntity queryPreNodesByPage(String req){
		return interfaceService.queryPreNodesByPage(req);
	}
	
	
}
