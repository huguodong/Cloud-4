package com.ssitcloud.business.mobile.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.FeedbackEntity;

/**
 * FeedBack服务接口
 * @author LXP
 * @version 创建时间：2017年2月23日 上午10:29:01
 */
public interface FeedbackServiceI {
	
	/**
	 * 我的收藏夹FeedBackEntity插入
	 * @param FeedBackEntity的json字符串
	 * @return 远程服务器返回的对象 
	 */
	ResultEntity insertFeedback(String FeedBackEntityJson);
	
	/**
	 * 我的收藏夹FeedBackEntity修改
	 * @param FeedBackEntity的json字符串,实体必须设置主键
	 * @return 远程服务器返回的对象 
	 */
	ResultEntity updateFeedback(String FeedBackEntityJson);
	
	/**
	 * 我的收藏夹FeedBackEntity删除
	 * @param FeedBackEntityPk 实体主键
	 * @return 是否删除成功信息
	 */
	boolean deleteFeedback(Integer FeedBackEntityPk);
	
	/**
	 * 我的收藏夹FeedBackEntity单个查询
	 * @param FeedbackEntity
	 * @return 查询实体
	 */
	FeedbackEntity queryOneFeedback(Integer FeedBackEntityPk);
	
	/**
	 * 我的收藏夹FeedBackEntity多个查询
	 * @param FeedBackQueryEntity实体的json字符串
	 * @return 查询实体的列表
	 */
	List<FeedbackEntity> queryFeedbacks(String FeedBackQueryEntityJson);
}
