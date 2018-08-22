package com.ssitcloud.business.common.system;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class BeanFactoryHelper implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		BeanFactoryHelper.beanFactory = beanFactory;
	}

	public static BeanFactory getBeanfactory() {
		return beanFactory;
	}
	
	public static <T> T  getBean(String beanName,Class<T> requiredType){
		return beanFactory.getBean(beanName, requiredType);
	}
	public static Object getBean(String beanName){
		return beanFactory.getBean(beanName);
	}
	public static <T> T getBean(Class<T> requredType){
		return beanFactory.getBean(requredType);
	}

}
