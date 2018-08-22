package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.devmgmt.entity.DeviceDisplayConfig;
import com.ssitcloud.business.devmgmt.service.DeviceDisplayConfigService;
import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;

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
	public ResultEntity queryAll(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (StringUtils.hasLength(result)&&JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return null;
	}

	@Override
	public ResultEntity delete(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (StringUtils.hasLength(result)&&JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return null;
	}

	@Override
	public ResultEntity findById(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		ResultEntity resultEntity = new ResultEntity();
		if (StringUtils.hasLength(result)&&JSONUtils.mayBeJSON(result)) {
			JSONObject json = JSONObject.fromObject(result);
			DeviceDisplayConfig entity = (DeviceDisplayConfig) JSONObject.toBean(json, DeviceDisplayConfig.class);
			resultEntity.setState(true);
			resultEntity.setResult(entity);
		}else{
			resultEntity.setState(false);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity insert(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (StringUtils.hasLength(result)&&JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return null;
	}

	@Override
	public ResultEntity update(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (StringUtils.hasLength(result)&&JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return null;
	}

	@Override
	public ResultEntity findByTypeId(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		ResultEntity resultEntity = new ResultEntity();
		if (StringUtils.hasLength(result)&&JSONUtils.mayBeJSON(result)) {
			JSONObject json = JSONObject.fromObject(result);
			DeviceDisplayConfig entity = (DeviceDisplayConfig) JSONObject.toBean(json, DeviceDisplayConfig.class);
			
			entity = JsonUtils.fromJson(result, DeviceDisplayConfig.class);
			resultEntity.setState(true);
			resultEntity.setMessage(Const.SUCCESS);
			resultEntity.setResult(entity);
		}else{
			resultEntity.setMessage(Const.FAILED);
			resultEntity.setState(false);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity getDeviceTypes(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		if (StringUtils.hasLength(result)&&JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return null;
	}

}
