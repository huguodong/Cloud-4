package com.ssitcloud.devmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.devmgmt.service.LibraryService;

@Service
public class LibraryServiceImpl extends BasicServiceImpl implements LibraryService{

	
	
	private static final String URL_querySlaveLibraryByMasterIdx = "querySlaveLibraryByMasterIdx";
	private static final String URL_queryAllMasterLibAndSlaveLib = "queryAllMasterLibAndSlaveLib";
	private static final String URL_getLibIdAndLibIdx = "getLibIdAndLibIdx";

	@Override
	public ResultEntity querySlaveLibraryByMasterIdx(String req) {
		return postUrl(URL_querySlaveLibraryByMasterIdx, req);
	}

	@Override
	public ResultEntity queryAllMasterLibAndSlaveLib(String req) {
		return postUrl(URL_queryAllMasterLibAndSlaveLib, req);
	}

	@Override
	public ResultEntity getLibIdAndLibIdx(String req) {
		return postUrl(URL_getLibIdAndLibIdx, req);
	}

}
