package com.ssitcloud.business.database.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;



public interface DataBaseService {

	/**
	 * 
	 * 数据库备份
	 * 如果是 mysql 则连接到 clouddbservice_authentication 或者clouddbservice_device 工程<br>
	 * 如果是 mongodb 则连接到 clouddbservice_devicemonitor 工程
	 * 
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity backUp(String req);
	
	/**
	 * 备份MYSQL SSITCloud 数据库
	 * @param username
	 * @param password
	 * @param ip
	 * @param dbName
	 * @param os
	 * @return
	 */
	ResultEntity bakupSsitCloud(String username, String password, String ip,String dbName, String os);
	/**
	 * MYSQL SSITCloud 数据库 还原
	 * @param username
	 * @param password
	 * @param ip
	 * @param dbName
	 * @param os
	 * @param zipFileName
	 * @return
	 */
	ResultEntity recoverSsitCloud(String username, String password, String ip,
			String dbName, String os, String zipFileName);

	int databaseRollback(String bakPath, String username, String password,
			String ip, String dbName, String zipFileName,
			MysqlExecuteCmd cmdExecute);

	ResultEntity queryDbBakByparam_bus(String req);

	ResultEntity deleteBakup_bus(String req);

	ResultEntity getLastBakUpTime_bus(String req);

	ResultEntity restoreBakup_bus(String req);

	String getDir();

	int bakupSsitCloudMongo(String bakPath, String username, String password,String ip, String dbName, MysqlExecuteCmd cmdExecute);

	int mongodbDatabaseRollback(String bakPath, String username,
			String password, String ip, String dbName, String zipFileName,
			MysqlExecuteCmd cmdExecute);

	boolean validateFileName(String fileName);

	ResultEntity queryLibraryDbBakByparamExt(String req);
	
}
