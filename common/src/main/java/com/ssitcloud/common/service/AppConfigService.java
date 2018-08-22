package com.ssitcloud.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ssitcloud.common.entity.InterfaceUrlConfigEntity;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 清理节点配置公共接口
 * 
 * @author yeyalin 2017-08-21
 */
public interface AppConfigService {
	/**
	 * 应用节点启动加载接口配置信息
	 * 
	 * @author yeyalin 2017-08-21
	 * @param nodeName
	 * @param postUrl
	 * @return
	 */
	public Map<String, String> autoReloadAppConfig(String nodeName,String postUrl);
	/**
	 * 清理应用配置的url并加载最新节点接口配置
	 * 
	 * @author yeyalin 2017-08-21
	 * @param request
	 * @param requestURL
	 * @param req
	 * @param postUrl
	 * @return
	 */
	public ResultEntity clearAppConfig(HttpServletRequest request,
			RequestURLListEntity requestURL, String req,String postUrl);

	/**
	 * 解析节点配置
	 * 
	 * @param requestURL
	 * @author yeyalin 2017-08-21
	 * @param json
	 * @return List
	 */
	public List<InterfaceUrlConfigEntity> parseAppConfigJson(String data);

	/**
	 * 重新加载节点配置
	 * 
	 * @param requestURL
	 * @author yeyalin 2017-08-21
	 * @param interfaceUrlConfigList
	 * @return List
	 */
	public Map<String, String> reLoadAppConfig(List<InterfaceUrlConfigEntity> interfaceUrlConfigList);

	/**
	 * 获取接口配置
	 * 
	 * @param req
	 * @author yeyalin 2017-08-21
	 * @return
	 */
	public String getAppConfig(String postUrl, String req);
	/**
	 * 获取项目名称
	 * 
	 * @author yeyalin 2017-08-21
	 * @return
	 */
	public String  getAppName();
}
