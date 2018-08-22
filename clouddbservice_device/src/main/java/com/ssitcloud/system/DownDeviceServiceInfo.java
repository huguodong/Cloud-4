package com.ssitcloud.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.DeviceServiceDao;
import com.ssitcloud.devmgmt.entity.DeviceServiceBaseEntity;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.utils.MBeanUtil;

@Component(value="device_service")
public class DownDeviceServiceInfo implements TableCommand{

	@Resource
	private DeviceServiceDao deviceServiceDao;

	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String library_idx=downcfgSync.getLibrary_id();
			String device_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			//查询模板信息
			Map<String, String> map = new HashMap<>();
			map.put("service_id", device_id);
			map.put("library_id", library_idx);
			
			List<DeviceServiceBaseEntity> deviceServiceEntityInfos=deviceServiceDao.queryDeviceServiceByServiceId(map);
			if(deviceServiceEntityInfos!=null){
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(deviceServiceEntityInfos,tableName));
				result.setState(true);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
