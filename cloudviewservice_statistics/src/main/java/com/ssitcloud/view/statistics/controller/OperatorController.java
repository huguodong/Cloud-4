package com.ssitcloud.view.statistics.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.controller.BasicController;
import com.ssitcloud.view.statistics.common.util.ExceptionHelper;
import com.ssitcloud.view.statistics.common.util.JsonUtils;
import com.ssitcloud.view.statistics.common.util.LogUtils;
import com.ssitcloud.view.statistics.common.util.SystemLogUtil;
import com.ssitcloud.view.statistics.service.OperatorService;


@Controller
@RequestMapping("/operator")
public class OperatorController extends BasicController{
	
	@Resource
	private OperatorService operatorService;
	
	/**
	 * 登陆的时候检测用户状态
	 *
	 * <p>2016年12月20日 上午10:45:53 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkOperatorStatus")
	@ResponseBody
	public ResultEntity checkOperatorStatus(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Operator operator = getCurrentUser();
			String firstChange = operator.getFirstChange();
			String pwdInvalid = operator.getPwdInvalid();
			String sessionPwdInvalid = (String) request.getSession().getAttribute("sessionPwdInvalid");//用session来判断每次登陆只提示一次
			
			if( !StringUtils.isBlank(firstChange) 
					&& "1".equals(firstChange)){
				resultEntity.setValue(true, "changePwd", "", "");	
				return resultEntity;
			}
			
			if ( (StringUtils.isBlank(sessionPwdInvalid) || "null".equals(sessionPwdInvalid))
					&& !StringUtils.isBlank(pwdInvalid) 
					&& !"-1".equals(pwdInvalid)) {
				resultEntity.setValue(true, "alert", "", pwdInvalid);
				request.getSession().setAttribute("sessionPwdInvalid", "1");
				return resultEntity;
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 用户修改密码
	 *
	 * <p>2016年7月28日 下午3:14:19 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/changePassword")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ResultEntity changePassword(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("json");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空！");
				return resultEntity;
			}
			Map<String, String> param = JsonUtils.fromJson(req, Map.class);
			String old = param.get("old");
			String pwd1 = param.get("pwd1");
			String pwd2 = param.get("pwd2");
			if(StringUtils.isBlank(old) || StringUtils.isBlank(pwd1) || StringUtils.isBlank(pwd2)){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空！");
				return resultEntity;
			}
			
			if (StringUtils.isBlank(getCurrentUser().getOperator_id())) {
				resultEntity.setState(false);
				resultEntity.setMessage("无法获取登录信息，尝试重新登录！");
				return resultEntity;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("operid", getCurrentUser().getOperator_id());
			map.put("operidx", getCurrentUser().getOperator_idx());
			
			map.put("old", old);
			map.put("pwd1", pwd1);
			map.put("pwd2", pwd2);
			
			resultEntity = operatorService.changePassword(JsonUtils.toJson(map));
			
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_UPD_PWD);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}


}
