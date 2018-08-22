package com.ssitcloud.common.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.DeviceIdx2IdContainer;
import com.ssitcloud.datasync.entity.HeartBeatChangeTableData;
import com.ssitcloud.datasync.entity.HeartBeatMidVersionData;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;

/**
 * 改成XML配置方式启动
 * 2016年6月6日11:47:34
 * @author lbh
 */
public class ScheduledConfig {

	@Resource(name = "requestURLListEntity")
	private RequestURLListEntity requestURLListEntity;
	
	private final static String charset=Consts.UTF_8.toString();
	
	public static final String URL_QueryDevIdxByDevId="QueryDevIdxByDevId";
	public static final String URL_SelTableChangeTri="SelTableChangeTri";
	public static final String URL_SelTableChangeTriPatchInfo="SelTableChangeTriPatchInfo";

	private static final String URL_queryDeviceIdbyLibIdx = "queryDeviceIdbyLibIdx";
	
	private static final String URL_QUERYSERVICEIDBYLIBIDX = "queryServiceIdbyLibIdx";
	private static final String URL_QUERYSERVICEIDBYSERVICEIDX = "queryServiceIdbyServiceIdx";
	

	private static final String URL_selLibraryByIdxsOrIds = "selLibraryByIdxsOrIds";
	
	
	@Resource(name="deviceIdx2IdContainer")
	private DeviceIdx2IdContainer deviceIdx2IdContainer;
	
	//library_idx 对应的 List<device_id>
	@Resource(name="libIdxAndArrayDeviceIdContainer")
	public ConcurrentMap<Integer, List<String>> libIdxAndArrayDeviceIdContainer;
	
	//除了 patchInfo表变动
	@Resource(name="heartBeatChangeTableData")
	private HeartBeatChangeTableData heartBeatChangeTableData;
	
	//只有 patchInfo 表变动
	@Resource(name="heartBeatMidVersionData")
	private HeartBeatMidVersionData heartBeatMidVersionData;
	
	//library_idx 对应的 List<service_Id>
	@Resource(name="libIdxAndArrayServiceIdContainer")
	public ConcurrentMap<Integer, List<String>> libIdxAndArrayServiceIdContainer;

	// service_idx 对应的 List<service_id>
	@Resource(name = "serviceIdxAndArrayserviceIdContainer")
	public ConcurrentMap<Integer, List<String>> serviceIdxAndArrayserviceIdContainer;

	/**
	 * 根据libidx获取 deviceId 分组
	 */
	@SuppressWarnings("unchecked")
	public void queryDeviceIdbyLibIdx(){
		String result=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_queryDeviceIdbyLibIdx), new HashMap<String, String>(), charset);
		if(!JSONUtils.mayBeJSON(result)){
			LogUtils.error("获取不到library_idx 对应的device_id数据 表信息!!!\n"+result+"\n");
			return;
		}
			ResultEntity res=null;
			try {
				 res=JsonUtils.fromJson(result, ResultEntity.class);
			} catch (Exception e) {
				LogUtils.error("ScheduledConfig queryDeviceIdbyLibIdx 转化json数据出错", e);
				return;
			}
		if(res!=null){
			//library_idx 对应的 List<device_id>
			Map<String,Map> map=(Map<String, Map>)res.getResult();
			if(map!=null){
				for(Entry<String, Map> e:map.entrySet()){
					Map value=e.getValue();
					String key=e.getKey();
					if(value!=null&&value.get("device_ids")!=null){
						String device_ids=(String)value.get("device_ids");
						String val[]=org.springframework.util.StringUtils.commaDelimitedListToStringArray(device_ids);
						List<String> devIds=Arrays.asList(val);
						//librayr_idx --- devIds 
						libIdxAndArrayDeviceIdContainer.put(Integer.parseInt(key),devIds);
					}
				}
			}else{
				LogUtils.error("ScheduledConfig queryDeviceIdbyLibIdx:"+res.getRetMessage());
			}
		}
	}
	
	/**
	 * 根据libidx获取特殊设备（3D导航、人流量、智能家具） 分组
	 */
	@SuppressWarnings("unchecked")
	public void queryServiceIdbyLibIdx(){
		String result=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_QUERYSERVICEIDBYLIBIDX), new HashMap<String, String>(), charset);
		if(!JSONUtils.mayBeJSON(result)){
			LogUtils.error("获取不到library_idx 对应的service_id数据 表信息!!!\n"+result+"\n");
			return;
		}
			ResultEntity res=null;
			try {
				 res=JsonUtils.fromJson(result, ResultEntity.class);
			} catch (Exception e) {
				LogUtils.error("ScheduledConfig queryServiceIdbyLibIdx 转化json数据出错", e);
				return;
			}
		if(res!=null){
			//library_idx 对应的 List<device_id>
			Map<String,Map> map=(Map<String, Map>)res.getResult();
			if(map!=null){
				for(Entry<String, Map> e:map.entrySet()){
					Map value=e.getValue();
					String key=e.getKey();
					if(value!=null&&value.get("service_ids")!=null){
						String service_ids=(String)value.get("service_ids");
						String val[]=org.springframework.util.StringUtils.commaDelimitedListToStringArray(service_ids);
						List<String> serviceIds=Arrays.asList(val);
						//librayr_idx --- devIds 
						libIdxAndArrayServiceIdContainer.put(Integer.parseInt(key),serviceIds);
					}
				}
			}else{
				LogUtils.error("ScheduledConfig queryServiceIdbyLibIdx:"+res.getRetMessage());
			}
		}
	}
	/**
	 * 根据serviceidx获取特殊设备（3D导航、人流量、智能家具）服务器名称分组
	 */
	@SuppressWarnings("unchecked")
	public void queryServiceIdbyServiceIdx(){
		String result=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_QUERYSERVICEIDBYSERVICEIDX), new HashMap<String, String>(), charset);
		if(!JSONUtils.mayBeJSON(result)){
			LogUtils.error("获取不到service_idx 对应的SpecialDeviceId数据 表信息!!!\n"+result+"\n");
			return;
		}
			ResultEntity res=null;
			try {
				 res=JsonUtils.fromJson(result, ResultEntity.class);
			} catch (Exception e) {
				LogUtils.error("ScheduledConfig queryServiceIdbyServiceIdx 转化json数据出错", e);
				return;
			}
		if(res!=null){
			//service_idx 对应的 List<device_id>
			Map<String,Map> map=(Map<String, Map>)res.getResult();
			if(map!=null){
				for(Entry<String, Map> e:map.entrySet()){
					Map value=e.getValue();
					String key=e.getKey();
					if(value!=null&&value.get("service_ids")!=null){
						String service_ids=(String)value.get("service_ids");
						String val[]=org.springframework.util.StringUtils.commaDelimitedListToStringArray(service_ids);
						List<String> qIds=Arrays.asList(val);
						//librayr_idx --- devIds 
						serviceIdxAndArrayserviceIdContainer.put(Integer.parseInt(key),qIds);
					}
				}
			}else{
				LogUtils.error("ScheduledConfig queryServiceIdbyServiceIdx:"+res.getRetMessage());
			}
		}
	}
	/**
	 * 根据libid获取 deviceId 分组
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	//{lib_id:"1,2,3,4"}
	public Map<String, List<String>> queryDeviceIdbyLibId(Map<String,String> libids){
		//{lib_id=["TEST001"]}
		Map<String, List<String>> libIdAndArrayDeviceIdContainer =new HashMap<>();
		String result=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_selLibraryByIdxsOrIds), libids, charset);
		if(!JSONUtils.mayBeJSON(result)){
			LogUtils.error("获取不到library_id 对应的device_id数据 表信息!!!\n"+result+"\n");
			return null;
		}
		ResultEntity res=null;
		try {
			 res=JsonUtils.fromJson(result, ResultEntity.class);
		} catch (Exception e) {
			LogUtils.error("ScheduledConfig queryDeviceIdbyLibId 转化json数据出错", e);
			return null;
		}
		if(res!=null && res.getState()){
			//library_id 对应的 List<device_id>
			List<Map<String,Object>> list=(List<Map<String,Object>>)res.getResult();
			if(list!=null){
				if(libIdxAndArrayDeviceIdContainer.isEmpty()){
					queryDeviceIdbyLibIdx();
				}
				for(Map<String,Object> m:list){
					String lib_id=(String) m.get("lib_id");
					Integer library_idx=(Integer) m.get("library_idx");
					List<String> devIds=libIdxAndArrayDeviceIdContainer.get(library_idx);
					libIdAndArrayDeviceIdContainer.put(lib_id, devIds);
				}
				return libIdAndArrayDeviceIdContainer;
			}else{
				LogUtils.error("ScheduledConfig queryDeviceIdbyLibId:"+res.getRetMessage());
			}
		}
		return libIdAndArrayDeviceIdContainer;
	}
	
	
	public void queryDeviceIdxByIdScheduled(){
		String result=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_QueryDevIdxByDevId), new HashMap<String, String>(), charset);
		if(result!=null&&!"".equals(result)){
			if(!JSONUtils.mayBeJSON(result)){
				LogUtils.error("获取不到 device_idx !!!\n"+result+"\n");
				return;
			}
			ResultEntity res=null;
			try {
				 res=JsonUtils.fromJson(result, ResultEntity.class);
			} catch (Exception e) {
				LogUtils.error("ScheduledConfig queryDeviceIdxByIdScheduled 转化json数据出错", e);
				return;
			}
			if(res!=null){
				@SuppressWarnings("unchecked")
				Map<Integer,String> map=(Map<Integer, String>)res.getResult();
				if(deviceIdx2IdContainer.equals(map)){
				}else{
					deviceIdx2IdContainer.putAll(map);
					LogUtils.info("deviceId2IdxContainer:"+deviceIdx2IdContainer.toString());
				}
			}
			
		}
	}
	
	public static final String URL_queryDevIdsByModelAndModelIdx="queryDevIdsByModelAndModelIdx";

	private static final String URL_SetDeviceIdsLibDistinctInHeartBeatByPathInfoIdx = "SetDeviceIdsLibDistinctInHeartBeatByPathInfoIdx";

	private static final String URL_SetDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx = "SetDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx";
	
	/**
	 * 根据 table_name model_idx 查询出使用该模版的设备
	 * 
	 * @param table_name
	 * @param modelIdx
	 * @return
	 */
	public List<Map<String,Object>> queryDevIdsByModelAndModelIdx(String table_name,Integer modelIdx){
		Map<String,String> map=new HashMap<>();
		map.put("req", "{\"table_name\":\""+table_name+"\",\"model_idx\":\""+modelIdx.toString()+"\"}");
		String result=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_queryDevIdsByModelAndModelIdx),map, charset);
		if(!JSONUtils.mayBeJSON(result)){
			LogUtils.error("获取不到 使用模版的设备IDX");
			return new ArrayList<>();
		}
		ResultEntity res=JsonUtils.fromJson(result, ResultEntity.class);
		if(res!=null && res.getState()){
			List<Map<String,Object>> rs=(List<Map<String,Object>>)res.getResult();
			return rs;
		}
		return null;
	} 
	
	
	/**
	 * 
	 * 获取 table_change_tri信息。
	 * 注意事项：<br/>
	 * 数字类型的key要转成String类型使用<br/>
	 * device表中必须存在对应的device_id<br/>
	 * 
	 * @methodName: storeChangeTableData
	 * @returnType: void
	 * @author: liuBh
	 */

	public void storeChangeTableData(){
		String res=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SelTableChangeTri), new HashMap<String, String>(), charset);
		if(res==null||"".equals(res)){
			LogUtils.error("获取不到table_change_tri 表信息!!!");
			return;
		}
		if(!JSONUtils.mayBeJSON(res)){
			LogUtils.error("获取不到table_change_tri 表信息!!!\n"+res+"\n");
			return;
		}
		ResultEntityF<List<TableChangeTriEntity>> result=null;
		
		try {
			result=JsonUtils.fromJson(res, new TypeReference<ResultEntityF<List<TableChangeTriEntity>>>() {});
		} catch (Exception e) {
			LogUtils.error("storeChangeTableData --->json 转化对象出错", e);
			return;
		}
	
		if(result!=null&&result.getState()==true){
			List<TableChangeTriEntity> tableChanges=result.getResult();
			if(tableChanges!=null){
				Map<String,Boolean> clearFlagMap=new HashMap<>();
				Map<String,String> metadataTableMap=new HashMap<>();
				for(TableChangeTriEntity tableChange:tableChanges){
					 Integer dataIdx=tableChange.getData_idx();
					 String dataType=tableChange.getData_type();
					 String tableName=tableChange.getTable_name();
					    if("metadata_logic_obj".equals(tableName)||"metadata_opercmd".equals(tableName)||
							"metadata_order".equals(tableName)||"metadata_run_config".equals(tableName)||
							"view_config".equals(tableName)){
							doMetadataChangeTableData(metadataTableMap, clearFlagMap, tableChange);
					    }else if("reader_type".equals(tableName) || "maintenance_card".equals(tableName) || "library_theme_config".equals(tableName)){//图书馆馆级配置 
							String typeDistinction=tableChange.getData_type();
							if("0".equals(typeDistinction)){//馆级配置 这个字段默认为0
								Integer library_idx=tableChange.getData_idx();
								//获取device_id
								queryDeviceIdbyLibIdx();
								if(libIdxAndArrayDeviceIdContainer.containsKey(library_idx)){
									List<String> devIds=libIdxAndArrayDeviceIdContainer.get(library_idx);
									if(CollectionUtils.isNotEmpty(devIds)){
										for(String devId:devIds){
											doHeartBeatChangeTableData(clearFlagMap, null, devId,tableChange);
										}
									}
								}
							}
						}else if("operator_maintenance_info".equals(tableName)){//维护卡
							queryDeviceIdbyLibIdx();
							Integer library_idx=tableChange.getData_idx();
							if(libIdxAndArrayDeviceIdContainer.containsKey(library_idx)){
								List<String> devIds=libIdxAndArrayDeviceIdContainer.get(library_idx);
								if(CollectionUtils.isNotEmpty(devIds)){
									for(String devId:devIds){
										doHeartBeatChangeTableData(clearFlagMap, null, devId,tableChange);
									}
								}
							}
						}else if(dataIdx!=null&&deviceIdx2IdContainer.containsKey(dataIdx+"") && !"0".equals(dataType)){//这里的话 只有设备的，没有模版
								doHeartBeatChangeTableData(clearFlagMap, dataIdx, null, tableChange);
					    }else if(dataIdx!=null && "0".equals(dataType)){//这个是模版的
					    		//dataIdx 模版idx table_name 更新的表明。
					    	    // 从device_config 查询使用该模版的设备的ID
					    		List<Map<String,Object>> list=queryDevIdsByModelAndModelIdx(tableChange.getTable_name(), dataIdx);
					    		if(list!=null && list.size()>0){
					    			for(Map<String,Object> m:list){
					    				if(m.containsKey("device_idx")){
					    					Integer i=(Integer)m.get("device_idx");
					    					String devId=deviceIdx2IdContainer.get(i+"");//Integer 类型的查不出来
					    					if(devId!=null)
					    						doHeartBeatChangeTableData(clearFlagMap, dataIdx, devId, tableChange);
					    				}
					    			}
					    		}
					    }else if("device_service".equals(tableName)||"device_service_queue".equals(tableName)||"special_device".equals(tableName)||"cloud_dbsync_config".equals(tableName)){//消息队列配置数据、人流量、3D导航、智能家具下属设备
							queryServiceIdbyServiceIdx();
					    	Integer service_idx=tableChange.getData_idx();
					    	if(serviceIdxAndArrayserviceIdContainer.containsKey(service_idx)){
								List<String> service_ids=serviceIdxAndArrayserviceIdContainer.get(service_idx);
								if(CollectionUtils.isNotEmpty(service_ids)){
									for(String service_id:service_ids){
										doHeartBeatChangeTableData(clearFlagMap, null, service_id,tableChange);
									}
								}
							}
						}
				}
			}
		}
	}
	
	//private ConcurrentMap<String, Boolean> devModelMap=new ConcurrentHashMap<>();
	
	/**
	 * 将 tableChange 数据 加入到 heartBeat 容器队列
	 * @param clearFlagMap
	 * @param dataIdx 一般的情况下是 device_idx
	 * @param devId   设备ID
	 * @param tableChange
	 */
	private void doHeartBeatChangeTableData(Map<String,Boolean> clearFlagMap,Integer dataIdx,String devId,TableChangeTriEntity tableChange){
		//String dataType=tableChange.getData_type();
		//if("0".equals(dataType)){
			//表示修改的是模版，则需要取出对应的 模版信息同步
			//获取 全部使用该模版的设备，需要全部设备同步完毕
			//tableChange.getTable_name();
		//}
		
		//获取device_id
		String device_id="";
		if(StringUtils.isNotBlank(devId)){
			device_id=devId;
		}else{
			device_id=deviceIdx2IdContainer.get(dataIdx+"");
		}
		
		if(clearFlagMap.containsKey(device_id)){
			//表示已经标记过，则不需要再次标记
			clearFlagMap.put(device_id, false);
		}else{
			//没有标记过,则需要清理，并且已经标记
			clearFlagMap.put(device_id, true);
		}
		//若果不存在则新建一个list,并保存
		ConcurrentLinkedQueue<TableChangeTriEntity> tableChangeQueue=new ConcurrentLinkedQueue<>();
		if(heartBeatChangeTableData.containsKey(device_id)){
			//存在 device_id,将device_id队列取出来
			//循环的开始每个队列都要清理一次
			Queue<TableChangeTriEntity> queue=heartBeatChangeTableData.get(device_id);
		
			if(clearFlagMap.get(device_id)==true&&!queue.isEmpty()){//没有清理过的队列，则循环第一次需要清理队列。
				queue.clear();
				clearFlagMap.put(device_id, false);
			}
			queue.add(tableChange);
			//由于间隔时间太短不能打印出来通过、queue.jsp观察
		}else{
			tableChangeQueue.add(tableChange);
			//第一次调用，没有队列，设置不清除
			clearFlagMap.put(device_id, false);
			heartBeatChangeTableData.put(device_id, tableChangeQueue);
		}
	}
	
	/**
	 * 保存 升级补丁
	 */
	public void storePatchInfo(){
		String res=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SelTableChangeTriPatchInfo), new HashMap<String, String>(), charset);
	
		if(res==null||"".equals(res)){
			LogUtils.info("获取不到table_change_tri 表信息!!!");
			return;
		}
		if(!JSONUtils.mayBeJSON(res)){
			LogUtils.error("获取不到table_change_tri 表信息!!!\n"+res+"\n");
		}
		ResultEntityF<List<TableChangeTriEntity>> result=JsonUtils.fromJson(res, new TypeReference<ResultEntityF<List<TableChangeTriEntity>>>() {});
		if(result!=null&&result.getState()==true){
			List<TableChangeTriEntity> tableChanges=result.getResult();
			if(tableChanges!=null){
				Map<String,Boolean> clearFlagMap=new HashMap<>();
				for(TableChangeTriEntity tableChange:tableChanges){
					 /**
					  * 这个是 path_idx,根据path_idx查询出 约束类型（全网、馆约束、设备类型约束），
  					  * 如果是全网则
					  */
					 Integer dataIdx=tableChange.getData_idx();
					 if("D".equals(tableChange.getChange_state())){//删除的跳过
						 continue;
					 }
					 if("1".equals(tableChange.getData_type())){//全网
						for( Entry<Integer, String> e:deviceIdx2IdContainer.entrySet()){
							String devId=e.getValue();//device_id
							doHeartBeatChangeTableDataPathInfo(clearFlagMap, dataIdx, devId, tableChange);
						}
					 }else if("2".equals(tableChange.getData_type())){//馆约束
						 //需要根据dataIdx查询的馆信息，可能有多个馆，再获取馆的所有设备
						 setDeviceIdsLibDistinctInHeartBeatByPathInfoIdx(clearFlagMap, dataIdx, tableChange);
					 }else if("3".equals(tableChange.getData_type())){//设备类型约束
						 //需要根据dataIdx查询约束的设备类型对应的设备
						 setDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx(clearFlagMap, dataIdx, tableChange);
					 }
				}
			}
		}
	}
	//2016年12月6日 15:49:41
	public void setDeviceIdsLibDistinctInHeartBeatByPathInfoIdx(Map<String,Boolean> clearFlagMap,Integer dataIdx,TableChangeTriEntity tableChange){
		if(dataIdx==null) return;
		Map<String, String> m=new HashMap<String, String>();
		m.put("req","{\"dataIdx\":\""+dataIdx.toString()+"\"}");
		String res=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SetDeviceIdsLibDistinctInHeartBeatByPathInfoIdx), m, charset);
		if(!JSONUtils.mayBeJSON(res)){
			LogUtils.error("method setDeviceIdsLibDistinctInHeartBeatByPathInfoIdx !!!\n"+res+"\n");
			return;
		}
		//getResult --->device_ids 数组
		ResultEntity result=JsonUtils.fromJson(res, ResultEntity.class);
		if(result!=null && result.getState()){
			@SuppressWarnings("unchecked")
			List<String> resList=(List<String>)result.getResult();
			if(!resList.isEmpty()){
				Map<String, String> reqMap=new HashMap<>();
				Map<String, String> map=new HashMap<>();
				map.put("lib_id",org.springframework.util.StringUtils.collectionToCommaDelimitedString(resList));
				reqMap.put("json", JsonUtils.toJson(map));
				Map<String,List<String>> libIdAndArrayDeviceIdContainer=queryDeviceIdbyLibId(reqMap);
				if(libIdAndArrayDeviceIdContainer!=null && !libIdAndArrayDeviceIdContainer.isEmpty()){
					for(Entry<String,List<String>> e:libIdAndArrayDeviceIdContainer.entrySet()){
						List<String> devIds=e.getValue();
						if(CollectionUtils.isNotEmpty(devIds)){
							for(String device_id:devIds){
								doHeartBeatChangeTableDataPathInfo(clearFlagMap, null, device_id, tableChange);
							}
						}
					}
				}
			}
		}
	}
	//2016年12月6日 15:49:38
	public void setDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx(Map<String,Boolean> clearFlagMap,Integer dataIdx,TableChangeTriEntity tableChange){
		if(dataIdx==null) return;
		Map<String, String> m=new HashMap<String, String>();
		m.put("req","{\"dataIdx\":\""+dataIdx.toString()+"\"}");
		//List<Map<String,object>>
		String res=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SetDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx), m, charset);
		if(!JSONUtils.mayBeJSON(res)){
			LogUtils.error("method setDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx !!!\n"+res+"\n");
			return;
		}
		//getResult --->device_ids 数组
		ResultEntity result=JsonUtils.fromJson(res, ResultEntity.class);
		if(result!=null && result.getState()){
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> resList=(List<Map<String,Object>>)result.getResult();
			if(!resList.isEmpty()){
				for(Map<String,Object> mRes:resList){
					String device_id=(String)mRes.get("device_id");
					doHeartBeatChangeTableDataPathInfo(clearFlagMap, null, device_id, tableChange);
				}
			}
		}
	}	
	
	/**
	 * pathInfo 加入到heartBeat队列
	 * @param clearFlagMap
	 * @param dataIdx
	 * @param devId
	 * @param tableChange
	 */
	public void doHeartBeatChangeTableDataPathInfo(Map<String,Boolean> clearFlagMap,Integer dataIdx,String devId,TableChangeTriEntity tableChange){
		String device_id="";
		if(StringUtils.isNotBlank(devId)){
			device_id=devId;
		}else{
			device_id=deviceIdx2IdContainer.get(dataIdx+"");
		}
		if(clearFlagMap.containsKey(device_id)){
			//表示已经标记过，则不需要再次标记
			clearFlagMap.put(device_id, false);
		}else{
			//没有标记过,则需要清理，并且已经标记
			clearFlagMap.put(device_id, true);
		}
		//若果不存在则新建一个list,并保存
		ConcurrentLinkedQueue<TableChangeTriEntity> tableChangeQueue=new ConcurrentLinkedQueue<>();
		if(heartBeatMidVersionData.containsKey(device_id)){
			//存在
			Queue<TableChangeTriEntity> queue=heartBeatMidVersionData.get(device_id);
			if(clearFlagMap.get(device_id)==true&&!queue.isEmpty()){//没有清理过，则清理
				queue.clear();
				clearFlagMap.put(device_id, false);
				LogUtils.info("heartBeatMidVersionData device_id:"+device_id+" queue clear");
			}
			queue.add(tableChange);
		}else{
			clearFlagMap.put(device_id, true);//第一次调用，设置不清除
			tableChangeQueue.add(tableChange);
			heartBeatMidVersionData.put(device_id, tableChangeQueue);
		}
	}
	
	/**
	 * 从 FTP保存升级策略到本地
	 * @deprecated
	 * @methodName: storeVersionXML
	 * @returnType: void
	 * @author: liuBh
	 * 
	 */
	/*public void storeVersionXML(){
		boolean b=FtpUtils.downVersionXmlFromFtp();
		if(!b){
			LogUtils.error("下载VersionXML失败");
		}
	}*/
	
	/**
	 * 数据同步处理 原始数据表的部分逻辑
	 * @param metadataTableMap
	 * @param tableName
	 */
	private void doMetadataChangeTableData(Map<String,String> metadataTableMap,Map<String,Boolean> clearFlagMap,TableChangeTriEntity tableChange){
		//boolean isContinue=false;
		String tableName=tableChange.getTable_name(); 
		/**
		 * 以下的表的是idx,这里还是要同步整个表的数据
		 * 	所有设备都要同步的，这个由于数据量比较大放到同步程序这边处理
		 * */

			if(metadataTableMap.containsKey(tableName)){
				//continue;//如果已经有了，就不加入了
				//isContinue=true;
				return;
			}
			metadataTableMap.put(tableName, tableName);
			Collection<String> values=deviceIdx2IdContainer.values();
			for(String device_id:values){
				if(clearFlagMap.containsKey(device_id)){
					//表示已经标记过，则不需要再次标记
					clearFlagMap.put(device_id, false);
				}else{
					//没有标记过,则需要清理，并且已经标记
					clearFlagMap.put(device_id, true);
				}
				//若果不存在则新建一个list,并保存
				ConcurrentLinkedQueue<TableChangeTriEntity> tableChangeQueue=new ConcurrentLinkedQueue<>();
				if(heartBeatChangeTableData.containsKey(device_id)){
					//存在 device_id,将device_id队列取出来
					//循环的开始每个队列都要清理一次
					Queue<TableChangeTriEntity> queue=heartBeatChangeTableData.get(device_id);
					if(clearFlagMap.get(device_id)==true&&!queue.isEmpty()){//没有清理过的队列，则循环第一次需要清理队列。
						queue.clear();
						clearFlagMap.put(device_id, false);
					}
					queue.add(tableChange);
				}else{
					tableChangeQueue.add(tableChange);
					//第一次调用，没有队列，设置不清除
					clearFlagMap.put(device_id, false);
					heartBeatChangeTableData.put(device_id, tableChangeQueue);
				}
			}
	}
	
}
