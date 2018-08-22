package com.ssitcloud.business.mobile.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.AppSettingEntity;

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
	public boolean insertAppSetting(String appSettingEntityJson);
	
	/**
	 * 更新appSettingEntity到数据库中
	 * @param appSettingEntityJson 实体的json字符串
	 * @return
	 */
	public boolean updateAppSetting(String appSettingEntityJson);
	
	/**
	 * 删除appSettingEntity
	 * @param appSettingEntityJson 实体的json字符串，必须设置实体的主键
	 * @return
	 */
	public boolean deleteAppSetting(String appSettingEntityJson);
	
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
	 * 查询appSettingEntity以及对应的菜单名
	 * add by shuangjunjie
	 * 2017年3月28日
	 * @param appSettingEntityJson
	 * @return
	 */
	public ResultEntity selectMenuNamesByServiceIds(String appSettingEntityJson);
	

	/**
	 * 检查馆员具有的app操作权限
	 * author shuangjunjie
	 * 2017年3月28日
	 * @param req
	 * @return
	 */
	ResultEntity checkPermission(String req);
}
