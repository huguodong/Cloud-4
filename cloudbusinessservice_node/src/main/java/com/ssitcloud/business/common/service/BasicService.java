package com.ssitcloud.business.common.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;



public interface BasicService {

	String queryPermession(String json);

	String queryMetaOperCmd();

	String queryPermessionByDevice(String req);

	ResultEntity postUrl(String req, String postUrl);

	<T> ResultEntityF<T> postUrlF(String postUrl, String req);

	ResultEntity SelPermissionBySsitCloudAdmin(String req);
	/**
	 * 
	 * 
	 * @param postUrl url对应的ID
	 * @param reqContent 请求的内容
	 * @param reqName 请求的参数名 一般为 req 或者json
	 * @return
	 */
	ResultEntity postUrl(String postUrl, String reqContent, String reqName);


}
