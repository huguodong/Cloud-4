package com.ssitcloud.view.common.system;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.SystemEntity;
import com.ssitcloud.database.entity.HeartBeatServer;
import com.ssitcloud.view.common.util.ResourcesUtil;
import com.ssitcloud.view.common.util.XMLUtils;

@Configuration
public class AppConfig {
	@Bean(name = "requestURLListEntity")
	public RequestURLListEntity requestURL() throws Exception {
		Map<String, String> map = XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml"));
		return new RequestURLListEntity(map);
	}

	@Value("${system.language}")
	private String systemLanguage;

	@Bean(name = "systemEntity")
	public SystemEntity systemEntity() {
		Assert.hasText(systemLanguage, "系统默认语言不能为空！！！请查看config.porperties system.language设置");
		SystemEntity system = new SystemEntity();
		system.setSystemLanguage(systemLanguage);
		return system;
	}
	@Bean(name="heartBeatMysqlServer")
	public HeartBeatServer heartBeatMysqlServer(){
		return new HeartBeatServer(100);
	}
}
