package com.ssitcloud.view.librarymgmt.service.impl;

import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.librarymgmt.service.ReaderTypeService;

/**
 * 读者流通类型Service
 * @comment 
 * @date 2016年5月17日
 * @author hwl
 */
@Service
public class ReaderTypeServiceImpl extends BasicServiceImpl implements ReaderTypeService {

	public static final String URL_SelectReaderType = "SelectReadertype"; 
	public static final String URL_SelectReaderTypeByFuzzy = "QueryReaderTypeByFuzzy"; 
	public static final String URL_UpdateReaderType = "UpdateReadertype"; 
	
	public static final String URL_InsertReaderType = "InsertReadertype"; 
	
	public static final String URL_DeleteReaderType = "DeleteReadertype"; 
	
	@Override
	public String Selectreadertype(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_SelectReaderType);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String SelectreadertypeByFuzzy(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_SelectReaderTypeByFuzzy);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String Updatereadertype(Map<String, String> map) {
		String reqURL= requestURL.getRequestURL(URL_UpdateReaderType);
		//Map<String, String > map = new HashMap<>();
		//map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public String Insertreadertype(Map<String, String> map) {
		
		String reqURL= requestURL.getRequestURL(URL_InsertReaderType);
		//Map<String, String > map = new HashMap<>();
		//map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public String Deletereadertype(Map<String, String> map) {
		
		String reqURL= requestURL.getRequestURL(URL_DeleteReaderType);
		//Map<String, String > map = new HashMap<>();
		//map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	
}
