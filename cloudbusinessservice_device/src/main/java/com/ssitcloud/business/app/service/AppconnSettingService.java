package com.ssitcloud.business.app.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface AppconnSettingService {
	/**
	 * 插入AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract ResultEntity insertAppconnSetting(String req);
	
	/**
	 * 更新AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract ResultEntity updateAppconnSetting(String req);
	
	/**
	 * 根据ID删除AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract ResultEntity deleteAppconnSetting(String req);
	
	/**
	 * 根据馆ID查询AppconnSettingEntit
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract ResultEntity selectAppconnSetting(String req);
	
	
	/**
	 * 根据条件分页查询AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract ResultEntity selectAppconnSettingByPage(String req);
	/**
	 * 查询AppconnMetadata所有系统名称
	 * @return
	 */
	ResultEntity selectSysName();
	/**
	 * 查询根据系统名称查询AppconnMetadata
	 * @param appconnMetadata
	 * @return
	 */
	ResultEntity selectAppconnDataBySysName(String req);

}
