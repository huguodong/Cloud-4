package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.FeedBackDao;
import com.ssitcloud.entity.FeedBackEntity;
import com.ssitcloud.service.FeedBackService;


@Service
public class FeedBackServiceImpl implements FeedBackService {
	@Resource
	private FeedBackDao feedBackDao;

	@Override
	public int insertFeedBack(
			FeedBackEntity feedBackEntity) {
		return feedBackDao.insertFeedBack(feedBackEntity);
	}

	@Override
	public int updateFeedBack(
			FeedBackEntity feedBackEntity) {
		return feedBackDao.updateFeedBack(feedBackEntity);
	}

	@Override
	public int deleteFeedBack(
			FeedBackEntity feedBackEntity) {
		return feedBackDao.deleteFeedBack(feedBackEntity);
	}

	@Override
	public FeedBackEntity queryOneFeedBack(
			FeedBackEntity feedBackEntity) {
		return feedBackDao.queryOneFeedBack(feedBackEntity);
			
	}

	@Override
	public List<FeedBackEntity> queryFeedBacks(
			FeedBackEntity feedBackEntity) {
		return feedBackDao.queryFeedBacks(feedBackEntity);
		
	}

	

}
