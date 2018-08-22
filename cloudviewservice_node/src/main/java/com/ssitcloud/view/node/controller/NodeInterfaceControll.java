package com.ssitcloud.view.node.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.node.service.NodeInterfaceService;

@Controller
@RequestMapping(value = { "nodeInterface" })
public class NodeInterfaceControll extends BasicController {
	
	@Autowired
	private NodeInterfaceService interfaceService;
	
	@RequestMapping("main")
	public String main(){
		return "/page/nodemgmt/interfaceUrlConfig";
	}
	
	/**添加节点URL信息*/
	@RequestMapping("addNodeInterface")
	@ResponseBody
	public ResultEntity addNodeInterface(String req){
		return interfaceService.addNodeInterface(req);
	}
	/**修改*/
	@RequestMapping("editNodeInterface")
	@ResponseBody
	public ResultEntity editNodeInterface(String req){
		return interfaceService.editNodeInterface(req);
		
	}
	
	/**分页查询节点URL*/
	@RequestMapping("queryNodeInterfaceByPage")
	@ResponseBody
	public ResultEntity queryNodeInterfaceByPage(String req){
		return interfaceService.queryNodeInterfaceByPage(req);
	}
	
	/**删除*/
	@RequestMapping("deleteNodeInterface")
	@ResponseBody
	public ResultEntity deleteNodeInterface(String req){
		return interfaceService.deleteNodeInterface(req);
	}
	
	/**根据节点名称查询节点所拥有的URL*/
	@RequestMapping("queryInterfaceByNodeName")
	@ResponseBody
	public String queryInterfaceByNodeName(String req){
		return interfaceService.queryInterfaceByNodeName(req);
	} 
	
	/**跳到节点清除缓存页面*/
	@RequestMapping("nodeCacheManager")
	public String nodeCacheManager(String req){
		return "/page/nodemgmt/nodeCacheManager";
	}
	
	/**清除节点缓存*/
	@RequestMapping("clearNodeCache")
	@ResponseBody
	public ResultEntity clearNodeCache(String req){
		return interfaceService.clearNodeCache(req);
	}
	
	/**查找节点的上一个节点*/
	@RequestMapping("queryPreNodes")
	@ResponseBody
	public ResultEntity queryPreNodes(String req){
		
		return interfaceService.queryPreNodesByPage(req);
	}
	

}
