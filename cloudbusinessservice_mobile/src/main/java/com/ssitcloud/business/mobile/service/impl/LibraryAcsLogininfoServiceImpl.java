package com.ssitcloud.business.mobile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.LibraryAcsLogininfoServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年3月2日 下午3:55:16
 */
@Service
public class LibraryAcsLogininfoServiceImpl implements LibraryAcsLogininfoServiceI {
	private final String URL_SELECTS = "selectLibraryAcsLogininfos";

	@Resource(name = "requestURL")
	private RequestURLListEntity requestURLEntity;
	private static final String charset = "utf-8";

	@Override
	public Map<String, Object> selectLibraryAcsLogininfo(Integer lib_idx) {
		Map<String, String> map = new HashMap<>(1);
		map.put("json", "{\"lib_idx\":" + lib_idx + "}");

		String url = requestURLEntity.getRequestURL(URL_SELECTS);
		String remoteJson = HttpClientUtil.doPost(url, map, charset);
		if (remoteJson == null) {
			LogUtils.error("请求" + url + "失败");
			return null;
		}
		try {
			Map<String, Object> remoteMap = JsonUtils.fromJson(remoteJson, Map.class);
			if((remoteMap.get("state")) != null && ((Boolean)remoteMap.get("state"))){
				List result = (List) remoteMap.get("result");
				if(result.isEmpty()){
					LogUtils.error("查询图书馆ACS数据时没有查询到数据，请查看数据表library_acs_logininfo，lib_idx=>"+lib_idx);
					return null;
				}else{
					return (Map<String, Object>) result.get(0);
				}
			}else{//请求失败
				LogUtils.debug("请求数据图书馆acs信息失败 json"+map+" return=>"+remoteMap);
				return null;
			}
		} catch (Exception e) {
			LogUtils.error("请求" + url + "没有返回预期json json=>" + map + " return=>" + remoteJson);
			return null;
		}

	}

}
