package com.ssitcloud.business.mobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.authentication.entity.LibraryUnionEntity;
import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.fatory.AppOPACEntityFatory;
import com.ssitcloud.business.mobile.service.DeviceServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.mobile.entity.AppOPACEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月28日 下午3:14:57
 */
@Service
public class DeviceServiceImpl implements DeviceServiceI {
	private final String URL_FIND_DEVICE_BY_LIBIDX = "findDeviceByLibId";
	private final String URL_FIND_DEVICE = "findDeviceByLibId";
	private final String URL_FIND_DEVICE_BY_DEV_TYPE = "selectByDevTypeNameList";
	private final String URL_FIND_DEVICE_BY_SELF_LIB = "selectChildLibrary";
	private final String URL_FIND_LIBRARY_AND_INFO = "selectLibraryAndInfo";
	private final String URL_FIND_DEVICE_IDX_AND_REG_CODE = "selectDeviceIdxAndRegion";

	@Resource(name = "requestURL")
	private RequestURLListEntity requestURLEntity;
	private static final String charset = "utf-8";

	@Override
	public List<Map<String, Object>> findDeviceByLibId(Integer libId) {
		if (libId == null) {
			return new ArrayList<>(0);
		}
		String url = requestURLEntity.getRequestURL(URL_FIND_DEVICE_BY_LIBIDX);
		Map<String, String> map = new HashMap<>(3);
		map.put("json", "{\"library_idx\":" + libId + "}");
		String result = HttpClientUtil.doPost(url, map, charset);
		try {
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if(resultEntity.getState()){//正常返回数据
				return (List<Map<String, Object>>) resultEntity.getResult();
			}
		} catch (Exception e) {
			LogUtils.error("向远程服务器获取数据失败，url=>"+url+" data=>"+map);
			return null;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findDevice(DeviceEntity entity) {
		if (entity == null) {
			entity = new DeviceEntity();
		}
		String url = requestURLEntity.getRequestURL(URL_FIND_DEVICE);
		Map<String, String> map = new HashMap<>(3);
		map.put("json", JsonUtils.toJson(entity));
		String result = HttpClientUtil.doPost(url, map, charset);
		try {
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if(resultEntity.getState()){//正常返回数据
				return (List<Map<String, Object>>) resultEntity.getResult();
			}
		} catch (Exception e) {
			LogUtils.error("向远程服务器获取数据失败，url=>"+url+" data=>"+map);
		}
		return new ArrayList<>(0);
	}
	
	
	@Override
	public List<DeviceEntity> findOpacDevice(Integer libId, Integer pageCurrent, Integer pageSize,Map<String, Object> otherPram) {
		if (libId == null) {
			return new ArrayList<>(0);
		}
		String url = requestURLEntity.getRequestURL(URL_FIND_DEVICE_BY_DEV_TYPE);
		
		Map<String, Object> data = new HashMap<>(8,1.0f);
		data.put("library_idx", libId);
		data.put("device_name", otherPram.get("search_str"));
		data.put("devType", new String[]{"ssl"});
		if(pageCurrent != null && pageSize != null){
			data.put("pageCurrent", pageCurrent);
			data.put("pageSize", pageSize);
		}
		data.put("regionCode", otherPram.get("regionCode"));
		
		
		Map<String, String> map = new HashMap<>(3);
		map.put("req", JsonUtils.toJson(data));
		String result = HttpClientUtil.doPost(url, map, charset);
		try {
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if(resultEntity.getState()){//正常返回数据
				List<Map<String, Object>> temp=(List<Map<String, Object>>) resultEntity.getResult();
				List<DeviceEntity> reList = new ArrayList<>(temp.size());
				String t;
				for (Map<String, Object> map2 : temp) {
					t = JsonUtils.toJson(map2);
					DeviceEntity dev = JsonUtils.fromJson(t, DeviceEntity.class);
					reList.add(dev);
				}
				
				return reList;
			}
		} catch (Exception e) {
			LogUtils.error("向远程服务器获取数据失败，url=>"+url+" data=>"+map);
			return null;
		}
		return null; 
		
	}

	@Override
	public List<AppOPACEntity> findOpacDeviceToApp(Integer libId, Integer pageCurrent, Integer pageSize,Map<String, Object> otherPram) {
		List<DeviceEntity> dataList = findOpacDevice(libId, pageCurrent, pageSize,otherPram);
		if (dataList != null) {
			List<AppOPACEntity> reList = new ArrayList<>(dataList.size());
			for (DeviceEntity deviceEntity : dataList) {
				reList.add(AppOPACEntityFatory.createEntity(deviceEntity));
			}
			return reList;
		} else {
			return null;
		}
	}
	
	@Override
	public List<AppOPACEntity> findOpacSelfHelpLib(Integer libId, Integer pageCurrent, Integer pageSize,Map<String, Object> otherPram) {
		if (libId == null) {
			return new ArrayList<>(0);
		}
		Map<String, Object> data = new HashMap<>(8,1.0f);
		data.put("master_lib_idx", libId);
		data.put("lib_name", otherPram.get("search_str"));
		if(pageCurrent != null && pageSize != null){
			data.put("pageCurrent", pageCurrent);
			data.put("pageSize", pageSize);
		}
		data.put("regionCode", otherPram.get("regionCode"));
		
		String url = requestURLEntity.getRequestURL(URL_FIND_DEVICE_BY_SELF_LIB);
		Map<String, String> map = new HashMap<>(3,1.0f);
		map .put("req", JsonUtils.toJson(data));
		String resultJson = HttpClientUtil.doPost(url, map, charset);
		try{
			ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
			if(resultEntity.getState()){
				List<Map<String, Object>> temps =(List<Map<String, Object>>) resultEntity.getResult();
				List<AppOPACEntity> opacs = new ArrayList<>(temps.size());
				String t;
				for (Map<String, Object> temp : temps) {
					t = JsonUtils.toJson(temp);
					opacs.add(AppOPACEntityFatory.createEntity(JsonUtils.fromJson(t, LibraryUnionEntity.class)));
				}
				return opacs;
			}
		}catch(Exception e){
			LogUtils.info(url+" 服务器可能没有返回预期的数据result==>List<LibraryUnionEntity>",e);
		}
		return null;
	}

	@Override
    public AppOPACEntity findOpacSelfHelpLib(Integer lib_idx){
        String url = requestURLEntity.getRequestURL(URL_FIND_LIBRARY_AND_INFO);
        Map<String, String> map = new HashMap<>(3,1.0f);
        Map<String, Object> data = new HashMap<>(8,1.0f);
        data.put("library_idx", lib_idx);
        map .put("req", JsonUtils.toJson(data));
        String resultJson = HttpClientUtil.doPost(url, map, charset);
        try{
            ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
            if(resultEntity.getState()){
                List<Map<String, Object>> temps =(List<Map<String, Object>>) resultEntity.getResult();
                if(!temps.isEmpty()){
                    Map<String, Object> temp = temps.get(0);
                    String t = JsonUtils.toJson(temp);
                    return AppOPACEntityFatory.createEntity(JsonUtils.fromJson(t, LibraryUnionEntity.class));
                }
            }
            return null;
        }catch(Exception e){
            LogUtils.info(url+" 服务器可能没有返回预期的数据result==>List<LibraryUnionEntity>",e);
            return null;
        }
    }

	@Override
	public List<Map<String, Object>> selectDeviceIdxAndRegion(Integer lib_idx) {
		String url = requestURLEntity.getRequestURL(URL_FIND_DEVICE_IDX_AND_REG_CODE);
		Map<String, String> map = new HashMap<>(1,1.2f);
		map.put("req", "{\"library_idx\":"+lib_idx+"}");
		String resultJson = HttpClientUtil.doPost(url, map , charset);
		try{
			ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
			if(resultEntity.getState()){
				return (List<Map<String, Object>>) resultEntity.getResult();
			}
		}catch (Exception e) {
			LogUtils.info(getClass()+"查询设备id和地区码失败",e);
		}
		return new ArrayList<>(0);
	}


}
