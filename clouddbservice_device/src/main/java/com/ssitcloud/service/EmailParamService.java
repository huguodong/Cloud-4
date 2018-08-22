package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.EmailParamEntity;
import com.ssitcloud.entity.page.EmailParamMgmtPageEntity;

public interface EmailParamService {
	
	/**
	 * 邮件服务器配置EmailParamEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param emailParamEntity
	 * @return
	 */
	public abstract int insertEmailParam(EmailParamEntity emailParamEntity);
	
	/**
	 * 邮件服务器配置EmailParamEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param emailParamEntity
	 * @return
	 */
	public abstract int updateEmailParam(EmailParamEntity emailParamEntity);
	
	/**
	 * 邮件服务器配置EmailParamEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param emailParamEntity
	 * @return
	 */
	public abstract int deleteEmailParam(EmailParamEntity emailParamEntity);
	
	/**
	 * 邮件服务器配置EmailParamEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param emailParamEntity
	 * @return
	 */
	public abstract EmailParamEntity queryOneEmailParam(EmailParamEntity emailParamEntity);
	
	/**
	 * 邮件服务器配置EmailParamEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param params
	 * @return
	 */
	public abstract List<EmailParamEntity> queryEmailParams(Map<String, Object> params);
	
	/**
	 * 邮件服务器配置EmailParamEntity分页查询
	 * author shuangjunjie
	 * 2017年2月22日 上午9:29
	 * @param req
	 * @return
	 */
	public abstract ResultEntity selectEmailParamByPage(String req);

}
