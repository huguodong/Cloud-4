package com.ssitcloud.business.opermgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface OperatorGroupService {

	ResultEntity queryOperGroupByparam(String req);

	ResultEntity addOperGroup(String req);

	ResultEntity delOperGroup(String req);

	ResultEntity updOperGroup(String req);
	
	/**
	 * 查询所有的用户组信息，以及对应用户组所拥有的权限
	 *
	 * <p>2016年6月23日 下午7:37:23 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryAllServiceGroup(String req);
	/**
	 * 查询图书馆的用户组信息，以及对应用户组所拥有的权限
	 *
	 * <p>2016年6月23日 下午7:37:23 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryLibraryServiceGroup(String req);
	
	
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
	 * 更新用户的用户组信息
	 *
	 * <p>2016年7月14日 下午3:34:30 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperatorGroup(String req);

}
