package com.ssitcloud.dbauth.service;

import java.util.List;

import com.ssitcloud.dbauth.entity.LibraryInfoEntity;

/**
 * <p>2016年4月5日 上午11:31:19
 * @author hjc
 *
 */
public interface LibraryInfoService {
	
	/**
	 * 新增一条图书馆元组信息
	 * 
	 * <p>2016年4月5日 下午5:40:38
	 * <p>create by hjc
	 * @param libraryInfoEntity 图书馆元组信息实体类
	 * @return 数据库操作结果
	 */
	public abstract int addLibraryInfo(LibraryInfoEntity libraryInfoEntity);
	
	
	/**
	 * 批量新增图书馆信息
	 *
	 * <p>2016年4月21日 下午4:30:17
	 * <p>create by hjc
	 * @param libraryInfoEntities
	 * @return
	 */
	public abstract Boolean addLibraryInfoList(List<LibraryInfoEntity> libraryInfoEntities);

    List<LibraryInfoEntity> selectByParam(LibraryInfoEntity param);
}
