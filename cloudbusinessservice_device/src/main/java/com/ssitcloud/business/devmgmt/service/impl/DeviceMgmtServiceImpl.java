package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.devmgmt.service.DeviceMgmtService;
/**
 * 
 * @package com.ssitcloud.devmgmt.service.impl
 * @comment
 * @data 2016年4月14日
 * @author hwl
 */
@Service
public class DeviceMgmtServiceImpl extends BasicServiceImpl implements DeviceMgmtService {

	protected static final String DEVICE_SELECT_URL="SelectDeviceMgmt";
	protected static final String DEVICE_ADD_URL = "AddDeviceMgmt";
	protected static final String DEVICE_DELETE_URL = "DeleteDeviceMgmt";
	@Override
	public String SelectDeviceMgmt(String reqInfo) {
		String reqURL=requestURL.getRequestURL(DEVICE_SELECT_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}
	@Override
	public String DeleteDeviceMgmt(String reqInfo) {
		String reqURL=requestURL.getRequestURL(DEVICE_DELETE_URL);
		Map<String, String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}
	
	
	
	/*@Override
	public String AddDeviceMgmt(String reqInfo) {
		String reqURL=requestURL.getRquestURL(DEVICE_ADD_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}
*/
}
