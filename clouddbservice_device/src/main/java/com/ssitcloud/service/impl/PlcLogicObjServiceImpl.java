package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dao.PlcLogicObjDao;
import com.ssitcloud.entity.PlcLogicObjEntity;
import com.ssitcloud.service.PlcLogicObjService;

@Service
public class PlcLogicObjServiceImpl implements PlcLogicObjService{

	@Resource
	private PlcLogicObjDao plcLogicObjDao;
	
	@Override
	public ResultEntity SelPlcLogicObjectEntity() {
		ResultEntity resultEntity=new ResultEntity();
		List<PlcLogicObjEntity> plcLogicObjEntitys=plcLogicObjDao.SelPlcLogicObjectEntity();
		resultEntity.setResult(plcLogicObjEntitys);
		resultEntity.setState(true);
		return resultEntity;
	}

	
}
