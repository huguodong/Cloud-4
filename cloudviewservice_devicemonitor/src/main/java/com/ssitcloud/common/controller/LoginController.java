package com.ssitcloud.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.UserEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.common.util.WebUtil;

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
	private static String controllerinfo =null;
    private static String url =null;

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
		UserEntity user=JsonUtils.fromJson(json, UserEntity.class);
		if(user==null){
			return new ResultEntity();
		}
		controllerinfo = req.getParameter("controllerinfo");
		url = req.getParameter("url");
		Subject currentUser = SecurityUtils.getSubject();
	    	UsernamePasswordToken token = new UsernamePasswordToken(user.getOperator_id(), user.getOperator_pwd());
	        token.setRememberMe(user.getRememberme());
	        token.setHost(WebUtil.getIpAddr(req)+":"+req.getRemotePort());
	        try{
	            currentUser.login(token);
	            String type = getCurrentUser().getOperator_type();
	            //如果不是云平台管理员，或者 如果没有以下权限之一，不让登陆
	            if (!(Operator.SSITCLOUD_ADMIN.equals(type) 
	            		||currentUser.isPermitted(Const.OPERCMD_MONITOR_MAINPAGE)
	            		||currentUser.isPermitted(Const.OPERCMD_MONITOR_SHUTDOWN)
	            		||currentUser.isPermitted(Const.OPERCMD_MONITOR_RESTART)
	            		||currentUser.isPermitted(Const.OPERCMD_MONITOR_DOWN_LOG)
	            		||currentUser.isPermitted(Const.OPERCMD_MONITOR_DEV_LOCK)
	            		||currentUser.isPermitted(Const.OPERCMD_MONITOR_CANCAL_OPER)
	            		||currentUser.isPermitted(Const.OPERCMD_MONITOR_REMOTE_CONTROL))
	            		) {
					throw new AuthenticationException();
				}
	        }catch(UnknownAccountException ex){
	            LogUtils.info("账号错误");
	            return new ResultEntity("登录名或者密码错误");
	        }catch(IncorrectCredentialsException ex){
	        	 LogUtils.info("密码错误");
	        	  return new ResultEntity("登录名或者密码错误");
	        }catch(LockedAccountException ex){
	        	 LogUtils.info("账号已被锁定，请与系统管理员联系");
	        	  return new ResultEntity("账号已被锁定，请与系统管理员联系");
	        }catch(AuthenticationException ex){
	        	  LogUtils.info("您没有授权!");
	        	  return new ResultEntity("您没有授权!");
	        }
		return new ResultEntity(true,"登录成功");
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
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isAuthenticated()){
			currentUser.logout();
		}
		return "redirect:/";
	}
	/**
	 * 处理非ajax页面跳转 session过期的问题
	 * @param request
	 * @author lbh
	 * @return
	 */
	@RequestMapping(value={"checkSession"})
	@ResponseBody
	public Map<String,Object> checkSession(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("checkSession", true);
		return model;
	}
}
