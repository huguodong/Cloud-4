package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.AppSettingEntity;
import com.ssitcloud.entity.MenuAppSettingEntity;
import com.ssitcloud.entity.page.AppSettingPage2Entity;

public interface AppSettingDao {
	/**
	 * 个人菜单设置AppSettingEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param appSettingEntity
	 * @return
	 */
	public abstract int insertAppSetting(AppSettingEntity appSettingEntity);
	
	/**
	 * 个人菜单设置AppSettingEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param appSettingEntity
	 * @return
	 */
	public abstract int updateAppSetting(AppSettingEntity appSettingEntity);
	
	/**
	 * 个人菜单设置AppSettingEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param appSettingEntity
	 * @return
	 */
	public abstract int deleteAppSetting(AppSettingEntity appSettingEntity);
	
	/**
	 * 个人菜单设置AppSettingEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param appSettingEntity
	 * @return
	 */
	public abstract AppSettingEntity queryOneAppSetting(AppSettingEntity appSettingEntity);
	
	/**
	 * 个人菜单设置AppSettingEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param appSettingEntity
	 * @return
	 */
	public abstract List<AppSettingEntity> queryAppSettings(AppSettingEntity appSettingEntity);
	/**
	 * 个人菜单设置AppSettingEntity根据馆dix分组分页查询
	 * author lqw
	 * 2017年3月20日 
	 */
	public abstract AppSettingPage2Entity selectAppSettingByPage(AppSettingPage2Entity appSettingPage2Entity);
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
