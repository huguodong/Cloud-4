package com.ssitcloud.view.librarymgmt.service.impl;

import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.librarymgmt.service.MaintenanceCardService;

@Service
public class MaintenanceCardServiceImpl extends BasicServiceImpl implements MaintenanceCardService{
	
	private static final String URL_insertMaintenanceCard = "insertMaintenanceCard";
	private static final String URL_updateMaintenanceCard = "updateMaintenanceCard";
	private static final String URL_deleteMaintenanceCard = "deleteMaintenanceCard";
	private static final String URL_queryMaintenanceCardByFuzzy = "queryMaintenanceCardByFuzzy";

	@Override
	public String insertMaintenanceCard(Map<String, String> map) {
		
		String reqURL= requestURL.getRequestURL(URL_insertMaintenanceCard);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	
	@Override
	public String updateMaintenanceCard(Map<String, String> map) {
		
		String reqURL= requestURL.getRequestURL(URL_updateMaintenanceCard);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	@Override
	public String deleteMaintenanceCard(Map<String, String> map) {
		
		String reqURL= requestURL.getRequestURL(URL_deleteMaintenanceCard);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	
	@Override
	public String queryMaintenanceCardByFuzzy(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_queryMaintenanceCardByFuzzy);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	

}
