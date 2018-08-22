package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.ThirdPartyServiceDao;
import com.ssitcloud.devmgmt.entity.ThirdPartyServiceEntity;
import com.ssitcloud.entity.DisplayInfoEntity;
import com.ssitcloud.entity.page.DisplayInfoPageEntity;
import com.ssitcloud.entity.page.ThirdPartyServicePageEntity;
@Repository
public class ThirdPartyServiceDaoImpl extends CommonDaoImpl implements  ThirdPartyServiceDao{

	@Override
	public List<ThirdPartyServiceEntity> queryThirdPartyServiceByParams(ThirdPartyServiceEntity entity) {
		return this.selectList("thirdPartyService.queryThirdPartyServiceByParams", entity);
	}

	@Override
	public int deleteThirdPartyService(int[] service_idxs) {
		return this.delete("thirdPartyService.deleteThirdPartyService", service_idxs);
	}

	@Override
	public ThirdPartyServicePageEntity queryThirdPartyServiceByPage(ThirdPartyServicePageEntity entity) {
		ThirdPartyServicePageEntity thirdPartyServicePageEntity = getSqlSession().selectOne("thirdPartyService.queryThirdPartyServiceByPage", entity);
		entity.setTotal(thirdPartyServicePageEntity == null ? 0 :thirdPartyServicePageEntity.getTotal());
		entity.setDoAount(false);
		List<ThirdPartyServiceEntity> entities = getSqlSession().selectList("thirdPartyService.queryThirdPartyServiceByPage", entity);
		entity.setRows(entities);
		return entity;
	}

	@Override
	public int editThirdPartyService(ThirdPartyServiceEntity entity) {
		return this.update("thirdPartyService.editThirdPartyService", entity);
	}

	@Override
	public int addThirdPartyService(ThirdPartyServiceEntity entity) {
		return this.getSqlSession().insert("thirdPartyService.addThirdPartyService", entity);
	}

	@Override
	public int deleteDisplayInfo(int[] display_info_idxs) {
		return this.delete("displayInfo.deleteDisplayInfo", display_info_idxs);
	}

	@Override
	public DisplayInfoPageEntity queryDisplayInfoList(DisplayInfoPageEntity entity) {
		DisplayInfoPageEntity displayInfoPageEntity = getSqlSession().selectOne("displayInfo.queryDisplayInfoList", entity);
		entity.setTotal(displayInfoPageEntity == null ? 0 :displayInfoPageEntity.getTotal());
		entity.setDoAount(false);
		List<DisplayInfoPageEntity> entities = getSqlSession().selectList("displayInfo.queryDisplayInfoList", entity);
		entity.setRows(entities);
		return entity;
	}

	@Override
	public DisplayInfoEntity queryDisplayInfo(DisplayInfoEntity entity) {
		return this.getSqlSession().selectOne("displayInfo.queryDisplayInfo", entity);
	}

	@Override
	public int addDisplayInfo(DisplayInfoEntity entity) {
		return this.getSqlSession().insert("displayInfo.addDisplayInfo", entity);
	}

	@Override
	public int editDisplayInfo(DisplayInfoEntity entity) {
		return this.update("displayInfo.editDisplayInfo", entity);
	}

}
