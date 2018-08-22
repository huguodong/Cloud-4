package com.ssitcloud.business.app.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.MyDocumentEntity;

/**
 * MyDocument操作服务
 * @author LXP
 * @version 创建时间：2017年2月22日 下午2:51:44
 */
public interface MyDocumentServiceI {
	/**
	 * 我的收藏夹MyDocumentEntity插入
	 * @param myDocumentEntity的json字符串
	 * @return 是否插入成功
	 */
	ResultEntity insertMyDocument(String myDocumentEntityJson);
	
	/**
	 * 我的收藏夹MyDocumentEntity修改
	 * @param myDocumentEntity的json字符串,实体必须设置主键
	 * @return 是否更新成功信息
	 */
	ResultEntity updateMyDocument(String myDocumentEntityJson);
	
	/**
	 * 我的收藏夹MyDocumentEntity删除
	 * @param myDocumentEntityPk 实体主键
	 * @return 是否删除成功信息
	 */
	boolean deleteMyDocument(Integer myDocumentEntityPk);
	
	/**
	 * 我的收藏夹MyDocumentEntity单个查询
	 * @param myDocumentEntity
	 * @return 查询实体
	 */
	MyDocumentEntity queryOneMyDocument(Integer myDocumentEntityPk);
	
	/**
	 * 我的收藏夹MyDocumentEntity多个查询
	 * @param MyDocumentQueryEntity实体的json字符串
	 * @return 查询实体的列表
	 */
	List<MyDocumentEntity> queryMyDocuments(String myDocumentQueryEntityJson);

}
