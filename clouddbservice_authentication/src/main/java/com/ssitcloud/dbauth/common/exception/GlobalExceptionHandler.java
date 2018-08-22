package com.ssitcloud.dbauth.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * 全局异常统一处理类
 * 
 * 2016年3月22日 下午5:56:06 
 * @author hjc 
 * 
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		try {
			System.out.println("进入全局错误处理类");
			LogUtils.error("出错了", ex);
			PrintWriter writer = response.getWriter();
			// 如果是ajax则返回错误码,然后让页面进行跳转
			String ajax = request.getHeader("X-Requested-With");
			if ("XMLHttpRequest".equals(ajax)) {
				writer.print("{\"error\":\"error\"}");
				writer.flush();
				writer.close();
			} else {
				response.sendRedirect(request.getContextPath() + "/page/error.jsp");
			}
		} catch (IOException e) {
			e.printStackTrace();
			LogUtils.error("出错了", e);
		}
		return new ModelAndView("/page/error");
	}

}
