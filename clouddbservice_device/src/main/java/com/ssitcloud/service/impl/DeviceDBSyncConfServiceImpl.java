package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceDBSyncConfDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncTempEntity;
import com.ssitcloud.entity.page.DBSyncTempPageEntity;
import com.ssitcloud.service.DeviceConfigService;
import com.ssitcloud.service.DeviceDBSyncConfService;
import com.ssitcloud.service.DeviceService;
import com.ssitcloud.system.entity.DeviceDBSyncConfigRemoteEntity;

@Service
public class DeviceDBSyncConfServiceImpl implements DeviceDBSyncConfService {

	@Resource
	DeviceDBSyncConfDao deviceDBSyncConfDao;
	@Resource
	DeviceConfigDao deviceConfigDao;
	@Resource
	private DeviceConfigService deviceConfigService;
	@Resource
	private DeviceService deviceService;
	
	@Override
	public int insertDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig){
		return deviceDBSyncConfDao.insertDeviceDBSyncConf(deviceDBSyncConfig);
	}
	@Override
	public int updateDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig){
		return deviceDBSyncConfDao.updateDeviceDBSyncConf(deviceDBSyncConfig);
	}
	@Override
	public int deleteDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig){
		return deviceDBSyncConfDao.deleteDeviceDBSyncConf(deviceDBSyncConfig);
	}
	@Override
	public List<DeviceDBSyncConfigEntity> selectDeviceDBSyncConfig(DeviceDBSyncConfigEntity deviceDBSyncConfig){
		return deviceDBSyncConfDao.selectDeviceDBSyncConfig(deviceDBSyncConfig);
	}
	@Override
	public List<DeviceDBSyncConfigEntity> selectList(DeviceDBSyncConfigEntity deviceDBSyncConfig){
		return deviceDBSyncConfDao.selectList( deviceDBSyncConfig);
	}
	@Override
	public int insertDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp){
		return deviceDBSyncConfDao.insertDeviceDBSyncTemp( deviceDBSyncTemp);
	}
	@Override
	public int updateDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp){
		return deviceDBSyncConfDao.updateDeviceDBSyncTemp( deviceDBSyncTemp);
	}
	@Override
	public int deleteDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp){
		return deviceDBSyncConfDao.deleteDeviceDBSyncTemp( deviceDBSyncTemp);
	}
	@Override
	public DeviceDBSyncTempEntity selectDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp){
		//查询单个一般是通过主键查询
		return deviceDBSyncConfDao.selectDeviceDBSyncTemp( deviceDBSyncTemp);
	}
	@Override
	public List<DeviceDBSyncTempEntity> selectList(DeviceDBSyncTempEntity deviceDBSyncTemp){
		return deviceDBSyncConfDao.selectList(deviceDBSyncTemp);
	}
	@Override
	public List<Map<String, Object>> selAllDBSyncTempList(Map<String, String> param ) {
		List<Map<String, Object>> returnList= new ArrayList<>();
		//获取所有的运行模板
		DeviceDBSyncTempEntity tempEntity = new DeviceDBSyncTempEntity();
		if (param!=null && param.get("device_type")!=null && !param.get("device_type").equals("")) {
			tempEntity.setDevice_type(Integer.valueOf(param.get("device_type")));
		}
		List<DeviceDBSyncTempEntity> dbSyncTempList = deviceDBSyncConfDao.selectListByDeviceType(tempEntity);
		for (DeviceDBSyncTempEntity dbSyncTempEntity : dbSyncTempList) {
			Map<String, Object> dbSyncTmpMap = new HashMap<String, Object>();
			Integer idx = dbSyncTempEntity.getDevice_tpl_idx();
			List<Map<String, Object>> dbsyncList = deviceDBSyncConfDao.selDbsyncDetailListByIdx(idx);
			
			dbSyncTmpMap.put("device_tpl_idx", dbSyncTempEntity.getDevice_tpl_idx());
			dbSyncTmpMap.put("device_tpl_ID", dbSyncTempEntity.getDevice_tpl_ID());
			dbSyncTmpMap.put("device_tpl_desc", dbSyncTempEntity.getDevice_tpl_desc());
			dbSyncTmpMap.put("device_type", dbSyncTempEntity.getDevice_type());
			dbSyncTmpMap.put("dbSyncDeatilList", dbsyncList);
			returnList.add(dbSyncTmpMap);
		}
		return returnList;
	}
	
	@Override
	public int InsertDbsyncAndUpdDevCfg(DeviceDBSyncConfigEntity deviceDBSyncConfig) {
		int result = 0;
		try {
			result = deviceDBSyncConfDao.insertDeviceDBSyncConf(deviceDBSyncConfig);
			/*DeviceConfigEntity dEntity = new DeviceConfigEntity();
			dEntity.setDevice_idx(deviceDBSyncConfig.getDevice_dbsync_id());
			dEntity.setDevice_dbsync_tpl_flg(0);
			result += deviceConfigDao.updateDeviceConfig(dEntity);*/
		} catch (Exception e) {
			
		}
		return result;
	}

	/**
	 * 数据同步模板配置页面功能
	 * 查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DBSyncTempPageEntity selDbsynctemp(String json) {
		List<Map<String, Object>> libinfo = new ArrayList<>();
		DBSyncTempPageEntity dbSyncPageTemp = new DBSyncTempPageEntity();
		dbSyncPageTemp = JsonUtils.fromJson(json, DBSyncTempPageEntity.class);
		DBSyncTempPageEntity tempPageEntity = deviceDBSyncConfDao.seldbsyncTemp(dbSyncPageTemp);
		List<DBSyncTempPageEntity> list = tempPageEntity.getRows();
		for (DBSyncTempPageEntity dbconfEntity : list) {
			Map<String, Object> map = new HashMap<>();
			//这里面显示的都是模板，所以应该是通过模板id获取模板数据 而不是 dbconfEntity.getDevice_dbsync_id()
			List<DeviceDBSyncConfigEntity> dbSyncConfigEntities = deviceDBSyncConfDao.seldbsynctempconfigByidx(dbconfEntity.getDevice_tpl_idx());
			//DeviceDBSyncTempEntity dbSyncTempEntity = deviceDBSyncConfDao.seldbsynctempByidx(dbconfEntity.getDevice_dbsync_id());
			map.put("device_dbsync_id", dbconfEntity.getDevice_dbsync_id());
			map.put("device_tpl_type", dbconfEntity.getDevice_tpl_type());
			map.put("device_tpl_idx", dbconfEntity.getDevice_tpl_idx());
			map.put("device_tpl_ID", dbconfEntity.getDevice_tpl_ID());
			map.put("device_tpl_desc",dbconfEntity.getDevice_tpl_desc());
			map.put("device_type", dbconfEntity.getDevice_type());
			map.put("TempConfigInfo",dbSyncConfigEntities);
			libinfo.add(map);
		}
		tempPageEntity.setRows(libinfo);
		return tempPageEntity;
	}
	
	/**
	 * 数据同步模板配置页面功能
	 * 删除
	 */
	@Override
	public ResultEntity DelDbsynctemp(String json) {
		ResultEntity result=new ResultEntity();
		int ret = 0;
		try {
			StringBuilder idx=new StringBuilder("同步模板IDX:");
			List<DeviceDBSyncTempEntity> list = JsonUtils.fromJson(json, new TypeReference<List<DeviceDBSyncTempEntity>>() {});
			String cannotdel = "";
			for (DeviceDBSyncTempEntity deviceDBSyncTempEntity : list) {
				//先检查是否有被使用的
				int cou1 = deviceDBSyncConfDao.countUsingDBSyncTempplate(deviceDBSyncTempEntity);
				if(cou1>0){
					cannotdel += deviceDBSyncTempEntity.getDevice_tpl_desc()+",";
					continue;
				}
				//先删除device_dbsync_config表中的内容
				DeviceDBSyncConfigEntity deviceDBSyncConfigEntity = new DeviceDBSyncConfigEntity();
				deviceDBSyncConfigEntity.setDevice_tpl_type(0);
				deviceDBSyncConfigEntity.setDevice_dbsync_id(deviceDBSyncTempEntity.getDevice_tpl_idx());
				deviceDBSyncConfDao.deleteDeviceDBSyncConf(deviceDBSyncConfigEntity);
				
				int re = deviceDBSyncConfDao.deleteDBSyncTemp(deviceDBSyncTempEntity.getDevice_tpl_idx());
				if(re>=0){
					idx.append(deviceDBSyncTempEntity.getDevice_tpl_idx()).append(",");
				}else{
					cannotdel += deviceDBSyncTempEntity.getDevice_tpl_desc()+",";
					continue;
				}
			}
			if (!cannotdel.equals("")) {
				cannotdel = cannotdel.substring(0,cannotdel.length()-1);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("cannotdel", cannotdel);
			result.setResult(map);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setRetMessage(idx.toString());//同步模板IDX
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	/**
	 * 数据同步模板配置页面功能
	 * 更新
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity UpdDbsynctemp(String json) {
		ResultEntity result=new ResultEntity();
		int ret = 0;
		Map<String, Object> map = new HashMap<>();
		map = JsonUtils.fromJson(json, Map.class);
		try {
			DeviceDBSyncConfigEntity dSyncConfigEntity = new DeviceDBSyncConfigEntity();
			List<DeviceDBSyncConfigEntity> list = JsonUtils.fromJson(map.get("TempConfigInfo").toString(), new TypeReference<List<DeviceDBSyncConfigEntity>>() {});
			DeviceDBSyncTempEntity dbTempEntity = JsonUtils.fromJson(map.get("TempInfo").toString(), DeviceDBSyncTempEntity.class);
			
			dSyncConfigEntity.setDevice_dbsync_id(dbTempEntity.getDevice_tpl_idx());
			dSyncConfigEntity.setDevice_tpl_type(0);
			
			//先查询时否有相同ID的模板
			dbTempEntity.setDevice_tpl_ID(dbTempEntity.getDevice_tpl_ID().trim());
			dbTempEntity.setDevice_tpl_desc(dbTempEntity.getDevice_tpl_desc().trim());
			int cou1 = deviceDBSyncConfDao.countDeviceDBSyncTempById(dbTempEntity);
			if (cou1>=1) {
				result.setValue(false, "模板ID不能重复，请检查！",dbTempEntity.getDevice_tpl_ID()+"模板id不能重复","");
				return result;
			}
			int cou2 = deviceDBSyncConfDao.countDeviceDBSyncTempByName(dbTempEntity);
			if (cou2>=1) {
				result.setValue(false, "模板名称不能重复，请检查",dbTempEntity.getDevice_tpl_desc()+"模板名称不能重复","");
				return result;
			}
			
			//先删除再增加模板配置
			int re =deviceDBSyncConfDao.updateDeviceDBSyncTemp(dbTempEntity);
			if(re>0){
				deviceDBSyncConfDao.deleteDeviceDBSyncConf(dSyncConfigEntity);
			}
			for (DeviceDBSyncConfigEntity dConfigEntity : list) {
				dConfigEntity.setDevice_dbsync_id(dbTempEntity.getDevice_tpl_idx());
				ret +=deviceDBSyncConfDao.insertDeviceDBSyncConf(dConfigEntity);
			}
			result.setResult(ret);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setRetMessage("|同步模板IDX:"+dbTempEntity.getDevice_tpl_idx()+"|同步模板IDX:"+dbTempEntity.getDevice_tpl_desc()+"||");
		
			//下发配置到设备端。
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("table_name", "device_dbsync_config");
			paramMap.put("model_idx", dbTempEntity.getDevice_tpl_idx()+"");
			ResultEntity res = deviceConfigService.queryDevIdsByModelAndModelIdx(JsonUtils.toJson(paramMap));
			if(res.getResult() != null && res.getResult().toString().length() > 0){
				List<DeviceConfigEntity> configEntities = JsonUtils.fromJson(JsonUtils.toJson(res.getResult()),
				new TypeReference<List<DeviceConfigEntity>>() {});
				for(DeviceConfigEntity configEntity : configEntities){
					DeviceConfigEntity deviceConfigEntity = new DeviceConfigEntity();
					deviceConfigEntity.setDevice_dbsync_tpl_idx(configEntity.getDevice_ext_tpl_idx());
					deviceConfigEntity.setDevice_dbsync_tpl_flg(configEntity.getDevice_ext_tpl_flg());
					deviceService.pushMessage(deviceConfigEntity, configEntity.getLibrary_idx(), configEntity.getDevice_idx());
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	/**
	 * 数据同步模板配置页面功能
	 * 新增
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity AddDbsynctemp(String json) {
		ResultEntity result=new ResultEntity();
		int ret = 0;
		Map<String , Object> map = new HashMap<>();
		map = JsonUtils.fromJson(json, Map.class);
		
		try {
			List<DeviceDBSyncConfigEntity> list = JsonUtils.fromJson(map.get("TempConfigInfo").toString(), new TypeReference<List<DeviceDBSyncConfigEntity>>() {});
			
			DeviceDBSyncTempEntity dbTempEntity = JsonUtils.fromJson(map.get("TempInfo").toString(), DeviceDBSyncTempEntity.class);
			//先查询时否有相同ID的模板
			dbTempEntity.setDevice_tpl_ID(dbTempEntity.getDevice_tpl_ID().trim());
			dbTempEntity.setDevice_tpl_desc(dbTempEntity.getDevice_tpl_desc().trim());
			int cou1 = deviceDBSyncConfDao.countDeviceDBSyncTempById(dbTempEntity);
			if (cou1>=1) {
				result.setValue(false, "模板ID不能重复，请检查！",dbTempEntity.getDevice_tpl_ID()+"模板ID不能重复","");
				return result;
			}
			int cou2 = deviceDBSyncConfDao.countDeviceDBSyncTempByName(dbTempEntity);
			if (cou2>=1) {
				result.setValue(false, "模板名称不能重复，请检查",dbTempEntity.getDevice_tpl_desc()+"模板名称不能重复","");
				return result;
			}
			
			
			//新增temp表数据
			int re = deviceDBSyncConfDao.insertDeviceDBSyncTemp(dbTempEntity);
			if(re<=0){
				throw new RuntimeException("新增数据同步模板出错");
			}
			if(CollectionUtils.isNotEmpty(list)){
				for (DeviceDBSyncConfigEntity dbSyncConfigEntity : list) {
					dbSyncConfigEntity.setDevice_dbsync_id(dbTempEntity.getDevice_tpl_idx());
					
					ret += deviceDBSyncConfDao.insertDeviceDBSyncConf(dbSyncConfigEntity);
				}
				if(ret<=0){
					throw new RuntimeException("新增数据同步模版配置出错");
				}
			}
			result.setResult(ret);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setRetMessage("|同步模板IDX:"+dbTempEntity.getDevice_tpl_idx()+"|同步模板名:"+dbTempEntity.getDevice_tpl_desc()+"||");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	/**
	 * 获取设备数据库配置
	 * @param configEntity
	 * @return
	 */
	public List<DeviceDBSyncConfigRemoteEntity> getDeviceDBSyncConfig(DeviceConfigEntity configEntity){
		
		if(configEntity == null || configEntity.getDevice_dbsync_tpl_flg() == null) return null;
		DeviceDBSyncConfigEntity params = new DeviceDBSyncConfigEntity();
		
		if(DeviceConfigEntity.IS_MODEL.equals(configEntity.getDevice_dbsync_tpl_flg())){
			
			params.setDevice_dbsync_id(configEntity.getDevice_dbsync_tpl_idx());
			params.setDevice_tpl_type(DeviceDBSyncConfigEntity.IS_MODEL);
			List<DeviceDBSyncConfigRemoteEntity> deviceDbSyncConfigs=deviceDBSyncConfDao.selectListByRemoteDeviceSync(params);
			return deviceDbSyncConfigs;
			
		}else if(DeviceConfigEntity.IS_NOT_MODEL.equals(configEntity.getDevice_dbsync_tpl_flg())){
			
			params.setLibrary_idx(configEntity.getLibrary_idx());
			params.setDevice_dbsync_id(configEntity.getDevice_idx());//device_tpl_type
			params.setDevice_tpl_type(DeviceDBSyncConfigEntity.IS_NOT_MODEL);
			List<DeviceDBSyncConfigRemoteEntity> deviceDbSyncConfigs=deviceDBSyncConfDao.selectListByRemoteDeviceSync(params);
			return deviceDbSyncConfigs;
			
		}
		
		return null;
	}
	
	 
}
