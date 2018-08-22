package com.ssitcloud.request.entity;

import java.sql.Timestamp;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 接口请求数据表
 * @author soft4
 *
 */
@SuppressWarnings("rawtypes")
public class InterfaceRequestEntity {
	
	private static final long serialVersionUID = 1999572588447320650L;
	
	private Integer id;
	/**
	 * 请求ID:UUID+时间戳
	 */
	private String requestId;
	
	/**
	 * 请求主机名
	 */
	private String requestHostname;
	
	/**
	 * 请求ip
	 */
	private String requestIp;
	
	/**
	 * 请求体
	 */
	private String requestBody;
	/**
	 * 请求url
	 */
	private String requestUrl;
	
	/**
	 * 请求端口
	 */
	private Integer requestPort;
	
	/**
	 * 请求类型
	 */
	private String requestType;
	
	/**
	 * 请求时间
	 */
	private Timestamp requestTime;
	/**
	 * 本机IP
	 */
	private String localIp;
	
	/**
	 * 云端返回的信息
	 */
	private String responseBody;
	
	/**
	 * 云端处理状态:0-未处理：默认状态，1-异常状态,2--正常状态
	 */
	private Integer responseStatus;//节点类型
	/**
	 * 返回相应时间
	 */
	private Timestamp responseTime;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	
	/**
	 * 修改时间
	 */
	private Timestamp updateTime;
	
	/**
	 * 备注
	 */
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestHostname() {
		return requestHostname;
	}

	public void setRequestHostname(String requestHostname) {
		this.requestHostname = requestHostname;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Integer getRequestPort() {
		return requestPort;
	}

	public void setRequestPort(Integer requestPort) {
		this.requestPort = requestPort;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}


	public Integer getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(Integer responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Timestamp getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((requestId == null) ? 0 : requestId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterfaceRequestEntity other = (InterfaceRequestEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (requestId == null) {
			if (other.requestId != null)
				return false;
		} else if (!requestId.equals(other.requestId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InterfaceRequestEntity [id=" + id + ", requestId=" + requestId
				+ ", requestHostname=" + requestHostname + ", requestIp="
				+ requestIp + ", requestBody=" + requestBody + ", requestUrl="
				+ requestUrl + ", requestPort=" + requestPort
				+ ", requestType=" + requestType + ", requestTime="
				+ requestTime + ", localIp=" + localIp + ", responseBody="
				+ responseBody + ", responseStatus=" + responseStatus
				+ ", responseTime=" + responseTime + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
}
