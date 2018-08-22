package com.ssitcloud.view.upgrade.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.upgrade.service.UpgradeService;
@Service
public class UpgradeServiceImpl extends BasicServiceImpl implements UpgradeService {
	private static final String URL_queryUpgradeByParam="queryUpgradeByParam";
	private static final String URL_addUpgrade = "addPatchInfo";
	private static final String URL_delUpgrade = "delPatchInfo";
	private static final String URL_delMultiPatchInfo = "delMultiPatchInfo";
	private static final String URL_updUpgrade = "updPatchInfo";
	
	@Override
	public ResultEntity queryUpgradeByParam(String req) {
		return postUrl(URL_queryUpgradeByParam, req);
	}

	@Override
	public ResultEntity addPatchInfo(String req) {
		return postUrl(URL_addUpgrade, req);
	}

	
	@Override
	public ResultEntity delPatchInfo(String req) {
		return postUrl(URL_delUpgrade, req);
	}
	@Override
	public ResultEntity delMultiPatchInfo(String req) {
		return postUrl(URL_delMultiPatchInfo, req);
	}

	@Override
	public ResultEntity updPatchInfo(String req) {
		return postUrl(URL_updUpgrade, req);
	}

}
