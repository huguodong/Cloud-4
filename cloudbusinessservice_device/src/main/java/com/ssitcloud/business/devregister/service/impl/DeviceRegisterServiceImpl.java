package com.ssitcloud.business.devregister.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devregister.param.AddDeviceParam;
import com.ssitcloud.business.devregister.service.DeviceRegisterService;

@Service
public class DeviceRegisterServiceImpl extends BasicServiceImpl implements DeviceRegisterService{

	protected static final String DEVICE_REGISTER_URL="AddRegisterInfo";
	protected static final String SEL_ALL_METADATA_DEVICETYPE="SelAllMetadataDeviceType";
	protected static final String SEL_ALL_EXTTEMPLIST="SelAllExtTempList";
	protected static final String SEL_ALL_RUNTEMPLIST="SelAllRunTempList";
	protected static final String SEL_ALL_MONITOR_TEMPLIST="SelAllMonitorTempList";
	protected static final String SEL_ALL_DBSYNC_TEMPLIST="SelAllDBSyncTempList";
	protected static final String HAS_DEVICE_CODE = "hasDeviceCode";
	protected static final String DEVICE_REGISTER = "deviceRegister";
	protected static final String HYDEVICE_REGISTER = "hydeviceRegister";
	protected static final String ADD_DEVICE = "addDevice";
	protected static final String SEL_ALL_EXTMODEL_AND_LOGICOBJ = "selAllExtModelAndLogicObj";
	protected static final String SEL_LIBRARY_BY_IDXORID = "selLibraryByIdxOrId";
	protected static final String ISEXIST_DEVICE_WITH_IDORIDX = "isExistDeviceWithIdOrIdx";
	protected static final String SEL_LIBRARYTEMP_AND_DEVICEIDS = "selLibraryTempAndDeviceIds";
	protected static final String SEL_DEVICE_COUNT_BY_IDS = "selDeviceCountByIds";
	protected static final String URL_queryDeviceByDeviceCode = "queryDeviceByDeviceCode";
	protected static final String URL_getAscTempList = "getAscTempList";

	

	
	@Override
	public String deviceRegister(String devRegInfo){
		String reqURL=requestURL.getRequestURL(DEVICE_REGISTER_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", devRegInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}

	@Override
	public String selAllMetadataDeviceType(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_METADATA_DEVICETYPE);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String selAllExtTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_EXTTEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String selAllRunTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_RUNTEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String selAllMonitorTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_MONITOR_TEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String selAllDBSyncTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_DBSYNC_TEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String hasDeviceCode(String json) {
		String reqURL=requestURL.getRequestURL(HAS_DEVICE_CODE);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String queryDeviceByDeviceCode(String json) {
		String reqURL=requestURL.getRequestURL(URL_queryDeviceByDeviceCode);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String registerDevice(String json) {
		String reqURL=requestURL.getRequestURL(DEVICE_REGISTER);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String addDevice(AddDeviceParam deviceParam) {
		String reqURL=requestURL.getRequestURL(ADD_DEVICE);
		Map<String,String> map=new HashMap<>();
		map.put("json", JsonUtils.toJson(deviceParam));
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String selAllExtModelAndLogicObj(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_EXTMODEL_AND_LOGICOBJ);
		Map<String,String> map=new HashMap<>();
		map.put("json",json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String selLibraryByIdxOrId(String json) {
		String reqURL=requestURL.getRequestURL(SEL_LIBRARY_BY_IDXORID);
		Map<String,String> map=new HashMap<>();
		map.put("json",json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String isExistDeviceWithIdOrIdx(String json) {
		String reqURL=requestURL.getRequestURL(ISEXIST_DEVICE_WITH_IDORIDX);
		Map<String,String> map=new HashMap<>();
		map.put("json",json);
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
	public String registerHYDevice(String json) {
		String reqURL=requestURL.getRequestURL(HYDEVICE_REGISTER);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
}
