package com.ssitcloud.dblib.service;

import java.util.List;
import com.ssitcloud.dblib.entity.MetaDataInfoTypeEntity;

public interface MetaDataInfoTypeService {
	
	/**
	 * 读者信息原始字段MetaDataInfoTypeEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param metaDataInfoTypeEntity
	 * @return
	 */
	public abstract MetaDataInfoTypeEntity queryOneMetaDataInfoType(MetaDataInfoTypeEntity metaDataInfoTypeEntity);
	
	/**
	 * 读者信息原始字段MetaDataInfoTypeEntity集合查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param metaDataInfoTypeEntity
	 * @return
	 */
	public abstract List<MetaDataInfoTypeEntity> queryMetaDataInfoTypes(MetaDataInfoTypeEntity metaDataInfoTypeEntity);

}
