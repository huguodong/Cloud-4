package com.ssitcloud.dbauth.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dbauth.entity.LibraryExtendInfoEntity;

/**
 * <p>2017年4月26日 下午1:56
 * @author shuangjunjie
 *
 */
public interface LibraryExtendInfoService {
	
	/**
	 * 根据library_idx查出region_idx
	 * add by shuangjunjie
	 * 2017年4月26日
	 * @param req
	 * @return
	 */
	public LibraryExtendInfoEntity selRegionIdxByLibIdx(LibraryExtendInfoEntity libraryExtendInfoEntity);
	
	/**
	 * 根据library_idxs查出对应多个实体
	 * add by shuangjunjie
	 * 2017年4月26日
	 * @param map
	 * @return
	 */
	public List<LibraryExtendInfoEntity> selRegionIdxsByLibIdxs(Map map);

}
