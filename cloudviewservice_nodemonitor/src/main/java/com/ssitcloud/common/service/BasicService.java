package com.ssitcloud.common.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 *  VIEW 层的service 都不涉及事务
 * @package: com.ssitcloud.common.service
 * @classFile: BasicService
 * @author: liuBh
 * @description: TODO
 */
public interface BasicService {

	ResultEntity postUrl(String req, String postUrl);

	/**
	 * 较长的超时时间设置
	 * @param postUrl
	 * @param req
	 * @return
	 */
	ResultEntity postUrlLongTimeout(String postUrl, String req);
	/**
	 * 根据ID 获取 定义的URL
	 * @param urlId
	 * @return
	 */
	String getUrl(String urlId);
	/**
	 * 返回string类型
	 * @param postUrl
	 * @param req
	 * @return
	 */
	String postUrlReturnStr(String postUrl, String req);
	/**
	 * 
	 * @param postUrl  请求的url
	 * @param reqContent 请求内容
	 * @param reqName   请求的 参数名字 
	 * @return
	 */
	ResultEntity postUrl(String postUrl, String reqContent, String reqName);

}
