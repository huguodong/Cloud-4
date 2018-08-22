package com.ssitcloud.business.datasync.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.datasync.service.DataSyncService;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.datasync.entity.UploadcfgSynResultEntity;

@Service
public class DataSyncServiceImpl extends BasicServiceImpl implements DataSyncService{
	
	private String selDevice="http://127.0.0.1:8080/clouddbservice_device/device/selbyid";
	
	private String charset="utf-8";
	
	public static final String URL_uploadcfgSyn="uploadcfgSyn";
	public static final String URL_downloadCfgSync="downloadCfgSync";
	public static final String URL_askVersion="askVersion";
	public static final String URL_transData="transData";
	public static final String URL_selLibraryByIdxOrId="selLibraryByIdxOrId";


	
	@SuppressWarnings("unchecked")
	
	@Override
	public ResultEntityF<UploadcfgSynResultEntity> uploadcfgSyn(String req){
		ResultEntityF<UploadcfgSynResultEntity> resEntity=new ResultEntityF<UploadcfgSynResultEntity>();
		try {
			Map<String,String> map=new HashMap<>();
			Map<String,Object> node=JsonUtils.fromJson(req, Map.class);
			//第一步 根据 library_id 获取 idx
			if(node!=null){
				String library_id=(String) node.get("library_id");
				Map<String,String> libraryIdMap=new HashMap<>();
				libraryIdMap.put("json", "{\"lib_id\":\""+library_id+"\"}");
				if(StringUtils.hasLength(library_id)){
					String resLibrary=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selLibraryByIdxOrId), libraryIdMap, charset);
					if(StringUtils.hasLength(resLibrary)){
						JsonNode libNode=JsonUtils.readTree(resLibrary);
						if(libNode!=null){										   //library_idx
							JsonNode libIdxNode=libNode.get("result");
							if(libIdxNode!=null){
								String library_Idx=libIdxNode.get("library_idx").asText();//library_idx
								if(library_Idx!=null){
									node.put("library_id", library_Idx);
								}
							}
						}
					}
				}
			}
			
			map.put("req", JsonUtils.toJson(node));
			String strResult=HttpClientUtil.doPost(requestURL.getRequestURL(URL_uploadcfgSyn), map, charset);
			if(StringUtils.hasText(strResult)){
				 resEntity=JsonUtils.fromJson(strResult, new TypeReference<ResultEntityF<UploadcfgSynResultEntity>>() {});
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resEntity;
	}
	
	/**
	 * 請求地址：clouddbservice_device/device/selbyid
	 * 
	 * 作用：獲取device表的一個記錄
	 * 
	 * @methodName: selDevice
	 * @param device_id
	 * @return
	 * @author: liuBh
	 * @description: TODO
	 */
	
	@Override
	public DeviceEntity selDevice(String device_id){
		Map<String,String> map=new HashMap<>();
		map.put("deviceid", device_id);
		String result=HttpClientUtil.doPost(selDevice, map, charset);
		List<DeviceEntity> devices=JsonUtils.fromJson(result, new TypeReference<List<DeviceEntity>>() {});
		if(CollectionUtils.isNotEmpty(devices)){
			return devices.get(0);
		}
		return null;
	}
	
	/**
	 * 通过业务层 getDeviceConfig() 判断是否在原来的模板的基础上,  是否参加修改，如果未修改则通过 device_run_id( 相当于 device_idx)
	 * @methodName: getDeviceConfig
	 * @return
	 * @returnType: DeviceConfig
	 * @author: liuBh
	 */
	/*public DeviceConfigEntity getDeviceConfig(String device_id){
		if(device_id==null||"".equals(device_id)) return null;
		return null;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity downloadCfgSync(String req) {
		ResultEntity resEntity=new ResultEntity();
		try {
			Map<String,String> map=new HashMap<>();
			Map<String,Object> node=JsonUtils.fromJson(req, Map.class);
			//第一步 根据 library_id 获取 idx
			if(node!=null){
				String library_id=(String) node.get("library_id");
				Map<String,String> libraryIdMap=new HashMap<>();
				libraryIdMap.put("json", "{\"lib_id\":\""+library_id+"\"}");
				if(StringUtils.hasLength(library_id)){
					String resLibrary=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selLibraryByIdxOrId), libraryIdMap, charset);
					if(StringUtils.hasLength(resLibrary)){
						JsonNode libNode=JsonUtils.readTree(resLibrary);
						if(libNode!=null){										   //library_idx
							JsonNode libIdxNode=libNode.get("result");
							if(libIdxNode!=null){
								String library_Idx=libIdxNode.get("library_idx").asText();//library_idx
								if(library_Idx!=null){
									node.put("library_id", library_Idx);
									node.put("lib_id", library_id);
								}
							}
						}
					}
				}
			}
			map.put("req", JsonUtils.toJson(node));
			String strResult=HttpClientUtil.doPost(requestURL.getRequestURL(URL_downloadCfgSync), map, charset);
			if(StringUtils.hasText(strResult)){
				 resEntity=JsonUtils.fromJson(strResult, ResultEntity.class);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resEntity;
	}

	@Override
	public ResultEntity askVersion(String req) {
		ResultEntity resEntity=new ResultEntity();
		try {
			Map<String,String> map=new HashMap<>();
			map.put("req", req);
			String strResult=HttpClientUtil.doPost(requestURL.getRequestURL(URL_askVersion), map, charset);
			if(StringUtils.hasText(strResult)){
				 resEntity=JsonUtils.fromJson(strResult, ResultEntity.class);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resEntity, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return resEntity;
	}

	@Override
	public ResultEntity transData(String req) {
		
		ResultEntity resEntity=new ResultEntity();
		try {
			Map<String,String> map=new HashMap<>();
			map.put("req", req);
			String strResult=HttpClientUtil.doPost(requestURL.getRequestURL(URL_transData), map, charset);
			if(StringUtils.hasText(strResult)){
				 resEntity=JsonUtils.fromJson(strResult, ResultEntity.class);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resEntity;
	}
	
	

}
