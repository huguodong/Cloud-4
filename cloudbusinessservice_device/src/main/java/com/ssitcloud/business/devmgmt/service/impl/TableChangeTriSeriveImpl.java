package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;
import com.ssitcloud.business.devmgmt.service.TableChangeTriSerive;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
@Service
public class TableChangeTriSeriveImpl extends BasicServiceImpl implements TableChangeTriSerive{

	public static final String URL_SelTableChangeTri="SelTableChangeTri";
	public static final String URL_SelTableChangeTriPatchInfo="SelTableChangeTriPatchInfo";
	private static final String URL_setRequestTimeByTriIdxs = "setRequestTimeByTriIdxs";
	private static final String URL_updateRequestTimeByTriTableName = "updateRequestTimeByTriTableName";
	
	@Override
	public ResultEntityF<List<TableChangeTriEntity>> queryAllTableChanges(String req){
		ResultEntityF<List<TableChangeTriEntity>> resultf=new ResultEntityF<>();
		try {
			String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_SelTableChangeTri), new HashMap<String, String>(), charset);
			if(result!=null&&!"".equals(result)){
				 resultf=JsonUtils.fromJson(result, new TypeReference<ResultEntityF<List<TableChangeTriEntity>>>() {});
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultf, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return resultf;
		
	}

	@Override
	public ResultEntityF<List<TableChangeTriEntity>> selTableChangeTriPatchInfo(
			String req) {
		ResultEntityF<List<TableChangeTriEntity>> resultf=new ResultEntityF<>();
		try {
			String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_SelTableChangeTriPatchInfo), new HashMap<String, String>(), charset);
			if(result!=null&&!"".equals(result)){
				 resultf=JsonUtils.fromJson(result, new TypeReference<ResultEntityF<List<TableChangeTriEntity>>>() {});
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultf, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return resultf;
	}
	
	@Override
	public ResultEntityF<Object> setRequestTimeByTriIdxs(String req) {
		
		return postUrlF(URL_setRequestTimeByTriIdxs, req);
	}

	@Override
	public ResultEntity updateRequestTimeByTriTableName(String req) {
		return postUrl(URL_updateRequestTimeByTriTableName, req);
	}
}
