package com.ssitcloud.dbauth.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.OperatorInfoDao;
import com.ssitcloud.dbauth.entity.OperatorInfoEntity;

/**
 * <p>2016年4月5日 下午1:35:23
 * @author hjc
 *
 */
@Repository
public class OperatorInfoDaoImpl extends CommonDaoImpl implements OperatorInfoDao{

	@Override
	public int addOperatorInfo(OperatorInfoEntity operatorInfoEntity) {
		return this.sqlSessionTemplate.insert("operatorInfo.addOperatorInfo",operatorInfoEntity);
	}

	@Override
	public int deleteInfoByOperIdx(OperatorInfoEntity operatorInfoEntity) {
		return this.sqlSessionTemplate.delete("operatorInfo.deleteInfoByOperIdx", operatorInfoEntity);
	}

	@Override
	public List<OperatorInfoEntity> selectOperatorInfos(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList("operatorInfo.selectOperatorInfo", params);
	}

	
	
}
