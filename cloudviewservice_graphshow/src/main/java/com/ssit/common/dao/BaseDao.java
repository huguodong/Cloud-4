package com.ssit.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ssit.common.entity.DatagridPageEntity;


public interface BaseDao {

	/**
	 * 查询全部
	 * @param sqlName
	 * @param t
	 * @return 执行结果
	 */
	public abstract <T> List<T> selectAll(String sqlName, T t) throws DataAccessException;
	
	/**
	 * 查询全部
	 * @param sqlName
	 * @param param
	 * @return 执行结果
	 */
	public abstract <T> List<T> selectAll(String sqlName, Map<String,String> param) throws DataAccessException;

	/**
	 * 查询单个
	 * @param sqlName
	 * @param t
	 * @return 执行结果
	 */
	public abstract <T> T select(String sqlName, T t) throws DataAccessException;

	/**
	 * 更新
	 * @param sqlName
	 * @param t
	 * @return 执行结果
	 */
	public abstract <T> int update(String sqlName, T t) throws DataAccessException;
	
	/**
	 * 更新
	 * @param sqlName
	 * @param t
	 * @return 执行结果
	 */
	public abstract <T> int insert(String sqlName, T t) throws DataAccessException;

	/**
	 * 删除
	 * @param sqlName
	 * @param t
	 * @return 执行结果
	 */
	public abstract <T> int delete(String sqlName, T t) throws DataAccessException;
	
	/**
	 * 分页查询
	 */
	public abstract DatagridPageEntity queryDatagridPage(Map<String,String> keyMap, Map<String,String> param) throws DataAccessException;

}
