package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.LibraryEntity;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.PropertiesUtil;
import com.ssitcloud.common.utils.HttpClientUtil;
import com.ssitcloud.dao.DeviceServiceDao;
import com.ssitcloud.dao.DeviceServiceQueueDao;
import com.ssitcloud.dao.RelOperatorGroupDao;
import com.ssitcloud.devmgmt.entity.DeviceServiceBaseEntity;
import com.ssitcloud.devmgmt.entity.DeviceServiceEntity;
import com.ssitcloud.devmgmt.entity.DeviceServicePageEntity;
import com.ssitcloud.devmgmt.entity.DeviceServiceQueue;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;
import com.ssitcloud.entity.RelOperatorGroupEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.service.DeviceServiceService;
import com.ssitcloud.system.entity.ReturnResultEntity;
import com.ssitcloud.utils.MBeanUtil;

import net.sf.json.util.JSONUtils;

@Service
public class DeviceServiceServiceImpl implements DeviceServiceService {
	
	@Resource
	private DeviceServiceDao deviceServiceDao;
	@Resource
	private DeviceServiceQueueDao queueDao;
	@Resource
	private RelOperatorGroupDao operatorGroupDao;
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	public ResultEntity deviceServiceRegister(String req) {
		
		
		DeviceServiceEntity deviceServiceEntity = JsonUtils.fromJson(req, DeviceServiceEntity.class);
		//鉴权表中设备服务的主键id
		int operatorId = deviceServiceEntity.getService_idx();
		
		List<DeviceServiceQueue> queues = deviceServiceEntity.getQueueList();
		Map<String, String> map = new HashMap<>();
		map.put("service_id", deviceServiceEntity.getService_id());
		synchronized (DeviceAcsLoginInfoServiceImpl.class) {
			List<DeviceServiceEntity> deviceServiceEntities = deviceServiceDao.queryDeviceServiceByParams(map);
			if(deviceServiceEntities != null && !deviceServiceEntities.isEmpty()){
				if(deviceServiceEntity.getService_id().equals(deviceServiceEntities.get(0).getService_id()))
					return new ResultEntity(false,"服务ID重复");
			}
			
			deviceServiceEntity.setOperate_time(new Date());
			DeviceServiceEntity serviceEntity = deviceServiceDao.insertDeviceService(deviceServiceEntity);
			
			String login_name = PropertiesUtil.getValue("rabbitmq.username");
			String login_pwd = PropertiesUtil.getValue("rabbitmq.password");
			if(login_name == null){
				login_name = "comomusr";
			}
			if(login_pwd == null){
				login_pwd = "5HkMenUwc4pf2mgFp9fGKw==";
			}
			for(DeviceServiceQueue queue : queues){
				queue.setQueue_login_name(login_name.trim());
				queue.setQueue_login_pwd(login_pwd.trim());
				queue.setDevice_service_idx(serviceEntity.getService_idx());//服务表中的主键id
				queue.setDevice_service_id(serviceEntity.getService_id());
				queueDao.insertDeviceServiceQueue(queue);
			}
		}
		
		//新增的设备用户与operator_group建立联系，operator_group的idx 为1 、对rel_operator_group表进行操作
		RelOperatorGroupEntity groupEntity = new RelOperatorGroupEntity();
		groupEntity.setLibrary_idx(0);//图书馆idx设为0
		groupEntity.setOperator_idx(operatorId);
		groupEntity.setOperator_group_idx(1);//operator_group的idx 为1
		operatorGroupDao.insert(groupEntity);
		downloadDeviceServiceInfo(deviceServiceEntity.getService_id(),deviceServiceEntity.getLibrary_id());
		return new ResultEntity(true,"注册成功！");
	}
	
	
	public CloudSyncRequest downloadDeviceServiceInfo(String device_id,String library_id){
		try{
			CloudSyncRequest cloudSyncRequest = new CloudSyncRequest();
			//获取clientId
			String clientId = cloudSyncRequest.getCacheClient(library_id, device_id);
			cloudSyncRequest.setClientId(clientId);
			//LibraryEntity libraryEntity = JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY,library_id,LibraryEntity.class);
			//if(libraryEntity == null)  return cloudSyncRequest;
			//下发设备服务信息
			Map<String, String> map = new HashMap<>();
			List<ReturnResultEntity> list = new ArrayList<>();
			map.put("service_id", device_id);
			List<DeviceServiceBaseEntity> deviceServiceEntityInfos = deviceServiceDao.queryDeviceServiceByServiceId(map);
			if(deviceServiceEntityInfos!=null){
				list.add(MBeanUtil.makeReturnResultEntityFromList(deviceServiceEntityInfos,"device_service"));
				cloudSyncRequest.setData(list);
				postUrl("downloadConfig",JsonUtils.toJson(cloudSyncRequest));
				System.out.println(JsonUtils.toJson(cloudSyncRequest));
			}
			
			//下发队列
			map.clear();
			list.clear();
			map.put("device_service_id", device_id);
			List<DeviceServiceQueue> deviceServiceQueueInfos=queueDao.queryQueueInfobyServiceIdxAndQueueId(map);
			if(deviceServiceQueueInfos!=null){
				list.add(MBeanUtil.makeReturnResultEntityFromList(deviceServiceQueueInfos,"device_service_queue"));
				cloudSyncRequest.setData(list);
				postUrl("downloadConfig",JsonUtils.toJson(cloudSyncRequest));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	
	private ResultEntity checkQueue(List<DeviceServiceQueue> deviceServiceQueues){
		Map<String, String> tempMap = new HashMap<>();
		Map<String, String> params = new HashMap<>();
		String message = "";
		boolean state = true;
		for(DeviceServiceQueue queue : deviceServiceQueues){
			if(tempMap.containsKey(queue.getQueue_ip())){
				return new ResultEntity(false,"队列ID重复");
			}
			tempMap.put(queue.getQueue_id(),queue.getQueue_id());
			params.clear();
			params.put("queue_id", queue.getQueue_id());
			List<DeviceServiceQueue> queues = queueDao.queryDeviceServiceQueue(params);
			for(DeviceServiceQueue serviceQueue : queues){
				if(queue.getQueue_id().equals(serviceQueue.getQueue_id())
						&&queue.getQueue_idx() != serviceQueue.getQueue_idx()){
					message = "队列ID重复";
					state = false;
					break;
				}
			}
			
		}
		tempMap.clear();
		return new ResultEntity(state  ,message);
	}

	public ResultEntity queryDeviceServiceByPage(String req){
		
		DeviceServicePageEntity pageEntity = new DeviceServicePageEntity();
		if(req != null && req.length() > 0){
			pageEntity = JsonUtils.fromJson(req, DeviceServicePageEntity.class);
		}
		DeviceServicePageEntity deviceServicePageEntity = deviceServiceDao.queryDeviceServiceByPage(pageEntity);
		ResultEntity entity = new ResultEntity();
		entity.setResult(deviceServicePageEntity);
		entity.setState(true);
		return entity;
		
	}



	
	public ResultEntity queryDeviceServiceByParams(String req) {
		Map<String,String> params = new HashMap<>();
		ResultEntity resultEntity = new ResultEntity();
		if(req != null && req.length() > 0){
			params = JsonUtils.fromJson(req, Map.class);
		}
		List<DeviceServiceEntity> deviceServiceEntities = deviceServiceDao.queryDeviceServiceByParams(params);
		resultEntity.setResult(deviceServiceEntities);
		resultEntity.setState(true);
		return resultEntity;
	}
	
	
	public ResultEntity queryDeviceServiceRegister(String req){
		Map<String,String> params = new HashMap<>();
		ResultEntity resultEntity = new ResultEntity();
		if(req != null && req.length() > 0){
			params = JsonUtils.fromJson(req, Map.class);
		}
		
		List<DeviceServiceEntity> deviceServiceEntities = deviceServiceDao.queryDeviceServiceByParams(params);
		if(deviceServiceEntities != null && !deviceServiceEntities.isEmpty()){
			Map<String, String> map = new HashMap<>();
			for(DeviceServiceEntity deviceServiceEntity : deviceServiceEntities){
				map.put("device_service_idx", deviceServiceEntity.getService_idx()+"");
				List<DeviceServiceQueue> deviceServiceQueues = queueDao.queryDeviceServiceQueue(map);
				deviceServiceEntity.setQueueList(deviceServiceQueues);
			}
		}
		
		resultEntity.setResult(deviceServiceEntities);
		resultEntity.setState(true);
		return resultEntity;
		
	}
	
	
	
	
	
	
	/**
	 * 根据libidx获取特殊设备（3D导航、人流量、智能家具） 分组
	 * @return ResultEntity
	 */
	@Override
	public ResultEntity queryServiceIdbyLibIdx() {
		ResultEntity result=new ResultEntity();
		//Map<Integer,String>//device_id逗号分割
		Map<Integer,String> libIdxAndDevIds=deviceServiceDao.queryServiceIdbyLibIdx();
		result.setState(true);
		result.setResult(libIdxAndDevIds);
		return result;
	}
	
	@Override
	public ResultEntity queryServiceIdbyLibId() {
		ResultEntity result=new ResultEntity();
		//Map<Integer,String>//device_id逗号分割
		Map<String,String> libIdxAndDevIds=deviceServiceDao.queryServiceIdbyLibId();
		result.setState(true);
		result.setResult(libIdxAndDevIds);
		return result;
	}

	@Override
	public ResultEntity editDeviceServiceRegister(String req) {
		DeviceServiceEntity deviceServiceEntity = JsonUtils.fromJson(req, DeviceServiceEntity.class);
		List<DeviceServiceQueue> deviceServiceQueues = deviceServiceEntity.getQueueList();
		ResultEntity resultEntity = new ResultEntity();
		deviceServiceEntity.setOperate_time(new Date());
		deviceServiceDao.editDeviceService(deviceServiceEntity);
		Map<String, String> map = new HashMap<>();
		map.put("device_service_idx", deviceServiceEntity.getService_idx()+"");
		//queueDao.deleteDeviceServiceQueue(map);
		List<DeviceServiceQueue> queues = queueDao.queryDeviceServiceQueue(map);//服务总的队列
		List<DeviceServiceQueue> resultQueue = new ArrayList<>();
		Map<String,DeviceServiceQueue> tempMap = new HashMap<>();
		
		String login_name = PropertiesUtil.getValue("rabbitmq.username");
		String login_pwd = PropertiesUtil.getValue("rabbitmq.password");
		if(login_name == null){
			login_name = "comomusr";
		}
		if(login_pwd == null){
			login_pwd = "5HkMenUwc4pf2mgFp9fGKw==";
		}
		for(DeviceServiceQueue deviceServiceQueue : deviceServiceQueues){
			deviceServiceQueue.setQueue_login_name(login_name.trim());
			deviceServiceQueue.setQueue_login_pwd(login_pwd.trim());
			deviceServiceQueue.setDevice_service_idx(deviceServiceEntity.getService_idx());
			deviceServiceQueue.setDevice_service_id(deviceServiceEntity.getService_id());
			if(deviceServiceQueue.getQueue_idx() == -1){
				DeviceServiceQueue serviceQueue = queueDao.insertDeviceServiceQueue(deviceServiceQueue);
				resultQueue.add(serviceQueue);
			}else{
				queueDao.editDeviceServiceQueue(deviceServiceQueue);
			}
			tempMap.put(deviceServiceQueue.getQueue_idx()+"", deviceServiceQueue);
			
		}
		
		for(DeviceServiceQueue serviceQueue : queues){
			if(!tempMap.containsKey(serviceQueue.getQueue_idx()+"")){
				map.clear();
				map.put("queue_idx", serviceQueue.getQueue_idx()+"");
				queueDao.deleteDeviceServiceQueue(map);
			}
		}
		
		downloadDeviceServiceInfo(deviceServiceEntity.getService_id(), deviceServiceEntity.getLibrary_id());
		resultEntity.setState(true);
		resultEntity.setResult(resultQueue);
		return resultEntity;
	}



	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity deleteDeviceService(String req) {
		if(req == null || req.length() <=0)
			return new ResultEntity();
		List<DeviceServiceEntity> deviceServiceEntities = JsonUtils.fromJson(req, new TypeReference<List<DeviceServiceEntity>>(){});
		Map<String, String> map = new HashMap<>();
		for(DeviceServiceEntity deviceServiceEntity : deviceServiceEntities){
			map.clear();
			map.put("service_idx", deviceServiceEntity.getService_idx()+"");
			deviceServiceDao.deleteDeviceService(map);
			map.put("device_service_idx", deviceServiceEntity.getService_idx()+"");
			queueDao.deleteDeviceServiceQueue(map);
		}
		return new ResultEntity(true,"删除成功");
	}
	
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx获取服务名称 分组serviceid
	 * @return ResultEntity
	 */
	public ResultEntity queryServiceIdbyServiceIdx() {
		ResultEntity result=new ResultEntity();
		//Map<Integer,String>//device_id逗号分割
		Map<Integer,String> libIdxAndDevIds=deviceServiceDao.queryServiceIdbyServiceIdx();
		result.setState(true);
		result.setResult(libIdxAndDevIds);
		return result;
	}


	@Override
	public ResultEntity updateDeviceServiceOperateTime(String req) {
		DeviceServiceEntity deviceServiceEntity = new DeviceServiceEntity();
		if(req != null && req.length() > 0){
			deviceServiceEntity = JsonUtils.fromJson(req, DeviceServiceEntity.class);
		}
		deviceServiceEntity.setOperate_time(new Date());
		deviceServiceDao.updateDeviceServiceOperateTime(deviceServiceEntity);
		return new ResultEntity(true,"更新成功");
	}


	@Override
	public ResultEntity selectCountDeviceService(String req) {
		Map<String, String> map = new HashMap<>();
		ResultEntity resultEntity = new ResultEntity();
		if(!StringUtils.isEmpty(req)){
			map = JsonUtils.fromJson(req, Map.class);
		}
		int count = deviceServiceDao.selectCountDeviceService(map);
		resultEntity.setResult(count);
		resultEntity.setState(true);
		return resultEntity;
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
