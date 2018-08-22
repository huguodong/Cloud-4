package com.ssitcloud.view.app.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.app.service.AppSettingServiceI;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;


/**
 * AppSetting的服务接口，包含device.app_setting的增删改操作
 * @author lqw
 *2017/3/18
 */
@Service
public class AppSettingServiceImpl extends BasicServiceImpl implements AppSettingServiceI{
	
	private final String URL_INSERT_APPSETTING="addAppSetting";
	private final String URL_UPDATE_APPSETTING="updateAppSetting";
	private final String URL_DELETE_APPSETTING="deleteAppSetting";
	private final String URL_QUERY_APPSETTING="selectAppSetting";//只返回一个实体
	private final String URL_QUERY_APPSETTING_S="selectAppSettings";//返回多个实体
	private final String URL_QUERY_APPSETTING_PAGE="selectAppSettingByPage";//根据馆idx分组分页
	private final String URL_DELETE_APPSETTING_LIBID="deleteAppSettingBylib_idx";//根据馆idx删除
	private final String URL_QUERY_MAIN_MENU_CODE="selectByCode";//根据用户main_menu_code查询其菜单
	private final String URL_QUERY_LIBRARY_ID="selLibraryByNameORLibId";//通过图书馆ID或名称模糊查询
	@Override
	public ResultEntity insertAppSetting(String json) {
		return postUrl(URL_INSERT_APPSETTING,json);
	}
	@Override
	public ResultEntity updateAppSetting(String json) {
		return postUrl(URL_UPDATE_APPSETTING,json);
	}
	@Override
	public ResultEntity deleteAppSetting(String json) {
		return postUrl(URL_DELETE_APPSETTING,json);
	}
	@Override
	public ResultEntity queryOneAppSetting(String json) {
		return postUrl(URL_QUERY_APPSETTING,json);
	}
	@Override
	public ResultEntity queryAppSettingS(String json) {
		return postUrl(URL_QUERY_APPSETTING_S,json);
	}
	@Override
	public ResultEntity selectAppSettingByPage(String req) {
		return postUrl(URL_QUERY_APPSETTING_PAGE,req);
	}

	@Override
	public ResultEntity deleteAppSettingBylibidx(String req) {
		return postUrl(URL_DELETE_APPSETTING_LIBID,req);
	}
	@Override
	public ResultEntity selectByCode(String req) {
		return postUrl(URL_QUERY_MAIN_MENU_CODE,req);
	}
	@Override
	public ResultEntity selLibraryByNameORLibId(String req) {
		return postUrl(URL_QUERY_LIBRARY_ID,req);
	}
	
	
}
