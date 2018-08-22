package com.ssitcloud.dblib.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class BookInputPageEntity extends DatagridPageEntity<BookInputPageEntity>{
	private static final long serialVersionUID = 1L;
	
	private Integer bookinput_idx;//int(11) NOT NULL图书录入索引号
	private Integer bookitem_idx;//int(11) NOT NULL图书在架信息表idx
	private Integer inputway;//int(11) NOT NULL入档方式
	private String inputdevice_idx;//varchar(32) NULL入档来源设备IDX
	private String inputdatetime;//varchar(20) NULL入档时间
	private String inputno;//varchar(20) NULL入档批号
	
	
	public Integer getBookinput_idx() {
		return bookinput_idx;
	}
	public void setBookinput_idx(Integer bookinput_idx) {
		this.bookinput_idx = bookinput_idx;
	}
	public Integer getBookitem_idx() {
		return bookitem_idx;
	}
	public void setBookitem_idx(Integer bookitem_idx) {
		this.bookitem_idx = bookitem_idx;
	}
	public Integer getInputway() {
		return inputway;
	}
	public void setInputway(Integer inputway) {
		this.inputway = inputway;
	}
	public String getInputdevice_idx() {
		return inputdevice_idx;
	}
	public void setInputdevice_idx(String inputdevice_idx) {
		this.inputdevice_idx = inputdevice_idx;
	}
	public String getInputdatetime() {
		return inputdatetime;
	}
	public void setInputdatetime(String inputdatetime) {
		this.inputdatetime = inputdatetime;
	}
	public String getInputno() {
		return inputno;
	}
	public void setInputno(String inputno) {
		this.inputno = inputno;
	}
	
	
}

