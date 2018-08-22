package com.ssitcloud.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * mybatis动态查询参数类
 * <p>2016年4月7日 上午9:58:21
 * @author hjc
 *
 */
public class SqlParam {
	/** 查询参数 */
	private Map<String, Object> selParam;
	
	/** 条件参数*/
	private Map<String, Object> whereParam;
	
	/** 更新的字段集*/
	private Map<String, Object> updParam;

	
	public SqlParam() {
		selParam = new HashMap<String,Object>();
		whereParam = new HashMap<String,Object>();
		updParam = new HashMap<String,Object>();
	}
	
	public Map<String, Object> getSelParam() {
		return selParam;
	}

	public void setSelParam(Map<String, Object> selParam) {
		this.selParam = selParam;
	}

	public Map<String, Object> getWhereParam() {
		return whereParam;
	}

	public void setWhereParam(Map<String, Object> whereParam) {
		this.whereParam = whereParam;
	}

	public Map<String, Object> getUpdParam() {
		return updParam;
	}

	public void setUpdParam(Map<String, Object> updParam) {
		this.updParam = updParam;
	}	
	
	

}
