package com.ssitcloud.dbauth.service;

import java.util.List;

import com.ssitcloud.dbauth.entity.MetadataInfotypeEntity;

/** 
 *
 * <p>2016年3月30日 下午1:49:36  
 * @author hjc 
 *
 */
public interface MetadataInfotypeService {
	/**
	 * 
	 *
	 * <p>2016年3月30日 下午2:03:16 
	 * <p>create by hjc
	 * @param metadataInfotypeEntity
	 */
	public abstract void addMetadataInfotype(MetadataInfotypeEntity metadataInfotypeEntity);
	
	
	/**
	 * 
	 *
	 * <p>2016年3月30日 下午4:35:32 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<MetadataInfotypeEntity> selMetadataInfotype();
	
	/**
	 * 根据元数据表的infotype_idx删除信息
	 * 
	 * <p>2016年4月6日 下午4:18:43
	 * <p>create by hjc
	 * @param metadataInfotypeEntity 元数据表的实体类
	 * @return  数据库操作结果
	 */
	public abstract int delMetadataInfotypeByIdx(MetadataInfotypeEntity metadataInfotypeEntity);
	
	public abstract List<MetadataInfotypeEntity> sellibdataInfotype();
}
