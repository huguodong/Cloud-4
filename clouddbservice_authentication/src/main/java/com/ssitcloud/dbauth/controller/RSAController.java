package com.ssitcloud.dbauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.service.RSAService;
import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * RSA加解密对外接口
 * @author LXP
 * @version 创建时间：2017年2月25日 上午9:53:04
 */
@Controller
@RequestMapping("/rsa")
public class RSAController {
	@Autowired
	private RSAService rsaSevice;
	
	@RequestMapping("/encryption")
	@ResponseBody
	public ResultEntity encryption(String encryptionStr){
		ResultEntity resultEntity = new ResultEntity();
		
		if(encryptionStr == null){
			resultEntity.setValue(false, "没有提供加密参数");
			return resultEntity;
		}
		
		try{
			String mi = rsaSevice.encryptionString(encryptionStr);
			resultEntity.setState(mi != null);
			resultEntity.setResult(mi);
		}catch(Exception e){
			LogUtils.error("尝试加密时出错", e);
			resultEntity.setValue(false, "尝试加密时出错");
			return resultEntity;
		}
		
		
		return resultEntity;
	}
	
	@RequestMapping("/decrypt")
	@ResponseBody
	public ResultEntity decryptString(String decryptStr){
		ResultEntity resultEntity = new ResultEntity();
		
		if(decryptStr == null){
			resultEntity.setValue(false, "没有提供解密参数");
			return resultEntity;
		}
		
		try{
			String ming = rsaSevice.decryptString(decryptStr);
			resultEntity.setState(ming != null);
			resultEntity.setResult(ming);
		}catch(Exception e){
			LogUtils.error("尝试加密时出错", e);
			resultEntity.setValue(false, "尝试解密时出错");
			return resultEntity;
		}
		
		
		return resultEntity;
	}
}
