package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.utils.HttpClientUtil;
import com.ssitcloud.dao.DeviceServiceDao;
import com.ssitcloud.dao.SpecialDeviceDao;
import com.ssitcloud.devmgmt.entity.DeviceServiceBaseEntity;
import com.ssitcloud.devmgmt.entity.DeviceServiceEntity;
import com.ssitcloud.devmgmt.entity.SpecialDeviceEntity;
import com.ssitcloud.devmgmt.entity.SpecialDevicePageEntity;
import com.ssitcloud.service.SpecialDeviceService;
import com.ssitcloud.system.entity.ReturnResultEntity;
import com.ssitcloud.utils.MBeanUtil;

import net.sf.json.util.JSONUtils;
@Service
public class SpecialDeviceServiceImpl implements SpecialDeviceService{
	
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	
	@Autowired
	private SpecialDeviceDao specialDeviceDao;
	@Autowired
	private DeviceServiceDao deviceServiceDao;
	
	public ResultEntity addSpecialDevice(String req) {
		
		SpecialDeviceEntity specialDeviceEntity = JsonUtils.fromJson(req, SpecialDeviceEntity.class);
		Map<String, String> map = new HashMap<>();
		map.put("service_idx", specialDeviceEntity.getService_idx()+"");
		List<DeviceServiceEntity> deviceServiceEntities = deviceServiceDao.queryDeviceServiceByParams(map);
		if(deviceServiceEntities != null && !deviceServiceEntities.isEmpty()){
			specialDeviceEntity.setService_id(deviceServiceEntities.get(0).getService_id());
		}
		map.clear();
		map.put("device_id", specialDeviceEntity.getDevice_id());
		synchronized (SpecialDeviceServiceImpl.class) {
			List<SpecialDeviceEntity> deviceEntities = specialDeviceDao.querySpecialDeviceByParams(map);
			if(deviceEntities != null && deviceEntities.size() == 1){
				return new ResultEntity(false, "设备ID重复");
			}
			specialDeviceDao.addSpecialDevice(specialDeviceEntity);
		}
		downloadDeviceServiceInfo(specialDeviceEntity.getService_id(),specialDeviceEntity.getLibrary_id());
		return new ResultEntity(true,"添加成功");
	}
	
	public CloudSyncRequest downloadDeviceServiceInfo(String device_id,String library_id){
		try{
			CloudSyncRequest cloudSyncRequest = new CloudSyncRequest();
			//获取clientId
			String clientId = cloudSyncRequest.getCacheClient(library_id, device_id);
			cloudSyncRequest.setClientId(clientId);
			//下发设备服务信息
			Map<String, String> map = new HashMap<>();
			List<ReturnResultEntity> list = new ArrayList<>();
			map.put("service_id", device_id);
			List<SpecialDeviceEntity> specialDeviceEntities = specialDeviceDao.querySpecialDeviceInfobyServiceIdxAndDeviceId(map);
			if(specialDeviceEntities!=null && !specialDeviceEntities.isEmpty()){
				list.add(MBeanUtil.makeReturnResultEntityFromList(specialDeviceEntities,"special_device"));
				cloudSyncRequest.setData(list);
				postUrl("downloadConfig",JsonUtils.toJson(cloudSyncRequest));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public ResultEntity querySpecialDeviceByPage(String req){
		
		SpecialDevicePageEntity devicePageEntity = new SpecialDevicePageEntity();
		if(req != null && req.length() >0){
			devicePageEntity = JsonUtils.fromJson(req, SpecialDevicePageEntity.class);
		}
		SpecialDevicePageEntity entity = specialDeviceDao.querySpecialDeviceByPage(devicePageEntity);
		
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setResult(entity);
		resultEntity.setState(true);
		return resultEntity;
	}
	
	public ResultEntity querySpecialDeviceByParams(String req){
		Map<String, String> params = new HashMap<>();
		if(req != null && req.length() > 0){
			params = JsonUtils.fromJson(req, Map.class);
		}
		List<SpecialDeviceEntity> deviceEntities = specialDeviceDao.querySpecialDeviceByParams(params);
		ResultEntity entity = new ResultEntity();
		entity.setResult(deviceEntities);
		entity.setState(true);
		return entity;
	}

	@Override
	public ResultEntity editSpecialDevice(String req) {
		if(req == null || req.length() <=0){
			return new ResultEntity();
		}
		SpecialDeviceEntity deviceEntity = JsonUtils.fromJson(req, SpecialDeviceEntity.class);
		Map<String, String> map = new HashMap<>();
		map.put("service_idx", deviceEntity.getService_idx()+"");
		List<DeviceServiceEntity> deviceServiceEntities = deviceServiceDao.queryDeviceServiceByParams(map);
		if(deviceServiceEntities != null && !deviceServiceEntities.isEmpty()){
			deviceEntity.setService_id(deviceServiceEntities.get(0).getService_id());
			deviceEntity.setLibrary_id(deviceServiceEntities.get(0).getLibrary_id());
		}
		specialDeviceDao.editSpecialDevice(deviceEntity);
		downloadDeviceServiceInfo(deviceEntity.getService_id(),deviceEntity.getLibrary_id());
		return new ResultEntity(true,"更新成功");
	}

	@Override
	public ResultEntity deleteSpecialDevice(String req) {
		if(req == null || req.length() <= 0){
			return new ResultEntity();
		}
		List<SpecialDeviceEntity> deviceEntities = JsonUtils.fromJson(req, new TypeReference<List<SpecialDeviceEntity>>() {});
		Map<String, String> map = new HashMap<>();
		for(SpecialDeviceEntity deviceEntity : deviceEntities){
			map.put("idx", deviceEntity.getIdx()+"");
			specialDeviceDao.deleteSpecialDevice(map);
		}
		return new ResultEntity(true,"删除成功！");
	}
	/**
	 * 根据serviceid获取特殊设备（3D导航、人流量、智能家具）服务器分组
	 * @return
	 */
	@Override
	public ResultEntity querySpecialDeviceIdbyServiceIdx(){
		ResultEntity result=new ResultEntity();
		//Map<Integer,String>//device_id逗号分割
		Map<Integer,String> libIdxAndDevIds=specialDeviceDao.querySpecialDeviceIdbyServiceIdx();
		result.setState(true);
		result.setResult(libIdxAndDevIds);
		return result;
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
