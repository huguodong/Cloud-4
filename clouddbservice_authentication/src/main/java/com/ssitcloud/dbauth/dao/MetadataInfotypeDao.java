package com.ssitcloud.dbauth.dao;

import java.util.List;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.MetadataInfotypeEntity;

/** 
 *
 * <p>2016年3月30日 下午1:46:57  
 * @author hjc 
 *
 */
public interface MetadataInfotypeDao extends CommonDao {

	/**
	 * 新增一条数据
	 *
	 * <p>2016年3月30日 下午2:00:17 
	 * <p>create by hjc
	 * @param metadataInfotypeEntity
	 */
	public abstract int addMetadataInfotype(MetadataInfotypeEntity metadataInfotypeEntity);
	
	
	
	/**
	 * 查询
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
	
	/**
	 * 查询图书馆信息元数据
	 * @comment
	 * @return
	 * @data 2016年5月26日`
	 * @author hwl
	 */
	public abstract List<MetadataInfotypeEntity> selLibdataInfotype();
	
	/**
	 * 查询操作员的所有元数据信息
	 *
	 * <p>2016年7月13日 下午3:23:56 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<MetadataInfotypeEntity> selOperatorInfotypeList();



	public abstract MetadataInfotypeEntity selectInfoByIdx(
			MetadataInfotypeEntity metadataInfotype);



	public abstract int update(MetadataInfotypeEntity metadataInfotype);
}
