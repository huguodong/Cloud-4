package com.ssitcloud.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.dao.DeviceMonConfDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceMonConfigEntity;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.utils.MBeanUtil;

@Component(value="device_monitor_config")
public class DownDeviceMonitorConfig implements TableCommand{
		
	@Resource
	private DeviceMonConfDao confDao;
	
	@Resource
	private DeviceConfigDao configDao;

	@Resource
	private DeviceDao deviceDao;

	@Override
	public ResultEntity execute(DownloadCfgSyncEntity downcfgSync) {
		ResultEntity result=new ResultEntity();
		try {
			String library_idx=downcfgSync.getLibrary_id();
			String device_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			downcfgSync.getdBName();
			downcfgSync.getKeyName();
			//查询模板信息
			Map<String, Object> map = new HashMap<>();
			map.put("device_id", device_id);
			map.put("library_id", library_idx);
			List<DeviceConfigEntity> devconfs = configDao.queryDeviceConfigByDevIdAndLibIdx(map);
			//one
			if(devconfs!=null&&devconfs.size()>0){
				DeviceConfigEntity deviceConf=devconfs.get(0);
				if(deviceConf!=null){
				Integer	 mon_tpl_flg=deviceConf.getDevice_monitor_tpl_flg();
				DeviceMonConfigEntity deviceMonConfig=new DeviceMonConfigEntity();
					
				if(DeviceConfigEntity.IS_MODEL.equals(mon_tpl_flg)){
						//模板,则获取模板ID
						Integer mon_tpl_idx=deviceConf.getDevice_monitor_tpl_idx();
						//模板注释掉library_idx
						//deviceMonConfig.setLibrary_idx(Integer.parseInt(library_idx));
						deviceMonConfig.setDevice_mon_tpl_idx(mon_tpl_idx);
						deviceMonConfig.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
						List<DeviceMonConfigEntity> devMonConfigs=confDao.selectList(deviceMonConfig);
						if(devMonConfigs!=null){
							result.setState(true);
							result.setResult(MBeanUtil.makeReturnResultEntityFromList(devMonConfigs,tableName));
						}
					}else if(DeviceConfigEntity.IS_NOT_MODEL.equals(mon_tpl_flg)){
						//不是模板
						String device_idx=deviceDao.selectDeviceIdx(new DeviceEntity(device_id));
						deviceMonConfig.setLibrary_idx(Integer.parseInt(library_idx));
						deviceMonConfig.setDevice_mon_tpl_idx(Integer.parseInt(device_idx));
						deviceMonConfig.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
						List<DeviceMonConfigEntity> devMonConfigs=confDao.selectList(deviceMonConfig);
						if(devMonConfigs!=null){
							result.setResult(MBeanUtil.makeReturnResultEntityFromList(devMonConfigs,tableName));
							result.setState(true);
						}
					}
				}
			}
		
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return result;
	}
}
