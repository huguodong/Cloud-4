package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.RelOperatorGroupDao;
import com.ssitcloud.entity.RelOperatorGroupEntity;

/**
 * 
 * @comment 实现操作员分组关联表的增删改查。表名：rel_operator_group
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Repository
public class RelOperatorGroupDaoImpl extends CommonDaoImpl implements
		RelOperatorGroupDao {

	@Override
	public int insert(RelOperatorGroupEntity relOperatorGroupEntity) {
		return this.sqlSessionTemplate.insert("reloperatorgroup.insert",
				relOperatorGroupEntity);
	}

	@Override
	public int update(RelOperatorGroupEntity relOperatorGroupEntity) {
		return this.sqlSessionTemplate.update("reloperatorgroup.update",
				relOperatorGroupEntity);
	}

	@Override
	public int delete(RelOperatorGroupEntity relOperatorGroupEntity) {
		return this.sqlSessionTemplate.delete("reloperatorgroup.delete",
				relOperatorGroupEntity);
	}

	@Override
	public List<RelOperatorGroupEntity> selectByid(
			RelOperatorGroupEntity relOperatorGroupEntity) {
		return this.sqlSessionTemplate.selectList(
				"reloperatorgroup.selectByidx", relOperatorGroupEntity);
	}

	@Override
	public List<RelOperatorGroupEntity> selectAll() {
		return this.sqlSessionTemplate.selectList("reloperatorgroup.selectAll");
	}

	@Override
	public RelOperatorGroupEntity queryOperatorGroupByOperIdx(RelOperatorGroupEntity groupEntity) {
		return this.sqlSessionTemplate.selectOne("reloperatorgroup.queryOperatorGroupByOperIdx",groupEntity);
	}

	@Override
	public int deleteRelByOperatorIdx(RelOperatorGroupEntity relOperatorGroupEntity) {
		return this.sqlSessionTemplate.delete("reloperatorgroup.deleteRelByOperatorIdx",relOperatorGroupEntity);
	}

	@Override
	public int deleteRelOperatorGroupByOperatorIdxs(List<Integer> operatorIdxs) {
		return this.sqlSessionTemplate.delete("reloperatorgroup.deleteRelOperatorGroupByOperatorIdxs",operatorIdxs);
	}

	
	
}
