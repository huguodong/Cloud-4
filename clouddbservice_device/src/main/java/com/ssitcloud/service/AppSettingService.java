package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.AppSettingEntity;
import com.ssitcloud.entity.MenuAppSettingEntity;

public interface AppSettingService {
	
	/**
	 * 个人菜单设置AppSettingEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param appSettingEntity
	 * @return
	 */
	public abstract int insertAppSetting(AppSettingEntity appSettingEntity);
	
	/**
	 * 个人菜单设置AppSettingEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param appSettingEntity
	 * @return
	 */
	public abstract int updateAppSetting(AppSettingEntity appSettingEntity);
	
	/**
	 * 个人菜单设置AppSettingEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param appSettingEntity
	 * @return
	 */
	public abstract int deleteAppSetting(AppSettingEntity appSettingEntity);
	
	/**
	 * 个人菜单设置AppSettingEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param appSettingEntity
	 * @return
	 */
	public abstract AppSettingEntity queryOneAppSetting(AppSettingEntity appSettingEntity);
	
	/**
	 * 个人菜单设置AppSettingEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param appSettingEntity
	 * @return
	 */
	public abstract List<AppSettingEntity> queryAppSettings(AppSettingEntity appSettingEntity);
	/**
	 * 个人菜单设置AppSettingEntity根据馆dix分组分页查询
	 * author lqw
	 * 2017年3月20日 
	 */
	public abstract ResultEntity selectAppSettingByPage(String req);
	/**
	 * 个人菜单设置AppSettingEntity 根据馆dix删除 
	 * author author lqw
	 * 2017年3月20日 
	 */
	public abstract int deleteAppSettingBylibidx(AppSettingEntity appSettingEntity);

	/**
	 * 个人菜单设置 AppSettingEntity 根据service_id,user_type和lib_idx查出对应的功能描述
	 * author shuangjunjie
	 * 2017年3月27日
	 * @param map
	 * @return
	 */
	public abstract List<Map> selectMenuNamesByServiceIds(Map<String,Object> map);
}
