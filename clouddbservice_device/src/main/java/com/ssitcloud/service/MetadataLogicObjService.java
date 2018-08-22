/**
 * 
 */
package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.MetadataLogicObjEntity;

/**
 * @comment
 * 
 * @author hwl
 * @data 2016年4月11日
 */
public interface MetadataLogicObjService {

	/**
	 * 添加逻辑对象元数据
	 * @param metadataLogicObjEntity
	 * @return
	 * @author hwl
	 */
	public abstract int addMetadataLogicObj(
			MetadataLogicObjEntity metadataLogicObjEntity);

	/**
	 * 删除逻辑对象元数据
	 * @param id
	 * @return
	 * @author hwl
	 */
	public abstract int deleteMetadataLogicObj(MetadataLogicObjEntity metadataLogicObjEntity);

	/**
	 * 更新逻辑对象元数据
	 * @param metadataLogicObjEntity
	 * @return
	 * @author hwl
	 */
	public abstract int updateMetadataLogicObj(
			MetadataLogicObjEntity metadataLogicObjEntity);

	/**
	 * 条件查询逻辑对象元数据
	 * @param metadataLogicObjEntity
	 * @return
	 * @author hwl
	 */
	public abstract List<MetadataLogicObjEntity> selectMetadataLogicObj(
			MetadataLogicObjEntity metadataLogicObjEntity);

}
