package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.MetadataOrderEntity;

/**
 * 
 * @comment 
 * 
 * @author hwl
 * @data 2016年4月9日
 */
public interface MetadataOrderService {

	/**
	 * 添加监控指令数据
	 * @param metadataOrderEntity
	 * @return
	 * @author hwl
	 */
	public abstract int addMetadataOrder(
			MetadataOrderEntity metadataOrderEntity);
	/**
	 * 更新监控指令数据
	 * @param metadataOrderEntity
	 * @return
	 * @author hwl
	 */
	public abstract int updateMetadataOrder(
			MetadataOrderEntity metadataOrderEntity);
	
	/**
	 * 删除监控指令数据
	 * @param id
	 * @return
	 * @author hwl
	 */
	public abstract int deleteMetadataOrder(MetadataOrderEntity metadataOrderEntity);
	
	/**
	 * 条件查询监控指令数据
	 * @param metadataOrderEntity
	 * @return
	 * @author hwl
	 */
	public abstract List<MetadataOrderEntity> selectMetadataOrder(
			MetadataOrderEntity metadataOrderEntity);
	

}
 