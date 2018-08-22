package com.ssitcloud.devmgmt.service;

import javax.servlet.http.HttpServletRequest;

import com.ssitcloud.common.entity.ResultEntity;

public interface NavigationService {
	/**
	 * 查询图书馆馆藏书
	 * @param request
	 * @param req
	 * @return
	 */
	ResultEntity queryBookItemTotal(String req);
	
	/**
	 * 查询图书馆层架表总数
	 * @param request
	 * @param req
	 */
	ResultEntity queryBookShelfLayerTotal(String req);
	
	/**
	 * 查询图书馆的书架总数
	 * @param request
	 * @param req
	 */
	ResultEntity queryBookShelfTotal(String req);
}
