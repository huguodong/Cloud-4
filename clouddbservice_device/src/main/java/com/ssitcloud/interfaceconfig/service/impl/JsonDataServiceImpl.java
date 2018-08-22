package com.ssitcloud.interfaceconfig.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.devmgmt.entity.ConfigDataEntity;
import com.ssitcloud.devmgmt.entity.ConfigDataEntityF;
import com.ssitcloud.devmgmt.entity.DataEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.devmgmt.entity.DeviceType;
import com.ssitcloud.devmgmt.entity.QueryDevice;
import com.ssitcloud.interfaceconfig.dao.JsonDataDao;
import com.ssitcloud.interfaceconfig.service.JsonDataService;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.utils.MBeanUtil;

import net.sf.json.util.JSONUtils;

@Service
public class JsonDataServiceImpl implements JsonDataService {
	@Resource
	private JsonDataDao jsonDataDao;
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	@Override
	public List<DataEntity> getJsonData(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return jsonDataDao.getJsonData(map);
	}

	@Override
	public int saveJsonData(ConfigDataEntity entity) {
		// TODO Auto-generated method stub
		return jsonDataDao.saveJsonData(entity);
	}

	public String getInitData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return jsonDataDao.getInitData(map);
	}

	@Override
	public int queryExsit(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return jsonDataDao.queryExsit(map);
	}

	@Override
	public int queryLibExsit(Map<String, String> map) {
		// TODO Auto-generated method stub
		return jsonDataDao.queryLibExsit(map);
	}

	@Override
	public int updateJsonData(ConfigDataEntity entity) {
		// TODO Auto-generated method stub
		return jsonDataDao.updateJsonData(entity);
	}

	@Override
	public List<QueryDevice> queryAllLibDev(HashMap<String, Object> map) {

		return jsonDataDao.queryAllLibDev(map);
	}

	@Override
	public List<DeviceType> getDevTypeData(String library_idx) {
		// TODO Auto-generated method stub
		return jsonDataDao.getDevTypeData(library_idx);
	}
	
	/**
	 * 推送前端页面配置到设备端
	 * @param device_idx 如果设备idx为空，则默认推送整个图书馆的设备
	 * @param library_idx 
	 * @return
	 */
	public ResultEntity pushThemeToDevice(String device_id,Integer library_idx){
		ResultEntity entity = new ResultEntity();
		try{
			if(library_idx == null) return entity;
			Map<String, Object> map = new HashMap<>();
			
			List<Object> list = new ArrayList<>();
			String lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+library_idx);
			map.put("libId",library_idx);
			map.put("deviceIdx", device_id);
			String table = StringUtils.isEmpty(device_id) ? "library_theme_config" : "device_theme_config";
			
			List<ConfigDataEntityF> dataEntityFs = jsonDataDao.queryConfigDataByDevIdAndLibIdx(map);
			if(dataEntityFs == null || dataEntityFs.isEmpty()){
					map.remove("deviceIdx");//设备注册，查询图书馆级别的配置
					dataEntityFs = jsonDataDao.queryConfigDataByDevIdAndLibIdx(map);
					if(dataEntityFs == null || dataEntityFs.isEmpty()) return entity;
					table = "library_theme_config";
				
			}
			List<DataEntity> dataEntities = jsonDataDao.queryViewConfigData();
			
			if(!StringUtils.isEmpty(device_id)){//对一台设备下发
				CloudSyncRequest cloudSyncRequest = new CloudSyncRequest();
				cloudSyncRequest.setOperation("downloadCfgSync");
				cloudSyncRequest.setServicetype("ssitcloud");
				cloudSyncRequest.setTarget("device");
				cloudSyncRequest.setLib_id(lib_id);
				cloudSyncRequest.setDevice_id(device_id);
				String clientId = cloudSyncRequest.getCacheClient(lib_id, device_id);
				cloudSyncRequest.setClientId(clientId);
				list.add(MBeanUtil.makeReturnResultEntityFromList(dataEntityFs,table));//
				list.add(MBeanUtil.makeReturnResultEntityFromList(dataEntities,"view_config"));
				cloudSyncRequest.setData(list);
				postUrl("send",JsonUtils.toJson(cloudSyncRequest));
			}else{//对整个图书馆的设备下发
				//查询使用模板的设备
				List<ConfigDataEntity> configDataEntities = jsonDataDao.selectDeviceIdxByLibId(map);
				Map<Integer, Object> tempMap = new HashMap<>();
				for(ConfigDataEntity configDataEntity : configDataEntities){
					tempMap.put(configDataEntity.getDeviceIdx(), null);
				}
				Set<String> deviceIds = JedisUtils.getInstance().smembers(RedisConstant.LIBRARY_DEVICEID+library_idx);
				for(String deviceId : deviceIds){
					DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+deviceId,DeviceEntity.class);
					if(deviceEntity.getDevice_idx() == 1671){
						System.out.println(deviceEntity.getDevice_idx());
						System.out.println(deviceEntity.getDevice_idx());
					}
					if(tempMap.containsKey(deviceEntity.getDevice_idx())) continue;
					list.clear();
					CloudSyncRequest cloudSyncRequest = new CloudSyncRequest();
					cloudSyncRequest.setOperation("downloadCfgSync");
					cloudSyncRequest.setServicetype("ssitcloud");
					cloudSyncRequest.setTarget("device");
					cloudSyncRequest.setLib_id(lib_id);
					cloudSyncRequest.setDevice_id(deviceId);
					String clientId = cloudSyncRequest.getCacheClient(lib_id, deviceId);
					cloudSyncRequest.setClientId(clientId);
					list.add(MBeanUtil.makeReturnResultEntityFromList(dataEntityFs,table));
					list.add(MBeanUtil.makeReturnResultEntityFromList(dataEntities,"view_config"));
					cloudSyncRequest.setData(list);
					postUrl("send",JsonUtils.toJson(cloudSyncRequest));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return entity;
	}
	
	public ResultEntity postUrl(String postUrl,String req){
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	
}
