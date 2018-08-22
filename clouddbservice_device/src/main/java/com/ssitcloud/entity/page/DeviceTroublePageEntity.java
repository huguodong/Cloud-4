package com.ssitcloud.entity.page;

import java.sql.Timestamp;
import java.util.List;

import com.ssitcloud.common.entity.DatagridPageEntity;


/**
 * 
 * @package com.ssitcloud.devmgmt.entity
 * @comment	设备故障分页 馆员app
 * @author shuangjunjie
 */
public class DeviceTroublePageEntity extends DatagridPageEntity<DeviceTroublePageEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer trouble_idx;
	private Integer lib_idx;
	private Integer device_idx;
	private String trouble_info;
	private String trouble_name;
	private Timestamp create_time;
	private Timestamp control_time;
	
	//查询参数	父馆和子馆对应的idx
	private List<Integer> libIdxs;
	
	public Integer getTrouble_idx() {
		return trouble_idx;
	}
	public void setTrouble_idx(Integer trouble_idx) {
		this.trouble_idx = trouble_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public Integer getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}
	public String getTrouble_info() {
		return trouble_info;
	}
	public void setTrouble_info(String trouble_info) {
		this.trouble_info = trouble_info;
	}
	public String getTrouble_name() {
		return trouble_name;
	}
	public void setTrouble_name(String trouble_name) {
		this.trouble_name = trouble_name;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getControl_time() {
		return control_time;
	}
	public void setControl_time(Timestamp control_time) {
		this.control_time = control_time;
	}
	public List<Integer> getLibIdxs() {
		return libIdxs;
	}
	public void setLibIdxs(List<Integer> libIdxs) {
		this.libIdxs = libIdxs;
	}
	
	
	
}
