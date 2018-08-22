package com.ssitcloud.common.system;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.WebUtil;

public class RoleAuthorizationFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {

        Subject subject = getSubject(request, response);  
        String[] rolesArray = (String[]) mappedValue;  
  
        if (rolesArray == null || rolesArray.length == 0) {  
            // no roles specified, so nothing to check - allow access.  
            return true;  
        }  
        Set<String> roles = CollectionUtils.asSet(rolesArray);  
        for (String role : roles) {  
            if (subject.hasRole(role)) {  
                return true;  
            }  
        }  
        return false;  
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws IOException {
		  Subject subject = getSubject(request, response);
	        // If the subject isn't identified, redirect to login URL
	        if (subject.getPrincipal() == null) {
	        	if(WebUtil.isAjax((HttpServletRequest)request)){
	        		WebUtil.sendJson((HttpServletResponse)response, JsonUtils.toJson(new ResultEntity("您尚未登录或登录时间过长,请重新登录!")));
	        		return false;
	        	}
	        	saveRequestAndRedirectToLogin(request, response);
	        	
	        } else {
	        	if(WebUtil.isAjax((HttpServletRequest)request)){
	        		WebUtil.sendJson((HttpServletResponse)response, JsonUtils.toJson(new ResultEntity("您没有足够的权限执行该操作!")));
	        		return false;
	        	}
	            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
	            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
	            String unauthorizedUrl = getUnauthorizedUrl();
	            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
	            if (StringUtils.hasText(unauthorizedUrl)) {
	                WebUtils.issueRedirect(request, response, unauthorizedUrl);
	            } else {
	                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
	            }
	        }
	        return false;
	}

}
