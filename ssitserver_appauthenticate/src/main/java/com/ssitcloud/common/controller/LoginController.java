package com.ssitcloud.common.controller;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.UserEntity;
import com.ssitcloud.common.service.UserService;
import com.ssitcloud.common.util.JsonUtils;
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

	@Resource
	private UserService userService;
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
	        }catch(AuthenticationException ex){
				return new ResultEntity("登录名或者密码错误");
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
	
	@RequestMapping(value={"changePassword"})
	@ResponseBody
	public ResultEntity changePassword(HttpServletRequest request, String json){
		ResultEntity result=new ResultEntity();
		JSONObject js = JSONObject.fromObject(json);
		String oldpasswd = js.getString("old");
		String newpasswd = js.getString("pwd1");
		Operator operator = getCurrentUser();
		if(!oldpasswd.equals(operator.getOperator_pwd())){
			result.setState(false);
			result.setRetMessage("oldpswderror");
			result.setMessage("原始密码错误");
			return result;
		}
		UserEntity user = new UserEntity();
		user.setOperator_id(operator.getOperator_id());
		user.setOperator_pwd(newpasswd);
		int num = userService.changePassword(user);
		if(num<=0){
			result.setState(false);
			result.setMessage("修改失败");
		}else{
			result.setState(true);
		}
		return result;
	}
}
