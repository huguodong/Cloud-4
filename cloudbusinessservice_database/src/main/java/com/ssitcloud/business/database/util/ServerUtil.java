package com.ssitcloud.business.database.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.database.entity.MongoCollectionEntity;
import com.ssitcloud.database.entity.MongoDatabaseEntity;
import com.ssitcloud.database.entity.MysqlDatabase;
import com.ssitcloud.database.entity.Server;

public class ServerUtil {
	private static Server addServer = null;
	private static Server remoceServer = null;
	
	public static List<Server> getServers() {
		List<Server> list = new ArrayList<>();
		String database_num = PropertiesUtil.getConfigPropValueAsText("database_num");
		if(StringUtils.isEmpty(database_num)){
			return list;
		}
		Integer num = Integer.parseInt(database_num);
		for (int i = 1; i <= num; i++) {
			Integer id = i;
			String host = PropertiesUtil.getConfigPropValueAsText("server_host_" + i);
			if(StringUtils.isEmpty(host)){
				continue;
			}
			Integer port = Integer.parseInt(PropertiesUtil.getConfigPropValueAsText("server_port_" + i));
			String user = PropertiesUtil.getConfigPropValueAsText("server_user_" + i);
			String password = PropertiesUtil.getConfigPropValueAsText("server_password_" + i);
			String description = PropertiesUtil.getConfigPropValueAsText("server_description_" + i);
			String type = PropertiesUtil.getConfigPropValueAsText("server_type_" + i);
			Server server = new Server();
			server.setId(id);
			server.setHost(host);
			server.setPort(port);
			server.setUser(user);
			server.setPassword(password);
			server.setDescription(description);
			server.setType(type);
			list.add(server);
		}
		return list;
	}
	
/*	public static Server getMysqlServer(Integer server_id){
		List<Server> list = getServers();
		for(Server server : list){
			if(server_id == server.getId()){
				return server;
			}
		}
		return null;
	}
	
	public static Server getMongoServer(Integer server_id){
		List<Server> list = getServers();
		for(Server server : list){
			if(server_id == server.getId()){
				return server;
			}
		}
		return null;
	}*/
	
	public static Boolean addDBConnect(Server server){
		Integer num = 1;
		String database_num = PropertiesUtil.getConfigPropValueAsText("database_num");
		if(!StringUtils.isEmpty(database_num)){
			num = Integer.parseInt(database_num);
			num = num + 1;
		}
		LinkedHashMap<String,String> map = new LinkedHashMap<>();
		map.put("database_num", String.valueOf(num));
		map.put("server_host_"+num, String.valueOf(server.getHost()));
		map.put("server_port_"+num, String.valueOf(server.getPort()));
		map.put("server_user_"+num, String.valueOf(server.getUser()));
		map.put("server_password_"+num, String.valueOf(server.getPassword()));
		map.put("server_description_"+num, String.valueOf(server.getDescription()));
		map.put("server_type_"+num, String.valueOf(server.getType()));
		if(PropertiesUtil.modifyByMap(map)){
			server.setId(num);
			addServer = server;
			return true;
		}
		return false;
	}
	
	public static Boolean removeDBConnect(Server server){
		int num = server.getId();
		LinkedHashMap<String,String> map = new LinkedHashMap<>();
		map.put("server_host_"+num, "");
		map.put("server_port_"+num, "");
		map.put("server_user_"+num, "");
		map.put("server_password_"+num, "");
		map.put("server_description_"+num, "");
		map.put("server_type_"+num, "");
		if(PropertiesUtil.modifyByMap(map)){
			remoceServer = server;
			return true;
		}
		return false;
	}
	
	public static void recoverData(){
		addServer = null;
		remoceServer = null;
	}
	
	public static Server mysqlServer(Server server){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<MysqlDatabase> databases = new ArrayList<>();
		try{
			conn = ConnectionPool.getInstance().getConnection(server);
			stmt = conn.prepareStatement("SHOW DATABASES");
			rs = stmt.executeQuery();
			while (rs.next()) {
				String databaseName = rs.getString(1);
				MysqlDatabase db = MysqlFactory.loadDatabase(server,databaseName);
				databases.add(db);
			}
			server.setDbs(databases);
			System.out.println("###############################加载mysql数据库"+server.getHost());
		}catch(Exception e){
			LogUtils.error("mysql数据库连接错误!!");
		}finally{
			try {
				if(rs!=null) rs.close();
		        if(stmt!=null) stmt.close();
		        if(conn!=null) conn.close();
			} catch (SQLException e) {
				LogUtils.error("mysql数据库连接关闭错误!!");
			}
		}
		return server;
	}
	
	public static Server mongoServer(Server server){
		MongoClient mongoClient=null;  
		try{
			
			
			int timeout = 3; //秒.  
	        ExecutorService executor = Executors.newSingleThreadExecutor(); 
	        
	        List<String> result = null;     
	        
			
			ServerAddress serverAddress = new ServerAddress(server.getHost(),server.getPort());    
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();    
            addrs.add(serverAddress);  
            
            /*MongoCredential credential = MongoCredential.createScramSha1Credential(server.getUser(), server.getDescription(), server.getPassword().toCharArray());    
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();    
            credentials.add(credential); 
            mongoClient = new MongoClient(addrs,credentials);*/
            
            MongoClientOptions clientOptions=new MongoClientOptions.Builder().connectionsPerHost(10).socketTimeout(3000).build();
			List<MongoCredential> lstCredentials = Arrays.asList(MongoCredential.createScramSha1Credential(server.getUser(), server.getDescription(), server.getPassword().toCharArray()));  
			mongoClient = new MongoClient(addrs,lstCredentials, clientOptions);  
			
            List<MongoDatabaseEntity> databases = new ArrayList<>();
            
            Future<List<String>> future = executor.submit(new MyJob(mongoClient));// 将任务提交到线程池中     
	        try {     
	            result = future.get(timeout*1000, TimeUnit.MILLISECONDS);// 设定在200毫秒的时间内完成   
	            System.out.println(result);  
	        } catch (InterruptedException e) {  
	            System.out.println("线程中断出错。");  
	            future.cancel(true);// 中断执行此任务的线程    
	            return server;
	        } catch (ExecutionException e) {     
	            System.out.println("线程服务出错。");  
	            future.cancel(true);// 中断执行此任务的线程    
	            return server;
	        } catch (TimeoutException e) {// 超时异常     
	            System.out.println("超时。");     
	            future.cancel(true);// 中断执行此任务的线程    
	            return server;
	        }finally{  
	            executor.shutdown();  
	        }  
	        
            for(String dbName : result){
            	MongoDatabaseEntity dbEntity = new MongoDatabaseEntity();
            	dbEntity.setName(dbName);
            	//连接到数据库    
                MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
                for(String collectionName : mongoDatabase.listCollectionNames()){
                	MongoCollectionEntity collectionEntity = new MongoCollectionEntity();
                	collectionEntity.setName(collectionName);
                	dbEntity.addCollection(collectionEntity);
                }
                databases.add(dbEntity);
            }
            server.setMdbs(databases);
            System.out.println("###############################加载mongo数据库"+server.getHost());
		}catch(Exception e){
			LogUtils.error("mongo数据库连接错误!!");
		}finally{
			if(mongoClient!=null) mongoClient.close();
		}
		return server;
	}
	
	static class MyJob implements Callable<List<String>> {    
        private MongoClient t;  
        public MyJob(MongoClient temp){  
            this.t= temp;  
        }  
        public List<String> call() {     
            List<String> list = t.getDatabaseNames();
            if (Thread.interrupted()){ //很重要  
                return null;     
            } 
            return list;     
        }     
    }  
	
	public static ConcurrentMap<String, List<Server>> dataBaseMenus() {
		ConcurrentHashMap<String, List<Server>> instances = new ConcurrentHashMap<String, List<Server>>(32);
		List<Server> mysqlList = new ArrayList<>();
		List<Server> mongoList = new ArrayList<>();
		List<Server> list = getServers();
		
		for (Server server : list) {
			if("Mysql".equals(server.getType())){
				mysqlList.add(mysqlServer(server));
			}else if("Mongo".equals(server.getType())){
				 mongoList.add(mongoServer(server));
			}
		}
		instances.put("Mysql", mysqlList);
		instances.put("Mongo", mongoList);
		return instances;
	}

	public static Server getAddServer() {
		return addServer;
	}

	public static Server getRemoceServer() {
		return remoceServer;
	}
	
}
