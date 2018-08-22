package com.ssitcloud.entity.page;

import com.ssitcloud.entity.AppSettingEntity;

/**
 * AppSettingEntity的查询类
 * @author LXP
 * @version 创建时间：2017年2月22日 下午4:59:49
 */
public class AppSettingPageEntity extends AppSettingEntity {
	private Integer pageCurrent;//第几页，从1开始
	private Integer pageSize;//每页多少条
	
	public Integer getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getLimitOffset() {
		return pageSize;
	}
	
	
	public Integer getLimitIndex() {
		if(pageCurrent == null || pageSize == null){
			return null;
		}
		return (pageCurrent-1)*pageSize;
	}
	
	public void setLimitOffset(Integer limitOffset) {}
	public void setLimitIndex(Integer limitIndex) {}
}
