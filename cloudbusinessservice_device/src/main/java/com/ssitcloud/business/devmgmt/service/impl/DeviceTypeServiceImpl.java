package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceTypeService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @package com.ssitcloud.devmgmt.service.impl
 * @comment
 * @data 2016年4月18日
 * @author hwl
 */
@Service
public class DeviceTypeServiceImpl extends BasicServiceImpl implements DeviceTypeService {

	protected static final String DEVICETYPE_SELECT_URL="SelectMetaDeviceType";
	
	protected static final String DEVICETYPE_DELETE_URL="DeleteMetaDeviceType";
	
	protected static final String DEVICETYPE_ADD_URL="AddMetaDeviceType";
	
	protected static final String DEVICETYPE_UPD_URL="UpdMetaDeviceType";
	
	protected static final String TYPE_SELECT_URL="SelectType";

	private static final String URL_selAllDeviceTypeGroupByType = "selAllDeviceTypeGroupByType";

	private static final String URL_queryDeviceTypeLogicObj = "queryDeviceTypeLogicObj";
	
	//根据名称查询设备类型 add 20161124
	private static final String URL_queryDeviceTypeByName = "queryDeviceTypeByName";
	
	@Override
	public String SelDeviceType(Map<String, String> map) {
		
		String reqURL=requestURL.getRequestURL(DEVICETYPE_SELECT_URL);
		/*Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);*/
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}
	
	@Override
	public String DeleteDeviceType(String reqInfo) {

		String reqURL=requestURL.getRequestURL(DEVICETYPE_DELETE_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	
	}

	@Override
	public String AddDeviceType(String reqInfo) {
		
		String reqURL=requestURL.getRequestURL(DEVICETYPE_ADD_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}

	@Override
	public String UpdDeviceType(String reqInfo) {
		
		String reqURL=requestURL.getRequestURL(DEVICETYPE_UPD_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}

	@Override
	public String SelectType(String reqInfo) {
		String reqURL=requestURL.getRequestURL(TYPE_SELECT_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public ResultEntity selAllDeviceTypeGroupByType() {
		ResultEntity result=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		map.put("req", "");
		String resString=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selAllDeviceTypeGroupByType), map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(resString)){
			result=JsonUtils.fromJson(resString, ResultEntity.class);
		}
		return result;
	}

	@Override
	public ResultEntity queryDeviceTypeLogicObj(String req) {
		return postUrl(URL_queryDeviceTypeLogicObj, req);
	}
	
	@Override
	public ResultEntity queryDeviceTypeByName(String req) {
		return postUrl(URL_queryDeviceTypeByName, req);
	}

}
