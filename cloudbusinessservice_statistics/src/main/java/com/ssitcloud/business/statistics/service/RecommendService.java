package com.ssitcloud.business.statistics.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 图书推荐
 * 
 * @author bob
 * 
 */
public interface RecommendService {
	/** 推荐服务  **/
	ResultEntity recommend(Map<String, String> param);

	/** 图书排行榜推荐规则 **/
	ResultEntity editRankRole(Map<String, String> map);

	ResultEntity deleteRankRole(Map<String, String> map);

	ResultEntity findRankRoleByIdx(Map<String, String> map);

	ResultEntity findRankRoleList(Map<String, String> map);

}
