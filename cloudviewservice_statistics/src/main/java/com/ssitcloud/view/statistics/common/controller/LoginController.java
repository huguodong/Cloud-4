package com.ssitcloud.view.statistics.common.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

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
import com.ssitcloud.common.entity.UserMenuPermessionEntity;
import com.ssitcloud.view.statistics.common.system.MyShiro;
import com.ssitcloud.view.statistics.common.util.JsonUtils;
import com.ssitcloud.view.statistics.common.util.LogUtils;
import com.ssitcloud.view.statistics.common.util.SystemLogUtil;
import com.ssitcloud.view.statistics.common.util.WebUtil;

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
    private static String oper_pwd=null;

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
			oper_pwd = user.getOperator_pwd();
			Subject currentUser = SecurityUtils.getSubject();
	    	UsernamePasswordToken token = new UsernamePasswordToken(user.getOperator_id(), user.getOperator_pwd());
	        token.setRememberMe(user.getRememberme());
	        token.setHost(WebUtil.getIpAddr(req)+":"+req.getRemotePort());
	        try{
	            currentUser.login(token);
	        }catch(UnknownAccountException ex){
	        	if (ex.getMessage()!=null) {
	        		if(ex.getMessage().equals(MyShiro.UNACTIVATED)){
	        			return new ResultEntity("不在可访问的时间段");
	        		}
	        		if(ex.getMessage().equals(MyShiro.PASSWORD_INVALID)){
	        			return new ResultEntity("密码已经失效，请联系管理员");
	        		}
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
	        Operator oper=getCurrentUser();
			if(oper!=null){
				req.getSession().setAttribute("oper", oper);
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
			Operator oper=getCurrentUser();
			ResultEntity result=new ResultEntity();
			result.setState(true);
			result.setRetMessage("馆ID:"+oper.getLib_id()+"|用户ID:"+oper.getOperator_id()+"|用户名:"+oper.getOperator_name()+"||");
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
	
	@RequestMapping(value={"main"})
	public String main(HttpServletRequest request){
		Operator oper=getCurrentUser();
		if(oper!=null){
			if("switch".equals(controllerinfo)){
				oper.setOperator_pwd(oper_pwd);
			}
			request.getSession().setAttribute("oper", oper);
		}
		return "/page/main";
	} 
	
	/**
	 * 获取用户的菜单
	 *
	 * <p>2017年2月14日 下午5:37:47 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getUserMenus")
	@ResponseBody
	public ResultEntity getUserMenu(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Operator operator = getCurrentUser();
			List<UserMenuPermessionEntity> menu = operator.getUserMenuPermessions();
			if (menu==null) {
				return resultEntity;
			}
			List<Map<String, Object>> returnMenu = new ArrayList<>();
			Map<String, Object> mainMenuSub = new HashMap<>();
			Map<String, Object> mainMenuName = new HashMap<>();
			Map<String, Object> mainMenuSort = new HashMap<>();
			Map<String, Object> mainMenuMark = new HashMap<>();
			for (UserMenuPermessionEntity sub : menu) {
				List<Map<String, Object>> subMenuList = new ArrayList<>();
				Map<String, Object> subMenu = new HashMap<>();
				if(mainMenuSub.get(sub.getMain_menu_code())==null){
					mainMenuSub.put(sub.getMain_menu_code(), subMenuList);//sub_menu
					mainMenuName.put(sub.getMain_menu_code(), sub.getMain_menu_name());//name
					mainMenuSort.put(sub.getMain_menu_code(), sub.getMenu_sort());//sort
					mainMenuMark.put(sub.getMain_menu_code(), sub.getMenu_mark());//sort
				}else{
					subMenuList = (List<Map<String, Object>>) mainMenuSub.get(sub.getMain_menu_code());
				}
				subMenu.put("code", sub.getSub_menu_code());
				subMenu.put("name", sub.getSub_menu_name());
				subMenu.put("sub_menu_sort", sub.getSub_menu_sort());
				subMenu.put("url", sub.getSub_menu_mark());
				subMenuList.add(subMenu);
				
				Collections.sort(subMenuList, new Comparator<Map<String, Object>>() {
					
					@Override
					public int compare(Map<String, Object> m1,
							Map<String, Object> m2) {
						if(m2==null || m1==null){	return 0;	}
						if(m1.get("sub_menu_sort")==null){	return 1;	}
						if(m2.get("sub_menu_sort")==null){	return -1;	}
						return ((Integer)m1.get("sub_menu_sort")).compareTo((Integer) m2.get("sub_menu_sort"));
					}
				});
			}
			for (Entry<String, Object> map : mainMenuSub.entrySet()) {
				Map<String, Object> mainMenu = new HashMap<>();
				mainMenu.put("code", map.getKey());
				mainMenu.put("name", mainMenuName.get(map.getKey()));
				mainMenu.put("menu_sort", mainMenuSort.get(map.getKey()));
				mainMenu.put("menu_mark", mainMenuMark.get(map.getKey()));
				mainMenu.put("subMenu", mainMenuSub.get(map.getKey()));
				returnMenu.add(mainMenu);
			}

			Collections.sort(returnMenu, new Comparator<Map<String, Object>>() {
				
				@Override
				public int compare(Map<String, Object> m1,
						Map<String, Object> m2) {
					if(m2==null || m1==null){	return 0;	}
					if(m1.get("menu_sort")==null){	return -1;	}
					if(m2.get("menu_sort")==null){	return 1;	}
					return ((Integer)m1.get("menu_sort")).compareTo((Integer) m2.get("menu_sort"));
				}
			});
			resultEntity.setValue(true, "", "", returnMenu);
		} catch (Exception e) {
			LogUtils.error("",e);
		}
		return resultEntity;
	}
	
}
