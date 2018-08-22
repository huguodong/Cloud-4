package com.ssitcloud.view.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.devmgmt.service.DeviceGroupService;

@Service
public class DeviceGroupServiceImpl extends BasicServiceImpl implements DeviceGroupService {
	
	protected static final String DEVICEGROUP_SELECT_URL="SelectDeviceGroup";
	protected static final String DEVICEGROUP_DELETE_URL="DeleteDeviceGroup";
	protected static final String DEVICEGROUP_ADD_URL="AddDeviceGroup";
	protected static final String DEVICEGROUP_UPD_URL="UpdDeviceGroup";
	protected static final String GROUP_SELECT_URL="SelectGroup";
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
	public String SelectGroup(String reqInfo) {
		String reqURL=requestURL.getRequestURL(GROUP_SELECT_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}

	@Override
	public ResultEntity SelectGroupByParam(String req) {
		ResultEntity resultEntity=new ResultEntity();
		String reqURL = requestURL.getRequestURL(URL_SelectGroupByParam);
		Map<String,String> map=new HashMap<>();
		map.put("json", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
//		return postUrl(URL_SelectGroupByParam, req);
	}

	@Override
	public ResultEntity selectGroupByDeviceIdx(String req) {
		return postUrl(URL_selectGroupByDeviceIdx, req);
	}

}
