package com.ssitcloud.view.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceDisplayConfig;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.devregister.entity.DeviceRegisterParam;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.service.DeviceRegisteService;

/**
 * <p>2016年4月25日 下午4:36:41
 * @author hjc
 */
@Service
public class DeviceRegisteServiceimpl extends BasicServiceImpl implements DeviceRegisteService{
	private static final String SEL_ALL_METADATA_DEVICETYPE = "SelAllMetadataDeviceType";
	private static final String SEL_ALL_EXTTEMPLIST = "SelAllExtTempList";
	private static final String SEL_ALL_RUNTEMPLIST = "SelAllRunTempList";
	private static final String SEL_ALL_MONITOR_TEMPLIST = "SelAllMonitorTempList";
	private static final String SEL_ALL_DBSYNC_TEMPLIST = "SelAllDBSyncTempList";
	private static final String HAS_DEVICE_CODE = "hasDeviceCode";
	private static final String DEVICE_REGISTER = "deviceRegister";
	private static final String HYDEVICE_REGISTER = "hydeviceRegister";
	private static final String SELECT_METADATA_LOGICOBJ="SelectMetadataLogicObj";
	private static final String SELECT_METADATA_EXTMODEL="SelMetadataExtModel";
	private static final String SEL_ALL_EXTMODEL_AND_LOGICOBJ = "selAllExtModelAndLogicObj";
	private static final String SEL_LIBRARY_BY_IDXORID = "selLibraryByIdxOrId";
	private static final String ISEXIST_DEVICE_WITH_IDORIDX = "isExistDeviceWithIdOrIdx";
	private static final String SEL_LIBRARYTEMP_AND_DEVICEIDS = "selLibraryTempAndDeviceIds";
	private static final String SEL_DEVICE_COUNT_BY_IDS = "selDeviceCountByIds";
	private static final String URL_queryDeviceByDeviceCode = "queryDeviceByDeviceCode";
	private static final String URL_getAscTempList = "getAscTempList";
	private static final String URL_queryMasterLibraryBySlaveIdx = "queryMasterLibraryBySlaveIdx";
	private static final String URL_queryAllRegion = "queryAllRegion";
	
	//2016-11-24添加
	private static final String URL_getDisplayByTypeId = "getDisplayByTypeId";
	private static final String URL_queryDeviceTypeByName = "queryDeviceTypeByName";
	private static final String URL_queryDeviceIps = "queryDeviceIps";
	private static final String URL_transferToNewLib = "transferToNewLib";
	

	
	@Override
	public String SelAllMetadataDeviceType(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_METADATA_DEVICETYPE);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}


	@Override
	public String getAllExtTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_EXTTEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	
	@Override
	public String getAllRunTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_RUNTEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}


	@Override
	public String getAllMonitorTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_MONITOR_TEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String getAllDbSyncTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_DBSYNC_TEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}


	@Override
	public String deviceRegister(DeviceRegisterParam registerParam) {
		String reqURL=requestURL.getRequestURL(DEVICE_REGISTER);
		Map<String,String> map=new HashMap<>();
		map.put("json", JsonUtils.toJson(registerParam));
		String result=HttpClientUtil.doPostForRegister(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	
	@Override
	public String hasDeviceCode(String deviceCode) {
		String reqURL=requestURL.getRequestURL(HAS_DEVICE_CODE);
		Map<String,String> map=new HashMap<>();
		map.put("json", deviceCode);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	
	@Override
	public String queryDeviceByDeviceCode(String deviceCode) {
		String reqURL=requestURL.getRequestURL(URL_queryDeviceByDeviceCode);
		Map<String,String> map=new HashMap<>();
		map.put("json", deviceCode);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	
	@Override
	public String SelectMetadataLogicObj(String json) {
		String reqURL=requestURL.getRequestURL(SELECT_METADATA_LOGICOBJ);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	
	@Override
	public String SelMetadataExtModel(String json) {
		String reqURL=requestURL.getRequestURL(SELECT_METADATA_EXTMODEL);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String selAllExtModelAndLogicObj(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_EXTMODEL_AND_LOGICOBJ);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	
	@Override
	public String selLibraryByIdxOrId(String json) {
		String reqURL=requestURL.getRequestURL(SEL_LIBRARY_BY_IDXORID);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}


	@Override
	public String isExistDeviceWithIdOrIdx(String json) {
		String reqURL=requestURL.getRequestURL(ISEXIST_DEVICE_WITH_IDORIDX);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	
	
	@Override
	public String selDeviceUserByLibraryIdx(String json) {
		String reqURL= requestURL.getRequestURL(SEL_LIBRARYTEMP_AND_DEVICEIDS);
		Map<String, String > map = new HashMap<>();
		map.put("json", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	
	

	@Override
	public String selDeviceCountByIds(String json) {
		String reqURL= requestURL.getRequestURL(SEL_DEVICE_COUNT_BY_IDS);
		Map<String, String > map = new HashMap<>();
		map.put("json", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}


	@Override
	public String getAscTempList(String json) {
		String reqURL= requestURL.getRequestURL(URL_getAscTempList);
		Map<String, String > map = new HashMap<>();
		map.put("json", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	

	@Override
	public DeviceDisplayConfig getDisplayByTypeId(String json) {
		String reqURL = requestURL.getRequestURL(URL_getDisplayByTypeId);
		Map<String, String> map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (JSONUtils.mayBeJSON(result)) {
			JSONObject obj = JSONObject.fromObject(result);
			if (obj.getBoolean("state")) {
				JSONObject config = obj.getJSONObject("result");
				return (DeviceDisplayConfig) JSONObject.toBean(config, DeviceDisplayConfig.class);
			}
		}
		return null;
	}

	@Override
	public MetadataDeviceTypeEntity queryDeviceTypeByName(String json) {
		String reqURL = requestURL.getRequestURL(URL_queryDeviceTypeByName);
		Map<String, String> map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
//		if (JSONUtils.mayBeJSON(result)) {
//			JSONObject obj = JSONObject.fromObject(result);
//			if (obj.getBoolean("state")) {
//				JSONObject array = obj.getJSONObject("result");
//				return (MetadataDeviceTypeEntity) JSONObject.toBean(array, MetadataDeviceTypeEntity.class);
//			}
//		}
		if(StringUtils.isNotBlank(result)){
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if(resultEntity.getState()){
				MetadataDeviceTypeEntity metadataDeviceTypeEntity = JsonUtils.fromJson(JsonUtils.toJson(resultEntity.getResult()), MetadataDeviceTypeEntity.class);
				return metadataDeviceTypeEntity;
			}
		}
		return null;
	}

	@Override
	public String queryDeviceIps(String json) {
		String reqURL = requestURL.getRequestURL(URL_queryDeviceIps);
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		ResultEntity re = JsonUtils.fromJson(result, ResultEntity.class);
		if (re.getState()) {
			String ipStr = re.getResult() + "";
			ipStr = ipStr.replaceAll("\\{", "").replaceAll("\\}", "");
			if (ipStr.indexOf("=") != -1) {
				return ipStr.split("\\=")[1];
			}
		}
		return null;
	}

	@Override
	public ResultEntity transferToNewLib(String req) {
		String reqURL = requestURL.getRequestURL(URL_transferToNewLib);
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return JsonUtils.fromJson(result, ResultEntity.class);
	}
	
	@Override
	public boolean isHHMaster(String req) {
		String reqURL = requestURL.getRequestURL(URL_queryMasterLibraryBySlaveIdx);
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		ResultEntity resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		if(resultEntity.getState()){
			JSONObject obj=JSONObject.fromObject(resultEntity.getResult());
			Map<String,Object> library=(Map<String,Object>)JSONObject.toBean(obj, Map.class);
			if(library.get("lib_id")==null||"".equals(library.get("lib_id")+"")||library.get("lib_id").toString().trim().length()==0){
				return false;
			}else{
				String masterLibId = library.get("lib_id")+"";
				if ("HHTEST".equals(masterLibId)) {
					return true;
				}
			}
		}
		return false;
	}


	@Override
	public ResultEntity queryAllRegion(String req) {
		ResultEntity resultEntity = new ResultEntity();
		String reqURL = requestURL.getRequestURL(URL_queryAllRegion);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(StringUtils.isNotBlank(result)){
			resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}
	
	@Override
	public String hydeviceRegister(DeviceRegisterParam registerParam) {
		String reqURL=requestURL.getRequestURL(HYDEVICE_REGISTER);
		Map<String,String> map=new HashMap<>();
		map.put("json", JsonUtils.toJson(registerParam));
		String result=HttpClientUtil.doPostForRegister(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
}
