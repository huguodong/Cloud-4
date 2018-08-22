package com.ssitcloud.datasync.service.impl;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.datasync.entity.ConstsEntity;
import com.ssitcloud.datasync.entity.ControlResultEntity;
import com.ssitcloud.datasync.entity.HeartBeatChangeTableData;
import com.ssitcloud.datasync.entity.HeartBeatDeviceOrder;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.entity.HeartBeatLibraryInfoData;
import com.ssitcloud.datasync.entity.HeartBeatMidVersionData;
import com.ssitcloud.datasync.entity.HeartBeatTransDataState;
import com.ssitcloud.datasync.service.SyncContainerService;
import com.ssitcloud.devmgmt.entity.DeviceOrder;
import com.ssitcloud.devmgmt.entity.FileUploadState;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;
@Service
public class SyncContainerServiceImpl extends BasicServiceImpl implements SyncContainerService{

	
	@Resource(name = "heartBeatDeviceOrder")
	private HeartBeatDeviceOrder heartBeatDeviceOrder;
	
	@Resource(name="heartBeatChangeTableData")
	private HeartBeatChangeTableData heartBeatChangeTableData;
	
	@Resource(name="heartBeatMidVersionData")
	private HeartBeatMidVersionData heartBeatMidVersionData;
	
	@Resource(name="hazelcastInstance")
	private HazelcastInstance hazelcastInstance;
	
	@Resource(name="heartBeatTransDataState")
	private HeartBeatTransDataState heartBeatTransDataState;
	
	@Resource(name="heartBeatFileUploadState")
	private HeartBeatFileUploadState heartBeatFileUploadState;
	
	@Resource(name="heartBeatLibraryInfoData")
	private HeartBeatLibraryInfoData heartBeatLibraryInfoData;
	
	
	//ConstsEntity
	/**
	 * req={
	 * 	container_name:"heartBeatDeviceOrder"
	 *  "library_id":"HHTEST","device_id":"SCH-99999"
	 * }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity QueryContainerInfo(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String, String> m=JsonUtils.fromJson(req, Map.class);
			if(!(m.containsKey("container_name") && m.containsKey("library_id") && m.containsKey("device_id"))){
				result.setState(false);
				result.setMessage("参数不正确!");
				return result;
			}
			String container_name = m.get("container_name");
			String library_id = m.get("library_id");
			String device_id = m.get("device_id");
			String key = library_id + "_" + device_id;
			
			if(container_name.equals("heartBeatDeviceOrder")){
				Queue<DeviceOrder> deviceOrders = heartBeatDeviceOrder.get(device_id);
				result.setResult(deviceOrders);
				result.setState(true);
			}else if(container_name.equals("heartBeatChangeTableData")){
				Queue<TableChangeTriEntity> tableChanges = heartBeatChangeTableData.get(device_id);
				result.setResult(tableChanges);
				result.setState(true);
			}else if(container_name.equals("heartBeatMidVersionData")){
				Queue<TableChangeTriEntity> midVersions = heartBeatMidVersionData.get(device_id);
				result.setResult(midVersions);
				result.setState(true);
			}else if(container_name.equals("heartBeatTransDataState")){
				Queue<Map<String, Object>> transDataState = heartBeatTransDataState.get(key);
				result.setResult(transDataState);
				result.setState(true);
			}else if(container_name.equals("heartBeatFileUploadState")){
				Queue<FileUploadState> fileUploadRs = heartBeatFileUploadState.get(device_id);
				result.setResult(fileUploadRs);
				result.setState(true);
			}else if(container_name.equals("heartBeatLibraryInfoData")){
				Queue<SyncConfigEntity> tableChanges = heartBeatLibraryInfoData.get(device_id);
				result.setResult(tableChanges);
				result.setState(true);
			}else if(container_name.equals(ConstsEntity.CONTROL_RESULT_CONTAINER)){
				IMap<String, ConcurrentLinkedQueue<ControlResultEntity>> controlResultContainer=hazelcastInstance.getMap(ConstsEntity.CONTROL_RESULT_CONTAINER);
				ConcurrentLinkedQueue<ControlResultEntity> queue=controlResultContainer.get(device_id);
				result.setResult(queue);
				result.setState(true);
			}else if(container_name.equals(ConstsEntity.UPLOAD_RUNLOG_CONTAINER)){
				IMap<String, ConcurrentLinkedQueue<ControlResultEntity>> controlResultContainer=hazelcastInstance.getMap(ConstsEntity.UPLOAD_RUNLOG_CONTAINER);
				ConcurrentLinkedQueue<ControlResultEntity> queue=controlResultContainer.get(device_id);
				result.setResult(queue);
				result.setState(true);
			}else if(container_name.equals(ConstsEntity.QUERY_RUNNING_LOG_ORDER)){
				IMap<String, ConcurrentLinkedQueue<ControlResultEntity>> controlResultContainer=hazelcastInstance.getMap(ConstsEntity.QUERY_RUNNING_LOG_ORDER);
				ConcurrentLinkedQueue<ControlResultEntity> queue=controlResultContainer.get(device_id);
				result.setResult(queue);
				result.setState(true);
			}
		}
		return result;
	}

}
