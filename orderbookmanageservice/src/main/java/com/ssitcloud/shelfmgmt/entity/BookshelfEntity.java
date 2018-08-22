package com.ssitcloud.shelfmgmt.entity;

public class BookshelfEntity {

	private int shelf_idx;
	private String lib_id;
	private String shelf_id;
	private int shelf_group_idx;
	private String shelf_name;
	private int layercount;
	private int layersort;
	private String floorimage_url;
	private String shelfimage_url;
	private String shelfpoint;
	private int issmartshelf;
	private Integer version_stamp;
	public int getShelf_idx() {
		return shelf_idx;
	}
	public void setShelf_idx(int shelf_idx) {
		this.shelf_idx = shelf_idx;
	}
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
	public String getShelf_name() {
		return shelf_name;
	}
	public void setShelf_name(String shelf_name) {
		this.shelf_name = shelf_name;
	}
	public int getLayercount() {
		return layercount;
	}
	public void setLayercount(int layercount) {
		this.layercount = layercount;
	}
	public int getLayersort() {
		return layersort;
	}
	public void setLayersort(int layersort) {
		this.layersort = layersort;
	}
	public String getFloorimage_url() {
		return floorimage_url;
	}
	public void setFloorimage_url(String floorimage_url) {
		this.floorimage_url = floorimage_url;
	}
	public String getShelfimage_url() {
		return shelfimage_url;
	}
	public void setShelfimage_url(String shelfimage_url) {
		this.shelfimage_url = shelfimage_url;
	}
	public String getShelfpoint() {
		return shelfpoint;
	}
	public void setShelfpoint(String shelfpoint) {
		this.shelfpoint = shelfpoint;
	}
	public int getIssmartshelf() {
		return issmartshelf;
	}
	public void setIssmartshelf(int issmartshelf) {
		this.issmartshelf = issmartshelf;
	}
	
	public int getShelf_group_idx() {
		return shelf_group_idx;
	}
	
	public void setShelf_group_idx(int shelf_group_idx) {
		this.shelf_group_idx = shelf_group_idx;
	}
	
	public Integer getVersion_stamp() {
		return version_stamp;
	}
	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
	
}
