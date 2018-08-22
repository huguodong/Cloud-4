package com.ssitcloud.business.common.dao;

import java.util.List;

import com.ssitcloud.common.entity.DatagridPageEntity;

public interface CommonDao {

	/**
	 * 查询全部
	 * @param sqlName
	 * @param t
	 * @return 执行结果
	 */
	public abstract <T> List<T> selectAll(String sqlName, T t);

	/**
	 * 查询单个
	 * @param sqlName
	 * @param t
	 * @return 执行结果
	 */
	public abstract <T> T select(String sqlName, T t);

	/**
	 * 更新
	 * @param sqlName
	 * @param t
	 * @return 执行结果
	 */
	public abstract <T> int update(String sqlName, T t);

	/**
	 * 删除
	 * @param sqlName
	 * @param t
	 * @return 执行结果
	 */
	public abstract <T> int delete(String sqlName, T t);
	
	/**
	 * easyui datagrid分页查询
	 * @param <T> 继承datagridPageEntity的实体bean
	 * @param sqlId mybatis键
	 */
	public abstract <T extends DatagridPageEntity> T queryDatagridPage(T datagridPageEntity,String sqlId);

}
