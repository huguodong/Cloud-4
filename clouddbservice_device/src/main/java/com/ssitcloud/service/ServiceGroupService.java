package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.ServiceGroupEntity;

/**
 * @comment
 * @author hwl
 * 
 * @data 2016年4月5日
 * 
 */
public interface ServiceGroupService {

	/**
	 * 根据条件查询数据
	 * 
	 * @param serviceGroupEntity
	 * @return
	 */
	public abstract List<ServiceGroupEntity> selbyidServiceGroup(
			ServiceGroupEntity serviceGroupEntity);

	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	public abstract List<ServiceGroupEntity> selallServiceGroup();
	/**
	 * 权限分组页面新增
	 * @param req
	 * @return
	 */
	public abstract ResultEntity addservgroup(String req);
	/**
	 * 删除操作
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delservgroup(String req);
	/**
	 * 编辑保存操作
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updservgroup(String req);
}
