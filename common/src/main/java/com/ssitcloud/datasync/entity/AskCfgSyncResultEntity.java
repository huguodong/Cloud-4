package com.ssitcloud.datasync.entity;

/**
 * 字段名称	类型	说明
	device_id		String	设备ID
	Library_id		String	馆ID
	requestResult	String	操作结果 1允许 0禁止
	disableInfo		String 	禁止原因
	Tables			String	云端发生变化的表名
	Table			String	表名1
	Table			String	表名2

 * @package: com.ssitcloud.datasync.entity
 * @classFile: AskCfgSyncResultEntity
 * @author: liuBh
 * @description: TODO
 */
public class AskCfgSyncResultEntity {
	private String device_id;
	private String library_id;
	private String requestResult;
	private String disableInfo;
	private String tables;
	
	public static final String RequestResult_allow="1";
	public static final String RequestResult_disable="0";
	
	public AskCfgSyncResultEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AskCfgSyncResultEntity(String device_id, String library_id,
			String requestResult, String disableInfo, String tables) {
		super();
		this.device_id = device_id;
		this.library_id = library_id;
		this.requestResult = requestResult;
		this.disableInfo = disableInfo;
		this.tables = tables;
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
	public String getRequestResult() {
		return requestResult;
	}
	public void setRequestResult(String requestResult) {
		this.requestResult = requestResult;
	}
	public String getDisableInfo() {
		return disableInfo;
	}
	public void setDisableInfo(String disableInfo) {
		this.disableInfo = disableInfo;
	}
	public String getTables() {
		return tables;
	}
	public void setTables(String tables) {
		this.tables = tables;
	}

	
}	
