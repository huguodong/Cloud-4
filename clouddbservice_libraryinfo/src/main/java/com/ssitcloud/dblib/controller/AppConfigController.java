package com.ssitcloud.dblib.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.ssitcloud.common.constant.AppConfigConstant;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.AppConfigService;
import com.ssitcloud.dblib.common.factory.BeanFactoryHelper;
import com.ssitcloud.dblib.common.system.ApplicationContextHolder;
import com.ssitcloud.dblib.common.utils.PropertiesUtil;

/**
 * 清理节点配置公共接口
 * 
 * @author yeyalin 2017-08-21
 */
@Controller
@RequestMapping(value = { "appConfig" })
public class AppConfigController {

	@Resource(name = "appConfigService")
	private AppConfigService appConfigService;

	/**
	 * 1、清理节点配置公共接口 {"node_name":"cloudbusinessservice_node"}
	 * 2、节点配置清理后，再加载最新配置信息
	 */
	@RequestMapping(value = { "clearAppConfig" })
	@ResponseBody
	public ResultEntity clearAppConfig(HttpServletRequest request, String req) {
		RequestURLListEntity requestURL = BeanFactoryHelper.getBean(
				"requestURL", RequestURLListEntity.class);
		String node_name = appConfigService.getAppName();
		String node_interface = AppConfigConstant.NODE_INFACCE_URL;
		if ("".equals(req) || req == null) {
			req = "{\"node_name\":\"" + node_name + "\"" + "}";
		}
		String developer_model = PropertiesUtil
				.getConfigPropValueAsText("developer_model");
		String baseUrl = "";
		if ("true".equals(developer_model)) {
			baseUrl = PropertiesUtil
					.getConfigPropValueAsText("cloudbusinessservice_node_url_developer");
		} else {
			baseUrl = PropertiesUtil
					.getConfigPropValueAsText("cloudbusinessservice_node_url");
		}
		ResultEntity resultEntity = appConfigService.clearAppConfig(request,
				requestURL, req, baseUrl + node_interface);
		// tomcat重新加载bean
		try {
			refreshBeans("requestURL", requestURL.getMap());
		} catch (BeanDefinitionStoreException e) {
			resultEntity.setMessage("fail");
			resultEntity.setState(false);
			e.printStackTrace();
		} catch (IOException e) {
			resultEntity.setMessage("fail");
			resultEntity.setState(false);
		}
		return resultEntity;
	}

	/**
	 * 刷新tomcat容器
	 * 
	 * @param beanName
	 * @param map
	 * @throws BeanDefinitionStoreException
	 * @throws IOException
	 */
	public void refreshBeans(String beanName, Map map)
			throws BeanDefinitionStoreException, IOException {
		XmlWebApplicationContext ac = (XmlWebApplicationContext) ApplicationContextHolder
				.getContext();
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(
				(BeanDefinitionRegistry) ac.getBeanFactory());
		beanDefinitionReader.setResourceLoader(ac);
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(ac));
		RequestURLListEntity requestURL = (RequestURLListEntity) ac
				.getBeanFactory().getBean(beanName);
		requestURL.setMap(map);

	}
}
