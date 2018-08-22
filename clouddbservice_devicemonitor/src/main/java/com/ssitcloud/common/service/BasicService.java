package com.ssitcloud.common.service;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.ssitcloud.common.system.MongoInstance;

/**
 * 
 * 约定：对于update操作， 如果WRITE_CONCERN等级在SAFE以下(不包含SAFE)， 更新失败或者SAFE以下 返回-1
 */

public interface BasicService {

	void insertOneJSON(String json, MongoInstance instance,String collectionName);

	UpdateResult updateById(MongoInstance instance,String collectionName, String id, Bson update);

	DeleteResult deleteById(MongoInstance instance,String collectionName, String id);

	Document findById(MongoInstance instance,String collectionName, String id);

	UpdateResult updateOneByQuery(MongoInstance instance,String collectionName, Bson filter, Bson update);

	UpdateResult updateByQuery(MongoInstance instance,String collectionName, Bson filter, Bson update);

	DeleteResult deleteOneByQuery(MongoInstance instance,String collectionName, Bson filter);

	DeleteResult deleteByQuery(MongoInstance instance,String collectionName, Bson filter);

	Document findOneByQuery(MongoInstance instance,String collectionName, Bson filter);

	List<Document> findByQuery(MongoInstance instance,String collectionName, Bson filter);

	List<Document> aggregate(MongoInstance instance,String collectionName, List<Bson> pipeline);

	AggregateIterable<Document> aggregate(List<Bson> pipeline,MongoInstance instance, String collectionName);

	List<MongoInstance> getInstances(Map<String, Object> params);

	void checkIftheDBExist(String databaseName);
	/**
	 * author huanghuang
	 * 2017年2月24日 下午1:54:22
	 * @param pipeline 管道
	 * @param instance mongodb实例
	 * @param collectionName 集合名称
	 * @return
	 */
	Document aggregateByOne(List<Bson> pipeline, MongoInstance instance,
			String collectionName);

}
