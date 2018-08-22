package com.ssitcloud.dblib.common.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;



public interface BasicService {

	

	ResultEntity postUrl(String req, String postUrl);

	<T> ResultEntityF<T> postUrlF(String postUrl, String req);

	/**
	 * 根据ID 获取 定义的URL
	 * @param urlId
	 * @return
	 */
	String getUrl(String urlId);
	/**
	 * 
	 * 
	 * @param postUrl url对应的ID
	 * @param reqContent 请求的内容
	 * @param reqName 请求的参数名 一般为 req 或者json
	 * @return
	 */
	ResultEntity postUrl(String postUrl, String reqContent, String reqName);
	/**
	 * 
	 * @param postUrl
	 * @param req
	 * @return
	 */
	String postUrlRetStr(String postUrl, String req);


	ResultEntity postUrlLongtime(String postUrl, String req);

}
