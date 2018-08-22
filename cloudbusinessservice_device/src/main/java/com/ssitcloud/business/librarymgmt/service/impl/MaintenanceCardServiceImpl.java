package com.ssitcloud.business.librarymgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.librarymgmt.service.MaintenanceCardService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class MaintenanceCardServiceImpl extends BasicServiceImpl implements MaintenanceCardService {
	
	private static final String URL_insertMaintenanceCard = "insertMaintenanceCard";
	private static final String URL_updateMaintenanceCard = "updateMaintenanceCard";
	private static final String URL_deleteMaintenanceCard = "deleteMaintenanceCard";
	private static final String URL_queryMaintenanceCardByFuzzy = "queryMaintenanceCardByFuzzy";
	private static final String URL_queryMaintenanceCard = "queryMaintenanceCard";
	private static final String URL_updateOperatorMaintenanceCard = "updateOperatorMaintenanceCard";
	
	@Override
	public String insertMaintenanceCard(String info) {
		
		String reqURL= requestURL.getRequestURL(URL_insertMaintenanceCard);
		Map<String, String > map = new HashMap<>();
		map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	@Override
	public String updateMaintenanceCard(String info) {
		
		String reqURL= requestURL.getRequestURL(URL_updateMaintenanceCard);
		Map<String, String > map = new HashMap<>();
		map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	@Override
	public String deleteMaintenanceCard(String info) {
		
		String reqURL= requestURL.getRequestURL(URL_deleteMaintenanceCard);
		Map<String, String > map = new HashMap<>();
		map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	
	@Override
	public String queryMaintenanceCardByFuzzy(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_queryMaintenanceCardByFuzzy);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public ResultEntity queryMaintenanceCard(String req) {
		return postUrl(URL_queryMaintenanceCard, req);
	}
	@Override
	public ResultEntity updateOperatorMaintenanceCard(String req) {
		return postUrl(URL_updateOperatorMaintenanceCard, req);
	}
	
}
