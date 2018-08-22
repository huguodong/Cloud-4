package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.AppSettingDao;
import com.ssitcloud.entity.AppSettingEntity;
import com.ssitcloud.entity.MenuAppSettingEntity;
import com.ssitcloud.entity.page.AppSettingPage2Entity;

@Repository
public class AppSettingDaoImpl extends CommonDaoImpl implements
		AppSettingDao {

	@Override
	public int insertAppSetting(AppSettingEntity appSettingEntity) {
		return this.sqlSessionTemplate.insert("app_setting.insertAppSetting", appSettingEntity);
	}

	@Override
	public int updateAppSetting(AppSettingEntity appSettingEntity) {
		return this.sqlSessionTemplate.update("app_setting.updateAppSetting", appSettingEntity);
	}

	@Override
	public int deleteAppSetting(AppSettingEntity appSettingEntity) {
		return this.sqlSessionTemplate.delete("app_setting.deleteAppSetting", appSettingEntity);
	}

	@Override
	public AppSettingEntity queryOneAppSetting(
			AppSettingEntity appSettingEntity) {
		return this.select("app_setting.selectAppSetting", appSettingEntity);
	}

	@Override
	public List<AppSettingEntity> queryAppSettings(
			AppSettingEntity appSettingEntity) {
		return this.selectAll("app_setting.selectAppSettings", appSettingEntity);
	}

	@Override
	public AppSettingPage2Entity selectAppSettingByPage(
			AppSettingPage2Entity appSettingPage2Entity) {
		if(null == appSettingPage2Entity) appSettingPage2Entity =new AppSettingPage2Entity();
		AppSettingPage2Entity total = getSqlSession().selectOne("app_setting.selectAppSettingByPage", appSettingPage2Entity);
		appSettingPage2Entity.setDoAount(false);
		appSettingPage2Entity.setTotal(total.getTotal());
		appSettingPage2Entity.setRows(getSqlSession().selectList("app_setting.selectAppSettingByPage", appSettingPage2Entity));
		return appSettingPage2Entity;
	}

	@Override
	public int deleteAppSettingBylibidx(AppSettingEntity appSettingEntity) {
	   return this.sqlSessionTemplate.delete("app_setting.deleteAppSettingBylib_idx", appSettingEntity);
	}

	@Override
	public List<Map> selectMenuNamesByServiceIds(Map<String, Object> map) {
		
		return this.sqlSessionTemplate.selectList("app_setting.selectMenuNamesByServiceIds", map);
	}

}
