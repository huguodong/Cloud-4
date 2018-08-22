package com.ssitcloud.businessauth.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.businessauth.common.service.impl.BasicServiceImpl;
import com.ssitcloud.businessauth.service.BakupService;
import com.ssitcloud.common.entity.ResultEntity;


@Service
public class BakupServiceImpl extends BasicServiceImpl implements BakupService{
	
	private static final String URL_bakupOnlyByLiraryIdxSsitAuth = "bakupOnlyByLiraryIdxSsitAuth";
	private static final String URL_restoreDataByLibraryIdx = "restoreDataByLibraryIdx";
	@Override
	public ResultEntity bakupOnlyByLiraryIdxSsitAuth(String req) {
		return postURL(URL_bakupOnlyByLiraryIdxSsitAuth, req);
	}
	
	
	@Override
	public ResultEntity restoreDataByLibraryIdx(String req) {
		return postURLLongtime(URL_restoreDataByLibraryIdx, req);
	}

}
