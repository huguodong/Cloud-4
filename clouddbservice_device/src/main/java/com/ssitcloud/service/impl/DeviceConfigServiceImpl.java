package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.factory.BeanFactoryHelper;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.dao.DeviceExtConfDao;
import com.ssitcloud.dao.DeviceRunConfDao;
import com.ssitcloud.dao.TableChangeTriDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DownloadCfgSyncEntity;
import com.ssitcloud.service.DeviceConfigService;
import com.ssitcloud.system.TableCommand;

@Service(value = "deviceConfigService")
public class DeviceConfigServiceImpl implements DeviceConfigService {

	@Resource(name = "commonDao")
	private CommonDao commonDao;

	@Resource(name = "deviceRunConfDao")
	protected DeviceRunConfDao deviceRunConfDao;

	@Resource
	protected DeviceExtConfDao deviceExtConfDao;

	@Resource
	protected DeviceDao deviceDao;
	
	@Resource
	private DeviceConfigDao deviceConfigDao;
	
	@Resource
	private TableChangeTriDao changeTriDao;

	@Override
	public int insertDeviceConfig(DeviceConfigEntity deviceConfig) {
		if (deviceConfig == null)
			return 0;
		return commonDao.getSqlSession().insert("deviceConfig.insertDeviceConfig", deviceConfig);
	}

	@Override
	public int updateDeviceConfig(DeviceConfigEntity deviceConfig) {
		if (deviceConfig == null)
			return 0;
		return commonDao.getSqlSession().update("deviceConfig.updateDeviceConfig", deviceConfig);
	}

	@Override
	public List<DeviceConfigEntity> selectList(DeviceConfigEntity deviceConfig) {
		return commonDao.getSqlSession().selectList("deviceConfig.queryDeviceConfig", deviceConfig);
	}

	@Override
	public int deleteDeviceConfig(DeviceConfigEntity deviceConfig) {
		if (deviceConfig == null)
			return 0;
		return commonDao.getSqlSession().delete("deviceConfig.deleteDeviceConfig", deviceConfig);
	}

	@Override
	public List<DeviceConfigEntity> queryDeviceConfigByDevIdAndLibIdx(Map<String, Object> params) {
		return commonDao.getSqlSession().selectList(
				"deviceConfig.queryDeviceConfigByDevIdAndLibIdx", params);
	}

	/**
	 * 如果字段中存在device_config这个值，则更新device表,
	 * 以下继承类中使用到该方法
	 * UploadCfgSyncDeviceExtConfig.java 
	 * UploadCfgSyncDeviceRunConfig.java
	 *{
		 *"LOGISTICS_NUMBER":"", 更新device表
		 *"LIBRARY_ID":"TEST001",更新device LIBRARY_IDX？表
		 *"DEVICE_ID":"SAT087",  更新device表的device_id?
		 *
		 *"USER":""
		 *"PWD":"",
	 *} 
	 *localdb_config 废弃
	 *更改为 device_config  
	 *
	 *
	 * @methodName: ifExistDeviceConfigValUpdate
	 * @return
	 * @returnType: String
	 * @author: liuBh
	 */
	protected int ifExistDeviceConfigValUpdate(Map<String, Object> updMap, Integer device_idx) {
		int updNum = 999;// 如果直接返回999则表示没有存在device_config
		//如果存在run_conf_type==device_config的情况下
		if (updMap.containsKey("run_conf_type")
				&& "device_config".equals(updMap.get("run_conf_type"))) {
			String run_conf_value = updMap.get("run_conf_value").toString();
			Map<String, String> node = convertMap(run_conf_value);
			if(node!=null){
			//JsonNode node = JsonUtils.readTree(run_conf_value);
			
				DeviceEntity device = new DeviceEntity();
				if(node.get("USER")!=null){
					String user = node.get("USER");
					device.setLibrary_login_name(user);
				}
				if(node.get("PWD")!=null){
					String pwd = node.get("PWD");
					device.setLibrary_login_pwd(pwd);
				}
				if(node.get("LOGISTICS_NUMBER")!=null){
					String logisticsNumber = node.get("LOGISTICS_NUMBER");
					device.setLogistics_number(logisticsNumber);
				}
				device.setDevice_idx(device_idx);
				updNum = deviceDao.update(device);
			}
		}
		return updNum;
	}
	
	/**
	 * 字符串转map	
	 * @param str
	 * @return
	 */
	protected Map<String, String> convertMap(String str) {
		if(str.startsWith("{"))str=str.substring(1);
		if(str.endsWith("}"))str=str.substring(0,str.length()-1);
		StringTokenizer token = new StringTokenizer(str,",");
		Map<String, String> map = new HashMap<String, String>();
		while (token.hasMoreTokens()) {
			String next = token.nextToken();
			if (next.indexOf("=") != -1) {
				String[] arry = next.split("\\=");
				if(arry.length==2){
					map.put(arry[0].trim(), arry[1].trim());
				}else if(arry.length==1){
					map.put(arry[0].trim(),"");
				}
			}
		}
		return map;
	}
	
	/**
	 * <p>
	 * 以下继承类中使用到该方法
	 * UploadCfgSyncDeviceExtConfig.java 
	 * UploadCfgSyncDeviceRunConfig.java
	 * </p>
	 * 
	 * fields 格式
	 * {
	 * 	"field1"="1",
	 * 	"field2"="2",
	 *  "field3"="3"
	 *  ..........
	 * }
	 * </br>
	 * records 格式
	 * [
		 {"1"="aaaaaaa","2"="bbbbbbb","3"="ccccccc"},
		 {"1"="dddddd","2"="eeeeee","3"="ffffff"}
	 * ]
	 * @param fields
	 * @param records
	 * @author lbh
	 * @return 
	 * 返回 格式
	 * [
	 * 	{field1=aaaaaaa,field2=bbbbbbb,field3=ccccccc},
	 *  {field1=dddddd,field2=eeeeee,field3=ffffff}
	 * ]
	 */
	protected static List<Map<String, Object>> comboFielsAndValue(Map<String,Object> fields,List<Map<String,Object>> records){
		List<Map<String,Object>> rets=new ArrayList<>();
		for(Map<String,Object> record:records){
			Map<String,Object> newMap=new HashMap<>();
			for(Entry<String, Object> entry:fields.entrySet()){
				String key=entry.getKey();//字段 
				Object value=entry.getValue();//1 2 3 4 
				if (record.get(value) != null) {
					Object obj = record.get(value);
					if (obj instanceof Map) {
						newMap.put(key, JsonUtils.toJson(obj));
					}else{
						newMap.put(key, String.valueOf(obj));
					}
				}
				//String confVal="";
				//if(record.get(value)!=null&&JSONUtils.mayBeJSON(record.get(value).toString())){
				//confVal=JsonUtils.toJson(JsonUtils.fromJson(record.get(value).toString(),Map.class));
				//}else if(record.get(value)!=null){
				//confVal=JSONUtils.stripQuotes(record.get(value).toString());
				//}
				//newMap.put(key,confVal);// 字段:value
			}
			rets.add(newMap);
		}
		return rets;
	}
	/*
	 * 设备端下载配置请求 device_id String 设备ID Library_id String 馆ID DBName String 数据库名
	 * Table String 表名 KeyName String 主键名 lastLocalUpdateTime DateTime 设备端最后更新时间
	 * 
	 * 需要同步的表
	 * 
	 * device_dbsync_config ,
	 * device_ext_config ,
	 * device_run_config,
	 * device_monitor_config,
	 * 
	 * metadata_ext_model ,
	 * metadata_logic_obj ,
	 * metadata_opercmd ,????
	 * metadata_order ? 需要系统类型
	 * metadata_run_config
	 * 
	 * patch_info 
	 * reader_type 读者类型? （不是读者类型，是原来的维护卡信息，现在已经弃用）
	 * maintenance_card 维护卡信息
	 * device
	 */
	@Override
	public ResultEntity downloadCfgSync(String req) {
		ResultEntity result=new ResultEntity();
		try {
			DownloadCfgSyncEntity downLoadCdfReq=JsonUtils.fromJson(req, DownloadCfgSyncEntity.class);
			if(downLoadCdfReq!=null){
				//
				TableCommand tableCommand = null;
				try {
					tableCommand=BeanFactoryHelper.getBean(downLoadCdfReq.getTable(),TableCommand.class);
				} catch (Exception e) {
					throw new RuntimeException("tableName:"+downLoadCdfReq.getTable()+" is not found!!!! ");
				}
				if(tableCommand!=null){
					result=tableCommand.execute(downLoadCdfReq);
				}
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		
		return result;
	}

	

	@Override
	public ResultEntity queryDeviceConfigByDeviceIdx(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			DeviceConfigEntity deviceConfig=JsonUtils.fromJson(req, DeviceConfigEntity.class);
			if(deviceConfig!=null){
				List<DeviceConfigEntity> deviceConfigs=deviceConfigDao.queryDeviceConfigByDeviceIdx(deviceConfig);
				result.setState(true);
				result.setResult(deviceConfigs);
			}
		}
		return result;
	}
	@Override
	public DeviceConfigEntity queryDeviceConfigByDeviceIdx(DeviceConfigEntity deviceConfigEntity){
		if(deviceConfigEntity==null) return null;
		List<DeviceConfigEntity> deviceConfigs=deviceConfigDao.queryDeviceConfigByDeviceIdx(deviceConfigEntity);
		if(CollectionUtils.isNotEmpty(deviceConfigs)){
			return deviceConfigs.get(0);
		}
		return null;
	}

	@Override
	public ResultEntity queryDeviceConfigNewAndOldByDeviceIdx(String req) {
		ResultEntity result=new ResultEntity();
		List<String> tableNames=new ArrayList<>();
		if(JSONUtils.mayBeJSON(req)){
			Map<String, String> map=JsonUtils.fromJson(req, Map.class);
			String delete=map.remove("delete");
			DeviceConfigEntity deviceConfig=JsonUtils.fromJson(JsonUtils.toJson(map), DeviceConfigEntity.class);
			if(delete!=null&&"true".equals(delete)){
				//直接执行删除操作
				//通过idx删除
				if(deviceConfig!=null){
					int del=deviceConfigDao.deleteDeviceConfigOldByIdx(deviceConfig);
					if(del<=0){
						LogUtils.error("删除device_config_old数据失败：device_idx["+deviceConfig.getDevice_idx()+"]");
					}
					result.setState(true);
					return result;
				}
			}else{
				
				if(deviceConfig!=null){
					List<DeviceConfigEntity> deviceConfigs=deviceConfigDao.queryDeviceConfigByDeviceIdx(deviceConfig);
					List<DeviceConfigEntity> deviceConfigOlds=deviceConfigDao.queryDeviceConfigOldByDeviceIdx(deviceConfig);
					if(CollectionUtils.isNotEmpty(deviceConfigs)&&CollectionUtils.isNotEmpty(deviceConfigOlds)){
						DeviceConfigEntity config=deviceConfigs.get(0);
						DeviceConfigEntity configOld=deviceConfigOlds.get(0);
						if(config!=null&&configOld!=null){
							/**
							 * 这种情况是device_dbsync_config 
							 * 模板--->数据(自定义数据原来是存在的情况下没有新增)(设备管理如果有新增自定义数据也加上表)
							 * 数据--->模板 
							 * 模板--->模板
							 */
							if(config.getDevice_dbsync_tpl_flg()!=configOld.getDevice_dbsync_tpl_flg()
									||config.getDevice_dbsync_tpl_idx()!=configOld.getDevice_dbsync_tpl_idx()){
								tableNames.add("device_dbsync_config");
							}
							if(config.getDevice_ext_tpl_flg()!=configOld.getDevice_ext_tpl_flg()
									||config.getDevice_ext_tpl_idx()!=configOld.getDevice_ext_tpl_idx()){
								tableNames.add("device_ext_config");
							}
							if(config.getDevice_run_tpl_flg()!=configOld.getDevice_run_tpl_flg()
									||config.getDevice_run_tpl_idx()!=configOld.getDevice_run_tpl_idx()){
								tableNames.add("device_run_config");
							}
							result.setResult(tableNames);
							result.setState(true);
						
						}
					}
				}
			}
			
			
		}
		return result;
	}
	/**
	 * 获取所有使用该模版的设备ID
	 */
	@Override
	public ResultEntity queryDevIdsByModelAndModelIdx(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String, String> m=JsonUtils.fromJson(req, Map.class);
			String model_idx;
			Integer model_idx_int;
			String table_name;
			if(m.containsKey("model_idx")){
				 model_idx=m.get("model_idx");
				 try {
					 model_idx_int=Integer.parseInt(model_idx);
				} catch (Exception e) {
					LogUtils.error("model_idx 不是 数字···"+model_idx);
					model_idx_int=-1;
				}
			}else{
				return result;
			}
			if(m.containsKey("table_name")){
				table_name=m.get("table_name");
			}else{
				return result;
			}
			DeviceConfigEntity deviceConfig=new DeviceConfigEntity();
			if("device_dbsync_config".equals(table_name)){
				deviceConfig.setDevice_dbsync_tpl_flg(DeviceConfigEntity.IS_MODEL);
				deviceConfig.setDevice_dbsync_tpl_idx(model_idx_int);
			}else if("device_ext_config".equals(table_name)){
				deviceConfig.setDevice_ext_tpl_flg(DeviceConfigEntity.IS_MODEL);
				deviceConfig.setDevice_ext_tpl_idx(model_idx_int);
			}else if("device_run_config".equals(table_name)){
				deviceConfig.setDevice_run_tpl_flg(DeviceConfigEntity.IS_MODEL);
				deviceConfig.setDevice_run_tpl_idx(model_idx_int);
			}else{
				return result;
			}
			result.setState(true);
			result.setResult(deviceConfigDao.selectList(deviceConfig));
		}
		
		return result;
	}

}
