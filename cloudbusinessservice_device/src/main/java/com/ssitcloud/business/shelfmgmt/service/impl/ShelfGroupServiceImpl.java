package com.ssitcloud.business.shelfmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.shelfmgmt.service.ShelfGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class ShelfGroupServiceImpl extends BasicServiceImpl implements ShelfGroupService {

	private static final String URL_queryAllShelfGroup = "queryAllShelfGroup";
	private static final String URL_deleteShelfGroup = "deleteShelfGroup";
	private static final String URL_updateShelfGroup = "updateShelfGroup";
	private static final String URL_addShelfGroup = "addShelfGroup";
	
	@Override
	public ResultEntity queryAllShelfGroup(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryAllShelfGroup, req);
	}

	@Override
	public ResultEntity updateShelfGroup(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateShelfGroup, req);
	}

	@Override
	public ResultEntity deleteShelfGroup(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_deleteShelfGroup, req);
	}

	@Override
	public ResultEntity addShelfGroup(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_addShelfGroup, req);
	}

}
