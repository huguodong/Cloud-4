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
import com.ssitcloud.view.devmgmt.service.DeviceTypeService;
/**
 * 
 * @package com.ssitcloud.devmgmt.service.impl
 * @comment
 * @data 2016年4月18日
 * @author hwl
 */
@Service
public class DeviceTypeServiceImpl extends BasicServiceImpl implements
		DeviceTypeService {
	
	protected static final String DEVICETYPE_SELECT_URL="SelectMetaDeviceType";
	
	protected static final String DEVICETYPE_DELETE_URL="DeleteMetaDeviceType";
	
	protected static final String DEVICETYPE_ADD_URL="AddMetaDeviceType";
	
	protected static final String DEVICETYPE_UPD_URL="UpdMetaDeviceType";
	
	protected static final String TYPE_QUERY="SelectType";

	private static final String URL_selAllDeviceTypeGroupByType = "selAllDeviceTypeGroupByType";

	private static final String URL_queryDeviceTypeLogicObj = "queryDeviceTypeLogicObj";

	private static final String URL_SelectMetadataLogicObj = "SelectMetadataLogicObj";
	
	private static final String URL_selectMetadataDeviceDb = "selectMetadataDeviceDb";
	
	private static final String URL_selectMetadataDeviceDbAndTableInfo = "selectMetadataDeviceDbAndTableInfo";
	
	private static final String URL_qyeryDeviceExtList = "qyeryDeviceExtList";
	
	
	private static final String URL_selAllExtModelAndLogicObj = "selAllExtModelAndLogicObj";
	
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
		// TODO Auto-generated method stub
		String reqURL=requestURL.getRequestURL(DEVICETYPE_DELETE_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String AddDeviceType(String reqInfo) {
		// TODO Auto-generated method stub
		String reqURL=requestURL.getRequestURL(DEVICETYPE_ADD_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String UpdDeviceType(String reqInfo) {
		// TODO Auto-generated method stub
		String reqURL=requestURL.getRequestURL(DEVICETYPE_UPD_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String QueryType(String reqInfo) {
		String reqURL=requestURL.getRequestURL(TYPE_QUERY);
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
			result.setState(true);
		}
		return result;
	}

	@Override
	public ResultEntity queryDeviceTypeLogicObj(String req) {
		return postUrl(URL_queryDeviceTypeLogicObj, req);
	}

	@Override
	public ResultEntity selectMetadataLogicObj(String req) {
		return postUrl(URL_SelectMetadataLogicObj, req);
	}
	
	@Override
	public ResultEntity selectMetadataDeviceDb(String req) {
		return postUrl(URL_selectMetadataDeviceDb, req);
	}
	
	@Override
	public ResultEntity selectMetadataDeviceDbAndTableInfo(String req) {
		return postUrl(URL_selectMetadataDeviceDbAndTableInfo, req);
	}
	
	@Override
	public ResultEntity qyeryDeviceExtList(String req) {
		return postUrl(URL_selAllExtModelAndLogicObj, req);
	}

	
}
