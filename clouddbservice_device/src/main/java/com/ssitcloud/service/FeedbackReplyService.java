package com.ssitcloud.service;

import java.util.List;
import com.ssitcloud.entity.FeedbackReplyEntity;

public interface FeedbackReplyService {
	
	/**
	 * APP意见反馈回复FeedbackReplyEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param feedbackReplyEntity
	 * @return
	 */
	public abstract int insertFeedbackReply(FeedbackReplyEntity feedbackReplyEntity);
	
	/**
	 * APP意见反馈回复FeedbackReplyEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param feedbackReplyEntity
	 * @return
	 */
	public abstract int updateFeedbackReply(FeedbackReplyEntity feedbackReplyEntity);
	
	/**
	 * APP意见反馈回复FeedbackReplyEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param feedbackReplyEntity
	 * @return
	 */
	public abstract int deleteFeedbackReply(FeedbackReplyEntity feedbackReplyEntity);
	
	/**
	 * APP意见反馈回复FeedbackReplyEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param feedbackReplyEntity
	 * @return
	 */
	public abstract FeedbackReplyEntity queryOneFeedbackReply(FeedbackReplyEntity feedbackReplyEntity);
	
	/**
	 * APP意见反馈回复FeedbackReplyEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param feedbackReplyEntity
	 * @return
	 */
	public abstract List<FeedbackReplyEntity> queryFeedbackReplys(FeedbackReplyEntity feedbackReplyEntity);

}
