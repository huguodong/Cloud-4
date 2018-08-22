 package com.ssitcloud.business.nodemgmt.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.nodemgmt.dao.HearbeatDao;
import com.ssitcloud.business.nodemgmt.service.HearbeatBusinessService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.node.entity.FlowInfo;
import com.ssitcloud.node.entity.NodeEntity;
import com.ssitcloud.node.entity.PrimaryNodeEntity;


@Service
public class HearbeatBusinessServiceImpl extends BasicServiceImpl implements HearbeatBusinessService {
	//private String url_prefix = "hearbeat_";
	
	@Resource
	private HearbeatDao getHearbeatDao;
	
	@Override
	public String saveData(String req) {
		JSONObject obj = JSONObject.fromObject(req);
		JSONArray processInfo = obj.optJSONArray("processInfo");
		// JSONArray waitInfo = obj.optJSONArray("waitInfo");
		Iterator<Object> it = processInfo.iterator();
		FlowInfo info = new FlowInfo();
		int count = 0;
		String node_name = obj.optString("node_name");
		info.setNode_id(node_name);
		info.setQueue_length(obj.optInt("queue_length"));
		info.setWait_info("");
		while (it.hasNext()) {
			String ob = String.valueOf(it.next());
			info.setProcess_info(ob);
			count += getHearbeatDao.saveData(info);
		}
		if (count > 0)
			return "success";
		return "error";
	}
	

	@Override
	public ResultEntity getDeadNodes(String req) {
		ResultEntity result = new ResultEntity();
		NodeEntity node = JsonUtils.fromJson(req, NodeEntity.class);
		List<PrimaryNodeEntity> list = getHearbeatDao.getDeadNodes(node);
		if(list != null && list.size() > 0){
			result.setValue(true, "success", "", list);
		}else{
			result.setState(false);
		}
		return result;
	}

}
