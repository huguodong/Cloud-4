package com.ssitcloud.business.common.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;



public interface BasicService {

	/**
	 * 通过用户IDX获取权限
	 * @param json 格式为{"idx": 1}
	 * @return
	 */
	String queryPermession(String json);
	
	/**
	 * 查询用户是否含有权限
	 * @param idx 用户的idx
	 * @param cmd 权限完整命令码，可以传入多个
	 * @return 
	 * 1.若用户含有cmd中所有的权限，返回true<br/>
	 * 2.若cmd为null，返回true<br/>
	 * 3.其他情况返回false<br/>
	 */
	boolean havePermession(Integer idx,String ...cmd);
	
	String SelMenuByOperIdx(String json);

	String queryMetaOperCmd();

	String queryPermessionByDevice(String req);

	ResultEntity postUrl(String req, String postUrl);

	<T> ResultEntityF<T> postUrlF(String postUrl, String req);

	ResultEntity SelPermissionBySsitCloudAdmin(String req);
	
	ResultEntity SelMenuBySsitCloudAdmin(String req);
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
