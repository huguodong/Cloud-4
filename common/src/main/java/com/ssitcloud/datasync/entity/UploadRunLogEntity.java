package com.ssitcloud.datasync.entity;

import java.io.Serializable;
/**
 * device_id	String	设备ID
	Library_id	String	馆ID
	Pages	String	总页数
	nowPage	String	当前页
	Contents	String	数据内容
	
	{ "servicetype":"ssitcloud", "target":"ssitcloud", "operation":"uploadRunLog","data":{"device_id":"001_SSL001","library_id":"1","pages":"2","nowPage":"1","contents":"xsdasda"} }
	
 * @package: com.ssitcloud.datasync.entity
 * @classFile: UploadRunLogEntity
 * @author: liubh
 * @createTime: 2016年5月4日 下午7:03:05
 * @description: TODO
 */
public class UploadRunLogEntity implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String device_id;
	private String library_id;
	private String operator_id;
	private String pages;
	private String nowPage;
	private String contents;

	
	
	public UploadRunLogEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UploadRunLogEntity(String device_id, String library_id,String operator_id, String pages, String nowPage,
			String contents) {
		super();
		this.device_id = device_id;
		this.library_id = library_id;
		this.pages = pages;
		this.nowPage = nowPage;
		this.contents = contents;
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
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getNowPage() {
		return nowPage;
	}
	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	
}
