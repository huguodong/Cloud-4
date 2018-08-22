package com.ssitcloud.business.database.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.business.database.service.MongoCollectionService;
import com.ssitcloud.business.database.util.ServerUtil;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.database.entity.DocumentEntity;
import com.ssitcloud.database.entity.MongoCollectionEntity;
import com.ssitcloud.database.entity.Server;


@Service
public class MongoCollectionServiceImpl extends BasicServiceImpl implements MongoCollectionService {

	
	private static final String URL_collectionData="collectionData";
	private static final String URL_collectionIndex="collectionIndex";
	
	private static final String URL_tableKey="tableKey";
	
	private static final String URL_updateField="updateField";
	private static final String URL_updateIndex="updateIndex";
	private static final String URL_updateconstraint="updateconstraint";
	
	private static final String URL_deleteField="deleteField";
	private static final String URL_deleteIndex="deleteIndex";
	private static final String URL_deleteConstraint="deleteConstraint";
	
	private static final String URL_addField="addField";
	private static final String URL_addIndex="addIndex";
	private static final String URL_addConstraint="addConstraint";
	
	@Resource(name = "dataBaseMenus")
    private ConcurrentMap<String, List<Server>> dataBaseMenus;

	@Override
	public PageEntity collectionData(String req) {
		MongoClient mongoClient=null;  
		List<DocumentEntity> list = new ArrayList<>();
		try{
			JSONObject jsonObject =JSONObject.fromObject(req);
			MongoCollectionEntity collection = JsonUtils.fromJson(jsonObject.getString("json"), MongoCollectionEntity.class);
			PageEntity pageEntity = JsonUtils.fromJson(jsonObject.getString("page"), PageEntity.class);
	        Server server = getMongoServer(collection.getServer_id());
			ServerAddress serverAddress = new ServerAddress(server.getHost(),server.getPort());    
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();    
            addrs.add(serverAddress);    
            MongoCredential credential = MongoCredential.createScramSha1Credential(server.getUser(), server.getDescription(), server.getPassword().toCharArray());    
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();    
            credentials.add(credential); 
            mongoClient = new MongoClient(addrs,credentials);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(collection.getDatabaseName());    
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection.getName()); 
            int total = new Long(mongoCollection.count()).intValue();
            pageEntity.setTotal(total);
            FindIterable<Document> findIterable = mongoCollection.find().skip(pageEntity.getBeginIndex()).limit(pageEntity.getPageSize());  
            MongoCursor<Document> mongoCursor = findIterable.iterator();    
            while(mongoCursor.hasNext()){    
            	Document document = mongoCursor.next();
            	DocumentEntity ety = new DocumentEntity();
            	//if(document.toJson().length()<200){
            		ety.setJsonStr(document.toJson());
            	//}else{
            	//	ety.setJsonStr(document.toJson().substring(0,200)+"...");
            	//}
            	
            	int flag = 1;
            	for(Entry<String, Object>  o : document.entrySet()){
            		if(flag == 1){
            			ety.setId(o.getValue().toString());
            			flag = 2;
            		}
            		ety.addField(o.getKey());
            	}
            	
            	list.add(ety);
            }   
            pageEntity.setRows(list);
            return pageEntity;
		}catch(Exception e){
			LogUtils.error("mongo数据库连接错误!!");
		}finally{
			mongoClient.close();
		}
		return null;
	}
	
	@Override
	public Boolean deleteDocument(String req) throws SQLException {
		MongoClient mongoClient=null;  
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MongoCollectionEntity collection = JsonUtils.fromJson(jsonObject.getString("json1"), MongoCollectionEntity.class);
	    	JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("json2"));
	    	List<String> ids = jsonArray.toList(jsonArray, String.class);
	    	Server server = getMongoServer(collection.getServer_id());
			ServerAddress serverAddress = new ServerAddress(server.getHost(),server.getPort());    
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();    
            addrs.add(serverAddress);    
            MongoCredential credential = MongoCredential.createScramSha1Credential(server.getUser(), server.getDescription(), server.getPassword().toCharArray());    
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();    
            credentials.add(credential); 
            mongoClient = new MongoClient(addrs,credentials);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(collection.getDatabaseName());    
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection.getName()); 
	        for(String id : ids){
	        	mongoCollection.deleteOne(new BasicDBObject("_id", new ObjectId(id)));
	        }
        	return true;
	    }catch(Exception e){
			LogUtils.error("mongo数据库连接错误!!");
		}finally{
			mongoClient.close();
		}
	    return false;
	}
	
	@Override
	public Boolean addDocument(String req) throws SQLException {
		MongoClient mongoClient=null;  
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MongoCollectionEntity collection = JsonUtils.fromJson(jsonObject.getString("json1"), MongoCollectionEntity.class);
	    	String content = jsonObject.getString("json2");
	    	Server server = getMongoServer(collection.getServer_id());
			ServerAddress serverAddress = new ServerAddress(server.getHost(),server.getPort());    
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();    
            addrs.add(serverAddress);    
            MongoCredential credential = MongoCredential.createScramSha1Credential(server.getUser(), server.getDescription(), server.getPassword().toCharArray());    
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();    
            credentials.add(credential); 
            mongoClient = new MongoClient(addrs,credentials);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(collection.getDatabaseName());    
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection.getName()); 
            
            Document doc = Document.parse(content);
            mongoCollection.insertOne(doc);
        	return true;
	    }catch(Exception e){
			LogUtils.error("mongo数据库连接错误!!");
		}finally{
			mongoClient.close();
		}
	    return false;
	}
	
	
	@Override
	public Boolean updateDocument(String req) throws SQLException {
		MongoClient mongoClient=null;  
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MongoCollectionEntity collection = JsonUtils.fromJson(jsonObject.getString("json1"), MongoCollectionEntity.class);
	    	String id = jsonObject.getString("json2");
	    	String content = jsonObject.getString("json3");
	    	Server server = getMongoServer(collection.getServer_id());
			ServerAddress serverAddress = new ServerAddress(server.getHost(),server.getPort());    
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();    
            addrs.add(serverAddress);    
            MongoCredential credential = MongoCredential.createScramSha1Credential(server.getUser(), server.getDescription(), server.getPassword().toCharArray());    
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();    
            credentials.add(credential); 
            mongoClient = new MongoClient(addrs,credentials);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(collection.getDatabaseName());    
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection.getName()); 
            
            BasicDBObject query = new BasicDBObject();  
            query.put("_id", new ObjectId(id));  
            
            BasicDBObject newDocument = (BasicDBObject) JSON.parse(content);
            newDocument.remove("_id");
  
            BasicDBObject updateObj = new BasicDBObject();  
            updateObj.put("$set", newDocument);  
  
            mongoCollection.updateOne(query, updateObj);  
        	return true;
	    }catch(Exception e){
			LogUtils.error("mongo数据库连接错误!!");
		}finally{
			mongoClient.close();
		}
	    return false;
	}
	
	@Override
	public Boolean addcollectionAction(String req) throws SQLException {
		MongoClient mongoClient=null;  
	    
	    try {
	    	MongoCollectionEntity collection = JsonUtils.fromJson(req, MongoCollectionEntity.class);
	    	Server server = getMongoServer(collection.getServer_id());
			ServerAddress serverAddress = new ServerAddress(server.getHost(),server.getPort());    
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();    
            addrs.add(serverAddress);    
            MongoCredential credential = MongoCredential.createScramSha1Credential(server.getUser(), server.getDescription(), server.getPassword().toCharArray());    
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();    
            credentials.add(credential); 
            mongoClient = new MongoClient(addrs,credentials);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(collection.getDatabaseName());    
            
            mongoDatabase.createCollection(collection.getName());
            
            //修改缓存
	        server = ServerUtil.mongoServer(server);
	        List<Server> list = new ArrayList<>();
	        List<Server> newlist = new ArrayList<>();
	        list = dataBaseMenus.get("Mongo");
	        for(Server ety : list){
	        	if(ety.getId() == server.getId()){
	        		newlist.add(server);
	        	}else{
	        		newlist.add(ety);
	        	}
	        }
			dataBaseMenus.remove("Mongo");
			dataBaseMenus.put("Mongo", newlist);
        	return true;
	    }catch(Exception e){
			LogUtils.error("mongo数据库连接错误!!");
		}finally{
			mongoClient.close();
		}
	    return false;
	}
	
	@Override
	public Server getMongoServer(Integer server_id) {
		List<Server> list = dataBaseMenus.get("Mongo");
		for(Server server : list){
			if(server_id == server.getId()){
				return server;
			}
		}
		return null;
	}

	@Override
	public MongoCollectionEntity collectionIndex(String req) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
