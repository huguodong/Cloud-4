package com.ssitcloud.datasync.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 设备端访问要做什么操作的指令askControl返回对象<p/>
 *  device_id		String	设备ID<br/>
	Library_id		String	馆ID<br/>
	Control_info	String	控制操作信息 1关机 2 重启 3获取日志4远程维护锁屏 5取消操作<br/>
	Start_time		String	获取日志时，客户查询的开始时间<br/>
	End_time		String 	获取日志时，客户查询的结束时间<br/>

 * @package: com.ssitcloud.devmgmt.entity
 * @classFile: AskControlEntity
 * @author: liuBh
 * @description: TODO
 */
@JsonInclude(value=Include.NON_NULL)
public class AskControlResultEntity {
	private String device_id;
	private String library_id;
	private String control_info;
	private String start_time;
	private String end_time;
	private String operator_id;
	
	public AskControlResultEntity(){
		
	}
	
	public AskControlResultEntity(String device_id, String library_id,
			String control_info, String start_time, String end_time,String operator_id) {
		super();
		this.device_id = device_id;
		this.library_id = library_id;
		this.control_info = control_info;
		this.start_time = start_time;
		this.end_time = end_time;
		this.operator_id=operator_id;
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
	public String getControl_info() {
		return control_info;
	}
	public void setControl_info(String control_info) {
		this.control_info = control_info;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	
}
