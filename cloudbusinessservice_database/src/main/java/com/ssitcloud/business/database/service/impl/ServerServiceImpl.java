package com.ssitcloud.business.database.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.database.service.ServerService;
import com.ssitcloud.business.database.util.ServerUtil;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.database.entity.Server;

@Service
public class ServerServiceImpl extends BasicServiceImpl implements
		ServerService {

	@Resource(name = "dataBaseMenus")
    private ConcurrentMap<String, List<Server>> dataBaseMenus;
	
	@Override
	public ResultEntity getUserMenus() {
		ResultEntity resultEntity = new ResultEntity();
		
		List<Server> list = new ArrayList<>();
		/*List<Server> newlist = new ArrayList<>();
		Server addServer = ServerUtil.getAddServer();
		Server remoceServer = ServerUtil.getRemoceServer();
		if(addServer!=null){
			String type = addServer.getType();
			if("Mysql".equals(type)){
				addServer = ServerUtil.mysqlServer(addServer);
			}else if("Mongo".equals(type)){
				addServer = ServerUtil.mongoServer(addServer);
			}
			list = dataBaseMenus.get(type);
			list.add(addServer);
			dataBaseMenus.remove(type);
			dataBaseMenus.put(type, list);
		}else if(remoceServer != null){
			String type = remoceServer.getType();
			list = dataBaseMenus.get(type);
			for(Server server : list){
				if(server.getId() != remoceServer.getId()){
					newlist.add(server);
				}
			}
			dataBaseMenus.remove(type);
			dataBaseMenus.put(type, newlist);
		}*/
		ServerUtil.recoverData();
		
		//重新加载所有数据
		//dataBaseMenus.clear();
		dataBaseMenus = ServerUtil.dataBaseMenus();
		
		List<Server> mysqlList = dataBaseMenus.get("Mysql");
		List<Server> mongoList = dataBaseMenus.get("Mongo");
		list = new ArrayList<>();
		list.addAll(mysqlList);
		list.addAll(mongoList);
		
		resultEntity.setValue(true, "", "", list);
		return resultEntity;
	}
	
	@Override
	public ResultEntity getServerConfig() {
		ResultEntity resultEntity = new ResultEntity();
		List<Server> list = ServerUtil.getServers();
		resultEntity.setValue(true, "", "", list);
		return resultEntity;
	}

	@Override
	public ResultEntity addDBConnect(String req) {
		ResultEntity resultEntity = new ResultEntity();
		Server server = JsonUtils.fromJson(req, Server.class);
		if(ServerUtil.addDBConnect(server)){
			resultEntity.setValue(true, "");
		}
		return resultEntity;
	}

	@Override
	public ResultEntity removeDBConnect(String req) {
		ResultEntity resultEntity = new ResultEntity();
		Server server = JsonUtils.fromJson(req, Server.class);
		if(ServerUtil.removeDBConnect(server)){
			resultEntity.setValue(true, "");
		}
		return resultEntity;
	}
}
