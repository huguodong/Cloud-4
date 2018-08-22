package com.ssitcloud.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.StatisticsConfigDao;
import com.ssitcloud.entity.StatisticsConfigEntity;

/**
 * 统计查询模板详情
 *
 * <p>2017年2月10日 下午2:20:45  
 * @author hjc 
 *
 */
@Repository
public class StatisticsConfigDaoImpl extends CommonDaoImpl implements StatisticsConfigDao {
	@Override
	public int insertStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity) {
		return this.sqlSessionTemplate.insert("statistics_config.insertStatisticsConfig", statisticsConfigEntity);
	}

	@Override
	public int updateStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity) {
		return this.sqlSessionTemplate.update("statistics_config.updateStatisticsConfig", statisticsConfigEntity);
	}

	@Override
	public int deleteStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity) {
		return this.sqlSessionTemplate.delete("statistics_config.deleteStatisticsConfig", statisticsConfigEntity);
	}

	@Override
	public StatisticsConfigEntity queryOneStatisticsConfig(
			StatisticsConfigEntity statisticsConfigEntity) {
		return this.select("statistics_config.selectStatisticsConfig", statisticsConfigEntity);
	}

	@Override
	public List<StatisticsConfigEntity> queryStatisticsConfigs(
			StatisticsConfigEntity statisticsConfigEntity) {
		return this.selectAll("statistics_config.selectStatisticsConfigs", statisticsConfigEntity);
	}
}
