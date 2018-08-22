package com.ssitcloud.view.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.devmgmt.entity.DeviceDisplayConfig;
import com.ssitcloud.devmgmt.entity.DeviceDisplayConfigPage;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.devmgmt.service.DeviceDisplayConfigService;

/**
 * 
 * @package com.ssitcloud.devmgmt.service.impl
 * @comment
 * @data 2016年4月18日
 */
@Service
public class DeviceDisplayConfigServiceImpl extends BasicServiceImpl implements DeviceDisplayConfigService {
	private String prefix = "display_";

	@Override
	public DeviceDisplayConfigPage queryAll(String reqInfo) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", reqInfo);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (result != null) {
			ResultEntityF<DeviceDisplayConfigPage> rs = JsonUtils.fromJson(result, new TypeReference<ResultEntityF<DeviceDisplayConfigPage>>() {
			});
			if (rs != null) {
				return rs.getResult();
			}
		}
		return null;
	}

	@Override
	public String delete(String reqInfo) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", reqInfo);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public DeviceDisplayConfig findById(String reqInfo) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", reqInfo);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (result != null) {
			ResultEntityF<DeviceDisplayConfig> rs = JsonUtils.fromJson(result, new TypeReference<ResultEntityF<DeviceDisplayConfig>>() {
			});
			if (rs != null) {
				return rs.getResult();
			}
		}
		return null;
	}

	@Override
	public String insert(String reqInfo) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", reqInfo);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String update(String reqInfo) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", reqInfo);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String getDeviceTypes(String json) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String getAllDeviceTypes(String json) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

}
