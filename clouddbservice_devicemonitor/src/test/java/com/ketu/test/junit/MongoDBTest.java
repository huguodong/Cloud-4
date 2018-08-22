package com.ketu.test.junit;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author m
 * 测试本地mongodb
 *
 */
@SuppressWarnings(value="all")
public class MongoDBTest{
	public static void main(String[] args){
		
		ServerAddress addr = new ServerAddress("127.0.0.1", 27017);
	    MongoCredential credential = MongoCredential.createScramSha1Credential("superuser", "admin", "pwd".toCharArray());
	    MongoClient client = new MongoClient(addr, Arrays.asList(credential));
	    MongoDatabase mongoDatabase= client.getDatabase("LIB001_SCH#111");
	    if(mongoDatabase!=null){
	    	//mongoDatabase.createCollection("test");
	    	System.out.println("连接创建成功！");
	    	MongoCollection<Document> collection = mongoDatabase.getCollection("bin_state");
	    	System.out.println(collection.count());
	    }
	}
}
