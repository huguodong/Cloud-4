package com.ssitcloud.business.mobile.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.PropertiesUtil;
import com.ssitcloud.business.mobile.service.NodeServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class NodeServiceImpl implements NodeServiceI {
	private String URL_NODE="findAddressByNodeName";
	private String charset = "utf-8";
	
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURLEntity;
	
	private final String EMAIL_NODE_NAME;
	
	public NodeServiceImpl(){
		EMAIL_NODE_NAME=PropertiesUtil.getConfigPropValueAsText("email_node_name");
		if(EMAIL_NODE_NAME == null){
			throw new IllegalArgumentException("config must set email_node_name");
		}
	}
	
	private String getNodeUrl(String nodeName,Integer libraryIdx){
		if(libraryIdx == null){
			libraryIdx = Integer.valueOf(0);
		}
		String libraryStr = libraryIdx.toString();
		
		String requestUrl = requestURLEntity.getRequestURL(URL_NODE);
		String nodeJson = "{\"node_name\":\""+nodeName+"\"}";
		Map<String, String> map = new HashMap<>(2,1.0f);
		map.put("req", nodeJson);
		String doPost = HttpClientUtil.doPost(requestUrl,map,charset);
		try{
			ResultEntity resultEntity = JsonUtils.fromJson(doPost, ResultEntity.class);
			if(resultEntity.getState()){
				Map<String, Object> resultMap = (Map<String, Object>) resultEntity.getResult();
				String nodeUrl = (String) resultMap.get("address");
				String libsStr = (String) resultMap.get("libs");
				String[] libs = libsStr.split(",");
				for (String lib : libs) {
					if(lib.equals(libraryStr)){
						LogUtils.debug("查询到节点==>"+nodeUrl);
						return nodeUrl;
					}
				}
				LogUtils.error("查询不到节点==>"+nodeName+",目标lib_idx==>"+libraryStr);
			}else{
				LogUtils.error("查询不到节点==>"+nodeName+",resultEntity state==>false");
			}
		}catch (Exception e) {
			LogUtils.error("解析节点数据异常,查询节点==>"+nodeName+",节点服务器返回数据==>"+doPost,e);
		}
		return null;
	}
	
	
	@Override
	public String getEmailUrl(Integer libraryIdx) {
		String nodeUrl = getNodeUrl(EMAIL_NODE_NAME,libraryIdx);
		if(nodeUrl != null){
			return nodeUrl+"/email/sendEmail";
		}else{
			return null;
		}
	}

}
