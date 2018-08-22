package com.ssitcloud.dbauth.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.RSADao;
import com.ssitcloud.dbauth.entity.RSAEntiy;

/**
 * 
 * <p>2016年4月13日 下午5:58:53
 * @author hjc
 */
@Repository
public class RSADaoImpl extends CommonDaoImpl implements RSADao {

	@Override
	public RSAEntiy selRsaEntityByIdx(RSAEntiy rsaEntiy) {
		return this.sqlSessionTemplate.selectOne("rsakey.selRsaEntityByIdx",rsaEntiy);
	}

	@Override
	public RSAEntiy selRsaEntityTop() {
		return this.sqlSessionTemplate.selectOne("rsakey.selRsaEntityTop");
	}

	@Override
	public RSAEntiy selRsaEntityByPublicKey(Map<String, String> param) {
		return this.sqlSessionTemplate.selectOne("rsakey.selRsaEntityByPublicKey",param);
	}
	
	
	
}
