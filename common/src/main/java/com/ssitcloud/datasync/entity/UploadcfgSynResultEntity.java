package com.ssitcloud.datasync.entity;

/**
 * 终端配置数据上传: uploadcfgSyn 响应返回<br/>
 * 字段名称			类型		说明<br/>
	device_id		String	设备ID<br/>
	Library_id		String	馆ID<br/>
	responseResult	String	操作结果 1成功 0失败<br/>
	transInfo		String 	数据传输消息<br/>

 * @package: com.ssitcloud.datasync.entity
 * @classFile: UploadcfgSynResultEntity
 * @author: liuBh
 * @description: TODO
 */
public class UploadcfgSynResultEntity {

	private String device_id;
	private String library_id;
	private String responseResult;
	private String transInfo;
	
	public static final String ResponseResult_fail="0";
	public static final String ResponseResult_success="1";
	
	public UploadcfgSynResultEntity() {
	}
	public UploadcfgSynResultEntity(String device_id, String library_id,
			String responseResult, String transInfo) {
		this.device_id = device_id;
		this.library_id = library_id;
		this.responseResult = responseResult;
		this.transInfo = transInfo;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getLibrary_id() {
		return library_id;
	}
	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}
	public String getResponseResult() {
		return responseResult;
	}
	public void setResponseResult(String responseResult) {
		this.responseResult = responseResult;
	}
	public String getTransInfo() {
		return transInfo;
	}
	public void setTransInfo(String transInfo) {
		this.transInfo = transInfo;
	}

	
}