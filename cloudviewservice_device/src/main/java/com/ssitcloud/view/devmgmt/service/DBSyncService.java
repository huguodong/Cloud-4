package com.ssitcloud.view.devmgmt.service;

import java.util.Map;

/**
 * 
 * @comment 
 * @date 2016年6月1日
 * @author hwl
 */
public interface DBSyncService {
	/**
	 * 查询数据库同步模板数据
	 * @comment
	 * @param map
	 * @return
	 * @data 2016年6月1日
	 * @author hwl
	 */
	String selDBsyncConfig(Map<String, String> map);
	
	/**
	 * 添加数据库同步模板
	 * @comment
	 * @param map
	 * @return
	 * @data 2016年6月1日
	 * @author hwl
	 */
	String addDBsyncConfig(Map<String, String> map);
	
	/**
	 * 更新数据库同步模板
	 * @comment
	 * @param map
	 * @return
	 * @data 2016年6月1日
	 * @author hwl
	 */
	String updDBsyncConfig(Map<String, String> map);
	
	/**
	 * 删除数据库同步模板
	 * @comment
	 * @param map
	 * @return
	 * @data 2016年6月1日
	 * @author hwl
	 */
	String delDBsyncConfig(Map<String, String> map);
}
