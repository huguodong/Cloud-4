package com.ssitcloud.auth.dao;

import java.util.List;

import com.ssitcloud.auth.entity.AuthAcsEntity;
import com.ssitcloud.auth.entity.AuthEntity;
import com.ssitcloud.auth.entity.AuthPayEntity;
import com.ssitcloud.auth.entity.page.AuthPageEntity;

public interface AuthDao {
	public abstract List<AuthEntity> queryAllAuth(AuthEntity auth);

	public abstract AuthPageEntity queryAuthByParam(AuthPageEntity auth);

	public abstract AuthEntity queryAuthByRandomCode(String code);

	public abstract int updateAuth(AuthEntity auth);

	public abstract int addAuth(AuthEntity auth);
	
	public abstract AuthEntity queryAuthByEntity(AuthEntity auth);
	
	public abstract int addAuthAcs(AuthAcsEntity authAcs);
	
	public abstract AuthAcsEntity queryAuthAcsByEntity(AuthAcsEntity authAcs);
	
	public abstract int addAuthPay(AuthPayEntity authPay);
	
	public abstract AuthPayEntity queryAuthPayByEntity(AuthPayEntity authPay);
}