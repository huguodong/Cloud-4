package com.ssitcloud.common.entity;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 根据XML配置的ResourceBundleMessageSource设置的信息 获取对应properties数据
 * 
 * @author lbh
 * 
 *         2016年3月23日
 */
public class MessageI18NEntity {

	private Locale locale;
	private ResourceBundleMessageSource msgsrc;

	private Object[] objs = new Object[0];

	public String getMessage(String propertieKey) {
		return msgsrc.getMessage(propertieKey, objs, locale);
	}

	public String getMessage(String propertieKey, Object[] params) {
		return msgsrc.getMessage(propertieKey, params, locale);
	}

	public String getMessage(String propertieKey, Object[] params, Locale locale) {
		return msgsrc.getMessage(propertieKey, params, locale);
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ResourceBundleMessageSource getMsgsrc() {
		return msgsrc;
	}

	public void setMsgsrc(ResourceBundleMessageSource msgsrc) {
		this.msgsrc = msgsrc;
	}

}
