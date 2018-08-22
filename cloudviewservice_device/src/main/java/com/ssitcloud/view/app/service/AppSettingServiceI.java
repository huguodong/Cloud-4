package com.ssitcloud.view.app.service;


import com.ssitcloud.common.entity.ResultEntity;


/**
 * App setting 服务
 * @author lqw
 *2017/3/18
 */
public interface AppSettingServiceI {

	/**
	 * 插入appSettingEntity到数据库中
	 * @param json字符串
	 * @return
	 */
	public ResultEntity insertAppSetting(String json);
	
	/**
	 * 更新appSettingEntity到数据库中
	 * @param json字符串
	 * @return
	 */
	public ResultEntity updateAppSetting(String json);
	
	/**
	 * 删除appSettingEntity
	 * @param json字符串，必须设置实体的主键
	 * @return
	 */
	public ResultEntity deleteAppSetting(String json);
	
	/**
	 * 查询appSettingEntity(单条)
	 * @param json字符串，必须设置实体的主键
	 * @return
	 */
	public ResultEntity queryOneAppSetting(String json);
	
	
	/**
	 * 查询appSettingEntity(多条)
	 * @param  json字符串
	 * @return
	 */
	public ResultEntity queryAppSettingS(String json);
	
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
	public ResultEntity selLibraryByNameORLibId(String req);
	
}
