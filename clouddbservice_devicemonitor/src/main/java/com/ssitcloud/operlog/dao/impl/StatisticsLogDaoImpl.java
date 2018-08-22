package com.ssitcloud.operlog.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.ssitcloud.common.system.MongoDBImpl;
import com.ssitcloud.operlog.dao.StatisticsLogDao;

@Repository
public class StatisticsLogDaoImpl extends MongoDBImpl implements StatisticsLogDao {

	@Override
	public List<Document> dataStatisticsGroup(Bson timeAggBson,MongoClient client,String dbName,String tableName,
			String typeKey,String statisticsKey,String date) {
		List<Bson> pipeline = new ArrayList<Bson>();
		if (timeAggBson != null) {
			pipeline.add(timeAggBson);
		}
		pipeline.add(Aggregates.match(Filters.and(Filters.regex("opertime", "^"
				+ date))));
		pipeline.add(Aggregates.group(new BasicDBObject(typeKey, "$"
				+ statisticsKey), Accumulators.sum("count", 1)));
		List<Document> doc = aggregate(client, dbName,tableName, pipeline);
		return doc;
	}

	@Override
	public String dataStatistics(Bson timeAggBson,MongoClient client,String dbName,String tableName,String date,String SubclassName,String SubclassValue) {
		Integer count=0;
		List<Bson> pipeline = new ArrayList<Bson>();
		if (timeAggBson != null) {
			pipeline.add(timeAggBson);
		}
		pipeline.add(Aggregates.match(Filters.and(Filters.regex("opertime", "^"
				+ date))));
		pipeline.add(Aggregates.match(Filters.and(Filters.eq(SubclassName, SubclassValue))));
//		pipeline.add(Aggregates.match(Filters.and(Filters.lt("Birth", endAge))));
//		pipeline.add(Aggregates.match(and(gte("Age", startAge),lte("Age",endAge))));
		pipeline.add(Aggregates.group("$sum",
				Accumulators.sum("count", 1)));
		List<Document> doc = aggregate(client, dbName,tableName, pipeline);
		if(doc !=null && !doc.isEmpty()){
			count = (Integer) doc.get(0).get("count");
		}
		return count+"";
	}
	
	@Override
	public List<Document> sel(MongoClient client,String dbName,String tableName,String fieldName){
		List<Bson> pipeline = new ArrayList<Bson>();
		pipeline.add(Aggregates.match(Filters.and(Filters.exists(fieldName))));
		List<Document> doc = aggregate(client, dbName,tableName, pipeline);
		return doc;
	}

	@Override
	public int total(MongoClient client, String dbName, String tableName,String date) {
		int count=0;
		List<Bson> pipeline = new ArrayList<Bson>();
		pipeline.add(Aggregates.match(Filters.and(Filters.regex("opertime", "^"
				+ date))));
		pipeline.add(Aggregates.group("$sum",
				Accumulators.sum("count", 1)));
		List<Document> doc = aggregate(client, dbName,tableName, pipeline);
		if(doc !=null && !doc.isEmpty()){
			count = (int) doc.get(0).get("count");
		}
		return count;
	}
	
	@Override
	public String ageGroup(Bson timeAggBson,MongoClient client,String dbName,String tableName,String date,String startAge,String endAge) {
		Integer count=0;
		List<Bson> pipeline = new ArrayList<Bson>();
		if (timeAggBson != null) {
			pipeline.add(timeAggBson);
		}
		pipeline.add(Aggregates.match(Filters.and(Filters.regex("opertime", "^"
				+ date))));
		pipeline.add(Aggregates.match(Filters.and(Filters.gte("Birth", startAge))));
		pipeline.add(Aggregates.match(Filters.and(Filters.lt("Birth", endAge))));
//		pipeline.add(Aggregates.match(and(gte("Age", startAge),lte("Age",endAge))));
		pipeline.add(Aggregates.group("$sum",
				Accumulators.sum("count", 1)));
		List<Document> doc = aggregate(client, dbName,tableName, pipeline);
		if(doc !=null && !doc.isEmpty()){
			count = (Integer) doc.get(0).get("count");
		}
		return count+"";
	}

	@Override
	public String dataStatistics(Bson timeAggBson, MongoClient client,
			String dbName, String tableName, String date, String SubclassName,
			String SubclassValue, String opercmd) {
		Integer count=0;
		List<Bson> pipeline = new ArrayList<Bson>();
		if (timeAggBson != null) {
			pipeline.add(timeAggBson);
		}
		pipeline.add(Aggregates.match(Filters.and(Filters.regex("opertime", "^"
				+ date))));
		pipeline.add(Aggregates.match(Filters.and(Filters.eq(SubclassName, SubclassValue))));
		pipeline.add(Aggregates.match(Filters.and(Filters.eq("opercmd", opercmd))));
		pipeline.add(Aggregates.group("$sum",
				Accumulators.sum("count", 1)));
		List<Document> doc = aggregate(client, dbName,tableName, pipeline);
		if(doc !=null && !doc.isEmpty()){
			count = (Integer) doc.get(0).get("count");
		}
		return count+"";
	}

	@Override
	public int total(MongoClient client, String dbName, String tableName,
			String date, String opercmd) {
		int count=0;
		List<Bson> pipeline = new ArrayList<Bson>();
		pipeline.add(Aggregates.match(Filters.and(Filters.regex("opertime", "^"
				+ date))));
		pipeline.add(Aggregates.match(Filters.and(Filters.eq("opercmd", opercmd))));
		pipeline.add(Aggregates.group("$sum",
				Accumulators.sum("count", 1)));
		List<Document> doc = aggregate(client, dbName,tableName, pipeline);
		if(doc !=null && !doc.isEmpty()){
			count = (int) doc.get(0).get("count");
		}
		return count;
	}
}
