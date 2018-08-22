package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.AppSettingDao;
import com.ssitcloud.entity.AppSettingEntity;
import com.ssitcloud.entity.MenuAppSettingEntity;
import com.ssitcloud.entity.page.AppSettingPage2Entity;
import com.ssitcloud.service.AppSettingService;


@Service
public class AppSettingServiceImpl implements AppSettingService {
	@Resource
	private AppSettingDao appSettingDao;

	@Override
	public int insertAppSetting(
			AppSettingEntity appSettingEntity) {
		return appSettingDao.insertAppSetting(appSettingEntity);
	}

	@Override
	public int updateAppSetting(
			AppSettingEntity appSettingEntity) {
		return appSettingDao.updateAppSetting(appSettingEntity);
	}

	@Override
	public int deleteAppSetting(
			AppSettingEntity appSettingEntity) {
		return appSettingDao.deleteAppSetting(appSettingEntity);
	}

	@Override
	public AppSettingEntity queryOneAppSetting(
			AppSettingEntity appSettingEntity) {
		return appSettingDao.queryOneAppSetting(appSettingEntity);
			
	}

	@Override
	public List<AppSettingEntity> queryAppSettings(
			AppSettingEntity appSettingEntity) {
		return appSettingDao.queryAppSettings(appSettingEntity);
		
	}

	@Override
	public ResultEntity selectAppSettingByPage(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
				AppSettingPage2Entity appSettingPage2Entity = new AppSettingPage2Entity();
				
				Integer page = map.get("page")==null?1:Integer.valueOf(map.get("page").toString());				
				Integer pageSize = map.get("pageSize")==null?10:Integer.valueOf(map.get("pageSize").toString());
				if(map.get("lib_idx")!=null && !map.get("lib_idx").toString().isEmpty()){
					Integer lib_idx = Integer.valueOf(map.get("lib_idx").toString());
					appSettingPage2Entity.setLib_idx(lib_idx);
				}
				if(map.get("lib_idx_str")!=null && !map.get("lib_idx_str").toString().isEmpty()){
					String lib_idx_str = map.get("lib_idx_str").toString();
					appSettingPage2Entity.setLib_idx_str(lib_idx_str);
				}
				if(map.get("service_id")!=null && !map.get("service_id").toString().isEmpty()){
					String service_id = map.get("service_id").toString();
					appSettingPage2Entity.setService_id(service_id);
				}
				if(map.get("setting_sort")!=null && !map.get("setting_sort").toString().isEmpty()){
					Integer setting_sort = Integer.valueOf(map.get("setting_sort").toString());
					appSettingPage2Entity.setSetting_sort(setting_sort);
				}
				if(map.get("user_type")!=null && !map.get("user_type").toString().isEmpty()){
					String user_type = map.get("user_type").toString();
					appSettingPage2Entity.setUser_type(user_type);
				}
				if(map.get("image_url")!=null && !map.get("image_url").toString().isEmpty()){
					String image_url = map.get("image_url").toString();
					appSettingPage2Entity.setImage_url(image_url);
				}
				appSettingPage2Entity.setPage(page);
				appSettingPage2Entity.setPageSize(pageSize);
				
				appSettingPage2Entity = appSettingDao.selectAppSettingByPage(appSettingPage2Entity);
				result.setValue(true, "", "", appSettingPage2Entity);
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@Override
	public int deleteAppSettingBylibidx(AppSettingEntity appSettingEntity) {
		return appSettingDao.deleteAppSettingBylibidx(appSettingEntity);
	}

	@Override
	public List<Map> selectMenuNamesByServiceIds(Map<String, Object> map) {
		return appSettingDao.selectMenuNamesByServiceIds(map);
	}

	

}
