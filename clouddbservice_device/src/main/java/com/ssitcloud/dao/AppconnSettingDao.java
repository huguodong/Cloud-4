package com.ssitcloud.dao;
import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.AppconnMetadata;
import com.ssitcloud.entity.AppconnSettingEntity;

public interface AppconnSettingDao {
	
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
	List<AppconnSettingEntity> selectAppconnSetting(AppconnSettingEntity appconnSettingEntity);
	
	
	/**
	 * 根据条件分页查询AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract AppconnSettingEntity selectAppconnSettingByPage(AppconnSettingEntity appconnSettingEntity);
	/**
	 * 根据馆ID删除AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	public abstract int deleteAppconnSettingBylib_idx(AppconnSettingEntity appconnSettingEntity);
	
	List<Map<String, Object>> selectRealValue(AppconnSettingEntity appconnSettingEntity);
	/**
	 * 查询AppconnMetadata所有系统名称
	 * @return
	 */
	List<AppconnMetadata> selectSysName();
	/**
	 * 查询根据系统名称查询AppconnMetadata
	 * @param appconnMetadata
	 * @return
	 */
	List<AppconnMetadata> selectAppconnDataBySysName(AppconnMetadata appconnMetadata);
}
