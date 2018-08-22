package com.ssitcloud.dbauth.entity.page;

import java.sql.Timestamp;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class LibraryPageTempInfoEntity extends DatagridPageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 自增ID */
	private Integer library_idx;
	/** 图书馆id*/
	private String lib_id;
	/** 图书馆名称*/
	private String lib_name;
	/** 图书馆模板id*/
	private Integer lib_service_tpl_id;
	/** 服务开始时间 */
	private Timestamp service_start_date;
	/** 服务结束时间 */
	private Timestamp service_expire_date;
	private Integer lib_type;
	/** 创建时间*/
	private Timestamp createtime;
	
	/** 服务周期，单位：月 */
	private Integer service_cycle;
	/** 最大设备数 */
	private Integer max_device_count;
	/** 最大用户数 */
	private Integer max_operator_count;
	/** 图书馆规模数 */
	private Integer max_sublib_count;
	
	/** 总馆ID，为library的自增id */
	private Integer master_lib_id;
	/** 分馆ID，为library的自增id */
	private Integer slave_lib_id;
	
	private Integer version_stamp;

	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public String getLib_name() {
		return lib_name;
	}
	public void setLib_name(String lib_name) {
		this.lib_name = lib_name;
	}
	public Integer getLib_service_tpl_id() {
		return lib_service_tpl_id;
	}
	public void setLib_service_tpl_id(Integer lib_service_tpl_id) {
		this.lib_service_tpl_id = lib_service_tpl_id;
	}
	public Timestamp getService_start_date() {
		return service_start_date;
	}
	public void setService_start_date(Timestamp service_start_date) {
		this.service_start_date = service_start_date;
	}
	public Timestamp getService_expire_date() {
		return service_expire_date;
	}
	public void setService_expire_date(Timestamp service_expire_date) {
		this.service_expire_date = service_expire_date;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Integer getService_cycle() {
		return service_cycle;
	}
	public void setService_cycle(Integer service_cycle) {
		this.service_cycle = service_cycle;
	}
	public Integer getMax_device_count() {
		return max_device_count;
	}
	public void setMax_device_count(Integer max_device_count) {
		this.max_device_count = max_device_count;
	}
	public Integer getMax_operator_count() {
		return max_operator_count;
	}
	public void setMax_operator_count(Integer max_operator_count) {
		this.max_operator_count = max_operator_count;
	}
	public Integer getMax_sublib_count() {
		return max_sublib_count;
	}
	public void setMax_sublib_count(Integer max_sublib_count) {
		this.max_sublib_count = max_sublib_count;
	}
	public Integer getMaster_lib_id() {
		return master_lib_id;
	}
	public void setMaster_lib_id(Integer master_lib_id) {
		this.master_lib_id = master_lib_id;
	}
	public Integer getSlave_lib_id() {
		return slave_lib_id;
	}
	public void setSlave_lib_id(Integer slave_lib_id) {
		this.slave_lib_id = slave_lib_id;
	}
	public Integer getVersion_stamp() {
		return version_stamp;
	}
	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
	public Integer getLib_type() {
		return lib_type;
	}
	public void setLib_type(Integer lib_type) {
		this.lib_type = lib_type;
	}
}
