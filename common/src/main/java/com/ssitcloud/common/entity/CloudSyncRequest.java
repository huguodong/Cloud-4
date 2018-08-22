package com.ssitcloud.common.entity;

import java.util.List;

import org.springframework.util.StringUtils;

import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

/**
 * 设备终端请求Request
 * 
 * @author yeyalin
 */
public class CloudSyncRequest<T> {

	private String clientId;
	private String requestId;
	private String servicetype;
	private String target;
	private String operation;
	private List<T> data;
	private String token;
	private String lib_id;
	private String device_id;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getCacheClient(String lib_id,String device_id){
		if(StringUtils.isEmpty(clientId)){
			return JedisUtils.getInstance().get(RedisConstant.CLIENTID+lib_id+":"+device_id);
		}
		return "";
	}
	
	
	public String getLib_id() {
		return lib_id;
	}

	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	@Override
	public String toString() {
		return "{\"clientId\":" + clientId + ",\"requestId\":" + requestId
				+ ",\"servicetype\":" + servicetype + ",\"target\":" + target
				+ ",\"operation\":" + operation + ",\"data\":" + data
				+ ",\"token\":" + token + "}";
	}
}