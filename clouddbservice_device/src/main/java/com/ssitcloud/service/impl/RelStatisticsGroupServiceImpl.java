package com.ssitcloud.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ssitcloud.dao.RelStatisticsGroupDao;
import com.ssitcloud.entity.RelStatisticsGroupEntity;
import com.ssitcloud.service.RelStatisticsGroupService;

/**
 * 统计模板与模板组对应关系
 *
 * <p>2017年2月10日 下午2:19:35  
 * @author hjc 
 *
 */
@Service
public class RelStatisticsGroupServiceImpl implements RelStatisticsGroupService{
	@Resource
	private RelStatisticsGroupDao relStatisticsGroupDao;

	@Override
	public int insertRelStatisticsGroup(
			RelStatisticsGroupEntity relStatisticsGroupEntity) {
		return relStatisticsGroupDao.insertRelStatisticsGroup(relStatisticsGroupEntity);
	}

	@Override
	public int updateRelStatisticsGroup(
			RelStatisticsGroupEntity relStatisticsGroupEntity) {
		return relStatisticsGroupDao.updateRelStatisticsGroup(relStatisticsGroupEntity);
	}

	@Override
	public int deleteRelStatisticsGroup(
			RelStatisticsGroupEntity relStatisticsGroupEntity) {
		return relStatisticsGroupDao.deleteRelStatisticsGroup(relStatisticsGroupEntity);
	}

	@Override
	public RelStatisticsGroupEntity queryOneRelStatisticsGroup(
			RelStatisticsGroupEntity relStatisticsGroupEntity) {
		return relStatisticsGroupDao.queryOneRelStatisticsGroup(relStatisticsGroupEntity);
			
	}

	@Override
	public List<RelStatisticsGroupEntity> queryRelStatisticsGroups(
			RelStatisticsGroupEntity relStatisticsGroupEntity) {
		return relStatisticsGroupDao.queryRelStatisticsGroups(relStatisticsGroupEntity);
		
	}
}
