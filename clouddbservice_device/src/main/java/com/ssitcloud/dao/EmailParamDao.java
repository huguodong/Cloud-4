package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.EmailParamEntity;
import com.ssitcloud.entity.page.EmailParamMgmtPageEntity;

public interface EmailParamDao {
	/**
	 * 邮件服务器配置EmailParamEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param emailParamEntity
	 * @return
	 */
	public abstract int insertEmailParam(EmailParamEntity emailParamEntity);
	
	/**
	 * 邮件服务器配置EmailParamEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param emailParamEntity
	 * @return
	 */
	public abstract int updateEmailParam(EmailParamEntity emailParamEntity);
	
	/**
	 * 邮件服务器配置EmailParamEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param emailParamEntity
	 * @return
	 */
	public abstract int deleteEmailParam(EmailParamEntity emailParamEntity);
	
	/**
	 * 邮件服务器配置EmailParamEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param emailParamEntity
	 * @return
	 */
	public abstract EmailParamEntity queryOneEmailParam(EmailParamEntity emailParamEntity);
	
	/**
	 * 邮件服务器配置EmailParamEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param emailParamEntity
	 * @return
	 */
	public abstract List<EmailParamEntity> queryEmailParams(Map<String, Object> params);
	
	/**
	 * 邮件服务器配置EmailParamEntity分页查询
	 * author shuangjunjie
	 * 2017年2月22日上午9:08
	 * @param emailParamEntity
	 * @return
	 */
	public abstract EmailParamMgmtPageEntity queryEmailParamByPage(EmailParamMgmtPageEntity emailParamMgmtPageEntity);

}
