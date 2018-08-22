package com.ssitcloud.view.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.service.DeviceService;

@Service
public class DeviceServiceImpl extends BasicServiceImpl implements DeviceService {
	private static final String URL_INSERTFILEUPLOADFLAG = "insertFileUploadFlag";
	
	@Override
	public ResultEntity insertFileUploadFlag(String req) {
		ResultEntity resultEntity=new ResultEntity();
		String reqURL = requestURL.getRequestURL(URL_INSERTFILEUPLOADFLAG);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	
	@Override
	public String getUrl(String urlId){
		return requestURL.getRequestURL(urlId);
	}
}
