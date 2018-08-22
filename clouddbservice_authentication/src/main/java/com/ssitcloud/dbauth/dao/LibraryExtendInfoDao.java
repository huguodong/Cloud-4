package com.ssitcloud.dbauth.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.LibraryExtendInfoEntity;

/**
 * <p>2017年4月26日 下午2:00
 * @author shuangjunjie
 *
 */
public interface LibraryExtendInfoDao extends CommonDao {
	
	public abstract LibraryExtendInfoEntity selRegionIdxByLibIdx(LibraryExtendInfoEntity libraryExtendInfoEntity);
	
	public abstract List<LibraryExtendInfoEntity> selRegionIdxsByLibIdxs(Map map);
}
