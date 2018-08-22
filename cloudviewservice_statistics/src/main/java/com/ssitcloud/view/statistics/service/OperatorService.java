package com.ssitcloud.view.statistics.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface OperatorService {

	
	/**
	 * 用户修改密码，用户id，idx，old，pwd1，pwd2
	 *
	 * <p>2016年7月28日 下午3:51:57 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity changePassword(String req);
	
	/**
	 * 检测密码格式
	 *
	 * <p>2016年12月20日 上午11:52:20 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity checkPwdFormat(String req);
}
