package com.ssitcloud.dbauth.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.PasswordHistoryDao;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.PasswordHistoryEntity;

/**
 * <p>2016年4月5日 下午1:38:56
 * @author hjc
 *
 */
@Repository
public class PasswordHistoryDaoImpl extends CommonDaoImpl implements PasswordHistoryDao{

	@Override
	public int addPasswordHistory(PasswordHistoryEntity passwordHistoryEntity) {
		return this.sqlSessionTemplate.insert("passwordHistory.addPasswordHistory",passwordHistoryEntity);
	}

	@Override
	public List<PasswordHistoryEntity> selPasswordHistoryByOperInfo(
			OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.selectList("passwordHistory.selPasswordHistoryByOperInfo", operatorEntity);
	}

	@Override
	public int delPasswordHistoryByIdx(PasswordHistoryEntity historyEntity) {
		return this.sqlSessionTemplate.delete("passwordHistory.delPasswordHistoryByIdx", historyEntity);
	}

	@Override
	public List<PasswordHistoryEntity> selPwdHistoryByOperIdx(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("passwordHistory.selPwdHistoryByOperIdx",param);
	}

	
}
