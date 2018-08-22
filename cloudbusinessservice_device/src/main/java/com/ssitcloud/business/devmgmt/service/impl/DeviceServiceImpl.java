package com.ssitcloud.business.devmgmt.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.Consts;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.devmgmt.entity.DeviceMgmtEntity;
import com.ssitcloud.business.devmgmt.service.DeviceService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class DeviceServiceImpl extends BasicServiceImpl implements DeviceService {

	public static final String URL_SelectDeviceByPage="SelectDeviceByPage";
	public static final String URL_QueryDevIdxByDevId="QueryDevIdxByDevId";
	public static final String URL_SelectDevice = "SelectDeviceMgmt";
	public static final String URL_DeleteDevice = "DeleteDeviceMgmt";
	public static final String URL_SelectInfo = "SelectDevice";
	public static final String LIBRARY_SELECT_URL="SelectLibrary";
	public static final String URL_queryDeviceByParam = "queryDeviceByParam";
	public static final String URL_queryServiceDeviceByParam = "queryServiceDeviceByParam";
	public static final String URL_queryDeviceIdByState = "queryDeviceIdByState";
	private static final String URL_UpdDevice = "UpdDevice";
	private static final String URL_UpdDeviceMgmtPage = "UpdDeviceMgmtPage";
	private static final String URL_UpdHyDeviceMgmtPage = "UpdHyDeviceMgmtPage";
	private static final String URL_selLibraryByIdxOrId = "selLibraryByIdxOrId";
	private static final String URL_checkAllConfigDataByDevIdx = "checkAllConfigDataByDevIdx";
	private static final String URL_compareMonitorConfig = "compareMonitorConfig";
	private static final String URL_loadAcsLogininfo = "loadAcsLogininfo";
	private static final String URL_deleteDevOperatorInfoByOperIds = "deleteDevOperatorInfoByOperIds";
	private static final String URL_deleteRelOperatorGroupByOperatorIdxs = "deleteRelOperatorGroupByOperatorIdxs";
	private static final String URL_queryDeviceIdbyLibIdx = "queryDeviceIdbyLibIdx";
	private static final String URL_getLibraryDevicesByPage = "getLibraryDevicesByPage";
	private static final String URL_queryDeviceIps = "queryDeviceIps";
	private static final String URL_authTransferToLibrary = "authTransferToLibrary";
	private static final String URL_devTransferToLibrary = "devTransferToLibrary";
	private static final String URL_devTransferToLibraryLog = "devTransferToLibraryLog";
	private static final String URL_queryAllRegion = "queryAllRegion";
	private static final String URL_queryDeviceRegion = "queryDeviceRegion";
	private static final String URL_getDevicePosition = "getDevicePosition";
	private static final String URL_getLibPosition = "getLibPosition";
	private static final String URL_saveDevicePosition = "saveDevicePosition";
	private static final String URL_deleteLibraryPosition = "deleteLibraryPosition";
	private static final String URL_insertFileUploadFlag = "insertFileUploadFlag";
	@Override
	public ResultEntity queryDeviceByPage(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_SelectDeviceByPage), map, charset);
		
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			LogUtils.error(result);
			ResultEntity resultError=new ResultEntity();
			resultError.setRetMessage(result);
		}
		return null;
	}
	@Override
	public ResultEntity QueryDevIdxByDevId(
			String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req==null?"":req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_QueryDevIdxByDevId), map, charset);
		if(StringUtils.hasLength(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return null;
	}
	@Override
	public String SelectDevice(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_SelectDevice);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String DeleteDevice(String json) {
		Map<String,String> map=new HashMap<>();
		List<DeviceMgmtEntity> deviceMgmtEntities = JsonUtils.fromJson(json,new TypeReference<List<DeviceMgmtEntity>>() {});
		if(CollectionUtils.isNotEmpty(deviceMgmtEntities)){
			
			String reqURL=requestURL.getRequestURL(URL_DeleteDevice);
			ResultEntity reEntity=postUrl(URL_deleteDevOperatorInfoByOperIds,json);
			if(reEntity!=null&&reEntity.getState()){
				Map<String, Object> m=(Map<String, Object>)reEntity.getResult();
				if(m!=null){
					if(m.get("deleteOkOpertorIdx")!=null){
						List<Integer> operatorIdxs=(List<Integer>)m.get("deleteOkOpertorIdx");
						Map<String,String> params=new HashMap<>();
						params.put("req", JsonUtils.toJson(operatorIdxs));
						HttpClientUtil.doPost(requestURL.getRequestURL(URL_deleteRelOperatorGroupByOperatorIdxs), params, Consts.UTF_8.toString());	
					}
					if(m.get("deleteFailDeviceIds")!=null){//deleteOkDeviceIds
						List<String> deleteFailDeviceIds=(List<String>)m.get("deleteFailDeviceIds");
						if(CollectionUtils.isNotEmpty(deleteFailDeviceIds)){
							for(String deviceId:deleteFailDeviceIds){
								for(int i=0;i<deviceMgmtEntities.size();i++){
									if(deviceId.equals(deviceMgmtEntities.get(i).getDevice_id())){
										deviceMgmtEntities.remove(i);
									}
								}
							}
						}
					}
					map.put("json", JsonUtils.toJson(deviceMgmtEntities));
					String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
					return result;
				}
			}
		}
		
		return JsonUtils.toJson(new ResultEntity());
	}
	@Override
	public String SelectInfo(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_SelectInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	
	@Override
	public String SelLibrary(String reqInfo) {
		
		String reqURL=requestURL.getRequestURL(LIBRARY_SELECT_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}
	/*
	格式 req=
	 * {	
	 *   "machineType":machineType,    //设备类型
		 "machineState":machineState,  //设备状态
		 "keyWord":keyWord//关键字 （设备名）
		 "page":page, //当前页数
		 "pageSize":pageSize //每页大小
	   }
	*/
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryDeviceByParam(String req) {
		ResultEntity resultEntity=new ResultEntity();
		JsonNode node=JsonUtils.readTree(req);
		Map<String,String> map=new HashMap<>();
		Map<String,String> stateMap=new HashMap<>();
		Map<String,Object> paramMap=new HashMap<>();
		if(node!=null){
			boolean devGroupLimit=false;
			Integer operator_idx=null;
			JsonNode pageNode=node.get("page");
			JsonNode pageSizeNode=node.get("pageSize");
			JsonNode machineTypeNode=node.get("machineType");//设备类型
			JsonNode keyWordNode=node.get("keyWord");//关键字
			JsonNode mSateNode=node.get("machineState");//设备状态
			JsonNode libraryIdxNode=node.get("library_idx");//
			JsonNode libraryIdxArrNode=node.get("lib_idx_list");//
			JsonNode operatorIdxNode=node.get("operator_idx_Limit");
			if(libraryIdxArrNode!=null){
				paramMap.put("lib_idx_list", libraryIdxArrNode);
			}
			if(pageNode!=null){
				int page=pageNode.asInt(1);
				paramMap.put("page", page);
			}
			if(pageSizeNode!=null){
				int pageSize=pageSizeNode.asInt(12);
				paramMap.put("pageSize", pageSize);
			}
			if(mSateNode!=null){
				String state=mSateNode.asText();
				stateMap.put("req", state);//1 alert
				//从 clouddbservice_devicemonitor 中查询 设备对应状态的ID 
				String stateResult=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceIdByState), stateMap, charset);
				if(JSONUtils.mayBeJSON(stateResult)){
					ResultEntity stateResultEntity=JsonUtils.fromJson(stateResult, ResultEntity.class);
					if(stateResultEntity.getResult()==null){
						//查询不到 某状态 设备ID 信息
					}else{
						//需要 在两边加冒号，需要去掉去掉图书馆ID
						paramMap.put("deviceIdByStateList","\""+StringUtils.collectionToDelimitedString((Collection<String>) stateResultEntity.getResult(), ",")+"\"");//得到某一种状态的 device_id List
					}
				}
			}
			if(keyWordNode!=null){
				String keyWord=keyWordNode.getTextValue();
				if(StringUtils.hasText(keyWord)){
					paramMap.put("keyWord", keyWord);
				}
			}
			if(machineTypeNode!=null){
				String machineType=machineTypeNode.getTextValue();
				if(StringUtils.hasText(machineType)){
					paramMap.put("machineType", machineType);
				}
			}
			if(libraryIdxNode!=null){
				int libraryIdx=libraryIdxNode.asInt();
				if(libraryIdx!=0){
					paramMap.put("library_idx", libraryIdx);
				}
			}
			if(operatorIdxNode!=null){
				operator_idx=operatorIdxNode.asInt();
				paramMap.put("operator_idx_Limit", operator_idx);
			}
			map.put("req", JsonUtils.toJson(paramMap));
		}else{
			paramMap.put("page", 1);
			paramMap.put("pageSize", 12);
			map.put("req", JsonUtils.toJson(paramMap));//按正常查询
		}
	
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceByParam), map, charset);
		
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	
	/*
	格式 req=
	 * {	
		 "page":page, //当前页数
		 "pageSize":pageSize //每页大小
	   }
	*/
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryServiceDeviceByParam(String req) {
		ResultEntity resultEntity=new ResultEntity();
		JsonNode node=JsonUtils.readTree(req);
		Map<String,String> map=new HashMap<>();
		Map<String,String> stateMap=new HashMap<>();
		Map<String,Object> paramMap=new HashMap<>();
		if(node!=null){
			boolean devGroupLimit=false;
			Integer operator_idx=null;
			JsonNode pageNode=node.get("page");
			JsonNode pageSizeNode=node.get("pageSize");
			JsonNode libraryIdxNode=node.get("library_idx");//
			JsonNode libraryIdxArrNode=node.get("lib_idx_list");//
			JsonNode operatorIdxNode=node.get("operator_idx_Limit");
			if(libraryIdxArrNode!=null){
				paramMap.put("lib_idx_list", libraryIdxArrNode);
			}
			if(pageNode!=null){
				int page=pageNode.asInt(1);
				paramMap.put("page", page);
			}
			if(pageSizeNode!=null){
				int pageSize=pageSizeNode.asInt(12);
				paramMap.put("pageSize", pageSize);
			}
			if(libraryIdxNode!=null){
				int libraryIdx=libraryIdxNode.asInt();
				if(libraryIdx!=0){
					paramMap.put("library_idx", libraryIdx);
				}
			}
			map.put("req", JsonUtils.toJson(paramMap));
		}else{
			paramMap.put("page", 1);
			paramMap.put("pageSize", 12);
			map.put("req", JsonUtils.toJson(paramMap));//按正常查询
		}
	
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryServiceDeviceByParam), map, charset);
		
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	
	@Override
	public ResultEntity UpdDevice(String req) {
		
		return postUrl(URL_UpdDevice, req);
	}
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity UpdDeviceMgmtPage(String req) {
		ResultEntity retResult=new ResultEntity();
		/**
		 * 这里需要通过 libId 查询出library_idx
		 */
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> node=JsonUtils.fromJson(req,Map.class);
			if(node!=null){
				if(node.get("library_idx")!=null){
					//存在libraryIdx的情况下不需要查询
				}else if(node.get("libId")!=null){
					String libId=node.get("libId").toString();
					if(StringUtils.hasText(libId)){
						//{lib_id:"M11"}
						ResultEntity result=postUrl(URL_selLibraryByIdxOrId, "{\"lib_id\":\""+libId+"\"}","json");
						if(result!=null&&result.getResult()!=null){
							Map<String,Object> map=(Map<String, Object>) result.getResult();
							if(map.get("library_idx")!=null){
								node.put("library_idx", map.get("library_idx"));
							}
						}
					}
				}
			}
		
			return postUrl(URL_UpdDeviceMgmtPage, JsonUtils.toJson(node));
		}
		return retResult;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity UpdHyDeviceMgmtPage(String req) {
		ResultEntity retResult=new ResultEntity();
		/**
		 * 这里需要通过 libId 查询出library_idx
		 */
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> node=JsonUtils.fromJson(req,Map.class);
			if(node!=null){
				if(node.get("library_idx")!=null){
					//存在libraryIdx的情况下不需要查询
				}else if(node.get("libId")!=null){
					String libId=node.get("libId").toString();
					if(StringUtils.hasText(libId)){
						//{lib_id:"M11"}
						ResultEntity result=postUrl(URL_selLibraryByIdxOrId, "{\"lib_id\":\""+libId+"\"}","json");
						if(result!=null&&result.getResult()!=null){
							Map<String,Object> map=(Map<String, Object>) result.getResult();
							if(map.get("library_idx")!=null){
								node.put("library_idx", map.get("library_idx"));
							}
						}
					}
				}
			}
		
			return postUrl(URL_UpdHyDeviceMgmtPage, JsonUtils.toJson(node));
		}
		return retResult;
	}
	@Override
	public ResultEntity checkAllConfigDataByDevIdx(String req) {
		return postUrl(URL_checkAllConfigDataByDevIdx, req);
	}
	@Override
	public ResultEntity compareMonitorConfig(String req) {
		return postUrl(URL_compareMonitorConfig, req);
	}
	@Override
	public ResultEntity loadAcsLogininfo(String req) {
		return postUrl(URL_loadAcsLogininfo, req);
	}
	@Override
	public ResultEntity queryDeviceIdbyLibIdx(String req) {
		return postUrl(URL_queryDeviceIdbyLibIdx, req);
	}
	@Override
	public ResultEntity getLibraryDevicesByPage(String req) {
		return postUrl(URL_getLibraryDevicesByPage, req);
	}
	@Override
	public ResultEntity queryDeviceIps(String req) {
		return postUrl(URL_queryDeviceIps, req);
	}
	@Override
	public String authTransferToLibrary(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String reqURL=requestURL.getRequestURL(URL_authTransferToLibrary);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	@Override
	public String devTransferToLibrary(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String reqURL=requestURL.getRequestURL(URL_devTransferToLibrary);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	
	@Override
	public String devTransferToLibraryLog(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String reqURL=requestURL.getRequestURL(URL_devTransferToLibraryLog);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	
	@Override
	public String queryAllRegion(String json) {
		String reqURL= requestURL.getRequestURL(URL_queryAllRegion);
		Map<String, String > map = new HashMap<>();
		map.put("json", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}	
	@Override
	public String queryDeviceRegion(String json) {
		String reqURL= requestURL.getRequestURL(URL_queryDeviceRegion);
		Map<String, String > map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	@Override
	public String GetLibPosition(String json) {
		String reqURL= requestURL.getRequestURL(URL_getLibPosition);
		Map<String, String > map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	@Override
	public String GetDevicePosition(String json) {
		String reqURL= requestURL.getRequestURL(URL_getDevicePosition);
		Map<String, String > map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	@Override
	public String saveDevicePosition(String json) {
		// TODO Auto-generated method stub
		String reqURL= requestURL.getRequestURL(URL_saveDevicePosition);
		Map<String, String > map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	
	@Override
	public String deleteLibraryPosition(String json) {
		// TODO Auto-generated method stub
		String reqURL= requestURL.getRequestURL(URL_deleteLibraryPosition);
		Map<String, String > map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public String insertFlagUploadFlag(String json) {
		// TODO Auto-generated method stub
		String reqURL = requestURL.getRequestURL(URL_insertFileUploadFlag);
		Map<String, String> map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
}
