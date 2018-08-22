package com.ssitcloud.dao;

import java.util.List;
import com.ssitcloud.entity.FeedbackReplyEntity;

public interface FeedbackReplyDao {
	/**
	 * APP意见反馈回复FeedbackReplyEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param feedbackReplyEntity
	 * @return
	 */
	public abstract int insertFeedbackReply(FeedbackReplyEntity feedbackReplyEntity);
	
	/**
	 * APP意见反馈回复FeedbackReplyEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param feedbackReplyEntity
	 * @return
	 */
	public abstract int updateFeedbackReply(FeedbackReplyEntity feedbackReplyEntity);
	
	/**
	 * APP意见反馈回复FeedbackReplyEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param feedbackReplyEntity
	 * @return
	 */
	public abstract int deleteFeedbackReply(FeedbackReplyEntity feedbackReplyEntity);
	
	/**
	 * APP意见反馈回复FeedbackReplyEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param feedbackReplyEntity
	 * @return
	 */
	public abstract FeedbackReplyEntity queryOneFeedbackReply(FeedbackReplyEntity feedbackReplyEntity);
	
	/**
	 * APP意见反馈回复FeedbackReplyEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param feedbackReplyEntity
	 * @return
	 */
	public abstract List<FeedbackReplyEntity> queryFeedbackReplys(FeedbackReplyEntity feedbackReplyEntity);

}
