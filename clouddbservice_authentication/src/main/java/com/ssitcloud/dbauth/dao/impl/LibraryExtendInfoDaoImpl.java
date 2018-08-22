package com.ssitcloud.dbauth.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.LibraryExtendInfoDao;
import com.ssitcloud.dbauth.entity.LibraryExtendInfoEntity;



/**
 * <p>2017年4月26日 下午2:02
 * @author shuangjunjie
 *
 */
@Repository
public class LibraryExtendInfoDaoImpl extends CommonDaoImpl implements LibraryExtendInfoDao {

	@Override
	public LibraryExtendInfoEntity selRegionIdxByLibIdx(
			LibraryExtendInfoEntity libraryExtendInfoEntity) {
		return this.sqlSessionTemplate.selectOne("libraryExtendInfo.selRegionIdxByLibIdx", libraryExtendInfoEntity);
	}

	@Override
	public List<LibraryExtendInfoEntity> selRegionIdxsByLibIdxs(Map map) {
		return this.sqlSessionTemplate.selectList("libraryExtendInfo.selRegionIdxsByLibIdxs", map);
	}
	
}
