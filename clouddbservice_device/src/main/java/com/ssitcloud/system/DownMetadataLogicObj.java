package com.ssitcloud.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.dao.MetadataLogicObjDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.entity.MetadataLogicObjEntity;
import com.ssitcloud.system.entity.MetadataLogicObjRemoteEntity;
import com.ssitcloud.utils.MBeanUtil;

@Component(value="metadata_logic_obj")
public class DownMetadataLogicObj implements TableCommand{

	@Resource
	private MetadataLogicObjDao metadataLogicObjDao;
	
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
			//
			List<DeviceConfigEntity> devconfs = configDao.queryDeviceConfigByDevIdAndLibIdx(map);
			//one
			if(devconfs!=null&&devconfs.size()>0){
				DeviceConfigEntity deviceConf=devconfs.get(0);
				if(deviceConf!=null){
				Integer	 ext_tpl_flg=deviceConf.getDevice_ext_tpl_flg();
				DeviceExtConfigEntity deviceExtConfig=new DeviceExtConfigEntity();
				if(DeviceConfigEntity.IS_MODEL.equals(ext_tpl_flg)){
						//模板,则获取模板ID
						Integer ext_tpl_idx=deviceConf.getDevice_ext_tpl_idx();
						//模板的话注释掉
						//deviceExtConfig.setLibrary_idx(Integer.parseInt(library_idx));
						deviceExtConfig.setDevice_ext_id(ext_tpl_idx);
						deviceExtConfig.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
						List<MetadataLogicObjRemoteEntity> metadataLogicObjs=metadataLogicObjDao.selectListByDeviceExtConfig(deviceExtConfig);
						if(metadataLogicObjs!=null){
							result.setResult(MBeanUtil.makeReturnResultEntityFromList(metadataLogicObjs,tableName));
							result.setState(true);
						}
					}else if(DeviceConfigEntity.IS_NOT_MODEL.equals(ext_tpl_flg)){
						//不是模板
						String device_idx=deviceDao.selectDeviceIdx(new DeviceEntity(device_id));
						deviceExtConfig.setLibrary_idx(Integer.parseInt(library_idx));
						deviceExtConfig.setDevice_ext_id(Integer.parseInt(device_idx));
						deviceExtConfig.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
						List<MetadataLogicObjRemoteEntity> metadataLogicObjs=metadataLogicObjDao.selectListByDeviceExtConfig(deviceExtConfig);
						if(metadataLogicObjs!=null){
							//result.setResult(metadataLogicObjs);
							result.setResult(MBeanUtil.makeReturnResultEntityFromList(metadataLogicObjs,tableName));
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
