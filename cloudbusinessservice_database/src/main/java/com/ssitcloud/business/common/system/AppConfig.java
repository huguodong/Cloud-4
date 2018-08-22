package com.ssitcloud.business.common.system;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ssitcloud.business.common.util.ResourcesUtil;
import com.ssitcloud.business.common.util.XMLUtils;
import com.ssitcloud.business.database.util.ServerUtil;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.database.entity.Server;

@Configuration
public class AppConfig {
	@Bean(name = "requestURL")
	public RequestURLListEntity requestURL() throws Exception {
		Map<String, String> map = XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml"));
		return new RequestURLListEntity(map);
	}
	
	@Bean(name = "dataBaseMenus")
	public ConcurrentMap<String, List<Server>> dataBaseMenus() {
		return ServerUtil.dataBaseMenus();
	}
}
