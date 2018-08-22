package com.ssitcloud.common.system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.ssitcloud.common.service.RedisService;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;

public class ScheduledRedisBookitem {
	
	@Resource
	private RedisService redisService;
	
	static java.sql.Connection jdbcConn = null;
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) {
		System.out.println(df.format(new Date()));
	}
	
	public void send(){
		try {
			String dbname = "daohang";
			try {
				Properties prop=PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("jdbc.properties")));
				Driver driver1 = (Driver) (Class.forName(prop.getProperty("jdbc.driverClass")).newInstance());
				DriverManager.registerDriver(driver1); // 注册 JDBC 驱动程序
				jdbcConn = DriverManager.getConnection(prop.getProperty("jdbc.jdbcUrl"),prop.getProperty("jdbc.user"), prop.getProperty("jdbc.password"));
			} catch (Exception e) {
				System.out.println("method ScheduledRedisBookitem 数据库连接失败！");
				try{
					jdbcConn.close();
				}catch(Exception ex){
					///
				}
			}
			
			if(jdbcConn == null){
				return;
			}
			
			String idx = getIDX(); //{"updatetime":"2018-02-03 00:00:00","book_barcode":"110110"}
			String r_updatetime = null;
			String r_book_barcode = null;
			if(StringUtils.isEmpty(idx)){
				return;
			}else{
				Map<String,String> map = JsonUtils.fromJson(idx, Map.class);
				r_updatetime = map.get("updatetime");
				r_book_barcode = map.get("book_barcode");
				
				if(StringUtils.isEmpty(r_updatetime) || StringUtils.isEmpty(r_book_barcode)){
					return;
				}
			}
			
			String book_barcode = null;
	        String book_uid = null;
	        String ISBN = null;
	        String title = null;
	        String author = null;
	        String publish = null;
	        String callNo = null;
	        String shelflayer_barcode = null;
	        String update_uid_flag = null;
	        String state = null;
	        String updatetime = null;
	        String statemodcount = null;
	        String subsidiary = null;
	        String location = null;
	        String uploadtime = null;
	        String device_id = null;
			
			String str = "select book_barcode,book_uid,ISBN,title,author,publish,callNo,shelflayer_barcode,update_uid_flag,state,updatetime,statemodcount,subsidiary,location,uploadtime,device_id "
					+ " from "+dbname+".bookitem "
					+ " where (uploadtime = ? and book_barcode > ?) or (uploadtime > ?)"
									+ " order by uploadtime,book_barcode asc limit 1000";   
			
			PreparedStatement prepareStatement = jdbcConn.prepareStatement(str);
			prepareStatement.setString(1, r_updatetime);
			prepareStatement.setString(2, r_book_barcode);
			prepareStatement.setString(3, r_updatetime);
			ResultSet result = prepareStatement.executeQuery();
			while(result.next()){
				book_barcode = result.getString("book_barcode")== null?"":result.getString("book_barcode").trim();
				book_uid = result.getString("book_uid")== null?"":result.getString("book_uid").trim();
				ISBN = result.getString("ISBN")== null?"":result.getString("ISBN").trim();
				title = result.getString("title")== null?"":result.getString("title").trim();
				author = result.getString("author")== null?"":result.getString("author").trim();
				publish = result.getString("publish")== null?"":result.getString("publish").trim();
				callNo = result.getString("callNo")== null?"":result.getString("callNo").trim();
				shelflayer_barcode = result.getString("shelflayer_barcode")== null?"":result.getString("shelflayer_barcode").trim();
				update_uid_flag = result.getString("update_uid_flag")== null?"":result.getString("update_uid_flag").trim();
				state = result.getString("state")== null?"":result.getString("state").trim();
				updatetime = result.getString("updatetime")== null?"":result.getString("updatetime").trim();
				statemodcount = result.getString("statemodcount")== null?"":result.getString("statemodcount").trim();
				subsidiary = result.getString("subsidiary")== null?"":result.getString("subsidiary").trim();
				location = result.getString("location")== null?"":result.getString("location").trim();
				uploadtime = result.getString("uploadtime")== null?"":result.getString("uploadtime").trim();
				device_id = result.getString("device_id")== null?"":result.getString("device_id").trim();
				
				Map<String,String> map = new HashMap<String, String>();
				map.put("book_barcode", book_barcode);
				map.put("book_uid", book_uid);
				map.put("ISBN", ISBN);
				map.put("title", title);
				map.put("author", author);
				map.put("publish", publish);
				map.put("callNo", callNo);
				map.put("shelflayer_barcode", shelflayer_barcode);
				map.put("update_uid_flag", update_uid_flag);
				map.put("state", state);
				map.put("updatetime", updatetime);
				map.put("statemodcount", statemodcount);
				map.put("subsidiary", subsidiary);
				map.put("location", location);
				map.put("uploadtime", uploadtime);
				map.put("device_id", device_id);
				
				//加入key 为条码号的数据
				redisService.addhmset(book_barcode, map);
				System.out.println("添加数据key:"+book_barcode);
				String flagStr = "{\"updatetime\":\""+uploadtime+"\",\"book_barcode\":\""+book_barcode+"\"}";
				saveIDX(flagStr);
			}
			
			jdbcConn.close();
	    } catch (Exception e) {
			LogUtils.error("write bookitem data to redis error:",e);
		}
	}
	
	public static String getIDX(){
		try{
			String filename = ScheduledConfig.class.getResource("/").getFile();
			filename = java.net.URLDecoder.decode(filename, "UTF-8");
			filename += "idx.ini";
			FileInputStream is = new FileInputStream(filename);
			Properties prop = new Properties();
			prop.load(is);
			return prop.getProperty("idx");
		}catch(Exception e){
			System.out.println("记录号读取失败！\r\n"+e);
		}
		return null;
	}
	
	public static void saveIDX(String idx){
		try{
			String filename = ScheduledConfig.class.getResource("/").getFile();
			filename = java.net.URLDecoder.decode(filename, "UTF-8");
			filename += "idx.ini";
			OutputStream out = new FileOutputStream(filename);
			Properties prop = new Properties();
			prop.setProperty("idx", idx);
			prop.store(out,null);
			out.flush();
			out.close();
		}catch(Exception e){
			System.out.println("记录号写入失败！\r\n"+e);
		}
	}
	
}
