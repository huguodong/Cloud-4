package com.ssitcloud.dbauth.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbauth.dao.OperatorInfoDao;
import com.ssitcloud.dbauth.entity.OperatorInfoEntity;
import com.ssitcloud.dbauth.service.OperatorInfoService;

/**
 * <p>2016年4月5日 下午1:34:19
 * @author hjc
 *
 */
@Service
public class OperatorInfoServiceImpl implements OperatorInfoService{
	@Resource
	private OperatorInfoDao operatorInfoDao;

	@Override
	public int addOperatorInfo(OperatorInfoEntity operatorInfoEntity) {
		return operatorInfoDao.addOperatorInfo(operatorInfoEntity);
	}

	@Override
	public List<OperatorInfoEntity> selectOperatorInfos(
			Map<String, Object> params) {
		return operatorInfoDao.selectOperatorInfos(params);
	}
	
	
	
}
