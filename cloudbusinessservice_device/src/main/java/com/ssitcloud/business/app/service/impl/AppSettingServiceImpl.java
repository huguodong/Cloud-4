package com.ssitcloud.business.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.app.service.AppSettingServiceI;
import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.app.entity.AppSettingEntity;

/**
 * AppSetting的服务接口，包含device.app_setting的增删改操作
 * @author LXP
 *
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
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	
	@Override
	public ResultEntity insertAppSetting(String appSettingEntityJson) {
		String url = requestURL.getRequestURL(URL_INSERT_APPSETTING);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", appSettingEntityJson);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}

	@Override
	public ResultEntity updateAppSetting(String appSettingEntityJson) {
		String url = requestURL.getRequestURL(URL_UPDATE_APPSETTING);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", appSettingEntityJson);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}

	@Override
	public ResultEntity deleteAppSetting(String appSettingEntityJson) {
		String url = requestURL.getRequestURL(URL_DELETE_APPSETTING);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", appSettingEntityJson);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}

	@Override
	public AppSettingEntity queryOneAppSetting(String appSettingEntityJson) {
		ResultEntity resultEntity = queryOneAppSettingByResultEntity(appSettingEntityJson);
		if(resultEntity == null){
			throw new IllegalArgumentException("向DB请求数据失败");
		}
		Map<String, Object> map = (Map<String, Object>) resultEntity.getResult();
		return mapTOAppSettingEntity(map);
	}

	@Override
	public List<AppSettingEntity> queryAppSettingS(String appSettingEntityJson) {
		ResultEntity resultEntity = queryAppSettingSByResultEntity(appSettingEntityJson);
		if(resultEntity == null){
			throw new IllegalArgumentException("向DB请求数据失败");
		}
		List<Map<String, Object>> result =(List<Map<String, Object>>) resultEntity.getResult();
		List<AppSettingEntity> data = new ArrayList<>(result.size());
		for(int i = 0,z = result.size();i<z;++i){
			data.add(mapTOAppSettingEntity(result.get(i)));
		}
		return data;
	}

	@Override
	public ResultEntity queryOneAppSettingByResultEntity(String appSettingEntityJson) {
		String url = requestURL.getRequestURL(URL_QUERY_APPSETTING);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", appSettingEntityJson);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}

	@Override
	public ResultEntity queryAppSettingSByResultEntity(String appSettingEntityJson) {
		String url = requestURL.getRequestURL(URL_QUERY_APPSETTING_S);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", appSettingEntityJson);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}

	private AppSettingEntity mapTOAppSettingEntity(Map<String, Object> map){
		String json = JsonUtils.toJson(map);
		return JsonUtils.fromJson(json, AppSettingEntity.class);
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
