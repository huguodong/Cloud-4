package com.ssitcloud.view.common.system;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssitcloud.view.common.util.WebUtil;

public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {
	private static final Logger log = LoggerFactory.getLogger("CaptchaFormAuthenticationFilter.class");
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if (!WebUtil.isAjax(httpServletRequest)) {// 不是ajax请求
			issueSuccessRedirect(request, response);
		} else {
			httpServletResponse.setCharacterEncoding("UTF-8");
			PrintWriter out = httpServletResponse.getWriter();
			out.println("{success:true,message:'登入成功'}");
			out.flush();
			out.close();
		}
		return false;
	}

	/**
	 * 主要是处理登入失败的方法
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		if (!WebUtil.isAjax((HttpServletRequest)request)) {// 不是ajax请求
			setFailureAttribute(request, e);
			return true;
		}
		try {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String message = e.getClass().getSimpleName();
			if ("IncorrectCredentialsException".equals(message)) {
				out.println("{success:false,message:'密码错误'}");
			} else if ("UnknownAccountException".equals(message)) {
				out.println("{success:false,message:'账号不存在'}");
			} else if ("LockedAccountException".equals(message)) {
				out.println("{success:false,message:'账号被锁定'}");
			} else {
				out.println("{success:false,message:'未知错误'}");
			}
			out.flush();
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return false;
	}
}
