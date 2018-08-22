package com.ssitcloud.common.entity;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * 响应实体<br/>
 * 对应的JSON格式为<br/>
 *
 * req=<br/>
 * {<br/>
 * 	&nbsp"servicetype":"项目名",<br/>
 * 	&nbsp"target":"发送请求来源",<br/>
 * 	&nbsp"operation":"请求操作的函数（方法）"<br/>
 * 	&nbsp"data":<br/>
 *      &nbsp&nbsp  {<br/>
 * 		&nbsp&nbsp device:{....},
 * 		&nbsp&nbsp device_dbsync_template:{....},<br/>
 * 		&nbsp&nbsp device_monitor_template:{....},<br/>
 * 		&nbsp&nbsp device_run_template:{....},<br/>
 * 		&nbsp&nbsp device_ext_template:{....}<br/>
 *      &nbsp&nbsp   }<br/> 
 *  }<br/>
 * <br/>
 *
*
 * @package: com.ssitcloud.common.entity
 * @classFile: RespEntity
 * @author: liuBh
 * @param <T>
 * @description: TODO
 */
public class RequestEntity {
	//项目名
	private String servicetype;
	//发送请求来源
	private String target;
	//请求操作的函数（方法）
	private String operation;
	//传入 需要操作的函数的 参数
	private Map<String,Object> data;
	
	private String requestId;
	
	private String token;
	
	private String objectname;
	
	@JsonIgnore
	private String requestIp;
	@JsonIgnore
	private String requestPort;
	
	public RequestEntity(){}

	/**
	 * 将requestEntity作为参数的构造方法
	 * @param reqEntity
	 */
	public RequestEntity(RequestEntity requestEntity){
		this.servicetype = requestEntity.getServicetype();
		this.target = requestEntity.getTarget();
		this.operation = requestEntity.getOperation();
	}
	
	
	public static final String defaultServiceType="ssitcloud";
	public static final String defaultTarget="api";
	public static final String defaultOperation="api";

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
	
	public String getServicetype() {
		return servicetype;
	}
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}
	public Map<String,Object> getData() {
		return data;
	}
	public void setData(Map<String,Object> data) {
		this.data = data;
	}
	public String getRequestIp() {
		return requestIp;
	}
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	public String getRequestPort() {
		return requestPort;
	}
	public void setRequestPort(String requestPort) {
		this.requestPort = requestPort;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	public String getObjectname() {
		return objectname;
	}

	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}
}
