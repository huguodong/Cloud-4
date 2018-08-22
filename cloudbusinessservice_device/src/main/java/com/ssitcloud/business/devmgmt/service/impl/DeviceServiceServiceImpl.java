package com.ssitcloud.business.devmgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.MBeanUtil;
import com.ssitcloud.business.common.util.MBeanUtils;
import com.ssitcloud.business.common.util.ReturnResultEntity;
import com.ssitcloud.business.devmgmt.service.DeviceServiceService;
import com.ssitcloud.business.devregister.param.AddDeviceParam;
import com.ssitcloud.business.devregister.service.DeviceRegisterService;
import com.ssitcloud.business.opermgmt.service.OperatorService;
import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.utils.HttpClientUtil;
import com.ssitcloud.devmgmt.entity.DeviceServiceEntity;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

import net.sf.json.JSONObject;


@Service
public class DeviceServiceServiceImpl extends BasicServiceImpl implements DeviceServiceService {

	public static final String URL_deviceServiceRegister="deviceServiceRegister";
	public static final String URL_querydeviceServiceByPage="queryDeviceServiceByPage";
	public static final String URL_queryDeviceServiceRegister="queryDeviceServiceRegister";
	public static final String URL_editDeviceServiceRegister="editDeviceServiceRegister";
	public static final String URL_deleteDeviceService="deleteDeviceService";
	public static final String URL_queryDeviceServiceByParams="queryDeviceServiceByParams";
	private static final String URL_deleteDevOperatorInfoByOperIds = "deleteDevOperatorInfoByOperIds";
	private static final String URL_addCloudDbSyncConfig = "addCloudDbSyncConfig";
	private static final String URL_downloadConfig = "downloadConfig";
	private static final String URL_selectCloudDbSyncConfig  = "selectCloudDbSyncConfig";
	private static final String URL_updateDeviceServiceOperateTime = "updateDeviceServiceOperateTime";
	private static final String URL_updIpWhite = "updIpWhite";
	
	
	@Resource
	private DeviceRegisterService deviceRegisterService;
	@Resource
	private OperatorService operatorService;
	
	
	public ResultEntity deviceServiceRegister(String req) {
		
		if(req == null || req.length() <= 0){
			return new ResultEntity();
		}
		DeviceServiceEntity deviceServiceEntity = JsonUtils.fromJson(req, DeviceServiceEntity.class);
		//向operator添加一个新用户
		AddDeviceParam deviceParam = new AddDeviceParam();
		deviceParam.setAdmin_idx(""); //操作员的idx
		deviceParam.setOperator_id(deviceServiceEntity.getService_id());  //设备的id
		deviceParam.setIp(deviceServiceEntity.getRequest_ip()); //设备的ip，存入白名单
		deviceParam.setPort(""); //设备的port
		deviceParam.setLibrary_idx(deviceServiceEntity.getLibrary_idx()); //所属图书馆的idx
		deviceParam.setOperator_name(deviceServiceEntity.getService_name()); //设备的名称
		deviceParam.setOperator_type("5");
		String dataResult = deviceRegisterService.addDevice(deviceParam);
		if(dataResult == null){
			return new ResultEntity(false,"注册设备失败！");
		}
		
		ResultEntity resultEntity = JsonUtils.fromJson(dataResult, ResultEntity.class);
		if(!resultEntity.getState()){
			return new ResultEntity(false,"添加失败");
		}
			
		Map<Object,Object> resultMap  = (Map<Object,Object>) resultEntity.getResult();
		Object operator_idx = resultMap.get("operator_idx");
		if(operator_idx != null){
			deviceServiceEntity.setService_idx((int)operator_idx);
		}
		
		
		Map<String,String> map=new HashMap<>();
		req = JsonUtils.toJson(deviceServiceEntity);
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_deviceServiceRegister), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
		
	}
	

	@Override
	public ResultEntity queryDeviceServiceByPage(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_querydeviceServiceByPage), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
		
	}

	
	public ResultEntity queryDeviceServiceRegister(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceServiceRegister), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	@Override
	public ResultEntity editDeviceServiceRegister(String req) {
		Map<String,String> map=new HashMap<>();
		
		DeviceServiceEntity deviceServiceEntity = JsonUtils.fromJson(req, DeviceServiceEntity.class);
		//获取operator的idx
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("operator_id", deviceServiceEntity.getService_id());
		
		ResultEntity resultEntity = operatorService.selOperatorByOperIdOrIdx(jsonObject.toString());
		Map<Object, Object> resultMap = null;
		if(resultEntity.getResult() != null && resultEntity.getResult().toString().length()> 0
				&&  resultEntity.getState()){
			resultMap = (Map<Object, Object>) resultEntity.getResult();
		}
		
		if(resultMap != null&&!resultMap.isEmpty()){
			//修改白名单ip地址
			jsonObject.clear();
			jsonObject.put("operator_idx",resultMap.get("operator_idx"));
			jsonObject.put("ipaddr", deviceServiceEntity.getRequest_ip());
			map.put("json", jsonObject.toString());
			String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_updIpWhite), map, charset);
			ResultEntity entity = JsonUtils.fromJson(result, ResultEntity.class);
			if(!entity.getState()){
				return new ResultEntity(false,"更新白名单IP地址失败");
			}
		}
		
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_editDeviceServiceRegister), map, charset);
		if(result!=null){ 
			return JsonUtils.fromJson(result, ResultEntity.class);
		} 
		return new ResultEntity();
	}

	@Override
	public ResultEntity deleteDeviceService(String req) {
		
		ResultEntity resultEntity = postUrl(URL_deleteDevOperatorInfoByOperIds, req);
		if(resultEntity != null && resultEntity.getState()){
			Map<Object, Object> map = (Map<Object, Object>) resultEntity.getResult();
			if(map != null && !map.isEmpty()){
				if(map.get("deleteFailDeviceIds") != null){
					return new ResultEntity();
				}
			}
		}
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_deleteDeviceService), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	
	public ResultEntity queryDeviceServiceByParams(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceServiceByParams), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	@Override
	public ResultEntity addCloudDbSyncConfig(String req) {
		
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_addCloudDbSyncConfig), map, charset);
		ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
		
		if(resultEntity.getState()){
			sendToDevice(req);
		}
		return resultEntity;
	}
	
	
	private void sendToDevice(String req){
		try{
			List<SyncConfigEntity> syncConfigEntities = JsonUtils.fromJson(req, new TypeReference<List<SyncConfigEntity>>() {});
			if(syncConfigEntities != null && !syncConfigEntities.isEmpty()){
				SyncConfigEntity configEntity = syncConfigEntities.get(0);
				String lib_id = configEntity.getLib_id();
				String device_id = configEntity.getDevice_id();
				String clientId = JedisUtils.getInstance().get(RedisConstant.CLIENTID+lib_id+":"+device_id);
				CloudSyncRequest request = new CloudSyncRequest();
				request.setClientId(clientId);
				List<ReturnResultEntity> list = new ArrayList<>();
				list.add(MBeanUtils.makeReturnResultEntityFromList(syncConfigEntities, "cloud_dbsync_config"));
				request.setData(list); 
				Map<String,String> map=new HashMap<>();
				map.put("req", JsonUtils.toJson(request));
				HttpClientUtil.doPost(requestURL.getRequestURL(URL_downloadConfig), map, charset);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	public ResultEntity selectCloudDbSyncConfig(String req) {
		
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selectCloudDbSyncConfig), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	@Override
	public ResultEntity updateDeviceServiceOperateTime(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_updateDeviceServiceOperateTime), map, charset);
		return JsonUtils.fromJson(result, ResultEntity.class);
	}
	
	
	
	
	
	

}
