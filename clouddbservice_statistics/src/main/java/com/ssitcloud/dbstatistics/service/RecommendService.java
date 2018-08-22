package com.ssitcloud.dbstatistics.service;



import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbstatistics.entity.BookRankRolePageEntity;
import com.ssitcloud.statistics.entity.BookRankRoleEntity;

/**
 * 图书推荐
 * @author bob
 *
 */
public interface RecommendService {
	ResultEntity addRankRole(BookRankRoleEntity entity);
	ResultEntity updateRankRole(BookRankRoleEntity entity);
	ResultEntity deleteRankRole(String req);
	ResultEntity findRankRoleByIdx(BookRankRoleEntity entity);
	ResultEntity findRankRoleByParam(BookRankRoleEntity entity);
	ResultEntity findRankRoleList(BookRankRolePageEntity entity);
	
}
