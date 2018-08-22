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
import com.ssitcloud.dao.MetaRunConfigDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.system.entity.MetaRunConfigRemoteEntity;
import com.ssitcloud.utils.MBeanUtil;
/**
 * metadata_run_config 同步到设备端
 * @author lbh
 *
 */
@Component(value="metadata_run_config")
public class DownMetadataRunConfig implements TableCommand{

	@Resource
	private MetaRunConfigDao metaRunConfigDao;
	
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
				Integer	 run_tpl_flg=deviceConf.getDevice_run_tpl_flg();
				DeviceRunConfigEntity deviceRunConfig=new DeviceRunConfigEntity();
					if(DeviceConfigEntity.IS_MODEL.equals(run_tpl_flg)){
						//模板,则获取模板ID
						Integer run_tpl_idx=deviceConf.getDevice_run_tpl_idx();
						//模板注释掉library_idx
						//deviceRunConfig.setLibrary_idx(Integer.parseInt(library_idx));
						deviceRunConfig.setDevice_run_id(run_tpl_idx);
						deviceRunConfig.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
						List<MetaRunConfigRemoteEntity> metaRunConfigs=metaRunConfigDao.selectListByDeviceRunConfig(deviceRunConfig);
						
						if(metaRunConfigs!=null){
							result.setResult(MBeanUtil.makeReturnResultEntityFromList(metaRunConfigs,tableName));
							result.setState(true);
						}
					}else if(DeviceConfigEntity.IS_NOT_MODEL.equals(run_tpl_flg)){
						//不是模板
						String device_idx=deviceDao.selectDeviceIdx(new DeviceEntity(device_id));
						deviceRunConfig.setLibrary_idx(Integer.parseInt(library_idx));
						deviceRunConfig.setDevice_run_id(Integer.parseInt(device_idx));
						deviceRunConfig.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA);

						List<MetaRunConfigRemoteEntity> metaRunConfigs=metaRunConfigDao.selectListByDeviceRunConfig(deviceRunConfig);
						
						if(metaRunConfigs!=null){
							result.setResult(MBeanUtil.makeReturnResultEntityFromList(metaRunConfigs,tableName));
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
