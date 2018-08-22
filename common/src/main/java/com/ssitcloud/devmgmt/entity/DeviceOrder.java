package com.ssitcloud.devmgmt.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 设备操作命令 ,来源 device view层
 * @package: com.ssitcloud.devmgmt.entity
 * @classFile: DeviceOrder
 * @author: liuBh
 * @description: TODO
 */
public class DeviceOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//设备ID
	//对应接口的device_id 
	private String device_id;
	//图书馆IDX
	private Integer library_idx;
	
	//1 表示有指令 0表示没有指令
	private String control;
	
	//1关机 2 重启 3获取日志4远程维护锁屏 5取消操作
	private String control_info;
	
	//命令进来的时间
	private Date receiveTime;
	
	/**
	 * 1.获取日志 用到的时间
	 * start_time 查询开始时间
	 * end_time 查询结束时间
	 * operator_id 用户ID
	 */
	private String start_time;
	
	private String end_time;
	
	private String operator_id;
	
	
	
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
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
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((control == null) ? 0 : control.hashCode());
		result = prime * result
				+ ((control_info == null) ? 0 : control_info.hashCode());
		result = prime * result
				+ ((device_id == null) ? 0 : device_id.hashCode());
		result = prime * result
				+ ((end_time == null) ? 0 : end_time.hashCode());
		result = prime * result
				+ ((operator_id == null) ? 0 : operator_id.hashCode());
		result = prime * result
				+ ((start_time == null) ? 0 : start_time.hashCode());
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
		DeviceOrder other = (DeviceOrder) obj;
		if (control == null) {
			if (other.control != null)
				return false;
		} else if (!control.equals(other.control))
			return false;
		if (control_info == null) {
			if (other.control_info != null)
				return false;
		} else if (!control_info.equals(other.control_info))
			return false;
		if (device_id == null) {
			if (other.device_id != null)
				return false;
		} else if (!device_id.equals(other.device_id))
			return false;
		if (end_time == null) {
			if (other.end_time != null)
				return false;
		} else if (!end_time.equals(other.end_time))
			return false;
		if (operator_id == null) {
			if (other.operator_id != null)
				return false;
		} else if (!operator_id.equals(other.operator_id))
			return false;
		if (start_time == null) {
			if (other.start_time != null)
				return false;
		} else if (!start_time.equals(other.start_time))
			return false;
		return true;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	/**
	 * 1关机 2 重启 3获取日志4远程维护锁屏 5取消操作
	 * 获取中文 指令
	 * @return
	 */
	public String getControlInfoDesc(){
		String retStr="";
		if("1".equals(control_info)){
			retStr="关机";
		}else if("2".equals(control_info)){
			retStr="重启";
		}else if("3".equals(control_info)){
			retStr="获取日志";
		}else if("4".equals(control_info)){
			retStr="远程维护锁屏";
		}else if("5".equals(control_info)){
			retStr="取消操作";
		}
		return retStr;
	}
	
}
