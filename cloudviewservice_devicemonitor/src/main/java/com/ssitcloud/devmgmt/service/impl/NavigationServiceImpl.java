package com.ssitcloud.devmgmt.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.devmgmt.service.NavigationService;
@Service
public class NavigationServiceImpl extends BasicServiceImpl implements NavigationService {
	public static final String URL_QueryBookItemTotal="queryBookItemTotal";
	public static final String URL_QueryBookShelfLayerTotal="queryBookShelfLayerTotal";
	public static final String URL_QueryBookShelfTotal="queryBookShelfTotal";
	
	@Override
	public ResultEntity queryBookItemTotal(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_QueryBookItemTotal, req);
	}
	@Override
	public ResultEntity queryBookShelfLayerTotal(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_QueryBookShelfLayerTotal, req);
	}
	@Override
	public ResultEntity queryBookShelfTotal(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_QueryBookShelfTotal, req);
	}
}
