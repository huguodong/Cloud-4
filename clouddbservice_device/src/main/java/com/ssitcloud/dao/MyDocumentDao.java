package com.ssitcloud.dao;

import java.util.List;
import com.ssitcloud.entity.MyDocumentEntity;

public interface MyDocumentDao {
	/**
	 * 我的收藏夹MyDocumentEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param myDocumentEntity
	 * @return
	 */
	public abstract int insertMyDocument(MyDocumentEntity myDocumentEntity);
	
	/**
	 * 我的收藏夹MyDocumentEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param myDocumentEntity
	 * @return
	 */
	public abstract int updateMyDocument(MyDocumentEntity myDocumentEntity);
	
	/**
	 * 我的收藏夹MyDocumentEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param myDocumentEntity
	 * @return
	 */
	public abstract int deleteMyDocument(MyDocumentEntity myDocumentEntity);
	
	/**
	 * 我的收藏夹MyDocumentEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param myDocumentEntity
	 * @return
	 */
	public abstract MyDocumentEntity queryOneMyDocument(MyDocumentEntity myDocumentEntity);
	
	/**
	 * 我的收藏夹MyDocumentEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param myDocumentEntity
	 * @return
	 */
	public abstract List<MyDocumentEntity> queryMyDocuments(MyDocumentEntity myDocumentEntity);

}
