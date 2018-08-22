package com.ssitcloud.dbauth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbauth.dao.PasswordHistoryDao;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.PasswordHistoryEntity;
import com.ssitcloud.dbauth.service.PasswordHistoryService;

/**
 * <p>2016年4月5日 下午1:38:03
 * @author hjc
 *
 */
@Service
public class PasswordHistoryServiceImpl implements PasswordHistoryService{
	@Resource
	private PasswordHistoryDao historyDao;

	@Override
	public int addPasswordHistory(PasswordHistoryEntity passwordHistoryEntity) {
		return historyDao.addPasswordHistory(passwordHistoryEntity);
	}

	@Override
	public List<PasswordHistoryEntity> selPasswordHistoryByOperInfo(
			OperatorEntity operatorEntity) {
		return historyDao.selPasswordHistoryByOperInfo(operatorEntity);
	}

	@Override
	public int delPasswordHistoryByIdx(PasswordHistoryEntity historyEntity) {
		return historyDao.delPasswordHistoryByIdx(historyEntity);
	}

	/* (non-Javadoc)
	 * @see com.ssitcloud.service.PasswordHistoryService#selPwdHistoryByOperId(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<PasswordHistoryEntity> selPwdHistoryByOperIdx(Integer operator_idx, Integer historyCount) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("operator_idx", operator_idx);
		param.put("historyCount", historyCount);
		
		return historyDao.selPwdHistoryByOperIdx(param);
	}
	
	
	
}
