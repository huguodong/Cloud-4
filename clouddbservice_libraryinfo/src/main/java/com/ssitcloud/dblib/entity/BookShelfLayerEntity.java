package com.ssitcloud.dblib.entity;

import java.util.Date;

public class BookShelfLayerEntity {
	private String lib_id;//图书馆ID
	private String shelf_id;//书架ID
	private Integer shelf_layer;//层架标所在书架层
	private String shelflayer_barcode;//层架标号
	private String shelflayer_name;//层架标名称
	private Integer version_stamp;
	private Date updatetime;
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public String getShelf_id() {
		return shelf_id;
	}
	public void setShelf_id(String shelf_id) {
		this.shelf_id = shelf_id;
	}
	public Integer getShelf_layer() {
		return shelf_layer;
	}
	public void setShelf_layer(Integer shelf_layer) {
		this.shelf_layer = shelf_layer;
	}
	public String getShelflayer_barcode() {
		return shelflayer_barcode;
	}
	public void setShelflayer_barcode(String shelflayer_barcode) {
		this.shelflayer_barcode = shelflayer_barcode;
	}
	public String getShelflayer_name() {
		return shelflayer_name;
	}
	public void setShelflayer_name(String shelflayer_name) {
		this.shelflayer_name = shelflayer_name;
	}
	public Integer getVersion_stamp() {
		return version_stamp;
	}
	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}
