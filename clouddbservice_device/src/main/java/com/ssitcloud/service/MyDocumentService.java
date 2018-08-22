package com.ssitcloud.service;

import java.util.List;
import com.ssitcloud.entity.MyDocumentEntity;

public interface MyDocumentService {
	
	/**
	 * 我的收藏夹MyDocumentEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param myDocumentEntity
	 * @return
	 */
	public abstract int insertMyDocument(MyDocumentEntity myDocumentEntity);
	
	/**
	 * 我的收藏夹MyDocumentEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param myDocumentEntity
	 * @return
	 */
	public abstract int updateMyDocument(MyDocumentEntity myDocumentEntity);
	
	/**
	 * 我的收藏夹MyDocumentEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param myDocumentEntity
	 * @return
	 */
	public abstract int deleteMyDocument(MyDocumentEntity myDocumentEntity);
	
	/**
	 * 我的收藏夹MyDocumentEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param myDocumentEntity
	 * @return
	 */
	public abstract MyDocumentEntity queryOneMyDocument(MyDocumentEntity myDocumentEntity);
	
	/**
	 * 我的收藏夹MyDocumentEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param myDocumentEntity
	 * @return
	 */
	public abstract List<MyDocumentEntity> queryMyDocuments(MyDocumentEntity myDocumentEntity);

}
