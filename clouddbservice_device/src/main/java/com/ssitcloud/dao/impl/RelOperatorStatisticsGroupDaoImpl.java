package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.RelOperatorStatisticsGroupDao;
import com.ssitcloud.entity.RelOperatorStatisticsGroupEntity;

/**
 * 操作员组与模板组关联表
 *
 * <p>2017年2月10日 下午2:18:52  
 * @author hjc 
 *
 */
@Repository
public class RelOperatorStatisticsGroupDaoImpl extends CommonDaoImpl implements RelOperatorStatisticsGroupDao {
	@Override
	public int insertRelOperatorStatisticsGroup(RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity) {
		return this.sqlSessionTemplate.insert("rel_operator_statistics_group.insertRelOperatorStatisticsGroup", relOperatorStatisticsGroupEntity);
	}

	@Override
	public int updateRelOperatorStatisticsGroup(RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity) {
		return this.sqlSessionTemplate.update("rel_operator_statistics_group.updateRelOperatorStatisticsGroup", relOperatorStatisticsGroupEntity);
	}

	@Override
	public int deleteRelOperatorStatisticsGroup(RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity) {
		return this.sqlSessionTemplate.delete("rel_operator_statistics_group.deleteRelOperatorStatisticsGroup", relOperatorStatisticsGroupEntity);
	}

	@Override
	public RelOperatorStatisticsGroupEntity queryOneRelOperatorStatisticsGroup(
			RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity) {
		return this.select("rel_operator_statistics_group.selectRelOperatorStatisticsGroup", relOperatorStatisticsGroupEntity);
	}

	@Override
	public List<RelOperatorStatisticsGroupEntity> queryRelOperatorStatisticsGroups(
			RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity) {
		return this.selectAll("rel_operator_statistics_group.selectRelOperatorStatisticsGroups", relOperatorStatisticsGroupEntity);
	}
}
