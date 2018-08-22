package com.ssitcloud.dblib.dao;

import java.util.List;

import com.ssitcloud.dblib.entity.MetaDataInfoTypeEntity;


public interface MetaDataInfoTypeDao {
	/**
	 * 读者信息原始字段MetaDataInfoTypeEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param metaDataInfoTypeEntity
	 * @return
	 */
	public abstract MetaDataInfoTypeEntity queryOneMetaDataInfoType(MetaDataInfoTypeEntity metaDataInfoTypeEntity);
	
	/**
	 * 读者信息原始字段MetaDataInfoTypeEntity全表查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param metaDataInfoTypeEntity
	 * @return
	 */
	public abstract List<MetaDataInfoTypeEntity> queryMetaDataInfoTypes(MetaDataInfoTypeEntity metaDataInfoTypeEntity);

}
