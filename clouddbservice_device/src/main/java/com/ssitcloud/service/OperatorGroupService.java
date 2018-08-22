package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.OperatorGroupEntity;
import com.ssitcloud.entity.page.OperGroupMgmtPageEntity;

/**
 * 
 * @comment
 * @author hwl
 * 
 * @data 2016年4月5日
 */
public interface OperatorGroupService {

	/**
	 * 增加操作员组数据
	 * 
	 * @param operatorGroupEntity
	 * @return
	 */
	public abstract int addOperatorGroup(OperatorGroupEntity operatorGroupEntity);

	/**
	 * 更新操作员组数据
	 * 
	 * @param operatorGroupEntity
	 * @return
	 */
	public abstract int updOperatorGroup(OperatorGroupEntity operatorGroupEntity);

	/**
	 * 删除操作员组数据
	 * 
	 * @param operatorGroupEntity
	 * @return
	 */
	public abstract int delOperatorGroup(OperatorGroupEntity operatorGroupEntity);

	/**
	 * 根据条件查询操作员组数据
	 * 
	 * @param operatorGroupEntity
	 * @return
	 */
	public abstract List<OperatorGroupEntity> selbyidOperatorGroup(
			OperatorGroupEntity operatorGroupEntity);

	/**
	 * 查询操作员组所有数据
	 * 
	 * @return
	 */
	public abstract List<OperatorGroupEntity> selallOperatorGroup();
	/**
	 * 带参数分页查询 用户分组页面数据
	 * @param req 
	 * @return
	 */
	public abstract OperGroupMgmtPageEntity queryOperGroupByparamDb(String req);

	public abstract ResultEntity addOperGroup(String req);

	public abstract ResultEntity delOperGroup(String req);

	public abstract ResultEntity updOperGroup(String req);
	
	
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
	


}
