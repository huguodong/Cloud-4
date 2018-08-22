package com.ssitcloud.view.librarymgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.librarymgmt.service.LibraryTempService;
/**
 * 图书馆模版Service
 * @comment 
 * @date 2016年5月20日
 * @author hwl
 */
@Service
public class LibraryTempServiceImpl extends BasicServiceImpl implements LibraryTempService {

	public static final String URL_SelectLibraryTemp = "SelectLibraryTemp"; 
	public static final String URL_SelectAllTemp = "SelectAllLibraryTemp";
	public static final String URL_UpdateLibraryTemp = "UpdateLibraryTemp"; 
	public static final String URL_InsertLibraryTemp = "InsertLibraryTemp"; 
	public static final String URL_DeleteLibraryTemp = "DeleteLibraryTemp";
	private static final String URL_selLibraryServiceTemplateByIdx = "selLibraryServiceTemplateByIdx"; 	
	
	@Override
	public String selectLibraryTemp(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_SelectLibraryTemp);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String addLibraryTemp(Map<String, String> map) {
		String reqURL= requestURL.getRequestURL(URL_InsertLibraryTemp);
		//Map<String, String > map = new HashMap<>();
		//map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public ResultEntity updateLibraryTemp(String req) {
		return postUrl(URL_UpdateLibraryTemp, req);
	}

	@Override
	public ResultEntity deleteLibraryTemp(String req) {
		return postUrl(URL_DeleteLibraryTemp, req);
	}

	@Override
	public String selectAllTemp(String info) {
		String reqURL= requestURL.getRequestURL(URL_SelectAllTemp);
		Map<String, String > map = new HashMap<>();
		map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public ResultEntity selLibraryServiceTemplateByIdx(String req) {
		
		return postUrl(URL_selLibraryServiceTemplateByIdx, req);
	}

	
}
