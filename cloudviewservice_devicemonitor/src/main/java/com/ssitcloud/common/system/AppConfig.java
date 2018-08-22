package com.ssitcloud.common.system;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.SystemEntity;
import com.ssitcloud.common.util.ResourcesUtil;
import com.ssitcloud.common.util.XMLUtils;

@Configuration
public class AppConfig {
	/**
	 * <p>
	 * 随着spring启动，将RequestURL.xml中的Url信息装入RequestURLListEntity中
	 * </p>
	 * 
	 * 使用方法 <br/>
	 * @Resource(name="requestURL")<br/>
	 * RequestURLListEntity requestURLListEntity;<br/>
	 * <br/>
	 * 获取requestURLListEntity对象，再通过ID属性获取对应的requestURL<br/>
	 * 
	 * @methodName: requestURL
	 * @return
	 * @throws Exception
	 * @returnType: RequestURLListEntity
	 * @author: liuBh
	 */
	@Bean(name = "requestURLListEntity")
	public RequestURLListEntity requestURL() throws Exception {
		Map<String, String> map = XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml"));
		return new RequestURLListEntity(map);
	}
	@Value("${system.language}")
	private String systemLanguage;
	
	@Bean(name="systemEntity")
	public SystemEntity systemEntity(){
		Assert.hasText(systemLanguage, "系统默认语言不能为空！！！请查看config.porperties system.language设置");
		SystemEntity system=new SystemEntity();
		system.setSystemLanguage(systemLanguage);
		return system;
	}

}
