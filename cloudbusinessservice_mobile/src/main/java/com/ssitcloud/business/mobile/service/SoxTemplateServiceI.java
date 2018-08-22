package com.ssitcloud.business.mobile.service;

import com.ssitcloud.authentication.entity.SoxTemplateEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月25日 上午11:32:33
 */
public interface SoxTemplateServiceI {
	
	/**
	 * 根据id获取模板
	 * @param sox_tpl_id 模板id
	 * @return
	 */
	SoxTemplateEntity getSoxTemplateEntityById(Integer sox_tpl_id);
}
