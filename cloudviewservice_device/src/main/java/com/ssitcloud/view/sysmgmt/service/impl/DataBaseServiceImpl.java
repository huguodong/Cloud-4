package com.ssitcloud.view.sysmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.sysmgmt.service.DataBaseService;

@Service
public class DataBaseServiceImpl extends BasicServiceImpl implements DataBaseService{

	
	public static final String URL_backUp="backUp";
	public static final String URL_queryDbBakByparam="queryDbBakByparam";
	private static final String URL_deleteBakup = "deleteBakup";
	private static final String URL_getLastBakUpTime = "getLastBakUpTime";
	private static final String URL_restoreBakup = "restoreBakup";
	private static final String URL_getMongodbNames = "getMongodbNames";
	private static final String URL_bakupByLibraryIdx = "bakupByLibraryIdx";
	private static final String URL_restoreDataByLibraryIdx = "restoreDataByLibraryIdx";
	private static final String URL_queryLibraryDbBakByparamExt = "queryLibraryDbBakByparamExt";
	private static final String URL_checkBakUpFileIfExsit = "checkBakUpFileIfExsit";
	private static final String URL_deleteLibBakup = "deleteLibBakup";
	private static final String URL_getLastLibBakUpTime = "getLastLibBakUpTime";
	
	
	@Override
	public ResultEntity backUp(String req) {
		return postUrlLongTimeout(URL_backUp, req);
	}
	
	@Override
	public ResultEntity queryDbBakByparam(String req) {
		return postUrl(URL_queryDbBakByparam, req);
	}

	@Override
	public ResultEntity deleteBakup(String req) {
		return postUrl(URL_deleteBakup, req);
	}

	@Override
	public ResultEntity getLastBakUpTime(String req) {
		return postUrl(URL_getLastBakUpTime, req);
	}

	@Override
	public ResultEntity restoreBakup(String req) {
		return postUrlLongTimeout(URL_restoreBakup, req);
	}

	@Override
	public ResultEntity getMongodbNames(String req) {
		return postUrl(URL_getMongodbNames, req);
	}

	@Override
	public ResultEntity bakupByLibraryIdx(String req) {
		return  postUrl(URL_bakupByLibraryIdx, req);
	}

	@Override
	public ResultEntity restoreDataByLibraryIdx(String req) {
		return postUrlLongTimeout(URL_restoreDataByLibraryIdx, req);
	}

	@Override
	public ResultEntity queryLibraryDbBakByparamExt(String req) {
		return postUrl(URL_queryLibraryDbBakByparamExt, req);
	}

	@Override
	public ResultEntity checkBakUpFileIfExsit(String req) {
		return postUrl(URL_checkBakUpFileIfExsit, req);
	}

	@Override
	public ResultEntity deleteLibBakup(String req) {
		return postUrl(URL_deleteLibBakup, req);
	}

	@Override
	public ResultEntity getLastLibBakUpTime(String req) {
		return postUrl(URL_getLastLibBakUpTime, req);
	}

}
