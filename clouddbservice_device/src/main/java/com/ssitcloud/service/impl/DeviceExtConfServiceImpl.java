package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceExtConfDao;
import com.ssitcloud.dao.DeviceMonConfDao;
import com.ssitcloud.dao.MetadataDeviceTypeDao;
import com.ssitcloud.dao.PlcLogicObjDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.DeviceExtTempEntity;
import com.ssitcloud.entity.DeviceMonConfigEntity;
import com.ssitcloud.entity.DeviceMonitorEntity;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.entity.MetadataExtModelEntity;
import com.ssitcloud.entity.PlcLogicObjEntity;
import com.ssitcloud.entity.page.DeviceExtLogicPageEntity;
import com.ssitcloud.service.DeviceConfigService;
import com.ssitcloud.service.DeviceExtConfService;
import com.ssitcloud.service.DeviceService;
import com.ssitcloud.system.entity.DeviceExtConfigRemoteEntity;

@Service
public class DeviceExtConfServiceImpl extends CommonDaoImpl implements DeviceExtConfService {

	@Resource
	private DeviceExtConfDao deviceExtConfDao;
	
	@Resource
	private DeviceMonConfDao deviceMonConfDao;
	
	@Resource
	private MetadataDeviceTypeDao metadataDeviceTypeDao;
	
	@Resource
	private DeviceConfigDao deviceConfigDao;
	
	@Resource
	private PlcLogicObjDao plcLogicObjDao;
	
	@Resource
	private DeviceConfigService deviceConfigService;
	
	@Resource
	private DeviceService deviceService;
	
	@Override
	public int insertDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig) {
		if (deviceExtConfig == null)return 0;
		return deviceExtConfDao.insertDeviceExtConfig(deviceExtConfig);
	}

	@Override
	public int insertDeviceExtTemp(DeviceExtTempEntity deviceExtTemp) {
		if (deviceExtTemp == null)return 0;
		return deviceExtConfDao.insertDeviceExtTemp(deviceExtTemp);
	}

	@Override
	public int deleteDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig) {
		if (deviceExtConfig == null)
			return 0;
		return deviceExtConfDao.deleteDeviceExtConfig(deviceExtConfig);
	}

	@Override
	public int deleteDeviceExtTemp(DeviceExtTempEntity deviceExtTemp) {
		if (deviceExtTemp == null)return 0;
		return deviceExtConfDao.deleteDeviceExtTemp(deviceExtTemp);
	}

	@Override
	public int updateDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig) {
		if (deviceExtConfig == null)
			return 0;
		return deviceExtConfDao.updateDeviceExtConfig(deviceExtConfig);
	}

	@Override
	public int updateDeviceExtTemp(DeviceExtTempEntity deviceExtTemp) {
		if (deviceExtTemp == null)
			return 0;
		return deviceExtConfDao.updateDeviceExtTemp(
				deviceExtTemp);
	}

	@Override
	public DeviceExtConfigEntity selectOne(DeviceExtConfigEntity deviceExtConfig) {
		return deviceExtConfDao.selectOne(deviceExtConfig);
	}

	@Override
	public List<DeviceExtConfigEntity> selectList(
			DeviceExtConfigEntity deviceExtConfig) {
		
		return deviceExtConfDao.selectList(deviceExtConfig);
	}

	@Override
	public DeviceExtTempEntity selectOne(DeviceExtTempEntity deviceExtTemp) {
		return deviceExtConfDao.selectOne(deviceExtTemp);
	}

	@Override
	public List<DeviceExtTempEntity> selectList(
			DeviceExtTempEntity deviceExtTemp) {
		return deviceExtConfDao.selectList(deviceExtTemp);
	}

	@Override
	public int insertMetadataExtModel(MetadataExtModelEntity metadataExtModel) {
		// TODO Auto-generated method stub
		return deviceExtConfDao.insertMetadataExtModel(metadataExtModel);
	}

	@Override
	public int updateMetadataExtModel(MetadataExtModelEntity metadataExtModel) {
		if (metadataExtModel == null)return 0;
		return deviceExtConfDao.updateMetadataExtModel(metadataExtModel);
	}

	@Override
	public int deleteMetadataExtModel(MetadataExtModelEntity metadataExtModel) {
		// TODO Auto-generated method stub
		if (metadataExtModel == null)
			return 0;
		return deviceExtConfDao.deleteMetadataExtModel(metadataExtModel);
	}

	@Override
	public MetadataExtModelEntity selectone(
			MetadataExtModelEntity metadataExtModel) {
		// TODO Auto-generated method stub
		return deviceExtConfDao.selectone(metadataExtModel);
	}

	@Override
	public List<MetadataExtModelEntity> selectList(
			MetadataExtModelEntity metadataExtModel) {
		// TODO Auto-generated method stub
		return deviceExtConfDao.selectList(metadataExtModel);
	}

	@Override
	public List<Map<String, Object>> selAllExtTempList(Map<String, String> param) {
		List<Map<String, Object>> returnList= new ArrayList<>();
		//获取所有的外设模板
		DeviceExtTempEntity tempEntity = new DeviceExtTempEntity();
		if (param!=null && param.get("device_type")!=null && !param.get("device_type").equals("")) {
			tempEntity.setDevice_type(Integer.valueOf(param.get("device_type")));
		}
		List<DeviceExtTempEntity> extTempList = deviceExtConfDao.selectListByDeviceType(tempEntity);
		for (DeviceExtTempEntity deviceExtTempEntity : extTempList) {
			Map<String, Object> extTmpMap = new HashMap<String, Object>();
			Integer idx = deviceExtTempEntity.getDevice_tpl_idx();
			//根据ext模板idx获取该模板的所有设备list
			List<Map<String, Object>> extList = deviceExtConfDao.selExtDetailListByIdx(idx);
			/*
			 *  private Integer device_tpl_idx;
				private String device_tpl_ID;
				private String device_tpl_desc;
				private String device_type;
			 * */
			extTmpMap.put("device_tpl_idx", deviceExtTempEntity.getDevice_tpl_idx());
			extTmpMap.put("device_tpl_ID", deviceExtTempEntity.getDevice_tpl_ID());//外设模板id
			extTmpMap.put("device_tpl_desc", deviceExtTempEntity.getDevice_tpl_desc());//外设描述
			extTmpMap.put("device_type", deviceExtTempEntity.getDevice_type());//外设类型
			extTmpMap.put("extDetailList", extList);
			returnList.add(extTmpMap);
		}
		return returnList;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public DeviceExtLogicPageEntity SelExtTempListByParam(DeviceExtLogicPageEntity pageEntity) {
		DeviceExtLogicPageEntity deviceExtLogicPageEntity = new DeviceExtLogicPageEntity();
		deviceExtLogicPageEntity = deviceExtConfDao.SelExtTempListByParam(pageEntity);
		List<DeviceExtLogicPageEntity> list = deviceExtLogicPageEntity.getRows();
		for (Object object : list) {
			Map<String,Object> map =  (Map<String,Object>) object;
			int idx = 0;
			if (map.get("device_tpl_idx")!=null && !map.get("device_tpl_idx").toString().equals("")) {
				idx = Integer.valueOf(map.get("device_tpl_idx").toString());
			}
			//根据ext模板idx获取该模板的所有设备list
			String content = "";
			List<Map<String, Object>> extList = deviceExtConfDao.selExtDetailListByIdx(idx);
			for (Map<String, Object> ext : extList) {
				if (ext.get("logic_obj_desc")!=null && !ext.get("logic_obj_desc").toString().equals("")) {
					String str1 = JsonUtils.fromJson(ext.get("logic_obj_desc").toString(), Map.class).get("zh_CN").toString();
					String str2 = JsonUtils.fromJson(ext.get("ext_model_desc").toString(), Map.class).get("zh_CN").toString();
					content += str1+":"+str2+",";
				}
			}
			if(content.length() >= 1){
				content = content.substring(0,content.length()-1);
			}
			map.put("content", content);
			map.put("extDetailList", extList);
		}
		return deviceExtLogicPageEntity;
	}
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity addExtTemp(String extConfStr) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			//保存该设备的config数据,先删除该设备原有的config数据
			Map<String, Object> extMap = JsonUtils.fromJson(extConfStr, Map.class);
			String device_tpl_ID = extMap.get("device_tpl_ID")==null?"":extMap.get("device_tpl_ID").toString();
			String device_tpl_desc = extMap.get("device_tpl_desc")==null?"":extMap.get("device_tpl_desc").toString();
			//设备类型
			String device_type = extMap.get("device_type")==null?"":extMap.get("device_type").toString();
			
			if (!StringUtils.hasText(device_tpl_ID)
					|| !StringUtils.hasText(device_tpl_desc)
					|| !StringUtils.hasText(device_type)) {
				
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			
			DeviceExtTempEntity deviceExtTempEntity = new DeviceExtTempEntity();
			deviceExtTempEntity.setDevice_tpl_ID(device_tpl_ID.trim());
			deviceExtTempEntity.setDevice_tpl_desc(device_tpl_desc.trim());
			deviceExtTempEntity.setDevice_type(Integer.valueOf(device_type));
			//根据id查询是否有这个设备
			int cou1 = deviceExtConfDao.countDeviceExtTempById(deviceExtTempEntity);
			if(cou1>=1){
				resultEntity.setValue(false, "模板ID已经被占用，请检查！");
				return resultEntity;
			}
			//根据名称查询是否有这个设备
			int cou2 = deviceExtConfDao.countDeviceExtTempByDesc(deviceExtTempEntity);
			if(cou2>=1){
				resultEntity.setValue(false, "模板名已经被占用，请检查！");
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
			//新增设备类型
			int ret1 = deviceExtConfDao.insertDeviceExtTemp(deviceExtTempEntity);
			if(ret1<=0){
				resultEntity.setValue(false, "新增设备模板失败");
				throw new RuntimeException("新增设备模板失败");
			}
			
			//插入新的ext_config
			if (extMap.get("extDetailList") instanceof List) {
				List<Map<String, Object>> detailList = (List<Map<String, Object>>) extMap.get("extDetailList");
				for (Map<String, Object> map : detailList) {
					String comm = map.get("ext_comm_conf")==null?"":JsonUtils.toJson(map.get("ext_comm_conf")).toString();
					String extend = map.get("ext_extend_conf")==null?"":JsonUtils.toJson(map.get("ext_extend_conf")).toString();
					String obj_idx = map.get("logic_obj_idx")==null?"":map.get("logic_obj_idx").toString();
					String model_idx = map.get("ext_model_idx")==null?"":map.get("ext_model_idx").toString();
					
					if(!StringUtils.hasText(obj_idx)){
						throw new RuntimeException("logic_obj_idx 数据为空");
					}
					if(!StringUtils.hasText(model_idx)){
						throw new RuntimeException("ext_model_idx 数据为空");
					}
					DeviceExtConfigEntity extConfigEntity = new DeviceExtConfigEntity(); 
					extConfigEntity.setLibrary_idx(0);
					extConfigEntity.setDevice_tpl_type(0);
					extConfigEntity.setDevice_ext_id(deviceExtTempEntity.getDevice_tpl_idx());//原模板的id
					extConfigEntity.setExt_comm_conf(comm);
					extConfigEntity.setExt_extend_conf(extend);
					extConfigEntity.setLogic_obj_idx(Integer.valueOf(obj_idx));
					extConfigEntity.setExt_model_idx(Integer.valueOf(model_idx));
					//插入device_ext_config表
					deviceExtConfDao.insertDeviceExtConfig(extConfigEntity);
				}
			}
			resultEntity.setValue(true, "success");
			//硬件与逻辑名配置模板IDX｜硬件与逻辑名配置模板名
			resultEntity.setRetMessage("|IDX:"+deviceExtTempEntity.getDevice_tpl_idx()+"|模板名:"+deviceExtTempEntity.getDevice_tpl_desc()+"||");
			
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
	public ResultEntity updateExtTemp(String extConfStr) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			//保存该设备的config数据,先删除该设备原有的config数据
			Map<String, Object> extMap = JsonUtils.fromJson(extConfStr, Map.class);
			String device_tpl_idx = extMap.get("device_tpl_idx")==null?"":extMap.get("device_tpl_idx").toString();
			String device_tpl_ID = extMap.get("device_tpl_ID")==null?"":extMap.get("device_tpl_ID").toString();
			String device_tpl_desc = extMap.get("device_tpl_desc")==null?"":extMap.get("device_tpl_desc").toString();
			//设备类型
			String device_type = extMap.get("device_type")==null?"":extMap.get("device_type").toString();
			
			if (!StringUtils.hasText(device_tpl_idx)
					|| !StringUtils.hasText(device_tpl_ID)
					|| !StringUtils.hasText(device_tpl_desc)
					|| !StringUtils.hasText(device_type)) {
				
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			
			DeviceExtTempEntity deviceExtTempEntity = new DeviceExtTempEntity();
			deviceExtTempEntity.setDevice_tpl_idx(Integer.valueOf(device_tpl_idx));
			deviceExtTempEntity.setDevice_tpl_ID(device_tpl_ID.trim());
			deviceExtTempEntity.setDevice_tpl_desc(device_tpl_desc.trim());
			deviceExtTempEntity.setDevice_type(Integer.valueOf(device_type));
			
			int cou1 = deviceExtConfDao.countDeviceExtTempById(deviceExtTempEntity);
			if(cou1>=1){
				resultEntity.setValue(false, "模板ID已经被占用，请检查！");
				return resultEntity;
			}
			//根据名称查询是否有这个设备
			int cou2 = deviceExtConfDao.countDeviceExtTempByDesc(deviceExtTempEntity);
			if(cou2>=1){
				resultEntity.setValue(false, "模板名已经被占用，请检查！");
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
			DeviceExtConfigEntity deviceExtConfig = new DeviceExtConfigEntity();
			deviceExtConfig.setDevice_ext_id(Integer.valueOf(device_tpl_idx));
			deviceExtConfig.setDevice_tpl_type(0);//只删除模板的
			//先删除原来的ext_config
			deviceExtConfDao.deleteDeviceExtConfig(deviceExtConfig);
			//插入新的ext_config
			if (extMap.get("extDetailList") instanceof List) {
				List<Map<String, Object>> detailList = (List<Map<String, Object>>) extMap.get("extDetailList");
				for (Map<String, Object> map : detailList) {
					String comm = map.get("ext_comm_conf")==null?"":JsonUtils.toJson(map.get("ext_comm_conf")).toString();
					String extend = map.get("ext_extend_conf")==null?"":JsonUtils.toJson(map.get("ext_extend_conf")).toString();
					String obj_idx = map.get("logic_obj_idx")==null?"":map.get("logic_obj_idx").toString();
					String model_idx = map.get("ext_model_idx")==null?"":map.get("ext_model_idx").toString();
					
					if(!StringUtils.hasText(obj_idx)){
						throw new RuntimeException("logic_obj_idx 数据为空");
					}
					if(!StringUtils.hasText(model_idx)){
						throw new RuntimeException("ext_model_idx 数据为空");
					}
					DeviceExtConfigEntity extConfigEntity = new DeviceExtConfigEntity(); 
					extConfigEntity.setLibrary_idx(0);
					extConfigEntity.setDevice_tpl_type(0);
					extConfigEntity.setDevice_ext_id(Integer.valueOf(device_tpl_idx));//原模板的id
					extConfigEntity.setExt_comm_conf(comm);
					extConfigEntity.setExt_extend_conf(extend);
					extConfigEntity.setLogic_obj_idx(Integer.valueOf(obj_idx));
					extConfigEntity.setExt_model_idx(Integer.valueOf(model_idx));
					//插入device_ext_config表
					deviceExtConfDao.insertDeviceExtConfig(extConfigEntity);
				}
			}
			//保存新的模板信息
			int ret = deviceExtConfDao.updateDeviceExtTemp(deviceExtTempEntity);
			
			//下发配置到设备端。
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("table_name", "device_ext_config");
			paramMap.put("model_idx", device_tpl_idx);
			ResultEntity result = deviceConfigService.queryDevIdsByModelAndModelIdx(JsonUtils.toJson(paramMap));
			if(result.getResult() != null && result.getResult().toString().length() > 0){
				List<DeviceConfigEntity> configEntities = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()),
				new TypeReference<List<DeviceConfigEntity>>() {});
				for(DeviceConfigEntity configEntity : configEntities){
					DeviceConfigEntity deviceConfigEntity = new DeviceConfigEntity();
					deviceConfigEntity.setDevice_ext_tpl_idx(configEntity.getDevice_ext_tpl_idx());
					deviceConfigEntity.setDevice_ext_tpl_flg(configEntity.getDevice_ext_tpl_flg());
					deviceService.pushMessage(deviceConfigEntity, configEntity.getLibrary_idx(), configEntity.getDevice_idx());
				}
				
			}
			
			//硬件与逻辑名配置模板IDX｜硬件与逻辑名配置模板名
			resultEntity.setValue(true, "success");
			resultEntity.setRetMessage("|IDX:"+deviceExtTempEntity.getDevice_tpl_idx()+"|模板名:"+deviceExtTempEntity.getDevice_tpl_desc()+"||");
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
			throw new RuntimeException(e);
		}
		return resultEntity;
	}
	
	@Override
	public ResultEntity delExtTemp(String idx) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(!StringUtils.hasText(idx)){
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			DeviceExtTempEntity d1 = new DeviceExtTempEntity();
			d1.setDevice_tpl_idx(Integer.valueOf(idx));
			
			//查询是否在使用
			DeviceConfigEntity deviceConfigEntity = new DeviceConfigEntity();
			deviceConfigEntity.setDevice_ext_tpl_idx(Integer.valueOf(idx));
			int ret = deviceExtConfDao.countUsingExtTempplate(deviceConfigEntity);
			if(ret > 0){
				resultEntity.setValue(false, "1");//此模板正在使用，无法删除
				return resultEntity;
			}
			//先删除ext_config的数据
			DeviceExtConfigEntity deviceExtConfig = new DeviceExtConfigEntity();
			deviceExtConfig.setDevice_ext_id(Integer.valueOf(idx));
			deviceExtConfig.setDevice_tpl_type(0);//删除模板消息
			int ret2 = deviceExtConfDao.deleteDeviceExtConfig(deviceExtConfig);
			if(ret2>=0){
				int ret3 = deviceExtConfDao.deleteDeviceExtTemp(d1);
				if(ret3<0){
					throw new RuntimeException("删除device_ext_template数据失败");
				}
			}else{
				throw new RuntimeException("删除device_ext_config数据失败");
			}
			resultEntity.setValue(true, "success");
			//硬件与逻辑名配置模板IDX｜硬件与逻辑名配置模板名
			resultEntity.setRetMessage("|IDX:"+d1.getDevice_tpl_idx()+"|模板名:"+d1.getDevice_tpl_desc()+"||");
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
			throw new RuntimeException(e);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity delMultiExtTemp(String req) {
		ResultEntity resultEntity = new ResultEntity();
		StringBuilder idxSb=new StringBuilder("硬件与逻辑名配置模板IDX:");
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
				String extId = map.get("extId")==null?"":map.get("extId").toString();
				
				if (idx.equals("")) {
					cannotDel += extId+",";
					continue;
				}
				
				DeviceExtTempEntity d1 = new DeviceExtTempEntity();
				d1.setDevice_tpl_idx(Integer.valueOf(idx));
				
				//查询是否在使用
				DeviceConfigEntity deviceConfigEntity = new DeviceConfigEntity();
				deviceConfigEntity.setDevice_ext_tpl_idx(Integer.valueOf(idx));
				int ret = deviceExtConfDao.countUsingExtTempplate(deviceConfigEntity);
				if(ret > 0){
					cannotDel += extId+",";
					continue;
				}
				//先删除ext_config的数据
				DeviceExtConfigEntity deviceExtConfig = new DeviceExtConfigEntity();
				deviceExtConfig.setDevice_ext_id(Integer.valueOf(idx));
				deviceExtConfig.setDevice_tpl_type(0);//删除模板消息
				int ret2 = deviceExtConfDao.deleteDeviceExtConfig(deviceExtConfig);
				if(ret2>=0){
					int ret3 = deviceExtConfDao.deleteDeviceExtTemp(d1);
					idxSb.append(d1.getDevice_tpl_idx()).append(",");
				}
			}
			if (cannotDel.length()>0) {
				cannotDel = cannotDel.substring(0,cannotDel.length()-1);
			}
			resMap.put("cannotDel", cannotDel);
  			resultEntity.setValue(true, "success","",resMap);
  			//硬件与逻辑名配置模板IDX｜硬件与逻辑名配置模板名
			resultEntity.setRetMessage(idxSb.toString());
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
			throw new RuntimeException(e);
		}
		return resultEntity;
	}
	
	@Override
	public List<Map<String, Object>> SelExtData(String json) {
		List<Map<String, Object>> returnList= new ArrayList<>();
		DeviceExtConfigEntity dConfigEntity = JsonUtils.fromJson(json, DeviceExtConfigEntity.class);
		//获取设备外设自定义配置
		List<Map<String, Object>> extList = deviceExtConfDao.selExtDataListByIdx(dConfigEntity.getDevice_ext_id());
			Map<String, Object> extTmpMap = new HashMap<String, Object>();
			extTmpMap.put("device_tpl_id", dConfigEntity.getDevice_ext_id());
			extTmpMap.put("device_tpl_type", 1);
			extTmpMap.put("extDetailList", extList);
			returnList.add(extTmpMap);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity GetDevExtModel(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			List<Integer> devIdxList=JsonUtils.fromJson(req, List.class);
			if(!CollectionUtils.isEmpty(devIdxList)){//写成List 其实只有一个值
				List<DeviceConfigEntity> deviceConfigs=deviceConfigDao.queryDeviceConfigInDeviceIdxArr(devIdxList);
				if(!CollectionUtils.isEmpty(deviceConfigs)){
					DeviceConfigEntity deviceConfig=deviceConfigs.get(0);
					DeviceExtConfigEntity deviceExtConfig=new DeviceExtConfigEntity();
					DeviceMonConfigEntity deviceMonConfig=new DeviceMonConfigEntity();
					if(DeviceConfigEntity.IS_MODEL==deviceConfig.getDevice_ext_tpl_flg()){
						deviceExtConfig.setDevice_ext_id(deviceConfig.getDevice_ext_tpl_idx());
						deviceExtConfig.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
						deviceMonConfig.setDevice_mon_tpl_idx(deviceConfig.getDevice_monitor_tpl_idx());
						deviceMonConfig.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
					}else if(DeviceConfigEntity.IS_NOT_MODEL==deviceConfig.getDevice_ext_tpl_flg()){
						deviceExtConfig.setDevice_ext_id(deviceConfig.getDevice_idx());
						deviceExtConfig.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
						deviceMonConfig.setDevice_mon_tpl_idx(deviceConfig.getDevice_idx());
						deviceMonConfig.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
					}
					List<PlcLogicObjEntity> plcLogicOBJList=plcLogicObjDao.SelPlcLogicObjectEntity();
					List<DeviceExtConfigEntity> deviceExtConfigs=deviceExtConfDao.selectByDeviceMonitor(deviceExtConfig);
					List<DeviceMonitorEntity> deviceMonConfigs=deviceMonConfDao.selectForMonitor(deviceMonConfig);
					Map<String,Object> retMap=new HashMap<>();
					retMap.put("plcLogicOBJList", plcLogicOBJList);
					retMap.put("deviceExtConfigs", deviceExtConfigs);
					retMap.put("deviceMonConfigs", deviceMonConfigs);
					result.setResult(retMap);
					result.setState(true);
				}
			}
		}
		return result;
	}

	
	/**
	 * 获取设备的硬件配置
	 * @param req
	 * @return
	 */
	public List<DeviceExtConfigRemoteEntity> getDeviceExtConfig(DeviceConfigEntity configEntity){
		 
		if(configEntity == null || configEntity.getDevice_ext_tpl_flg() == null ) return null;
		
		DeviceExtConfigEntity params = new DeviceExtConfigEntity();
		if(DeviceConfigEntity.IS_MODEL.equals(configEntity.getDevice_ext_tpl_flg())){// 1 全局模板
			params.setDevice_ext_id(configEntity.getDevice_ext_tpl_idx());
			params.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);// 0 表示模板 1 表示数据 
			List<DeviceExtConfigRemoteEntity> deviceExtConfigs = deviceExtConfDao.selectListByRemoteDevice(params);
			return deviceExtConfigs;
		}else if(DeviceConfigEntity.IS_NOT_MODEL.equals(configEntity.getDevice_ext_tpl_flg())){//0 设备自定义模板
			//自定义模板,根据图书馆id、设备id来查询，device_ext_id保存着设备id， 
			params.setLibrary_idx(configEntity.getLibrary_idx());
			params.setDevice_ext_id(configEntity.getDevice_idx());
			params.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
			List<DeviceExtConfigRemoteEntity> deviceExtConfigs = deviceExtConfDao.selectListByRemoteDevice(params);
			return deviceExtConfigs;
		}
		return null;
	}
	
	
}
