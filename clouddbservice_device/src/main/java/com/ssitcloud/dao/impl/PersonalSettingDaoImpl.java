package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.PersonalSettingDao;
import com.ssitcloud.entity.PersonalSettingEntity;
import com.ssitcloud.entity.page.PersonalSettingMgmtPageEntity;

/**
 * 个人菜单设置
 *
 * <p>2017年2月10日 下午2:17:41  
 * @author hjc 
 *
 */
@Repository
public class PersonalSettingDaoImpl extends CommonDaoImpl implements PersonalSettingDao {

	@Override
	public int addPersonalSetting(PersonalSettingEntity personalSettingEntity) {
		return getSqlSession().insert("personalsetting.addPersonalSetting",personalSettingEntity);
	}

	@Override
	public int delPersonalSetting(PersonalSettingEntity personalSettingEntity) {
		return getSqlSession().delete("personalsetting.delPersonalSetting",personalSettingEntity);
	}

	@Override
	public int updPersonalSetting(PersonalSettingEntity personalSettingEntity) {
		return getSqlSession().update("personalsetting.updPersonalSetting",personalSettingEntity);
	}

	@Override
	public PersonalSettingEntity selPersonalSettingByIdx(
			PersonalSettingEntity personalSettingEntity) {
		return getSqlSession().selectOne("personalsetting.selPersonalSettingByIdx",personalSettingEntity);
	}

	@Override
	public PersonalSettingMgmtPageEntity selectPersonalSettingByPage(
			PersonalSettingMgmtPageEntity personalSettingMgmtPageEntity) {
		if(null == personalSettingMgmtPageEntity) personalSettingMgmtPageEntity = new PersonalSettingMgmtPageEntity();
		PersonalSettingMgmtPageEntity total = getSqlSession().selectOne("personalsetting.selectPersonalSettingByPage", personalSettingMgmtPageEntity);
		personalSettingMgmtPageEntity.setDoAount(false);
		personalSettingMgmtPageEntity.setTotal(total.getTotal());
		personalSettingMgmtPageEntity.setRows(getSqlSession().selectList("personalsetting.selectPersonalSettingByPage", personalSettingMgmtPageEntity));
		return personalSettingMgmtPageEntity;
	}

	@Override
	public List<PersonalSettingEntity> selPersonalSettingList(
			PersonalSettingEntity personalSettingEntity) {
		return getSqlSession().selectList("personalsetting.selPersonalSettingList", personalSettingEntity);
	}

}
