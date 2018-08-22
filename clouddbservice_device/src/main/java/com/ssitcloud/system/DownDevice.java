package com.ssitcloud.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.entity.sync.DeviceSyncEntity;
import com.ssitcloud.utils.MBeanUtil;

@Component(value="device")
public class DownDevice implements TableCommand{
	
	public static final String tableName="device";

	@Resource
	private DeviceConfigDao configDao;

	@Resource
	private DeviceDao deviceDao;
	
	//@Resource
	//private DeviceAcsLoginInfoDao deviceAcsLoginInfoDao;

	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String library_id=downcfgSync.getLib_id();
			String device_id=downcfgSync.getDevice_id();
			//downcfgSync.getTable();
			//downcfgSync.getdBName();
			//downcfgSync.getKeyName();
			List<DeviceSyncEntity> devices=deviceDao.selectByid(new DeviceSyncEntity(device_id));
			//注释信息  ACS登陆信息
			//List<DeviceAcsLoginInfoRemoteEntity> deviceAcsLoginInfoRemotes=deviceAcsLoginInfoDao.queryAcsInfoByDeviceId(device_id);
			if(devices!=null&&devices.size()>0){
				for(DeviceSyncEntity device:devices){
					device.setLibrary_id(library_id);
					//device.setDevice_acs_loginInfo(JsonUtils.toJson(deviceAcsLoginInfoRemotes));
				}
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(devices,tableName));
			}
			result.setState(true);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
}
