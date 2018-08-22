package com.ssitcloud.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.DeviceServiceQueueDao;
import com.ssitcloud.devmgmt.entity.DeviceServiceQueue;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.utils.MBeanUtil;

@Component(value="device_service_queue")
public class DownDeviceServiceQueueInfo implements TableCommand{

	@Resource
	private DeviceServiceQueueDao deviceServiceQueueDao;

	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String device_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			downcfgSync.getdBName();
			downcfgSync.getKeyName();
			//查询模板信息
			Map<String, String> map = new HashMap<>();
			map.put("device_service_id", device_id);
			
			List<DeviceServiceQueue> deviceServiceQueueInfos=deviceServiceQueueDao.queryQueueInfobyServiceIdxAndQueueId(map);
			if(deviceServiceQueueInfos!=null){
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(deviceServiceQueueInfos,tableName));
				result.setState(true);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
