package com.ssitcloud.dblib.dao;

import java.util.Map;

import com.ssitcloud.dblib.entity.BibliosEntity;

public interface NavigationDao {
	/**
	 * 查询书本总数
	 *
	 * <p>2017年10月23日 下午4:24:29 
	 * <p>create by liuwei
	 * @param bibliosEntity
	 * @return
	 */
	public abstract int queryBookItemTotal(Map<String, Object> param);
	
	/**
	 * 查询层架标总数
	 *
	 * <p>2017年10月23日 下午4:24:29 
	 * <p>create by liuwei
	 * @param bibliosEntity
	 * @return
	 */
	public abstract int queryBookShelfLayerTotal(Map<String, Object> param);
	
	/**
	 * 查询书架总数
	 *
	 * <p>2017年10月23日 下午4:24:29 
	 * <p>create by liuwei
	 * @param bibliosEntity
	 * @return
	 */
	public abstract int queryBookShelfTotal(Map<String, Object> param);
}
