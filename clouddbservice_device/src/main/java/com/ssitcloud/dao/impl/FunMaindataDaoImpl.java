package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.FunMaindataDao;
import com.ssitcloud.entity.FunMaindataEntity;

@Repository
public class FunMaindataDaoImpl extends CommonDaoImpl implements
FunMaindataDao {

	@Override
	public FunMaindataEntity queryOneFunMaindata(
			FunMaindataEntity funMaindataEntity) {
		return this.select("function_maindata.selectFunMaindata", funMaindataEntity);
	}

	@Override
	public List<FunMaindataEntity> queryFunMaindatas(
			FunMaindataEntity funMaindataEntity) {
		return this.sqlSessionTemplate.selectList("function_maindata.selectFunMaindatas", funMaindataEntity);
	}

}
