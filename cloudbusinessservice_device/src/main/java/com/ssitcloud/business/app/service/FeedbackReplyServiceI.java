package com.ssitcloud.business.app.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.FeedbackReplyEntity;

/**
 * FeedBackReply服务接口
 * @author LXP
 * @version 创建时间：2017年2月23日 上午10:29:01
 */
public interface FeedbackReplyServiceI {
	
	/**
	 * 我的收藏夹FeedbackReplyEntity插入
	 * @param FeedbackReplyEntity的json字符串
	 * @return 远程服务器返回的对象 
	 */
	ResultEntity insertFeedbackReply(String feedbackReplyEntityJson);
	
	/**
	 * 我的收藏夹FeedbackReplyEntity修改
	 * @param FeedbackReplyEntity的json字符串,实体必须设置主键
	 * @return 远程服务器返回的对象 
	 */
	ResultEntity updateFeedbackReply(String feedbackReplyEntityJson);
	
	/**
	 * 我的收藏夹FeedbackReplyEntity删除
	 * @param FeedbackReplyEntityPk 实体主键
	 * @return 是否删除成功信息
	 */
	boolean deleteFeedbackReply(Integer feedbackReplyEntityPk);
	
	/**
	 * 我的收藏夹FeedbackReplyEntity单个查询
	 * @param FeedbackReplyEntity
	 * @return 查询实体主键
	 */
	FeedbackReplyEntity queryOneFeedbackReply(Integer feedbackReplyEntityPk);
	
	/**
	 * 我的收藏夹FeedbackReplyEntity多个查询
	 * @param FeedbackReplyQueryEntity实体的json字符串
	 * @return 查询实体的列表
	 */
	List<FeedbackReplyEntity> queryFeedbackReplyReplys(String feedbackReplyQueryEntityJson);
}
