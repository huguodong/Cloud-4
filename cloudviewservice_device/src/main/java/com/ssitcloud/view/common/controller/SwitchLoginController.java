package com.ssitcloud.view.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ssitcloud.view.common.util.*;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.UserEntity;
import com.ssitcloud.view.common.service.SwitchLoginService;
import com.ssitcloud.view.common.system.MyShiro;

/**
 * 单点登录控制
 * 
 * @author: lqw
 * @description: TODO
 */
@Controller
@RequestMapping(value = { "switchlogin" })
public class SwitchLoginController extends BasicController{
	
	@Resource
	SwitchLoginService switchLoginService;
    private static String oper_pwd="";
    
    
    /**
	 * 登录验证
	 * 
	 * @methodName: logincheck
	 * @param req
	 * @param json
	 * @return
	 * @returnType: ResultEntity
	 * @author: lqw
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
	        	return new ResultEntity(true,"登录成功");
	        }
	        oper_pwd = user.getOperator_pwd();
		return new ResultEntity(true,"登录成功");
	}
	
	/**
	 * 登出系统
	 * @methodName: logout
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: lqw
	 */
	@RequestMapping(value={"logout"})
	public String logout(HttpServletRequest req){
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isAuthenticated()){
			Operator oper=getCurrentUser();
			ResultEntity result=new ResultEntity();
			result.setState(true);
			result.setRetMessage("馆ID:"+oper.getLib_id()+"|用户ID:"+oper.getOperator_id()+"|用户名:"+oper.getOperator_name()+"||");
			SystemLogUtil.LogOperation(result,oper,req, Const.OPERCMD_LOG_OUT);
			currentUser.logout();
		}
		return "redirect:/switchIndex.html";
	}
	
	@RequestMapping(value={"switchlogin"})
	public String switchlogin(HttpServletRequest request){
	    Operator oper=getCurrentUser();
		if(oper!=null){
			oper.setOperator_pwd(oper_pwd);
			request.getSession().setAttribute("oper", oper);
			return "/page/switch/switch";
		}else{
			return "redirect:/switchIndex.html";
		}
	} 
	/**
	 * 获取url跳转不同后台登录
	 * @param request
	 * @param map
	 * @return
	 * @author: lqw
	 */
	@RequestMapping(value={"jumplogin"},method=RequestMethod.GET)
	public ModelAndView jumplogin(HttpServletRequest request,ModelMap map){
		String node_name = request.getParameter("node_name");
		Operator oper= (Operator) request.getSession().getAttribute("oper");
		String req= "{\"node_name\":\""+node_name+"\",\"lib_idx\":"+oper.getLibrary_idx()+"}";
		String req2= "{\"node_name\":\"cloudviewservice_device\",\"lib_idx\":"+oper.getLibrary_idx()+"}";
		ResultEntity resulturl = switchLoginService.getProjectURL(req);
		ResultEntity resulturl2 = switchLoginService.getProjectURL(req2);
		String url = JSONObject.fromObject(resulturl.getResult()).getString("address")+"/page/switch/jumplogin.jsp";
		String url2 = JSONObject.fromObject(resulturl2.getResult()).getString("address");
//		String url2 = "http://127.0.0.1:8080/cloudviewservice_device";
//		String url = "http://127.0.0.1:8080/"+node_name+"/page/switch/jumplogin.jsp";
        map.put("name", oper.getOperator_id());
		map.put("url", url2);
		map.put("pwd", oper.getOperator_pwd());
		return new ModelAndView("redirect:"+url, map);
	}
	/**
	 * 验证是否有后台登录权限
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value={"testAuthority"})
	@ResponseBody
	public ResultEntity testAuthority(HttpServletRequest request,String json){
		JSONObject js = JSONObject.fromObject(json);
		String[] node_name = js.getString("node_name").split(",");
		Operator oper= (Operator) request.getSession().getAttribute("oper");
		ResultEntity re = new ResultEntity();
		StringBuffer str = new StringBuffer();
		StringBuffer estr = new StringBuffer();
		for(int i=0,z=node_name.length;i<z;i++){
			ResultEntity resultEntity=new ResultEntity();
			json ="{\"operator_id\":\""+oper.getOperator_id()+"\",\"operator_pwd\":\""+oper.getOperator_pwd()+"\",\"rememberme\":\"false\"}";
			String req= "{\"node_name\":\""+node_name[i]+"\",\"lib_idx\":"+oper.getLibrary_idx()+"}";
			ResultEntity resulturl = switchLoginService.getProjectURL(req);
            //System.out.println("单点登录——>从节点获取到返回结果----"+i+"：" + JsonUtils.toJson(resulturl));
			if(!resulturl.getState()){
                System.out.println("单点登录——>从节点获取单点登录url3-->"+JsonUtils.toJson(resulturl));
                estr.append(node_name[i]).append("#").append(resulturl.getMessage()).append(";");
                continue;
            }
			String url = JSONObject.fromObject(resulturl.getResult()).getString("address")+"/login/logincheck";
//			String url = "http://127.0.0.1:8080/"+node_name[i]+"/login/logincheck";
			Map<String, String> map = new HashMap<String, String>();
			map.put("json",json);
			String string = HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
            //System.out.println("单点登录——>认证用户是否有登录后台权限返回参数----"+i+"：" + string);
			if(JSONUtils.mayBeJSON(string)){
			    resultEntity=JsonUtils.fromJson(string, ResultEntity.class);
			}else{
				resultEntity.setRetMessage(string);
			}
			if(resultEntity.getState()){
				str.append(node_name[i]).append(",");
			}else{
				estr.append(node_name[i]).append("#").append(resultEntity.getMessage()+resultEntity.getRetMessage()).append(";");
			}
		}
		if(str.length() >0){
			re.setResult(str.substring(0, str.length()-1).toString());
		}
		if(estr.length() >0){
			re.setRetMessage(estr.substring(0, estr.length()-1).toString());
		}
		return re;
	}
}
