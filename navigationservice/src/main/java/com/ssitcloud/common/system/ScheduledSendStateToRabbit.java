package com.ssitcloud.common.system;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import com.ssitcloud.common.util.LogUtils;

public class ScheduledSendStateToRabbit {
	
	static java.sql.Connection jdbcConn = null;
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) {
		System.out.println(df.format(new Date()));
	}
	
	public static void send(){
		try {
			String dbname = "daohang";
			try {
				Properties prop=PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("jdbc.properties")));
				Driver driver1 = (Driver) (Class.forName(prop.getProperty("jdbc.driverClass")).newInstance());
				DriverManager.registerDriver(driver1); // 注册 JDBC 驱动程序
				jdbcConn = DriverManager.getConnection(prop.getProperty("jdbc.jdbcUrl"),prop.getProperty("jdbc.user"), prop.getProperty("jdbc.password"));
			} catch (Exception e) {
				System.out.println("method ScheduledSendStateToRabbit 数据库连接失败！");
				try{
					jdbcConn.close();
				}catch(Exception ex){
					///
				}
			}
			
			String queue_ip = null;
	        String queue_port = null;
	        String queue_login_name = null;
	        String queue_login_pwd = null;
	        String virtualHost = null;
	        String queue_exchange = null;
	        String queue_route = null;
	        String queue_id = null;
	        
	        String library_id = null;
	        String service_id = null;
	        
			if(jdbcConn != null){
				String str = "select * from daohang.device_service_queue LEFT JOIN "+dbname+".device_service on device_service_queue.device_service_id = device_service.service_id limit 1";
				PreparedStatement prepareStatement = jdbcConn.prepareStatement(str);
				ResultSet result = prepareStatement.executeQuery();
				while(result.next()){
					queue_ip = result.getString("queue_ip");
			        queue_port = result.getString("queue_port");
			        queue_login_name = result.getString("queue_login_name");
			        queue_login_pwd = result.getString("queue_login_pwd");
			        virtualHost = result.getString("queue_virtual_host");
			        queue_exchange = result.getString("queue_exchange");
			        queue_route = result.getString("queue_route");
			        queue_id = result.getString("queue_id");
			        library_id = result.getString("library_id");
			        service_id = result.getString("service_id");
				}
			}
			
			if(library_id == null || service_id == null || queue_ip == null){
				LogUtils.error("missing rabbit config message ！！！");
				return;
			}
			
			String path = ScheduledSendStateToRabbit.class.getResource("/").getFile() + "rabbit/";
			path = java.net.URLDecoder.decode(path, "UTF-8");
			String keycert = path + "keycert.p12";
			String store = path + "trustStore";
			
	    	//客户端SSL私钥
	        char[] keyPassphrase = "MySecretPassword".toCharArray();
	        KeyStore ks = KeyStore.getInstance("PKCS12");
	        ks.load(new FileInputStream(keycert), keyPassphrase);

	        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
	        kmf.init(ks, keyPassphrase);
	        
	        //客户端keystore 
	        char[] trustPassphrase = "rabbit".toCharArray();
	        KeyStore tks = KeyStore.getInstance("JKS");
	        tks.load(new FileInputStream(store), trustPassphrase);

	        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
	        tmf.init(tks);
	        
	        SSLContext c = SSLContext.getInstance("TLSv1.2");
	        c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

	        
	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost(queue_ip);
	        factory.setPort(Integer.parseInt(queue_port));
	        factory.useSslProtocol(c);
	        factory.setUsername(queue_login_name);
	        factory.setPassword(queue_login_pwd);
	        factory.setVirtualHost(virtualHost);
//	        factory.setUri("amqps://comomusr:5HkMenUwc4pf2mgFp9fGKw==@106.75.22.29/");
	        Connection conn = factory.newConnection();
	        Channel channel = conn.createChannel();

	        channel.exchangeDeclare(queue_exchange, "topic", true);
	        channel.queueDeclare(queue_id, true, false, false, null);
	        channel.queueBind(queue_id, queue_exchange, queue_route);

	        String result = "{\"device_id\":\""+service_id+"\",\"library_id\":\""+library_id+"\",\"content\":{\"soft_state\":{\"Function\":{\"00040105\":\"1\"},\"Main\":{\"StartupTime\":\"2017-01-01 00:00:00\",\"UpdateTime\":\""+df.format(new Date())+"\"}}}}";
	        byte[] messageBodyBytes = result.getBytes();  
	        BasicProperties bp = new BasicProperties("application/json",
                    null,
                    null,
                    2,
                    0, null, null, "7200000",
                    null, null, null, null,
                    null, null);
	        channel.basicPublish(queue_exchange, queue_id, bp, messageBodyBytes);  
	        
	        
	        GetResponse chResponse = channel.basicGet(queue_id, false);
	        if(chResponse == null) {
	            System.out.println("No message retrieved");
	        } else {
	            byte[] body = chResponse.getBody();
	            System.out.println("Recieved: " + new String(body));
	        }


	        channel.close();
	        conn.close();
	    } catch (Exception e) {
			LogUtils.error("send state to ucloud rabbit 异常！！！",e);
		}
	}
	
}
