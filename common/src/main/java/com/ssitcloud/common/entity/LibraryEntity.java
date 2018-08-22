package com.ssitcloud.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 图书馆表
 * <p>2016年4月5日 上午10:21:11
 * @author hjc
 *
 */
public class LibraryEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/** 自增ID */
	private Integer library_idx;
	/** 图书馆id*/
	private String lib_id;
	/** 图书馆名称*/
	private String lib_name;
	private Integer lib_type;
	/** 图书馆模板id*/
	private Integer lib_service_tpl_id;
	/** 服务开始时间 */
	private Timestamp service_start_date;
	/** 服务结束时间 */
	private Timestamp service_expire_date;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LibraryEntity){
			LibraryEntity d=(LibraryEntity)obj;
			boolean eq=StringUtils.equals(d.getLib_id(), this.getLib_id());
			if(!eq) return eq;
			eq=StringUtils.equals(d.getLib_name(), this.getLib_name());
			if(!eq) return eq;
			eq=(d.getLib_type()==this.getLib_type());
			if(!eq) return eq;
			if(d.getService_expire_date()!=null){
				if(d.getService_expire_date().compareTo(this.getService_expire_date())==0){
					eq=true;
				}else{
					eq=false;
				}
			}else if(this.getService_expire_date()!=null){
				eq=false;
			}else{
				eq=true;
			}
			if(!eq) return eq;
			eq=d.getLib_service_tpl_id()==this.getLib_service_tpl_id()?true:false;
			if(!eq) return eq;
			if(d.getCreatetime()!=null){
				if(d.getCreatetime().compareTo(this.getCreatetime())==0){
					eq=true;
				}else{
					eq=false;
				}
			}else if(this.getCreatetime()!=null){
				eq=false;
			}else{
				eq=true;
			}
			if(!eq) return eq;
			
			if(d.getService_start_date()!=null){
				if(d.getService_start_date().compareTo(this.getService_start_date())==0){
					eq=true;
				}else{
					eq=false;
				}
			}else if(this.getService_start_date()!=null){
				eq=false;
			}else{
				eq=true;
			}
			return eq;
		}
		return false;
	}

	
	
	
}
