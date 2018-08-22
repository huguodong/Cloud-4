package com.ssitcloud.view.emailmgmt.service.impl;


import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.emailmgmt.service.EmailParamService;

@Service
public class EmailParamServiceImpl extends BasicServiceImpl implements EmailParamService{

	
	private static final String URL_insertEmailParam = "insertEmailParam";
	private static final String URL_updateEmailParam = "updateEmailParam";
	private static final String URL_deleteEmailParam = "deleteEmailParam";
	private static final String URL_selectEmailParam = "selectEmailParam";
	private static final String URL_selectEmailParams = "selectEmailParams";
	private static final String URL_selectEmailParamByPage = "selectEmailParamByPage";
	
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
	public ResultEntity selectEmailParamByPage(String req) {
		
		return postUrl(URL_selectEmailParamByPage,req) ;
	}

	@Override
	public ResultEntity selectEmailParams(String req) {
		return postUrl(URL_selectEmailParams,req) ;
	}


}
