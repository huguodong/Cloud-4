package com.ssitcloud.dbauth.param;

import java.sql.Timestamp;
import java.util.List;

import com.ssitcloud.dbauth.entity.LibraryInfoEntity;
import com.ssitcloud.dbauth.entity.RelLibsEntity;

/**
 * 新增图书馆时传递的param
 * 
 * 	{
		"operator_idx":"1",
		"client_ip":"127.0.0.1",
		"client_port":"8080",
	    "lib_id":"M42",
	    "lib_name":"猎户座星云",
	    "lib_service_tpl_id":"2",
	    "rellibs":{
	    		"master_lib_id":"",
	    		"slave_lib_id":"",
	    		"rel_type":"主从关系",
	    }
	    "library_infos":[
	        {
	            "infotype_idx":"15",
	            "info_value":"000-65650650"
	        },
	        {
	            "infotype_idx":"16",
	            "info_value":"宇宙大街"
	        },
	        {
	            "infotype_idx":"17",
	            "info_value":"www.xxx.com"
	        },
	        {
	            "infotype_idx":"17",
	            "info_value":"www.xxx1.com"
	        }
	    ]
	}
			
 * <p>2016年4月22日 下午12:17:32
 * @author hjc
 */
public class LibraryParam {
	/** 操作员idx */
	private Integer operator_idx;
	
	/** 客户端ip */
	private String client_ip;
	
	/** 客户端端口 */
	private String client_port;
	
	/** 图书馆idx */
	private Integer library_idx;
	
	/** 图书馆id */
	private String lib_id;
	
	/** 图书馆名称 */
	private String lib_name;
	
	private Integer lib_type;
	
	/** 图书馆模板id */
	private Integer lib_service_tpl_id;
	
	/** 服务截止时间 */
	private String service_start_date;
	
	/** 服务截止时间 */
	private String service_expire_date;

	/** 图书馆信息列表 */
	private List<LibraryInfoEntity> library_infos;
	
	private Integer version_stamp;
	
	private RelLibsEntity rellib_info;

	private List<RelLibsEntity> relSubLibs;
	
	public String getService_start_date() {
		return service_start_date;
	}

	public void setService_start_date(String service_start_date) {
		this.service_start_date = service_start_date;
	}

	public String getService_expire_date() {
		return service_expire_date;
	}

	public void setService_expire_date(String service_expire_date) {
		this.service_expire_date = service_expire_date;
	}

	public RelLibsEntity getRellib_info() {
		return rellib_info;
	}

	public void setRellib_info(RelLibsEntity rellib_info) {
		this.rellib_info = rellib_info;
	}

	public Integer getOperator_idx() {
		return operator_idx;
	}

	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}

	public String getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	public String getClient_port() {
		return client_port;
	}

	public void setClient_port(String client_port) {
		this.client_port = client_port;
	}

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

	public List<LibraryInfoEntity> getLibrary_infos() {
		return library_infos;
	}

	public void setLibrary_infos(List<LibraryInfoEntity> library_infos) {
		this.library_infos = library_infos;
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

	public List<RelLibsEntity> getRelSubLibs() {
		return relSubLibs;
	}

	public void setRelSubLibs(List<RelLibsEntity> relSubLibs) {
		this.relSubLibs = relSubLibs;
	}
}
