package com.ssitcloud.common.system;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.ssitcloud.common.utils.CommonException;

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
		try {
			return beanFactory.getBean(beanName, requiredType);
		} catch (NoSuchBeanDefinitionException  noSuchBean) {
			throw new CommonException(beanName+":方法名错误，请检查"+noSuchBean.getMessage());
		}catch (Exception e) {
			throw new CommonException(e);
		}
	
	}
	public static Object getBean(String beanName){
		return beanFactory.getBean(beanName);
	}
	public static <T> T getBean(Class<T> requredType){
		return beanFactory.getBean(requredType);
	}

}
