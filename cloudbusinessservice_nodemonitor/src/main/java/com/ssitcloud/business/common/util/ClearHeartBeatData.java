package com.ssitcloud.business.common.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssitcloud.business.nodemgmt.service.ClearHeartBeatDataService;

public class ClearHeartBeatData {
	
	@Autowired
	private ClearHeartBeatDataService beatDataService;
	
	/**
	 * 清空心跳数据
	 * 
	 * **/
	public void clearHeartBeatData(){
		beatDataService.clearHeartBeatData(); 
	}

}
