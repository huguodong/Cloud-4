package com.ssitcloud.common.system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.ssitcloud.common.util.LogUtils;

public class ScheduledConfig {
	
	static Connection jdbcConn = null;
	static Connection odbcConn = null;
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void exportReaderInfoFromSqlServerDB(){
		try {
			System.out.println("启动。。。");
			Properties prop=PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("static-config.properties")));
			
			String ip = prop.getProperty("ip").toString();
			int port=Integer.parseInt(prop.get("port").toString());
			String username=prop.get("username").toString();
			String password=prop.get("password").toString();
			String dbname = prop.get("dbname").toString();
			System.out.println("sql server 配置信息     ip="+ip+" port="+port+" username="+username+" password="+password+" dbname="+dbname);
			
			try {
				
				Driver driver1 = (Driver) (Class.forName("com.mysql.jdbc.Driver").newInstance());
				DriverManager.registerDriver(driver1); // 注册 JDBC 驱动程序
				jdbcConn = DriverManager.getConnection("jdbc:mysql://localhost:38096/","root", "root");
				
				Driver driver2 = (Driver) (Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance());
				DriverManager.registerDriver(driver2); // 注册 JDBC 驱动程序
				odbcConn = DriverManager.getConnection("jdbc:sqlserver://"+ip+":"+port+";DatabaseName="+dbname,username, password);
			} catch (Exception e) {
				System.out.println("sqlserver数据库连接失败！");
				try{
					jdbcConn.close();
					odbcConn.close();
				}catch(Exception ex){
					///
				}
			}
			
			if(odbcConn != null && jdbcConn != null){
				try{
					Date idx = getIDX();
					if(idx == null){
						return;
					}
					String ClsName = "";
					String DepName = "";
					String str = "select top 1000 LastTime,CardNO,PerCode,AccName,AccClsCode,DepCode,LostDate,AccType from ac_dict_Accounts where LastTime >= ? order by LastTime asc";
					PreparedStatement prepareStatement = odbcConn.prepareStatement(str);
					prepareStatement.setDate(1, idx);
					ResultSet result = prepareStatement.executeQuery();
					
					while(result.next()){
						String LastTime = result.getString("LastTime");
						System.out.println("####################################"+LastTime);
						String CardNO = result.getString("CardNO");
						String AccClsCode = result.getString("AccClsCode");
						String DepCode = result.getString("DepCode");
						String AccType = result.getString("AccType");
						
						String str1 = "select ClsName from ac_dict_AccountClass where ClsCode = ? ";
						PreparedStatement prepareStatement1 = odbcConn.prepareStatement(str1);
						prepareStatement1.setString(1, AccClsCode);
						ResultSet result1 = prepareStatement1.executeQuery();
						while(result1.next()){
							ClsName = result1.getString(1);
						}
						prepareStatement1.close();
						result1.close();
						
						
						/*select b.DepName from ac_dict_AccDep a 
						inner JOIN ac_dict_AccDep b
						ON a.ParentDepCode = b.DepCode
						and a.DepCode = '240'*/
						
						String str2 = "select b.DepName from ac_dict_AccDep a inner JOIN ac_dict_AccDep b ON a.ParentDepCode = b.DepCode and a.DepCode = ?";
						PreparedStatement prepareStatement2 = odbcConn.prepareStatement(str2);
						prepareStatement2.setString(1, DepCode);
						ResultSet result2 = prepareStatement2.executeQuery();
						while(result2.next()){
							DepName = result2.getString(1);
						}
						prepareStatement2.close();
						result2.close();
						//查找
						
						String str3 = "select count(1) from skedb.Icreader where CardId = ? ";
						PreparedStatement prepareStatement3 = jdbcConn.prepareStatement(str3);
						prepareStatement3.setString(1, CardNO);
						ResultSet result3 = prepareStatement3.executeQuery();
						while(result3.next()){
							int num = Integer.parseInt(result3.getString(1));
							if(num == 0){
								//插入
								String str4 = "insert into skedb.Icreader values(?,?,?,?,?,?,?,?,?,?);";
								PreparedStatement prepareStatement4 = jdbcConn.prepareStatement(str4);
								prepareStatement4.setString(1, CardNO);
								prepareStatement4.setString(2, result.getString("PerCode"));
								prepareStatement4.setString(3, result.getString("AccName"));
								prepareStatement4.setString(4, ClsName);
								prepareStatement4.setString(5, DepName);
								prepareStatement4.setString(6, result.getString("LostDate"));
								prepareStatement4.setString(7, "0");
								prepareStatement4.setString(8, AccType);
								prepareStatement4.setString(9, null);
								prepareStatement4.setString(10, null);
								prepareStatement4.execute();
								prepareStatement4.close();
								System.out.println("插入成功！");
							}else{
								//更新
								String str5 = "update skedb.Icreader set CardId=?,StudentCode=?,Name=?,Type=?,Dept=?,Date=?,Flag=?,State=?,Stop=?,Day=? where CardId = ?";
								PreparedStatement prepareStatement5 = jdbcConn.prepareStatement(str5);
								prepareStatement5.setString(1, CardNO);
								prepareStatement5.setString(2, result.getString("PerCode"));
								prepareStatement5.setString(3, result.getString("AccName"));
								prepareStatement5.setString(4, ClsName);
								prepareStatement5.setString(5, DepName);
								prepareStatement5.setString(6, result.getString("LostDate"));
								prepareStatement5.setString(7, "0");
								prepareStatement5.setString(8, AccType);
								prepareStatement5.setString(9, null);
								prepareStatement5.setString(10, null);
								prepareStatement5.setString(11, CardNO);
								prepareStatement5.execute();
								prepareStatement5.close();
								System.out.println("更新成功！");
							}
						}
						prepareStatement1.close();
						result1.close();
						saveIDX(LastTime);
							
					}
					prepareStatement.close();
					result.close();
				}catch(Exception e){
					System.out.println("1111"+e);
				}
				jdbcConn.close();
				odbcConn.close();
			}
			
			/*if(odbcConn != null && jdbcConn != null){
				try{
					String idx = getIDX();
					String card = "";
					String type = "";
					String str = "select top 10 idx,card,type from Icreader_tri where idx > ? ";
					PreparedStatement prepareStatement = odbcConn.prepareStatement(str);
					prepareStatement.setString(1, idx);
					ResultSet result = prepareStatement.executeQuery();
					
					while(result.next()){
						idx = result.getString("idx");
						card = result.getString("card");
						type = result.getString("type");
						
						String str1 = "select count(1) from skedb.Icreader where CardId = ? ";
						PreparedStatement prepareStatement1 = jdbcConn.prepareStatement(str1);
						prepareStatement1.setString(1, card);
						ResultSet result1 = prepareStatement1.executeQuery();
						while(result1.next()){
							int num = Integer.parseInt(result1.getString(1));
							if(num == 0){
								//不存在数据，处理3种情况
								if("I".equals(type)){
									insertData(card);
								}else if("U".equals(type)){
									insertData(card);
								}else if("D".equals(type)){
									//
								}
							}else{
								//存在数据，处理3种情况
								if("I".equals(type)){
									updateData(card);
								}else if("U".equals(type)){
									updateData(card);
								}else if("D".equals(type)){
									deleteData(card);
								}
							}
						}
						prepareStatement1.close();
						result1.close();
						saveIDX(idx);
							
					}
					prepareStatement.close();
					result.close();
				}catch(Exception e){
					System.out.println("1111");
				}
				odbcConn.close();
			}*/
			
		} catch (Exception e) {
			LogUtils.error("static-config.properties 配置异常！！！",e);
		}
	}
	
	public static void insertData(String card) throws Exception{
		String str = "select CardId,StudentCode,Name,Type,Dept,Date,Flag,State,Stop,Day from Icreader where CardId = ?";
		PreparedStatement prepareStatement = odbcConn.prepareStatement(str);
		prepareStatement.setString(1, card);
		ResultSet result = prepareStatement.executeQuery();
		while(result.next()){
			//插入
			String str1 = "insert into skedb.Icreader values(?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement prepareStatement1 = jdbcConn.prepareStatement(str1);
			prepareStatement1.setString(1, result.getString("CardId"));
			prepareStatement1.setString(2, result.getString("StudentCode"));
			prepareStatement1.setString(3, result.getString("Name"));
			prepareStatement1.setString(4, result.getString("Type"));
			prepareStatement1.setString(5, result.getString("Dept"));
			prepareStatement1.setString(6, result.getString("Date"));
			prepareStatement1.setString(7, result.getString("Flag"));
			prepareStatement1.setString(8, result.getString("State"));
			prepareStatement1.setString(9, result.getString("Stop"));
			prepareStatement1.setString(10, result.getString("Day"));
			prepareStatement1.execute();
			prepareStatement1.close();
		}
		prepareStatement.close();
		result.close();
	}
	
	public static void updateData(String card) throws Exception{
		String str = "select CardId,StudentCode,Name,Type,Dept,Date,Flag,State,Stop,Day from Icreader where CardId = ?";
		PreparedStatement prepareStatement = odbcConn.prepareStatement(str);
		prepareStatement.setString(1, card);
		ResultSet result = prepareStatement.executeQuery();
		while(result.next()){
			//更新
			String str1 = "update skedb.Icreader set CardId=?,StudentCode=?,Name=?,Type=?,Dept=?,Date=?,Flag=?,State=?,Stop=?,Day=? where CardId = ?";
			PreparedStatement prepareStatement1 = jdbcConn.prepareStatement(str1);
			prepareStatement1.setString(1, result.getString("CardId"));
			prepareStatement1.setString(2, result.getString("StudentCode"));
			prepareStatement1.setString(3, result.getString("Name"));
			prepareStatement1.setString(4, result.getString("Type"));
			prepareStatement1.setString(5, result.getString("Dept"));
			prepareStatement1.setString(6, result.getString("Date"));
			prepareStatement1.setString(7, result.getString("Flag"));
			prepareStatement1.setString(8, result.getString("State"));
			prepareStatement1.setString(9, result.getString("Stop"));
			prepareStatement1.setString(10, result.getString("Day"));
			prepareStatement1.setString(11, card);
			prepareStatement1.execute();
			prepareStatement1.close();
		}
		prepareStatement.close();
		result.close();
	}
	
	public static void deleteData(String card) throws Exception{
		String str = "delete from skedb.Icreader where card = ?";
		PreparedStatement prepareStatement = jdbcConn.prepareStatement(str);
		prepareStatement.setString(1, card);
		prepareStatement.execute();
		prepareStatement.close();
	}
	
	public static Date getIDX(){
		try{
			String filename = ScheduledConfig.class.getResource("/").getFile();
			filename = java.net.URLDecoder.decode(filename, "UTF-8");
			filename += "idx.ini";
			FileInputStream is = new FileInputStream(filename);
			Properties prop = new Properties();
			prop.load(is);
			String idx = prop.getProperty("idx");
			java.util.Date date = df.parse(idx);
			return new Date(date.getTime());
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
	
	/*public static void resertIDX(){
		saveIDX("0");
	}*/
	
	public static void main(String[] args) {
		exportReaderInfoFromSqlServerDB();
	}

}
