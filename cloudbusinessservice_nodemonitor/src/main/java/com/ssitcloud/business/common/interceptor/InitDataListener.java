package com.ssitcloud.business.common.interceptor;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ServletContextAware;

/**
 * 容器启动时运行，对默认系统语言进行初始化
 * 
 * @author hjc
 * @date 2015年9月8日 下午7:26:55
 * @comment
 * 
 */
public class InitDataListener implements InitializingBean, ServletContextAware {

	@Value("${language}")
	private String language;

	@Override
	public void setServletContext(ServletContext servletContext) {
		if (!StringUtils.hasText(language)) {
			language = "zh_CN";
		}
		servletContext.setAttribute("lang", language);// 设置系统默认语言
		System.out.println("##################Set default language：" + language);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}
}
