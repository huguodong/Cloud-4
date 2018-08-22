package com.ssitcloud.business.librarymgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.librarymgmt.service.ReaderTypeService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class ReaderTypeServiceImpl extends BasicServiceImpl implements ReaderTypeService {

	private static final String URL_SelectReaderType = "SelectReadertype";
	private static final String URL_SelectReaderTypeByFuzzy = "SelectReadertypeByFuzzy"; 
	
	private static final String URL_UpdateReaderType = "UpdateReadertype"; 
	private static final String URL_InsertReaderType = "InsertReadertype"; 
	private static final String URL_DeleteReaderType = "DeleteReadertype"; 
	private static final String LIBRARY_SELECT_URL = "SelectLibrary";
	
	private static final String LIBRARY_SELECTByFUZZY_URL = "SelectLibByFuzzy";
	private static final String ADD_Operationlog = "AddOperationLog";
	private static final String URL_queryMaintenanceCard = "queryMaintenanceCard";
	private static final String URL_queryOperatorMaintenanceCard = "queryOperatorMaintenanceCard";
	private static final String URL_updateOperatorMaintenanceCard = "updateOperatorMaintenanceCard";
	private static final String URL_selLibraryIDByFuzzy = "selLibraryIDByFuzzy";
	
	
	@Override
	public String Selectreadertype(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_SelectReaderType);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String Updatereadertype(String info) {
		String reqURL= requestURL.getRequestURL(URL_UpdateReaderType);
		Map<String, String > map = new HashMap<>();
		map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public String Insertreadertype(String info) {
		
		String reqURL= requestURL.getRequestURL(URL_InsertReaderType);
		Map<String, String > map = new HashMap<>();
		map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public String Deletereadertype(String info) {
		
		String reqURL= requestURL.getRequestURL(URL_DeleteReaderType);
		Map<String, String > map = new HashMap<>();
		map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public String SelLibrary(String reqInfo) {
		
		String reqURL=requestURL.getRequestURL(LIBRARY_SELECT_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}
	
	@Override
	public String SelLibraryByFuzzy(String reqInfo) {
		
		String reqURL=requestURL.getRequestURL(LIBRARY_SELECTByFUZZY_URL);
		Map<String,String> map=new HashMap<>();
		map.put("json", reqInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}

	@Override
	public String SelectreaderTypeByFuzzy(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_SelectReaderTypeByFuzzy);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String addOperationlog(String operlog) {
		String reqURL=requestURL.getRequestURL(ADD_Operationlog);
		Map<String,String> map=new HashMap<>();
		map.put("json", operlog);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}
	
	@Override
	public ResultEntity queryMaintenanceCard(String req) {
		return postUrl(URL_queryMaintenanceCard, req);
	}

	@Override
	public ResultEntity queryOperatorMaintenanceCard(String req) {
		return postUrl(URL_queryOperatorMaintenanceCard, req);
	}
	
	@Override
	public ResultEntity updateOperatorMaintenanceCard(String req) {
		return postUrl(URL_updateOperatorMaintenanceCard, req);
	}

	@Override
	public ResultEntity selLibraryIDByFuzzy(String libinfo) {
		return postUrl(URL_selLibraryIDByFuzzy, libinfo);
	}
	
	
}
