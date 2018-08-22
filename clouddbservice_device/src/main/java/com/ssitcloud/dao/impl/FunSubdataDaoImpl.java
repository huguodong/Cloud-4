package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.FunSubdataDao;
import com.ssitcloud.entity.FunSubdataEntity;

@Repository
public class FunSubdataDaoImpl extends CommonDaoImpl implements
FunSubdataDao {

	@Override
	public FunSubdataEntity queryOneFunSubdata(
			FunSubdataEntity funSubdataEntity) {
		return this.select("function_subdata.selectFunSubdata", funSubdataEntity);
	}

	@Override
	public List<FunSubdataEntity> queryFunSubdatas(
			FunSubdataEntity funSubdataEntity) {
		return this.sqlSessionTemplate.selectList("function_subdata.selectFunSubdatas", funSubdataEntity);
	}



}
