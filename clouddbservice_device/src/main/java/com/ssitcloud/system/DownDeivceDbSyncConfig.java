package com.ssitcloud.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceDBSyncConfDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncConfigEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.system.entity.DeviceDBSyncConfigRemoteEntity;
import com.ssitcloud.utils.MBeanUtil;
/**
 * 同步 device_dbsync_config表数据
 * @author lbh
 *
 */
@Component(value="device_dbsync_config")
public class DownDeivceDbSyncConfig implements TableCommand{
	
	@Resource
	private DeviceDBSyncConfDao confDao;
	
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
				Integer	 dbsync_tpl_flg=deviceConf.getDevice_dbsync_tpl_flg();
				DeviceDBSyncConfigEntity deviceDbSyncConfig=new DeviceDBSyncConfigEntity();
					if(DeviceConfigEntity.IS_MODEL.equals(dbsync_tpl_flg)){
						//模板,则获取模板ID
						Integer dbsync_tpl_idx=deviceConf.getDevice_dbsync_tpl_idx();
						//模板注释掉library_idx
						//deviceDbSyncConfig.setLibrary_idx(Integer.parseInt(library_idx));
						deviceDbSyncConfig.setDevice_dbsync_id(dbsync_tpl_idx);
						deviceDbSyncConfig.setDevice_tpl_type(DeviceDBSyncConfigEntity.IS_MODEL);
						List<DeviceDBSyncConfigRemoteEntity> deviceDbSyncConfigs=confDao.selectListByRemoteDeviceSync(deviceDbSyncConfig);
						if(deviceDbSyncConfigs!=null){
							result.setResult(MBeanUtil.makeReturnResultEntityFromList(deviceDbSyncConfigs,tableName));
							result.setState(true);
						}
					}else if(DeviceConfigEntity.IS_NOT_MODEL.equals(dbsync_tpl_flg)){
						//不是模板
						String device_idx=deviceDao.selectDeviceIdx(new DeviceEntity(device_id));
						deviceDbSyncConfig.setLibrary_idx(Integer.parseInt(library_idx));
						deviceDbSyncConfig.setDevice_dbsync_id(Integer.parseInt(device_idx));//device_tpl_type
						deviceDbSyncConfig.setDevice_tpl_type(DeviceDBSyncConfigEntity.IS_NOT_MODEL);
						
						List<DeviceDBSyncConfigRemoteEntity> deviceDbSyncConfigs=confDao.selectListByRemoteDeviceSync(deviceDbSyncConfig);
				
						if(deviceDbSyncConfigs!=null){
							result.setResult(MBeanUtil.makeReturnResultEntityFromList(deviceDbSyncConfigs,tableName));
							result.setState(true);
						}
					}
				}
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
