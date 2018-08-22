package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MetadataOpercmdTableDao;
import com.ssitcloud.entity.MetadataOpercmdTableEntity;


@Repository
public class MetadataOpercmdTableDaoImpl extends CommonDaoImpl implements MetadataOpercmdTableDao{

	@Override
	public List<MetadataOpercmdTableEntity> queryAllOpercmdTable() {
		return this.sqlSessionTemplate.selectList("metadataOpercmdTable.queryAllOpercmdTable");
	}

	
	
}
