package com.ssitcloud.dblib.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.NavigationDao;
@Repository
public class NavigationDaoImpl extends CommonDaoImpl implements NavigationDao {

	@Override
	public int queryBookItemTotal(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectOne("navigation.queryBookItemTotal",param);
	}

	@Override
	public int queryBookShelfLayerTotal(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("navigation.queryBookShelfLayerTotal",param);
	}

	@Override
	public int queryBookShelfTotal(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("navigation.queryBookShelfTotal",param);
	}

}
