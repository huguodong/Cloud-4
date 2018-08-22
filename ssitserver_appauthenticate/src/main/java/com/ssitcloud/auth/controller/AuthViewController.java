package com.ssitcloud.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.auth.entity.AuthEntity;
import com.ssitcloud.auth.entity.page.AuthPageEntity;
import com.ssitcloud.auth.service.AuthViewService;
import com.ssitcloud.common.controller.BasicController;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "auth" })
public class AuthViewController extends BasicController {
	@Resource
	private AuthViewService authViewService;

	/** 分页查询 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ModelAndView queryAuthByPage(HttpServletRequest request, String req) {
		Map<String, Object> model = new HashMap<>();
		model.put("oper", getCurrentUser());
		return new ModelAndView("/page/authmgmt/auth-manage", model);
	}

	/** 根据条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryAuthByParam(HttpServletRequest request, String req) {
		AuthPageEntity entity = authViewService.queryAuthByParam(req);
		ResultEntity result = new ResultEntity();
		if (entity != null) {
			result.setValue(true, "success", "", entity);
		} else {
			result.setState(false);
		}
		return result;
	}

	/** 根据id查询 */
	@RequestMapping(value = { "queryAuthByRandomCode" })
	@ResponseBody
	public ResultEntity queryAuthByRandomCode(HttpServletRequest request, String req) {
		AuthEntity entity = authViewService.queryAuthByRandomCode(req);
		ResultEntity result = new ResultEntity();
		if (entity != null) {
			result.setValue(true, "success", "", entity);
		} else {
			result.setState(false);
		}
		return result;
	}

	/** 更新 */
	@RequestMapping(value = { "update" })
	@ResponseBody
	public ResultEntity updateAuth(HttpServletRequest request, String req) {
		return authViewService.updateAuth(req);
	}

	/** 新增 */
	@RequestMapping(value = { "add" })
	@ResponseBody
	public ResultEntity addAuth(HttpServletRequest request, String req) {
		return authViewService.addAuth(req);
	}
	
	/** 提供对外接口，功能：插入认证数据  */
	@RequestMapping(value = { "appServerResult" })
	@ResponseBody
	public Boolean appServerResult(HttpServletRequest request, String req) {
		return authViewService.appServerResult(req);
	}
	
	/** 提供对外接口，功能：设备端查询认证结果  */
	@RequestMapping(value = { "authenticateResult" })
	@ResponseBody
	public String authenticateResult(HttpServletRequest request, String req) {
		return authViewService.authenticateResult(req);
	}
	
	/** 提供对外接口，功能：门禁扫码插入认证数据  */
	@RequestMapping(value = { "appServerResult_acs" })
	@ResponseBody
	public Boolean appServerResult_acs(HttpServletRequest request, String req) {
		return authViewService.appServerResult_acs(req);
	}
	
	/** 提供对外接口，功能：门禁扫码设备端查询认证结果  */
	@RequestMapping(value = { "authenticateResult_acs" })
	@ResponseBody
	public String authenticateResult_acs(HttpServletRequest request, String req) {
		return authViewService.authenticateResult_acs(req);
	}
	
	/** 提供对外接口，功能：插入微信支付结果  */
	@RequestMapping(value = { "appServerResult_pay" })
	@ResponseBody
	public Boolean appServerResult_pay(HttpServletRequest request, String req) {
		return authViewService.appServerResult_pay(req);
	}
	
	/** 提供对外接口，功能：设备端查询微信支付结果  */
	@RequestMapping(value = { "authenticateResult_pay" })
	@ResponseBody
	public String authenticateResult_pay(HttpServletRequest request, String req) {
		return authViewService.authenticateResult_pay(req);
	}
}
