package com.ssitcloud.business.emailmgmt.service.iml;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.emailmgmt.service.EmailParamService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class EmailParamServiceImpl extends BasicServiceImpl implements EmailParamService{

	
	private static final String URL_insertEmailParam = "insertEmailParam";
	private static final String URL_updateEmailParam = "updateEmailParam";
	private static final String URL_deleteEmailParam = "deleteEmailParam";
	private static final String URL_selectEmailParam = "selectEmailParam";
	private static final String URL_selectEmailParams = "selectEmailParams";
	private static final String URL_selectEmailParamByPage = "selectEmailParamByPage";
	private static final String URL_selLibraryByIdxOrId = "selLibraryByIdxOrId";
	private static final String ADD_Operationlog = "AddOperationLog";
	
	@Override
	public ResultEntity insertEmailParam(String req) {
		
		return postUrl(URL_insertEmailParam,req) ;
	}

	@Override
	public ResultEntity updateEmailParam(String req) {
		
		return postUrl(URL_updateEmailParam,req) ;
	}

	@Override
	public ResultEntity deleteEmailParam(String req) {
		
		return postUrl(URL_deleteEmailParam,req) ;
	}

	@Override
	public ResultEntity selectEmailParam(String req) {
		
		return postUrl(URL_selectEmailParam,req) ;
	}
	
	@Override
	public ResultEntity SelLibrary(String req) {
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URL_selLibraryByIdxOrId);
		Map<String,String> map=new HashMap<>();
		map.put("json",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
		
//		return postUrl(URL_selLibraryByIdxOrId,req) ;
	}

	
	@Override
	public ResultEntity addOperationlog(String req) {
		
		return postUrl(ADD_Operationlog,req) ;
	}

	@Override
	public ResultEntity selectEmailParamByPage(String req) {
		
		return postUrl(URL_selectEmailParamByPage,req) ;
	}

	@Override
	public ResultEntity selectEmailParams(String req) {
		return postUrl(URL_selectEmailParams,req) ;
	}


}
