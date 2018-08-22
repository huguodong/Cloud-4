package com.ssitcloud.shelfmgmt.entity;

import java.util.Date;

public class BookshelflayerEntity {

	private String lib_id;
	private String shelf_id;
	private String shelflayer_barcode;
	private int shelf_layer;
	private String shelflayer_name;
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
	public String getShelflayer_barcode() {
		return shelflayer_barcode;
	}
	public void setShelflayer_barcode(String shelflayer_barcode) {
		this.shelflayer_barcode = shelflayer_barcode;
	}
	public int getShelf_layer() {
		return shelf_layer;
	}
	public void setShelf_layer(int shelf_layer) {
		this.shelf_layer = shelf_layer;
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
	@Override
	public String toString() {
		return "BookshelflayerEntity [lib_id=" + lib_id + ", shelf_id=" + shelf_id
				+ ", shelflayer_barcode=" + shelflayer_barcode
				+ ", shelf_layer=" + shelf_layer + ", shelflayer_name="
				+ shelflayer_name  + ", version_stamp="
						+ version_stamp + "]";
	}
	
	
}
