package com.ssitcloud.view.common.controller;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.UserEntity;
import com.ssitcloud.view.common.system.MyShiro;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.common.util.WebUtil;
import com.ssitcloud.view.database.service.ServerService;

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

	@Resource
	private ServerService serverService;
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
			Subject currentUser = SecurityUtils.getSubject();
	    	UsernamePasswordToken token = new UsernamePasswordToken(user.getOperator_id(), user.getOperator_pwd());
	        token.setRememberMe(user.getRememberme());
	        token.setHost(WebUtil.getIpAddr(req)+":"+req.getRemotePort());
	        try{
	            currentUser.login(token);
	        }catch(UnknownAccountException ex){
	        	ex.printStackTrace();
	        	if (ex.getMessage()!=null && ex.getMessage().equals(MyShiro.UNACTIVATED)) {
	        		return new ResultEntity("无效用户");
				}else{
					return new ResultEntity("登录名或者密码错误");
				}
	        }catch(IncorrectCredentialsException ex){
	        	  return new ResultEntity("登录名或者密码错误");
	        }catch(LockedAccountException ex){
	        	  return new ResultEntity("账号已被锁定，请与系统管理员联系");
	        }catch(AuthenticationException ex){
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
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isAuthenticated()){
			Operator oper=getCurrentUser();
			ResultEntity result=new ResultEntity();
			result.setState(true);
			result.setRetMessage("馆ID:"+oper.getLib_id()+"|用户ID:"+oper.getOperator_id()+"|用户名:"+oper.getOperator_name());
			SystemLogUtil.LogOperation(result,oper,req, Const.OPERCMD_LOG_OUT);
			currentUser.logout();
		}
		return "redirect:/";
	}
	/**
	 * main.jsp 获取系统时间
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"getTime"})
	@ResponseBody
	public ResultEntity getTime(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		DateTime dateTime2 = new DateTime();
		String string_c = dateTime2.toString("今天是 yyyy年MM月dd日  EE",Locale.CHINESE);    
		result.setResult(string_c);
		return result;
	}
	
	@RequestMapping("/getUserMenus")
	@ResponseBody
	public ResultEntity getUserMenu(HttpServletRequest request) {
		return serverService.getUserMenus();
	}
	
	@RequestMapping("/getServerConfig")
	@ResponseBody
	public ResultEntity getServerConfig(HttpServletRequest request) {
		return serverService.getServerConfig();
	}
}
