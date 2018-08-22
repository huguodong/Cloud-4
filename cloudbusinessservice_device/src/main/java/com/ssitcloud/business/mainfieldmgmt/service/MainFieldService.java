package com.ssitcloud.business.mainfieldmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 信息主字段表 业务层  MainFieldService
 * @author shuangjunjie
 * 2017年2月23日下午4:02
 *
 */
public interface MainFieldService {

	/**
	 * 信息主字段表 service层的新增
	 * author shuangjunjie
	 * 2017年2月23日下午4:05
	 * @param req
	 * @return
	 */
	ResultEntity insertMainField(String req);
	
	/**
	 * 信息主字段表 service层的修改
	 * author shuangjunjie
	 * 2017年2月23日下午4:05
	 * @param req
	 * @return
	 */
	ResultEntity updateMainField(String req);
	
	/**
	 * 信息主字段表 service层的删除
	 * author shuangjunjie
	 * 2017年2月23日下午4:05
	 * @param req
	 * @return
	 */
	ResultEntity deleteMainField(String req);
	
	/**
	 * 信息主字段表 service层的查询
	 * author shuangjunjie
	 * 2017年2月23日下午4:05
	 * @param req
	 * @return
	 */
	ResultEntity selectMainField(String req);
	
	/**
	 * 信息主字段表 service层的 分页查询
	 * author shuangjunjie
	 * 2017年2月24日 上午11:13
	 * @param req
	 * @return
	 */
	ResultEntity selectMainFieldByPage(String req);
	/**
	 * 查询全部
	 * <p>2017年4月14日
	 * <p>create by lqw
	 * @param mainFieldEntity
	 * @return
	 */
	ResultEntity selectMainFieldList(String req);
}
