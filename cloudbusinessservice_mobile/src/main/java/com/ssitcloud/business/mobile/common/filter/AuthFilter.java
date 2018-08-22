package com.ssitcloud.business.mobile.common.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.ReaderAuthServiceI;
import com.ssitcloud.business.mobile.service.impl.ReaderAuthServiceImpl.PasswordServiceFailException;

/**
 * 安全过滤器，用于需要鉴权的资源
 * @author LXP
 * @version 创建时间：2017年3月27日 下午5:56:36
 */
public abstract class AuthFilter extends OncePerRequestFilter{

	@Autowired
	private ReaderAuthServiceI readerAuthService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        String authStr = getAuthStr(request);
		if(authStr == null){
            forbidden(request,response);
			return ;
		}

		boolean strictModel = true;
		String path = request.getServletPath();
		String commonMode = getFilterConfig().getInitParameter("commonMode");
		String[] commonModes = commonMode.split(",");
		for (String string : commonModes) {
			if(path.matches(string.trim())){
				strictModel = false;
				break;
			}
		}
		//使用服务识别用户请求
		try {
			boolean result;
			if(strictModel){
				result = readerAuthService.auth(authStr, request);
			}else{
				result = readerAuthService.auth(authStr, null);
			}

			if(result){
                check(request);
				filterChain.doFilter(request, response);
				return ;
			}
		} catch (PasswordServiceFailException e) {
			LogUtils.error(e);
		}

        forbidden(request,response);
    }

    protected abstract String getAuthStr(HttpServletRequest request);

    abstract protected void check(HttpServletRequest request);

    abstract protected void forbidden(HttpServletRequest request, HttpServletResponse response);
}
