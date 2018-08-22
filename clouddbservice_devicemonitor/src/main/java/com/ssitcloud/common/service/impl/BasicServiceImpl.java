package com.ssitcloud.common.service.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.ssitcloud.common.service.BasicService;
import com.ssitcloud.common.system.MongoDB;
import com.ssitcloud.common.system.MongoInstance;
import com.ssitcloud.common.system.MongoInstanceManager;
import com.ssitcloud.common.system.MongoParam;

import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "basicServiceImpl")
public class BasicServiceImpl implements BasicService {

	private static final Logger LOG =LoggerFactory.getLogger(BasicServiceImpl.class);
	
	@Resource(name = "mongoDBImpl")
	protected MongoDB mongoDB;

	@Resource(name = "mongoInstanceManager")
	protected MongoInstanceManager instanceManager;
	
	private MongoClient client = MongoInstanceManager.mongoClient;

	@Resource(name="mongoParam")
	private MongoParam mongoParam;
	
	private  static final String DB_NAME="dbName";
	private  static final String DEV_LOGIN_MGMT="_DEV_LOGIN_MGMT_";
	private  static final String LOGIN_INFO="LOGIN_INFO";


	/**
	 * 单个库信息插入
	 */
	@Override
	public void insertOneJSON(String json, MongoInstance instance, String collectionName) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			mongoDB.insertJSON(json,client,instance.getOperDatabase(), collectionName);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
	}


	@Override
	public UpdateResult updateById(MongoInstance instance, 
			String collectionName, String id, Bson update) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.updateOne(client,instance.getOperDatabase(), collectionName,Filters.eq("_id", new ObjectId(id)), update);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}

	@Override
	public UpdateResult updateOneByQuery(MongoInstance instance,
			String collectionName, Bson filter, Bson update) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.updateOne(client,instance.getOperDatabase(),collectionName, filter,update);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}

	@Override
	public UpdateResult updateByQuery(MongoInstance instance,String collectionName, Bson filter, Bson update) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.updateMany(client,instance.getOperDatabase(), collectionName, filter,update);		
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}

	@Override
	public DeleteResult deleteById(MongoInstance instance, String collectionName, String id) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.deleteOne(client,instance.getOperDatabase(), collectionName,Filters.eq("_id", new ObjectId(id)));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}

	@Override
	public DeleteResult deleteOneByQuery(MongoInstance instance,String collectionName, Bson filter) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.deleteOne(client,instance.getOperDatabase(),  collectionName, filter);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}

	@Override
	public DeleteResult deleteByQuery(MongoInstance instance,String collectionName, Bson filter) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.deleteMany(client,instance.getOperDatabase(), collectionName, filter);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}

	@Override
	public Document findById(MongoInstance instance, String collectionName, String id) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.findOne(client,instance.getOperDatabase(),collectionName,Filters.eq("_id", new ObjectId(id)));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}

	@Override
	public Document findOneByQuery(MongoInstance instance, String collectionName, Bson filter) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.findOne(client,instance.getOperDatabase(), collectionName, filter);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}

	@Override
	public List<Document> findByQuery(MongoInstance instance, String collectionName, Bson filter) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.find(client,instance.getOperDatabase(),  collectionName, filter);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}

	@Override
	public List<Document> aggregate(MongoInstance instance,String collectionName, List<Bson> pipeline) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.aggregate(client,instance.getOperDatabase(),  collectionName,pipeline);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}

	@Override
	public AggregateIterable<Document> aggregate(List<Bson> pipeline,
			MongoInstance instance,String collectionName) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.aggregate(pipeline, client,instance.getOperDatabase(),collectionName);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}
	
	@Override
	public Document aggregateByOne(List<Bson> pipeline,
			MongoInstance instance,String collectionName) {
//		MongoClient client=null;
		try {
//			client=mongoDB.getDBClient(instance);
			return mongoDB.aggregate(pipeline, client,instance.getOperDatabase(),collectionName).first();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}finally{
//			CloseUtil.close(client);
		}
		return null;
	}


	/**
	 * 
	 * @methodName: getInstances
	 * @param params
	 * @return
	 * @author: liubh
	 * @description: 根据查询参数获取到 MongoInstance实例集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MongoInstance> getInstances(Map<String, Object> params) {
		if (params.containsKey("dbNames")) {
			List<String> dbNames = (List<String>) params.get("dbNames");
			List<MongoInstance> instances = instanceManager.getInstances(dbNames.toArray());
			if (CollectionUtils.isEmpty(instances)) {
				return null;
			}
			return instances;
		}
		return null;
	}

	//databaseName 一般就是device_id 和 library_id 的组合
	@Override
	public void checkIftheDBExist(String databaseName) {
		//将创建mongodb的数据库添加为大写	add by huanghuang
		databaseName = databaseName.toUpperCase();
		//LOG.debug("databaseName--->"+databaseName);
		try {
			mongoDB.copyDeviceTemplate(client,databaseName);
			saveLogInfo(databaseName);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		} finally {
			//CloseUtil.close(client); //adminClien should not be close
		}

	}
	   
   
	/**
	 * 保存信息到_DEV_LOGIN_MGMT_
	 * @param databaseName
	 */
	private void saveLogInfo(String dataBaseName) {
		//缓存mongodb实例化
		MongoInstanceManager.putInstance(dataBaseName,createInstance(dataBaseName));
		
		Document doc = createDocument(dataBaseName);
		doc.put(DB_NAME, dataBaseName);
		
		boolean isExists = mongoDB.theDataBaseNameIsExist(client,DEV_LOGIN_MGMT);
		MongoDatabase database = client.getDatabase(DEV_LOGIN_MGMT);
		if (!isExists) {
			// 不存在创建
			database.createCollection(LOGIN_INFO);
			database.getCollection(LOGIN_INFO).insertOne(doc);
			
			MongoInstanceManager.putInstance(DEV_LOGIN_MGMT,createInstance(DEV_LOGIN_MGMT));
		}else{
			//MongoDatabase database = client.getDatabase(DEV_LOGIN_MGMT);
			MongoCollection<Document> mcc = database.getCollection(LOGIN_INFO);
			ArrayList<Document> finds = mcc.find(new Document().append(DB_NAME, dataBaseName)).into(new ArrayList<Document>());
			if (CollectionUtils.isEmpty(finds)) {
				mongoDB.insertOne(client,DEV_LOGIN_MGMT, LOGIN_INFO, doc);
			}
		}
	}


	/**
	 * 缓存mongodb实例化
	 * @param dataBaseName
	 * @return
	 */
	private MongoInstance createInstance(String dataBaseName) {
		// 缓存实例
		MongoInstance mongoDBIns = new MongoInstance();
		mongoDBIns.setHost(mongoParam.getHost());
		mongoDBIns.setOperDatabase(dataBaseName);
		mongoDBIns.setPort(mongoParam.getPort());
		mongoDBIns.setUser(mongoParam.getUser());
		mongoDBIns.setPwd(mongoParam.getPwd());
		
		return mongoDBIns;
	}
	
	/**
	 * 生成document
	 * @param dataBaseName
	 * @return
	 */
	private Document createDocument(String dataBaseName) {
		return new Document(dataBaseName, new Document().append("host", mongoParam.getHost()).append("port", mongoParam.getPort()).append("operDatabase", dataBaseName)
				.append("user", mongoParam.getUser()).append("pwd", mongoParam.getPwd()));
	}

}
