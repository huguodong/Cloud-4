package com.ssitcloud.business.upgrade.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.upgrade.service.UpgradeService;
import com.ssitcloud.common.entity.ResultEntity;
@Service
public class UpgradeServiceImpl extends BasicServiceImpl implements UpgradeService {
	public static final String URL_selectPatchInfo="selPatchInfo";
	public static final String URL_askVersion="askVersion";
	private static final String URL_addPatchInfo = "addPatchInfo";
	private static final String URL_deletePatchInfo = "delPatchInfo";
	private static final String URL_delMultiPatchInfo = "delMultiPatchInfo";
	private static final String URL_updatePatchInfo = "updPatchInfo";
	@Override
	public ResultEntity selectPatchInfo(String req) {
		return postUrl(URL_selectPatchInfo, req);
	}

	@Override
	public ResultEntity askVersion(String req) {
		return postUrl(URL_askVersion, req);
	}

	@Override
	public ResultEntity addPatchInfo(String req) {
		return postUrl(URL_addPatchInfo, req);
	}

	@Override
	public ResultEntity deletePatchInfo(String req) {
		return postUrl(URL_deletePatchInfo, req);
	}
	@Override
	public ResultEntity delMultiPatchInfo(String req) {
		return postUrl(URL_delMultiPatchInfo, req);
	}

	@Override
	public ResultEntity updatePatchInfo(String req) {
		return postUrl(URL_updatePatchInfo, req);
	}

}
