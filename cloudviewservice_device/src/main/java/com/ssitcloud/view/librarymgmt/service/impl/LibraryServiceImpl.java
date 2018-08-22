package com.ssitcloud.view.librarymgmt.service.impl;

import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.librarymgmt.service.LibraryService;

/**
 * 
 * @comment 图书馆管理Service
 * @date 2016年5月24日
 * @author hwl
 */
@Service
public class LibraryServiceImpl extends BasicServiceImpl implements
		LibraryService {
	public static final String URL_SELECTLIBRARY = "SelectLibraryInfo";
	public static final String URL_SELLIBBYPARAM ="selLibInfoByParam";
	public static final String URL_SELECTMASTER = "SelectMasterLib";
	public static final String URL_queryMasterSubRelations = "queryMasterSubRelations";
	public static final String URL_ADDLIBRARY = "AddLibraryInfo";
	public static final String URL_DELETELIBRARY = "DeleteLibraryInfo";
	public static final String URL_UPDATELIBRARY ="UpdateLibraryInfo";
	
	public static final String URL_SELMETAINFOTYPE ="selLibInfotype";
	
	public static final String URL_QUERYLIBINFO = "querylibInfoByCurUserEXT1";
	
	public static final String URL_SELLIBRARYBYIDXORID = "SelectLibrary";
	
	public static final String URL_SAVELIBPOSITION = "saveLibPosition";
	
	public static final String URL_SELACTUALLIBRARYMASTER = "selActualLibraryMaster";

	@Override
	public String selectLibrary(Map<String, String> map) {
		String requrl = requestURL.getRequestURL(URL_SELECTLIBRARY);
		String result = HttpClientUtil.doPost(requrl, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String addLibrary(Map<String, String> map) {
		String requrl = requestURL.getRequestURL(URL_ADDLIBRARY);
		String result = HttpClientUtil.doPost(requrl, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public ResultEntity deleteLibrary(String req) {
		return postUrl(URL_DELETELIBRARY, req);
	}

	@Override
	public String updateLibrary(Map<String, String> map) {
		String requrl = requestURL.getRequestURL(URL_UPDATELIBRARY);
		String result = HttpClientUtil.doPost(requrl, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String selectLibInfoType(Map<String, String> map) {
		String requrl = requestURL.getRequestURL(URL_SELMETAINFOTYPE);
		String result = HttpClientUtil.doPost(requrl, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String selectMasterlib(Map<String, String> map) {
		String requrl = requestURL.getRequestURL(URL_SELECTMASTER);
		String result = HttpClientUtil.doPost(requrl, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String queryMasterSubRelations(Map<String, String> map) {
		String requrl = requestURL.getRequestURL(URL_queryMasterSubRelations);
		String result = HttpClientUtil.doPost(requrl, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String selectLibInfoByParam(Map<String, String> map) {
		String requrl = requestURL.getRequestURL(URL_SELLIBBYPARAM);
		String result = HttpClientUtil.doPost(requrl, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String querylibInfoByCurUserEXT1(Map<String, String> map) {
		String requrl = requestURL.getRequestURL(URL_QUERYLIBINFO);
		String result = HttpClientUtil.doPost(requrl, map, Consts.UTF_8.toString());
		return result;
	}
	
	public String selLibraryByIdxOrId(Map<String, String> map){
		String requrl = requestURL.getRequestURL(URL_SELLIBRARYBYIDXORID);
		String result = HttpClientUtil.doPost(requrl, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public ResultEntity saveLibPosition(String req) {
		return postUrl(URL_SAVELIBPOSITION, req);
	}
	
	@Override
	public ResultEntity selActualLibraryMaster(String req) {
		return postUrl(URL_SELACTUALLIBRARYMASTER, req);
	}
}
