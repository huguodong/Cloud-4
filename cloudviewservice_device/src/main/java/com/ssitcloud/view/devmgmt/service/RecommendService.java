package com.ssitcloud.view.devmgmt.service;



import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 图书推荐
 * @author bob
 *
 */
public interface RecommendService {
	ResultEntity editRankRole(Map<String, String> map);
	ResultEntity deleteRankRole(Map<String, String> map);
	ResultEntity findRankRoleByIdx(Map<String, String> map);
	ResultEntity findRankRoleList(Map<String, String> map);
	
}
