package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.devmgmt.service.MetaOperCmdService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class MetaOperCmdServiceImpl extends BasicServiceImpl implements MetaOperCmdService{
	
	public static final String QUERY_META_OPER_CMD="SelectMetaOperCmd";
	public static final String URL_selectCmdType="selectCmdType";
	private static final String URL_selectCardCmdType="selectCardCmdType";
	private static final String URL_selectDeviceOperLogCmd="selectDeviceOperLogCmd";
	private static final String URL_queryServgroupByparam = "queryServgroupByparam";
	
	@Override
	public String SelMetaOperCmd(){
		String url=requestURL.getRequestURL(QUERY_META_OPER_CMD);
		Map<String,String> map=new HashMap<>();
		return  HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	@Override
	public ResultEntity selectCmdType() {
		return postUrl(URL_selectCmdType, "");
	}
	@Override
	public ResultEntity selectCardCmdType() {
		return postUrl(URL_selectCardCmdType, "");
	}
	
	@Override
	public ResultEntity selectDeviceOperLogCmd() {
		return postUrl(URL_selectDeviceOperLogCmd, "");
	}
	
	@Override
	public ResultEntity queryServgroupByparam(String req) {
		return postUrl(URL_queryServgroupByparam, req);
	}
}
