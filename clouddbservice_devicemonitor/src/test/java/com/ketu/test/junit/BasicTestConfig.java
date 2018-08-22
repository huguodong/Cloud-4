package com.ketu.test.junit;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.Configuration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ssitcloud.common.service.BasicService;
import com.ssitcloud.common.system.MongoDB;
import com.ssitcloud.common.system.MongoInstance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/springMVC.xml")
public class BasicTestConfig {
	@Resource(name = "mongoDBImpl")
	protected MongoDB mongoDB;
	@Resource(name = "mongoInstances")
	protected Map<String, MongoInstance> mongoInstances;
	@Resource(name = "xmlconfig")
	protected Configuration xmlconfig;

	protected String key = "127.0.0.127017superuserpwdadmin";

	public String dbName = "device_state_template";
}
