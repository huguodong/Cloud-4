package com.ssitcloud.view.node.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.node.service.SwitchViewService;

@Service
public class SwitchViewServiceImpl extends BasicServiceImpl implements SwitchViewService {
	private String suffix = "switch_";

	/**
	 * req={"type":"manual","node_idx":"1"}
	 */
	@Override
	public ResultEntity switcher(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(suffix + method), map, charset);
		if (result != null&&JSONUtils.mayBeJSON(result)) {
			ResultEntity rst = JsonUtils.fromJson(result, ResultEntity.class);
			return rst;
		}
		return null;
	}
}