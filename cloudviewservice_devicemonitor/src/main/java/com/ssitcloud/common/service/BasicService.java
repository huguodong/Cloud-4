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

	String postUrlReturnStr(String postUrl, String req);

	ResultEntity postUrlSSL(String postUrl, String req);
	
}
