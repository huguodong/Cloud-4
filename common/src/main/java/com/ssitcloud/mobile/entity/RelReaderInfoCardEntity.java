package com.ssitcloud.mobile.entity;

import java.util.Date;

public class RelReaderInfoCardEntity {
	
	private Integer rel_readerinfo_card_idx;
	private Integer reader_idx;
	private Integer lib_idx;
	private String card_no;
	private Date createtime;
	private Date updatetime;
	public Integer getRel_readerinfo_card_idx() {
		return rel_readerinfo_card_idx;
	}
	public void setRel_readerinfo_card_idx(Integer rel_readerinfo_card_idx) {
		this.rel_readerinfo_card_idx = rel_readerinfo_card_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public Integer getReader_idx() {
		return reader_idx;
	}
	public void setReader_idx(Integer reader_idx) {
		this.reader_idx = reader_idx;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
