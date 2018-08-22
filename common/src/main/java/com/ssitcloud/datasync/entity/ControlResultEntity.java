package com.ssitcloud.datasync.entity;

import java.io.Serializable;

/**
 * 设备端返回的远程控制结果
 *  device_id		String	设备ID
	Library_id		String	馆ID
	Controlresult	String	操作结果 1开机、关机、重启操作成功 2开机、关机、重启操作失败
	errInfo			String 	失败原因

 * @package: com.ssitcloud.devmgmt.entity
 * @classFile: ControlResultEntity
 * @author: liuBh
 * @description: TODO
 */
public class ControlResultEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String device_id;
	private String library_id;
	private String controlresult;
	private String errInfo;
	
	public ControlResultEntity() {
	}
	public ControlResultEntity(String device_id, String library_id,
			String controlresult, String errInfo) {
		super();
		this.device_id = device_id;
		this.library_id = library_id;
		this.controlresult = controlresult;
		this.errInfo = errInfo;
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
	public String getControlresult() {
		return controlresult;
	}
	public void setControlresult(String controlresult) {
		this.controlresult = controlresult;
	}
	public String getErrInfo() {
		return errInfo;
	}
	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}
	
}
