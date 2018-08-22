package com.ssitcloud.view.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.UserEntity;
import com.ssitcloud.view.common.system.MyShiro;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.WebUtil;

/**
 * 登录控制
 * 
 * @package: com.ssitcloud.common.controller
 * @classFile: LoginController
 * @author: liuBh
 * @description: TODO
 */
@Controller
@RequestMapping(value = { "login" })
public class LoginController extends BasicController{

	@Resource(name = "requestURLListEntity")
	private RequestURLListEntity requestURLList;
	private static String controllerinfo =null;
    private static String url =null;
	
	@RequestMapping("/index")
	public String gotoIndex(HttpServletRequest request){
		System.out.println(request.getParameter("deviceCode"));
		System.out.println(request.getParameter("loginparam"));
		return "/index";
	}

	/**
	 * 登录验证
	 * 
	 * @methodName: logincheck
	 * @param req
	 * @param json
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value = { "logincheck" })
	@ResponseBody
	public ResultEntity logincheck(HttpServletRequest req, String json) {
		UserEntity user = JsonUtils.fromJson(json, UserEntity.class);
		if (user == null) {
			return new ResultEntity();
		}
		controllerinfo = req.getParameter("controllerinfo");
		url = req.getParameter("url");
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getOperator_id(), user.getOperator_pwd());
		token.setRememberMe(user.getRememberme());
		token.setHost(WebUtil.getIpAddr(req) + ":" + req.getRemotePort());
		try {
			currentUser.login(token);
			//登录成功之后判断是不是有设备注册的权限,如果不是云平台管理员，或者维护人员，要判断是不是有权限
			String type = getCurrentUser().getOperator_type();
			if (!Operator.SSITCLOUD_ADMIN.equals(type) && !Operator.SSITCLOUD_MANAGER.equals(type)
					&& !currentUser.isPermitted(MyShiro.REGISTER_CODE)) {
				throw new AuthenticationException();
			}
		} catch (UnknownAccountException ex) {
			if (ex.getMessage() != null) {
				if (ex.getMessage().equals(MyShiro.UNACTIVATED)) {
					return new ResultEntity("不在可访问的时间段");
				}
				if (ex.getMessage().equals(MyShiro.PASSWORD_INVALID)) {
					return new ResultEntity("密码已经失效，请联系管理员");
				}
			} else {
				return new ResultEntity("登录名或者密码错误");
			}
		} catch (IncorrectCredentialsException ex) {
			return new ResultEntity("登录名或者密码错误");
		} catch (LockedAccountException ex) {
			return new ResultEntity("账号已被锁定，请与系统管理员联系");
		} catch (AuthenticationException ex) {
			return new ResultEntity("您没有授权!");
		}
		return new ResultEntity(true, "登录成功");
	}
	/**
	 * 登出系统
	 * @methodName: logout
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"logout"})
	public String logout(HttpServletRequest req){
		if("switch".equals(controllerinfo)){
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
			return "redirect:"+url+"/switchlogin/switchlogin";
		}
		String devicecode = req.getParameter("deviceCode");
		String url = "";
		if (StringUtils.isNotBlank(devicecode)) {
			url = "/device/connect?deviceCode="+devicecode;
		}else{
			url = "/device/connect";
		}
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isAuthenticated()){
			currentUser.logout();
		}
		return "redirect:"+url;
	}
}
