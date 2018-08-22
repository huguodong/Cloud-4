package com.ssitcloud.view.emailmgmt.service;


import com.ssitcloud.common.entity.ResultEntity;


/**
 * 邮件提醒业务层EmailParamService
 * @author shuangjunjie
 * 2017年2月21日
 *
 */
public interface EmailParamService {
	
	/**
	 * 邮件提醒service层的新增
	 * author shuangjunjie
	 * 2017年2月21日 下午3:15
	 * @param req
	 * @return
	 */
	ResultEntity insertEmailParam(String req);
	
	/**
	 * 邮件提醒service层的修改
	 * author shuangjunjie
	 * 2017年2月21日 下午3:15
	 * @param req
	 * @return
	 */
	ResultEntity updateEmailParam(String req);
	
	/**
	 * 邮件提醒service层的删除
	 * author shuangjunjie
	 * 2017年2月21日 下午3:15
	 * @param req
	 * @return
	 */
	ResultEntity deleteEmailParam(String req);
	
	/**
	 * 邮件提醒service层的单条查询
	 * author shuangjunjie
	 * 2017年2月21日 下午3:15
	 * @param req
	 * @return
	 */
	ResultEntity selectEmailParam(String req);
	/**
	 * 邮件服务器配置EmailParamEntity多个查询
	 * author lqw
	 * 2017年3月15日 
	 * @param params
	 * @return
	 */
	ResultEntity selectEmailParams(String req);
	
	/**
	 * 邮件提醒service层的分页查询
	 * author shuangjunjie
	 * 2017年2月22日 上午10:00
	 * @param req
	 * @return
	 */
	ResultEntity selectEmailParamByPage(String req);
	
	
	
}
