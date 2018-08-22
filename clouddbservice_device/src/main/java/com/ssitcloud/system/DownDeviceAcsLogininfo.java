package com.ssitcloud.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.DeviceAcsLoginInfoDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.datasync.entity.DeviceAcsModuleEntity;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.service.DeviceAcsLoginInfoService;
import com.ssitcloud.system.entity.DeviceAcsLoginInfoRemoteEntity;
import com.ssitcloud.utils.MBeanUtil;

@Component(value="device_acs_logininfo")
public class DownDeviceAcsLogininfo implements TableCommand{

	/*
	 * @Resource private DeviceDao deviceDao;
	 * 
	 * @Resource private DeviceAcsLoginInfoDao deviceAcsLoginInfoDao;
	 */

	@Resource
	private DeviceAcsLoginInfoService deviceAcsLoginInfoService;

	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String lib_id=downcfgSync.getLibrary_id();
			String device_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			downcfgSync.getdBName();
			downcfgSync.getKeyName();
			//查询模板信息
			Map<String, Object> map = new HashMap<>();
			map.put("device_id", device_id);
			map.put("lib_idx", lib_id);
			
			/*List<DeviceAcsLoginInfoRemoteEntity> deviceAcsLoginInfoRemotes=deviceAcsLoginInfoDao.queryAcsInfoByDeviceId(device_id);
			if(deviceAcsLoginInfoRemotes!=null){
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(deviceAcsLoginInfoRemotes,tableName));
				result.setState(true);
			}*/
			// ever为xml返回
			List<DeviceAcsModuleEntity> deviceAcsModules = deviceAcsLoginInfoService.loadAcsXml(lib_id, device_id);
			// 将ever转换成xml文件，并上传到固定目录

			result.setResult(deviceAcsModules);

		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
