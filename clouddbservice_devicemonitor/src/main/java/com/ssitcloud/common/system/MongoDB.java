package com.ssitcloud.common.system;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public interface MongoDB {

	/**
	 * 
	 * @methodName: getCollection 取得集合对象
	 * @param instance （IP/PORT/USER/PASSWORD等信息的封装对象）
	 * @param collectionName 集合名
	 * @return 返回一个集合对象
	 * @returnType: MongoCollection<Document>
	 * @author: liuBh
	 */
	MongoCollection<Document> getCollection(MongoClient client,String databaseName,String collectionName);
	/**
	 * 
	 * @methodName: getDBClient 获取MongoClient实例对象
	 * @param host 地址
	 * @param port	端口
	 * @param userName 用户名
	 * @param databaseName 库名，一般为admin库
	 * @param password 密码
	 * @return 返回对应的MongoClient实例对象
	 * @returnType: MongoClient
	 * @author: liuBh
	 */
	MongoClient getDBClient(String host, int port, String userName,
			 String password,String operDatabase);
	/**
	 * 
	 * @methodName: getDBClient 获取MongoClient实例对象
	 * @param instance （IP/PORT/USER/PASSWORD等信息的封装对象）
	 * @return
	 * @returnType: MongoClient
	 * @author: liuBh
	 */
	MongoClient getDBClient(MongoInstance instance);
	/**
	 * 
	 * @methodName: theDataBaseNameIsExist 判断库名是否存在
	 * @param client MongoClient实例对象
	 * @param newDbName 要创建的库的名字
	 * @return 返回存在或者不存在
	 * @returnType: boolean
	 * @author: liuBh
	 */
	boolean theDataBaseNameIsExist(MongoClient client, String newDbName);
	/**
	 * 
	 * @methodName: insertOne 在数据库中插入一个文档对象
	 * @param instance （IP/PORT/USER/PASSWORD等信息的封装对象）
	 * @param collectionName 集合名
	 * @param document mongoDB文档对象
	 * @returnType: void
	 * @author: liuBh
	 */
	void insertOne(MongoClient client,String databaseName,String collectionName, Document document);
	
	/**
	 * 
	 * @methodName: insertMany 插入多个文档对象
	 * @param instance （IP/PORT/USER/PASSWORD等信息的封装对象）
	 * @param collectionName 集合名
	 * @param documents mongoDB文档对象集
	 * @returnType: void
	 * @author: liuBh
	 */
	void insertMany(MongoClient client,String databaseName,String collectionName, List<Document> documents);
	/**
	 * 
	 * @methodName: updateOne 更新一个文档对象
	 * @param instance （IP/PORT/USER/PASSWORD等信息的封装对象）
	 * @param collectionName 集合名
	 * @param filter 条件
	 * @param update 要更新的数据
	 * @return 返回更新结果
	 * @returnType: UpdateResult
	 * @author: liuBh
	 */
	UpdateResult updateOne(MongoClient client,String databaseName,String collectionName, Bson filter, Bson update);

	/**
	 * 
	 * @methodName: updateMany 更新多个文档对象
	 * @param instance （IP/PORT/USER/PASSWORD等信息的封装对象）
	 * @param collectionName 集合名
	 * @param filter 条件
	 * @param update 要更新的数据
	 * @return 返回更新结果
	 * @returnType: UpdateResult
	 * @author: liuBh
	 */
	UpdateResult updateMany(MongoClient client,String databaseName,String collectionName, Bson filter, Bson update);

	/**
	 * 
	 * @methodName: deleteOne 删除一个文档对象
	 * @param instance （IP/PORT/USER/PASSWORD等信息的封装对象）
	 * @param collectionName 集合名
	 * @param filter 条件
	 * @return 返回删除结果
	 * @returnType: DeleteResult
	 * @author: liuBh
	 */
	DeleteResult deleteOne(MongoClient client,String databaseName,String collectionName, Bson filter);
	/**
	 * 
	 * @methodName: deleteMany 删除多个文档对象
	 * @param instance （IP/PORT/USER/PASSWORD等信息的封装对象）
	 * @param collectionName 集合名
	 * @param filter 条件
	 * @return 返回删除结果
	 * @returnType: DeleteResult
	 * @author: liuBh
	 */
	DeleteResult deleteMany(MongoClient client,String databaseName,String collectionName, Bson filter);
	/**
	 * 
	 * @methodName: findOne 查询一个符合条件的文档对象
	 * @param instance （IP/PORT/USER/PASSWORD等信息的封装对象）
	 * @param collectionName 集合名
	 * @param filter 条件
	 * @return 返回一个文档对象
	 * @returnType: Document
	 * @author: liuBh
	 */
	Document findOne(MongoClient client,String databaseName,String collectionName, Bson filter);
	/**
	 * 
	 * @methodName: find 查询多个符合条件的文档对象
	 * @param instance （IP/PORT/USER/PASSWORD等信息的封装对象）
	 * @param collectionName 集合名
	 * @param filter 条件
	 * @return 返回一个文档集合
	 * @returnType: List<Document>
	 * @author: liuBh
	 */
	List<Document> find(MongoClient client,String databaseName,String collectionName, Bson filter);
	/**
	 * 
	 * @methodName: copyDeviceTemplate 创建一个新的库，包括指定的集合和索引
	 * @param instance IP/PORT/USER/PASSWORD等信息的封装对象
	 * @param newDbName 新的库名
	 * @return 返回创建成功或者失败
	 * @returnType: boolean
	 * @author: liuBh
	 */
	boolean copyDeviceTemplate(MongoClient client, String newDbName);
	/**
	 * 
	 * @methodName: insertJSON 插入一个json格式的字符串
	 * @param json json格式的字符串
	 * @param instance IP/PORT/USER/PASSWORD等信息的封装对象
	 * @param collectionName 集合名
	 * @returnType: void
	 * @author: liuBh
	 */
	void insertJSON(String json, MongoClient client,String databaseName,String collectionName);
	/**
	 * 
	 * @methodName: aggregate 聚合查询操作
	 * @param instance IP/PORT/USER/PASSWORD等信息的封装对象
	 * @param collectionName 集合名
	 * @param pipeline LIST管道
	 * @return 返回符合条件的文档集合
	 * @returnType: List<Document>
	 * @author: liuBh
	 */
	List<Document> aggregate(MongoClient client,String databaseName,String collectionName, List<Bson> pipeline);
	/**
	 * 
	 * @methodName: aggregate 聚合查询操作
	 * @param pipeline LIST管道
	 * @param instance IP/PORT/USER/PASSWORD等信息的封装对象
	 * @param collectionName 集合名
	 * @return 返回迭代器
	 * @returnType: AggregateIterable<Document>
	 * @author: liuBh
	 */
	AggregateIterable<Document> aggregate(List<Bson> pipeline,MongoClient client,String databaseName, String collectionName);

	/**
	 * 
	 * @methodName: getDatabase 获取指定的库实例
	 * @param instance IP/PORT/USER/PASSWORD等信息的封装对象
	 * @return 返回库实例 或者 null
	 * @returnType: MongoDatabase
	 * @author: liuBh
	 */
	MongoDatabase getDatabase(MongoClient client,String databaseName);
	
	Document findOneAndSort(MongoClient client,String databaseName,String collectionName, Bson filter,Bson sort);
	/**
	 * 
	 * @param client
	 * @param databaseName
	 * @param collectionName
	 * @param filter
	 * @param projection
	 * @param limit
	 * @param skip
	 * @return
	 */
	List<Document> find(MongoClient client, String databaseName,String collectionName, Bson filter, Bson projection, int limit,int skip);
	
	/**
	 * 排序之后获取分页数据
	 *
	 * <p>2017年11月29日 上午10:43:45 
	 * <p>create by hjc
	 * @param client
	 * @param databaseName
	 * @param collectionName
	 * @param filter
	 * @param projection
	 * @param sort
	 * @param limit
	 * @param skip
	 * @return
	 */
	List<Document> find(MongoClient client, String databaseName, String collectionName, Bson filter, Bson projection, Bson sort, int limit, int skip);

}
