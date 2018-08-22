package com.ssitcloud.view.opermgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface OperGroupService {

	ResultEntity queryOperGroupByparam_view(String req);

	ResultEntity queryServiceGroupAndCmd(String req);

	ResultEntity addOperGroup(String req);

	ResultEntity delOperGroup(String req);

	ResultEntity updOperGroup(String req);

	ResultEntity queryOperatorNameByoperIdxArr(String req);

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
	 * 查询所有的用户组权限信息
	 *
	 * <p>2016年6月14日 下午1:59:19 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryLibraryServiceGroup(String req);
	
}
