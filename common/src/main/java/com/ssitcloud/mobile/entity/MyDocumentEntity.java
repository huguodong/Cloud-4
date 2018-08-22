package com.ssitcloud.mobile.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * 我的收藏夹表
 * author huanghuang
 * 2017年2月10日 上午10:56:09
 */
@JsonRootName(value="myDocument")
public class MyDocumentEntity {
	private Integer document_idx;//int(11) NOT NULL 收藏ID
	private Integer reader_idx;//int(11) NULL 读者证idx
	private String barcode;//varchar(40) NULL 图书条码号
	private String document_desc;//varchar(200) NULL 说明
	private Timestamp createtime;//timestamp NOT NULL 收藏时间
	
	public Integer getDocument_idx() {
		return document_idx;
	}
	public void setDocument_idx(Integer document_idx) {
		this.document_idx = document_idx;
	}
	public Integer getReader_idx() {
		return reader_idx;
	}
	public void setReader_idx(Integer reader_idx) {
		this.reader_idx = reader_idx;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getDocument_desc() {
		return document_desc;
	}
	public void setDocument_desc(String document_desc) {
		this.document_desc = document_desc;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	
	
	
}

