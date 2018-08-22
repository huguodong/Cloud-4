package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.PlcLogicObjEntity;

public interface PlcLogicObjDao extends CommonDao{

	List<PlcLogicObjEntity> SelPlcLogicObjectEntity();

}
