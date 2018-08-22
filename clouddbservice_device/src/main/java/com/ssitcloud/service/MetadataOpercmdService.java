package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.MetadataOpercmdEntity;
import com.ssitcloud.entity.page.OperCmdMgmtPageEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月7日
 */
public interface MetadataOpercmdService {

	/**
	 * 添加操作指令元数据表数据
	 * @param metadataOpercmdEntity
	 * @return 
	 */
	public abstract int addMetadataOpercmd(
			MetadataOpercmdEntity metadataOpercmdEntity);

	/**
	 * 更新操作指令元数据表数据
	 * @param metadataOpercmdEntity
	 * @return
	 */
	public abstract int updMetadataOpercmd(
			MetadataOpercmdEntity metadataOpercmdEntity);

	/**
	 * 删除操作指令元数据表数据
	 * @param metadataOpercmdEntity
	 * @return
	 */
	public abstract int delMetadataOpercmd(
			MetadataOpercmdEntity metadataOpercmdEntity);

	/**
	 * 根据条件查询操作指令元数据表数据
	 * @param metadataOpercmdEntity
	 * @return
	 */
	public abstract List<MetadataOpercmdEntity> selbyidMetadataOpercmd(
			MetadataOpercmdEntity metadataOpercmdEntity);
	
	/**
	 * 查询所有数据
	 * @return
	 */
	public abstract List<MetadataOpercmdEntity> selallMetadataOpercmd();

	public abstract List<MetadataOpercmdEntity> selectCmdType();
	public abstract List<MetadataOpercmdEntity> selectCardCmdType();

	public abstract OperCmdMgmtPageEntity queryServgroupByparam(String req);
	
	/**
	 * 馆员App:查询馆员有没有设备监控权限
	 * @param req
	 * @return
	 */
	public abstract List<MetadataOpercmdEntity> checkPermission(Map<String,Object> param);
	
	public abstract List<MetadataOpercmdEntity> selectDeviceOperLogCmd();
}
