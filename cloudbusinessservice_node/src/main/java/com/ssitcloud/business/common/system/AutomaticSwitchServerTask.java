package com.ssitcloud.business.common.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;

import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.PropertiesUtil;
import com.ssitcloud.business.common.util.StringUtils;
import com.ssitcloud.business.nodemgmt.service.HearbeatBusinessService;
import com.ssitcloud.business.nodemgmt.service.SwitchServerService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.node.entity.PrimaryNodeEntity;

public class AutomaticSwitchServerTask {
	@Resource
	private SwitchServerService switchServerService;
	
	@Resource
	private HearbeatBusinessService getHearbeatBusinessService;
	
	public void switchServer(){
		String str = PropertiesUtil.getValue("switchServer_automatic");
		if(StringUtils.hasLength(str) && "false".equals(str)){
			return;
		}
		//先处理view节点
		String viewReq="{\"node_attributes\":\"primary\",\"node_model\":\"view\"}";
		List viewNodeIdxs = new ArrayList<>();
		ResultEntity viewResult = getHearbeatBusinessService.getDeadNodes(viewReq);
		if(viewResult != null && viewResult.getState()){
			List<PrimaryNodeEntity> viewNodes = JsonUtils.convertMap(viewResult.getResult(), new TypeReference<List<PrimaryNodeEntity>>(){});
			for(PrimaryNodeEntity node : viewNodes){
				String req="{\"type\":\"manual\",\"node_idx\":\""+node.getNode_idx()+"\"}";
				ResultEntity result = switchServerService.switcher(req);
				if(result != null && !result.getState()){
					viewNodeIdxs.add(node.getNode_idx());
				}
			}
		}
		
		//处理business节点
		String businessReq="{\"node_attributes\":\"primary\",\"node_model\":\"business\"}";
		List businessNodeIdxs = new ArrayList<>();
		ResultEntity businessResult = getHearbeatBusinessService.getDeadNodes(businessReq);
		if(businessResult != null && businessResult.getState()){
			List<PrimaryNodeEntity> businessNodes = JsonUtils.convertMap(businessResult.getResult(), new TypeReference<List<PrimaryNodeEntity>>(){});
			for(PrimaryNodeEntity node : businessNodes){
				Integer flag = 1;
				String node_relations = node.getNode_relations();
				if(StringUtils.hasLength(node_relations)){
					String[] array = node_relations.split(",");
					for(String s : array){
						if(viewNodeIdxs.contains(s)){
							flag = 2;
							break;
						}
					}
				}
				if(flag ==2){
					continue;
				}
				
				String req="{\"type\":\"manual\",\"node_idx\":\""+node.getNode_idx()+"\"}";
				ResultEntity result = switchServerService.switcher(req);
				if(result != null && !result.getState()){
					businessNodeIdxs.add(node.getNode_idx());
				}
			}
		}
		
		//处理db节点
		String dbReq="{\"node_attributes\":\"primary\",\"node_model\":\"db\"}";
		ResultEntity dbResult = getHearbeatBusinessService.getDeadNodes(dbReq);
		if(dbResult != null && dbResult.getState()){
			List<PrimaryNodeEntity> dbNodes = JsonUtils.convertMap(dbResult.getResult(), new TypeReference<List<PrimaryNodeEntity>>(){});
			for(PrimaryNodeEntity node : dbNodes){
				Integer flag = 1;
				String node_relations = node.getNode_relations();
				if(StringUtils.hasLength(node_relations)){
					String[] array = node_relations.split(",");
					for(String s : array){
						if(businessNodeIdxs.contains(s)){
							flag = 2;
							break;
						}
					}
				}
				if(flag ==2){
					continue;
				}
				
				String req="{\"type\":\"manual\",\"node_idx\":\""+node.getNode_idx()+"\"}";
				switchServerService.switcher(req);
			}
		}
		
		
	}
}
