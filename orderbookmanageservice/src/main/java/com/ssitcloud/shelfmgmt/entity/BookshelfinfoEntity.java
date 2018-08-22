package com.ssitcloud.shelfmgmt.entity;

public class BookshelfinfoEntity {

	private String lib_id;
	private String shelf_id;
	private int info_type;
	private String info_value;
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
	public int getInfo_type() {
		return info_type;
	}
	public void setInfo_type(int info_type) {
		this.info_type = info_type;
	}
	public String getInfo_value() {
		return info_value;
	}
	public void setInfo_value(String info_value) {
		this.info_value = info_value;
	}
	@Override
	public String toString() {
		return "BookshelfinfoEntity [lib_id=" + lib_id + ", shelf_id="
				+ shelf_id + ", info_type=" + info_type + ", info_value="
				+ info_value + "]";
	}
	
	
}
