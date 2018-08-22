package com.ssitcloud.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Consts;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.exception.DeleteDeviceErrorExeception;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceAcsLoginInfoDao;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceDBSyncConfDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.dao.DeviceExtConfDao;
import com.ssitcloud.dao.DeviceGroupDao;
import com.ssitcloud.dao.DeviceMonConfDao;
import com.ssitcloud.dao.DeviceRunConfDao;
import com.ssitcloud.dao.MetaRunConfigDao;
import com.ssitcloud.dao.RelDeviceGroupDao;
import com.ssitcloud.dao.RelOperatorGroupDao;
import com.ssitcloud.datasync.entity.DeviceAcsModuleEntity;
import com.ssitcloud.devmgmt.entity.DevStatusCode;
import com.ssitcloud.devmgmt.entity.FileUploadState;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;
import com.ssitcloud.entity.DeivceIdxAndIDEntity;
import com.ssitcloud.entity.DeviceAcsLoginInfoEntity;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncConfigEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.DeviceGroupEntity;
import com.ssitcloud.entity.DeviceMgmtEntity;
import com.ssitcloud.entity.DeviceMonConfigEntity;
import com.ssitcloud.entity.DevicePosition;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.MetaRunConfigEntity;
import com.ssitcloud.entity.RelDeviceGroupEntity;
import com.ssitcloud.entity.RelOperatorGroupEntity;
import com.ssitcloud.entity.ServiceDevicePageEntity;
import com.ssitcloud.entity.page.DeviceMgmtAppPageEntity;
import com.ssitcloud.entity.page.DeviceMgmtPageEntity;
import com.ssitcloud.entity.page.DevicePageEntity;
import com.ssitcloud.interfaceconfig.service.JsonDataService;
import com.ssitcloud.node.entity.FileManagerEntity;
import com.ssitcloud.param.DeviceRegisterParam;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.service.DeviceAcsLoginInfoService;
import com.ssitcloud.service.DeviceDBSyncConfService;
import com.ssitcloud.service.DeviceExtConfService;
import com.ssitcloud.service.DeviceMonConfService;
import com.ssitcloud.service.DeviceRunConfService;
import com.ssitcloud.service.DeviceService;
import com.ssitcloud.system.entity.DeviceDBSyncConfigRemoteEntity;
import com.ssitcloud.system.entity.DeviceExtConfigRemoteEntity;
import com.ssitcloud.system.entity.DeviceRunConfigRemoteEntity;
import com.ssitcloud.system.entity.ReturnResultEntity;
import com.ssitcloud.utils.MBeanUtil;

/**
 * 
 * @comment 设备表Service
 * 
 * @author hwl
 * @data 2016年4月7日
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	
	@Resource
	private DeviceDao deviceDao;
	
	@Resource
	private DeviceConfigDao deviceConfigDao;
	@Resource
	private DeviceGroupDao deviceGroupDao;
	
	@Resource
	private RelOperatorGroupDao relOperatorGroupDao;
	
	@Resource
	private RelDeviceGroupDao relDeviceGroupDao;
	
	@Resource
	private DeviceExtConfDao deviceExtConfDao;
	
	@Resource
	private DeviceExtConfService extConfService;
	
	@Resource
	private DeviceRunConfDao deviceRunConfDao;
	
	@Resource
	private DeviceRunConfService deviceRunService;
	
	@Resource
	private DeviceDBSyncConfDao deviceDBSyncConfDao;
	
	@Resource
	private DeviceDBSyncConfService deviceDBSyncService;
	
	@Resource
	private DeviceMonConfDao deviceMonConfDao;
	@Resource
	private DeviceMonConfService monService;
	
	@Resource
	private MetaRunConfigDao metaRunConfigDao;
	
	@Resource
	private DeviceAcsLoginInfoDao deviceAcsLoginInfoDao;
	@Resource
	private DeviceAcsLoginInfoService acsLoginInfoService;
	@Resource
	private JsonDataService jsonDataService;
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	@Override
	public int addDevice(DeviceEntity deviceEntity) {
		return deviceDao.insert(deviceEntity);
	}

	@Override
	public int updDevice(DeviceEntity deviceEntity) {
		return deviceDao.update(deviceEntity);
	}

	@Override
	public int delDevice(DeviceEntity deviceEntity) {
		return deviceDao.delete(deviceEntity);
	}

	@Override
	public List<DeviceEntity> selbyidDevice(DeviceEntity deviceEntity) {
		return deviceDao.selectByid(deviceEntity);
	}
	@Override
	public PageEntity selbyPage(PageEntity page){
		return deviceDao.selectPage(page);
	}
	@Override
	public PageEntity SelectDeviceMgmt(Map<String, String> map ) {
		return deviceDao.selectdevicemgmt(map);
	}

	@Override
	public int DeleteDeviceMgmt(DeviceMgmtEntity deviceMgmtEntity) throws DeleteDeviceErrorExeception{
		//返回1代表没有抛异常 ，视为正常
		return deviceDao.deletedevicemgmt(deviceMgmtEntity);
	}
	@Override
	public List<DeivceIdxAndIDEntity> selectDeviceIdAndIdx(){
		return deviceDao.selectDeviceIdAndIdx();
	}
	/*@Override
	public int AddDeviceMgmt(DeviceMgmtEntity deviceMgmtEntity) {
		// TODO Auto-generated method stub
		return deviceDao.adddevicemgmt(deviceMgmtEntity);
	}*/


	@Override
	public DevicePageEntity selbyDevicePage(DevicePageEntity page,Integer operator_idx,boolean devGroupLimit) {
		return deviceDao.selectPage(page,operator_idx,devGroupLimit);
	}

	@Override
	public Integer hasDeviceCode(String deviceCode) {
		return deviceDao.hasDeviceCode(deviceCode);
	}
	
	@Override
	public Map<String, Object> queryDeviceByDeviceCode(String deviceCode) {
		return deviceDao.queryDeviceByDeviceCode(deviceCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity deviceRegister(DeviceRegisterParam registerParam) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			//设备数据拼装，保存设备信息，获取新增成功之后的idx
			DeviceEntity deviceEntity = new DeviceEntity();
			deviceEntity = setDevice(registerParam);
			int ret = deviceDao.insert(deviceEntity);
			if (deviceEntity==null || deviceEntity.getDevice_idx()==null) {
				//设备新增失败
				throw new RuntimeException("设备新增失败");
			}
			
			//保存到redis
			JedisUtils.getInstance().set(RedisConstant.DEVICE+deviceEntity.getDevice_id(), JsonUtils.toJson(deviceEntity));
			//将设备id添加到集合中，key为lib_idx
			JedisUtils.getInstance().sadd(RedisConstant.LIBRARY_DEVICEID+deviceEntity.getLibrary_idx()+""
			,deviceEntity.getDevice_id());
			//将设备idx与设备id添加到缓存中
			JedisUtils.getInstance().set(RedisConstant.DEVICE_KEYS+deviceEntity.getDevice_idx()
			,deviceEntity.getDevice_id());
			//获取设备idx
			Integer deviceIdx = deviceEntity.getDevice_idx();
			//保存设备acs信息
			if(registerParam.getAcsList()!=null){
				for (Map<String, Object> acsmap : registerParam.getAcsList()) {
					DeviceAcsLoginInfoEntity infoEntity = new DeviceAcsLoginInfoEntity();
					Integer protocol_tpl_idx = Integer.valueOf(acsmap.get("protocol_tpl_idx").toString());
					Integer library_idx = Integer.valueOf(acsmap.get("library_idx").toString());
					String login_ip = acsmap.get("login_ip").toString();
					Integer login_port = Integer.valueOf(acsmap.get("login_port").toString());
					String login_username = acsmap.get("login_username").toString();
					String login_pwd = acsmap.get("login_pwd").toString();
					String acs_service_name = acsmap.get("acs_service_name").toString();
					
					//ACS配置增加的登录信息
					String login_check = acsmap.get("login_check").toString();
					String login_code = acsmap.get("login_code").toString();
					String login_count = acsmap.get("login_count").toString();
					String login_type = acsmap.get("login_type").toString();
					
					infoEntity.setAcs_service_name(acs_service_name);
					infoEntity.setDevice_idx(deviceIdx);
					infoEntity.setLibrary_idx(library_idx);
					infoEntity.setLogin_ip(login_ip);
					infoEntity.setLogin_port(login_port);
					infoEntity.setLogin_pwd(login_pwd);
					infoEntity.setLogin_username(login_username);
					infoEntity.setLogin_check(login_check);
					infoEntity.setLogin_code(login_code);
					infoEntity.setLogin_count(login_count);
					infoEntity.setLogin_type(login_type);
					infoEntity.setProtocol_tpl_idx(protocol_tpl_idx);
					
					deviceAcsLoginInfoDao.insertAcsInfo(infoEntity);
				}
				Map<String,Object> insertMap = new HashMap<String,Object>();
				// 转换提日期输出格式
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String modifyTime = dateFormat.format(new java.sql.Date(System.currentTimeMillis())).toString();
				insertMap.put("table_name", "acs_protocols");
				insertMap.put("lib_id", registerParam.getLibId());
				insertMap.put("lib_idx", registerParam.getLibrary_idx());
				insertMap.put("device_id", registerParam.getDeviceId());
				insertMap.put("issync", "0");
				insertMap.put("sync_type","xml");
				insertMap.put("last_modify_time",modifyTime);
				
				deviceDao.saveFileUploadFlag(insertMap);
				
				//下发acs配置数据到设备端
				List<DeviceAcsModuleEntity> acsModuleEntities = 
						acsLoginInfoService.loadAcsXml(insertMap.get("lib_idx").toString(),insertMap.get("device_id").toString());
				if(acsModuleEntities != null && !acsModuleEntities.isEmpty()){
					ResultEntity entity = postUrl("uploadAcsConfig", JsonUtils.toJson(acsModuleEntities));
					if(entity.getState()){
						FileManagerEntity fileManagerEntity = JsonUtils.fromJson(JsonUtils.toJson(entity.getResult()), FileManagerEntity.class);
						downLoadFileURLToDevice(fileManagerEntity);
					}
				}
				
			}
			if (registerParam.getRegion()!=null) {
				//保存地理位置信息
				Map<String, Object> deviceExtendInfoMap = new HashMap<>();
				deviceExtendInfoMap.put("regi_code", registerParam.getRegion());
				deviceExtendInfoMap.put("device_idx", deviceIdx);
				Map<String, Object> map = deviceDao.queryRegionByCode(deviceExtendInfoMap);
				if(map!=null){
					deviceExtendInfoMap.put("region_idx", map.get("regi_idx"));
					//查询有没有
					Map<String, Object> extendInfo = deviceDao.queryDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					
					if(extendInfo==null){
						deviceDao.insertDeviceExtendInfo(deviceExtendInfoMap);
					}else{
						deviceDao.updateDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					}
				}
			}
			
			
			//保存设备的配置信息
			DeviceConfigEntity configEntity = new DeviceConfigEntity();
			//如果是使用模板
			//硬件与逻辑配置
			String extSubmit = registerParam.getExtTempSubmit();
			String runSubmit = registerParam.getRunTempSubmit();
			if(StringUtils.hasText(extSubmit)){
				//如果不为空则为使用自定义数据
				configEntity.setDevice_ext_tpl_idx(Integer.valueOf(registerParam.getExtTempId()));//自定义数据，在device_config表中也是保存模板id
				configEntity.setDevice_ext_tpl_flg(0);//1是模板，0不是模板
				
				//保存该设备的config数据,先删除该设备原有的config数据
				Map<String, Object> extMap = JsonUtils.fromJson(extSubmit, Map.class);
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
						if(!StringUtils.hasText(registerParam.getLibrary_idx())){
							throw new RuntimeException("library_idx 数据为空");
						}
						DeviceExtConfigEntity extConfigEntity = new DeviceExtConfigEntity(); 
						extConfigEntity.setLibrary_idx(Integer.valueOf(registerParam.getLibrary_idx()));
						extConfigEntity.setDevice_tpl_type(1);
						extConfigEntity.setDevice_ext_id(deviceIdx);
						extConfigEntity.setExt_comm_conf(comm);
						extConfigEntity.setExt_extend_conf(extend);
						extConfigEntity.setLogic_obj_idx(Integer.valueOf(obj_idx));
						extConfigEntity.setExt_model_idx(Integer.valueOf(model_idx));
						//插入device_ext_config表
						deviceExtConfDao.insertDeviceExtConfig(extConfigEntity);
					}
					
				}
				
			}else{
				configEntity.setDevice_ext_tpl_idx(Integer.valueOf(registerParam.getExtTempId()));//自定义数据
				configEntity.setDevice_ext_tpl_flg(1);//1是模板，0不是模板
			}
			if(StringUtils.hasText(runSubmit)){
				//如果不为空则为使用自定义数据
				configEntity.setDevice_run_tpl_idx(Integer.valueOf(registerParam.getRunTempId()));
				configEntity.setDevice_run_tpl_flg(0);//1是模板，0不是模板
				
				//保存该设备的config数据,先删除该设备原有的config数据
				Map<String, Object> runMap = JsonUtils.fromJson(runSubmit, Map.class);
				if (runMap.get("runDetailList") instanceof List) {
					List<Map<String, Object>> detailList = (List<Map<String, Object>>) runMap.get("runDetailList");
					for (Map<String, Object> map : detailList) {
						String conftype = map.get("run_conf_type")==null?"":map.get("run_conf_type").toString();
						String confValue = map.get("run_conf_value")==null?"":JsonUtils.toJson(map.get("run_conf_value")).toString();
						if (!StringUtils.hasText(conftype)) {
							throw new RuntimeException("run_conf_type 数据为空");
						}
						if (!StringUtils.hasText(confValue)) {
							throw new RuntimeException("run_conf_value 数据为空");
						}
						if(!StringUtils.hasText(registerParam.getLibrary_idx())){
							throw new RuntimeException("library_idx 数据为空");
						}
						
						MetaRunConfigEntity mConfigEntity = new MetaRunConfigEntity();
						mConfigEntity.setRun_conf_type(conftype);
						mConfigEntity = metaRunConfigDao.selMetaRunConfigEntityByConfType(mConfigEntity);
						if(mConfigEntity==null||mConfigEntity.getMeta_run_cfg_idx()==null){
							throw new RuntimeException("查询不到于run_conf_value对应数据为空");
						}
						
						DeviceRunConfigEntity runConfigEntity = new DeviceRunConfigEntity();
						runConfigEntity.setDevice_tpl_type(1);
						runConfigEntity.setDevice_run_id(deviceIdx);
						runConfigEntity.setLibrary_idx(Integer.valueOf(registerParam.getLibrary_idx()));
						runConfigEntity.setRun_config_idx(mConfigEntity.getMeta_run_cfg_idx());
						runConfigEntity.setRun_conf_value(confValue);
						
						//保存到device_run_config表中
						deviceRunConfDao.insertDeviceRunConfig(runConfigEntity);
					}
				}
				
			}else {
				//运行配置模板
				configEntity.setDevice_run_tpl_idx(Integer.valueOf(registerParam.getRunTempId()));
				configEntity.setDevice_run_tpl_flg(1);//1是模板，0不是模板
			}
			
			//设备监控模板
			configEntity.setDevice_monitor_tpl_idx(Integer.valueOf(registerParam.getMonitorTempId()));
			configEntity.setDevice_monitor_tpl_flg(1);//1是模板，0不是模板
			//数据同步模板
			configEntity.setDevice_dbsync_tpl_idx(Integer.valueOf(registerParam.getDbSyncTempId()));
			configEntity.setDevice_dbsync_tpl_flg(1);//1是模板，0不是模板
			//设置设备idx，图书馆idx
			configEntity.setDevice_idx(deviceIdx);
			configEntity.setLibrary_idx(Integer.valueOf(registerParam.getLibrary_idx()));
			deviceConfigDao.insertDeviceConfig(configEntity);
			
			//新增的设备用户与operator_group建立联系，operator_group的idx 为1 、对rel_operator_group表进行操作
			RelOperatorGroupEntity groupEntity = new RelOperatorGroupEntity();
			groupEntity.setLibrary_idx(0);//图书馆idx设为0
			groupEntity.setOperator_idx(Integer.valueOf(registerParam.getOperator_idx()));
			groupEntity.setOperator_group_idx(1);//operator_group的idx 为1
			relOperatorGroupDao.insert(groupEntity);
			
			resultEntity.setValue(true, "success","",registerParam);
			
			
			//下发配置数据到设备端
			pushMessage(configEntity,configEntity.getLibrary_idx(),configEntity.getDevice_idx());
			//下发主题
			jsonDataService.pushThemeToDevice(deviceEntity.getDevice_id(), deviceEntity.getLibrary_idx());
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
			throw new RuntimeException(e);
		}
		return resultEntity;
	}
	
	public void pushMessage(DeviceConfigEntity configEntity,int library_idx,int device_idx){
		
		List<ReturnResultEntity> resultEntities = downloadCfgSync(configEntity);
		List<ReturnResultEntity> data = new ArrayList<>();
		for(ReturnResultEntity returnResultEntity : resultEntities){
			data.clear();
			CloudSyncRequest<ReturnResultEntity> cloudSyncRequest = new CloudSyncRequest<>();
			String deviceId = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+device_idx);
			String libId = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+library_idx);
			String clientId = JedisUtils.getInstance().get(RedisConstant.CLIENTID+libId+":"+deviceId);
			cloudSyncRequest.setLib_id(libId);
			cloudSyncRequest.setDevice_id(deviceId);
			cloudSyncRequest.setClientId(clientId);
			data.add(returnResultEntity);
			cloudSyncRequest.setData(data);
			postUrl("downloadConfig",JsonUtils.toJson(cloudSyncRequest));
		}
	}
	
public ResultEntity downLoadFileURLToDevice(FileManagerEntity entity){
    	
	  ResultEntity resultEntity = new ResultEntity();
	    if(StringUtils.isEmpty(entity.getLibrary_id()) || StringUtils.isEmpty(entity.getDevice_id())){
	    	resultEntity.setMessage("lib_id or device_id or operation is null");
	    	return resultEntity;
	    }
	    CloudSyncRequest<FileUploadState> request = new CloudSyncRequest<FileUploadState>();
	    FileUploadState fileUploadState = new FileUploadState();
		String clientId = request.getCacheClient(entity.getLibrary_id(), entity.getDevice_id());//获取客户端的clicnetId
		request.setClientId(clientId);
		request.setOperation(FileManagerEntity.ACSPROTOCOLS);
		request.setTarget("device");
		request.setServicetype("ssitcloud");
		fileUploadState.setTableName(FileManagerEntity.ACSPROTOCOLS);
		fileUploadState.setFileName(entity.getFile_name());
		fileUploadState.setState("2");
		fileUploadState.setFilePath(entity.getFile_path());
		List<FileUploadState> list = new ArrayList<>();
		list.add(fileUploadState);
		request.setData(list);
		request.setDevice_id(entity.getDevice_id());
		request.setLib_id(entity.getLibrary_id());
		return postUrl("send",JsonUtils.toJson(request));
    }
	
	
	/**
	 * 下发设备模板配置到设备端<br>
	 * 目前硬件模板和数据同步配置需要下发<br>
	 * 运行模板旧的中间件还在用
	 * @param configEntity
	 * @return
	 */
	private List<ReturnResultEntity> downloadCfgSync(DeviceConfigEntity configEntity){
		
		List<ReturnResultEntity> resultList = new ArrayList<>();
		try{
			//硬件配置
			List<DeviceExtConfigRemoteEntity> extConfigRemoteEntities = extConfService.getDeviceExtConfig(configEntity);
			ReturnResultEntity extResultEntity = MBeanUtil.makeReturnResultEntityFromList(extConfigRemoteEntities,"device_ext_config");
			fillEntityToList(resultList,extResultEntity);
			//数据库同步配置
			List<DeviceDBSyncConfigRemoteEntity> syncConfigRemoteEntities = deviceDBSyncService.getDeviceDBSyncConfig(configEntity);
			ReturnResultEntity syncResultEntity = MBeanUtil.makeReturnResultEntityFromList(syncConfigRemoteEntities,"device_dbsync_config");
			fillEntityToList(resultList,syncResultEntity);
			//运行模板下发
			List<DeviceRunConfigRemoteEntity> runConfigs = deviceRunService.getDeviceRunConfig(configEntity);
			ReturnResultEntity runResultEntity = MBeanUtil.makeReturnResultEntityFromList(runConfigs,"device_run_config");
			fillEntityToList(resultList,runResultEntity);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultList;
	}
	
	
	private void fillEntityToList(List<ReturnResultEntity> list,ReturnResultEntity object){
		if(object != null){
			list.add(object);
		}
		
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity hydeviceRegister(DeviceRegisterParam registerParam) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			//设备数据拼装，保存设备信息，获取新增成功之后的idx
			DeviceEntity deviceEntity = new DeviceEntity();
			deviceEntity = setDevice(registerParam);
			int ret = deviceDao.insert(deviceEntity);
			if (deviceEntity==null || deviceEntity.getDevice_idx()==null) {
				//设备新增失败
				throw new RuntimeException("设备新增失败");
			}
			JedisUtils.getInstance().set(RedisConstant.DEVICE+deviceEntity.getDevice_id(), JsonUtils.toJson(deviceEntity));
			//将设备id添加到集合中，key为lib_idx
			JedisUtils.getInstance().sadd(RedisConstant.LIBRARY_DEVICEID+deviceEntity.getLibrary_idx()+""
			,deviceEntity.getDevice_id());
			//将设备idx与设备id添加到缓存中
			JedisUtils.getInstance().set(RedisConstant.DEVICE_KEYS+deviceEntity.getDevice_idx()
			,deviceEntity.getDevice_id());
			//获取设备idx
			Integer deviceIdx = deviceEntity.getDevice_idx();
			//保存设备acs信息
			/*if(registerParam.getAcsList()!=null){
				for (Map<String, Object> acsmap : registerParam.getAcsList()) {
					DeviceAcsLoginInfoEntity infoEntity = new DeviceAcsLoginInfoEntity();
					Integer protocol_tpl_idx = Integer.valueOf(acsmap.get("protocol_tpl_idx").toString());
					Integer library_idx = Integer.valueOf(acsmap.get("library_idx").toString());
					String login_ip = acsmap.get("login_ip").toString();
					Integer login_port = Integer.valueOf(acsmap.get("login_port").toString());
					String login_username = acsmap.get("login_username").toString();
					String login_pwd = acsmap.get("login_pwd").toString();
					String acs_service_name = acsmap.get("acs_service_name").toString();
					
					//ACS配置增加的登录信息
					String login_check = acsmap.get("login_check").toString();
					String login_code = acsmap.get("login_code").toString();
					String login_count = acsmap.get("login_count").toString();
					String login_type = acsmap.get("login_type").toString();
					
					infoEntity.setAcs_service_name(acs_service_name);
					infoEntity.setDevice_idx(deviceIdx);
					infoEntity.setLibrary_idx(library_idx);
					infoEntity.setLogin_ip(login_ip);
					infoEntity.setLogin_port(login_port);
					infoEntity.setLogin_pwd(login_pwd);
					infoEntity.setLogin_username(login_username);
					infoEntity.setLogin_check(login_check);
					infoEntity.setLogin_code(login_code);
					infoEntity.setLogin_count(login_count);
					infoEntity.setLogin_type(login_type);
					infoEntity.setProtocol_tpl_idx(protocol_tpl_idx);
					
					deviceAcsLoginInfoDao.insertAcsInfo(infoEntity);
				}
				Map<String,Object> insertMap = new HashMap<String,Object>();
				// 转换提日期输出格式
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String modifyTime = dateFormat.format(new java.sql.Date(System.currentTimeMillis())).toString();
				insertMap.put("table_name", "acs_protocols");
				insertMap.put("lib_id", registerParam.getLibId());
				insertMap.put("lib_idx", registerParam.getLibrary_idx());
				insertMap.put("device_id", registerParam.getDeviceId());
				insertMap.put("issync", "0");
				insertMap.put("sync_type","xml");
				insertMap.put("last_modify_time",modifyTime);
				
				deviceDao.saveFileUploadFlag(insertMap);
			}*/
			if (registerParam.getRegion()!=null) {
				//保存地理位置信息
				Map<String, Object> deviceExtendInfoMap = new HashMap<>();
				deviceExtendInfoMap.put("regi_code", registerParam.getRegion());
				deviceExtendInfoMap.put("device_idx", deviceIdx);
				Map<String, Object> map = deviceDao.queryRegionByCode(deviceExtendInfoMap);
				if(map!=null){
					deviceExtendInfoMap.put("region_idx", map.get("regi_idx"));
					//查询有没有
					Map<String, Object> extendInfo = deviceDao.queryDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					
					if(extendInfo==null){
						deviceDao.insertDeviceExtendInfo(deviceExtendInfoMap);
					}else{
						deviceDao.updateDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					}
				}
			}
			
			
			//保存设备的配置信息
			DeviceConfigEntity configEntity = new DeviceConfigEntity();
			//如果是使用模板
			//硬件与逻辑配置
			/*String extSubmit = registerParam.getExtTempSubmit();
			String runSubmit = registerParam.getRunTempSubmit();
			if(StringUtils.hasText(extSubmit)){
				//如果不为空则为使用自定义数据
				configEntity.setDevice_ext_tpl_idx(Integer.valueOf(registerParam.getExtTempId()));//自定义数据，在device_config表中也是保存模板id
				configEntity.setDevice_ext_tpl_flg(0);//1是模板，0不是模板
				
				//保存该设备的config数据,先删除该设备原有的config数据
				Map<String, Object> extMap = JsonUtils.fromJson(extSubmit, Map.class);
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
						if(!StringUtils.hasText(registerParam.getLibrary_idx())){
							throw new RuntimeException("library_idx 数据为空");
						}
						DeviceExtConfigEntity extConfigEntity = new DeviceExtConfigEntity(); 
						extConfigEntity.setLibrary_idx(Integer.valueOf(registerParam.getLibrary_idx()));
						extConfigEntity.setDevice_tpl_type(1);
						extConfigEntity.setDevice_ext_id(deviceIdx);
						extConfigEntity.setExt_comm_conf(comm);
						extConfigEntity.setExt_extend_conf(extend);
						extConfigEntity.setLogic_obj_idx(Integer.valueOf(obj_idx));
						extConfigEntity.setExt_model_idx(Integer.valueOf(model_idx));
						//插入device_ext_config表
						deviceExtConfDao.insertDeviceExtConfig(extConfigEntity);
					}
					
				}
				
			}else{
				configEntity.setDevice_ext_tpl_idx(Integer.valueOf(registerParam.getExtTempId()));//自定义数据
				configEntity.setDevice_ext_tpl_flg(1);//1是模板，0不是模板
			}
			if(StringUtils.hasText(runSubmit)){
				//如果不为空则为使用自定义数据
				configEntity.setDevice_run_tpl_idx(Integer.valueOf(registerParam.getRunTempId()));
				configEntity.setDevice_run_tpl_flg(0);//1是模板，0不是模板
				
				//保存该设备的config数据,先删除该设备原有的config数据
				Map<String, Object> runMap = JsonUtils.fromJson(runSubmit, Map.class);
				if (runMap.get("runDetailList") instanceof List) {
					List<Map<String, Object>> detailList = (List<Map<String, Object>>) runMap.get("runDetailList");
					for (Map<String, Object> map : detailList) {
						String conftype = map.get("run_conf_type")==null?"":map.get("run_conf_type").toString();
						String confValue = map.get("run_conf_value")==null?"":JsonUtils.toJson(map.get("run_conf_value")).toString();
						if (!StringUtils.hasText(conftype)) {
							throw new RuntimeException("run_conf_type 数据为空");
						}
						if (!StringUtils.hasText(confValue)) {
							throw new RuntimeException("run_conf_value 数据为空");
						}
						if(!StringUtils.hasText(registerParam.getLibrary_idx())){
							throw new RuntimeException("library_idx 数据为空");
						}
						
						MetaRunConfigEntity mConfigEntity = new MetaRunConfigEntity();
						mConfigEntity.setRun_conf_type(conftype);
						mConfigEntity = metaRunConfigDao.selMetaRunConfigEntityByConfType(mConfigEntity);
						if(mConfigEntity==null||mConfigEntity.getMeta_run_cfg_idx()==null){
							throw new RuntimeException("查询不到于run_conf_value对应数据为空");
						}
						
						DeviceRunConfigEntity runConfigEntity = new DeviceRunConfigEntity();
						runConfigEntity.setDevice_tpl_type(1);
						runConfigEntity.setDevice_run_id(deviceIdx);
						runConfigEntity.setLibrary_idx(Integer.valueOf(registerParam.getLibrary_idx()));
						runConfigEntity.setRun_config_idx(mConfigEntity.getMeta_run_cfg_idx());
						runConfigEntity.setRun_conf_value(confValue);
						
						//保存到device_run_config表中
						deviceRunConfDao.insertDeviceRunConfig(runConfigEntity);
					}
				}
				
			}else {
				//运行配置模板
				configEntity.setDevice_run_tpl_idx(Integer.valueOf(registerParam.getRunTempId()));
				configEntity.setDevice_run_tpl_flg(1);//1是模板，0不是模板
			}*/
			configEntity.setDevice_ext_tpl_idx(Integer.valueOf("0"));//自定义数据
			configEntity.setDevice_ext_tpl_flg(1);//1是模板，0不是模板
			configEntity.setDevice_run_tpl_idx(Integer.valueOf("0"));
			configEntity.setDevice_run_tpl_flg(1);//1是模板，0不是模板
			//设备监控模板
			configEntity.setDevice_monitor_tpl_idx(Integer.valueOf(registerParam.getMonitorTempId()));
			configEntity.setDevice_monitor_tpl_flg(1);//1是模板，0不是模板
			//数据同步模板
			configEntity.setDevice_dbsync_tpl_idx(Integer.valueOf(registerParam.getDbSyncTempId()));
			configEntity.setDevice_dbsync_tpl_flg(1);//1是模板，0不是模板
			//设置设备idx，图书馆idx
			configEntity.setDevice_idx(deviceIdx);
			configEntity.setLibrary_idx(Integer.valueOf(registerParam.getLibrary_idx()));
			deviceConfigDao.insertDeviceConfig(configEntity);
			
			pushMessage(configEntity,configEntity.getLibrary_idx(),configEntity.getDevice_idx());
			jsonDataService.pushThemeToDevice(deviceEntity.getDevice_id(), deviceEntity.getLibrary_idx());
			//新增的设备用户与operator_group建立联系，operator_group的idx 为1 、对rel_operator_group表进行操作
			RelOperatorGroupEntity groupEntity = new RelOperatorGroupEntity();
			groupEntity.setLibrary_idx(0);//图书馆idx设为0
			groupEntity.setOperator_idx(Integer.valueOf(registerParam.getOperator_idx()));
			groupEntity.setOperator_group_idx(1);//operator_group的idx 为1
			relOperatorGroupDao.insert(groupEntity);
			
			resultEntity.setValue(true, "success","",registerParam);

			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
			throw new RuntimeException(e);
		}
		return resultEntity;
	}
	
	/**
	 * 将传递的参数组装成为device实体
	 *
	 * <p>2016年5月5日 上午10:16:19 
	 * <p>create by hjc
	 * @param deviceEntity
	 * @param registerParam
	 * @return
	 */
	private DeviceEntity setDevice(DeviceRegisterParam registerParam){
		DeviceEntity deviceEntity = new DeviceEntity();
		//设备特征码
		deviceEntity.setDevice_code(registerParam.getDeviceCode());
		//设备id
		deviceEntity.setDevice_id(registerParam.getDeviceId());
		//设备名称
		deviceEntity.setDevice_name(registerParam.getDeviceName());
		//设备描述
		deviceEntity.setDevice_desc(registerParam.getDeviceDesc());
		//设备类型的idx
		deviceEntity.setDevice_model_idx(Integer.valueOf(registerParam.getDeviceType()));
		//acs登录名
		deviceEntity.setLibrary_login_name(registerParam.getAcsName());
		//acs密码
		deviceEntity.setLibrary_login_pwd(registerParam.getAcsPwd());
		//物流编号
		deviceEntity.setLogistics_number(registerParam.getLogisticsNumber());
		//流通地点
		deviceEntity.setCircule_location(registerParam.getCirculeLocation());
		//图书馆idx
		deviceEntity.setLibrary_idx(Integer.valueOf(registerParam.getLibrary_idx()));
		deviceEntity.setPid(registerParam.getpId());
		return deviceEntity;
	}

	@Override
	public ResultEntity queryDeviceByParam(String req) {
		//req={}
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			DevicePageEntity devicePageEntity=new DevicePageEntity();
			JsonNode node=JsonUtils.readTree(req);
			boolean devGroupLimit=false;
			Integer operator_idx=null;
			if(node!=null){
				//某状态的设备ID
				JsonNode stateNodeString=node.get("deviceIdByStateList");
				JsonNode pageNode=node.get("page");
				JsonNode pageSizeNode=node.get("pageSize");
				JsonNode keyWordNode=node.get("keyWord");
				JsonNode machineTypeNode=node.get("machineType");
				JsonNode libraryIdxNode=node.get("library_idx");
				JsonNode libraryIdxArrNode=node.get("lib_idx_list");
				JsonNode operatorIdxNode=node.get("operator_idx_Limit");
				
				if(operatorIdxNode!=null){
					devGroupLimit=true;
					operator_idx=operatorIdxNode.asInt();
				}
				if(pageNode!=null){
					devicePageEntity.setPage(pageNode.asInt(1));
				}
				if(pageSizeNode!=null){
					devicePageEntity.setPageSize(pageSizeNode.asInt(12));
				}
				if(libraryIdxNode!=null){
					devicePageEntity.setLibrary_idx(libraryIdxNode.asInt());
				}
				if(libraryIdxArrNode!=null){
					List<Integer> listIdxs=JsonUtils.fromNode(libraryIdxArrNode, new TypeReference<List<Integer>>() {});
					devicePageEntity.setLib_idx_list(listIdxs);
					//
				}
				if(stateNodeString!=null){
					//没有查询到某个状态，则
					String stateStr=stateNodeString.asText();
					
					if(StringUtils.isEmpty(stateStr)||stateStr.length()==2){
						//devicePageEntity.setDevice_id("NO_STATE!!");
					}else{
//						String[] deviceIds = stateStr.split(",");
//						List<String> dList = new ArrayList<>(deviceIds.length);
//						Collections.addAll(dList, deviceIds);
//						devicePageEntity.setDev_id_list(dList);
                        devicePageEntity.setDevice_id(stateStr);
					}
				}
				if(keyWordNode!=null){
					devicePageEntity.setDevice_name(keyWordNode.asText());
				}
				if(machineTypeNode!=null){
					devicePageEntity.setDevice_type(machineTypeNode.asText());
				}
			}
			
			devicePageEntity=deviceDao.selectPage(devicePageEntity,operator_idx,devGroupLimit);
			result.setResult(devicePageEntity);
			result.setState(true);
		}
		return result;
	}
	
	public ResultEntity queryServiceDeviceByParam(String req){
		//req={}
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			ServiceDevicePageEntity devicePageEntity=new ServiceDevicePageEntity();
			JsonNode node=JsonUtils.readTree(req);
			//boolean devGroupLimit=false;
			//Integer operator_idx=null;
			if(node!=null){
				//某状态的设备ID
				//JsonNode stateNodeString=node.get("deviceIdByStateList");
				JsonNode pageNode=node.get("page");
				JsonNode pageSizeNode=node.get("pageSize");
				//JsonNode keyWordNode=node.get("keyWord");
				//JsonNode machineTypeNode=node.get("machineType");
				JsonNode libraryIdxNode=node.get("library_idx");
				JsonNode libraryIdxArrNode=node.get("lib_idx_list");
				//JsonNode operatorIdxNode=node.get("operator_idx_Limit");
			
				if(pageNode!=null){
					devicePageEntity.setPage(pageNode.asInt(1));
				}
				if(pageSizeNode!=null){
					devicePageEntity.setPageSize(pageSizeNode.asInt(12));
				}
				if(libraryIdxNode!=null){
					devicePageEntity.setLibrary_idx(libraryIdxNode.asInt());
				}
				if(libraryIdxArrNode!=null){
					List<Integer> listIdxs=JsonUtils.fromNode(libraryIdxArrNode, new TypeReference<List<Integer>>() {});
					devicePageEntity.setLib_idx_list(listIdxs);
					//
				}
			}

			devicePageEntity=deviceDao.selectServiceDevicePage(devicePageEntity);
			result.setResult(devicePageEntity);
			result.setState(true);
		}
		return result;
	}

	@Override
	public int isExistDeviceWithIdOrIdx(DeviceEntity deviceEntity) {
		return deviceDao.isExistDeviceWithIdOrIdx(deviceEntity);
	}

	@Override
	public int selDeviceCountByIds(Map<String, Object> param) {
		return deviceDao.selDeviceCountByIds(param);
	}
	/**
	 *  需要修改到的表有
		device_config[变更模板（不是模板改数据或者数据该模板）]
		device[设备类型/图书馆ID/设备物流编号/设备流通地点/设备描述/设备名称/设备ID]
		device_group[设备分组]
	    {
		    "deviceCode":"qq",
		    "libId":"1",
		    "deviceId":"001_SSL001",
		    "deviceName":"朝阳24小时自助借还",
		    "deviceType":"1",
		    "deviceGroup":"-1",
		    "acsName":"rfid",
		    "acsPwd":"rfid",
		    "logisticsNumber":"ddd",
		    "extTempId":"2",
		    "runTempId":"1",
		    "monitorTempId":"9",
		    "dbSyncTempId":"1",
		    "deviceDesc":"24小时自助借还机",
		    "acsList" :"acs 信息数组"
	    }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity UpdDeviceMgmtPage(String req) {
		ResultEntity result=new ResultEntity();
		int upd=0;
		if(JSONUtils.mayBeJSON(req)){
			Map<String, Object> params=JsonUtils.fromJson(req, Map.class);
			if(MapUtils.isNotEmpty(params)){
				DeviceEntity device=new DeviceEntity();
				if(params.get("deviceIdx")==null){
					result.setValue(false, "", "设备IDX不能为空，请检查！", "");
					return result;
				}
				if(params.get("library_idx")==null){
					result.setValue(false, "", "图书馆IDX不能为空，请检查！", "");
					return result;
				}
				if(params.get("deviceId")==null){
					result.setValue(false, "", "设备ID不能为空，请检查！", "");
					return result;
				}
				if(params.get("deviceName")==null){
					result.setValue(false, "", "设备名称不能为空，请检查！", "");
					return result;
				}
				if(params.get("deviceType")==null){
					result.setValue(false, "", "设备类型不能为空，请检查！", "");
					return result;
				}
				if(params.get("acsList")==null){
					result.setValue(false, "", "ACS配置模板不能为空，请检查！", "");
					return result;
				}
				if(params.get("region")==null){
					result.setValue(false, "", "设备地点信息不能为空，请检查！", "");
					return result;
				}
				/*if(params.get("libId")!=null){
					//这个需要获取library_idx
				}*/
				int deviceIdx=Integer.parseInt(params.get("deviceIdx").toString());
				device.setDevice_idx(deviceIdx);
				device.setLibrary_idx(Integer.parseInt(params.get("library_idx").toString()));
				device.setDevice_id(params.get("deviceId").toString());
				device.setDevice_name(params.get("deviceName").toString());
				device.setDevice_model_idx(Integer.parseInt(params.get("deviceType").toString()));
				//设备分组可以为空,如果为空则 插入数据，不为空则更新数据
				/*
				 * if (registerParam.getRegion()!=null) {
				//保存地理位置信息
				Map<String, Object> deviceExtendInfoMap = new HashMap<>();
				deviceExtendInfoMap.put("regi_code", registerParam.getRegion());
				deviceExtendInfoMap.put("device_idx", deviceIdx);
				Map<String, Object> map = deviceDao.queryRegionByCode(deviceExtendInfoMap);
				if(map!=null){
					deviceExtendInfoMap.put("region_idx", map.get("regi_idx"));
					//查询有没有
					Map<String, Object> extendInfo = deviceDao.queryDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					
					if(extendInfo==null){
						deviceDao.insertDeviceExtendInfo(deviceExtendInfoMap);
					}else{
						deviceDao.updateDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					}
				}
			}
				 */
				
				//保存地理位置信息
				Map<String, Object> deviceExtendInfoMap = new HashMap<>();
				deviceExtendInfoMap.put("regi_code", params.get("region")+"");
				deviceExtendInfoMap.put("device_idx", deviceIdx);
				Map<String, Object> rMap = deviceDao.queryRegionByCode(deviceExtendInfoMap);
				if(rMap!=null){
					deviceExtendInfoMap.put("region_idx", rMap.get("regi_idx"));
					//查询有没有
					Map<String, Object> extendInfo = deviceDao.queryDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					
					if(extendInfo==null){
						deviceDao.insertDeviceExtendInfo(deviceExtendInfoMap);
					}else{
						deviceDao.updateDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					}
				}
				
				if(params.get("deviceGroup")!=null){
					DeviceGroupEntity deviceGroupEntity=new DeviceGroupEntity();
					deviceGroupEntity.setDevice_group_idx(Integer.parseInt(params.get("deviceGroup").toString()));
					DeviceGroupEntity deviceGroup=deviceGroupDao.selectByDeviceGroupIdx(deviceGroupEntity);
					if(deviceGroup!=null){//表示选择了可用的分组,==null则表示没有选择设备分组
						RelDeviceGroupEntity queryRelDeviceGroup=new RelDeviceGroupEntity();
						queryRelDeviceGroup.setDevice_idx(device.getDevice_idx());
						queryRelDeviceGroup.setLibrary_idx(device.getLibrary_idx());
						List<RelDeviceGroupEntity> queryRelDeviceGroups=relDeviceGroupDao.selectByid(queryRelDeviceGroup);
						if(CollectionUtils.isNotEmpty(queryRelDeviceGroups)){
							RelDeviceGroupEntity needChangeEntity=queryRelDeviceGroups.get(0);
							needChangeEntity.setDevice_group_idx(Integer.parseInt(params.get("deviceGroup").toString()));
							int updNum=relDeviceGroupDao.update(needChangeEntity);
							if(updNum<=0){
								throw new RuntimeException("DEVICE_IDX:["+device.getDevice_idx()+"]更新rel_device_group失败");
							}
						}else{
							// 插入rel_device_group 表
							RelDeviceGroupEntity relDeviceGroup=new RelDeviceGroupEntity();
							relDeviceGroup.setDevice_group_idx(Integer.parseInt(params.get("deviceGroup").toString()));
							relDeviceGroup.setLibrary_idx(device.getLibrary_idx());
							relDeviceGroup.setDevice_idx(device.getDevice_idx());
							int insertNum=relDeviceGroupDao.insert(relDeviceGroup);
							if(insertNum<=0){
								throw new RuntimeException("DEVICE_IDX:["+device.getDevice_idx()+"] 更新设备信息失败：设备分组失败");
							}
						}
					}
				
				}
				/*
				 * 废除 使用 acsList
				 * if(params.get("acsName")!=null){//library_login_name
					device.setLibrary_login_name(params.get("acsName").toString());
				}
				if(params.get("acsPwd")!=null){//library_login_pwd
					device.setLibrary_login_pwd(params.get("acsPwd").toString());
				}
				*/
				boolean remove=false;
				boolean insert=false;
				List<Map<String, Object>> acsList=(List<Map<String, Object>>)params.get("acsList");
				List<DeviceAcsLoginInfoEntity> insertDeviceAcsLoginInfos=new ArrayList<>();
				if(CollectionUtils.isNotEmpty(acsList)){
					for (Map<String, Object> acsmap : acsList) {
						DeviceAcsLoginInfoEntity infoEntity = new DeviceAcsLoginInfoEntity();
						Integer protocol_tpl_idx = Integer.valueOf(acsmap.get("protocol_tpl_idx").toString());
						Integer library_idx = Integer.valueOf(acsmap.get("library_idx").toString());
						String login_ip = acsmap.get("login_ip").toString();
						Integer login_port = Integer.valueOf(acsmap.get("login_port").toString());
						String login_username = acsmap.get("login_username").toString();
						String login_pwd = acsmap.get("login_pwd").toString();
						String acs_service_name = acsmap.get("acs_service_name").toString();
						//ACS配置增加的登录信息
						String login_check = acsmap.get("login_check").toString();
						String login_code = acsmap.get("login_code").toString();
						String login_count = acsmap.get("login_count").toString();
						String login_type = acsmap.get("login_type").toString();
						infoEntity.setAcs_service_name(acs_service_name);
						infoEntity.setDevice_idx(deviceIdx);
						infoEntity.setLibrary_idx(library_idx);
						infoEntity.setLogin_ip(login_ip);
						infoEntity.setLogin_port(login_port);
						infoEntity.setLogin_pwd(login_pwd);
						infoEntity.setLogin_username(login_username);
						infoEntity.setLogin_check(login_check);
						infoEntity.setLogin_code(login_code);
						infoEntity.setLogin_count(login_count);
						infoEntity.setLogin_type(login_type);
						infoEntity.setProtocol_tpl_idx(protocol_tpl_idx);
						insertDeviceAcsLoginInfos.add(infoEntity);
					}
					//在同步dev_dbsync_config表中添加一条改图书馆和设备的修改信息
				}
				
				List<DeviceAcsLoginInfoEntity> deviceAcsLoginInfos=deviceAcsLoginInfoDao.queryAcsInfoByDeviceIdx(deviceIdx);
				if(CollectionUtils.isEmpty(deviceAcsLoginInfos)&&CollectionUtils.isNotEmpty(acsList)){
					//第一种情况原来不存在数据， 新增的
					insert=true;
				}
				if(CollectionUtils.isNotEmpty(acsList)&&CollectionUtils.isNotEmpty(deviceAcsLoginInfos)){
					if(acsList.size()!=deviceAcsLoginInfos.size()){
						remove=true;
						insert=true;
					}else{
						//如果数目相等 则需要一个个的判断，比如 删了一个 再增加一个 或者修改一个 
						for(DeviceAcsLoginInfoEntity deviceAcsLoginInfo:deviceAcsLoginInfos){
							if(!insertDeviceAcsLoginInfos.contains(deviceAcsLoginInfo)){
								remove=true;
								insert=true;
							}
						}
					}
				}
				//如果过原来存在的话需要先清空在增加
				if(remove){
					int deleteNum=deviceAcsLoginInfoDao.deleteAcsInfoByDeviceIdx(deviceIdx);
					if(deleteNum!=deviceAcsLoginInfos.size()){
						throw new RuntimeException("删除ACS配置信息失败！DEVICE_IDX："+deviceIdx);
					}
				}
				if(insert){
					int insertNum=0;
					insertNum=deviceAcsLoginInfoDao.insertAcsInfoBatch(insertDeviceAcsLoginInfos);
					if(insertNum!=acsList.size()){
						throw new RuntimeException("新增ACS配置信息失败！");
					}
				}
				if(remove||insert){
					Map<String,Object> map = new HashMap<String,Object>();
					Map<String,Object> insertMap = new HashMap<String,Object>();
					map.put("lib_idx", params.get("library_idx"));
					map.put("device_id", params.get("deviceId"));
					map.put("lib_id", params.get("libId"));
					map.put("table_name", "acs_protocols");
					
					int count = deviceDao.queryFileUploadFlag(map);
					
					// 转换提日期输出格式
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String modifyTime = dateFormat.format(new java.sql.Date(System.currentTimeMillis())).toString();
					insertMap.put("table_name", "acs_protocols");
					insertMap.put("lib_id", params.get("libId"));
					insertMap.put("lib_idx", params.get("library_idx"));
					insertMap.put("device_id", params.get("deviceId"));
					insertMap.put("issync", "0");
					insertMap.put("sync_type","xml");
					insertMap.put("last_modify_time",modifyTime);
					
					if(count==0){
						deviceDao.saveFileUploadFlag(insertMap);
					}else{
						deviceDao.updateFileUploadFlag(insertMap);
					}
					
					//下发acs配置数据到设备端
					List<DeviceAcsModuleEntity> acsModuleEntities = 
							acsLoginInfoService.loadAcsXml(params.get("library_idx").toString(),params.get("deviceId").toString());
					if(acsModuleEntities != null && !acsModuleEntities.isEmpty()){
						ResultEntity entity = postUrl("uploadAcsConfig", JsonUtils.toJson(acsModuleEntities));
						if(entity.getState()){
							FileManagerEntity fileManagerEntity = JsonUtils.fromJson(JsonUtils.toJson(entity.getResult()), FileManagerEntity.class);
							downLoadFileURLToDevice(fileManagerEntity);
						}
					}
					
					
				}
				if(params.get("logisticsNumber")!=null){
					device.setLogistics_number(params.get("logisticsNumber").toString());
				}
				if(params.get("circuleLocation")!=null){
					device.setCircule_location(params.get("circuleLocation").toString());
				}
				if(params.get("deviceDesc")!=null){
					device.setDevice_desc(params.get("deviceDesc").toString());
				}
				if(params.get("version_stamp")!=null){
					device.setVersion_stamp(Integer.parseInt(params.get("version_stamp").toString()));
				}
				upd=deviceDao.update(device);
				if(upd!=1){
					//throw new RuntimeException("更新设备信息失败：设备ID-->"+device.getDevice_id());
					result.setState(false);
					result.setRetMessage("optimistic");
					return result;
				}
				//修改缓存
				
				JedisUtils.getInstance().set(RedisConstant.DEVICE+device.getDevice_id(), JsonUtils.toJson(device));
				/**
				 * 是否更改了模板数据，如果更改了模板才进行更新操作，否则不进行更新操作
				 * 模板IDX 更新选择的模板 ,
				 * 有可能选的是自定义数据  则 值为-1,需要修改device-config 对应的flg==0 即表示使用数据
				 */
				DeviceConfigEntity deviceConfig=new DeviceConfigEntity();
				deviceConfig.setDevice_idx(device.getDevice_idx());
				deviceConfig.setLibrary_idx(device.getLibrary_idx());
				List<DeviceConfigEntity> deviceConfigs=deviceConfigDao.selectList(deviceConfig);//根据device_idx 查询
				DeviceConfigEntity queryDeviceConfig = null;
				if(CollectionUtils.isNotEmpty(deviceConfigs)){
					queryDeviceConfig=deviceConfigs.get(0);
				}
				if(params.get("extTempId")!=null){
					if(params.get("extTempId").equals("-1")){
						//选择自定义  并且 原来是模板的情况下 需要更新这个字段 	//原来是自定义则不用更新
						if(queryDeviceConfig.getDevice_ext_tpl_flg()==DeviceConfigEntity.IS_MODEL){
							deviceConfig.setDevice_ext_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
						}
					}else{
						//如果不是-1则表示选择的是其他模板，直接更新模板名 和模板flg
						if(queryDeviceConfig.getDevice_ext_tpl_flg()==DeviceConfigEntity.IS_MODEL&&queryDeviceConfig.getDevice_ext_tpl_idx()==Integer.parseInt(params.get("extTempId").toString())){
							//如果没有发生变化的情况 则不更新这个字段
						}else{
							deviceConfig.setDevice_ext_tpl_flg(DeviceConfigEntity.IS_MODEL);
							deviceConfig.setDevice_ext_tpl_idx(Integer.parseInt(params.get("extTempId").toString()));
						}
					}
				}
				if(params.get("runTempId")!=null){
					if(params.get("runTempId").equals("-1")){
						if(queryDeviceConfig.getDevice_run_tpl_flg()==DeviceConfigEntity.IS_MODEL){
							deviceConfig.setDevice_run_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
						}
					}else{
						//模板ID发生变化的时候更新，不发生变化的时候不更新
						if(queryDeviceConfig.getDevice_run_tpl_flg()==DeviceConfigEntity.IS_MODEL&&queryDeviceConfig.getDevice_run_tpl_idx()==Integer.parseInt(params.get("runTempId").toString())){
						}else{
							deviceConfig.setDevice_run_tpl_flg(DeviceConfigEntity.IS_MODEL);
							deviceConfig.setDevice_run_tpl_idx(Integer.parseInt(params.get("runTempId").toString()));
						}
					}
				}
				if(params.get("monitorTempId")!=null){
					if(params.get("monitorTempId").equals("-1")){
						if(queryDeviceConfig.getDevice_monitor_tpl_flg()==DeviceConfigEntity.IS_MODEL){
							deviceConfig.setDevice_monitor_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
						}
					}else{
						if(queryDeviceConfig.getDevice_monitor_tpl_flg()==DeviceConfigEntity.IS_MODEL
								&&queryDeviceConfig.getDevice_monitor_tpl_idx()==Integer.parseInt(params.get("monitorTempId").toString())){
						}else{
							deviceConfig.setDevice_monitor_tpl_flg(DeviceConfigEntity.IS_MODEL);
							deviceConfig.setDevice_monitor_tpl_idx(Integer.parseInt(params.get("monitorTempId").toString()));
						}
					}
				}
				if(params.get("dbSyncTempId")!=null){
					if(params.get("dbSyncTempId").equals("-1")){
						if(queryDeviceConfig.getDevice_dbsync_tpl_flg()==DeviceConfigEntity.IS_MODEL){
							deviceConfig.setDevice_dbsync_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
						}
					}else{
						if(queryDeviceConfig.getDevice_dbsync_tpl_flg()==DeviceConfigEntity.IS_MODEL
								&&queryDeviceConfig.getDevice_dbsync_tpl_idx()==Integer.parseInt(params.get("dbSyncTempId").toString())){
						}else{
							deviceConfig.setDevice_dbsync_tpl_flg(DeviceConfigEntity.IS_MODEL);
							deviceConfig.setDevice_dbsync_tpl_idx(Integer.parseInt(params.get("dbSyncTempId").toString()));
						}
					}
				}
				if(deviceConfig.hasUpdateField()){
					upd=deviceConfigDao.updateDeviceConfig(deviceConfig);
					if(upd!=1){
						throw new RuntimeException("更新device_config设备信息失败：设备ID-->"+device.getDevice_id());
					}
					
					//下发配置到设备端
					pushMessage(deviceConfig,deviceConfig.getLibrary_idx(),deviceConfig.getDevice_idx());
					
				}else{
					//相同 则不更新
					//LogUtils.info("DEVICE_IDX:"+deviceConfig.getDevice_idx()+" 没有修改模板，不更新device_config");
				}
				  
				
			}
			result.setState(true);
			//图书馆idx|设备idx|设备ID|设备名|
			result.setRetMessage("馆IDX:"+params.get("library_idx")+"|设备IDX:"+params.get("deviceIdx")+"|设备ID:"+params.get("deviceId")+"|设备名:"+params.get("deviceName")+"|");
		}
	
		return result;
	}
	/**
	 * 海洋设备更新
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity UpdHyDeviceMgmtPage(String req) {
		ResultEntity result=new ResultEntity();
		int upd=0;
		if(JSONUtils.mayBeJSON(req)){
			Map<String, Object> params=JsonUtils.fromJson(req, Map.class);
			if(MapUtils.isNotEmpty(params)){
				DeviceEntity device=new DeviceEntity();
				if(params.get("deviceIdx")==null){
					result.setValue(false, "", "设备IDX不能为空，请检查！", "");
					return result;
				}
				if(params.get("library_idx")==null){
					result.setValue(false, "", "图书馆IDX不能为空，请检查！", "");
					return result;
				}
				if(params.get("deviceId")==null){
					result.setValue(false, "", "设备ID不能为空，请检查！", "");
					return result;
				}
				if(params.get("deviceName")==null){
					result.setValue(false, "", "设备名称不能为空，请检查！", "");
					return result;
				}
				if(params.get("deviceType")==null){
					result.setValue(false, "", "设备类型不能为空，请检查！", "");
					return result;
				}
				if(params.get("region")==null){
					result.setValue(false, "", "设备地点信息不能为空，请检查！", "");
					return result;
				}
				/*if(params.get("libId")!=null){
					//这个需要获取library_idx
				}*/
				int deviceIdx=Integer.parseInt(params.get("deviceIdx").toString());
				device.setDevice_idx(deviceIdx);
				device.setLibrary_idx(Integer.parseInt(params.get("library_idx").toString()));
				device.setDevice_id(params.get("deviceId").toString());
				device.setDevice_name(params.get("deviceName").toString());
				device.setDevice_model_idx(Integer.parseInt(params.get("deviceType").toString()));
				//设备分组可以为空,如果为空则 插入数据，不为空则更新数据
				/*
				 * if (registerParam.getRegion()!=null) {
				//保存地理位置信息
				Map<String, Object> deviceExtendInfoMap = new HashMap<>();
				deviceExtendInfoMap.put("regi_code", registerParam.getRegion());
				deviceExtendInfoMap.put("device_idx", deviceIdx);
				Map<String, Object> map = deviceDao.queryRegionByCode(deviceExtendInfoMap);
				if(map!=null){
					deviceExtendInfoMap.put("region_idx", map.get("regi_idx"));
					//查询有没有
					Map<String, Object> extendInfo = deviceDao.queryDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					
					if(extendInfo==null){
						deviceDao.insertDeviceExtendInfo(deviceExtendInfoMap);
					}else{
						deviceDao.updateDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					}
				}
			}
				 */
				
				//保存地理位置信息
				Map<String, Object> deviceExtendInfoMap = new HashMap<>();
				deviceExtendInfoMap.put("regi_code", params.get("region")+"");
				deviceExtendInfoMap.put("device_idx", deviceIdx);
				Map<String, Object> rMap = deviceDao.queryRegionByCode(deviceExtendInfoMap);
				if(rMap!=null){
					deviceExtendInfoMap.put("region_idx", rMap.get("regi_idx"));
					//查询有没有
					Map<String, Object> extendInfo = deviceDao.queryDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					
					if(extendInfo==null){
						deviceDao.insertDeviceExtendInfo(deviceExtendInfoMap);
					}else{
						deviceDao.updateDeviceExtendInfoByDeviceIdx(deviceExtendInfoMap);
					}
				}
				
				if(params.get("deviceGroup")!=null){
					DeviceGroupEntity deviceGroupEntity=new DeviceGroupEntity();
					deviceGroupEntity.setDevice_group_idx(Integer.parseInt(params.get("deviceGroup").toString()));
					DeviceGroupEntity deviceGroup=deviceGroupDao.selectByDeviceGroupIdx(deviceGroupEntity);
					if(deviceGroup!=null){//表示选择了可用的分组,==null则表示没有选择设备分组
						RelDeviceGroupEntity queryRelDeviceGroup=new RelDeviceGroupEntity();
						queryRelDeviceGroup.setDevice_idx(device.getDevice_idx());
						queryRelDeviceGroup.setLibrary_idx(device.getLibrary_idx());
						List<RelDeviceGroupEntity> queryRelDeviceGroups=relDeviceGroupDao.selectByid(queryRelDeviceGroup);
						if(CollectionUtils.isNotEmpty(queryRelDeviceGroups)){
							RelDeviceGroupEntity needChangeEntity=queryRelDeviceGroups.get(0);
							needChangeEntity.setDevice_group_idx(Integer.parseInt(params.get("deviceGroup").toString()));
							int updNum=relDeviceGroupDao.update(needChangeEntity);
							if(updNum<=0){
								throw new RuntimeException("DEVICE_IDX:["+device.getDevice_idx()+"]更新rel_device_group失败");
							}
						}else{
							// 插入rel_device_group 表
							RelDeviceGroupEntity relDeviceGroup=new RelDeviceGroupEntity();
							relDeviceGroup.setDevice_group_idx(Integer.parseInt(params.get("deviceGroup").toString()));
							relDeviceGroup.setLibrary_idx(device.getLibrary_idx());
							relDeviceGroup.setDevice_idx(device.getDevice_idx());
							int insertNum=relDeviceGroupDao.insert(relDeviceGroup);
							if(insertNum<=0){
								throw new RuntimeException("DEVICE_IDX:["+device.getDevice_idx()+"] 更新设备信息失败：设备分组失败");
							}
						}
					}
				
				}
				/*
				 * 废除 使用 acsList
				 * if(params.get("acsName")!=null){//library_login_name
					device.setLibrary_login_name(params.get("acsName").toString());
				}
				if(params.get("acsPwd")!=null){//library_login_pwd
					device.setLibrary_login_pwd(params.get("acsPwd").toString());
				}
				*/
				if(params.get("logisticsNumber")!=null){
					device.setLogistics_number(params.get("logisticsNumber").toString());
				}
				if(params.get("circuleLocation")!=null){
					device.setCircule_location(params.get("circuleLocation").toString());
				}
				if(params.get("deviceDesc")!=null){
					device.setDevice_desc(params.get("deviceDesc").toString());
				}
				if(params.get("version_stamp")!=null){
					device.setVersion_stamp(Integer.parseInt(params.get("version_stamp").toString()));
				}
				upd=deviceDao.update(device);
				if(upd!=1){
					//throw new RuntimeException("更新设备信息失败：设备ID-->"+device.getDevice_id());
					result.setState(false);
					result.setRetMessage("optimistic");
					return result;
				}
				JedisUtils.getInstance().set(RedisConstant.DEVICE+device.getDevice_id(), JsonUtils.toJson(device));
				/**
				 * 是否更改了模板数据，如果更改了模板才进行更新操作，否则不进行更新操作
				 * 模板IDX 更新选择的模板 ,
				 * 有可能选的是自定义数据  则 值为-1,需要修改device-config 对应的flg==0 即表示使用数据
				 */
				DeviceConfigEntity deviceConfig=new DeviceConfigEntity();
				deviceConfig.setDevice_idx(device.getDevice_idx());
				deviceConfig.setLibrary_idx(device.getLibrary_idx());
				List<DeviceConfigEntity> deviceConfigs=deviceConfigDao.selectList(deviceConfig);//根据device_idx 查询
				DeviceConfigEntity queryDeviceConfig = null;
				if(CollectionUtils.isNotEmpty(deviceConfigs)){
					queryDeviceConfig=deviceConfigs.get(0);
				}
				/*if(params.get("extTempId")!=null){
					if(params.get("extTempId").equals("-1")){
						//选择自定义  并且 原来是模板的情况下 需要更新这个字段 	//原来是自定义则不用更新
						if(queryDeviceConfig.getDevice_ext_tpl_flg()==DeviceConfigEntity.IS_MODEL){
							deviceConfig.setDevice_ext_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
						}
					}else{
						//如果不是-1则表示选择的是其他模板，直接更新模板名 和模板flg
						if(queryDeviceConfig.getDevice_ext_tpl_flg()==DeviceConfigEntity.IS_MODEL&&queryDeviceConfig.getDevice_ext_tpl_idx()==Integer.parseInt(params.get("extTempId").toString())){
							//如果没有发生变化的情况 则不更新这个字段
						}else{
							deviceConfig.setDevice_ext_tpl_flg(DeviceConfigEntity.IS_MODEL);
							deviceConfig.setDevice_ext_tpl_idx(Integer.parseInt(params.get("extTempId").toString()));
						}
					}
				}
				if(params.get("runTempId")!=null){
					if(params.get("runTempId").equals("-1")){
						if(queryDeviceConfig.getDevice_run_tpl_flg()==DeviceConfigEntity.IS_MODEL){
							deviceConfig.setDevice_run_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
						}
					}else{
						//模板ID发生变化的时候更新，不发生变化的时候不更新
						if(queryDeviceConfig.getDevice_run_tpl_flg()==DeviceConfigEntity.IS_MODEL&&queryDeviceConfig.getDevice_run_tpl_idx()==Integer.parseInt(params.get("runTempId").toString())){
						}else{
							deviceConfig.setDevice_run_tpl_flg(DeviceConfigEntity.IS_MODEL);
							deviceConfig.setDevice_run_tpl_idx(Integer.parseInt(params.get("runTempId").toString()));
						}
					}
				}*/
				if(params.get("monitorTempId")!=null){
					if(params.get("monitorTempId").equals("-1")){
						if(queryDeviceConfig.getDevice_monitor_tpl_flg()==DeviceConfigEntity.IS_MODEL){
							deviceConfig.setDevice_monitor_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
						}
					}else{
						if(queryDeviceConfig.getDevice_monitor_tpl_flg()==DeviceConfigEntity.IS_MODEL
								&&queryDeviceConfig.getDevice_monitor_tpl_idx()==Integer.parseInt(params.get("monitorTempId").toString())){
						}else{
							deviceConfig.setDevice_monitor_tpl_flg(DeviceConfigEntity.IS_MODEL);
							deviceConfig.setDevice_monitor_tpl_idx(Integer.parseInt(params.get("monitorTempId").toString()));
						}
					}
				}
				if(params.get("dbSyncTempId")!=null){
					if(params.get("dbSyncTempId").equals("-1")){
						if(queryDeviceConfig.getDevice_dbsync_tpl_flg()==DeviceConfigEntity.IS_MODEL){
							deviceConfig.setDevice_dbsync_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
						}
					}else{
						if(queryDeviceConfig.getDevice_dbsync_tpl_flg()==DeviceConfigEntity.IS_MODEL
								&&queryDeviceConfig.getDevice_dbsync_tpl_idx()==Integer.parseInt(params.get("dbSyncTempId").toString())){
						}else{
							deviceConfig.setDevice_dbsync_tpl_flg(DeviceConfigEntity.IS_MODEL);
							deviceConfig.setDevice_dbsync_tpl_idx(Integer.parseInt(params.get("dbSyncTempId").toString()));
						}
					}
				}
				if(deviceConfig.hasUpdateField()){
					upd=deviceConfigDao.updateDeviceConfig(deviceConfig);
					//下发配置数据
					pushMessage(deviceConfig,deviceConfig.getLibrary_idx(),deviceConfig.getDevice_idx());
					if(upd!=1){
						throw new RuntimeException("更新device_config设备信息失败：设备ID-->"+device.getDevice_id());
					}
				}else{
					//相同 则不更新
					//LogUtils.info("DEVICE_IDX:"+deviceConfig.getDevice_idx()+" 没有修改模板，不更新device_config");
				}
				
				
			}
			result.setState(true);
			//图书馆idx|设备idx|设备ID|设备名|
			result.setRetMessage("馆IDX:"+params.get("library_idx")+"|设备IDX:"+params.get("deviceIdx")+"|设备ID:"+params.get("deviceId")+"|设备名:"+params.get("deviceName")+"|");
		}
	
		return result;
	}
	
	/**
	 * req={"device_idx":deviceIdx,"configName":configName}
	 * configName 规定的参数如下
	 * -->
	 * 1 deviceRunConfig
	 * 2 deviceExtConfig
	 * 3 deviceDbSyncConfig
	 * 4 deviceMonitorConfig
	 * 5 ALL_CONFIG
	 * 返回的数据类型
	 * {
	 * 	"deviceExtConfig":"true",
	 *  "deviceRunConfig":"true",
	 *  ""
	 * }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity checkAllConfigDataByDevIdx(String req) {
		ResultEntity result=new ResultEntity();
		Map<String,String> returnMap=new HashMap<String, String>();
		if(JSONUtils.mayBeJSON(req)){
			Map<String, String> map=JsonUtils.fromJson(req, Map.class);
			if(map.get("deviceIdx")!=null&&map.get("configName")!=null){
				String deviceIdx=map.get("deviceIdx");
				String configName=map.get("configName");
				DeviceEntity device=new DeviceEntity();
				device.setDevice_idx(Integer.parseInt(deviceIdx));
				Integer libIdx=null;
				List<DeviceEntity> deviceEntitys=deviceDao.selectByid(device);
				if(CollectionUtils.isNotEmpty(deviceEntitys)){
					libIdx=deviceEntitys.get(0).getLibrary_idx();
				}else{
					//报错
				}
				if("deviceExtConfig".equals(configName)){
					DeviceExtConfigEntity queryDeviceExt=new DeviceExtConfigEntity();
					queryDeviceExt.setDevice_ext_id(Integer.parseInt(deviceIdx));
					queryDeviceExt.setLibrary_idx(libIdx);
					queryDeviceExt.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
					Integer num=deviceExtConfDao.selectDataTypeCount(queryDeviceExt);
					if(num>0){
						returnMap.put("deviceExtConfig", "true");
					}else{
						returnMap.put("deviceExtConfig", "false");
					}
				}else if("deviceRunConfig".equals(configName)){
					DeviceRunConfigEntity deviceRunConfig=new DeviceRunConfigEntity();
					deviceRunConfig.setDevice_run_id(Integer.parseInt(deviceIdx));
					deviceRunConfig.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
					deviceRunConfig.setLibrary_idx(libIdx);
					Integer num=deviceRunConfDao.selectDataTypeCount(deviceRunConfig);
					if(num>0){
						returnMap.put("deviceRunConfig", "true");
					}else{
						returnMap.put("deviceRunConfig", "false");
					}
				}else if("deviceDbSyncConfig".equals(configName)){
					DeviceDBSyncConfigEntity deviceDBSyncConfig=new DeviceDBSyncConfigEntity();
					deviceDBSyncConfig.setDevice_dbsync_id(Integer.parseInt(deviceIdx));
					deviceDBSyncConfig.setDevice_tpl_type(DeviceDBSyncConfigEntity.IS_NOT_MODEL);
					deviceDBSyncConfig.setLibrary_idx(libIdx);
					Integer num=deviceDBSyncConfDao.selectDataTypeCount(deviceDBSyncConfig);
					if(num>0){
						returnMap.put("deviceDbSyncConfig", "true");
					}else{
						returnMap.put("deviceDbSyncConfig", "false");
					}
				}else if("deviceMonitorConfig".equals(configName)){
					DeviceMonConfigEntity deviceMonConfig=new DeviceMonConfigEntity();
					deviceMonConfig.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
					deviceMonConfig.setDevice_mon_tpl_idx(Integer.parseInt(deviceIdx));
					deviceMonConfig.setLibrary_idx(libIdx);
					Integer num=deviceMonConfDao.selectDataTypeCount(deviceMonConfig);
					if(num>0){
						returnMap.put("deviceMonitorConfig", "true");
					}else{
						returnMap.put("deviceMonitorConfig", "false");
					}
				}else if("ALL_CONFIG".equals(configName)){
					DeviceExtConfigEntity queryDeviceExt=new DeviceExtConfigEntity();
					queryDeviceExt.setDevice_ext_id(Integer.parseInt(deviceIdx));
					queryDeviceExt.setLibrary_idx(libIdx);
					queryDeviceExt.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
					Integer num=deviceExtConfDao.selectDataTypeCount(queryDeviceExt);
					if(num>0){
						returnMap.put("deviceExtConfig", "true");
					}else{
						returnMap.put("deviceExtConfig", "false");
					}
					DeviceRunConfigEntity deviceRunConfig=new DeviceRunConfigEntity();
					deviceRunConfig.setDevice_run_id(Integer.parseInt(deviceIdx));
					deviceRunConfig.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
					deviceRunConfig.setLibrary_idx(libIdx);
					 num=deviceRunConfDao.selectDataTypeCount(deviceRunConfig);
					if(num>0){
						returnMap.put("deviceRunConfig", "true");
					}else{
						returnMap.put("deviceRunConfig", "false");
					}
					DeviceDBSyncConfigEntity deviceDBSyncConfig=new DeviceDBSyncConfigEntity();
					deviceDBSyncConfig.setDevice_dbsync_id(Integer.parseInt(deviceIdx));
					deviceDBSyncConfig.setDevice_tpl_type(DeviceDBSyncConfigEntity.IS_NOT_MODEL);
					deviceDBSyncConfig.setLibrary_idx(libIdx);
					 num=deviceDBSyncConfDao.selectDataTypeCount(deviceDBSyncConfig);
					if(num>0){
						returnMap.put("deviceDbSyncConfig", "true");
					}else{
						returnMap.put("deviceDbSyncConfig", "false");
					}
					DeviceMonConfigEntity deviceMonConfig=new DeviceMonConfigEntity();
					deviceMonConfig.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
					deviceMonConfig.setDevice_mon_tpl_idx(Integer.parseInt(deviceIdx));
					deviceMonConfig.setLibrary_idx(libIdx);
					 num=deviceMonConfDao.selectDataTypeCount(deviceMonConfig);
					if(num>0){
						returnMap.put("deviceMonitorConfig", "true");
					}else{
						returnMap.put("deviceMonitorConfig", "false");
					}
				}
				result.setResult(returnMap);
				result.setState(true);
			}
		}
		return result;
	}

	@Override
	public ResultEntity compareMonitorConfig(String req) {
		ResultEntity result=new ResultEntity();
		if(!JSONUtils.mayBeJSON(req)){
			return result;
		}
		List<DeviceMonConfigEntity> deviceMonConfigs=JsonUtils.fromJson(req, new TypeReference<List<DeviceMonConfigEntity>>() {});
		if(CollectionUtils.isNotEmpty(deviceMonConfigs)){
			DeviceMonConfigEntity deviceMonConfig=deviceMonConfigs.get(0);
			Integer device_mon_tpl_idx=deviceMonConfig.getDevice_mon_tpl_idx();
			List<DeviceMonConfigEntity> qdeviceMonConfigs=deviceMonConfDao.selectList(new DeviceMonConfigEntity(device_mon_tpl_idx,DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_MODEL));
			if(CollectionUtils.isNotEmpty(qdeviceMonConfigs)){
				if(qdeviceMonConfigs.size()!=deviceMonConfigs.size()){
					result.setState(true);
					result.setResult(false);//不相同
				}
			}
			for(DeviceMonConfigEntity updDeviceMonConfig:deviceMonConfigs){
				List<DeviceMonConfigEntity> updDeviceMonConfigs=deviceMonConfDao.setDeviceMonConfigExactly(updDeviceMonConfig);
				if(CollectionUtils.isEmpty(updDeviceMonConfigs)){
					//认定为查不出该条件 ，则 表示发生变化
					//模板IDX=模板IDX device_ext_type=0
					result.setState(true);
					result.setResult(false);//不相同
					break;
				}
			}
			
		}
		if((Boolean)result.getResult()==false){
		}
		
		return result;
	}

	@Override
	public ResultEntity queryDeviceIdbyLibIdx(String req) {
		ResultEntity result=new ResultEntity();
		//Map<Integer,String>//device_id逗号分割
		Map<Integer,String> libIdxAndDevIds=deviceDao.queryDeviceIdbyLibIdx();
		result.setState(true);
		result.setResult(libIdxAndDevIds);
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity getLibraryDevicesByPage(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if (StringUtils.hasText(req)) {
				Map<String, Object> map  = JsonUtils.fromJson(req, Map.class);
				DeviceMgmtPageEntity deviceMgmtPageEntity = new DeviceMgmtPageEntity();
				Integer page = map.get("page")==null?1:Integer.valueOf(map.get("page").toString());				
				Integer pageSize = map.get("pageSize")==null?10:Integer.valueOf(map.get("pageSize").toString());				
				
				String deviceType = map.get("deviceType")==null?"-1":map.get("deviceType").toString();
				String deviceGroup = map.get("deviceGroup")==null?"-1":map.get("deviceGroup").toString();
				String keyword = map.get("keyword")==null?"-1":map.get("keyword").toString();
				
				if(map.get("libIdx")==null && "".equals(map.get("libIdx").toString())){
					result.setValue(false, "参数不能为空", "", "");
					return result;
				}
				
				deviceMgmtPageEntity.setPage(page);
				deviceMgmtPageEntity.setPageSize(pageSize);
				deviceMgmtPageEntity.setLibrary_idx(Integer.valueOf(map.get("libIdx").toString()));
				
				if ("-1".equals(deviceType)) {	deviceType = "";}
				deviceMgmtPageEntity.setDevice_type(deviceType);
				if ("-1".equals(deviceGroup)) {	
					deviceMgmtPageEntity.setDevice_group_idx(null);
				}else{
					deviceMgmtPageEntity.setDevice_group_idx(Integer.valueOf(deviceGroup));
				}
				deviceMgmtPageEntity.setDevice_id(keyword);
				deviceMgmtPageEntity.setDevice_name(keyword);
				
				deviceMgmtPageEntity = deviceDao.getLibraryDevicesByPage(deviceMgmtPageEntity);
				
				result.setValue(true, "","",deviceMgmtPageEntity);
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity devTransferToLibrary(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if (StringUtils.hasText(req)) {
				Map<String, Object> transMap  = JsonUtils.fromJson(req, Map.class);
//				String oldLibId = transMap.get("oldLibid").toString();
				String oldLibIdx = transMap.get("oldLibidx").toString();
//				String newLibId = transMap.get("newLibid").toString();
				String newLibIdx = transMap.get("newLibidx").toString();
//				String client_ip = transMap.get("client_ip").toString();
//				String client_port = transMap.get("client_port").toString();
//				String operator_idx =  transMap.get("operator_idx").toString();
				
				List<String> deviceIdxList = new ArrayList<>();
				
				if(transMap.get("transferList") instanceof List){
					List<Map<String, String>> list = (List<Map<String, String>>) transMap.get("transferList");
					//map{device_id=1,device_idx=1,ip=1.1.1.1}
					for (Map<String, String> map : list) {
						String deviceIdx = map.get("device_idx");
						String ip = map.get("ip");
						if (!StringUtils.hasText(deviceIdx)) {
							continue;
						}
						
						deviceIdxList.add(deviceIdx.trim());
					}
					Map<String, Object> param = new HashMap<>();
					param.put("oldLibIdx", oldLibIdx);
					param.put("newLibIdx", newLibIdx);
					param.put("deviceList", deviceIdxList);
					
					//先删除 rel_device_group,device_acs_logininfo 表中 设备与设备组的关系
					int delRet1 = relDeviceGroupDao.deleteByDeviceIdxs(param);
					int delRet2 = deviceAcsLoginInfoDao.deleteAcsInfoByDeviceIdxs(param);
					//修改device,device_config,
					//device_dbsync_config,device_ext_config,device_monitor_config,
					//device_run_config的数据
					int mdfRet1 = deviceExtConfDao.updateDeviceExtLibrary(param);
					int mdfRet2 = deviceDBSyncConfDao.updateDeviceDBsyncLibrary(param);
					int mdfRet3 = deviceMonConfDao.updateDeviceMonLibrary(param);
					int mdfRet4 = deviceRunConfDao.updateDeviceRunLibrary(param);
					
					int mdfRet5 = deviceDao.updateDeviceLibrary(param);
					int mdfRet6 = deviceConfigDao.updateDeviceConfigLibrary(param);
					
					System.out.println("delRet1:"+delRet1+",delRet2:"+delRet2+",mdfRet1:"+mdfRet1+",mdfRet2:"+mdfRet2);
					System.out.println("mdfRet3:"+mdfRet3+",mdfRet4:"+mdfRet4+",mdfRet5:"+mdfRet5+",mdfRet6:"+mdfRet6);
					
					if (delRet1>=0 && delRet2>=0 && mdfRet1>=0 && mdfRet2>=0
							&& mdfRet3>=0 && mdfRet4>=0 && mdfRet5>=0 && mdfRet6>=0) {
						result.setValue(true, "success");
					}else{
						result.setValue(false, "failed");
						new RuntimeException("failed");
					}
				}
				
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity selectDeviceCode(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if (StringUtils.hasText(req)) {
				Map<String, Object> transMap  = JsonUtils.fromJson(req, Map.class);
				String device_id = transMap.get("device_id").toString();
				String device_code = deviceDao.selectDeviceCode(new DeviceEntity(device_id));
				result.setValue(true, "", "", device_code);
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@Override
	public ResultEntity selectDevicTypeByDeviceId(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
				req = map.get("device_id").toString();
				result.setValue(true, "", "", deviceDao.selectDevicTypeByDeviceId(req));
			}else{
				result.setValue(false, "参数不能为空", "", null);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}

	@Override
	public ResultEntity selectDevIdByIdx(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				DeviceEntity deviceEntity = JsonUtils.fromJson(req, DeviceEntity.class);
				result.setValue(true, "", "", deviceDao.selectDevIdByIdx(deviceEntity));
			}else{
				result.setValue(false, "参数不能为空", "", null);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}


	@Override
	public ResultEntity selectByDevTypeNameList(Map<String, Object> map) {
		ResultEntity result = new ResultEntity();
		try {
				if(map == null){
					map = new HashMap<>(1);
				}else{
					Integer pageCurrent = (Integer) map.get("pageCurrent");
					Integer pageSize = (Integer) map.get("pageSize");
					
					if(pageCurrent != null && pageSize != null){
						pageCurrent = pageCurrent>0 ? pageCurrent:Integer.valueOf(1);
						pageSize = pageSize>0 ? pageSize:Integer.valueOf(15);
						
						map.remove("pageCurrent");
						map.remove("pageSize");
						
						map.put("limitS", ((pageCurrent-1)*pageSize));
						map.put("limitE", pageSize);
					}
				}
			
				List<DeviceEntity> resultList = deviceDao.selectByDevTypeNameList(map);
				result.setResult(resultList);
				result.setState(true);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity getAllDeviceByLibidx(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
				List<Map<String, Object>> list = deviceDao.getAllDeviceByLibidx(map);	
				result.setValue(true, "success", "", list);
			}else{
				result.setValue(false, "参数不能为空", "", null);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity selDeviceById(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
				if (map.containsKey("device_id")) {
					Map<String, Object> dev = deviceDao.selDeviceById(map);	
					result.setValue(true, "success", "", dev);
				}else{
					result.setValue(false, "参数不能为空", "", null);
				}
			}else{
				result.setValue(false, "参数不能为空", "", null);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@Override
	public DeviceMgmtAppPageEntity SelectDeviceMgmtByLibraryIdxs(
			DeviceMgmtAppPageEntity pageEntity) {
		DeviceMgmtAppPageEntity deviceMgmtAppPageEntity = new DeviceMgmtAppPageEntity();
		deviceMgmtAppPageEntity = deviceDao.SelectDeviceMgmtByLibraryIdxs(pageEntity);
		return deviceMgmtAppPageEntity;
	}

	@Override
	public List<Map<String, Object>> selectDeviceRegionByLibidx(Integer library_idx) {
		return deviceDao.selectDeviceRegionByLibidx(library_idx);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity selDeviceIdx(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if (StringUtils.hasText(req)) {
				Map<String, Object> transMap  = JsonUtils.fromJson(req, Map.class);
				String device_id = transMap.get("device_id").toString();
				String device_idx = deviceDao.selectDeviceIdx(new DeviceEntity(device_id));
				result.setValue(true, "", "", device_idx);
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@Override
	public ResultEntity queryDeviceRegion(String devIdx) {
		Map<String, Object> map = new HashMap<>();
		map.put("device_idx", devIdx);
		Map<String, Object> resultMap = deviceDao.queryRegionByDeviceIdx(map);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setValue(true, "", "", resultMap);
		return resultEntity;
	}
	
	public ResultEntity getLibPosition(Map<String,Object> map){
		List<DevicePosition> list = deviceDao.getLibPosition(map);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setValue(true, "", "", list);
		return resultEntity;
	}
	
	public ResultEntity getDevicePosition(Map<String,Object> map){
		List<DevicePosition> list = deviceDao.getDevicePosition(map);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setValue(true, "", "", list);
		return resultEntity;
	}

	@Override
	public int queryDeviceById(String device_id) {
		// TODO Auto-generated method stub
		int count = deviceDao.queryDeviceById(device_id);
		return count;
	}
	
	@Override
	public ResultEntity saveDevicePosition(Map<String, Object> map) {
		deviceDao.saveDevicePosition(map);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setValue(true, "", "","");
		return resultEntity;
	}
	
	@Override
	public ResultEntity updateDevicePosition(Map<String,Object> map){
		deviceDao.updateDevicePosition(map);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setValue(true, "", "","");
		return resultEntity;
	}
	
	@Override
	public int queryLibById(String lib_id) {
		// TODO Auto-generated method stub
		int count = deviceDao.queryLibById(lib_id);
		return count;
	}
	
	@Override
	public ResultEntity saveLibPosition(Map<String, Object> map) {
		deviceDao.saveLibPosition(map);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setValue(true, "", "","");
		return resultEntity;
	}

	@Override
	public ResultEntity updateLibPosition(Map<String,Object> map){
		deviceDao.updateLibPosition(map);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setValue(true, "", "","");
		return resultEntity;
	}

	@Override
	public ResultEntity deleteLibraryPosition(String req) {
		ResultEntity result=new ResultEntity();
		int delNum = 0;
		ArrayList ids = new ArrayList();
		if(JSONUtils.mayBeJSON(req)){
			List<LibraryEntity> list = JsonUtils.fromJson(req, new TypeReference<List<LibraryEntity>>() {});
			if(CollectionUtils.isNotEmpty(list)){
				for(LibraryEntity libraryEntity:list){
					String library_id=libraryEntity.getLib_id();
					ids.add(library_id);
				}
				//删除图书馆数组
				deviceDao.deleteLibraryPosition(ids);
			}
		}
		return result;
	}
	@Override
	public int queryFileUploadFlag(Map<String, Object> map) {
		int count = deviceDao.queryFileUploadFlag(map);
		return count;
	}

	@Override
	public ResultEntity saveFileUploadFlag(Map<String, Object> map) {
		deviceDao.saveFileUploadFlag(map);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setValue(true, "", "", "");
		return resultEntity;
	}

	@Override
	public ResultEntity updateFileUploadFlag(Map<String, Object> map) {
		deviceDao.updateFileUploadFlag(map);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setValue(true, "", "", "");
		return resultEntity;
	}

	@Override
	public List<SyncConfigEntity> SelSyncConfig() {
		return deviceDao.SelSyncConfig(new SyncConfigEntity());
	}

	@Override
	public ResultEntity selectDeviceCountByLibraryIdx(String req) {
		Map<String, String> params = new HashMap<>();
		ResultEntity resultEntity = new ResultEntity();
		if(!StringUtils.isEmpty(req)){
			params = JsonUtils.fromJson(req, Map.class);
		}
		int count = deviceDao.selectDeviceCountByLibraryIdx(params);
		resultEntity.setResult(count);
		resultEntity.setState(true);
		return resultEntity;
	}

	public ResultEntity queryDevStatusCode(){
		ResultEntity resultEntity = new ResultEntity();
		List<DevStatusCode> list = deviceDao.queryDevStatusCode();
		resultEntity.setResult(list);
		resultEntity.setState(true);
		return resultEntity;
	}
	/***
	 * 加载设备到redis缓存
	 */
	public void loadDeviceToRedis(){
		List<DeviceEntity> entities = deviceDao.selectAllDevice();
		for(DeviceEntity deviceEntity : entities){
			//将设备id添加到集合中，key为lib_idx
			JedisUtils.getInstance().sadd(RedisConstant.LIBRARY_DEVICEID+deviceEntity.getLibrary_idx()+""
			,deviceEntity.getDevice_id());
			//将设备信息添加到缓存中
			JedisUtils.getInstance().set(RedisConstant.DEVICE+deviceEntity.getDevice_id()
			,JsonUtils.toJson(deviceEntity));
			//将设备idx与设备id添加到缓存中
			JedisUtils.getInstance().set(RedisConstant.DEVICE_KEYS+deviceEntity.getDevice_idx()
			,deviceEntity.getDevice_id());
		}
	}
	
	public ResultEntity postUrl(String postUrl,String req){
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
}
