package com.ssitcloud.dblib.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.MetaDataInfoTypeDao;
import com.ssitcloud.dblib.entity.MetaDataInfoTypeEntity;

@Repository
public class MetaDataInfoTypeDaoImpl extends CommonDaoImpl implements
		MetaDataInfoTypeDao {

	@Override
	public MetaDataInfoTypeEntity queryOneMetaDataInfoType(
			MetaDataInfoTypeEntity metaDataInfoTypeEntity) {
		return this.select("metadata_infotype.selectMetaDataInfoType", metaDataInfoTypeEntity);
	}

	@Override
	public List<MetaDataInfoTypeEntity> queryMetaDataInfoTypes(
			MetaDataInfoTypeEntity metaDataInfoTypeEntity) {
		return this.selectAll("metadata_infotype.selectMetaDataInfoTypes", metaDataInfoTypeEntity);
	}

}
