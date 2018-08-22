package com.ssitcloud.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.AppconnSettingEntity;

public interface AppconnSettingService {
	/**
	 * 插入AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract int insertAppconnSetting(AppconnSettingEntity appconnSettingEntity);
	
	/**
	 * 更新AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract int updateAppconnSetting(AppconnSettingEntity appconnSettingEntity);
	
	/**
	 * 根据ID删除AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract int deleteAppconnSetting(AppconnSettingEntity appconnSettingEntity);
	
	/**
	 * 根据馆ID查询AppconnSettingEntit
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	ResultEntity selectAppconnSetting(AppconnSettingEntity appconnSettingEntity);
	
	
	/**
	 * 根据条件分页查询AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract ResultEntity selectAppconnSettingByPage(String req);
	/**
	 * 根据馆ID删除AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract int deleteAppconnSettingBylib_idx(AppconnSettingEntity appconnSettingEntity);

	
	/**
	 * 根据图书馆查询webservice配置
	 * @param lib_idx 图书馆idx
	 * @return webservice配置
	 */
	Map<String, Object> selectRealValue(Integer lib_idx);
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
