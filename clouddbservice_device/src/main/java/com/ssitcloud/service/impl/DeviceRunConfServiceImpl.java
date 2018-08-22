package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceRunConfDao;
import com.ssitcloud.dao.MetaRunConfigDao;
import com.ssitcloud.dao.MetadataDeviceTypeDao;
import com.ssitcloud.dao.MetadataOpercmdDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.DeviceRunTempEntity;
import com.ssitcloud.entity.MetaRunConfigEntity;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.entity.MetadataOpercmdEntity;
import com.ssitcloud.entity.page.DeviceRunTempPageEntity;
import com.ssitcloud.service.DeviceConfigService;
import com.ssitcloud.service.DeviceRunConfService;
import com.ssitcloud.service.DeviceService;
import com.ssitcloud.system.entity.DeviceRunConfigRemoteEntity;

@Service
public class DeviceRunConfServiceImpl implements DeviceRunConfService {


	
	@Resource
	private DeviceRunConfDao deviceRunConfDao;
	
	@Resource
	private MetadataDeviceTypeDao metadataDeviceTypeDao;
	
	@Resource
	private MetaRunConfigDao metaRunConfigDao;
	
	@Resource
	private DeviceConfigDao deviceConfigDao;
	
	@Resource
	private MetadataOpercmdDao metadataOpercmdDao;
	
	@Resource
	private DeviceService deviceService;
	
	@Resource
	private DeviceConfigService deviceConfigService;
	

	@Override
	public int insertDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig) {
		return deviceRunConfDao.insertDeviceRunConfig(deviceRunConfig);
	}

	@Override
	public int insertBatchDeviceRunConfig(
			List<DeviceRunConfigEntity> deviceRunConfigs) {
		return deviceRunConfDao.insertBatchDeviceRunConfig(deviceRunConfigs);
	}

	@Override
	public int updateDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig) {
		return deviceRunConfDao.updateDeviceRunConfig(deviceRunConfig);
	}

	@Override
	public int deleteDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig) {
		return deviceRunConfDao.deleteDeviceRunConfig(deviceRunConfig);
	}

	

	@Override
	public List<DeviceRunConfigEntity> selectList(
			DeviceRunConfigEntity deviceRunConfig) {
		return deviceRunConfDao.selectList(deviceRunConfig);
	}

	
	@Override
	public int insertDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity) {
		return deviceRunConfDao.insertDeviceRunTemp(deviceRunTempEntity);
	}

	@Override
	public int updateDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity) {
		return deviceRunConfDao.updateDeviceRunTemp( deviceRunTempEntity);
	}

	@Override
	public int deleteDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity) {
		return deviceRunConfDao.deleteDeviceRunTemp(deviceRunTempEntity);
	}

	
	@Override
	public List<DeviceRunTempEntity> selectList(
			DeviceRunTempEntity deviceRunTempEntity) {
		return deviceRunConfDao.selectList(deviceRunTempEntity);
	}

	@Override
	public int insertMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity) {
		return deviceRunConfDao.insertMetaRunConfig(metaRunConfigEntity);
	}

	@Override
	public int updateMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity) {
		return deviceRunConfDao.updateMetaRunConfig(metaRunConfigEntity);
	}

	@Override
	public int deleteMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity) {
		return deviceRunConfDao.deleteMetaRunConfig(metaRunConfigEntity);
	}
	
	@Override
	public List<MetaRunConfigEntity> selectList(
			MetaRunConfigEntity metaRunConfigEntity) {
		return deviceRunConfDao.selectList(metaRunConfigEntity);
	}

	@Override
	public List<Map<String, Object>> selAllRunTempList(Map<String, String> param) {
		List<Map<String, Object>> returnList= new ArrayList<>();
		DeviceRunTempEntity tempEntity = new DeviceRunTempEntity();
		if (param!=null && param.get("device_type")!=null && !param.get("device_type").equals("")) {
			tempEntity.setDevice_type(Integer.valueOf(param.get("device_type")));
		}
		//获取所有的运行模板
		List<DeviceRunTempEntity> runTempList = deviceRunConfDao.selectListByDeviceType(tempEntity);
		for (DeviceRunTempEntity deviceRunTempEntity : runTempList) {
			Map<String, Object> runTmpMap = new HashMap<String, Object>();
			Integer idx = deviceRunTempEntity.getDevice_tpl_idx();
			List<Map<String, Object>> runList = deviceRunConfDao.selRunDetailListByIdx(idx);
			
			runTmpMap.put("device_tpl_idx", deviceRunTempEntity.getDevice_tpl_idx());
			runTmpMap.put("device_tpl_ID", deviceRunTempEntity.getDevice_tpl_ID());
			runTmpMap.put("device_tpl_desc", deviceRunTempEntity.getDevice_tpl_desc());
			runTmpMap.put("device_type", deviceRunTempEntity.getDevice_type());
			runTmpMap.put("runDetailList", runList);
			returnList.add(runTmpMap);
		}
		return returnList;
	}

	@Override
	public List<Map<String, Object>> selRunDataList(String json) {
		List<Map<String, Object>> returnList= new ArrayList<>();
		DeviceRunConfigEntity runConfigEntity = JsonUtils.fromJson(json, DeviceRunConfigEntity.class);
		List<Map<String, Object>> runList = deviceRunConfDao.selRunDataListByIdx(runConfigEntity.getDevice_run_id());
		Map<String, Object> runTmpMap = new HashMap<String, Object>();
		runTmpMap.put("device_run_id", runConfigEntity.getDevice_run_id());
		runTmpMap.put("device_tpl_type", 1);
		runTmpMap.put("runDetailList", runList);
		
		return returnList;
	}

	@Override
	public int deleteRunData(DeviceRunConfigEntity deviceRunConfig) {
	
		return deviceRunConfDao.deleteRunDataByidxAndtype(deviceRunConfig);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DeviceRunTempPageEntity selRunTempListByParam(DeviceRunTempPageEntity pageEntity) {
		DeviceRunTempPageEntity deviceRunTempPageEntity = new DeviceRunTempPageEntity();
		deviceRunTempPageEntity = deviceRunConfDao.selRunTempListByParam(pageEntity);
		List<DeviceRunTempPageEntity> list = deviceRunTempPageEntity.getRows();
		for (Object object : list) {
			Map<String,Object> map =  (Map<String,Object>) object;
			int idx = 0;
			if (map.get("device_tpl_idx")!=null && !map.get("device_tpl_idx").toString().equals("")) {
				idx = Integer.valueOf(map.get("device_tpl_idx").toString());
			}
			//根据ext模板idx获取该模板的所有设备list
			//在首页上显示 语言版本（多选）、运行日志级别、运行日志保存方式、运行日志文件大小
			//String content = "";
			List<Object> content=new ArrayList<>();
			List<Map<String, Object>> runList = deviceRunConfDao.selRunDetailListByIdx(idx);
			for (Map<String, Object> run : runList) {
				if(run.get("run_conf_type_desc")!=null){
					if("runlog_config".equals(run.get("run_conf_type").toString())){
						content.add(run.get("run_conf_value"));
					}else if("language_config".equals(run.get("run_conf_type").toString())){
						content.add(run.get("run_conf_value"));
					}
				}
				/*if (run.get("run_conf_type_desc")!=null && !run.get("run_conf_type_desc").toString().equals("")) {
					String str1 = JsonUtils.fromJson(run.get("run_conf_type_desc").toString(), Map.class).get("zh_CN").toString();
					String str2 = run.get("run_conf_value").toString();
					if (str2!=null && !str2.equals("")) {
						content += str1+":"+str2+",";
						break;
					}
				}*/
			}
			/*if(contentsb.length() >= 1){
				content=content.substring(0,content.length()-1);
			}*/
			map.put("content", content);
			map.put("runDetailList", runList);
		}
		
		
		return deviceRunTempPageEntity;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity addRunTemp(String runConfStr) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			
			Map<String, Object> runMap = JsonUtils.fromJson(runConfStr, Map.class);
			String device_tpl_ID = runMap.get("device_tpl_ID")==null?"":runMap.get("device_tpl_ID").toString();
			String device_tpl_desc = runMap.get("device_tpl_desc")==null?"":runMap.get("device_tpl_desc").toString();
			//设备类型
			String device_type = runMap.get("device_type")==null?"":runMap.get("device_type").toString();
			
			if (!StringUtils.hasText(device_tpl_ID)
					|| !StringUtils.hasText(device_tpl_desc)
					|| !StringUtils.hasText(device_type)) {
				
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			
			
			//查询是否有这个设备类型
			MetadataDeviceTypeEntity deviceTypeEntity = new MetadataDeviceTypeEntity();
			deviceTypeEntity.setMeta_devicetype_idx(Integer.valueOf(device_type));
			deviceTypeEntity = metadataDeviceTypeDao.selOneByIdx(deviceTypeEntity);
			if (deviceTypeEntity == null) {
				resultEntity.setValue(false, "设备类型不存在");
				return resultEntity;
			}
			
			//设置模板信息
			DeviceRunTempEntity deviceRunTempEntity = new DeviceRunTempEntity();
			deviceRunTempEntity.setDevice_tpl_ID(device_tpl_ID.trim());
			deviceRunTempEntity.setDevice_tpl_desc(device_tpl_desc.trim());
			deviceRunTempEntity.setDevice_type(Integer.valueOf(device_type));
			
			//根据id查询是否有这个设备
			int cou1 = deviceRunConfDao.countDeviceRunTempById(deviceRunTempEntity);
			if(cou1>=1){
				resultEntity.setValue(false, "模板ID已经被占用，请检查！");
				return resultEntity;
			}
			//根据名称查询是否有这个设备
			int cou2 = deviceRunConfDao.countDeviceRunTempByDesc(deviceRunTempEntity);
			if(cou2>=1){
				resultEntity.setValue(false, "模板名已经被占用，请检查！");
				return resultEntity;
			}
			
			
			int ret1 = deviceRunConfDao.insertDeviceRunTemp(deviceRunTempEntity);
			if(ret1<=0){
				resultEntity.setValue(false, "新增设备模板失败");
				throw new RuntimeException("新增设备模板失败");
			}
			
			if(runMap.get("runDetailList") instanceof List){
				List<Map<String, Object>> detailList = (List<Map<String, Object>>) runMap.get("runDetailList");
				for (Map<String, Object> map : detailList) {
					String conftype = map.get("run_conf_type")==null?"":map.get("run_conf_type").toString();
					String confValue = map.get("run_conf_value")==null?"":JsonUtils.toJson(map.get("run_conf_value")).toString();
					
					if (conftype.equals("")) {
						continue;
					}
					MetaRunConfigEntity mConfigEntity = new MetaRunConfigEntity();
					mConfigEntity.setRun_conf_type(conftype);
					mConfigEntity = metaRunConfigDao.selMetaRunConfigEntityByConfType(mConfigEntity);
					if(mConfigEntity==null||mConfigEntity.getMeta_run_cfg_idx()==null){
						continue;
					}
					
					DeviceRunConfigEntity runConfigEntity = new DeviceRunConfigEntity();
					runConfigEntity.setLibrary_idx(0);
					runConfigEntity.setDevice_tpl_type(0);
					runConfigEntity.setDevice_run_id(Integer.valueOf(deviceRunTempEntity.getDevice_tpl_idx()));
					runConfigEntity.setRun_config_idx(mConfigEntity.getMeta_run_cfg_idx());
					runConfigEntity.setRun_conf_value(confValue);
					deviceRunConfDao.insertDeviceRunConfig(runConfigEntity);
					
				}
			}
			
			resultEntity.setValue(true, "success");
			//运行日志模板IDX｜运行日志模板名  2017年3月7号修改格式  |模板idx|模板名称||
			resultEntity.setRetMessage("|运行日志模板IDX:"+deviceRunTempEntity.getDevice_tpl_idx()+"|运行日志模板名:"+deviceRunTempEntity.getDevice_tpl_desc()+"||");

		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
			throw new RuntimeException(e);
		}
		return resultEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity updateRunTemp(String runConfStr) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, Object> runMap = JsonUtils.fromJson(runConfStr, Map.class);
			String device_tpl_idx = runMap.get("device_tpl_idx")==null?"":runMap.get("device_tpl_idx").toString();
			String device_tpl_ID = runMap.get("device_tpl_ID")==null?"":runMap.get("device_tpl_ID").toString();
			String device_tpl_desc = runMap.get("device_tpl_desc")==null?"":runMap.get("device_tpl_desc").toString();
			//设备类型
			String device_type = runMap.get("device_type")==null?"":runMap.get("device_type").toString();
			
			if (!StringUtils.hasText(device_tpl_idx)
					|| !StringUtils.hasText(device_tpl_ID)
					|| !StringUtils.hasText(device_tpl_desc)
					|| !StringUtils.hasText(device_type)) {
				
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			
			//查询是否有这个设备类型
			MetadataDeviceTypeEntity deviceTypeEntity = new MetadataDeviceTypeEntity();
			deviceTypeEntity.setMeta_devicetype_idx(Integer.valueOf(device_type));
			deviceTypeEntity = metadataDeviceTypeDao.selOneByIdx(deviceTypeEntity);
			if (deviceTypeEntity == null) {
				resultEntity.setValue(false, "设备类型不存在");
				return resultEntity;
			}
			
			//设置模板信息
			DeviceRunTempEntity deviceRunTempEntity = new DeviceRunTempEntity();
			deviceRunTempEntity.setDevice_tpl_idx(Integer.valueOf(device_tpl_idx));
			deviceRunTempEntity.setDevice_tpl_ID(device_tpl_ID.trim());
			deviceRunTempEntity.setDevice_tpl_desc(device_tpl_desc.trim());
			deviceRunTempEntity.setDevice_type(Integer.valueOf(device_type));
			
			//根据id查询是否有这个设备
			int cou1 = deviceRunConfDao.countDeviceRunTempById(deviceRunTempEntity);
			if(cou1>=1){
				resultEntity.setValue(false, "模板ID已经被占用，请检查！");
				return resultEntity;
			}
			//根据名称查询是否有这个设备
			int cou2 = deviceRunConfDao.countDeviceRunTempByDesc(deviceRunTempEntity);
			if(cou2>=1){
				resultEntity.setValue(false, "模板名已经被占用，请检查！");
				return resultEntity;
			}
			
			
			DeviceRunConfigEntity deviceRunConfig = new DeviceRunConfigEntity();
			deviceRunConfig.setDevice_run_id(Integer.valueOf(device_tpl_idx));
			deviceRunConfig.setDevice_tpl_type(0);//只删除模板信息
			deviceRunConfDao.deleteRunDataByidxAndtype(deviceRunConfig);
			
			if(runMap.get("runDetailList") instanceof List){
				List<Map<String, Object>> detailList = (List<Map<String, Object>>) runMap.get("runDetailList");
				for (Map<String, Object> map : detailList) {
					String conftype = map.get("run_conf_type")==null?"":map.get("run_conf_type").toString();
					String confValue = map.get("run_conf_value")==null?"":JsonUtils.toJson(map.get("run_conf_value")).toString();
					
					if (conftype.equals("")) {
						continue;
					}
					MetaRunConfigEntity mConfigEntity = new MetaRunConfigEntity();
					mConfigEntity.setRun_conf_type(conftype);
					mConfigEntity = metaRunConfigDao.selMetaRunConfigEntityByConfType(mConfigEntity);
					if(mConfigEntity==null||mConfigEntity.getMeta_run_cfg_idx()==null){
						continue;
					}
					
					DeviceRunConfigEntity runConfigEntity = new DeviceRunConfigEntity();
					runConfigEntity.setLibrary_idx(0);
					runConfigEntity.setDevice_tpl_type(0);
					runConfigEntity.setDevice_run_id(Integer.valueOf(device_tpl_idx));
					runConfigEntity.setRun_config_idx(mConfigEntity.getMeta_run_cfg_idx());
					runConfigEntity.setRun_conf_value(confValue);
					deviceRunConfDao.insertDeviceRunConfig(runConfigEntity);
					
				}
			}
			int ret = deviceRunConfDao.updateDeviceRunTemp(deviceRunTempEntity);
			
			//下发配置到设备端。
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("table_name", "device_run_config");
			paramMap.put("model_idx", device_tpl_idx);
			ResultEntity res = deviceConfigService.queryDevIdsByModelAndModelIdx(JsonUtils.toJson(paramMap));
			if(res.getResult() != null && res.getResult().toString().length() > 0){
				List<DeviceConfigEntity> configEntities = JsonUtils.fromJson(JsonUtils.toJson(res.getResult()),
				new TypeReference<List<DeviceConfigEntity>>() {});
				for(DeviceConfigEntity configEntity : configEntities){
					DeviceConfigEntity deviceConfigEntity = new DeviceConfigEntity();
					deviceConfigEntity.setDevice_run_tpl_idx(configEntity.getDevice_run_tpl_idx());
					deviceConfigEntity.setDevice_run_tpl_flg(configEntity.getDevice_run_tpl_flg());
					deviceService.pushMessage(deviceConfigEntity, configEntity.getLibrary_idx(), configEntity.getDevice_idx());
				}
			}
			
			
			resultEntity.setValue(true, "success");
			//运行日志模板IDX｜运行日志模板名 2017年3月7号修改格式  |模板idx|模板名称||
			resultEntity.setRetMessage("|运行日志模板IDX:"+deviceRunTempEntity.getDevice_tpl_idx()+"|运行日志模板名:"+deviceRunTempEntity.getDevice_tpl_desc()+"||");
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
			throw new RuntimeException(e);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity delRunTemp(String idx) {

		ResultEntity resultEntity = new ResultEntity();
		try {
			if(!StringUtils.hasText(idx)){
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			DeviceRunTempEntity d1 = new DeviceRunTempEntity();
			d1.setDevice_tpl_idx(Integer.valueOf(idx));
			
			//查询是否在使用
			DeviceConfigEntity deviceConfigEntity = new DeviceConfigEntity();
			deviceConfigEntity.setDevice_run_tpl_idx(Integer.valueOf(idx));
			int ret = deviceRunConfDao.countUsingRunTempplate(deviceConfigEntity);
			if(ret > 0){
				resultEntity.setValue(false, "1");//此模板正在使用，无法删除
				return resultEntity;
			}
			//先删除ext_config的数据
			DeviceRunConfigEntity deviceRunConfig = new DeviceRunConfigEntity();
			deviceRunConfig.setDevice_run_id(Integer.valueOf(idx));
			deviceRunConfig.setDevice_tpl_type(0);//删除模板为0的数据
			int ret2 = deviceRunConfDao.deleteRunDataByidxAndtype(deviceRunConfig);
			if(ret2>=0){
				int ret3 = deviceRunConfDao.deleteDeviceRunTemp(d1);
				if(ret3<0){
					throw new RuntimeException("删除device_run_template数据失败");
				}
			}else{
				throw new RuntimeException("删除device_run_config数据失败");
			}
			resultEntity.setValue(true, "success");
			resultEntity.setRetMessage("|运行日志模板IDX:"+d1.getDevice_tpl_idx()+"|运行日志模板名:"+d1.getDevice_tpl_desc()+"||"); //运行日志模板IDX｜运行日志模板名 2017年3月7号修改格式  |模板idx|模板名称||

		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
			throw new RuntimeException(e);
		}
		return resultEntity;
	
	}
	
	@Override
	public ResultEntity delMultiRunTemp(String req) {
		ResultEntity resultEntity = new ResultEntity();
		StringBuilder idxStringB=new StringBuilder("运行日志模板IDX:");
		try {
			if(!StringUtils.hasText(req)){
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			List<Map<String, Object>> list = JsonUtils.fromJson(req, new TypeReference<List<Map<String,Object>>>() {});
			Map<String, Object> resMap = new HashMap<>();
			String cannotDel = "";
			for (Map<String, Object> map : list) {
				String idx = map.get("idx")==null?"":map.get("idx").toString();
				String runId = map.get("runId")==null?"":map.get("runId").toString();
				
				if (idx.equals("")) {
					cannotDel += runId+",";
					continue;
				}
				DeviceRunTempEntity d1 = new DeviceRunTempEntity();
				d1.setDevice_tpl_idx(Integer.valueOf(idx));
				
				//查询是否在使用
				DeviceConfigEntity deviceConfigEntity = new DeviceConfigEntity();
				deviceConfigEntity.setDevice_run_tpl_idx(Integer.valueOf(idx));
				int ret = deviceRunConfDao.countUsingRunTempplate(deviceConfigEntity);
				if(ret > 0){
					cannotDel += runId+",";
					continue;
				}
				//先删除ext_config的数据
				DeviceRunConfigEntity deviceRunConfig = new DeviceRunConfigEntity();
				deviceRunConfig.setDevice_run_id(Integer.valueOf(idx));
				deviceRunConfig.setDevice_tpl_type(0);//删除模板为0的数据
				int ret2 = deviceRunConfDao.deleteRunDataByidxAndtype(deviceRunConfig);
				if(ret2>=0){
					int ret3 = deviceRunConfDao.deleteDeviceRunTemp(d1);
					idxStringB.append(d1.getDevice_tpl_idx()).append(",");
				}
			}
			if (cannotDel.length()>0) {
				cannotDel = cannotDel.substring(0,cannotDel.length()-1);
			}
			resMap.put("cannotDel", cannotDel);
  			resultEntity.setValue(true, "success","",resMap);
			resultEntity.setRetMessage("运行日志模板IDX:"+idxStringB.toString());//运行日志模板IDX｜运行日志模板名
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
			throw new RuntimeException(e);
		}
		return resultEntity;
	}
	/****
	 * view UpdateRunConfigData function
	 * 原来有自定义数据 更新 则执行此方法
	 */
	@Override
	public ResultEntity DelAndAddDeviceRunConf(String json) {
		ResultEntity result=new ResultEntity();
		
		if(JSONUtils.mayBeJSON(json)){
			List<Map<String,Object>> runConfsMap =JsonUtils.fromJson(json, new TypeReference<List<Map<String,Object>>>() {});
			List<DeviceRunConfigEntity> runConfs = new ArrayList<>();
			for(Map<String,Object> m:runConfsMap){
				DeviceRunConfigEntity deviceRunConfigEntity=new DeviceRunConfigEntity();
				if(m.get("device_run_id")!=null){
					deviceRunConfigEntity.setDevice_run_id(Integer.parseInt(m.get("device_run_id").toString()));
				}
				if(m.get("library_idx")!=null){
					deviceRunConfigEntity.setLibrary_idx(Integer.parseInt(m.get("library_idx").toString()));
				}
				if(m.get("device_tpl_type")!=null){
					deviceRunConfigEntity.setDevice_tpl_type(Integer.parseInt(m.get("device_tpl_type").toString()));
				}
				if(m.get("run_conf_type")!=null){
					deviceRunConfigEntity.setRun_conf_type(m.get("run_conf_type").toString());
				}
				if(m.get("run_conf_value")!=null){
					deviceRunConfigEntity.setRun_conf_value(JsonUtils.toJson(m.get("run_conf_value")));
				}
				runConfs.add(deviceRunConfigEntity);
			}
			
			DeviceRunConfigEntity runConfigEntity=runConfs.get(0);
			DeviceRunConfigEntity deviceRunConfig=new DeviceRunConfigEntity();
			deviceRunConfig.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
			deviceRunConfig.setDevice_run_id(runConfigEntity.getDevice_run_id());
			List<DeviceRunConfigEntity> deviceRunConfigs=selectList(deviceRunConfig);
			if(CollectionUtils.isNotEmpty(deviceRunConfigs)){
				//删除模板 或者 数据
				int ret = deleteRunData(runConfigEntity);
				if(ret!=deviceRunConfigs.size()){
					throw new RuntimeException("更新运行配置数据 中 删除自定义数据失败！");
				}
			}
			
			//根据conf_type 获得 run_config_idx
			for (DeviceRunConfigEntity deviceRunConfigEntity : runConfs) {
				MetaRunConfigEntity mRunConfigEntity = new MetaRunConfigEntity();
				mRunConfigEntity.setRun_conf_type(deviceRunConfigEntity.getRun_conf_type());
				List<MetaRunConfigEntity> mEntity = selectList(mRunConfigEntity);
				deviceRunConfigEntity.setRun_config_idx(mEntity.get(0).getMeta_run_cfg_idx());
			}
			int re = 0;
			re = insertBatchDeviceRunConfig(runConfs);
			Integer device_idx=runConfigEntity.getDevice_run_id();
			if(device_idx!=null){
				DeviceConfigEntity deviceConfig=new DeviceConfigEntity();
				deviceConfig.setDevice_idx(device_idx);
				List<DeviceConfigEntity> deviceConfigs=deviceConfigDao.selectList(deviceConfig);
				if(CollectionUtils.isNotEmpty(deviceConfigs)){
					Integer device_run_tpl_flg=deviceConfigs.get(0).getDevice_run_tpl_flg();
					if(device_run_tpl_flg==DeviceConfigEntity.IS_MODEL){
						//如果原来是运行配置模板是模板类型的 
						deviceConfig.setDevice_run_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
						int updNum=deviceConfigDao.updateDeviceConfig(deviceConfig);
						if(updNum<=0){
							throw new RuntimeException("更新运行配置数据 中 更新device_config失败！");
						}
					}
				}
			}
			
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			
			//下发数据到设备端
			if(result.getState()){
				DeviceConfigEntity dc = new DeviceConfigEntity();
				dc.setLibrary_idx(runConfigEntity.getLibrary_idx());
				dc.setDevice_idx(device_idx);
				dc.setDevice_run_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
				deviceService.pushMessage(dc, dc.getLibrary_idx(), dc.getDevice_idx());
			}
			
		}
		return result;
	}

	@Override
	public List<DeviceRunConfigEntity> queryDeviceRunConfigAndMetadataRunConfig(String req) {
		if(JSONUtils.mayBeJSON(req)){
			DeviceRunConfigEntity deviceRunConfig=JsonUtils.fromJson(req,DeviceRunConfigEntity.class);
			List<DeviceRunConfigEntity> deviceRunConfigs=deviceRunConfDao.queryDeviceRunConfigAndMetadataRunConfig(deviceRunConfig);
			return deviceRunConfigs;
		}
		return null;
	}
	/**
	 * req={ 
	 * 	device_idx : "" 
	 * 	library_idx : "" 这个可能没有
	 * }
	 * 获取 设备的device_run_config 信息，
	 * 在获取出里面配置的  function 字段，就是这样
	 */
	@Override
	public ResultEntity SelFunctionByDeviceIdx(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			DeviceConfigEntity deviceConfig=JsonUtils.fromJson(req, DeviceConfigEntity.class);
			List<DeviceConfigEntity> devConfigs=deviceConfigDao.queryDeviceConfigByDeviceIdx(deviceConfig);
			if(CollectionUtils.isNotEmpty(devConfigs)){
				DeviceConfigEntity df=devConfigs.get(0);
				Integer run_tpl_flg=df.getDevice_run_tpl_flg();
				DeviceRunConfigEntity deviceRunConfig=new DeviceRunConfigEntity();
				if(DeviceConfigEntity.IS_MODEL==run_tpl_flg){//muban
					
					deviceRunConfig.setLibrary_idx(0);
					deviceRunConfig.setDevice_run_id(df.getDevice_run_tpl_idx());//muban IDX
				}else if(DeviceConfigEntity.IS_NOT_MODEL==run_tpl_flg){
					
					deviceRunConfig.setDevice_run_id(deviceConfig.getDevice_idx());
					//deviceRunConfig.setDevice_run_id(df.getLibrary_idx());
					deviceRunConfig.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
				}
				deviceRunConfig.setRun_config_idx(3);//function_config
				List<DeviceRunConfigEntity> deviceRunConfigs=deviceRunConfDao.selectList(deviceRunConfig);
				if(CollectionUtils.isNotEmpty(deviceRunConfigs)){
					DeviceRunConfigEntity drc=deviceRunConfigs.get(0);
					String run_conf_value=drc.getRun_conf_value();
					Map<String,Object> map=new HashMap<>();
					if(JSONUtils.mayBeJSON(run_conf_value)){
//						JsonNode node=JsonUtils.convertJSON(run_conf_value);
						JsonNode node=JsonUtils.readTree(run_conf_value);
						if(node!=null){
							JsonNode funcNode= node.get("FUNCTION");
							if(funcNode!=null){
								List<MetadataOpercmdEntity> metadataOpercmds=metadataOpercmdDao.selectDevAllFunction();// type==2 and opercmd_url is not null
								if(CollectionUtils.isNotEmpty(metadataOpercmds)){
									for(MetadataOpercmdEntity cmdEntity:metadataOpercmds){
										String cmd=cmdEntity.getOpercmd_url();
										if(cmd!=null){
											JsonNode funcState=funcNode.get(cmd);
											if(funcState!=null){//run_config exists function 
												String flg=funcState.asText();
												if("0".equals(flg)){
													//pass
												}else if("1".equals(flg)){
													map.put(cmdEntity.getOpercmd(), cmdEntity);
												}
											}
										}
										
									}
									
								}
							}
						}
						
					}
					result.setResult(map);
					result.setState(true);
				}
			}
			
		}
		return result;
	}
	
	
	/**
	 * 获取设备的运行模板配置
	 * @return
	 */
	public List<DeviceRunConfigRemoteEntity> getDeviceRunConfig(DeviceConfigEntity configEntity){
			
		DeviceRunConfigEntity params = new DeviceRunConfigEntity();
		if(DeviceConfigEntity.IS_MODEL.equals(configEntity.getDevice_run_tpl_flg())){//全局模板
			
			params.setDevice_run_id(configEntity.getDevice_run_tpl_idx());
			params.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
			List<DeviceRunConfigRemoteEntity> devRunsConfigs=deviceRunConfDao.queryDeviceRunConfigByDown(params);
			return devRunsConfigs;
		}else if(DeviceConfigEntity.IS_NOT_MODEL.equals(configEntity.getDevice_run_tpl_flg())){
			//设备自定义模板
			params.setLibrary_idx(configEntity.getLibrary_idx());
			params.setDevice_run_id(configEntity.getDevice_idx());
			params.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
			List<DeviceRunConfigRemoteEntity> devRunsConfigs=deviceRunConfDao.queryDeviceRunConfigByDown(params);
			return devRunsConfigs;
		}
		
		return null;
	}

}
