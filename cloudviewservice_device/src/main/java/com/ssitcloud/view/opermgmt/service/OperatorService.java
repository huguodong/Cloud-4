package com.ssitcloud.view.opermgmt.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface OperatorService {

	
	public abstract ResultEntity queryOperatorByParam(String req);


	/**
	 * 查询操作员的详细信息
	 *
	 * <p>2016年6月12日 下午7:56:18 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryOperatorDetailByIdx(String req);
	
	/**
	 * 查询所有的用户模板
	 *
	 * <p>2016年6月13日 下午7:12:12 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryAllSoxTemp(String req);
	
	/**
	 * 根据当前登录用户信息查询用户类型
	 *
	 * <p>2016年7月7日 上午11:57:29 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryOperatorTypes(String req);
	
	
	/**
	 * 根据图书馆id或者idx获取图书馆信息
	 *
	 * <p>2016年6月1日 上午10:26:24 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selLibraryByIdxOrId(String json);
	
	/**
	 * 根据用户operator_idx 查询用户所属的用户组
	 *
	 * <p>2016年6月28日 下午1:53:51 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryOperatorGroupByOperIdx(String req);
	/**
	 *根据idx 查询用户的维护卡信息
	 *
	 * <p>2016年6月28日 下午1:53:51 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryOperatorMaintenanceCard(String req);

	/**
	 * 更新用户信息
	 *
	 * <p>2016年7月5日 下午5:25:15 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperator(String req);
	
	/**
	 * 更新用户的用户组
	 *
	 * <p>2016年7月14日 下午3:30:35 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperatorGroup(String req);
	
	/**
	 * 修改操作员的维护卡
	 *
	 * <p>2016年7月14日 下午4:53:28 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperatorMaintenanceCard(String req);
	
	/**
	 * 新增用户信息
	 *
	 * <p>2016年7月7日 下午4:13:06 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity addOperator(String req);
	
	/**
	 * 删除单个用户(设置成失效)
	 *
	 * <p>2016年7月7日 下午7:46:26 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delOperator(String req);
	
	/**
	 * 删除多个用户(设置成失效)
	 *
	 * <p>2016年7月7日 下午7:46:45 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delMultiOperator(String req);
	
	
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
	 * 根据图书馆ID查询图书馆维护卡信息
	 *
	 * <p>2016年7月14日 下午2:01:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	public abstract ResultEntity queryMaintenanceCard(String req);
	
	
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
}
