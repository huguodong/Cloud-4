package com.ssit.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ssitcloud.view.node.entity.ContainerEntity;

public class Test {
	public static void main(String[] args) {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("springMVC.xml");
		ContainerEntity containerEntity = (ContainerEntity) appCtx.getBean("containerEntity");
		containerEntity.toString();
	}
}
