package com.ssitcloud.businessauth.service.impl;

import java.util.Map;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Service;

import com.ssitcloud.businessauth.common.service.impl.BasicServiceImpl;
import com.ssitcloud.businessauth.service.LibraryService;
import com.ssitcloud.businessauth.utils.HttpClientUtil;
import com.ssitcloud.common.entity.ResultEntity;


/**
 * <p>2016年4月21日 下午5:30:04
 * @author hjc
 */
@Service
public class LibraryServiceImpl extends BasicServiceImpl implements LibraryService{
	private static final String CHARSET = "UTF-8";
	private static final String ADD_LIBRARY = "addLibrary";
	private static final String DEL_LIBRARY_BY_IDX = "delLibraryByIdx";
	private static final String SEL_LIBRARY_BY_IDX_OR_ID = "selLibraryByIdxOrId";
	private static final String SEL_LIBRARY_BY_IDXS_OR_IDS = "selLibraryByIdxsOrIds";
	private static final String SEL_LIBRARY_BY_IDS_FUZZY = "selLibraryByIdsFuzzy";
	private static final String	SEL_MASTERLIB = "selMasterLib";
	
	public static final String SEL_LIBRARY_INFO = "sellibraryinfo";
	public static final String SEL_LIBRARY_INFOBYPARAM = "sellibinfoByParam";
	public static final String ADD_LIBRARY_INFO = "addlibraryinfo";
	public static final String DEL_LIBRARY_INFO = "dellibraryinfo";
	public static final String UPD_LIBRARY_INFO = "updlibraryinfo";
	private static final String URL_querylibInfoByCurUser = "querylibInfoByCurUser";
	private static final String URL_querySlaveLibraryByMasterIdx = "querySlaveLibraryByMasterIdx";
	private static final String URL_queryAllMasterLibAndSlaveLib = "queryAllMasterLibAndSlaveLib";
	private static final String URL_getLibIdAndLibIdx = "getLibIdAndLibIdx";
	private static final String URL_selLibraryIDByFuzzy = "selLibraryIDByFuzzy";
	private static final String URL_querylibInfoByCurUserEXT1 = "querylibInfoByCurUserEXT1";
	private static final String URL_queryMasterSubRelations = "queryMasterSubRelations";
	public static final String URL_SELACTUALLIBRARYMASTER = "selActualLibraryMaster";

	@Override
	public String addLibrary(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(ADD_LIBRARY), param, CHARSET);
		return response;
	}

	@Override
	public String delLibraryByIdx(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(DEL_LIBRARY_BY_IDX), param, CHARSET);
		return response;
	}

	@Override
	public String selLibraryByIdxOrId(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(SEL_LIBRARY_BY_IDX_OR_ID), param, CHARSET);
		return response;
	}

	@Override
	public String selLibraryByIdxsOrIds(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(SEL_LIBRARY_BY_IDXS_OR_IDS), param, CHARSET);
		return response;
	}
	
	@Override
	public String selLibraryByFuzzy(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(SEL_LIBRARY_BY_IDS_FUZZY), param, CHARSET);
		return response;
	}

	@Override
	public String selectlibinfo(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(SEL_LIBRARY_INFO), param, CHARSET);
		return response;
	}

	@Override
	public String addlibinfo(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(ADD_LIBRARY_INFO), param, CHARSET);
		return response;
	}

	@Override
	public ResultEntity deletelibinfo(String req) {
		return postURL(DEL_LIBRARY_INFO, req);
	}

	@Override
	public String updatelibinfo(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(UPD_LIBRARY_INFO), param, CHARSET);
		return response;
	}

	@Override
	public String selectMasterinfo(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(SEL_MASTERLIB), param, CHARSET);
		return response;
	}

	@Override
	public String selectlibinfoByParam(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(SEL_LIBRARY_INFOBYPARAM), param, CHARSET);
		return response;
	}

	@Override
	public ResultEntity querylibInfoByCurUser(String req) {
		return postURL(URL_querylibInfoByCurUser, req);
	}

	@Override
	public ResultEntity querySlaveLibraryByMasterIdx(String req) {
		return postURL(URL_querySlaveLibraryByMasterIdx, req);
	}

	@Override
	public ResultEntity queryAllMasterLibAndSlaveLib(String req) {
		return postURL(URL_queryAllMasterLibAndSlaveLib, req);
	}

	@Override
	public ResultEntity getLibIdAndLibIdx(String req) {
		return postURL(URL_getLibIdAndLibIdx, req);
	}

	@Override
	public ResultEntity selLibraryIDByFuzzy(String req) {
		return postURL(URL_selLibraryIDByFuzzy, req);
	}

	@Override
	public ResultEntity querylibInfoByCurUserEXT1(String req) {
		return postURL(URL_querylibInfoByCurUserEXT1, req);
	}

	@Override
	public ResultEntity queryMasterSubRelations(String req) {
		return postURL(URL_queryMasterSubRelations, req);
	}
	
	@Override
	public ResultEntity selActualLibraryMaster(String req) {
		return postURL(URL_SELACTUALLIBRARYMASTER, req);
	}

}
