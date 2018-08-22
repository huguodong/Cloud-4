package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.StatisticsGroupDao;
import com.ssitcloud.entity.StatisticsGroupEntity;
import com.ssitcloud.entity.page.StatisticsGroupMgmtPageEntity;

/**
 * 模板组
 *
 * <p>2017年2月10日 下午2:21:49  
 * @author hjc 
 *
 */
@Repository
public class StatisticsGroupDaoImpl extends CommonDaoImpl implements StatisticsGroupDao {
	@Override
	public int insertStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity) {
		return this.sqlSessionTemplate.insert("statistics_group.insertStatisticsGroup", statisticsGroupEntity);
	}

	@Override
	public int updateStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity) {
		return this.sqlSessionTemplate.update("statistics_group.updateStatisticsGroup", statisticsGroupEntity);
	}

	@Override
	public int deleteStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity) {
		return this.sqlSessionTemplate.delete("statistics_group.deleteStatisticsGroup", statisticsGroupEntity);
	}

	@Override
	public StatisticsGroupEntity queryOneStatisticsGroup(
			StatisticsGroupEntity statisticsGroupEntity) {
		return this.select("statistics_group.selectStatisticsGroup", statisticsGroupEntity);
	}

	@Override
	public List<StatisticsGroupEntity> queryStatisticsGroups(
			StatisticsGroupEntity statisticsGroupEntity) {
		return this.selectAll("statistics_group.selectStatisticsGroups", statisticsGroupEntity);
	}

	@Override
	public StatisticsGroupMgmtPageEntity queryStatisticsGroupByPage(
			StatisticsGroupMgmtPageEntity sGroupMgmtPageEntity) {
		if(sGroupMgmtPageEntity==null)
			sGroupMgmtPageEntity = new StatisticsGroupMgmtPageEntity();
		StatisticsGroupMgmtPageEntity total = getSqlSession().selectOne("statistics_group.selectStatisticsGroupByPage", sGroupMgmtPageEntity);
		sGroupMgmtPageEntity.setDoAount(false);
		sGroupMgmtPageEntity.setTotal(total.getTotal());
		sGroupMgmtPageEntity.setRows(getSqlSession().selectList("statistics_group.selectStatisticsGroupByPage", sGroupMgmtPageEntity));
		return sGroupMgmtPageEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StatisticsGroupMgmtPageEntity querysGroupByPage(
			StatisticsGroupMgmtPageEntity sGroupMgmtPageEntity) {
		if(sGroupMgmtPageEntity==null)
			sGroupMgmtPageEntity = new StatisticsGroupMgmtPageEntity();
		StatisticsGroupMgmtPageEntity total = getSqlSession().selectOne("statistics_group.queryStatisticsGroupByPage", sGroupMgmtPageEntity);
		sGroupMgmtPageEntity.setDoAount(false);
		sGroupMgmtPageEntity.setTotal(total.getTotal());
		sGroupMgmtPageEntity.setRows(getSqlSession().selectList("statistics_group.queryStatisticsGroupByPage", sGroupMgmtPageEntity));
		return sGroupMgmtPageEntity;
	}
}
