package com.ssitcloud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.AppconnSettingDao;
import com.ssitcloud.entity.AppconnMetadata;
import com.ssitcloud.entity.AppconnSettingEntity;
import com.ssitcloud.service.AppconnSettingService;
@Service
public class AppconnSettingServiceImpl implements AppconnSettingService {
	@Resource
	AppconnSettingDao appconnSettingDao;

	@Override
	public int insertAppconnSetting(AppconnSettingEntity appconnSettingEntity) {
		return appconnSettingDao.insertAppconnSetting(appconnSettingEntity);
	}

	@Override
	public int updateAppconnSetting(AppconnSettingEntity appconnSettingEntity) {
		return appconnSettingDao.updateAppconnSetting(appconnSettingEntity);
	}

	@Override
	public int deleteAppconnSetting(AppconnSettingEntity appconnSettingEntity) {
		return appconnSettingDao.deleteAppconnSetting(appconnSettingEntity);
	}

	@Override
	public ResultEntity selectAppconnSetting(
			AppconnSettingEntity appconnSettingEntity) {
		ResultEntity result = new ResultEntity();
		List<AppconnSettingEntity> list = appconnSettingDao.selectAppconnSetting(appconnSettingEntity);
		if(list != null && list.size() >0){
			result.setValue(true, "", "", list);
		}else{
			result.setValue(false, "没有返回数据！", "", "");
		}
		return result;
	}

	@Override
	public ResultEntity selectAppconnSettingByPage(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
				AppconnSettingEntity appconnSettingEntity = new AppconnSettingEntity();
				
				Integer page = map.get("page")==null?1:Integer.valueOf(map.get("page").toString());				
				Integer pageSize = map.get("pageSize")==null?10:Integer.valueOf(map.get("pageSize").toString());
				if(map.get("conn_id")!=null && !map.get("conn_id").toString().isEmpty()){
					Integer conn_id = Integer.valueOf(map.get("conn_id").toString());
					appconnSettingEntity.setConn_id(conn_id);
				}
				if(map.get("lib_idx")!=null && !map.get("lib_idx").toString().isEmpty()){
					Integer lib_idx = Integer.valueOf(map.get("lib_idx").toString());
					appconnSettingEntity.setLib_idx(lib_idx);
				}
				if(map.get("lib_idx_str")!=null && !map.get("lib_idx_str").toString().isEmpty()){
					String lib_idx = map.get("lib_idx_str").toString();
					appconnSettingEntity.setLib_idx_str(lib_idx);
				}
				appconnSettingEntity.setPage(page);
				appconnSettingEntity.setPageSize(pageSize);
				
				appconnSettingEntity = appconnSettingDao.selectAppconnSettingByPage(appconnSettingEntity);
				result.setValue(true, "", "", appconnSettingEntity);
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
	public int deleteAppconnSettingBylib_idx(
			AppconnSettingEntity appconnSettingEntity) {
		return appconnSettingDao.deleteAppconnSettingBylib_idx(appconnSettingEntity);
	}

	@Override
	public Map<String, Object> selectRealValue(Integer lib_idx){
		AppconnSettingEntity entity = new AppconnSettingEntity();
		entity.setLib_idx(lib_idx);
		List<Map<String, Object>> sourceData = appconnSettingDao.selectRealValue(entity );
		
		Map<String, Object> resultData = new HashMap<>();
		for (Map<String, Object> map : sourceData) {
			String name = (String) map.get("meta_name");
			String value = (String) map.get("meta_value");
			resultData.put(name, value);
			
			resultData.put("@lib_sys_type", map.get("sys_type"));
		}

		return resultData;
	}

	@Override
	public ResultEntity selectSysName() {
		ResultEntity result = new ResultEntity();
		try {
			List<AppconnMetadata> list = appconnSettingDao.selectSysName();
			result.setValue(true, "", "", list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@Override
	public ResultEntity selectAppconnDataBySysName(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
				AppconnMetadata appconnMetadata = new AppconnMetadata();
				if(map.get("sys_name")!=null && !map.get("sys_name").toString().isEmpty()){
					String sys_name = (String) map.get("sys_name");
					appconnMetadata.setSys_name(sys_name);
					List<AppconnMetadata> list = appconnSettingDao.selectAppconnDataBySysName(appconnMetadata);
					result.setValue(true, "", "", list);
				}else{
					result.setValue(false, "系统名参数不能为空", "", "");
				}
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
}
