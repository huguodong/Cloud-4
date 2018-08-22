package com.ssitcloud.business.mobile.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.service.ElectronicCertificateServiceI;
import com.ssitcloud.business.mobile.service.MobileMessageServiceI;
import com.ssitcloud.common.entity.ResultEntity;

import net.sf.json.util.JSONUtils;

/**
 * 短信服务
 * @author LXP
 * @version 创建时间：2017年2月28日 上午10:57:11
 */
@Controller
@RequestMapping("/mobileServer")
public class MobileMessageController {
	
	@Autowired
	private MobileMessageServiceI mobileMessageServiceI;
	
	@RequestMapping("/sendSMS")
	@ResponseBody
	public ResultEntity sendSMS(HttpServletRequest request) {
		String json = request.getParameter("json");
		if (!JSONUtils.mayBeJSON(json)) {
			return new ResultEntity(false,"参数不正确");
		}
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
		String phone = map.get("phone") + "";
		String content = map.get("content") + "";
		if(!StringUtils.hasText(phone)||!StringUtils.hasText(content)){
			return new ResultEntity(false,"参数不正确");
		}
		
		boolean isSend = mobileMessageServiceI.sendSMS(phone, content);
		if (isSend) {
			resultEntity.setState(true);
			resultEntity.setResult(isSend);
			resultEntity.setMessage("发送成功");
		} else {
			resultEntity.setState(false);
			resultEntity.setMessage("发送失败");
		}
		return resultEntity;
	}
	
	
	@RequestMapping("/sendSMSOnTemplate")
	@ResponseBody
	public ResultEntity sendSMSOnTemplate(HttpServletRequest request) {
		String json = request.getParameter("json");
		if (!JSONUtils.mayBeJSON(json)) {
			return new ResultEntity(false,"参数不正确");
		}
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
		String phone = map.get("phone") + "";
		String content = map.get("content") + "";
		if(!StringUtils.hasText(phone)||!StringUtils.hasText(content)){
			return new ResultEntity(false,"参数不正确");
		}
		
		boolean isSend = mobileMessageServiceI.sendSMSOnTemplate(phone, content);
		if (isSend) {
			resultEntity.setState(true);
			resultEntity.setResult(isSend);
			resultEntity.setMessage("发送成功");
		} else {
			resultEntity.setState(false);
			resultEntity.setMessage("发送失败");
		}
		return resultEntity;
	}
}
