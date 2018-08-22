package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.RelStatisticsGroupDao;
import com.ssitcloud.entity.RelStatisticsGroupEntity;

/**
 * 统计模板与模板组对应关系
 *
 * <p>2017年2月10日 下午2:19:35  
 * @author hjc 
 *
 */
@Repository
public class RelStatisticsGroupDaoImpl extends CommonDaoImpl implements RelStatisticsGroupDao {
	@Override
	public int insertRelStatisticsGroup(RelStatisticsGroupEntity relStatisticsGroupEntity) {
		return this.sqlSessionTemplate.insert("rel_statistics_group.insertRelStatisticsGroup", relStatisticsGroupEntity);
	}

	@Override
	public int updateRelStatisticsGroup(RelStatisticsGroupEntity relStatisticsGroupEntity) {
		return this.sqlSessionTemplate.update("rel_statistics_group.updateRelStatisticsGroup", relStatisticsGroupEntity);
	}

	@Override
	public int deleteRelStatisticsGroup(RelStatisticsGroupEntity relStatisticsGroupEntity) {
		return this.sqlSessionTemplate.delete("rel_statistics_group.deleteRelStatisticsGroup", relStatisticsGroupEntity);
	}

	@Override
	public RelStatisticsGroupEntity queryOneRelStatisticsGroup(
			RelStatisticsGroupEntity relStatisticsGroupEntity) {
		return this.select("rel_statistics_group.selectRelStatisticsGroup", relStatisticsGroupEntity);
	}

	@Override
	public List<RelStatisticsGroupEntity> queryRelStatisticsGroups(
			RelStatisticsGroupEntity relStatisticsGroupEntity) {
		return this.selectAll("rel_statistics_group.selectRelStatisticsGroups", relStatisticsGroupEntity);
	}
}
