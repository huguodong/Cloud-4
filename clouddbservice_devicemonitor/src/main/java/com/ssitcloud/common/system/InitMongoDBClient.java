package com.ssitcloud.common.system;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.MBeanUtil;
import com.ssitcloud.common.utils.CommonException;
import com.ssitcloud.common.utils.LogUtils;

@Component  
public class InitMongoDBClient implements InitializingBean  {
	
	@Resource(name = "mongoParam")
    private MongoParam mongoParam;
	
	@Value("${mongdb.mongdbCluster}")
	private String mongdbCluster;
	
	@Resource(name="mongoClientOpt")
	private MongoClientOptions mongoClientOptions;
	
	private  MongoClient client = MongoInstanceManager.mongoClient;
	
	public InitMongoDBClient() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initServerAddress();
		initMongoDBClient();
		initMongoInstances();
	}

	/**
	 * 初始化mongodb客户端
	 */
	private void initMongoDBClient() {
		MongoCredential credential = MongoCredential.createScramSha1Credential(mongoParam.getUser(), mongoParam.getOpacDb(), mongoParam.getPwd().toCharArray());
		MongoInstanceManager.mongoClient = new MongoClient(MongoInstanceManager.seeds, Arrays.asList(credential),mongoClientOptions);
		MongoInstanceManager.mongoClient.setWriteConcern(WriteConcern.ACKNOWLEDGED);
//		MongoInstanceManager.mongoClient.setReadPreference(ReadPreference.secondaryPreferred());
		
	}

	/**
	 * 应用启动后加载mongodb数据库
	 * @return
	 */
	private void initMongoInstances() {
		LogUtils.info("----------------------instantiation MongoInstance start-----------------------");
		System.out.println("----------------------instantiation MongoInstance start----------------------- ");
		MongoInstanceManager.instancesCache.clear();
        try {
            MongoInstance mongoIns=new MongoInstance();
            mongoIns.setOperDatabase(mongoParam.getOpacDb());//oper admindb
            mongoIns.setUser(mongoParam.getUser());
            mongoIns.setPwd(mongoParam.getPwd());
            List<ServerAddress> seeds = MongoInstanceManager.seeds ;
            MongoCredential credential = MongoCredential.createScramSha1Credential(mongoIns.getUser(), mongoParam.getOpacDb(), mongoParam.getPwd().toCharArray());
            client = new MongoClient(seeds, Arrays.asList(credential));
            MongoDatabase mongoDatabase= client.getDatabase("_DEV_LOGIN_MGMT_");//用于管理设备登陆的
            if(mongoDatabase!=null){
            	MongoInstanceManager.instancesCache.put("_DEV_LOGIN_MGMT_",mongoIns);
                MongoCollection<Document> mc=mongoDatabase.getCollection("LOGIN_INFO");
                if(mc!=null){
                    MongoCursor<Document> mcur= mc.find().iterator();
                    while(mcur.hasNext()){
                        Document doc=mcur.next();
                        doc.remove("_id");
                        String dbName=(String)doc.remove("dbName");
                        if(doc.containsKey(dbName)){
                            MongoInstance mo= JsonUtils.convertValue(doc.get(dbName),MongoInstance.class);
                            if(MBeanUtil.validationMongoInstance(mo)){
                                //LogUtils.info("MongoInstance name： " + dbName);
                                MongoInstanceManager.instancesCache.put(dbName,mo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
            throw new CommonException("initMongoInstances method 异常", e);
        }finally{
            //CloseUtil.close(client);
        }
        LogUtils.info("----------------------instantiation MongoInstance finish-----------------------");
    }
	 /**
     * mongodb集群
     * config.properties中配置如下：
     * mongdb.mongdbCluster=xxx.xxx.xxx.xxx:27017,xxx.xxx.xxx.xxx:27018,xxx.xxx.xxx.xxx:27019
     * @return
     */
	private void  initServerAddress(){
    	MongoInstanceManager.seeds = new CopyOnWriteArrayList<ServerAddress>();
    	if(StringUtils.isEmpty(mongdbCluster)){
    		System.out.println("mongdb集群配置信息，请检查集群配置");
    	}
    	
    	if (mongdbCluster.indexOf(",")>-1) {
    		String[] mongdbClusters = mongdbCluster.split(",");
    		if(mongdbClusters.length>0){
    			
    			for ( String singleCluster: mongdbClusters){
    				
    				if( ! StringUtils.isEmpty(singleCluster)){
    					
    					String[] mongdbAddress = singleCluster.split(":");
    					MongoInstanceManager.seeds.add(new ServerAddress(mongdbAddress[0],Integer.valueOf(mongdbAddress[1])));
    				}
    			}
    		}
    	}
    	
    }
}
