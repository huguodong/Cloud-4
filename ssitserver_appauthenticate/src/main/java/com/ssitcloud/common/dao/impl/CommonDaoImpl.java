package com.ssitcloud.common.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.common.entity.DatagridPageEntity;

@Repository(value="commonDao")
public class CommonDaoImpl implements CommonDao {
	@Resource
	protected SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public SqlSession getSqlSession(){
		return sqlSessionTemplate;
	}

	/** (non-Javadoc)
	 * @see com.ssitcloud.common.dao.CommonDao#selectAll(String, Object)
	 */
	@Override
	public <T> List<T> selectAll(String sqlName,T t){
			List<T> selectList = sqlSessionTemplate.selectList(sqlName,t);
			return selectList;
	}
	
	/** (non-Javadoc)
	 * @see com.ssitcloud.common.dao.CommonDao#select(String, Object)
	 */
	@Override
	public <T> T select(String sqlName,T t){
			return sqlSessionTemplate.selectOne(sqlName,t);
	}
	
	/** (non-Javadoc)
	 * @see com.ssitcloud.common.dao.CommonDao#update(String, Object)
	 */
	@Override
	public <T> int update(String sqlName,T t){
			return sqlSessionTemplate.update(sqlName,t);
	}
	
	/** (non-Javadoc)
	 * @see com.ssitcloud.common.dao.CommonDao#delete(String, Object)
	 */
	@Override
	public <T> int delete(String sqlName,T t){
			return sqlSessionTemplate.delete(sqlName,t);
	}
	@SuppressWarnings("unchecked")
	
	
	
	/**
	 * 分页插件
	 */
	@Override
	public <T extends DatagridPageEntity> T queryDatagridPage(T datagridPageEntity, String sqlId) {
		
		Map<String, Object> countMap = (Map<String, Object>) this
				.select(sqlId, datagridPageEntity); // 查询全部数据数量
		// 设置总条数
		datagridPageEntity.setTotal(this.getCount(countMap.get("total")));
		datagridPageEntity.setDoAount(false);
		// 设置实际数据内容
		datagridPageEntity.setRows(this.selectAll(sqlId, datagridPageEntity));
		return datagridPageEntity;
	}

	private int getCount(Object countsObj) {
		int count = 0;
		if (countsObj instanceof BigDecimal) {
			count = ((BigDecimal) countsObj).intValue();
		} else if (countsObj instanceof Long) {
			count = ((Long) countsObj).intValue();
		} else if (countsObj instanceof Integer) {
			count = ((Integer) countsObj).intValue();
		} else {
//			LogUtils.error("obj is error , the obj is :" + countsObj);
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> selectList(String string,
			Map<String, Object> m) {
		return sqlSessionTemplate.selectList(string, m);
	}

	@Override
	public int selectOne(String string, Map<String, Object> map) {
		return sqlSessionTemplate.selectOne(string, map);
	}

	@Override
	public int insertBySql(String sqlString) {
		return sqlSessionTemplate.insert("common.insertBySql",sqlString);
	}

	@Override
	public List<Map<String, Object>> selectBySql(String sql) {
		return sqlSessionTemplate.selectList("common.selectBySQL", sql);
	}
}
