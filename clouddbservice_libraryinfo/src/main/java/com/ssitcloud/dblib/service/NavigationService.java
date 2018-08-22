package com.ssitcloud.dblib.service;

import javax.servlet.http.HttpServletRequest;

import com.ssitcloud.common.entity.ResultEntity;

public interface NavigationService {
	public ResultEntity queryBookItemTotal(String req);
	
	public ResultEntity queryBookShelfLayerTotal(String req);
	
	public ResultEntity queryBookShelfTotal(String req);
}
