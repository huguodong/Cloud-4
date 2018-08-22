package com.ssitcloud.view.statistics.common.system;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssitcloud.view.statistics.common.util.WebUtil;

public class MyAuthenticationFilter extends FormAuthenticationFilter {
	private static final Logger log = LoggerFactory.getLogger("MyAuthenticationFilter.java");
	
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		 if (isLoginRequest(request, response)) {
	            if (isLoginSubmission(request, response)) {
	                if (log.isTraceEnabled()) {
	                    log.info("Login submission detected.  Attempting to execute login.");
	                }
	                return executeLogin(request, response);
	            } else {
	                if (log.isTraceEnabled()) {
	                    log.info("Login page view.");
	                }
	                //allow them to see the login page ;)
	                return true;
	            }
	        } else {
	            if (log.isTraceEnabled()) {
	                log.info("Attempting to access a path which requires authentication.  Forwarding to the " +
	                        "Authentication url [" + getLoginUrl() + "]");
	            }
	            if(WebUtil.isAjax((HttpServletRequest)request)){
	            	 HttpServletResponse res = WebUtils.toHttp(response);  
	                 res.sendError(HttpServletResponse.SC_UNAUTHORIZED); 
	        	}else{
	        		saveRequestAndRedirectToLogin(request, response);
	        	}
	            return false;
	        }
	        
	}
	
	
}
