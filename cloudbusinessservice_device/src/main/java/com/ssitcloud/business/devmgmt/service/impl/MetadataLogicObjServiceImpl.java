package com.ssitcloud.business.devmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.devmgmt.service.MetadataLogicObjService;
import com.ssitcloud.common.entity.ResultEntity;
@Service
public class MetadataLogicObjServiceImpl extends BasicServiceImpl implements MetadataLogicObjService{

	private static final String URL_SelectMetadataLogicObj = "SelectMetadataLogicObj";

	@Override
	public ResultEntity SelectMetadataLogicObj(String req) {
		return postUrl(URL_SelectMetadataLogicObj, req);
	}

}
