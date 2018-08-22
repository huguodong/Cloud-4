package com.ssitcloud.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.MetadataExtModelDao;
import com.ssitcloud.dao.MetadataLogicObjDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.MetadataExtModelEntity;
import com.ssitcloud.entity.MetadataLogicObjEntity;
import com.ssitcloud.entity.UploadcfgSynEntity;
import com.ssitcloud.entity.UploadcfgSynResultEntity;
import com.ssitcloud.service.UploadCfgSyncService;

@Component("uploadCfgSync_device_ext_config")
public class UploadCfgSyncDeviceExtConfig extends DeviceConfigServiceImpl implements UploadCfgSyncService{
	@Resource
	private MetadataLogicObjDao metadataLogicObjDao;
	@Resource
	private MetadataExtModelDao metadataExtModelDao;
	
	private Lock lock = new ReentrantLock();// 锁对象  
	
	public static void main(String[] args) {
		String s="{\"library_id\":\"1\",\"device_id\":\"SSL001\",\"fields\":{\"logic_obj\":\"1\",\"ext_model\":\"2\",\"ext_comm_conf\":\"3\",\"ext_extend_conf\":\"4\",\"updatetime\":\"5\"},\"table\":\"device_ext_config\",\"records\":[{\"1\":\"AuthorityReader\",\"2\":\"RR-3036\",\"3\":{\"TYPE\":\"com\",\"COM\":\"1\",\"BAUD\":\"9600\"},\"4\":\"\",\"5\":\"\"}]}";
		UploadcfgSynEntity UploadcfgSyn = JsonUtils.fromJson(s,UploadcfgSynEntity.class);
		List<Map<String, Object>> list=comboFielsAndValue(UploadcfgSyn.getFields(), UploadcfgSyn.getRecords());
		for(Map<String, Object> m:list){
		}
	}
	@Override
	public ResultEntityF<UploadcfgSynResultEntity> execute(UploadcfgSynEntity uploadcfgSyn) {
		ResultEntityF<UploadcfgSynResultEntity> result=new ResultEntityF<UploadcfgSynResultEntity>();
		try {
			Map<String, Object> fields=uploadcfgSyn.getFields();
			List<Map<String, Object>> records=uploadcfgSyn.getRecords();
			String device_id = uploadcfgSyn.getDevice_id();
			String library_idx = uploadcfgSyn.getLibrary_id();//已经在业务层将 library_id改成 library_idx
			if(fields==null||records==null){
				result.setState(false);
				result.setMessage(Const.FAILED);
				result.setRetMessage("fields is null or records is null");
				return result;
			}
			records=comboFielsAndValue(uploadcfgSyn.getFields(), uploadcfgSyn.getRecords());
			
			if (StringUtils.hasText(device_id) && StringUtils.hasText(library_idx)) {
				Map<String, Object> map = new HashMap<>();
				map.put("device_id", device_id);
				map.put("library_id", library_idx);
				List<DeviceConfigEntity> devconfs = queryDeviceConfigByDevIdAndLibIdx(map);
				// list assert only one data
				if (devconfs != null && devconfs.size() > 0) {
					DeviceConfigEntity devconf = devconfs.get(0);
					if (devconf != null) {
						// 如果原来的是模板，则重新复制一份
						// 如果原来不是模板，则修改模板
						// 取到Idx
						Integer device_idx=devconf.getDevice_idx();
						//Integer device_run_tpl_idx=devconf.getDevice_run_tpl_idx();//模板idx
						Integer device_ext_tpl_idx=devconf.getDevice_ext_tpl_idx();
						//String library_idx=devconf.getLibrary_idx();
						Integer ext_tpl_flg=devconf.getDevice_ext_tpl_flg();//1 模板 0 不是模板
						int isUpd=0;//数据更新结果
						if(ext_tpl_flg==0){
							/**
							 * 先判断数据库中是否存在设备的自定义数据
							 */
							DeviceExtConfigEntity queryDeviceExt=new DeviceExtConfigEntity();
							queryDeviceExt.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
							queryDeviceExt.setDevice_ext_id(device_idx);
							queryDeviceExt.setLibrary_idx(Integer.parseInt(library_idx));
							Integer count=deviceExtConfDao.selectDataTypeCount(queryDeviceExt);
							if(count!=null&&count>0){
								try {
									lock.lock();
									int delNum=deviceExtConfDao.deleteDeviceExtConfig(queryDeviceExt);
									if(delNum!=count){
										throw new RuntimeException("[设备端]device_ext_config--->更新运行配置失败，删除记录数不符合条件！");
									}
								} catch (Exception e) {
									throw new RuntimeException(e);
								}finally{
									if(lock!=null)
										lock.unlock();
								}
							}
							for(Map<String,Object> updMap:records){
								DeviceExtConfigEntity deviceExt=new DeviceExtConfigEntity();
								deviceExt.setDevice_ext_id(device_idx);
								deviceExt.setLibrary_idx(Integer.parseInt(library_idx));
								//logic_obj
								deviceExt.setLogic_obj(updMap.get("logic_obj")==null?"":updMap.get("logic_obj").toString());
								MetadataLogicObjEntity metadataLogicObjEntity=new MetadataLogicObjEntity();
								metadataLogicObjEntity.setLogic_obj(updMap.get("logic_obj")==null?"":updMap.get("logic_obj").toString());
								List<MetadataLogicObjEntity> metadataLogicObjs=metadataLogicObjDao.select(metadataLogicObjEntity);
								if(CollectionUtils.isNotEmpty(metadataLogicObjs)){
									deviceExt.setLogic_obj_idx(metadataLogicObjs.get(0).getMeta_log_obj_idx());
								}else{
									throw new RuntimeException("[设备端]device_ext_config<--->[服务端]插入运行配置信息 device_ext_config table insert fail:"+updMap.get("logic_obj").toString()+"没有找到对应的ID，请检查metadata_ext_model表中的字段名是否一致");
								}
								deviceExt.setExt_comm_conf(updMap.get("ext_comm_conf")==null?"":updMap.get("ext_comm_conf").toString());
								deviceExt.setExt_model(updMap.get("ext_model")==null?"":updMap.get("ext_model").toString());
								MetadataExtModelEntity metadataExtModel=new MetadataExtModelEntity();
								metadataExtModel.setExt_model(updMap.get("ext_model")==null?"":updMap.get("ext_model").toString());
								List<MetadataExtModelEntity> metadataExts=metadataExtModelDao.select(metadataExtModel);
								if(CollectionUtils.isNotEmpty(metadataExts)){
									deviceExt.setExt_model_idx(metadataExts.get(0).getMeta_ext_idx());
								}else{
									throw new RuntimeException("[设备端]device_ext_config<--->[服务端]插入运行配置信息 device_ext_config table insert fail:"+updMap.get("ext_model").toString()+"没有找到对应的ID，请检查 metadata_ext_model表中的字段名是否一致");
								}
								deviceExt.setExt_extend_conf(updMap.get("ext_extend_conf")==null?"":updMap.get("ext_extend_conf").toString());
								deviceExt.setUpdatetime(new Timestamp(System.currentTimeMillis()));
								deviceExt.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
								isUpd=deviceExtConfDao.insertDeviceExtConfig(deviceExt);
								if(isUpd<=0){//插入不成功抛异常
									throw new RuntimeException("[设备端]device_ext_config<--->[服务端]插入运行配置信息 device_ext_config table insert fail");
								}
								isUpd=ifExistDeviceConfigValUpdate(updMap, device_idx);
							}
							result.setResult(new UploadcfgSynResultEntity(device_id, library_idx, UploadcfgSynResultEntity.ResponseResult_success, ""));
							result.setState(true);
							
						}else if(ext_tpl_flg==1){
							//是模板，
							//1更新之后重新复制一份,（通过lib_id和dev_idx 查询出来设备的所有设置）
							//2.然后再device_config中设置不是模板
							DeviceExtConfigEntity deviceExtConfig=new DeviceExtConfigEntity();
							deviceExtConfig.setDevice_ext_id(device_ext_tpl_idx);
							deviceExtConfig.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
							
							List<DeviceExtConfigEntity> deviceExtConfigs=deviceExtConfDao.queryDeviceExtConfigAndExtModel(deviceExtConfig);
							if(deviceExtConfigs==null||deviceExtConfigs.size()<=0){
								throw new RuntimeException("[设备端]device_ext_config<--->[服务端]查询不到模板运行配置");
							}
							/**
							 * 先判断数据库中是否存在设备的自定义数据
							 */
							DeviceExtConfigEntity queryDeviceExt=new DeviceExtConfigEntity();
							queryDeviceExt.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
							queryDeviceExt.setDevice_ext_id(device_idx);
							queryDeviceExt.setLibrary_idx(Integer.parseInt(library_idx));
							Integer count=deviceExtConfDao.selectDataTypeCount(queryDeviceExt);
							if(count!=null&&count>0){
								int delNum=deviceExtConfDao.deleteDeviceExtConfig(queryDeviceExt);
								if(delNum!=count){
									throw new RuntimeException("[设备端]device_ext_config--->更新运行配置失败，删除记录数不符合条件！");
								}
							}
							/**
							 * 1.复制模板，(将TYPE改为非模板)XXXX
							 *  将他们上传的数据直接插入数据库，不用复制
							 */
							for(Map<String,Object> updMap:records){
								DeviceExtConfigEntity deviceExt=new DeviceExtConfigEntity();
								deviceExt.setLibrary_idx(Integer.parseInt(library_idx));
								deviceExt.setDevice_ext_id(device_idx);
								deviceExt.setExt_comm_conf(updMap.get("ext_comm_conf")==null?"":updMap.get("ext_comm_conf").toString());
								deviceExt.setLogic_obj(updMap.get("logic_obj")==null?"":updMap.get("logic_obj").toString());
								MetadataLogicObjEntity metadataLogicObjEntity=new MetadataLogicObjEntity();
								metadataLogicObjEntity.setLogic_obj(updMap.get("logic_obj")==null?"":updMap.get("logic_obj").toString());
								List<MetadataLogicObjEntity> metadataLogicObjs=metadataLogicObjDao.select(metadataLogicObjEntity);
								if(CollectionUtils.isNotEmpty(metadataLogicObjs)){
									deviceExt.setLogic_obj_idx(metadataLogicObjs.get(0).getMeta_log_obj_idx());
								}else{
									throw new RuntimeException("[设备端]device_ext_config<--->[服务端]插入运行配置信息 device_ext_config table insert fail:"+updMap.get("logic_obj").toString()+"没有找到对应的ID，请检查 metadata_ext_model表中的字段名是否一致");
								}
								deviceExt.setExt_model(updMap.get("ext_model")==null?"":updMap.get("ext_model").toString());
								MetadataExtModelEntity metadataExtModel=new MetadataExtModelEntity();
								metadataExtModel.setExt_model(updMap.get("ext_model")==null?"":updMap.get("ext_model").toString());
								List<MetadataExtModelEntity> metadataExts=metadataExtModelDao.select(metadataExtModel);
								if(CollectionUtils.isNotEmpty(metadataExts)){
									deviceExt.setExt_model_idx(metadataExts.get(0).getMeta_ext_idx());
								}else{
									throw new RuntimeException("[设备端]device_ext_config<--->[服务端]插入运行配置信息 device_ext_config table insert fail:"+updMap.get("ext_model").toString()+"没有找到对应的ID，请检查 metadata_ext_model表中的字段名是否一致");
								}
								deviceExt.setExt_extend_conf(updMap.get("ext_extend_conf")==null?"":updMap.get("ext_extend_conf").toString());
								deviceExt.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
								deviceExt.setUpdatetime(new Timestamp(System.currentTimeMillis()));
								isUpd=deviceExtConfDao.insertDeviceExtConfig(deviceExt);
								if(isUpd<=0){//插入不成功抛异常
									throw new RuntimeException("[设备端]device_ext_config<--->[服务端]插入运行配置信息 device_ext_config table insert fail");
								}
							}
							/**
							 * 2.更新复制后的配置信息
							 */
							for(Map<String,Object> updMap:records){
								isUpd=ifExistDeviceConfigValUpdate(updMap, device_idx);
							}
							/**
							 * 3.修改device_config上的是否使用模板的flg改成0，表示不使用
							 */
							//更新 deviceConfig表
							DeviceConfigEntity deviceConfig=new DeviceConfigEntity();
							deviceConfig.setDevice_idx(device_idx);
							deviceConfig.setLibrary_idx(Integer.parseInt(library_idx));
							deviceConfig.setDevice_ext_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
							isUpd=updateDeviceConfig(deviceConfig);
							if(isUpd<=0){//更新成功,不成功考虑抛异常
								throw new RuntimeException("[设备端]device_ext_config<--->[服务端]复制运行配置信息 device_config table update fail");
							}
							result.setState(true);
							result.setResult(new UploadcfgSynResultEntity(device_id, library_idx, UploadcfgSynResultEntity.ResponseResult_success, ""));

						}
					}
				 }
				}else{
					//List<DeviceConfigEntity> devconfs  size is 0
					result.setState(false);
					result.setResult(new UploadcfgSynResultEntity(device_id, library_idx, UploadcfgSynResultEntity.ResponseResult_fail, "没有查询到数据，请输入正确的library_idx和device_id"));
				}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
