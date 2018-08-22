package com.ssitcloud.common.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.result.UpdateResult;

/**
 *
 * @author liubh
 */
@Component(value = "mongoInstanceManager")
public class MongoInstanceManager {
    @Resource(name="mongoDBImpl")
    private MongoDB mongoDB;

    @Value("${mongoDB.autoChangeHost}")
    private String autoChangeHost;

    @Resource(name = "mongoParam")
    private MongoParam mongoParam;
    
	@Resource(name="mongoClientOpt")
	private MongoClientOptions mongoClientOptions;
	
	private static Lock lock = new ReentrantReadWriteLock().writeLock();

	private static Condition condition = lock.newCondition();

    public  static ConcurrentMap<String, MongoInstance> instancesCache = new ConcurrentHashMap<>();
    
    public static CopyOnWriteArrayList<ServerAddress> seeds = new CopyOnWriteArrayList<ServerAddress>();
    
    
    public static MongoClient mongoClient = null;
    
    @Value("${mongdb.mongdbCluster}")
	private String mongdbCluster;
	/**
	 * 
	 * @methodName: getInstances
	 * @param instanceNames
	 * @return
	 * @returnType: List<MongoInstance>
	 * @author : liubh
	 * @createTime : 2016年8月15日
	 */
	public List<MongoInstance> getInstances(Object... instanceNames) {
		List<MongoInstance> instancesList = new ArrayList<>();
		for (Object instanceName : instanceNames) {
			if (instancesCache.containsKey(instanceName)) {
				if(null != instanceName){
					instancesList.add(getInstance(instanceName.toString()));
				}
			}
		}
		return instancesList;
	}
	/**
	 * 
	 * @methodName: getInstance
	 * @param instanceName
	 * @return
	 * @returnType: MongoInstance
	 * @author: liubh
	 * @createTime: 2016年8月15日 
	 * @description: 根据名字 获取 MongoInstance
	 */
	public MongoInstance getInstance(String instanceName) {
		
        return getInstances(instanceName.toUpperCase());
	}
	
	/**
	 * 添加mongodb实例
	 * @param instance
	 * @param instanceName
	 */
	public static void putInstance(String instanceName,MongoInstance instance){
		
		try {
			
			lock.lock();
			
			if(!instancesCache.containsKey(instanceName)){
				
				instancesCache.put(instanceName, instance);
				//condition.signal();
				
			}
			
		}catch(Exception e ){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	/**
	 * 实例化客户端
	 * @param user
	 * @param pwd
	 * @param dbName admin 用来验证身份用的
	 * @return
	 */
	private MongoClient initClient(String user,
			String pwd, String dbName) {
		List<ServerAddress> seeds = setServerAddress() ;
		MongoCredential credential = MongoCredential.createScramSha1Credential(user, dbName, pwd.toCharArray());
		MongoClient client = new MongoClient(seeds, Arrays.asList(credential),mongoClientOptions);
		client.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		return client;
	}

	/**
	 * 实例化客户端
	 * @return
	 */
	public MongoClient initClient() {
		
		MongoCredential credential = MongoCredential.createScramSha1Credential(mongoParam.getUser(), mongoParam.getOpacDb(), mongoParam.getPwd().toCharArray());
		MongoClient client = new MongoClient(MongoInstanceManager.seeds, Arrays.asList(credential),mongoClientOptions);
		client.setWriteConcern(WriteConcern.JOURNALED);
		
		return client;
	}

	/**
	 * 主要使用这个方法
	 * @methodName: initClient
	 * @param mongoInstance
	 * @return
	 * @returnType: MongoClient
	 * @author: liubh
	 * @createTime:
	 */
	public MongoClient initClient(MongoInstance mongoInstance) {
		Assert.notNull(mongoInstance);
		if (mongoInstance.getUser() == null) {
			return initClient();
		}
		return initClient(mongoInstance.getUser(), mongoInstance.getPwd(),
                mongoParam.getOpacDb());
	}
	
	/**
	 * 根据db名称获取mongodb实例
	 * @param dbName
	 * @return
	 */
	public MongoInstance  getInstances(String dbName) {
		//MongoInstance instance = instancesCache.get(dbName);
		//if(instance == null){
			
		//}
		return  instancesCache.get(dbName);
	}

    /**
     * 更新数据库的_DEV_LOGIN_MGMT_库LOGIN_INFO表所有设备对应的IP
     */
    public void autoChangeHost(MongoClient adminClient,MongoParam mp){
        if("true".equals(autoChangeHost)){
            //先查出一个 X
            String dbName="_DEV_LOGIN_MGMT_";
            String collectionName="LOGIN_INFO";
            Document filter=new Document();
            Document update=new Document();
            //Document doc=mongoDB.findOne(adminClient, dbName,collectionName, filter);
            List<Document>  docs=mongoDB.find(adminClient, dbName, collectionName, filter);
            if(docs!=null && docs.size()>0){
                for(Document doc:docs){
                    String db_name=(String) doc.get("dbName");
                    if(db_name!=null){
                        Document info=(Document)doc.get(db_name);
                        if(info!=null){
                            info.put("host",mp.getHost());
                            info.put("port", mp.getPort());
                            filter.put("dbName", db_name);
                            update.put("$set",new Document(db_name,info));
                            UpdateResult ur=mongoDB.updateMany(adminClient, dbName, collectionName, filter, update);
                            System.out.println("MONGODB修改设备IP数："+ur.getModifiedCount());
                        }
                    }
                }
            }
        }
    }
    /**
     * mongodb集群
     * config.properties中配置如下：
     * mongdb.mongdbCluster=xxx.xxx.xxx.xxx:27017,xxx.xxx.xxx.xxx:27018,xxx.xxx.xxx.xxx:27019
     * @return
     */
    public List<ServerAddress>  setServerAddress(){
    	List<ServerAddress> seeds = new ArrayList<>();
    	if(StringUtils.isEmpty(mongdbCluster)){
    		System.out.println("mongdb集群配置信息，请检查集群配置");
    		return seeds ;
    	}
    	
    	if (mongdbCluster.indexOf(",")>-1) {
    		String[] mongdbClusters = mongdbCluster.split(",");
    		if(mongdbClusters.length>0){
    			
    			for ( String singleCluster: mongdbClusters){
    				
    				if( ! StringUtils.isEmpty(singleCluster)){
    					
    					String[] mongdbAddress = singleCluster.split(":");
    					seeds.add(new ServerAddress(mongdbAddress[0],Integer.valueOf(mongdbAddress[1])));
    				}
    			}
    		}
    	}
    	
    	return seeds;
    }
}
