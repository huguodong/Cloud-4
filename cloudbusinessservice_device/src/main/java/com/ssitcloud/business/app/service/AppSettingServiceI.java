package com.ssitcloud.business.app.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.app.entity.AppSettingEntity;

/**
 * App setting 服务
 * @author LXP
 *
 */
public interface AppSettingServiceI {

	/**
	 * 插入appSettingEntity到数据库中
	 * @param appSettingEntityJson 实体的json字符串
	 * @return
	 */
	public ResultEntity insertAppSetting(String appSettingEntityJson);
	
	/**
	 * 更新appSettingEntity到数据库中
	 * @param appSettingEntityJson 实体的json字符串
	 * @return
	 */
	public ResultEntity updateAppSetting(String appSettingEntityJson);
	
	/**
	 * 删除appSettingEntity
	 * @param appSettingEntityJson 实体的json字符串，必须设置实体的主键
	 * @return
	 */
	public ResultEntity deleteAppSetting(String appSettingEntityJson);
	
	/**
	 * 查询appSettingEntity
	 * @param appSettingEntityJson 实体的json字符串，必须设置实体的主键
	 * @return
	 */
	public AppSettingEntity queryOneAppSetting(String appSettingEntityJson);
	
	/**
	 * 查询appSettingEntity
	 * @param appSettingEntityJson 实体的json字符串，必须设置实体的主键
	 * @return
	 */
	public ResultEntity queryOneAppSettingByResultEntity(String appSettingEntityJson);
	
	/**
	 * 查询appSettingEntity
	 * @param appSettingEntityJson 实体的json字符串
	 * @return
	 */
	public List<AppSettingEntity> queryAppSettingS(String appSettingEntityJson);
	
	/**
	 * 查询appSettingEntity
	 * @param appSettingEntityJson 实体的json字符串
	 * @return
	 */
	public ResultEntity queryAppSettingSByResultEntity(String appSettingEntityJson);
	
	/**
	 * 个人菜单设置AppSettingEntity根据馆dix分组分页查询
	 * author lqw
	 * 2017年3月20日 
	 */
	public ResultEntity selectAppSettingByPage(String req);
	/**
	 * 个人菜单设置AppSettingEntity 根据馆dix删除 
	 * author author lqw
	 * 2017年3月20日 
	 */
	public ResultEntity deleteAppSettingBylibidx(String req);
	
	/**
	 * 根据用户main_menu_code查询其菜单
	 *lqw
	 * <p>2017年3月20日 
	 * @return
	 */
	public ResultEntity selectByCode(String req);
	/**
	 * 通过图书馆ID或名称模糊查询
	 *lqw 2017/3/22
	 */
	public abstract ResultEntity selLibraryByNameORLibId(String req);
	

}
