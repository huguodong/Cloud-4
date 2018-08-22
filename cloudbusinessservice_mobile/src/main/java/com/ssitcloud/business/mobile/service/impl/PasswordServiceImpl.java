package com.ssitcloud.business.mobile.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.PasswordServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月25日 下午2:52:56
 */
@Service
public class PasswordServiceImpl implements PasswordServiceI{
	private final String encryption = "encryption";
	private final String decrypt = "decrypt";
	
	@Resource(name="requestURL")
	private RequestURLListEntity requestURLEntity;
	private static final String charset = "utf-8";
	
	@Override
	public String encryption(String str) {
		if(str == null){
			throw new IllegalArgumentException("str is null");
		}
		String url = requestURLEntity.getRequestURL(encryption);
		Map<String, String> map = new HashMap<>(1);
		map.put("encryptionStr", str);
		String result = HttpClientUtil.doPost(url, map, charset);
		ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
		if(resultEntity == null || !resultEntity.getState() || resultEntity.getResult() == null){
			LogUtils.error(PasswordServiceImpl.class+"加密失败，请检查clouddbservice_authentication是否可用，或者查看日志");
			return null;
		}
		return resultEntity.getResult().toString();
	}

	@Override
	public String decrypt(String str) {
		if(str == null){
			throw new IllegalArgumentException("str is null");
		}
		String url = requestURLEntity.getRequestURL(decrypt);
		Map<String, String> map = new HashMap<>(1);
		map.put("decryptStr", str);
		String result = HttpClientUtil.doPost(url, map, charset);
		ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
		if(resultEntity == null || !resultEntity.getState() || resultEntity.getResult() == null){
			LogUtils.error(PasswordServiceImpl.class+"解密失败，请检查clouddbservice_authentication是否可用，或者查看日志");
			return null;
		}
		return resultEntity.getResult().toString();
	}

}
