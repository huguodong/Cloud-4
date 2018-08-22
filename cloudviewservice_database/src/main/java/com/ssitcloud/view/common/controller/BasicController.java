package com.ssitcloud.view.common.controller;

import net.sf.json.util.JSONUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.view.common.util.JsonUtils;

@Controller
public class BasicController {
	
	
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
	
}
