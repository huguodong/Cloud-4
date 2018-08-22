package com.ssitcloud.common.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.Consts;

import com.ssitcloud.common.entity.InterfaceUrlConfigEntity;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.AppConfigService;
import com.ssitcloud.common.utils.HttpClientUtil;
import com.ssitcloud.common.utils.LogUtils;

/**
 * 清理节点配置公共接口
 * 
 * @author yeyalin 2017-08-21
 */
public class AppConfigServiceImpl implements AppConfigService {

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
	@Override
	public ResultEntity clearAppConfig(HttpServletRequest request,
			RequestURLListEntity requestURL, String req, String postUrl) {
		String message = "清缓存失败";
		// 1、清理节点配置缓存信息
		boolean flag = clearAppConfigMap(requestURL);
		// 2、清理节点配置缓存成功后 获取节点最新配置
		String result = "";
		result = getAppConfig(postUrl, req);
		if (flag) {
			message = "清缓存成功";
		}
		// 3、 解析节点最新配置
		List<InterfaceUrlConfigEntity> interfaceUrlConfigList = parseAppConfigJson(result);
		// 4、加载节点最新接口配置
		Map<String, String> reqUrls = reLoadAppConfig(interfaceUrlConfigList);
		if (reqUrls != null && reqUrls.size() > 0) {
			requestURL.setMap(reqUrls);
		}
		return new ResultEntity(flag, message);
	}

	/**
	 * 清理AppConfig中的map
	 * 
	 * @param requestURL
	 * @author yeyalin 2017-08-21
	 */
	public boolean clearAppConfigMap(RequestURLListEntity requestURL) {
		boolean flag = false;
		if (requestURL != null) {
			Map<String, String> appMap = requestURL.getMap();
			if (appMap != null && appMap.size() > 0) {
				appMap.clear();
			}
			flag = true;
			LogUtils.info("-----------------清理url配置成功， requestURL缓存数据是: " + appMap);
		}
		return flag;
	}

	/**
	 * 重新加载节点配置
	 * 
	 * @param requestURL
	 * @author yeyalin 2017-08-21
	 * @param json
	 * @return List
	 */
	@Override
	public Map<String, String> reLoadAppConfig(
			List<InterfaceUrlConfigEntity> interfaceUrlConfigList) {
		Map<String, String> reqUrls = new HashMap<>();
		LogUtils.info("=========================装载URL总数：" +interfaceUrlConfigList.size());
		if (interfaceUrlConfigList != null && interfaceUrlConfigList.size() > 0) {
			for (InterfaceUrlConfigEntity vo : interfaceUrlConfigList) {
				// 例如：http://127.0.0.1:8080/clouddbservice_device/devconf/AddNewDeviceConfig
				String url =vo.getProtocol()+ "://" + vo.getIp() + ":" + vo.getPort() + "/"
						+ vo.getTo_node() + vo.getRequest_url();
				reqUrls.put(vo.getRequest_id(), url);
				LogUtils.info("装载URL：" + vo.getRequest_id() + "-->" + url);
			}
		}
		return reqUrls;
	}

	/**
	 * 解析节点配置
	 * 
	 * @param requestURL
	 * @author yeyalin 2017-08-21
	 * @param json
	 * @return List
	 */
	@Override
	public List<InterfaceUrlConfigEntity> parseAppConfigJson(String data) {

		List<InterfaceUrlConfigEntity> interfaceUrlConfigList = new ArrayList<InterfaceUrlConfigEntity>();
		if (!"".equals(data) && data != null) {
			JSONObject jsonObj = JSONObject.fromObject(data);
			// 状态：1-成功，0-失败
			String status = jsonObj.getString("status");
			// 失败原因
			String msg = jsonObj.getString("msg");
			if ("0".equals(status)) {
				LogUtils.info("解析节点配置失败，原因如下： " + msg);
			} else {
				if (jsonObj.get("result") != null) {

					JSONArray resultArr = JSONArray.fromObject(jsonObj
							.get("result"));
					for (int i = 0; i < resultArr.size(); i++) {
						InterfaceUrlConfigEntity interfaceUrlConfigEntity = (InterfaceUrlConfigEntity) JSONObject
								.toBean((JSONObject) resultArr.get(i),
										InterfaceUrlConfigEntity.class);
						interfaceUrlConfigList.add(interfaceUrlConfigEntity);
					}
				} else {
					LogUtils.info("解析节点配置，返回结果为空");
				}
			}
		}
		return interfaceUrlConfigList;

	}

	/**
	 * 获取接口配置
	 * 
	 * @param req
	 * @author yeyalin 2017-08-21
	 * @return
	 */
	@Override
	public String getAppConfig(String postUrl, String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		LogUtils.info("请求地址,postUrl: " + postUrl);
		LogUtils.info("请求参数,req: " + req);
		String result = HttpClientUtil.doPost(postUrl, map,
				Consts.UTF_8.toString());
		LogUtils.info("返回参数,result: " + result);
		return result;
	}

	/**
	 * 应用节点启动加载接口配置信息
	 * 
	 * @author yeyalin 2017-08-21
	 * @param nodeName
	 * @param postUrl
	 * @return
	 */
	@Override
	public Map<String, String> autoReloadAppConfig(String nodeName,
			String postUrl) {
		Map<String, String> reqUrls = new HashMap<String, String>();
		String req = "{\"node_name\":\"" + nodeName + "\"" + "}";
		// 1、 获取节点最新配置
		String result = getAppConfig(postUrl, req);
		// 2、 解析节点最新配置
		List<InterfaceUrlConfigEntity> interfaceUrlConfigList = parseAppConfigJson(result);
		// 3、加载节点最新接口配置
		reqUrls = reLoadAppConfig(interfaceUrlConfigList);
		return reqUrls;

	}

	/**
	 * 获取项目名称
	 * 
	 * @author yeyalin 2017-08-21
	 * @return
	 */
	@Override
	public String getAppName() {
		String appName = "";
		try {
			String path = java.net.URLDecoder.decode(AppConfigServiceImpl.class
					.getProtectionDomain().getCodeSource().getLocation()
					.getFile().replaceAll("/", "\\\\"), "UTF-8");
			;
			int start = path.indexOf("webapps") + "webapps".length() + 1;
			int end = path.indexOf("WEB-INF") - 1;
			appName = path.substring(start, end);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		LogUtils.info(" 当前节点名称是：" + appName);
		System.out.println(" 当前节点名称是：" + appName);
		return appName;
	}

	public static void main(String[] str) {
		String json = "{'status':1,'from_node':'cloudviewservice_device','result':'[{'to_node':'cloudbusinessservice_device','ip':'172.16.0.118','port':'8080','request_id':'selectEmailParam','request_url':'/cloudbusinessservice_device/emailParam/updateEmailParam'},{'to_node':'cloudbusinessservice_device','ip':'172.16.0.118','port':'8080','request_id':'UpdDeviceConfig','request_url':'/cloudbusinessservice_device/devconf/UpdDeviceConfig'}]'}";
		AppConfigServiceImpl AppConfigServiceImpl = new AppConfigServiceImpl();
		AppConfigServiceImpl.parseAppConfigJson(json);
	}
}
