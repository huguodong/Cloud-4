package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.devmgmt.service.DeviceDBsyncConfigService;
/**
 * 
 * @author hwl
 *
 */
@Service
public class DeviceDBsyncConfigServiceImpl extends BasicServiceImpl implements DeviceDBsyncConfigService {

	public static final String URL_Selectdbsync="SelDeviceDbsyncBytype";
	
	public static final String URL_Updatadbsync="UpdDevDbsyncList";
	
	
	public static final String URL_Insertdbsync="AddDbsyncConfig";
	
	public static final String LIBRARY_SELECT_URL="SelectLibrary";
	
	public static final String URL_Updatadevconf= "UpdDeviceConfig";
	
	public static final String URL_Deldevicedbsync= "DelDevDbsyncList";
	
	public static final String URL_Seldbsynctemp= "SelDbsyncTemp";
	public static final String URL_Deldbsynctemp= "DelDbsyncTemp";
	public static final String URL_Upddbsynctemp= "UpdDbsyncTemp";
	public static final String URL_Adddbsynctemp= "AddNewDbsyncTemp";
	@Override
	public String SelDeviceDbsync(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_Selectdbsync);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	@Override
	public String UpdDeviceDbsync(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_Updatadbsync);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}

	@Override
	public String SelLibrary(String json) {
		
		String reqURL=requestURL.getRequestURL(LIBRARY_SELECT_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}
	@Override
	public String AddDeviceDbsync(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_Insertdbsync);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	@Override
	public String delDevDBsync(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_Deldevicedbsync);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	@Override
	public String updDevConfig(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_Updatadevconf);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	
	@Override
	public String selDbsynctemp(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_Seldbsynctemp);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	@Override
	public String updDbsynctemp(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_Upddbsynctemp);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	@Override
	public String addDbsynctemp(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_Adddbsynctemp);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	@Override
	public String delDbsynctemp(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_Deldbsynctemp);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
}
