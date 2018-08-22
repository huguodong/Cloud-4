package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.FeedBackDao;
import com.ssitcloud.entity.FeedBackEntity;

@Repository
public class FeedBackDaoImpl extends CommonDaoImpl implements
		FeedBackDao {

	@Override
	public int insertFeedBack(FeedBackEntity feedBackEntity) {
		return this.sqlSessionTemplate.insert("feedback.insertFeedBack", feedBackEntity);
	}

	@Override
	public int updateFeedBack(FeedBackEntity feedBackEntity) {
		return this.sqlSessionTemplate.update("feedback.updateFeedBack", feedBackEntity);
	}

	@Override
	public int deleteFeedBack(FeedBackEntity feedBackEntity) {
		return this.sqlSessionTemplate.delete("feedback.deleteFeedBack", feedBackEntity);
	}

	@Override
	public FeedBackEntity queryOneFeedBack(
			FeedBackEntity feedBackEntity) {
		return this.select("feedback.selectFeedBack", feedBackEntity);
	}

	@Override
	public List<FeedBackEntity> queryFeedBacks(
			FeedBackEntity feedBackEntity) {
		return this.selectAll("feedback.selectFeedBacks", feedBackEntity);
	}

}
