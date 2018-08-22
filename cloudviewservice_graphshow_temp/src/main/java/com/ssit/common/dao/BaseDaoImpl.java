package com.ssit.common.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DataAccessException;

import com.ssit.common.entity.DatagridPageEntity;


public class BaseDaoImpl implements BaseDao{
	@Resource
	protected SqlSessionTemplate sqlSessionTemplate;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.mysite.common.dao.impl.ssitcloud.common.dao.CommonDao#selectAll(String, Object)
	 */
	@Override
	public <T> List<T> selectAll(String sqlName, T t) throws DataAccessException {
		List<T> selectList = sqlSessionTemplate.selectList(sqlName, t);
		return selectList;
	}
	
	/**
	 * 根据param传输的参数查询数据
	 */
	@Override
	public <T> List<T> selectAll(String sqlName, Map<String,Object> param) throws DataAccessException {
		List<T> selectList = sqlSessionTemplate.selectList(sqlName, param);
		return selectList;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.mysite.common.dao.impl.ssitcloud.common.dao.CommonDao#select(String, Object)
	 */
	@Override
	public <T> T select(String sqlName, T t) throws DataAccessException {
		return sqlSessionTemplate.selectOne(sqlName, t);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.mysite.common.dao.impl.ssitcloud.common.dao.CommonDao#update(String, Object)
	 */
	@Override
	public <T> int update(String sqlName, T t) throws DataAccessException {
		return sqlSessionTemplate.update(sqlName, t);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.mysite.common.dao.impl.ssitcloud.common.dao.CommonDao#add(String, Object)
	 */
	@Override
	public <T> int insert(String sqlName, T t) throws DataAccessException {
		return sqlSessionTemplate.insert(sqlName, t);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.mysite.common.dao.impl.ssitcloud.common.dao.CommonDao#delete(String, Object)
	 */
	@Override
	public <T> int delete(String sqlName, T t) throws DataAccessException {
		return sqlSessionTemplate.delete(sqlName, t);
	}
	
	/**
	 * 统计
	 * @param sqlName
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	public int count(String sqlName,Map<String,String> param)throws DataAccessException {
		return this.sqlSessionTemplate.selectOne(sqlName, param);
	}

	/**
	 * 分页查询
	 */
	@Override
	public DatagridPageEntity queryDatagridPage(Map<String, String> keyMap, Map<String, String> param) throws DataAccessException {
		DatagridPageEntity datagridPageEntity = new DatagridPageEntity();
		datagridPageEntity.setParam(param);// 设置查询参数
		String page = param.get("page") == null ? "1" : param.get("page");
		datagridPageEntity.setPage(Integer.parseInt(page));// 设置当前页
		if (keyMap.containsKey("pageSize")) {
			datagridPageEntity.setPageSize(Integer.parseInt(keyMap.get("pageSize")));// 设置页面显示条数
		}
		// 统计数量
		long total = this.sqlSessionTemplate.selectOne(keyMap.get("totalSqlId"), datagridPageEntity);
		// 设置总条数
		datagridPageEntity.setTotal(total);
		// 查询一页数据
		List<?> rows = this.sqlSessionTemplate.selectList(keyMap.get("rowsSqlId"), datagridPageEntity);
		// 设置实际数据内容
		datagridPageEntity.setRows(rows);
		// 返回
		return datagridPageEntity;
	}

}
