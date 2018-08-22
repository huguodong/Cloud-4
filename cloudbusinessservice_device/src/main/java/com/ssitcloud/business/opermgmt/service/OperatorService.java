package com.ssitcloud.business.opermgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface OperatorService {

	/**
	 * 分页查询操作员列表
	 *
	 * <p>2016年6月8日 上午9:40:59 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryOperatorByParam(String req);
	
	/**
	 * 新增操作员
	 *
	 * <p>2016年6月8日 上午9:40:59 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity addOperator(String req);
	
	/**
	 * 删除操作员
	 *
	 * <p>2016年6月8日 上午9:40:59 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delOperator(String req);
	/**
	 * 删除多个操作员
	 *
	 * <p>2016年7月7日 下午7:49:22 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delMultiOperator(String req);

	/**
	 * 更新操作员信息
	 *
	 * <p>2016年6月8日 上午9:40:59 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updOperator(String req);
	
	/**
	 * 根据idx查询操作员的详细信息，包括所有的用户模板信息
	 *
	 * <p>2016年6月13日 上午11:36:00 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryOperatorDetailByIdx(String req);
	
	/**
	 * 查询所有的用户模板
	 *
	 * <p>2016年6月13日 下午7:15:44 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryAllSoxTemp(String req);
	
	/**
	 * 查询用户组信息
	 *
	 * <p>2016年7月7日 下午1:36:07 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryOperatorTypes(String req);
	
	/**
	 * 跟新操作员信息
	 *
	 * <p>2016年7月5日 下午6:09:37 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperator(String req);
	
	
	/**
	 * 查询所有的用户信息项，包括可以新增的项目
	 *
	 * <p>2016年7月9日 下午12:03:14 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryAllOperatorInfo(String req);
	
	/**
	 * 重置用户密码
	 *
	 * <p>2016年7月14日 下午7:32:49 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity resetPassword(String req);
	
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
	
	public ResultEntity selOperatorByOperIdOrIdx(String req);
	
}
