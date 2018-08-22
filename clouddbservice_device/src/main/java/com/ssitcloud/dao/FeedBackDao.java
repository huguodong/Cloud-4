package com.ssitcloud.dao;

import java.util.List;
import com.ssitcloud.entity.FeedBackEntity;

public interface FeedBackDao {
	/**
	 * APP中意见反馈FeedBackEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param feedBackEntity
	 * @return
	 */
	public abstract int insertFeedBack(FeedBackEntity feedBackEntity);
	
	/**
	 * APP中意见反馈FeedBackEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param feedBackEntity
	 * @return
	 */
	public abstract int updateFeedBack(FeedBackEntity feedBackEntity);
	
	/**
	 * APP中意见反馈FeedBackEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param feedBackEntity
	 * @return
	 */
	public abstract int deleteFeedBack(FeedBackEntity feedBackEntity);
	
	/**
	 * APP中意见反馈FeedBackEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param feedBackEntity
	 * @return
	 */
	public abstract FeedBackEntity queryOneFeedBack(FeedBackEntity feedBackEntity);
	
	/**
	 * APP中意见反馈FeedBackEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param feedBackEntity
	 * @return
	 */
	public abstract List<FeedBackEntity> queryFeedBacks(FeedBackEntity feedBackEntity);

}
