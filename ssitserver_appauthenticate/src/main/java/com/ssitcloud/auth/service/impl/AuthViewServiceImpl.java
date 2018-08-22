package com.ssitcloud.auth.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.auth.dao.AuthDao;
import com.ssitcloud.auth.entity.AuthAcsEntity;
import com.ssitcloud.auth.entity.AuthEntity;
import com.ssitcloud.auth.entity.AuthPayEntity;
import com.ssitcloud.auth.entity.page.AuthPageEntity;
import com.ssitcloud.auth.service.AuthViewService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.common.util.MD5Util;
import com.ssitcloud.common.util.StringUtil;

@Service
public class AuthViewServiceImpl implements AuthViewService {
	@Resource
	private AuthDao authDao;
	
	@Override
	public List<AuthEntity> queryAllAuth(String req) {
		AuthEntity auth = null;
		if (StringUtil.isEmpty(req)) {
			auth = new AuthEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			auth = (AuthEntity) JSONObject.toBean(json, AuthEntity.class);
		}
		return authDao.queryAllAuth(auth);
	}

	@Override
	public AuthPageEntity queryAuthByParam(String req) {
		AuthPageEntity auth = null;
		if (StringUtil.isEmpty(req)) {
			auth = new AuthPageEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			auth = (AuthPageEntity) JSONObject.toBean(json, AuthPageEntity.class);
		}
		return authDao.queryAuthByParam(auth);
	}
	
	@Override
	public AuthEntity queryAuthByRandomCode(String req) {
		AuthEntity auth = null;
		if (StringUtil.isEmpty(req)) {
			auth = new AuthEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			auth = (AuthEntity) JSONObject.toBean(json, AuthEntity.class);
		}
		return authDao.queryAuthByRandomCode(auth.getRandom_code());
	}

	@Override
	public ResultEntity updateAuth(String req) {
		ResultEntity resultEntity = new ResultEntity();
		AuthEntity auth = null;
		if (StringUtil.isEmpty(req)) {
			auth = new AuthEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			auth = (AuthEntity) JSONObject.toBean(json, AuthEntity.class);
		}
		int num = authDao.updateAuth(auth);
		if(num==1){
			resultEntity.setState(true);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity addAuth(String req) {
		ResultEntity resultEntity = new ResultEntity();
		AuthEntity auth = null;
		if (StringUtil.isEmpty(req)) {
			auth = new AuthEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			auth = (AuthEntity) JSONObject.toBean(json, AuthEntity.class);
		}
		int num = authDao.addAuth(auth);
		if(num==1){
			resultEntity.setState(true);
		}
		return resultEntity;
	}

	@Override
	public Boolean appServerResult(String req) {
		try{
			String[] str = req.split("\\|");
			if(str== null || str.length < 5){
				return false;
			}
			AuthEntity auth = new AuthEntity();
			auth.setRandom_code(str[0]);
			auth.setScan_date(str[1]);
			auth.setDevice_id(str[2]);
			auth.setState(str[3]);
			auth.setReader_code(str[4]);
			if(str.length==6){
				auth.setReader_passwd(str[5]);
			}else if(str.length==5){
				auth.setReader_passwd("");
			}
			int num = authDao.addAuth(auth);
			if(num==1){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			LogUtils.error(e);
			return false;
		}
	}

	@Override
	public String authenticateResult(String req) {
		try{
			String[] str = req.split("\\|");
			if(str== null || str.length != 2){
				return null;
			}
			AuthEntity auth = new AuthEntity();
			auth.setRandom_code(str[1]);
			auth.setDevice_id(str[0]);
			AuthEntity ety = authDao.queryAuthByEntity(auth);
			if(ety== null){
				return null;
			}else{
				String returnStr = ety.getReader_code()+"|"+ety.getReader_passwd()+"|"+ety.getState();
				return returnStr;
			}
		}catch(Exception e){
			LogUtils.error(e);
			return null;
		}
	}
	
	@Override
	public Boolean appServerResult_acs(String req) {
		try{
			String[] str = req.split("\\|");
			if(str== null || str.length < 4){
				return false;
			}
			String md5 = str[3];
			String st = str[0] + "|" + str[1] + MD5Util.getMD5Str(str[2]);
			if(md5.equals(MD5Util.getMD5Str(st))){
				AuthAcsEntity authAcs = new AuthAcsEntity();
				authAcs.setType(str[0]);
				authAcs.setDevice_id(str[1]);
				authAcs.setReader_code(str[2]);
				authAcs.setMd5(str[3]);
				authAcs.setState("000");
				int num = authDao.addAuthAcs(authAcs);
				if(num==1){
					return true;
				}else{
					return false;
				}
				
			}else{
				AuthAcsEntity authAcs = new AuthAcsEntity();
				authAcs.setType(str[0]);
				authAcs.setDevice_id(str[1]);
				authAcs.setReader_code(str[2]);
				authAcs.setMd5(str[3]);
				authAcs.setState("-1");
				authDao.addAuthAcs(authAcs);
				return false;
			}
		}catch(Exception e){
			LogUtils.error(e);
			return false;
		}
	}

	@Override
	public String authenticateResult_acs(String req) {
		try{
			String[] str = req.split("\\|");
			if(str== null || str.length != 2){
				return null;
			}
			AuthAcsEntity authAcs = new AuthAcsEntity();
			authAcs.setType(str[0]);
			authAcs.setDevice_id(str[1]);
			AuthAcsEntity ety = authDao.queryAuthAcsByEntity(authAcs);
			if(ety== null){
				return null;
			}else{
				String returnStr = ety.getReader_code()+"|"+ety.getState();
				return returnStr;
			}
		}catch(Exception e){
			LogUtils.error(e);
			return null;
		}
	}
	
	@Override
	public Boolean appServerResult_pay(String req) {
		try{
			String[] str = req.split("\\|");
			if(str== null || str.length < 7){
				return false;
			}
			
			String md5 = str[5];
			String st = str[2] + "|" + str[3] + MD5Util.getMD5Str("szlib");
			if(md5.equals(MD5Util.getMD5Str(st))){
				AuthPayEntity authPay = new AuthPayEntity();
				authPay.setRandom_code(str[0]);
				authPay.setScan_date(str[1]);
				authPay.setDevice_id(str[2]);
				authPay.setReader_code(str[3]);
				authPay.setReader_passwd(str[4]);
				authPay.setMd5(md5);
				authPay.setState(str[6]);
				int num = authDao.addAuthPay(authPay);
				if(num==1){
					return true;
				}else{
					return false;
				}
				
			}else{
				
				AuthPayEntity authPay = new AuthPayEntity();
				authPay.setRandom_code(str[0]);
				authPay.setScan_date(str[1]);
				authPay.setDevice_id(str[2]);
				authPay.setReader_code(str[3]);
				authPay.setReader_passwd(str[4]);
				authPay.setMd5(md5);
				authPay.setState("-1");
				authDao.addAuthPay(authPay);
				return false;
			}
		}catch(Exception e){
			LogUtils.error(e);
			return false;
		}
	}

	@Override
	public String authenticateResult_pay(String req) {
		try{
			String[] str = req.split("\\|");
			if(str== null || str.length != 2){
				return null;
			}
			AuthPayEntity authPay = new AuthPayEntity();
			authPay.setRandom_code(str[0]);
			authPay.setDevice_id(str[1]);
			AuthPayEntity ety = authDao.queryAuthPayByEntity(authPay);
			if(ety== null){
				return null;
			}else{
				String returnStr = ety.getReader_code()+"|"+ety.getState();
				return returnStr;
			}
		}catch(Exception e){
			LogUtils.error(e);
			return null;
		}
	}
}
