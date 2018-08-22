package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.devmgmt.service.DeviceGroupService;
import com.ssitcloud.common.entity.ResultEntity;
/**
 * 
 * @package com.ssitcloud.devmgmt.service.impl
 * @comment
 * @data 2016年4月21日
 * @author hwl
 */
@Service
public class DeviceGroupServiceImpl extends BasicServiceImpl implements DeviceGroupService {

	protected static final String DEVICEGROUP_SELECT_URL="SelectDeviceGroup";
	protected static final String DEVICEGROUP_DELETE_URL="DeleteDeviceGroup";
	protected static final String DEVICEGROUP_ADD_URL="AddDeviceGroup";
	protected static final String DEVICEGROUP_UPD_URL="UpdDeviceGroup";
	protected static final String GROUP_SELECT_URL="SelectGroup";
	protected static final String LIBRARY_SELECT_URL="SelectLibrary";
	private static final String URL_SelectGroupByParam = "SelectGroupByParam";
	private static final String URL_selectGroupByDeviceIdx = "selectGroupByDeviceIdx";
	@Override
	public String SelDeviceGroup(Map<String, String> map) {

		String reqURL=requestURL.getRequestURL(DEVICEGROUP_SELECT_URL);
		/*Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);*/
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}

	@Override
	public String DeleteDeviceGroup(String reqInfo) {
		
		String reqURL=requestURL.getRequestURL(DEVICEGROUP_DELETE_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}

	@Override
	public String AddDeviceGroup(String reqInfo) {
		
		String reqURL=requestURL.getRequestURL(DEVICEGROUP_ADD_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}

	@Override
	public String UpdDeviceGroup(String reqInfo) {
		
		String reqURL=requestURL.getRequestURL(DEVICEGROUP_UPD_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
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

	@Override
	public String SelectGroup(String reqInfo) {
		String reqURL=requestURL.getRequestURL(GROUP_SELECT_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}

	@Override
	public ResultEntity SelectGroupByParam(String req) {
		return postUrl(URL_SelectGroupByParam, req);
	}

	@Override
	public ResultEntity selectGroupByDeviceIdx(String req) {
		return postUrl(URL_selectGroupByDeviceIdx, req);
	}

	
	
}
