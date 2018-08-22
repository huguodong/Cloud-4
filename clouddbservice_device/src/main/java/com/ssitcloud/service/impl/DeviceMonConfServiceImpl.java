package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceExtConfDao;
import com.ssitcloud.dao.DeviceMonConfDao;
import com.ssitcloud.dao.MetadataDeviceTypeDao;
import com.ssitcloud.dao.MetadataLogicObjDao;
import com.ssitcloud.entity.DevMonConfMetadataLogObjEntity;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncConfigEntity;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.DeviceMonConfigEntity;
import com.ssitcloud.entity.DeviceMonTempEntity;
import com.ssitcloud.entity.DeviceMonitorTempAndConfigEntity;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.entity.MetadataLogicObjEntity;
import com.ssitcloud.entity.page.DeviceMonTempPageEntity;
import com.ssitcloud.service.DeviceMonConfService;
import com.ssitcloud.system.entity.DeviceDBSyncConfigRemoteEntity;

@Service
public class DeviceMonConfServiceImpl implements DeviceMonConfService{

	
	@Resource
	private DeviceMonConfDao deviceMonConfDao;
	@Resource
	private DeviceConfigDao deviceConfigDao;
	@Resource
	private MetadataLogicObjDao metadataLogicObjDao;
	@Resource
	private MetadataDeviceTypeDao metadataDeviceTypeDao;
	@Resource
	private DeviceExtConfDao deviceExtConfDao;
	
	@Override
	public int insertDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig){
		if(deviceMonConfig==null) return 0;
		return deviceMonConfDao.insertDeviceMonConfig( deviceMonConfig);
	}
	@Override
	public int updateDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig){
		if(deviceMonConfig==null) return 0;
		return deviceMonConfDao.updateDeviceMonConfig( deviceMonConfig);
	}
	@Override
	public int deleteDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig){
		if(deviceMonConfig==null) return 0;
		return deviceMonConfDao.deleteDeviceMonConfig( deviceMonConfig);
	}
	@Override
	public DeviceMonConfigEntity selectOne(DeviceMonConfigEntity deviceMonConfig){
		return deviceMonConfDao.selectOne( deviceMonConfig);
	}
	@Override
	public List<DeviceMonConfigEntity> selectList(DeviceMonConfigEntity deviceMonConfig){
		return deviceMonConfDao.selectList( deviceMonConfig);
	}
	@Override
	public int insertDeviceMonTemp(DeviceMonTempEntity deviceMonTemp){
		if(deviceMonTemp==null) return 0;
		return deviceMonConfDao.insertDeviceMonTemp( deviceMonTemp);
	}
	@Override
	public int updateDeviceMonTemp(DeviceMonTempEntity deviceMonTemp){
		if(deviceMonTemp==null) return 0;
		return deviceMonConfDao.updateDeviceMonTemp( deviceMonTemp);
	}
	@Override
	public int deleteDeviceMonTemp(DeviceMonTempEntity deviceMonTemp){
		if(deviceMonTemp==null) return 0;
		return deviceMonConfDao.deleteDeviceMonTemp(deviceMonTemp);
	}
	@Override
	public DeviceMonTempEntity selectOne(DeviceMonTempEntity deviceMonTemp){
		return deviceMonConfDao.selectOne( deviceMonTemp);
	}
	@Override
	public List<DeviceMonTempEntity> selectList(DeviceMonTempEntity deviceMonTemp){
		return deviceMonConfDao.selectList( deviceMonTemp);
	}
	@Override
	public List<Map<String, Object>> selAllMonitorTempList(Map<String, String> param ) {
		List<Map<String, Object>> returnList= new ArrayList<>();
		//获取所有的运行模板
		DeviceMonTempEntity tempEntity = new DeviceMonTempEntity();
		if (param!=null && param.get("device_type")!=null && !param.get("device_type").equals("")) {
			tempEntity.setDevice_mon_type_idx(Integer.valueOf(param.get("device_type")));
		}
		List<DeviceMonTempEntity> monTempList = deviceMonConfDao.selectTempBytype(tempEntity);
		
		for (DeviceMonTempEntity deviceMonTempEntity : monTempList) {
			Map<String, Object> monTmpMap = new HashMap<String, Object>();
			int idx = deviceMonTempEntity.getDevice_mon_tpl_idx();
			List<Map<String, Object>> monitorList = deviceMonConfDao.selectmonitor_infoByidx(idx);
//			
//			private String device_mon_tpl_idx;
//			private String device_mon_tpl_id;
//			private String device_mon_tpl_desc;
//			private String device_mon_type_idx;
			
			monTmpMap.put("device_mon_tpl_idx", deviceMonTempEntity.getDevice_mon_tpl_idx());
			monTmpMap.put("device_mon_tpl_id", deviceMonTempEntity.getDevice_mon_tpl_id());
			monTmpMap.put("device_mon_tpl_desc", deviceMonTempEntity.getDevice_mon_tpl_desc());
			monTmpMap.put("device_mon_type_idx", deviceMonTempEntity.getDevice_mon_type_idx());
 			monTmpMap.put("monitorDetailList", monitorList);
			returnList.add(monTmpMap);
		}
		return returnList;
	}
	
	@Override
	public DeviceMonTempPageEntity queryDeviceMonitorByParam(String req) {
		//DeviceMonConfigPageEntity deviceMonConfigPage=new DeviceMonConfigPageEntity();
		DeviceMonTempPageEntity deviceMonTempPage=new DeviceMonTempPageEntity();
		// 暂定查询条件 device_type、device_name
		if(JSONUtils.mayBeJSON(req)){
			deviceMonTempPage=JsonUtils.fromJson(req, DeviceMonTempPageEntity.class);
		}
		DeviceMonTempPageEntity monconfPage= deviceMonConfDao.selectPage(deviceMonTempPage);
		
		return monconfPage;
	}
	/**
	 * 需要至少选择一个参数
	 */
	@Override
	public ResultEntity AddNewDeviceMonitorConfAndTemp(String req) {
		ResultEntity result=new ResultEntity();
		int inserNum=0;
		Integer device_mon_tpl_idx=null;
		String device_mon_tpl_desc=null;
		if(JSONUtils.mayBeJSON(req)){
			DeviceMonitorTempAndConfigEntity devMonTempAndConfig=JsonUtils.fromJson(req, DeviceMonitorTempAndConfigEntity.class);
			if(devMonTempAndConfig!=null){
				DeviceMonTempEntity deviceMonTemp=devMonTempAndConfig.getDevice_mon_tpl();
				//获取设备类型  在 获取对应的设备逻辑外设 然后判断只能有这么几个外设
				Integer deviceTypeIdx=deviceMonTemp.getDevice_mon_type_idx();
				Assert.hasText(deviceMonTemp.getDevice_mon_tpl_desc(), "模板名称不能为空");
				Assert.hasText(deviceMonTemp.getDevice_mon_tpl_id(),"模板ID不能为空");
				MetadataDeviceTypeEntity metadataDeviceType=new MetadataDeviceTypeEntity();
				metadataDeviceType.setMeta_devicetype_idx(deviceTypeIdx);
				MetadataDeviceTypeEntity qMetadataDeviceType=metadataDeviceTypeDao.selOneByIdx(metadataDeviceType);
				String logStr=qMetadataDeviceType.getDevice_logic_list();
				List<MetadataLogicObjEntity> metadataLogicObjs=metadataLogicObjDao.selectInlogicObjArr(Arrays.asList(StringUtils.commaDelimitedListToStringArray(logStr)));
				//验证是否符合 对应的逻辑外设   这样的话前段要判断对应的外设
				if(CollectionUtils.isNotEmpty(metadataLogicObjs)){
					Map<Integer, String> map =new HashMap<>();
					for(MetadataLogicObjEntity metadataLogicObjEntity:metadataLogicObjs){
						map.put(metadataLogicObjEntity.getMeta_log_obj_idx(), metadataLogicObjEntity.getLogic_obj());
					}
					List<DeviceMonConfigEntity> deviceMonConfigs=devMonTempAndConfig.getDev_mon_conf_Arr();
					if(CollectionUtils.isNotEmpty(deviceMonConfigs)){
						for(DeviceMonConfigEntity deviceMonConfig:deviceMonConfigs){
							//"metadata_logic_obj".equals(deviceMonConfig.getTable_name())&&!
							if(!"time_out".equals(deviceMonConfig.getTable_name())
									&&!map.containsKey(deviceMonConfig.getMeta_log_obj_idx())){
								throw new RuntimeException("该设备类型没有对应的逻辑外设:"+deviceMonConfig.getMeta_log_obj_idx());
							}
						}
					}
				}
				boolean atLeastOneInsert=false;
				if(deviceMonTemp!=null){
					try {
						inserNum=deviceMonConfDao.insertDeviceMonTemp(deviceMonTemp);
					} catch ( org.springframework.dao.DuplicateKeyException e) {//for key 'device_mon_tpl_id'
						String msg=e.getRootCause().getMessage();
						AntPathMatcher matcher=new AntPathMatcher();
						if(matcher.match("*device_mon_tpl_id*", msg)){
							throw new RuntimeException("模板ID已经被使用，请更改后保存");
						}else if(matcher.match("*device_mon_tpl_desc*", msg)){
							throw new RuntimeException("模板名称已经被使用，请更改后保存");
						}else{
							throw new RuntimeException(e);
						}		
					}
					if(inserNum>0){
						device_mon_tpl_idx=deviceMonTemp.getDevice_mon_tpl_idx();
						device_mon_tpl_desc=deviceMonTemp.getDevice_mon_tpl_desc();
						if(device_mon_tpl_idx!=null){
							List<DeviceMonConfigEntity> deviceMonConfigs= devMonTempAndConfig.getDev_mon_conf_Arr();
							for(DeviceMonConfigEntity deviceMonConfig:deviceMonConfigs){
								deviceMonConfig.setLibrary_idx(0);//默认为0  在页面已经默认设置
								deviceMonConfig.setVisiable(1);//可见？
								deviceMonConfig.setDevice_ext_type(0);//模板
								deviceMonConfig.setDevice_mon_tpl_idx(device_mon_tpl_idx);
								//没有选择颜色不插入数据
								if(deviceMonConfig.getColor()!=null&&deviceMonConfig.getColor()>-1){
									inserNum=deviceMonConfDao.insertDeviceMonConfig(deviceMonConfig);
									if(inserNum<0){
										throw new RuntimeException("device_monitor_config 新增监控配置模板失败: logic_obj_idx "+deviceMonConfig.getLogic_obj_idx()+" table_name:"+deviceMonConfig.getTable_name());	
									}
									if(!"time_out".equals(deviceMonConfig.getTable_name())){
										atLeastOneInsert=true;
									}
								}
							}
						}else{
							throw new RuntimeException("device_monitor_template 新增监控配置模板失败");	
						}
					}else{
						throw new RuntimeException("新增监控配置模板失败");
					}
				}
				if(!atLeastOneInsert){
					//一个数据都没有的情况下 抛异常
					throw new RuntimeException("至少设置一个监控配置信息（选择颜色）");	
				}
			}
		}
		if(inserNum>0){
			result.setState(true);
			result.setRetMessage("|IDX:"+device_mon_tpl_idx+"|模板描述:"+device_mon_tpl_desc+"||");
		}
		return result;
	}
	/**
	 * 只对模板操作
	 * 更新操作是要先删除所有 再新增
	 * 
	 * 需要至少选择一个参数
	 *
	 */
	@Override
	public ResultEntity UpdNewDeviceMonitorConfAndTemp(String req) {
		ResultEntity result=new ResultEntity();
		int updateNum=0;
		int delNum=0;
		Integer device_mon_tpl_idx=null;
		String device_mon_tpl_desc=null;
		if(JSONUtils.mayBeJSON(req)){
			/**
			 * 	"device_mon_tpl_idx":device_mon_tpl_idx,
				"logic_obj_idx":meta_log_obj_idx/.....idx,
				"alert":alert,
				"color":color,
				"threshold":threshold,
				"table_name":table_name,
				"meta_log_obj_idx":meta_log_obj_idx, //用于区分PLC设备
				"visiable":1,
				"library_idx":0
				"device_ext_type":0
			 */
			
			DeviceMonitorTempAndConfigEntity devMonTempAndConfig=JsonUtils.fromJson(req, DeviceMonitorTempAndConfigEntity.class);
			if(devMonTempAndConfig!=null){
				List<DeviceMonConfigEntity> deviceMonConfigs= devMonTempAndConfig.getDev_mon_conf_Arr();
				DeviceMonTempEntity deviceMonTemp=devMonTempAndConfig.getDevice_mon_tpl();
				if(deviceMonTemp!=null){
					//获取设备类型  在 获取对应的设备逻辑外设 然后判断只能有这么几个外设
					Integer deviceTypeIdx=deviceMonTemp.getDevice_mon_type_idx();
					//直接update 不管结果如何
					deviceMonConfDao.updateDeviceMonTemp(deviceMonTemp);
					MetadataDeviceTypeEntity metadataDeviceType=new MetadataDeviceTypeEntity();
					metadataDeviceType.setMeta_devicetype_idx(deviceTypeIdx);
					MetadataDeviceTypeEntity qMetadataDeviceType=metadataDeviceTypeDao.selOneByIdx(metadataDeviceType);
					String logStr=qMetadataDeviceType.getDevice_logic_list();
					List<MetadataLogicObjEntity> metadataLogicObjs=metadataLogicObjDao.selectInlogicObjArr(Arrays.asList(StringUtils.commaDelimitedListToStringArray(logStr)));
					//验证是否符合 对应的逻辑外设
					if(CollectionUtils.isNotEmpty(metadataLogicObjs)){
						Map<Integer, String> map =new HashMap<>();
						for(MetadataLogicObjEntity metadataLogicObjEntity:metadataLogicObjs){
							map.put(metadataLogicObjEntity.getMeta_log_obj_idx(), metadataLogicObjEntity.getLogic_obj());
						}
						List<DeviceMonConfigEntity> qDeviceMonConfigs=devMonTempAndConfig.getDev_mon_conf_Arr();
						if(CollectionUtils.isNotEmpty(qDeviceMonConfigs)){
							for(DeviceMonConfigEntity deviceMonConfig:qDeviceMonConfigs){
								//"metadata_logic_obj".equals(deviceMonConfig.getTable_name())&&
								if(!"time_out".equals(deviceMonConfig.getTable_name())
										&&!map.containsKey(deviceMonConfig.getMeta_log_obj_idx())){
									throw new RuntimeException("该设备类型没有对应的逻辑外设:"+deviceMonConfig.getMeta_log_obj_idx());
								}
							}
						}
					}
					
					 device_mon_tpl_idx=deviceMonTemp.getDevice_mon_tpl_idx();
					 device_mon_tpl_desc=deviceMonTemp.getDevice_mon_tpl_desc();
					 boolean atLeastOneInsert=false;
					if(device_mon_tpl_idx!=null){
						//两个参数 模板ID 和 模板FLAG （0）
						DeviceMonConfigEntity deviceMonConfig=new DeviceMonConfigEntity(device_mon_tpl_idx,0);
						
						List<DeviceMonConfigEntity> queryDeviceMonConfigs=deviceMonConfDao.selectList(deviceMonConfig);
						//首先删除所有
						delNum=deviceMonConfDao.deleteDeviceMonConfig(deviceMonConfig);
						if(queryDeviceMonConfigs!=null){
							if(queryDeviceMonConfigs.size()!=delNum){
								//删除的数目不对等，则报错
								throw new RuntimeException("更新监控模板操作 -->删除模板数据失败");
							}
							//插入操作
							if(deviceMonConfigs!=null){
								for(DeviceMonConfigEntity dmc:deviceMonConfigs){
									dmc.setLibrary_idx(0);//
									dmc.setVisiable(1);//可见？
									dmc.setDevice_ext_type(0);//模板
									dmc.setDevice_mon_tpl_idx(device_mon_tpl_idx);
									//没有选择颜色不插入数据
									if(dmc.getColor()!=null&&dmc.getColor()>-1){
										updateNum=deviceMonConfDao.insertDeviceMonConfig(dmc);
										if(updateNum<=0){//插入失败 报错
											throw new RuntimeException("更新监控模板操作 --插入模板数据失败");
										}
										if(!"time_out".equals(dmc.getTable_name())){
											atLeastOneInsert=true;
										}
									}
								}
							}
						}
					}
					if(!atLeastOneInsert){
						//一个数据都没有的情况下 抛异常
						throw new RuntimeException("至少设置一个监控配置信息（选择颜色）");	
					}
				}
				
			}
		}
		if(updateNum>0){
			result.setState(true);
			result.setRetMessage("|IDX:"+device_mon_tpl_idx+"|模板描述:"+device_mon_tpl_desc+"||");
		}
		return result;
	}
	@Override
	public int DelNewDeviceMonitorConfAndTemp(String req) {
		//"device_mon_tpl_idx":device_mon_tpl_idx  参数 主键
		int delNum=0;
		
		if(JSONUtils.mayBeJSON(req)){
			DeviceMonTempEntity deviceMonTemp=JsonUtils.fromJson(req, DeviceMonTempEntity.class);
			DeviceMonConfigEntity deviceMonConfig=JsonUtils.fromJson(req, DeviceMonConfigEntity.class);
			
			deviceMonConfig.setLibrary_idx(0);//默认设置为0
			deviceMonConfig.setDevice_ext_type(0);//模板
						
			List<DeviceMonConfigEntity> deviceMonConfigs= deviceMonConfDao.selectList(deviceMonConfig);
			
			if(deviceMonConfigs!=null&&deviceMonConfigs.size()>0){
				delNum=deviceMonConfDao.deleteDeviceMonConfig(deviceMonConfig);
				if(delNum<1){
					throw new RuntimeException("删除模板配置模板数据失败");
				}
			}
			try {
				delNum=deviceMonConfDao.deleteDeviceMonTemp(deviceMonTemp);
			} catch (Exception e) {
				throw new RuntimeException("存在关联的外键，可能模板正在使用中");
			}
		
			if(delNum<1){
				throw new RuntimeException("删除模板配置模板失败");
			}
		}

		return delNum;
	}
	
	
	@Override
	public ResultEntity SelDevMonConfMetaLogObjByDeviceMonTplIdx(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			DevMonConfMetadataLogObjEntity devMonConfMetadataLogObj=JsonUtils.fromJson(req, DevMonConfMetadataLogObjEntity.class);
			List<DevMonConfMetadataLogObjEntity> devMonConfMetadataLogObjs=deviceMonConfDao.SelDevMonConfMetaLogObjByDeviceMonTplIdx(devMonConfMetadataLogObj);
			if(devMonConfMetadataLogObjs!=null){
				result.setResult(devMonConfMetadataLogObjs);
				result.setState(true);
			}
		}
		return result;
	}
	
	
 
	/**
	 * 获取颜色
	 * #查询所有设备用的是数据还是模板
	 	SELECT device_idx,device_monitor_tpl_flg,device_monitor_tpl_idx FROM device_config
	 	#过滤出所有的是模板的则 取出所有模板ID，不是模板的获取设备IDX
	 */
	@Override
	public ResultEntity queryAlertColor(String req) {
		ResultEntity result=new ResultEntity();
		List<Integer> monitorTplList=new ArrayList<>();
		List<Integer> deviceIdxList=new ArrayList<>();
		List<DeviceConfigEntity> deviceConfigs=deviceConfigDao.selectList(new DeviceConfigEntity());
		if(deviceConfigs!=null){
			for(DeviceConfigEntity deviceConfig:deviceConfigs){
				if(DeviceConfigEntity.IS_MODEL.equals(deviceConfig.getDevice_monitor_tpl_flg())){
					monitorTplList.add(deviceConfig.getDevice_monitor_tpl_idx());//设备模板ID
				}else if(DeviceConfigEntity.IS_NOT_MODEL.equals(deviceConfig.getDevice_monitor_tpl_flg())){
					deviceIdxList.add(deviceConfig.getDevice_idx());//设备自增主键
				}
			}
		}
		//根据查询模板ID 查询出 模板部件对应的报警颜色
		//Map<String,Object> color=deviceMonConfDao.queryAlertColor();
		//result.setResult(color);
		result.setState(true);
		return result;
	}
	@Override
	public int DeleteMonitor(DeviceMonConfigEntity deviceMonConfigEntity) {
		DeviceMonConfigEntity dMonConfigEntity = new DeviceMonConfigEntity();
		dMonConfigEntity.setDevice_mon_tpl_idx(deviceMonConfigEntity.getDevice_mon_tpl_idx());
		dMonConfigEntity.setDevice_ext_type(deviceMonConfigEntity.getDevice_ext_type());
		return deviceMonConfDao.deleteDeviceMonConfig(dMonConfigEntity);
	}
	/**
	 * 对自定义数据操作
	 * 更新监控配置数据
	 */
	@Override
	public ResultEntity UpdateMonitor(String json) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(json)){
			List<DeviceMonConfigEntity> list = JsonUtils.fromJson(json, new TypeReference<List<DeviceMonConfigEntity>>() {});
			if(CollectionUtils.isNotEmpty(list)){
				DeviceMonConfigEntity dEntity = list.get(0);
				dEntity.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
				Integer device_idx=dEntity.getDevice_mon_tpl_idx();
				DeviceMonConfigEntity deviceMonConfig=new DeviceMonConfigEntity();
				deviceMonConfig.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
				deviceMonConfig.setDevice_mon_tpl_idx(device_idx);
				List<DeviceMonConfigEntity> queryList =selectList(deviceMonConfig);
				if(CollectionUtils.isNotEmpty(queryList)){
					//如果是自定义数据 则先删除 idx=device_idx 和 device_mon_type=1 的数据
					//注意：查询的条件要和善处的条件一致
					int ret = DeleteMonitor(dEntity);
					if(ret!=queryList.size()){
						throw new RuntimeException("更新监控配置数据 中 删除旧数据失败");
					}
				}
				int re =0;
				for (DeviceMonConfigEntity deviceMonConfigEntity : list) {
					//没有选择颜色不插入数据
					if(deviceMonConfigEntity.getColor()!=null&&deviceMonConfigEntity.getColor()>-1){
						re =insertDeviceMonConfig(deviceMonConfigEntity);
						if(re<=0){//插入失败 报错
							throw new RuntimeException("更新监控配置数据 中 新增新数据失败");
						}
					}
				}
				if(device_idx!=null){
					DeviceConfigEntity deviceConfig=new DeviceConfigEntity();
					deviceConfig.setDevice_idx(device_idx);
					List<DeviceConfigEntity> deviceConfigs=deviceConfigDao.queryDeviceConfigByDeviceIdx(deviceConfig);
					if(CollectionUtils.isNotEmpty(deviceConfigs)){
						Integer tplFlag=deviceConfigs.get(0).getDevice_monitor_tpl_flg();
						if(tplFlag!=null&&tplFlag==DeviceConfigEntity.IS_MODEL){
							//如果原来使用的是模板需要改为非模板
							deviceConfig.setDevice_monitor_tpl_flg(DeviceConfigEntity.IS_NOT_MODEL);
							int upd=deviceConfigDao.updateDeviceConfig(deviceConfig);
							if(upd<=0){
								throw new RuntimeException("更新监控配置数据 中 更新为非模板失败");
							}
						}
					}
				}
			}
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		}
		return result;
	}
	/***
	 * 获取设备监控配置信息 
	 * 注意：需要配合 硬件模板使用
	 */
	@Override
	public ResultEntity GetCurrentDevColorSet(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			List<Integer> devIdxList=JsonUtils.fromJson(req, new TypeReference<List<Integer>>(){});
			if(CollectionUtils.isNotEmpty(devIdxList)){
				//先查询设备是不是用的模板
				List<DeviceConfigEntity> deviceConfigs=deviceConfigDao.queryDeviceConfigInDeviceIdxArr(devIdxList);
				if(CollectionUtils.isNotEmpty(deviceConfigs)){
					Map<Integer,List<DeviceMonConfigEntity>> map=new HashMap<>();
					for(DeviceConfigEntity deviceConfigEntity:deviceConfigs){
						Integer tplFlg=deviceConfigEntity.getDevice_monitor_tpl_flg();
						Integer library_idx=deviceConfigEntity.getLibrary_idx();
						DeviceMonConfigEntity deviceMonConfig=new DeviceMonConfigEntity();
						
						DeviceExtConfigEntity devExtConf=new DeviceExtConfigEntity();
						// device_ext_id 设备IDX或者模板IDX

						if(DeviceConfigEntity.IS_NOT_MODEL.equals(tplFlg)){
							Integer idx=deviceConfigEntity.getDevice_idx();
							deviceMonConfig.setDevice_mon_tpl_idx(idx);
							deviceMonConfig.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
							deviceMonConfig.setLibrary_idx(library_idx);
						
							devExtConf.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
							devExtConf.setDevice_ext_id(idx);
							devExtConf.setLibrary_idx(library_idx);
						}else if(DeviceConfigEntity.IS_MODEL.equals(tplFlg)){
							Integer device_mon_tpl_idx=deviceConfigEntity.getDevice_monitor_tpl_idx();
							deviceMonConfig.setLibrary_idx(0);//如果是模板的话，library_idx设置为0
							deviceMonConfig.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
							deviceMonConfig.setDevice_mon_tpl_idx(device_mon_tpl_idx);
							
							Integer device_ext_tpl_idx=deviceConfigEntity.getDevice_ext_tpl_idx();
							devExtConf.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
							devExtConf.setLibrary_idx(0);//如果是模板的话，library_idx设置为0
							devExtConf.setDevice_ext_id(device_ext_tpl_idx);
						}
						//组装 deviceIdx:[]
						List<DeviceMonConfigEntity> tempDeviceMonConfigs=deviceMonConfDao.selectList(deviceMonConfig);
						
						List<DeviceMonConfigEntity> deviceMonConfigs =new ArrayList<>(tempDeviceMonConfigs);
						//如果硬件模板没有这个部件的则过滤掉
						List<DeviceExtConfigEntity> deviceExtConfigs=deviceExtConfDao.selectList(devExtConf);
						if(deviceMonConfigs!=null && deviceMonConfigs.size()>0 && deviceExtConfigs!=null && deviceExtConfigs.size()>0){
							
							List<DeviceMonConfigEntity> removeIndexArr=new ArrayList<>();
                            for (DeviceMonConfigEntity dm : deviceMonConfigs) {
                                boolean hasObj = false;
                                for (DeviceExtConfigEntity de : deviceExtConfigs) {
                                    Integer logicObjIdx = de.getLogic_obj_idx();
                                    Integer dmlogicObjIdx = dm.getMeta_log_obj_idx();
                                    if (dmlogicObjIdx == 0 || dmlogicObjIdx.equals(logicObjIdx)) {
                                        hasObj = true;
                                        break;
                                    }
                                }
                                if (!hasObj) {
                                    removeIndexArr.add(dm);
                                }
                            }
							if(!removeIndexArr.isEmpty()){
                                deviceMonConfigs.removeAll(removeIndexArr);
							}
						}
						map.put(deviceConfigEntity.getDevice_idx(), deviceMonConfigs);
					}
					result.setState(true);
					result.setResult(map);
				}
			}
		}
		return result;
	}
	
	
	/**
	 * 查询设备监控模板配置
	 * @param configEntity
	 * @return
	 */
	public List<DeviceMonConfigEntity> getDeviceMonConfig(DeviceConfigEntity configEntity){
		
		DeviceMonConfigEntity params = new DeviceMonConfigEntity();
		
		if(DeviceConfigEntity.IS_MODEL.equals(configEntity.getDevice_monitor_tpl_flg())){
			params.setDevice_mon_tpl_idx(configEntity.getDevice_monitor_tpl_idx());
			params.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
			List<DeviceMonConfigEntity> devMonConfigs = deviceMonConfDao.selectList(params);
			return devMonConfigs;
			
		}else if(DeviceConfigEntity.IS_NOT_MODEL.equals(configEntity.getDevice_monitor_tpl_flg())){
			
			params.setLibrary_idx(configEntity.getLibrary_idx());
			params.setDevice_mon_tpl_idx(configEntity.getDevice_idx());
			params.setDevice_ext_type(DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_DATA);
			List<DeviceMonConfigEntity> devMonConfigs=deviceMonConfDao.selectList(params);
			return devMonConfigs;
			
		}
		
		return null;
		
	}
	
	
	
	

}
