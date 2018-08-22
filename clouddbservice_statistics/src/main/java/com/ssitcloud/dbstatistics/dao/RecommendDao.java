package com.ssitcloud.dbstatistics.dao;


import java.util.List;

import com.ssitcloud.dbstatistics.entity.BookRankRolePageEntity;
import com.ssitcloud.statistics.entity.BookRankRoleEntity;

public interface RecommendDao {

	int addRankRole(BookRankRoleEntity entity);

	int updateRankRole(BookRankRoleEntity entity);

	int deleteRankRole(List<Integer> role_idxs);

	BookRankRoleEntity findRankRoleByIdx(BookRankRoleEntity entity);
	
	BookRankRoleEntity findRankRoleByParam(BookRankRoleEntity entity);

	BookRankRolePageEntity findRankRoleList(BookRankRolePageEntity entity);

}
