package com.ssitcloud.dbauth.entity.page;

import java.sql.Timestamp;
import java.util.List;

import com.ssitcloud.common.entity.DatagridPageEntity;
import com.ssitcloud.dbauth.entity.LibraryInfoEntity;

public class LibraryPageEntity extends DatagridPageEntity {

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
