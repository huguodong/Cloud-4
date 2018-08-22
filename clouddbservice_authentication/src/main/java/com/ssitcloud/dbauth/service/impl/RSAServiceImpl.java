package com.ssitcloud.dbauth.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbauth.dao.RSADao;
import com.ssitcloud.dbauth.entity.RSAEntiy;
import com.ssitcloud.dbauth.service.RSAService;
import com.ssitcloud.dbauth.utils.LogUtils;
import com.ssitcloud.dbauth.utils.RsaHelper;

/**
 * <p>2016年4月13日 下午7:36:48
 * @author hjc
 */
@Service
public class RSAServiceImpl implements RSAService{
	@Resource
	private RSADao rsaDao;

	@Override
	public RSAEntiy selRsaEntityTop() {
		return rsaDao.selRsaEntityTop();
	}
	
	//start author by LXP,add two method
	@Override
	public String encryptionString(String eString) {
		RSAEntiy rsaEntiy;
		try{
			rsaEntiy = selRsaEntityTop();
		}catch(Exception e){
			LogUtils.error("查询RSA配置失败", e);
			throw new RuntimeException("查询RSA配置失败");
		}
		String pukXml = rsaEntiy.getPublicKey();
		try{
			String mi = RsaHelper.encryRSA(eString, pukXml);
			return mi;
		}catch(Exception e){
			throw new RuntimeException("加密字符串失败");
		}
	}

	@Override
	public String decryptString(String dString) {
		RSAEntiy rsaEntiy;
		try{
			rsaEntiy = selRsaEntityTop();
		}catch(Exception e){
			LogUtils.error("查询RSA配置失败", e);
			throw new RuntimeException("查询RSA配置失败");
		}
		String prkXml = rsaEntiy.getPrivateKey();
		try{
			String ming = RsaHelper.decryRSA(dString, prkXml);
			return ming;
		}catch(Exception e){
			throw new RuntimeException("解密字符串失败");
		}
	}
	//end author by LXP,add two method
}
