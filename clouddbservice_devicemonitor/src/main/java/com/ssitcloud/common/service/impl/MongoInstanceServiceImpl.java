package com.ssitcloud.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.client.result.UpdateResult;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.MongoInstanceService;
import com.ssitcloud.common.system.MongoDB;
import com.ssitcloud.common.system.MongoInstanceManager;
import com.ssitcloud.common.util.JsonUtils;

@Service
public class MongoInstanceServiceImpl implements MongoInstanceService{

	@Resource(name="mongoDBImpl")
	private MongoDB mongoDB;
	
	private MongoClient adminClient = MongoInstanceManager.mongoClient;
	
	@Override
	public ResultEntityF<Object> queryMongoInstances(String req) {
		ResultEntityF<Object> r=new ResultEntityF<>();
		Document filter=new Document();
		Document projection=new Document();
		projection.put("_id", 0);
		List<Document> f_docs=mongoDB.find(adminClient, "_DEV_LOGIN_MGMT_", "LOGIN_INFO", filter,projection, 1000, 0);
		r.setState(true);
		r.setResult(f_docs);
		return r;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<Object> updMongoInstances(String req) {
		ResultEntityF<Object> r=new ResultEntityF<>();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,String> m=JsonUtils.fromJson(req, Map.class);
			if(m!=null){
				String db_name=m.get("db_name");//一般情况下是libId _ devId
				String host= m.get("host");
				String port_s=m.get("port");
				Integer port = null;
				if(port_s!=null){
					port=Integer.parseInt(port_s);
				}
				if(db_name!=null){
					Document filter=new Document();
					Document f_doc=mongoDB.findOne(adminClient, "_DEV_LOGIN_MGMT_", "LOGIN_INFO", filter);
					if(f_doc!=null){
						Document f_info=(Document)f_doc.get(db_name);
						f_info.put("host", host);
						f_info.put("port", port);
						f_doc.put(db_name, f_info);
						filter.append("_id", f_doc.remove("_id"));
						UpdateResult ur=mongoDB.updateOne(adminClient, "_DEV_LOGIN_MGMT_", "LOGIN_INFO", filter, f_doc);
						r.setState(true);
						r.setResult(ur);
					}
				}
			}
		}
		return r;
	}
}
