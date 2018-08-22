package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.RecommendDao;
import com.ssitcloud.dbstatistics.entity.BookRankRolePageEntity;
import com.ssitcloud.statistics.entity.BookRankRoleEntity;

@Repository
public class RecommendDaoImpl extends CommonDaoImpl implements RecommendDao {

	@Override
	public int addRankRole(BookRankRoleEntity entity) {
		return this.sqlSessionTemplate.insert("recommend.addRankRole", entity);
	}

	@Override
	public int updateRankRole(BookRankRoleEntity entity) {
		return this.sqlSessionTemplate.update("recommend.updateRankRole", entity);

	}

	@Override
	public int deleteRankRole(List<Integer> role_idxs) {
		return this.sqlSessionTemplate.delete("recommend.deleteRankRole", role_idxs);

	}

	@Override
	public BookRankRoleEntity findRankRoleByIdx(BookRankRoleEntity entity) {
		return this.sqlSessionTemplate.selectOne("recommend.findRankRoleByIdx", entity);

	}
	
	@Override
	public BookRankRoleEntity findRankRoleByParam(BookRankRoleEntity entity) {
		return this.sqlSessionTemplate.selectOne("recommend.findRankRoleByParam", entity);

	}

	@Override
	public BookRankRolePageEntity findRankRoleList(BookRankRolePageEntity entity) {
		BookRankRolePageEntity roleEntity = this.sqlSessionTemplate.selectOne("recommend.findRankRoleList", entity);
		entity.setTotal(roleEntity == null ? 0 :roleEntity.getTotal());
		entity.setDoAount(false);
		List<BookRankRolePageEntity> entities = this.sqlSessionTemplate.selectList("recommend.findRankRoleList", entity);
		entity.setRows(entities);
		return entity;
	}

}
