package com.ssitcloud.auth.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.auth.dao.AuthDao;
import com.ssitcloud.auth.entity.AuthAcsEntity;
import com.ssitcloud.auth.entity.AuthEntity;
import com.ssitcloud.auth.entity.AuthPayEntity;
import com.ssitcloud.auth.entity.page.AuthPageEntity;
import com.ssitcloud.common.dao.impl.CommonDaoImpl;

@Repository
public class AuthDaoImpl extends CommonDaoImpl implements AuthDao {

	@Override
	public List<AuthEntity> queryAllAuth(AuthEntity auth) {
		return this.sqlSessionTemplate.selectList("auth.queryAllAuth", auth);
	}

	@Override
	public AuthPageEntity queryAuthByParam(AuthPageEntity auth) {
		AuthPageEntity total = this.sqlSessionTemplate.selectOne("auth.queryAuthByParam", auth);
		auth.setTotal(total.getTotal());
		auth.setDoAount(false);
		auth.setRows(this.sqlSessionTemplate.selectList("auth.queryAuthByParam", auth));
		return auth;
	}

	@Override
	public AuthEntity queryAuthByRandomCode(String code) {
		return this.sqlSessionTemplate.selectOne("auth.queryAuthByRandomCode", code);
	}

	@Override
	public int updateAuth(AuthEntity auth) {
		return this.sqlSessionTemplate.update("auth.updateAuth", auth);
	}

	@Override
	public int addAuth(AuthEntity auth) {
		return this.sqlSessionTemplate.insert("auth.addAuth", auth);
	}

	@Override
	public AuthEntity queryAuthByEntity(AuthEntity auth) {
		return this.sqlSessionTemplate.selectOne("auth.queryAuthByEntity", auth);
	}

	@Override
	public int addAuthAcs(AuthAcsEntity authAcs) {
		return this.sqlSessionTemplate.insert("auth.addAuthAcs", authAcs);
	}
	
	@Override
	public AuthAcsEntity queryAuthAcsByEntity(AuthAcsEntity authAcs) {
		return this.sqlSessionTemplate.selectOne("auth.queryAuthAcsByEntity", authAcs);
	}
	
	@Override
	public int addAuthPay(AuthPayEntity authPay) {
		return this.sqlSessionTemplate.insert("auth.addAuthPay", authPay);
	}
	
	@Override
	public AuthPayEntity queryAuthPayByEntity(AuthPayEntity authPay) {
		return this.sqlSessionTemplate.selectOne("auth.queryAuthPayByEntity", authPay);
	}
}
