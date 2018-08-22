package com.ssitcloud.operlog.dao;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.ssitcloud.common.system.MongoDB;

public interface StatisticsLogDao extends MongoDB {
	/**
	 * 2017年3月3号
	 * 数据统计方法 lqw
	 * @return
	 */
	public List<Document> dataStatisticsGroup(Bson timeAggBson,MongoClient client,String dbName,String tableName,String typeKey,String statisticsKey,String date);
	
	/**
	 * 2017年3月3号
	 * 根据子类数据统计方法 lqw
	 * @return
	 */
	public String dataStatistics(Bson timeAggBson,MongoClient client,String dbName,String tableName,String date,String SubclassName,String SubclassValue);
	
	/**
	 * 2017年3月9号
	 * 根据子类数据统计方法 lqw
	 * @return
	 */
	public String dataStatistics(Bson timeAggBson,MongoClient client,String dbName,String tableName,String date,String SubclassName,String SubclassValue,String opercmd);
	
	/**
	 * 2017年3月7号
	 * 检查mongodb是否存在某个字段  lqw
	 * @return
	 */
	public List<Document> sel(MongoClient client,String dbName,String tableName,String fieldName);
	
	/**
	 * 2017年3月8号
	 * 统计总数 lqw
	 * @return
	 */
	public int total(MongoClient client,String dbName,String tableName,String date);
	/**
	 * 2017年3月8号
	 * 按借、还、续统计总数 lqw
	 * @return
	 */
	public int total(MongoClient client,String dbName,String tableName,String date,String opercmd);
	/**
	 * 2017年3月8号
	 * 按年龄段统计 lqw
	 * @return
	 */
	public String ageGroup(Bson timeAggBson,MongoClient client,String dbName,String tableName,String date,String startAge,String endAge);

}
