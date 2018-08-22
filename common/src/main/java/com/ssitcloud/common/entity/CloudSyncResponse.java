package com.ssitcloud.common.entity;

import java.util.List;


/**
 * RPC Response
 * 
 * @author yeyalin
 */
public class CloudSyncResponse<T> {
	private String requestId;
	private String msg_name;
	private String msg_type;
	private String status;
	private Object result;
	private List<T> data;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getMsg_name() {
		return msg_name;
	}

	public void setMsg_name(String msg_name) {
		this.msg_name = msg_name;
	}

	public String getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}


	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		
		String json = "{\"requestId\":" +"\""+ requestId+ "\""+ ",\"msg_name\":" +"\""+ msg_name+"\""
				+ ",\"msg_type\":" +"\""+msg_type+"\""+ ",\"status\":" +"\""+status+"\""
				+ ",\"result\":" +"\""+ result+"\"" + ",\"data\":" + data+ "}";
		return json;
	}

}
