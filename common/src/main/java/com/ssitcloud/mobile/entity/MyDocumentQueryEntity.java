
package com.ssitcloud.mobile.entity;


/**
 * MyDocumentEntity的查询实体类
 * @author LXP
 * @version 创建时间：2017年2月22日 下午1:16:37
 */
public class MyDocumentQueryEntity extends MyDocumentEntity {
	private Integer pageCurrent;//第几页，第1，2，3。。。。页
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
	
	/**
	 * 返回mysql的limit值
	 * @return
	 */
	public Integer getLimitIndex(){
		if(pageCurrent == null || pageSize == null){
			return null;
		}
		return (pageCurrent-1)*pageSize;
	}
	
	/**
	 * 返回mysql的limit值
	 * @return
	 */
	public Integer getLimitOffset(){
		return pageSize;
	}
	
	public void setLimitIndex(Integer i){}
	
	public void setLimitOffset(Integer i){}
}
