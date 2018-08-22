package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.FeedbackReplyDao;
import com.ssitcloud.entity.FeedbackReplyEntity;
import com.ssitcloud.service.FeedbackReplyService;


@Service
public class FeedbackReplyServiceImpl implements FeedbackReplyService {
	@Resource
	private FeedbackReplyDao feedbackReplyDao;

	@Override
	public int insertFeedbackReply(
			FeedbackReplyEntity feedbackReplyEntity) {
		return feedbackReplyDao.insertFeedbackReply(feedbackReplyEntity);
	}

	@Override
	public int updateFeedbackReply(
			FeedbackReplyEntity feedbackReplyEntity) {
		return feedbackReplyDao.updateFeedbackReply(feedbackReplyEntity);
	}

	@Override
	public int deleteFeedbackReply(
			FeedbackReplyEntity feedbackReplyEntity) {
		return feedbackReplyDao.deleteFeedbackReply(feedbackReplyEntity);
	}

	@Override
	public FeedbackReplyEntity queryOneFeedbackReply(
			FeedbackReplyEntity feedbackReplyEntity) {
		return feedbackReplyDao.queryOneFeedbackReply(feedbackReplyEntity);
			
	}

	@Override
	public List<FeedbackReplyEntity> queryFeedbackReplys(
			FeedbackReplyEntity feedbackReplyEntity) {
		return feedbackReplyDao.queryFeedbackReplys(feedbackReplyEntity);
		
	}

	

}
