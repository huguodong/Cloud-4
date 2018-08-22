package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceMonConfService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class DeviceMonConfServiceImpl extends BasicServiceImpl implements DeviceMonConfService{

	public static final String URL_queryDeviceMonitorByParam="queryDeviceMonitorByParam";
	
	public static final String URL_AddNewDeviceMonitorConfAndTemp = "AddNewDeviceMonitorConfAndTemp";

	public static final String URL_UpdNewDeviceMonitorConfAndTemp = "UpdNewDeviceMonitorConfAndTemp";

	public static final String URL_DelNewDeviceMonitorConfAndTemp = "DelNewDeviceMonitorConfAndTemp";

	public static final String URL_AddNewDeviceMonitorTemp = "AddNewDeviceMonitorTemp";

	private static final String URL_SelPlcLogicObjectEntity = "SelPlcLogicObjectEntity";

	private static final String URL_SelDevMonConfMetaLogObjByDeviceMonTplIdx = "SelDevMonConfMetaLogObjByDeviceMonTplIdx";

	private static final String URL_queryAlertColor = "queryAlertColor";

	private static final String URL_UpdMonitor = "UpdateMonitor";
	private static final String URL_DelMonitor = "DeleteMonitor";
	private static final String URL_AddMonitor = "ADDMonitor";
	private static final String URL_SelMonitor = "SelDeviceMonitorConf";
	public static final String URL_GetCurrentDevColorSet="GetCurrentDevColorSet";
	
	@Override
	public ResultEntity queryDeviceMonitorByParam(String req) {
		return postUrl(URL_queryDeviceMonitorByParam,req);
	}

	@Override
	public ResultEntity AddNewDeviceMonitorConfAndTemp(String req) {
		return postUrl(URL_AddNewDeviceMonitorConfAndTemp,req);
	}

	@Override
	public ResultEntity UpdNewDeviceMonitorConfAndTemp(String req) {
		return postUrl(URL_UpdNewDeviceMonitorConfAndTemp,req);
	}

	@Override
	public ResultEntity DelNewDeviceMonitorConfAndTemp(String req) {
		return postUrl(URL_DelNewDeviceMonitorConfAndTemp,req);
	}

	@Override
	public ResultEntity AddNewDeviceMonitorTemp(String req) {
		return postUrl(URL_AddNewDeviceMonitorTemp,req);
	}

	@Override
	public ResultEntity SelPlcLogicObjectEntity() {
		return postUrl(URL_SelPlcLogicObjectEntity,null);
	}

	@Override
	public ResultEntity SelDevMonConfMetaLogObjByDeviceMonTplIdx(String req) {
		return postUrl(URL_SelDevMonConfMetaLogObjByDeviceMonTplIdx,req);
	}

	@Override
	public ResultEntity queryAlertColor_bus(String req) {
		return postUrl(URL_queryAlertColor,req);
	}

	@Override
	public ResultEntity UpdMonitor(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_UpdMonitor);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
		
	}

	@Override
	public ResultEntity AddMonitor(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_AddMonitor);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
		
	}

	@Override
	public ResultEntity DelMonitor(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_DelMonitor);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
	
	}

	@Override
	public ResultEntity SelMonitor(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_SelMonitor);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
	}
	
	
	@Override
	public ResultEntity GetCurrentDevColorSet(String req) {
		return postUrl(URL_GetCurrentDevColorSet, req);
	}


}
