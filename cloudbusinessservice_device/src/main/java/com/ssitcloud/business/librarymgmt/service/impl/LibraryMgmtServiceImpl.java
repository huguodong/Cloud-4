package com.ssitcloud.business.librarymgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.librarymgmt.service.LibraryMgmtService;
import com.ssitcloud.common.entity.ResultEntity;
@Service
public class LibraryMgmtServiceImpl extends BasicServiceImpl implements LibraryMgmtService{

	private static final String URL_selLibraryServiceTemplateByIdx = "selLibraryServiceTemplateByIdx";
	private static final String URL_queryAllMasterLibAndSlaveLib = "queryAllMasterLibAndSlaveLib";
	private static final String URL_getLibIdAndLibIdx = "getLibIdAndLibIdx";
	private static final String URL_querylibInfoByCurUserEXT1 = "querylibInfoByCurUserEXT1";
	private static final String URL_saveLibPosition = "saveLibPosition";

	@Override
	public ResultEntity selLibraryServiceTemplateByIdx(String req) {
		return postUrl(URL_selLibraryServiceTemplateByIdx, req);
	}

	@Override
	public ResultEntity queryAllMasterLibAndSlaveLib(String req) {
		return postUrl(URL_queryAllMasterLibAndSlaveLib, req);
	}

	@Override
	public ResultEntity getLibIdAndLibIdx(String req) {
		return postUrl(URL_getLibIdAndLibIdx, req);
	}

	@Override
	public ResultEntity querylibInfoByCurUserEXT1(String req) {
		
		return postUrl(URL_querylibInfoByCurUserEXT1, req);
	}

	@Override
	public ResultEntity saveLibPosition(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_saveLibPosition, req);
	}
}
