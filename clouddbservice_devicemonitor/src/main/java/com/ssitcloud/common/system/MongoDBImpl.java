package com.ssitcloud.common.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.ssitcloud.operlog.bookenum.EnumClass;

/**
 * 
 * @package: com.ketu.selfservice.common.system
 * @classFile: MongoDB
 * @author: liubh
 * @createTime:
 * @description: mongodb初始化封装
 */
@Component(value = "mongoDBImpl")
public class MongoDBImpl implements MongoDB {
	private static final Logger LOG = LoggerFactory
			.getLogger(MongoDBImpl.class);

	@Resource(name = "mongoInstanceManager")
	private MongoInstanceManager mongoInstanceManager;

	@Resource(name = "xmlconfig")
	private Configuration xmlconfig;

	@Override
	public MongoDatabase getDatabase(MongoClient client,String databaseName) {
		if (client != null ) {
			return client.getDatabase(databaseName);
		}
		return null;
	}

	@Override
	public MongoCollection<Document> getCollection(MongoClient client,String databaseName,String collectionName) {
		if(client!=null){
			MongoDatabase database=client.getDatabase(databaseName);
			if(database!=null){
				return database.getCollection(collectionName);
			}
		}
		return null;
	}

	@Override
	public MongoClient getDBClient(String host, int port, String userName,
			 String password,String operDatabase) {
		MongoInstance mongoInstance=new MongoInstance();
		mongoInstance.setHost(host);
		mongoInstance.setPort(port);
		mongoInstance.setPwd(password);
		mongoInstance.setUser(userName);
		mongoInstance.setOperDatabase(operDatabase);
		return mongoInstanceManager.initClient(mongoInstance);
	}

	@Override
	public MongoClient getDBClient(MongoInstance instance) {
		return mongoInstanceManager.initClient(instance);
	}

	@Override
	public boolean theDataBaseNameIsExist(MongoClient client, String newDbName) {
		Assert.notNull(newDbName);
		boolean DBNameIsExist = false;// 假设不存在库
		if(MongoInstanceManager.instancesCache.containsKey(newDbName.toUpperCase())){
			return true;
		}
//		MongoIterable<String> dbNamesIt = client.listDatabaseNames();
//		MongoCursor<String> mcursor = dbNamesIt.iterator();
//		while (mcursor.hasNext()) {
//			if (newDbName.equals(mcursor.next())) {
//				DBNameIsExist = true;
//				break;
//			}
//		}
//		mcursor.close();
		return DBNameIsExist;
	}

	@Override
	public void insertOne(MongoClient client,String databaseName,String collectionName, Document document) {
		MongoCollection<Document> col = getCollection(client,databaseName,collectionName);
		if (col != null) {
			col.insertOne(document);
		}
	}

	@Override
	public void insertJSON(String json, MongoClient client,String databaseName,String collectionName) {
		insertOne(client,databaseName,collectionName, Document.parse(json));
	}

	@Override
	public void insertMany(MongoClient client,String databaseName,String collectionName, List<Document> documents) {
		MongoCollection<Document> col = getCollection(client,databaseName,collectionName);
		if (col != null) {
			col.insertMany(documents);
		}
	}
	@Override
	public UpdateResult updateOne(MongoClient client,String databaseName,String collectionName, Bson filter, Bson update) {
		MongoCollection<Document> col = getCollection( client,databaseName,collectionName);
		if (col != null) {
			return col.updateOne(filter, update);
		}
		return null;

	}


	@Override
	public UpdateResult updateMany(MongoClient client,String databaseName,String collectionName, Bson filter, Bson update) {
		MongoCollection<Document> col = getCollection(client,databaseName,collectionName);
		if (col != null) {
			return col.updateMany(filter, update);
		}
		return null;
	}


	@Override
	public DeleteResult deleteOne(MongoClient client,String databaseName,String collectionName, Bson filter) {
		MongoCollection<Document> col = getCollection(client,databaseName,collectionName);
		if (col != null) {
			return col.deleteOne(filter);
		}
		return null;
	}
	@Override
	public DeleteResult deleteMany(MongoClient client,String databaseName,String collectionName, Bson filter) {
		MongoCollection<Document> col = getCollection(client,databaseName,collectionName);
		if (col != null) {
			return col.deleteMany(filter);
		}
		return null;
	}

	@Override
	public Document findOne(MongoClient client,String databaseName,String collectionName, Bson filter) {
		
		MongoCollection<Document> col = getCollection(client,databaseName,collectionName);
		return col.find(filter).first();
	}
	@Override
	public Document findOneAndSort(MongoClient client,String databaseName,String collectionName, Bson filter,Bson sort) {
		MongoCollection<Document> col = getCollection(client,databaseName,collectionName);
		return col.find(filter).sort(sort).first();
	}
	
	@Override
	public List<Document> find(MongoClient client,String databaseName,String collectionName, Bson filter) {
		MongoCollection<Document> col = getCollection(client,databaseName,collectionName);
		return col.find(filter).into(new ArrayList<Document>());
	}
	@Override
	public List<Document> find(MongoClient client,String databaseName,String collectionName, Bson filter,Bson projection,int limit,int skip){
		MongoCollection<Document> col = getCollection(client,databaseName,collectionName);
		return col.find(filter).projection(projection).skip(skip).limit(limit).into(new ArrayList<Document>());
	} 
	
	
	
	@Override
	public List<Document> find(MongoClient client, String databaseName,
			String collectionName, Bson filter, Bson projection, Bson sort,
			int limit, int skip) {
		MongoCollection<Document> col = getCollection(client, databaseName, collectionName);
		return col.find(filter).projection(projection).sort(sort).skip(skip).limit(limit).into(new ArrayList<Document>());
	}

	@Override
	public boolean copyDeviceTemplate(MongoClient client,String newDbName) {
		boolean copySuccess = false;
		boolean dBNameIsExist = this.theDataBaseNameIsExist(client, newDbName);
		if (dBNameIsExist) {
			LOG.warn("数据库名已存在，不创建数据库");
			return false;// 数据库名已存在，不创建数据库
		} else {
			MongoDatabase database = client.getDatabase(newDbName);
			if (database != null) {
				LOG.warn("开始创建集合和索引");
				for (int i = 0; i < EnumClass.COLLECTION.values().length; i++) {
					String collectionName = EnumClass.COLLECTION.values()[i].name();
					//database.createCollection(collectionName);
					MongoCollection<Document> col = database.getCollection(collectionName);
					if (col != null) {
						MappedStatement ms = xmlconfig.getMappedStatement("dbIndex." + collectionName);
						if (ms != null) {
							BoundSql bsql = ms.getBoundSql(null);
							database.runCommand(Document.parse(bsql.getSql()));
						} else {
							throw new RuntimeException(
									"请检查XML/dbIndex.xml中是否存在：dbIndex."
											+ collectionName);
						}
					}
				}
				copySuccess = true;
			}
		}
		return copySuccess;
	}

	@Override
	public List<Document> aggregate(MongoClient client,String databaseName, String collectionName, List<Bson> pipeline) {
		MongoCollection<Document> col = getCollection( client, databaseName,collectionName);
		if (col != null) {
			return col.aggregate(pipeline).into(new ArrayList<Document>());
		}
		return null;
	}
	@Override
	public AggregateIterable<Document> aggregate(List<Bson> pipeline,
			MongoClient client,String databaseName, String collectionName) {
		MongoCollection<Document> col = getCollection( client, databaseName,collectionName);
		if (col != null) {
			return col.aggregate(pipeline);
		}
		return null;
	}

}
