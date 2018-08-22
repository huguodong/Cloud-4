package com.ssitcloud.business.shelfmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.shelfmgmt.service.MetadataBookshelfService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class MetadataBookshelfServiceImpl extends BasicServiceImpl implements MetadataBookshelfService {

	private static final String URL_queryAllMetadataBookshelf = "queryAllMetadataBookshelf";
	private static final String URL_deleteMetadataBookshelf = "deleteMetadataBookshelf";
	private static final String URL_updateMetadataBookshelf = "updateMetadataBookshelf";
	private static final String URL_addMetadataBookshelf = "addMetadataBookshelf";
	
	@Override
	public ResultEntity queryAllMetadataBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryAllMetadataBookshelf, req);
	}

	@Override
	public ResultEntity updateMetadataBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_deleteMetadataBookshelf, req);
	}

	@Override
	public ResultEntity deleteMetadataBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateMetadataBookshelf, req);
	}

	@Override
	public ResultEntity addMetadataBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_addMetadataBookshelf, req);
	}

}
