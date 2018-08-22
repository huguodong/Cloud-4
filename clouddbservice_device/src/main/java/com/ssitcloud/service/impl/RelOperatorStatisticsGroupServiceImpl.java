package com.ssitcloud.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ssitcloud.dao.RelOperatorStatisticsGroupDao;
import com.ssitcloud.entity.RelOperatorStatisticsGroupEntity;
import com.ssitcloud.service.RelOperatorStatisticsGroupService;

/**
 * 操作员组与模板组关联表
 *
 * <p>2017年2月10日 下午2:18:52  
 * @author hjc 
 *
 */
@Service
public class RelOperatorStatisticsGroupServiceImpl implements RelOperatorStatisticsGroupService{
	@Resource
	private RelOperatorStatisticsGroupDao relOperatorStatisticsGroupDao;

	@Override
	public int insertRelOperatorStatisticsGroup(
			RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity) {
		return relOperatorStatisticsGroupDao.insertRelOperatorStatisticsGroup(relOperatorStatisticsGroupEntity);
	}

	@Override
	public int updateRelOperatorStatisticsGroup(
			RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity) {
		return relOperatorStatisticsGroupDao.updateRelOperatorStatisticsGroup(relOperatorStatisticsGroupEntity);
	}

	@Override
	public int deleteRelOperatorStatisticsGroup(
			RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity) {
		return relOperatorStatisticsGroupDao.deleteRelOperatorStatisticsGroup(relOperatorStatisticsGroupEntity);
	}

	@Override
	public RelOperatorStatisticsGroupEntity queryOneRelOperatorStatisticsGroup(
			RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity) {
		return relOperatorStatisticsGroupDao.queryOneRelOperatorStatisticsGroup(relOperatorStatisticsGroupEntity);
			
	}

	@Override
	public List<RelOperatorStatisticsGroupEntity> queryRelOperatorStatisticsGroups(
			RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity) {
		return relOperatorStatisticsGroupDao.queryRelOperatorStatisticsGroups(relOperatorStatisticsGroupEntity);
		
	}
}
