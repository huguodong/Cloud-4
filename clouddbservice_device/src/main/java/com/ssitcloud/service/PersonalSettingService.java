package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.PersonalSettingEntity;

/**
 * 个人菜单设置
 *
 * <p>2017年2月10日 下午2:17:41  
 * @author hjc 
 *
 */
public interface PersonalSettingService {

	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月10日 下午4:36:43 
	 * <p>create by hjc
	 * @param personalSettingEntity
	 * @return
	 */
	public abstract int addPersonalSetting(PersonalSettingEntity personalSettingEntity);
	
	/**
	 * 删除记录
	 *
	 * <p>2017年2月10日 下午4:36:53 
	 * <p>create by hjc
	 * @param personalSettingEntity
	 * @return
	 */
	public abstract int delPersonalSetting(PersonalSettingEntity personalSettingEntity);
	/**
	 * 更新记录
	 *
	 * <p>2017年2月10日 下午4:36:53 
	 * <p>create by hjc
	 * @param personalSettingEntity
	 * @return
	 */
	public abstract int updPersonalSetting(PersonalSettingEntity personalSettingEntity);
	/**
	 * 查询单条记录信息
	 *
	 * <p>2017年2月10日 下午4:36:53 
	 * <p>create by hjc
	 * @param personalSettingEntity
	 * @return
	 */
	public abstract PersonalSettingEntity selPersonalSettingByIdx(PersonalSettingEntity personalSettingEntity);
	
	/**
	 * 分页查询
	 * <p>2017年2月23日 
	 * <p>create by shaungjunjie
	 * @param req
	 * @return
	 */
	public abstract ResultEntity selectPersonalSettingByPage(String req);
	/**
	 * 查询多条记录
	 * author huanghuang
	 * 2017年4月5日 上午10:21:24
	 * @param personalSettingEntity
	 * @return
	 */
	List<PersonalSettingEntity> selPersonalSettingList(PersonalSettingEntity personalSettingEntity);

	/**
	 * 查询是否在数据库中存在记录
	 * @param personalSettingEntity
	 * @return
	 */
	public abstract boolean isExists(PersonalSettingEntity personalSettingEntity);
	
}
