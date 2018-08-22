package com.ssitcloud.view.common.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.view.common.util.JsonUtils;

@Controller
public class BasicController {
	
	@Resource(name="sessionDAO")
	private SessionDAO sessionDAO;
	
	/**
	 * 获取当前操作的 用户信息
	 * @return
	 */
	public Operator getCurrentUser(){
		Subject sub=SecurityUtils.getSubject();
		if(sub!=null){
			String operatorString=(String) sub.getPrincipal();
			if(JSONUtils.mayBeJSON(operatorString)){
				return JsonUtils.fromJson(operatorString, Operator.class);
			}
		}
		return null;
	}
	/**
	 * 获取当前操作的 用户信息 JSON String 
	 * @return
	 */
	public String getCurrentUserJson(){
		Subject sub=SecurityUtils.getSubject();
		String operatorString = null;
		if(sub!=null){
			 operatorString=(String) sub.getPrincipal();
		}
		return operatorString;
	}
	/**************************
	 * 检查有没有权限
	 * 没有权限返回 :false
	 * 有权限的话返回: true
	 * @param permession metadata_opercmd表的 opercmd字段
	 * @return
	 *************************/
	public boolean checkPermession(String permession){
		Subject sub=SecurityUtils.getSubject();
		if(sub!=null){
			try {
				sub.checkPermission(permession);
			} catch (AuthorizationException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取所有在线的用户 名字
	 * @return
	 */
	public Set<String> getOnlineLoginNames() {
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		Iterator<Session> it = sessions.iterator();
		Set<String> loginNames = new HashSet<String>();
		while (it.hasNext()) {
			Session session = (Session) it.next();
			String str = session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY").toString();
			loginNames.add(str);//str为operator的JSON数据 
		}
		return loginNames;
	}
	/**
	 * 将用户踢出系统
	 * @param username 登录名
	 */
	public void kickOutUser(String username){
		if(username==null) 
			return;
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		Iterator<Session> it = sessions.iterator();
		while (it.hasNext()) {
			Session session = (Session) it.next();
			Object session_obj = session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
			if(session_obj==null)
				return;
			String session_key=session_obj.toString();
			if(JSONUtils.mayBeJSON(session_key)){
				JsonNode node=JsonUtils.readTree(session_key);
				if(node!=null && node.get("operator_id")!=null){
					String operator_id=node.get("operator_id").asText();
					if(username.equals(operator_id)){
						session.stop();
						break;
					}
				}
			}
		}
	}
	
}
