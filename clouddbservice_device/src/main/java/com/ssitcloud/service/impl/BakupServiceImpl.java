package com.ssitcloud.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.common.util.PropertiesUtil;
import com.ssitcloud.dao.AcsConfigDao;
import com.ssitcloud.dao.BakuDao;
import com.ssitcloud.dao.DeviceAcsLoginInfoDao;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceDBSyncConfDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.dao.DeviceExtConfDao;
import com.ssitcloud.dao.DeviceGroupDao;
import com.ssitcloud.dao.DeviceMonConfDao;
import com.ssitcloud.dao.DeviceRunConfDao;
import com.ssitcloud.dao.MetaRunConfigDao;
import com.ssitcloud.dao.MetadataDeviceTypeDao;
import com.ssitcloud.dao.MetadataExtModelDao;
import com.ssitcloud.dao.MetadataLogicObjDao;
import com.ssitcloud.dao.OperatorGroupDao;
import com.ssitcloud.dao.OperatorMaintenanceInfoDao;
import com.ssitcloud.dao.ReaderTypeDao;
import com.ssitcloud.dao.RelDeviceGroupDao;
import com.ssitcloud.dao.RelOperatorDeviceGroupDao;
import com.ssitcloud.dao.RelOperatorGroupDao;
import com.ssitcloud.dao.RelOperatorServiceGroupDao;
import com.ssitcloud.dao.ServiceGroupDao;
import com.ssitcloud.entity.ACSProtocolEntity;
import com.ssitcloud.entity.BakupDataEntity;
import com.ssitcloud.entity.DeviceAcsLoginInfoEntity;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncTempEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.DeviceExtTempEntity;
import com.ssitcloud.entity.DeviceGroupEntity;
import com.ssitcloud.entity.DeviceMonConfigEntity;
import com.ssitcloud.entity.DeviceMonTempEntity;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.DeviceRunTempEntity;
import com.ssitcloud.entity.MetaRunConfigEntity;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.entity.MetadataExtModelEntity;
import com.ssitcloud.entity.MetadataLogicObjEntity;
import com.ssitcloud.entity.OperatorGroupEntity;
import com.ssitcloud.entity.OperatorMaintenanceInfoEntity;
import com.ssitcloud.entity.ProtocolConfigTemplateEntity;
import com.ssitcloud.entity.ReaderTypeEntity;
import com.ssitcloud.entity.RelOperatorDeviceGroupEntity;
import com.ssitcloud.entity.RelOperatorGroupEntity;
import com.ssitcloud.entity.RelOperatorServiceGroupEntity;
import com.ssitcloud.entity.ServiceGroupEntity;
import com.ssitcloud.service.BakupService;
import com.ssitcloud.service.DeviceConfigService;

@Service
public class BakupServiceImpl  implements BakupService{
	@Resource
	private CommonDao commonDao;
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURLList;
	@Resource
	private RelOperatorServiceGroupDao relOperatorServiceGroupDao;
	@Resource
	private DeviceDBSyncConfDao deviceDBSyncConfDao;
	@Resource
	private DeviceExtConfDao deviceExtConfDao;
	@Resource
	private DeviceRunConfDao deviceRunConfDao;
	@Resource
	private DeviceMonConfDao deviceMonConfDao;
	@Resource
	private BakuDao  bakuDao;
	@Resource
	private DeviceConfigDao deviceConfigDao;
	@Resource
	private MetadataDeviceTypeDao metadataDeviceTypeDao;
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private RelDeviceGroupDao relDeviceGroupDao;
	@Resource
	private RelOperatorDeviceGroupDao relOperatorDeviceGroupDao;
	@Resource
	private MetadataLogicObjDao metadataLogicObjDao;
	@Resource
	private MetadataExtModelDao metadataExtModelDao;
	@Resource
	private MetaRunConfigDao metaRunConfigDao;
	@Resource
	private OperatorGroupDao operatorGroupDao;
	@Resource
	private ServiceGroupDao serviceGroupDao;
	@Resource
	private DeviceGroupDao deviceGroupDao;
	@Resource
	private AcsConfigDao acsConfigDao;
	@Resource
	private DeviceAcsLoginInfoDao deviceAcsLoginInfoDao;
	@Resource
	private RelOperatorGroupDao relOperatorGroupDao;
	@Resource
	private ReaderTypeDao readerTypeDao;
	@Resource
	private OperatorMaintenanceInfoDao operatorMaintenanceInfoDao;
	//type_idx list
	private Map<String,List<String>> readerTypeMap=new HashMap<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity bakupOnlyByLiraryIdx(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> m=JsonUtils.fromJson(req, Map.class);
			if(m.containsKey("tableName")  && m.containsKey("library_idx")){
				String talbeName=m.get("tableName").toString();
				String library_idx=m.get("library_idx").toString();
				if("reader_type".equals(talbeName)){
					List<Map<String,Object>> lists= commonDao.selectList("common.select", m);
					List<String> typeIdxArr=new ArrayList<>();
					for(Map<String,Object> readerType:lists){
						typeIdxArr.add(readerType.get("type_idx").toString());
					}
					readerTypeMap.put(library_idx, typeIdxArr);
					result.setResult(lists);
				}else if("operator_maintenance_info".equals(talbeName)){
					if(readerTypeMap.containsKey(library_idx)){
						List<String> typeIdxList=readerTypeMap.get(library_idx);
						if (typeIdxList==null || typeIdxList.size()<=0) {
							result.setResult(new ArrayList<>());
						}else{
							//condition
							m.put("condition", "maintenance_idx in ("+StringUtils.collectionToCommaDelimitedString(typeIdxList)+")");
							List<Map<String,Object>> lists= commonDao.selectList("common.selectCondition", m);
							result.setResult(lists);
						}
					}
				}else{
					/**
					 * 直接装成Map的话传出来的时间类型的数据会有点问题，显示长整形,注意数据库字段tinyint类型长度至少为2
					 */
					List<Map<String,Object>> lists= commonDao.selectList("common.select", m);
					result.setResult(lists);
				}
			}
			result.setState(true);
		}
		return result;
	}
	public static final String device_run_template="device_run_template";
	public static final String device_ext_template="device_ext_template";
	public static final String device_monitor_template="device_monitor_template";
	public static final String device_dbsync_template="device_dbsync_template";

	private static final String URL_GetChangedIDXByIdxNameAndLibraryInfo = "GetChangedIDXByIdxNameAndLibraryInfo";
	
	@Resource
	private DeviceConfigService deviceConfigService;
	
	/**
	 * 处理设备使用模板的情况，如果设备使用的是自定义数据则？已经处理过这里不需要处理。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity bakupBySpecalTableDevice(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> mLibInfo=JsonUtils.fromJson(req, Map.class);
			if(mLibInfo.containsKey("library_idx") && mLibInfo.containsKey("table_name")){
				String table_name=mLibInfo.get("table_name").toString();
				Integer library_idx=Integer.parseInt(mLibInfo.get("library_idx").toString());
				//需要查询出的表数据
				Map<String,Object> retMap=new HashMap<>();
				Map<String, Object> params=new HashMap<>();
				params.put("library_idx", library_idx);
				if(device_dbsync_template.equals(table_name)){
					List<DeviceDBSyncConfigEntity> deviceDBSyncConfigEntitys=deviceDBSyncConfDao.selectDeviceDBSyncModalByLibraryIdx(params);
					List<DeviceDBSyncTempEntity> deviceDBSyncTempEntitys=deviceDBSyncConfDao.seldbsyncTempModalByLibraryIdx(params);
					if(deviceDBSyncTempEntitys!=null && deviceDBSyncTempEntitys.size()>0){
						retMap.put("device_dbsync_template_config_list", deviceDBSyncConfigEntitys);
					}
					if(deviceDBSyncTempEntitys!=null && deviceDBSyncTempEntitys.size()>0){
						retMap.put("device_dbsync_template_list", deviceDBSyncTempEntitys);
					}
				}
				else if(device_ext_template.equals(table_name)){
					List<DeviceExtConfigEntity> deviceExtConfigEntitys=deviceExtConfDao.selectDeviceExtModalByLibraryIdx(params);
					List<DeviceExtTempEntity> deviceExtTempEntitys=deviceExtConfDao.selectDeviceExtTempModalByLibraryIdx(params);
					if(deviceExtConfigEntitys!=null && deviceExtConfigEntitys.size()>0){
						retMap.put("device_ext_template_config_list", deviceExtConfigEntitys);
					}
					if(deviceExtTempEntitys!=null && deviceExtTempEntitys.size()>0){
						retMap.put("device_ext_template_list", deviceExtTempEntitys);
					}
				}
				else if(device_monitor_template.equals(table_name)){
					List<DeviceMonConfigEntity> deviceMonConfigEntitys=deviceMonConfDao.selectDeviceMonModalByLibraryIdx(params);
					List<DeviceMonTempEntity> deviceMonTempEntitys=deviceMonConfDao.selectDeviceMonTempModalByLibraryIdx(params);
					if(deviceMonConfigEntitys!=null && deviceMonConfigEntitys.size()>0){
						retMap.put("device_monitor_template_config_list", deviceMonConfigEntitys);
					}
					if(deviceMonTempEntitys!=null && deviceMonTempEntitys.size()>0){
						retMap.put("device_monitor_template_list", deviceMonTempEntitys);
					}
				}
				else if(device_run_template.equals(table_name)){
					List<DeviceRunConfigEntity> deviceRunConfigEntitys=deviceRunConfDao.selectDeviceRunModalByLibraryIdx(params);
					List<DeviceRunTempEntity> deviceRunTempEntitys=deviceRunConfDao.selectDeviceRunTempModalByLibraryIdx(params);
					if(deviceRunConfigEntitys!=null && deviceRunConfigEntitys.size()>0){
						retMap.put("device_run_template_config_list", deviceRunConfigEntitys);
					}
					if(deviceRunTempEntitys!=null && deviceRunTempEntitys.size()>0){
						retMap.put("device_run_template_list", deviceRunTempEntitys);
					}
				}
				/////////////////////////////////////////////////////////
				result.setResult(retMap);
				result.setState(true);
				/////////////////////////////////////////////////////////
				
				
			}
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryBakDataInfo(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> m=JsonUtils.fromJson(req, Map.class);
			List<BakupDataEntity> bakupDataEntitys=bakuDao.queryBakDataInfo(m);
			if(bakupDataEntitys!=null && bakupDataEntitys.size()>0){
				result.setResult(bakupDataEntitys);
				result.setState(true);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryBakDataInfoByIdx(String req){
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> m=JsonUtils.fromJson(req, Map.class);
			BakupDataEntity bakupDataEntity=bakuDao.queryBakDataInfoByIdx(m);
			if(bakupDataEntity!=null ){
				result.setResult(bakupDataEntity);
				result.setState(true);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity updBakDataInfoByIdx(String req){
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String, Object> m=JsonUtils.fromJson(req, Map.class);
			int i=bakuDao.updBakDataInfoByIdx(m);
			if(i>=0){
				result.setState(true);
			}
		}
		return result;
	}
	
	@Override
	public ResultEntity insertBakDataInfo(String req){
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			BakupDataEntity m=JsonUtils.fromJson(req, BakupDataEntity.class);
			int i=bakuDao.insertBakDataInfo(m);
			if(i>=0){
				result.setState(true);
			}
		}
		return result;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity getTplIdxByLibraryIdx(String req) {
		ResultEntity result =new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> m=JsonUtils.fromJson(req, Map.class);
			Map<Object, Object> retMap=new HashMap<>();
			if(m.containsKey("library_idx")){
				List<Integer> monitorList=new ArrayList<>();
				List<Integer> runList=new ArrayList<>();
				List<Integer> extList=new ArrayList<>();
				List<Integer> dbsyncList=new ArrayList<>();
				Integer library_idx=Integer.parseInt(m.get("library_idx").toString());
				DeviceConfigEntity deviceConfigEntity=new DeviceConfigEntity();
				deviceConfigEntity.setLibrary_idx(library_idx);
				List<DeviceConfigEntity> deviceConfigEntitys=deviceConfigDao.queryDeviceConfigByLibraryIdx(deviceConfigEntity);
				if(!CollectionUtils.isEmpty(deviceConfigEntitys)){
					for(DeviceConfigEntity d:deviceConfigEntitys){
						dbsyncList.add(d.getDevice_dbsync_tpl_idx());
						extList.add(d.getDevice_ext_tpl_idx());
						runList.add(d.getDevice_run_tpl_idx());
						monitorList.add(d.getDevice_monitor_tpl_idx());
					}
					retMap.put("dbsyncList", dbsyncList);
					retMap.put("extList", extList);
					retMap.put("runList", runList);
					retMap.put("monitorList", monitorList);
					result.setState(true);
					result.setResult(retMap);
				}
			}
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity restoreDataByLibraryIdx(String req) {
		ResultEntity result=new ResultEntity();
		System.out.println("DB device restoreDataByLibraryIdx req--->"+req);
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> map=JsonUtils.fromJson(req, Map.class);
			if(map.containsKey("file_path") && map.containsKey("library_idx")){
				String file_path=map.get("file_path").toString();//传输备份文件到db层所在服务器
				String file_name=map.get("file_name").toString();
				String library_id=map.get("library_id").toString();
				Integer library_idx=Integer.parseInt(map.get("library_idx").toString());
				File bakupFile=recvFile(file_path,file_name);
				if(bakupFile!=null){
					if(bakupFile.exists() && bakupFile.isFile()){
						
						//從鑒權db層 獲取operator_idx變化關係
						Map<String,Object> param=new HashMap<>();
						param.put("library_idx", library_idx);
						param.put("library_id", library_id);
						param.put("idx_name", "operator_idx");
						Map<String,String> rq=new HashMap<>();
						rq.put("req", JsonUtils.toJson(param));
						String url=requestURLList.getRequestURL(URL_GetChangedIDXByIdxNameAndLibraryInfo);
						String resultStr=HttpClientUtil.doPostLongtime(url, rq, "UTF-8");
						System.out.println("restoreDataByLibraryIdx resultStr----->"+resultStr);
						//默認一定成功，不成功直接報錯,因為假設這個館一定會有operator_idx的變化。
						ResultEntity re=JsonUtils.fromJson(resultStr, ResultEntity.class);
						Map<String,Integer> operator_idx_map=null;
						if(re.getState() && "NO_DATA".equals(re.getMessage())){
							//没有数据,可能没有 operator 的情况，跳过
							operator_idx_map=new HashMap<>();
						}else{
							operator_idx_map=JsonUtils.fromJson(JsonUtils.toJson(re.getResult()),new TypeReference<Map<String,Integer>>() {});
						}
						//还原的话 特殊的表特殊处理了。
						XMLInputFactory factory = XMLInputFactory.newInstance();
						String defaultDBName="ssitcloud_device";
						String databaseName=null;
						String tablename=null;
						List<Map<String,Object>> rowList=new ArrayList<>();
						//这个会抛异常！
						String logFileName=logFilePath+library_id+"/"+file_name+"_DEVICE_"+System.currentTimeMillis()+".log";
						LogUtils.LogOnFile(logFileName, "<###################START###################>\n");
						deleteDataHandler(library_idx,logFileName);
						try {
							XMLStreamReader reader=factory.createXMLStreamReader(new FileInputStream(bakupFile));
							while(reader.hasNext()){
							    int type = reader.next();
				                //是否是开始节点，开始节点就是<>
				                if(type==XMLStreamReader.START_ELEMENT){
				                	String name=reader.getName().toString();
				                	if("database".equals(name)){
				                		databaseName=reader.getAttributeValue(0);
				                	}else if("table".equals(name)){
				                    	String attrName=reader.getAttributeName(0).toString();
				                    	if("tablename".equals(attrName)){
				                    		tablename=reader.getAttributeValue(0);
				                    	}
				                    }else if("row".equals(name)){
				                    	if(defaultDBName.equals(databaseName)){
				                    		String json=reader.getElementText();
					                    	Map<String, Object> rowMap=JsonUtils.fromJson(json, Map.class);
					                    	rowList.add(rowMap);
				                    	}
				                    }
				                    //是否是文本节点,开始节点和结束节点之间的内容
				                }else if(type==XMLStreamReader.CHARACTERS){
				                    //是否是结束节点,结束节点就是</>
				                }else if(type==XMLStreamReader.END_ELEMENT){
				                	String name=reader.getName().toString();
				                	if("table".equals(name)){// 获取rows数据结束
				                		if(defaultDBName.equals(databaseName)){
				                			LogUtils.LogOnFile(logFileName, "<#############TABLE:"+tablename+" INSERT_OPERATION##############>\n");
				                			insertDataHandler(rowList, tablename,library_idx,library_id,operator_idx_map,logFileName);
				                		}
				                		tablename=null;
				                		rowList.clear();
				                	}
				                	if("database".equals(name)){
				                		databaseName=null;
				                	}
				                }
							}
							result.setState(true);
                			LogUtils.LogOnFile(logFileName, "<###################END###################>\n");
    						System.out.println("~~~~~~~~~~~~~restore device end~~~~~~~~~~~~");
    						//更新一下还原时间
    						Map<String,Object> m=new HashMap<>();
    						m.put("restore_time", new Date());
    						m.put("file_path", file_path);
    						bakuDao.updBakDataInfoByFilePath(m);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (XMLStreamException e) {
							throw new RuntimeException(e);//controller catched and state is false
						}
					}
				}
			}
			
		}
		return result;
	}
	/**
	 * 
	 * 需要根据LibIdx删除的数据的表
	 */
	private static List<String> deleteByLibIdxArr=Arrays.asList(new String[]{
			"device_run_config",
			"device_monitor_config",
			"device_ext_config",
			"device_dbsync_config",
			
			"rel_operator_shelf_group",
			"rel_operator_service_group",
			"rel_operator_device_group",
			"rel_shelf_group",
			"rel_service_group",
			"rel_operator_group",
			"rel_device_group",
			
			"operator_group",
			"device_group",
			"device_config",
			"service_group",
			"device",
			
			"device_selfcheck_protocol_config",
			"device_acs_logininfo",
			"protocol_config_template",
			
			//"operator_maintenance_info",
			//"reader_type"
	});
	//
	//issue1: device_group 可能有其他馆在使用，删除报错
	//FCK
    //
	private boolean deleteDataHandler(Integer library_idx,String fileName){
		Map<String,Object> map=new HashMap<>();
		map.put("library_idx", library_idx);
		for(int i=0;i<deleteByLibIdxArr.size();i++){
			String tableName=deleteByLibIdxArr.get(i);
			map.put("tableName", tableName);
			if(tableName.equals("device_group")){
				Map<String,Object> params=new HashMap<>();
				params.put("library_idx", library_idx);
				List<RelOperatorDeviceGroupEntity> lists=relOperatorDeviceGroupDao.selectByDeviceGroupLibraryIdx(map);
				if(!CollectionUtils.isEmpty(lists)){
					//lists 的这几device_group_idx个是删不掉的
					List<Integer> idxList=new ArrayList<>();
					for(RelOperatorDeviceGroupEntity r:lists){
						idxList.add(r.getDevice_group_idx());
					}
					String idxRange=StringUtils.collectionToCommaDelimitedString(idxList);
					String srchSql="select * from device_group where library_idx="+library_idx+" and device_group_idx not in ("+idxRange+")";
					String condition= "delete from device_group where library_idx="+library_idx+" and device_group_idx not in ("+idxRange+")";
					List<Map<String,Object>> recordlist=commonDao.selectBySql(srchSql);
					LogUtils.LogOnFile(fileName,JsonUtils.toJson(recordlist)+" \n");
					commonDao.delete("common.deleteBySQL", condition);
					LogUtils.LogOnFile(fileName,condition+" \n");
				}else{
					String srchSql="select * from device_group where library_idx="+library_idx;
					String condition= "delete from device_group where library_idx="+library_idx;
					List<Map<String,Object>> recordlist=commonDao.selectBySql(srchSql);
					LogUtils.LogOnFile(fileName,JsonUtils.toJson(recordlist)+" \n");
					commonDao.delete("common.deleteBySQL", condition);
					LogUtils.LogOnFile(fileName,condition+" \n");
				}
			}else if("service_group".equals(tableName)){
				/**
				 * 有条件删除，则在插入的时候要判断数据是否存在
				 * 		SELECT rosg.* FROM rel_operator_service_group rosg 
						INNER JOIN service_group sg ON sg.`service_group_idx`=rosg.`service_group_idx`		
					WHERE sg.`library_idx`=16
				 */
				List<RelOperatorServiceGroupEntity>  relOperatorServiceGroups=relOperatorServiceGroupDao.selectByServGroupByLibraryIdx(map);
				if(!CollectionUtils.isEmpty(relOperatorServiceGroups)){
					List<Integer> idxList=new ArrayList<>();
					for(RelOperatorServiceGroupEntity r:relOperatorServiceGroups){
						idxList.add(r.getService_group_idx());
					}
					String idxListStr=StringUtils.collectionToCommaDelimitedString(idxList);
					String srchSql="select * from service_group where library_idx="+library_idx+" and service_group_idx not in ("+idxListStr+")";
					String condition= "delete from service_group where library_idx="+library_idx+" and service_group_idx not in ("+idxListStr+")";
					List<Map<String,Object>> recordlist=commonDao.selectBySql(srchSql);
					LogUtils.LogOnFile(fileName,JsonUtils.toJson(recordlist)+" \n");
				
					commonDao.delete("common.deleteBySQL", condition);
					LogUtils.LogOnFile(fileName,condition+" \n");
				}
			}else if("operator_group".equals(tableName)){
				List<RelOperatorServiceGroupEntity>  relOperatorServiceGroups=relOperatorServiceGroupDao.selectByOperGroupByLibraryIdx(map);
				if(!CollectionUtils.isEmpty(relOperatorServiceGroups)){
					List<Integer> idxList=new ArrayList<>();
					for(RelOperatorServiceGroupEntity r:relOperatorServiceGroups){
						idxList.add(r.getOperator_group_idx());
					}
					String idxArrStr=StringUtils.collectionToCommaDelimitedString(idxList);
					String srchSql="select * from operator_group where library_idx="+library_idx+" and  operator_group_idx not in ("+idxArrStr+")";
					String condition= "delete from operator_group where library_idx="+library_idx+" and  operator_group_idx not in ("+idxArrStr+")";
					List<Map<String,Object>> recordlist=commonDao.selectBySql(srchSql);
					LogUtils.LogOnFile(fileName,JsonUtils.toJson(recordlist)+" \n");
					commonDao.delete("common.deleteBySQL", condition);
					LogUtils.LogOnFile(fileName,condition+" \n");
				}
			}else{
				String srchSql="select * from "+tableName+" where library_idx="+library_idx;
				String sql="delete from "+tableName+" where library_idx="+library_idx;
				List<Map<String,Object>> recordlist=commonDao.selectBySql(srchSql);
				LogUtils.LogOnFile(fileName,JsonUtils.toJson(recordlist)+" \n");
				commonDao.delete("common.deleteBySQL", sql);
				LogUtils.LogOnFile(fileName,sql+" \n");
			}
		}
		return true;
	}
	private static Map<String,String> fileNameMap=new HashMap<>();
	//設備IDX
	private static Map<String,Map<Integer,Integer>> device_idx_map=new HashMap<>();
	//設備分組IDX
	private static Map<String,Map<Integer,Integer>> device_group_idx_map=new HashMap<>();
	//
	private static Map<String,Map<Integer,Integer>> operator_group_idx_map=new HashMap<>();

	private static Map<String,Map<Integer,Integer>> service_group_idx_map=new HashMap<>();

	private static Map<String,Map<Integer,Integer>> protocol_tpl_idx_map=new HashMap<>();

	private static Map<String,Map<Integer,Integer>> type_idx_map=new HashMap<>();
	
	public static final String logFilePath="/usr/restoreLog/";
	/**
	 * TIPS：
	 * 1.还原table 的顺序以还原的顺序为准，如果备份文件备份的表顺序出现问题则，则还原时抛出异常不允许还原。
	 * 2.执行该函数时 该删除的表已经删除
	 * 
	 * @param rows
	 * @param tableName
	 * @param library_idx
	 * @param operator_idx_map 
	 * @return
	 */
	/**
	 * @param rows
	 * @param tableName
	 * @param library_idx
	 * @param library_id
	 * @param operator_idx_map
	 * @return
	 */
	private boolean  insertDataHandler(List<Map<String,Object>> rows,String tableName,Integer library_idx, String library_id,Map<String, Integer> operator_idx_map,String logFileName){
		if(fileNameMap.containsKey(library_id)){
			String logFile=fileNameMap.get(library_id);
			if(!logFile.equals(logFileName)){
				fileNameMap.put(library_id, logFileName);
			}else{
				logFileName=logFile;
			}
		}else{
			fileNameMap.put(library_id, logFileName);
		}
		Map<String,Object> param=new HashMap<>();
		param.put("library_idx", library_idx);
		if("metadata_devicetype".equals(tableName)){
			for(Map<String,Object> m:rows){
				MetadataDeviceTypeEntity metadataDeviceTypeEntity=new MetadataDeviceTypeEntity();
				metadataDeviceTypeEntity.setMeta_devicetype_idx(Integer.parseInt(m.get("meta_devicetype_idx").toString()));
				MetadataDeviceTypeEntity md=metadataDeviceTypeDao.selOneByIdx(metadataDeviceTypeEntity);
				if(md!=null){//表示存在，并且有引用的，不能删除，只能修改
					int i=metadataDeviceTypeDao.updateByMap(m);
					if(i<=0){
						throw new RuntimeException("metadata_devicetype_updateByMap_failed");
					}
					LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}else{//根據IDX查不到？
					int i=metadataDeviceTypeDao.insertWithIdx(m);
					if(i<=0){
						throw new RuntimeException("metadata_devicetype insert failed");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}
			}
		}else 
		////先查询存不存在，不存在则插入，存在的修改,不能删除
		if("device_dbsync_template".equals(tableName)){
			for(Map<String,Object> m:rows){
				Integer device_tpl_idx=(Integer)m.get("device_tpl_idx");
				DeviceDBSyncTempEntity ddJson=JsonUtils.fromJson(JsonUtils.toJson(m), DeviceDBSyncTempEntity.class);
				DeviceDBSyncTempEntity dd=new DeviceDBSyncTempEntity();
				dd.setDevice_tpl_idx(device_tpl_idx);
				DeviceDBSyncTempEntity srchDbsync=deviceDBSyncConfDao.selectDeviceDBSyncTemp(dd);
				if(srchDbsync!=null){
					if(deviceDBSyncConfDao.updateDeviceDBSyncTemp(ddJson)<=0){
						throw new RuntimeException("Recover device_dbsync_template table Update_Fail");
					}
					LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}else{
					if(deviceDBSyncConfDao.insertDeviceDBSyncTempWithIdx(ddJson)<=0){
						throw new RuntimeException("Recover device_dbsync_template table Insert_Fail");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}
			}
		}else 
		if("device_monitor_template".equals(tableName)){
			for(Map<String,Object> m:rows){
				Integer device_tpl_idx=(Integer)m.get("device_mon_tpl_idx");
				DeviceMonTempEntity ddJson=JsonUtils.fromJson(JsonUtils.toJson(m), DeviceMonTempEntity.class);
				DeviceMonTempEntity dd=new DeviceMonTempEntity();
				dd.setDevice_mon_tpl_idx(device_tpl_idx);
				List<DeviceMonTempEntity> srchDeviceMonTemps=deviceMonConfDao.selectList(dd);
				if(!CollectionUtils.isEmpty(srchDeviceMonTemps)){
					if(deviceMonConfDao.updateDeviceMonTemp(ddJson)<=0){
						throw new RuntimeException("Recover device_monitor_template table Update_Fail");
					}
					LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");

				}else{
					if(deviceMonConfDao.insertDeviceMonTempWithIdx(ddJson)<=0){
						throw new RuntimeException("Recover device_monitor_template table Insert_Fail");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}
			}
		}else 
		if("device_run_template".equals(tableName)){
			for(Map<String,Object> m:rows){
				Integer device_tpl_idx=(Integer)m.get("device_tpl_idx");
				DeviceRunTempEntity ddJson=JsonUtils.fromJson(JsonUtils.toJson(m), DeviceRunTempEntity.class);
				DeviceRunTempEntity dd=new DeviceRunTempEntity();
				dd.setDevice_tpl_idx(device_tpl_idx);
				List<DeviceRunTempEntity> srchDeviceRunTemps=deviceRunConfDao.selectList(dd);
				if(!CollectionUtils.isEmpty(srchDeviceRunTemps)){
					if(deviceRunConfDao.updateDeviceRunTemp(ddJson)<=0){
						throw new RuntimeException("Recover device_run_template table Update_Fail");
					}
					LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}else{
					if(deviceRunConfDao.insertDeviceRunTempWithIdx(ddJson)<=0){
						throw new RuntimeException("Recover device_run_template table Insert_Fail");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}
			}
		}else 
		if("device_ext_template".equals(tableName)){
			Map<String,Object> params=new HashMap<>();
			params.put("library_idx", library_idx);
			for(Map<String,Object> m:rows){
				Integer device_tpl_idx=(Integer)m.get("device_tpl_idx");
				DeviceExtTempEntity ddJson=JsonUtils.convertMapToObject(m, DeviceExtTempEntity.class);
				DeviceExtTempEntity dd=new DeviceExtTempEntity();
				dd.setDevice_tpl_idx(device_tpl_idx);
				List<DeviceExtTempEntity> srchDeviceExtTemps=deviceExtConfDao.selectList(dd);
				if(!CollectionUtils.isEmpty(srchDeviceExtTemps)){
					if(deviceExtConfDao.updateDeviceExtTemp(ddJson)<=0){
						throw new RuntimeException("Recover device_ext_template table Update_Fail");
					}
					LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}else{
					if(deviceExtConfDao.insertDeviceExtTempWithIdx(ddJson)<=0){
						throw new RuntimeException("Recover device_ext_template table Insert_Fail");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}
			}
		}else 
		if("metadata_logic_obj".equals(tableName)){
			for(Map<String,Object> m:rows){
				MetadataLogicObjEntity metadataLogicObjEntity=new MetadataLogicObjEntity();
				metadataLogicObjEntity.setMeta_log_obj_idx(Integer.parseInt(m.get("meta_log_obj_idx").toString()));
				MetadataLogicObjEntity md=metadataLogicObjDao.selOneByIdx(metadataLogicObjEntity);
				if(md!=null){//表示存在，并且有引用的，不能删除，只能修改
					int i=metadataLogicObjDao.updateByMap(m);
					if(i<=0){
						throw new RuntimeException("metadata_logic_obj updateByMap_failed");
					}
					LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}else{
					if(metadataLogicObjDao.insertMapWithIdx(m)<=0){
						throw new RuntimeException("metadata_logic_obj insert failed");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}
			}
		}else 
		if("metadata_ext_model".equals(tableName)){
			for(Map<String,Object> m:rows){
				MetadataExtModelEntity metadataExtModelEntity=new MetadataExtModelEntity();
				metadataExtModelEntity.setMeta_ext_idx(Integer.parseInt(m.get("meta_ext_idx").toString()));
				MetadataExtModelEntity md=metadataExtModelDao.selOneByIdx(metadataExtModelEntity);
				if(md!=null){//表示存在，并且有引用的，不能删除，只能修改
					int i=metadataExtModelDao.updateByMap(m);
					if(i<=0){
						throw new RuntimeException("metadata_ext_model updateByMap_failed");
					}
					LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");

				}else{
					if(metadataExtModelDao.insertMapWithIdx(m)<=0){
						throw new RuntimeException("metadata_ext_model insert failed");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}
			}
		}else 
		if("metadata_run_config".equals(tableName)){
			for(Map<String,Object> m:rows){
				MetaRunConfigEntity metaRunConfig=new MetaRunConfigEntity();
				metaRunConfig.setMeta_run_cfg_idx(Integer.parseInt(m.get("meta_run_cfg_idx").toString()));
				MetaRunConfigEntity md=metaRunConfigDao.selOneByIdx(metaRunConfig);
				if(md!=null){//表示存在，并且有引用的，不能删除，只能修改
					int i=metaRunConfigDao.updateByMap(m);
					if(i<=0){
						throw new RuntimeException("metadata_logic_obj update failed");
					}
					LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}else{
					if(metaRunConfigDao.insertMapWithIdx(m)<=0){
						throw new RuntimeException("metadata_logic_obj insert failed");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}
			}
		}else if("device".equals(tableName)){
			Map<Integer,Integer> map=null;
			if(device_idx_map.containsKey(library_id)){
				map=device_idx_map.get(library_id);
				map.clear();
			}else{
				map=new HashMap<>();
				device_idx_map.put(library_id, map);
			}
			for(Map<String,Object> m:rows){
				DeviceEntity device=JsonUtils.fromJson(JsonUtils.toJson(m), DeviceEntity.class);
				Integer old_idx=device.getDevice_idx();
				int i=deviceDao.insert(device);
				Integer new_idx=device.getDevice_idx();
				if(i<=0){
					throw new RuntimeException("device insert failed");
				}
				LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");

				map.put(old_idx, new_idx);
			}
		}else if("operator_group".equals(tableName)){
			//operator_group
			Map<Integer,Integer> map=null;
			if(operator_group_idx_map.containsKey(library_id)){
				map=operator_group_idx_map.get(library_id);
				map.clear();
			}else{
				map=new HashMap<>();
				operator_group_idx_map.put(library_id, map);
			}
			for(Map<String,Object> m:rows){
				OperatorGroupEntity oge=JsonUtils.fromJson(JsonUtils.toJson(m), OperatorGroupEntity.class);
				String operator_idx=m.get("operator_idx").toString();
				if(operator_idx_map.containsKey(operator_idx)){
					Integer new_idx=operator_idx_map.get(operator_idx);
					oge.setOperator_idx(new_idx);//更新operator_idx
				}
				Integer old_idx=oge.getOperator_group_idx();
				int i=0;
				try {
					i=operatorGroupDao.insert(oge);//注意这个由于某些组别的馆也在用那么有可能有部分数据没有删除的，那么只能插入部分数据
				} catch (org.springframework.dao.DuplicateKeyException e) {
					String msg=e.getCause().getMessage();
					if(org.apache.commons.lang.StringUtils.contains(msg, "Duplicate entry")
							){
						LogUtils.LogOnFile(logFileName, "重复数据---->"+JsonUtils.toJson(oge));
						i=1;
					}else{
						throw new RuntimeException("operator_group insert failed");
					}
				}
				if(i<=0){
					throw new RuntimeException("operator_group insert failed");
				}
				LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");

				Integer new_idx=oge.getOperator_group_idx();
				map.put(old_idx, new_idx);
			}
		}else if("service_group".equals(tableName)){
			Map<Integer,Integer> map=null;
			if(service_group_idx_map.containsKey(library_id)){
				map=service_group_idx_map.get(library_id);
				map.clear();
			}else{
				map=new HashMap<>();
				service_group_idx_map.put(library_id, map);
			}
			ServiceGroupEntity serviceGroup=new ServiceGroupEntity();
			for(Map<String,Object> m:rows){
				ServiceGroupEntity sg=JsonUtils.fromJson(JsonUtils.toJson(m), ServiceGroupEntity.class);
				Integer old_idx=sg.getService_group_idx();
				serviceGroup.setService_group_idx(old_idx);
				List<ServiceGroupEntity> serviceGroups=serviceGroupDao.selectByidx(serviceGroup);
				if(!CollectionUtils.isEmpty(serviceGroups)){
					continue;
				}
				int i=serviceGroupDao.insert(sg);
				if(i<=0){
					throw new RuntimeException("service_group insert failed");
				}
				LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");

				Integer new_idx=sg.getService_group_idx();
				map.put(old_idx, new_idx);
			}
		}else if("device_group".equals(tableName)){
			Map<Integer,Integer> map=null;
			if(device_group_idx_map.containsKey(library_id)){
				map=device_group_idx_map.get(library_id);
				map.clear();
			}else{
				map=new HashMap<>();
				device_group_idx_map.put(library_id, map);
			}
			for(Map<String,Object> m:rows){
				DeviceGroupEntity dg=JsonUtils.fromJson(JsonUtils.toJson(m), DeviceGroupEntity.class);
				DeviceGroupEntity d=	deviceGroupDao.selectByDeviceGroupIdx(dg);
				if(d!=null){
					int i=deviceGroupDao.update(dg);
					if(i<=0){
						throw new RuntimeException("device_group update failed");
					}
					LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}else{
					int i=deviceGroupDao.insert(dg);
					if(i<=0){
						throw new RuntimeException("device_group insert failed");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");

				}
			}
		}else if("protocol_config_template".equals(tableName)){
			Map<Integer,Integer> map=null;
			if(protocol_tpl_idx_map.containsKey(library_id)){
				map=protocol_tpl_idx_map.get(library_id);
				map.clear();
			}else{
				map=new HashMap<>();
				protocol_tpl_idx_map.put(library_id, map);
			}
			for(Map<String,Object> m:rows){
				ProtocolConfigTemplateEntity protocolConfigTemplate=JsonUtils.fromJson(JsonUtils.toJson(m), ProtocolConfigTemplateEntity.class);
				//之前已经删除，直接插入
				Integer old_idx=protocolConfigTemplate.getProtocol_tpl_idx();
				//p.setProtocol_tpl_idx(old_idx);
				//List<ProtocolConfigTemplateEntity> pps=acsConfigDao.queryProtocolConfigTemplateExactly(p);
				if(acsConfigDao.addProtocolConfigTemplate(protocolConfigTemplate)<=0){
					throw new RuntimeException("protocol_config_template insert failed");
				}
				LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				Integer new_idx=protocolConfigTemplate.getProtocol_tpl_idx();
				map.put(old_idx, new_idx);
			}
		}else if("rel_operator_group".equals(tableName)){
			for(Map<String,Object> m:rows){//要还原的数据
				RelOperatorGroupEntity relOperatorGroupEntity=JsonUtils.fromJson(JsonUtils.toJson(m), RelOperatorGroupEntity.class);
				Integer old_operator_idx=relOperatorGroupEntity.getOperator_idx();
				//boolean contains=operator_idx_map.containsKey(old_operator_idx);
				Integer new_operator_idx=operator_idx_map.get(old_operator_idx.toString());
				if(new_operator_idx!=null){
					relOperatorGroupEntity.setOperator_idx(new_operator_idx);
					int i=relOperatorGroupDao.insert(relOperatorGroupEntity);
					if(i<=0){
						throw new RuntimeException("rel_operator_group insert failed");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(m)+"\n");
				}else{
					//pass
				}
			}
		}else if("reader_type".equals(tableName)){
			//在这里 删除 维护卡 和 用户关系表数据 特殊处理
			//删除所有
			List<Integer> deletelist=new ArrayList<>();
			//for(Entry e:operator_idx_map.entrySet()){
				//Integer old_idx=Integer.parseInt(e.getKey().toString());//operator_idx
				//Integer new_idx=e.getValue();
				//deletelist.add(old_idx);
			//}
			ReaderTypeEntity sreaderType=new ReaderTypeEntity();
			sreaderType.setLibrary_idx(library_idx);
			List<ReaderTypeEntity> readerTypes=readerTypeDao.select(sreaderType);
			if(!CollectionUtils.isEmpty(readerTypes)){
				for(ReaderTypeEntity r:readerTypes){
					if(!deletelist.contains(r.getType_idx())){
						deletelist.add(r.getType_idx());
					}
				}
			}
			String cond=StringUtils.collectionToCommaDelimitedString(deletelist);
			//删除不干净
			String sql="delete from operator_maintenance_info where maintenance_idx in ("+cond+")";
			LogUtils.LogOnFile(logFileName, sql+"\n");
			commonDao.delete("common.deleteBySQL",sql);
			ReaderTypeEntity readerTypeEntity=new ReaderTypeEntity();
			readerTypeEntity.setLibrary_idx(library_idx);
			int d=readerTypeDao.deleteByCon(readerTypeEntity);
			LogUtils.LogOnFile(logFileName, "reader_type 删除 library_idx="+library_idx+" "+d+"条\n");
			
			/*********************************************************/
			Map<Integer,Integer> map=null;
			if(type_idx_map.containsKey(library_id)){
				map=type_idx_map.get(library_id);
				map.clear();
			}else{
				map=new HashMap<>();
				type_idx_map.put(library_id, map);
			}
			for(Map<String,Object> m:rows){
				ReaderTypeEntity readerType=JsonUtils.fromJson(JsonUtils.toJson(m), ReaderTypeEntity.class);
				Integer old_type_idx=readerType.getType_idx();
				int i=readerTypeDao.insert(readerType);
				if(i<=0){
					throw new RuntimeException("reader_type insert failed");
				}
				Integer new_type_Idx=readerType.getType_idx();
				map.put(old_type_idx, new_type_Idx);
				LogUtils.LogOnFile(logFileName, "還原新增數據：reader_type--->"+JsonUtils.toJson(readerType)+"\n");
			}
		}else if("operator_maintenance_info".equals(tableName)){//在reader_type 之后插入的数据
			Map<Integer,Integer> map=type_idx_map.get(library_id);
			for(Map<String,Object> m:rows){
				OperatorMaintenanceInfoEntity operatorMaintenanceInfo=JsonUtils.fromJson(JsonUtils.toJson(m), OperatorMaintenanceInfoEntity.class);
				Integer operator_idx=operatorMaintenanceInfo.getOperator_idx();
				Integer old_type_Idx=operatorMaintenanceInfo.getMaintenance_idx();
				if(operator_idx_map.containsKey(operator_idx.toString())){
					Integer new_idx=operator_idx_map.get(operator_idx);
					operatorMaintenanceInfo.setOperator_idx(new_idx);
				}
				if(map.containsKey(old_type_Idx)){
					Integer new_type_idx=map.get(old_type_Idx);
					operatorMaintenanceInfo.setMaintenance_idx(new_type_idx);
				}
				int i=operatorMaintenanceInfoDao.addMaintenance(operatorMaintenanceInfo);
				if(i<=0){
					throw new RuntimeException("operator_maintenance_info insert failed");
				}
				LogUtils.LogOnFile(logFileName, "operator_maintenance_info--->"+JsonUtils.toJson(operatorMaintenanceInfo)+"\n");
			}
		}
		else if("device_acs_logininfo".equals(tableName)){
			//
			Map<Integer,Integer> map=protocol_tpl_idx_map.get(library_id);
			List<DeviceAcsLoginInfoEntity> arr=new ArrayList<>();
			for(Map<String,Object> m:rows){
				DeviceAcsLoginInfoEntity deviceAcsLoginInfo=JsonUtils.fromJson(JsonUtils.toJson(m),DeviceAcsLoginInfoEntity.class);
				Integer old_idx=deviceAcsLoginInfo.getProtocol_tpl_idx();
				//if(map.containsKey(old_idx)){
					//则新增
					Integer protocol_tpl_idx=map.get(old_idx);
					deviceAcsLoginInfo.setProtocol_tpl_idx(protocol_tpl_idx);
					arr.add(deviceAcsLoginInfo);
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(deviceAcsLoginInfo)+"\n");
				//}else{
					//不存在的情况下插入会报错，正常情况下不可能出现这样的数据
					//LogUtils.error("device_acs_logininfo error data--->"+JsonUtils.toJson(deviceAcsLoginInfo));
				//}
			}
			if(arr.size()>0){
				if(deviceAcsLoginInfoDao.insertAcsInfoBatch(arr)!=arr.size()){
					throw new RuntimeException("device_acs_logininfo insert failed");
				}
			}
		}else if("device_selfcheck_protocol_config".equals(tableName)){
			Map<Integer,Integer> map=protocol_tpl_idx_map.get(library_id);
			List<ACSProtocolEntity> aCSProtocolArr=new ArrayList<>();
			for(Map<String,Object> m:rows){
				ACSProtocolEntity aCSProtocol=JsonUtils.fromJson(JsonUtils.toJson(m), ACSProtocolEntity.class);
				Integer old_idx=aCSProtocol.getProtocol_idx();
				//if(map.containsKey(old_idx)){
					Integer new_idx=map.get(old_idx);
					aCSProtocol.setProtocol_idx(new_idx);
					aCSProtocolArr.add(aCSProtocol);
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(aCSProtocol)+"\n");
				//}else{
					//正常情况下rows old_idx 一定存在 map中
				//}
			}
			if(aCSProtocolArr.size()>0){
				if(acsConfigDao.addProtocolConfigEx1Batch(aCSProtocolArr)!=aCSProtocolArr.size()){
					throw new RuntimeException("device_acs_logininfo insert failed");
				}
			}
		}else if("device_monitor_config".equals(tableName)){
			Map<Integer,Integer> device_idx_m=device_idx_map.get(library_id);
			for(Map<String,Object> m:rows){
				DeviceMonConfigEntity deviceMonConfig=JsonUtils.fromJson(JsonUtils.toJson(m), DeviceMonConfigEntity.class);
				//自定義模板數據
				if(deviceMonConfig.getDevice_ext_type()==DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_DATA){
					if(device_idx_m.containsKey(deviceMonConfig.getDevice_mon_tpl_idx())){
						deviceMonConfig.setDevice_mon_tpl_idx(device_idx_m.get(deviceMonConfig.getDevice_mon_tpl_idx()));
					}
					int i=deviceMonConfDao.insertDeviceMonConfig(deviceMonConfig);
					if(i<=0){
						throw new RuntimeException("device_monitor_config insert failed");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(deviceMonConfig)+"\n");
				//模板數據？
				}else if(deviceMonConfig.getDevice_ext_type()==DeviceMonConfigEntity.DEVICE_TPL_TYPE_IS_MODEL){
					//查詢？不存在插入，存在則直接修改
					DeviceMonConfigEntity deviceMon=new DeviceMonConfigEntity();
					deviceMon.setDevice_ext_type(deviceMonConfig.getDevice_ext_type());
					deviceMon.setDevice_mon_tpl_idx(deviceMonConfig.getDevice_mon_tpl_idx());
					deviceMon.setLibrary_idx(0);
					List<DeviceMonConfigEntity> lists=deviceMonConfDao.setDeviceMonConfigExactly(deviceMon);
					if(CollectionUtils.isEmpty(lists)){
						int i=deviceMonConfDao.insertDeviceMonConfig(deviceMonConfig);
						if(i<=0){
							throw new RuntimeException("device_monitor_config insert failed");
						}
						LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(deviceMonConfig)+"\n");
					}else{
						if(deviceMonConfDao.updateDeviceMonConfig(deviceMonConfig)<=0){
							throw new RuntimeException("device_monitor_config update failed");
						}
						LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(deviceMonConfig)+"\n");
					}
				}
			}
		}else if("device_ext_config".equals(tableName)){
			Map<Integer,Integer> device_idx_m=device_idx_map.get(library_id);
			for(Map<String,Object> m:rows){
				DeviceExtConfigEntity deviceExtConfig=JsonUtils.fromJson(JsonUtils.toJson(m), DeviceExtConfigEntity.class);
				//自定義模板數據
				if(deviceExtConfig.getDevice_tpl_type()==DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_DATA){
					if(device_idx_m.containsKey(deviceExtConfig.getDevice_ext_id())){
						deviceExtConfig.setDevice_ext_id(device_idx_m.get(deviceExtConfig.getDevice_ext_id()));
					}
					int i=deviceExtConfDao.insertDeviceExtConfig(deviceExtConfig);
					if(i<=0){
						throw new RuntimeException("device_ext_config insert failed");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(deviceExtConfig));
				//模板數據？
				}else if(deviceExtConfig.getDevice_tpl_type()==DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_MODEL){
					//查詢？不存在插入，存在則直接修改
					DeviceExtConfigEntity deviceExt=new DeviceExtConfigEntity();
					deviceExt.setDevice_tpl_type(DeviceExtConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
					deviceExt.setDevice_ext_id(deviceExtConfig.getDevice_ext_id());
					deviceExt.setLibrary_idx(0);
					List<DeviceExtConfigEntity> lists=deviceExtConfDao.selectList(deviceExtConfig);
					if(CollectionUtils.isEmpty(lists)){
						int i=deviceExtConfDao.insertDeviceExtConfig(deviceExtConfig);
						if(i<=0){
							throw new RuntimeException("device_ext_config insert failed");
						}
						LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(deviceExtConfig)+"\n");
					}else{
						if(deviceExtConfDao.updateDeviceExtConfig(deviceExtConfig)<=0){
							throw new RuntimeException("device_ext_config update failed");
						}
						LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(deviceExtConfig)+"\n");
					}
				}
			}
		}else if("device_run_config".equals(tableName)){
			Map<Integer,Integer> device_idx_m=device_idx_map.get(library_id);
			for(Map<String,Object> m:rows){
				DeviceRunConfigEntity deviceRunConfig=JsonUtils.fromJson(JsonUtils.toJson(m), DeviceRunConfigEntity.class);
				if(deviceRunConfig.getDevice_tpl_type()==DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_DATA){
					if(device_idx_m.containsKey(deviceRunConfig.getDevice_run_id())){
						deviceRunConfig.setDevice_run_id(device_idx_m.get(deviceRunConfig.getDevice_run_id()));
					}
					int i=deviceRunConfDao.insertDeviceRunConfig(deviceRunConfig);
					if(i<=0){
						throw new RuntimeException("device_run_config insert failed");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(deviceRunConfig)+"\n");

				}else if(deviceRunConfig.getDevice_tpl_type()==DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_MODEL){
					DeviceRunConfigEntity deviceRun=new DeviceRunConfigEntity();
					deviceRun.setDevice_run_id(deviceRunConfig.getDevice_run_id());
					deviceRun.setLibrary_idx(0);
					deviceRun.setDevice_tpl_type(DeviceRunConfigEntity.DEVICE_TPL_TYPE_IS_MODEL);
					List<DeviceRunConfigEntity> deviceRunConfigs=deviceRunConfDao.selectList(deviceRun);
					if(CollectionUtils.isEmpty(deviceRunConfigs)){
						if(deviceRunConfDao.insertDeviceRunConfig(deviceRunConfig)<=0){
							throw new RuntimeException("device_run_config insert failed");
						}
						LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(deviceRunConfig)+"\n");
					}else{
						if(deviceRunConfDao.updateDeviceRunConfig(deviceRunConfig)<=0){
							throw new RuntimeException("device_run_config update failed");
						}
						LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(deviceRunConfig)+"\n");

					}
				}
			}
		}else if("device_dbsync_config".equals(tableName)){
			Map<Integer,Integer> device_idx_m=device_idx_map.get(library_id);
			for(Map<String,Object> m:rows){
				DeviceDBSyncConfigEntity deviceDBSyncConfig=JsonUtils.fromJson(JsonUtils.toJson(m), DeviceDBSyncConfigEntity.class);
				if(deviceDBSyncConfig.getDevice_tpl_type()==DeviceDBSyncConfigEntity.IS_NOT_MODEL){
					if(device_idx_m.containsKey(deviceDBSyncConfig.getDevice_dbsync_id())){
						deviceDBSyncConfig.setDevice_dbsync_id(device_idx_m.get(deviceDBSyncConfig.getDevice_dbsync_id()));
					}
					if(deviceDBSyncConfDao.insertDeviceDBSyncConf(deviceDBSyncConfig)<=0){
						throw new RuntimeException("device_dbsync_config insert failed");
					}
					LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(deviceDBSyncConfig)+"\n");

				}else if(deviceDBSyncConfig.getDevice_tpl_type()==DeviceDBSyncConfigEntity.IS_MODEL){
					DeviceDBSyncConfigEntity deviceDBSync=new DeviceDBSyncConfigEntity();
					deviceDBSync.setLibrary_idx(0);
					deviceDBSync.setDevice_tpl_type(DeviceDBSyncConfigEntity.IS_MODEL);
					deviceDBSync.setDevice_dbsync_id(deviceDBSyncConfig.getDevice_dbsync_id());
					List<DeviceDBSyncConfigEntity> deviceDBSyncConfigs=deviceDBSyncConfDao.selectDeviceDBSyncConfig(deviceDBSync);
					if(CollectionUtils.isEmpty(deviceDBSyncConfigs)){
						if(deviceDBSyncConfDao.insertDeviceDBSyncConf(deviceDBSyncConfig)<=0){
							throw new RuntimeException("device_dbsync_config insert failed");
						}
						LogUtils.LogOnFile(logFileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(deviceDBSyncConfig)+"\n");
					}else{
						if(deviceDBSyncConfDao.updateDeviceDBSyncConf(deviceDBSyncConfig)<=0){
							throw new RuntimeException("device_dbsync_config update failed");
						}
						LogUtils.LogOnFile(logFileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(deviceDBSyncConfig)+"\n");
					}
				}
			}
		}
		//组装sql语句  能通过library_idx删除和备份的 //模板中的数据不在这里插入 
		else if(deleteByLibIdxArr.contains(tableName)){
			Map<String,Object> map=new HashMap<>();
			map.put("tableName", tableName);
			List<Map<String,Object>> lists=commonDao.selectList("common.getColumsByTable", map);
			StringBuilder sb=new StringBuilder("insert into ").append(tableName);
			String fieldsVal="";
			StringBuilder values=new StringBuilder("(");
			Map<Integer,Integer> device_idx_m=device_idx_map.get(library_id);
			Map<Integer,Integer> service_group_idx_m=service_group_idx_map.get(library_id);
			Map<Integer,Integer> operator_group_idx_m=operator_group_idx_map.get(library_id);
			Map<Integer,Integer> device_group_idx_m=device_group_idx_map.get(library_id);
			for(Map<String,Object> m:rows){
				StringBuilder fields=new StringBuilder("(");
				for(Map<String,Object> mm:lists){
					String fieldname=mm.get("Field").toString();
					String keyType=mm.get("Key").toString();//
					String fieldType=mm.get("Type").toString();
					if(keyType.equals("PRI")){//去除主键操作
						continue;
					}
					if(fieldname.equals("operator_idx")){
						Integer operator_idx=(Integer)m.get("operator_idx");
						if(operator_idx_map.containsKey(operator_idx.toString())){
							Integer new_idx=operator_idx_map.get(operator_idx.toString());
							m.put("operator_idx", new_idx);
						}
					}else if(fieldname.equals("device_idx")){
						Integer old_idx=(Integer)m.get("device_idx");
						if(device_idx_m.containsKey(old_idx)){
							m.put("device_idx", device_idx_m.get(old_idx));//換上新的idx value
						}
					}else if(fieldname.equals("service_group_idx")){
						Integer old_idx=(Integer)m.get("service_group_idx");
						if(service_group_idx_m.containsKey(old_idx)){
							m.put("service_group_idx", service_group_idx_m.get(old_idx));//換上新的idx value
						}
					}else if(fieldname.equals("operator_group_idx")){
						Integer old_idx=(Integer)m.get("operator_group_idx");
						if(operator_group_idx_m.containsKey(old_idx)){
							m.put("operator_group_idx", operator_group_idx_m.get(old_idx));//換上新的idx value
						}
					}else if(fieldname.equals("device_group_idx")){
						Integer old_idx=(Integer)m.get("device_group_idx");
						if(device_group_idx_m.containsKey(old_idx)){
							m.put("device_group_idx", device_group_idx_m.get(old_idx));//換上新的idx value
						}
					}
					Object v=m.get(fieldname);
					fields.append(fieldname).append(",");
					if(v instanceof String){
						values.append("'").append(v.toString()).append("',");
					}else if(v instanceof Integer){
						values.append((Integer)v).append(",");
					}else if(v instanceof Long){
						if("timestamp".equals(fieldType)){
							values.append("'").append((Long)v).append("',");
						}else{
							values.append((Long)v).append(",");
						}
					}else if(v instanceof Timestamp){
						values.append("'").append((Timestamp)v).append("',");
					}else if(v instanceof Double){
						values.append((Double)v).append(",");
					}else if(v instanceof Date){
						values.append("'").append((Date)v).append("',");
					}else if(v==null){
						values.append("NULL").append(",");
					}
				}
				fieldsVal=fields.deleteCharAt(fields.length()-1).append(")").toString();
				values.deleteCharAt(values.length()-1).append("),(");
			}
			if(rows!=null && rows.size()>0){
				sb.append(fieldsVal).append(" values ").append(values.deleteCharAt(values.length()-1).deleteCharAt(values.length()-1));
				LogUtils.LogOnFile(logFileName, "[_SQL_]："+sb.toString()+"\n");
				int i=commonDao.insertBySql(sb.toString());
				if(i<=0){
					throw new RuntimeException("TABLE_INSERT_FAILED|"+tableName);
				}
			}
		}
		return true;
	}
	
	private static String GetBusUrl(){
		String developer_model=PropertiesUtil.getConfigPropValueAsText("developer_model");
		if("true".equals(developer_model)){
			return PropertiesUtil.getConfigPropValueAsText("cloudbusinessservice_device_url_developer");
		}
		if("false".equals(developer_model)){
			return PropertiesUtil.getConfigPropValueAsText("cloudbusinessservice_device_url");
		}
		return PropertiesUtil.getConfigPropValueAsText("cloudbusinessservice_device_url");	
	}
	private static File recvFile(String filePath,String file_name){
		//如果在同一台服务器上感觉有问题，不好测试，则修改一下路径
    	String path=StringUtils.delete(filePath, file_name);
    	String fix="db";
		File file = new File(path+File.separator+fix);
		File filep = new File(path);
		if(!filep.exists()){
		   if(!filep.mkdirs()){
			   throw new RuntimeException("创建路径失败");
		   }
	    }
	    if(!file.exists()){
		  boolean isMk=file.mkdir();
		  if(!isMk) throw new RuntimeException("创建文件夹失败");
	    }
	    //需要保存的地址
	    File relFile=new File(path+File.separator+fix+File.separator+file_name);
	    if(relFile.exists()){
		    System.out.println("^^^^^^^^ relFile 存在 ^^^^^^^^");
		    //存在则,则直接返回供下载
		    return relFile;
	    }
	    
		String url=GetBusUrl();
		String ip=StringUtils.delete(url.split(":")[1], "//");
		System.out.println("cloudbusinessservice_device ip:"+ip);
		Socket socket = null;
		try {
			socket=new Socket(ip, 33456);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		DataOutputStream dos = null;
		BufferedOutputStream bos = null; //What can BufferedOutputStream help ? 
		BufferedInputStream dis=null;
		byte[] bytes = new byte[1024];
			try {
				/*
				 * new a File with the filePath
				 * new a FileInputStream with the File to read the file by byte
				 * new a BufferedInputStream with the FileInputStream to use buffered stream
				 */
				dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				//首先发送文件名     客户端发送使用writeUTF方法，服务器端应该使用readUTF方法
				dos.writeUTF(filePath);//bus服务器，真实文件路径
				dos.flush();
				bos=new BufferedOutputStream(new FileOutputStream(relFile));
				dis=new BufferedInputStream(socket.getInputStream());
				int length = 0;
				while ((length = dis.read(bytes, 0, bytes.length)) > 0) {
					bos.write(bytes, 0, length);
					bos.flush();
				}
				
				return relFile;
			} catch (IOException e) {
				//e.printStackTrace();
				LogUtils.error(e);
			} finally {
				//使用完毕后，应关闭输入、输出流和socket
					try {
						if(dis != null)
							dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						try {
							if(bos != null)
								bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							try {
							  if(dos != null)
								dos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}finally{
								try {
									if(socket != null)
										socket.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
			}
		return null;
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity bakupBasicListSsit(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String, Object> m=JsonUtils.fromJson(req, Map.class);
			List<Map<String,Object>> lists= commonDao.selectList("common.selectAll", m);
			if(!CollectionUtils.isEmpty(lists)){
				result.setState(true);
				result.setResult(lists);
			}
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryLibraryDbBakByparamExt(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			BakupDataEntity b=JsonUtils.fromJson(req, BakupDataEntity.class);
			List<BakupDataEntity> totals=bakuDao.queryBakDataInfoByPage(b);
			if(!CollectionUtils.isEmpty(totals)){
				b.setTotal(totals.get(0).getTotal());
			}
			b.setDoAount(false);
			List<BakupDataEntity> ret=bakuDao.queryBakDataInfoByPage(b);
			b.setRows(ret);
			result.setState(true);
			result.setResult(b);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity checkBakUpFileIfExsit(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> m=JsonUtils.fromJson(req, Map.class);
			if(m.containsKey("needUpdToExistList")){
				List<Integer> needUpdToExistList=(List<Integer>)m.get("needUpdToExistList");
				int u=bakuDao.updBakDataInfoToExist(needUpdToExistList);
				result.setMessage("need_fresh");
			}
			if(m.containsKey("needUpdToNotExistList")){
				List<Integer> needUpdToNotExistList=(List<Integer>) m.get("needUpdToNotExistList");
				int u=bakuDao.updBakDataInfoToNotExist(needUpdToNotExistList);
				result.setMessage("need_fresh");
			}
			result.setState(true);
			//result.setResult(m);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity deleteLibBakup(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			List<String> list=JsonUtils.fromJson(req, List.class);
			if(list!=null){
				int d=bakuDao.delBakDataInfoByPath(list);
				if(d>0){
					result.setState(true);
				}
			}
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity getLastLibBakUpTime(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			/**
			 * 这个要分 云平台 管理员 和 一般用户，
			 * 云平台管理员取所有的最新的备份时间
			 * 一般用户就获取按本馆的最新的备份时间
			 */
			Map<String,Object> map=JsonUtils.fromJson(req, Map.class);
			//if(map.containsKey("library_idx_arr")){
			//}else{//没有 则云平台
				Map<String,Object> m=bakuDao.getLastLibBakUpTime(map);
				result.setState(true);
				result.setResult(m);
			//}
		}
		return result;
	}

}
