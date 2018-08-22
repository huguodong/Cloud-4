package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.PlcLogicObjDao;
import com.ssitcloud.entity.PlcLogicObjEntity;

@Repository
public class PlcLogicObjDaoImpl extends CommonDaoImpl implements PlcLogicObjDao{

	@Override
	public List<PlcLogicObjEntity> SelPlcLogicObjectEntity() {
		
		return getSqlSession().selectList("plcLogicObj.SelPlcLogicObj");
	}

}
