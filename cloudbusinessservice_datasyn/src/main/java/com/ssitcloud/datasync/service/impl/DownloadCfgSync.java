package com.ssitcloud.datasync.service.impl;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.ssitcloud.common.entity.LibraryEntity;
import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.common.util.PropertiesUtil;
import com.ssitcloud.datasync.entity.DeviceAcsModuleEntity;
import com.ssitcloud.datasync.entity.HeartBeatChangeTableData;
import com.ssitcloud.datasync.entity.ProtocolInfo;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;
import com.ssitcloud.library.service.LibraryService;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

@Component(value = "downloadCfgSync")
public class DownloadCfgSync extends BasicServiceImpl implements DataSyncCommand {

	public static final String URL_DOWNLOAD_META_SYNC = "downloadCfgSync";

	public static final String URL_DOWNLOAD_LIBRARYINFO_SYNC = "downloadLibraryInfoCfgSync";

	private static final String URL_updateRequestTimeByTriTableName = "updateRequestTimeByTriTableName";
	private static final String URL_queryDeviceConfigNewAndOldByDeviceIdx = "queryDeviceConfigNewAndOldByDeviceIdx";

	@Resource(name = "heartBeatChangeTableData")
	private HeartBeatChangeTableData heartBeatChangeTableData;
	
	@Resource
	private LibraryService libServcie;
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		ResultEntity resEntity = new ResultEntity();
		Map<String, Object> map = conditionInfo.getData();
		// String lib_id = map.get(LIB_ID) + "";
		String lib_id = map.get(LIB_ID) + "";
		String dev_id = map.get(DEV_ID) + "";
		String paramTableName = map.get("table") + "";
		Map<String, String> params = new HashMap<>();
		PropertiesUtil propertites = new PropertiesUtil();
		params.put("lib_id", lib_id);
		params.put("device_id", dev_id);
		String lib_idx = "";
		try {
			if (StringUtils.hasLength(lib_id)) {
				lib_idx = libServcie.getLibraryIdxById(lib_id)+"";
				/*if(librarycache != null){
					Element e = librarycache.get(lib_id);
					if(e!=null){
						lib_idx = (String) e.getObjectValue();
					}
				}*/
			}
			String url = URL_DOWNLOAD_META_SYNC;
			if("cloud_dbsync_config".equals(paramTableName)){
				url = URL_DOWNLOAD_LIBRARYINFO_SYNC;
			}
			map.put("library_id", lib_idx);
			map.put("lib_id", lib_id);
			params.put("req", JsonUtils.toJson(map));
			String result = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(url), params, charset);
			if (JSONUtils.mayBeJSON(result)) {
				resEntity = JsonUtils.fromJson(result, ResultEntity.class);
				resEntity.setState(true);
				resp.setData(resEntity);
				//有延迟
				//在这里清空 队列 更新表名
				//清除数据 该设备下载数据之后，清除queue数据，更新change_table_tri
				doClear(dev_id,paramTableName);

			}else{
				LogUtils.error(result);
				resEntity.setRetMessage(result);
				resp.setData(resEntity);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread(), e);
		}
		return resp;
	}
	@Async
	public void doClear(String dev_id,String paramTableName){
		List<Integer> triIdxList=new ArrayList<>();
		if(!"null".equals(dev_id)&&heartBeatChangeTableData.containsKey(dev_id)){
			Queue<TableChangeTriEntity> changes=heartBeatChangeTableData.get(dev_id);
			Boolean isHasDeviceConfigData = false;
			Boolean isHasMetaData = false;
			Integer deviceConfigTriIdx = null;
			TableChangeTriEntity deviceConfigTri = null;
			
			Boolean isHasDeviceConfigData_ = false;
			Boolean isHasDeviceMoBanData = false;
			Integer deviceConfigTriIdx_ = null;
			TableChangeTriEntity deviceConfigTri_ = null;
			
			//针对device_config（I），即设备注册时系统产生的一条table_change_tri数据，
			//设备端请求会返回给设备端metadata_表名，可能还会有模板表名
			if(paramTableName != null && paramTableName.startsWith("metadata_")){
				isHasMetaData = true;
			}
			
			//针对device_config(U),即设备修改了模板，从模板数据到自定义数据，这个时候存在device_config(U)和对应模板表名，下载完成模板表名之后请求一同把device_config(U)清除
			if(paramTableName != null && ("device_ext_config".equals(paramTableName) || "device_run_config".equals(paramTableName) || "device_dbsyc_config".equals(paramTableName)))
			{
				isHasDeviceMoBanData = true;
			}
			
			if(CollectionUtils.isNotEmpty(changes)){
				for(TableChangeTriEntity change:changes){
					String tableName=change.getTable_name();
					Integer triIdx=change.getTri_idx();//如果涉及到有多个设备都使用的话，只要有一个设备返回了（表示内存中已经存在），存在的风险是如果停电则可能部分设备更新失败，需要重新修改同步
					//针对device_config 插入类型
					if("device_config".equals(tableName) && "I".equals(change.getChange_state())){
						isHasDeviceConfigData = true;
						deviceConfigTriIdx = triIdx;
						deviceConfigTri = change ;
					}
					
					if("device_config".equals(tableName) && "U".equals(change.getChange_state())){
						isHasDeviceConfigData_ = true;
						deviceConfigTriIdx_ = triIdx;
						deviceConfigTri_ = change ;
					}
					
					if(paramTableName.equals(tableName)){
						if(triIdx!=null){
							triIdxList.add(triIdx);
							changes.remove(change);
						}
					}
				}
				if(isHasDeviceConfigData && isHasMetaData){
					if(deviceConfigTriIdx!=null){
						triIdxList.add(deviceConfigTriIdx);
						changes.remove(deviceConfigTri);
					}
				}
				
				if(isHasDeviceConfigData_ && isHasDeviceMoBanData){
					if(deviceConfigTriIdx_!=null){
						triIdxList.add(deviceConfigTriIdx_);
						changes.remove(deviceConfigTri_);
					}
				}
				
				//这里可以考虑清理数据,task//如果是所有设备都需要同步的数据的话，
				if(triIdxList.size()>0){
					//注意 ：测试的时候注释掉,修改requestTime
					updateRequestTimeByTriIdxs(triIdxList);
				}
			}
		}
	}
	/**
	 * 直接返回来需要同步的表名 逗号分隔
	 * @param deviceIdx
	 * @return
	 */
	public ResultEntity queryDeviceConfigNewAndOldByDeviceIdx(Integer deviceIdx){
		Map<String, String> params=new HashMap<String, String>();
		params.put("req", "{\"device_idx\":\""+deviceIdx+"\",\"delete\":\"true\"}");
		String resDeviceConfig=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_queryDeviceConfigNewAndOldByDeviceIdx), params, charset);
		if(JSONUtils.mayBeJSON(resDeviceConfig)){
			return JsonUtils.fromJson(resDeviceConfig, ResultEntity.class);
		}
		return null;
	}
	
	/**
	 * 更新requestTime时间 
	 * @param triTabNameList
	 */
	@Async
	public  void updateRequestTimeByTriTableName(List<String> triTabNameList){
		String res=postRetStr(URL_updateRequestTimeByTriTableName, JsonUtils.toJson(triTabNameList));
		if(JSONUtils.mayBeJSON(res)){
			LogUtils.info("update requestTime tri_change_table数据返回结果:"+res);
		}
	}
}
