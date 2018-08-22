package com.ssitcloud.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.FeedbackReplyDao;
import com.ssitcloud.entity.FeedbackReplyEntity;

@Repository
public class FeedbackReplyDaoImpl extends CommonDaoImpl implements
		FeedbackReplyDao {

	@Override
	public int insertFeedbackReply(FeedbackReplyEntity feedbackReplyEntity) {
		return this.sqlSessionTemplate.insert("feedback_reply.insertFeedbackReply", feedbackReplyEntity);
	}

	@Override
	public int updateFeedbackReply(FeedbackReplyEntity feedbackReplyEntity) {
		return this.sqlSessionTemplate.update("feedback_reply.updateFeedbackReply", feedbackReplyEntity);
	}

	@Override
	public int deleteFeedbackReply(FeedbackReplyEntity feedbackReplyEntity) {
		return this.sqlSessionTemplate.delete("feedback_reply.deleteFeedbackReply", feedbackReplyEntity);
	}

	@Override
	public FeedbackReplyEntity queryOneFeedbackReply(
			FeedbackReplyEntity feedbackReplyEntity) {
		return this.select("feedback_reply.selectFeedbackReply", feedbackReplyEntity);
	}

	@Override
	public List<FeedbackReplyEntity> queryFeedbackReplys(
			FeedbackReplyEntity feedbackReplyEntity) {
		return this.selectAll("feedback_reply.selectFeedbackReplys", feedbackReplyEntity);
	}

}
