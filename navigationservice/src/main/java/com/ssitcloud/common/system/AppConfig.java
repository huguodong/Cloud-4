package com.ssitcloud.common.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import com.ssitcloud.common.entity.SystemEntity;

@Configuration
public class AppConfig {

	@Value("${system.language}")
	private String systemLanguage;

	@Bean(name = "systemEntity")
	public SystemEntity systemEntity() {
		Assert.hasText(systemLanguage, "系统默认语言不能为空！！！请查看config.porperties system.language设置");
		SystemEntity system = new SystemEntity();
		system.setSystemLanguage(systemLanguage);
		return system;
	}
}
