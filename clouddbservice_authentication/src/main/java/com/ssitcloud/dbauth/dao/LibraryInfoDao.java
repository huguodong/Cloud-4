package com.ssitcloud.dbauth.dao;

import java.util.List;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.LibraryInfoEntity;
import com.ssitcloud.dbauth.entity.LibraryMetatypeInfoEntity;

/**
 * <p>2016年4月5日 上午11:21:35
 * @author hjc
 *
 */
public interface LibraryInfoDao extends CommonDao {
	
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
	 * 根据id查询图书馆具体信息
	 * @comment
	 * @param library_idx
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	public abstract List<LibraryMetatypeInfoEntity> selectLibraryInfoByidx(int library_idx);
	
	/**
	 * 根据id删除图书馆具体信息
	 * @comment
	 * @param library_idx
	 * @return
	 * @data 2016年5月30日`
	 * @author hwl
	 */
	int deleteLibInfo(int library_idx);

	List<LibraryInfoEntity> selectByParam(LibraryInfoEntity param);
}
