package com.ssitcloud.business.mobile.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.PublicKeyServiceI;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("/publicKey")
public class PublicKeyController {
	@Autowired
	private PublicKeyServiceI publicKeyService;
	/**
	 * 获取公钥信息
	 * 
	 * @param request
	 *            json={"app_type":app代码(1:苹果,2:安卓)}
	 * @return
	 */
	@RequestMapping("/lastestKey")
	@ResponseBody
	public ResultEntity key(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		try {
			Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
			Integer appType = (Integer) map.get("app_type");
			if(appType != null){
				return publicKeyService.selectNewPublic(appType);
			}
		} catch (Exception e) {
			LogUtils.info(e);
		}
		return resultEntity;
	}
}
