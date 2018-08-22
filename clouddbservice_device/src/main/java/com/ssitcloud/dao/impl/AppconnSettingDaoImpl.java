package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.AppconnSettingDao;
import com.ssitcloud.entity.AppconnMetadata;
import com.ssitcloud.entity.AppconnSettingEntity;

@Repository
public class AppconnSettingDaoImpl extends CommonDaoImpl implements AppconnSettingDao {

	@Override
	public int insertAppconnSetting(AppconnSettingEntity appconnSettingEntity) {
		return this.sqlSessionTemplate.insert("appconn_setting.insertAppconnSetting",appconnSettingEntity);
	}

	@Override
	public int updateAppconnSetting(AppconnSettingEntity appconnSettingEntity) {
		return this.sqlSessionTemplate.update("appconn_setting.updateAppconnSetting",appconnSettingEntity);
	}

	@Override
	public int deleteAppconnSetting(AppconnSettingEntity appconnSettingEntity) {
		return this.sqlSessionTemplate.delete("appconn_setting.deleteAppconnSetting",appconnSettingEntity);
	}

	@Override
	public List<AppconnSettingEntity> selectAppconnSetting(
			AppconnSettingEntity appconnSettingEntity) {
		return this.sqlSessionTemplate.selectList("appconn_setting.selectAppconnSetting",appconnSettingEntity);
	}

	@Override
	public AppconnSettingEntity selectAppconnSettingByPage(
			AppconnSettingEntity appconnSettingEntity) {
		if(null == appconnSettingEntity) appconnSettingEntity =new AppconnSettingEntity();
		AppconnSettingEntity total = getSqlSession().selectOne("appconn_setting.selectAppconnSettingByPage", appconnSettingEntity);
		appconnSettingEntity.setDoAount(false);
		appconnSettingEntity.setTotal(total.getTotal());
		appconnSettingEntity.setRows(getSqlSession().selectList("appconn_setting.selectAppconnSettingByPage", appconnSettingEntity));
		return appconnSettingEntity;
	}

	@Override
	public int deleteAppconnSettingBylib_idx(
			AppconnSettingEntity appconnSettingEntity) {
		return this.sqlSessionTemplate.delete("appconn_setting.deleteAppconnSettingBylib_idx",appconnSettingEntity);
	}

	@Override
	public List<Map<String, Object>> selectRealValue(AppconnSettingEntity appconnSettingEntity) {
		return this.sqlSessionTemplate.selectList("selectRealValue", appconnSettingEntity);
	}

	@Override
	public List<AppconnMetadata> selectSysName() {
		return this.sqlSessionTemplate.selectList("appconn_setting.selectSysName");
	}

	@Override
	public List<AppconnMetadata> selectAppconnDataBySysName(
			AppconnMetadata appconnMetadata) {
		return this.sqlSessionTemplate.selectList("appconn_setting.selectAppconnDataBySysName", appconnMetadata);
	}
	
}
