package com.ssitcloud.service.impl;

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
import com.ssitcloud.dao.MetaRunConfigDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.MetaRunConfigEntity;
import com.ssitcloud.entity.UploadcfgSynEntity;
import com.ssitcloud.entity.UploadcfgSynResultEntity;
import com.ssitcloud.service.UploadCfgSyncService;

@Component("uploadCfgSync_device_run_config")
public class UploadCfgSyncDeviceRunConfig extends DeviceConfigServiceImpl implements UploadCfgSyncService{
	@Resource
	private MetaRunConfigDao metaRunConfigDao;
	
	private Lock lock = new ReentrantLock();// 锁对象  
	public static void main(String[] args) {
		String s="{\"library_id\":\"1\",\"device_id\":\"001_SSL032\",\"fields\":{\"run_conf_type\":\"1\",\"run_conf_value\":\"2\",\"updatetime\":\"3\"},\"table\":\"device_run_config\",\"records\":[{\"1\":\"center_config\",\"2\":{\"IP\":\"127.0.0.1\",\"PORT\":\"8080\"},\"3\":\"\"},{\"1\":\"localdb_config\",\"2\":{\"IP\":\"127.0.0.1\",\"PORT\":\"3306\",\"USER\":\"root\",\"PWD\":\"123456\"},\"3\":\"\"},{\"1\":\"print_config\",\"2\":{\"HEAD\":{\"FONT\":\"宋体\",\"SIZE\":\"14\"},\"CONTEXT\":{\"FONT\":\"宋体\",\"SIZE\":\"12\"},\"TAIL\":{\"FONT\":\"宋体\",\"SIZE\":\"14\"},\"CHECKOUT\":{\"TYPE\":\"1\",\"PATRONID\":\"1\",\"NAME\":\"1\",\"COUNT\":\"1\",\"FEE\":\"1\",\"HEAD\":\"借书凭据\",\"TAIL\":\"深圳图书馆\"},\"CHECKIN\":{\"TYPE\":\"1\",\"PATRONID\":\"1\",\"COUNT\":\"1\",\"HEAD\":\"还书凭据\",\"TAIL\":\"深圳图书馆\"},\"REGISTER\":{\"TYPE\":\"1\",\"PATRONID\":\"1\",\"NAME\":\"1\",\"ID\":\"1\",\"AMOUNT\":\"1\",\"CARDTYPE\":\"1\",\"DEPOSIT\":\"1\",\"HEAD\":\"办证凭据\",\"TAIL\":\"深圳图书馆\"}},\"3\":\"\"},{\"1\":\"runlog_config\",\"2\":{\"RUNLOG_LEVEL\":\"debug\",\"RUNLOG_TYPE\":\"file\",\"RUNLOG_FILESIZE\":\"5M\"},\"3\":\"\"}]}";
		UploadcfgSynEntity UploadcfgSyn = JsonUtils.fromJson(s,UploadcfgSynEntity.class);
		//JSONUtils.quote(arg0)
		
		List<Map<String, Object>> list=comboFielsAndValue(UploadcfgSyn.getFields(), UploadcfgSyn.getRecords());
		for(Map<String, Object> m:list){
		}
	}
	


	
	
	/**
	 * {
        "library_id": "1",
        "device_id": "001_SSL032",
        "fields": {
            "run_conf_type": "1",
            "run_conf_value": "2",
            "updatetime": "3"
        },
        "table": "device_run_config",
        "records": [
            {
                "1": "center_config",
                "2": {
                    "IP": "127.0.0.1",
                    "PORT": "8080"
                },
                "3": ""
            },
            {
                "1": "localdb_config",
                "2": {
                    "IP": "127.0.0.1",
                    "PORT": "3306",
                    "USER": "root",
                    "PWD": "123456"
                },
                "3": ""
            },
            {
                "1": "print_config",
                "2": {
                    "HEAD": {
                        "FONT": "宋体",
                        "SIZE": "14"
                    },
                    "CONTEXT": {
                        "FONT": "宋体",
                        "SIZE": "12"
                    },
                    "TAIL": {
                        "FONT": "宋体",
                        "SIZE": "14"
                    },
                    "CHECKOUT": {
                        "TYPE": "1",
                        "PATRONID": "1",
                        "NAME": "1",
                        "COUNT": "1",
                        "FEE": "1",
                        "HEAD": "借书凭据",
                        "TAIL": "深圳图书馆"
                    },
                    "CHECKIN": {
                        "TYPE": "1",
                        "PATRONID": "1",
                        "COUNT": "1",
                        "HEAD": "还书凭据",
                        "TAIL": "深圳图书馆"
                    },
                    "REGISTER": {
                        "TYPE": "1",
                        "PATRONID": "1",
                        "NAME": "1",
                        "ID": "1",
                        "AMOUNT": "1",
                        "CARDTYPE": "1",
                        "DEPOSIT": "1",
                        "HEAD": "办证凭据",
                        "TAIL": "深圳图书馆"
                    }
                },
                "3": ""
            },
            {
                "1": "runlog_config",
                "2": {
                    "RUNLOG_LEVEL": "debug",
                    "RUNLOG_TYPE": "file",
                    "RUNLOG_FILESIZE": "5M"
                },
                "3": ""
            }
        ]
    }
	 */
	
	@Override
	public ResultEntityF<UploadcfgSynResultEntity> execute(UploadcfgSynEntity uploadcfgSyn) {
		ResultEntityF<UploadcfgSynResultEntity> result =new ResultEntityF<UploadcfgSynResultEntity>();
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
			records=comboFielsAndValue(fields,records);
			if (StringUtils.hasText(device_id) && StringUtils.hasText(library_idx)) {
				Map<String, Object> map = new HashMap<>();
				map.put("device_id", device_id);
				map.put("library_id", library_idx);
				List<DeviceConfigEntity> devconfs = queryDeviceConfigByDevIdAndLibIdx(map);
				// list assert only one data
				if (devconfs != null && devconfs.size() > 0) {
					DeviceConfigEntity devconf = devconfs.get(0);
					if (devconf != null) {
						// 如果原来的是模板，则重新新增上传的数据一份
						// 如果原来不是模板，则直接修改
						// 取到Idx
						Integer device_idx=devconf.getDevice_idx();
						Integer device_run_tpl_idx=devconf.getDevice_run_tpl_idx();//模板idx
						//Integer device_ext_tpl_idx=devconf.getDevice_ext_tpl_idx();
						//String library_idx=devconf.getLibrary_idx();
							//1 模板 0 不是模板
							Integer run_tpl_flg=devconf.getDevice_run_tpl_flg();
							if(run_tpl_flg==0){//没有使用模板
								//需要先删除
								DeviceRunConfigEntity delRunConfig=new DeviceRunConfigEntity();
								delRunConfig.setLibrary_idx(Integer.parseInt(library_idx));
								delRunConfig.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
								delRunConfig.setDevice_run_id(device_idx);
								List<DeviceRunConfigEntity> deviceRunConfigs=deviceRunConfDao.selectList(delRunConfig);
								if(CollectionUtils.isNotEmpty(deviceRunConfigs)){
									//循环操作的时候 ，执行这句话的时候会发生死锁
									try {
										lock.lock();
										int delNum=deviceRunConfDao.deleteDeviceRunConfig(delRunConfig);
										if(delNum!=deviceRunConfigs.size()){
											throw new RuntimeException("device_idx:"+device_idx+" devicd_run_config 删除记录数有误!");
										}
									}catch(Exception e) {
										throw new RuntimeException(e.getMessage());
									}finally{
										if(lock!=null)
											lock.unlock();
									}
								}
								for(Map<String,Object> updMap:records){
									DeviceRunConfigEntity deviceRun=new DeviceRunConfigEntity();
									//配置类型
									deviceRun.setDevice_run_id(device_idx);
									deviceRun.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
									deviceRun.setLibrary_idx(Integer.parseInt(library_idx));
									if("device_config".equals(updMap.get("run_conf_type"))){
										//如果存在run_conf_type==device_config的情况下
										ifExistDeviceConfigValUpdate(updMap, device_idx);
									}else{
										if(updMap.get("run_conf_type")!=null){
											MetaRunConfigEntity metaRunConfig=new MetaRunConfigEntity();
											metaRunConfig.setRun_conf_type(updMap.get("run_conf_type")==null?"":updMap.get("run_conf_type").toString());
											MetaRunConfigEntity metaRunConfigEntity=metaRunConfigDao.selMetaRunConfigEntityByConfType(metaRunConfig);
											if(metaRunConfigEntity!=null){
												deviceRun.setRun_config_idx(metaRunConfigEntity.getMeta_run_cfg_idx());
											}else{
												throw new RuntimeException("[设备端]update device_run_config<--->[服务端]查询不到run_conf_type："+updMap.get("run_conf_type")+" 对应的ID，请检查metadata_run_config是否存在 "+updMap.get("run_conf_type"));
											}
											deviceRun.setRun_conf_type(updMap.get("run_conf_type")==null?"":updMap.get("run_conf_type").toString());
										}else{
											throw new RuntimeException("device_idx："+device_idx+" [设备端]update device_run_config<--->[服务端]run_conf_type："+updMap.get("run_conf_type")+" 不能为空");
										}
										
										if(updMap.get("run_conf_value")!=null){
											deviceRun.setRun_conf_value(updMap.get("run_conf_value")==null?"":updMap.get("run_conf_value").toString());
										}else{
											//这个应该可以为空
											//throw new RuntimeException("[设备端]update device_run_config<--->[服务端]run_conf_value："+updMap.get("run_conf_value")+" 不能为空");
										}
										int isInsert=deviceRunConfDao.insertDeviceRunConfig(deviceRun);
										if(isInsert<=0){
											throw new RuntimeException("[设备端]device_run_config<--->[服务端]新增运行配置信息失败");
										}
									}
									
								}
								result.setResult(new UploadcfgSynResultEntity(device_id, library_idx, "1", ""));
								result.setState(true);
							}else if(run_tpl_flg==1){
								//是模板，
								//1更新之后重新复制一份,（通过lib_id和dev_idx 查询出来设备的所有设置）
								//2.然后再device_config中设置不是模板
								DeviceRunConfigEntity deviceRunConf=new DeviceRunConfigEntity();
								deviceRunConf.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);//模板
								deviceRunConf.setDevice_run_id(device_run_tpl_idx);//此处是模板ID
								List<DeviceRunConfigEntity> deviceRunConfigs=deviceRunConfDao.queryDeviceRunConfigAndMetadataRunConfig(deviceRunConf);
								if(deviceRunConfigs==null||deviceRunConfigs.size()<=0){
									throw new RuntimeException("[设备端]device_run_config<--->[服务端]查询不到模板运行配置");
								}
								
								//需要先删除
								DeviceRunConfigEntity delRunConfig=new  DeviceRunConfigEntity();
								delRunConfig.setLibrary_idx(Integer.parseInt(library_idx));
								delRunConfig.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
								delRunConfig.setDevice_run_id(device_idx);
								List<DeviceRunConfigEntity> runConfigs=deviceRunConfDao.selectList(delRunConfig);
								if(CollectionUtils.isNotEmpty(runConfigs)){
									int delNum=deviceRunConfDao.deleteDeviceRunConfig(delRunConfig);
									if(delNum!=runConfigs.size()){
										throw new RuntimeException("device_idx:"+device_idx+" devicd_run_config 删除记录数有误!");
									}
								}
								
								for(Map<String,Object> updMap:records){
									DeviceRunConfigEntity deviceRun=new DeviceRunConfigEntity();
									//配置类型
									deviceRun.setDevice_run_id(device_idx);
									deviceRun.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
									deviceRun.setLibrary_idx(Integer.parseInt(library_idx));
									if("device_config".equals(updMap.get("run_conf_type"))){
										//如果存在run_conf_type==device_config的情况下
										ifExistDeviceConfigValUpdate(updMap, device_idx);
									}else{
										if(updMap.get("run_conf_type")!=null){
											MetaRunConfigEntity metaRunConfig=new MetaRunConfigEntity();
											metaRunConfig.setRun_conf_type(updMap.get("run_conf_type")==null?"":updMap.get("run_conf_type").toString());
											MetaRunConfigEntity metaRunConfigEntity=metaRunConfigDao.selMetaRunConfigEntityByConfType(metaRunConfig);
											if(metaRunConfigEntity!=null){
												deviceRun.setRun_config_idx(metaRunConfigEntity.getMeta_run_cfg_idx());
											}else{
												throw new RuntimeException("[设备端]update device_run_config<--->[服务端]查询不到run_conf_type："+updMap.get("run_conf_type")+" 对应的ID，请检查metadata_run_config是否存在 "+updMap.get("run_conf_type"));
											}
											deviceRun.setRun_conf_type(updMap.get("run_conf_type")==null?"":updMap.get("run_conf_type").toString());
										}else{
											throw new RuntimeException("[设备端]update device_run_config<--->[服务端]run_conf_type："+updMap.get("run_conf_type")+" 不能为空");
										}
										if(updMap.get("run_conf_value")!=null){
											deviceRun.setRun_conf_value(updMap.get("run_conf_value")==null?"":updMap.get("run_conf_value").toString());
										}else{
											//这个应该可以为空
											//throw new RuntimeException("[设备端]update device_run_config<--->[服务端]run_conf_value："+updMap.get("run_conf_value")+" 不能为空");
										}
										int isInsert=deviceRunConfDao.insertDeviceRunConfig(deviceRun);
										if(isInsert<=0){
											throw new RuntimeException("[设备端]device_run_config<--->[服务端]复制运行配置信息失败");
										}
									}
									
								}
								/**
								 * 2.更新复制后的配置信息
								 */
								for(Map<String,Object> updMap:records){
									ifExistDeviceConfigValUpdate(updMap, device_idx);
								}
								/**
								 * 3.修改device_config上的是否使用模板的flg改成0，表示不使用
								 */
								DeviceConfigEntity deviceConfig=new DeviceConfigEntity();
								deviceConfig.setDevice_idx(device_idx);
								deviceConfig.setLibrary_idx(Integer.parseInt(library_idx));
								deviceConfig.setDevice_run_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
								int isUpd=updateDeviceConfig(deviceConfig);//
								if(isUpd<=0){//更新0条
									throw new RuntimeException("device_idx："+device_idx+" [设备端]device_run_config<--->[服务端]新增运行配置信息  device_config table update fail");
								}
								result.setState(true);
								result.setResult(new UploadcfgSynResultEntity(device_id, library_idx, UploadcfgSynResultEntity.ResponseResult_success, ""));
							}
					}
				}else{
					//List<DeviceConfigEntity> devconfs  size is 0
					result.setState(false);
					result.setResult(new UploadcfgSynResultEntity(device_id, library_idx, UploadcfgSynResultEntity.ResponseResult_fail, "没有查询到数据，请输入正确的library_idx和device_id"));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	

}
