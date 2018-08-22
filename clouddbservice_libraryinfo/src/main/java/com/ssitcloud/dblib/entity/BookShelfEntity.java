package com.ssitcloud.dblib.entity;

import java.util.Date;

public class BookShelfEntity {

	private String lib_id;//varchar(32) NOT NULL图书ID
	private String shelf_id;//varchar(50) NULL书架ID
	private int shelf_group_idx;
	private String shelf_name;//varchar(100) NULL书架名称
	private Integer layerCount;//int(11) NOT NULL书架层数
	private Integer layerSort;//int(1) NOT NULL书架层排序，0 – 从下到上递增，1 – 从上到下递增
	private String floorImage_url;//varchar(1024) NULL书架所在楼层图
	private String shelfImage_url;//varchar(1024) NULL书架层次示意图
	private String shelfPoint;//varchar(1024) NULL书架导航描点
	private Integer isSmartShelf;//int(1) NULL是否是智能书架，0 – 否，1 – 是
	private Integer version_stamp;
	private String shelf_group_id;
	private String info_1;
	private String info_2;
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
	public String getShelf_name() {
		return shelf_name;
	}
	public void setShelf_name(String shelf_name) {
		this.shelf_name = shelf_name;
	}
	public Integer getLayerCount() {
		return layerCount;
	}
	public void setLayerCount(Integer layerCount) {
		this.layerCount = layerCount;
	}
	public Integer getLayerSort() {
		return layerSort;
	}
	public void setLayerSort(Integer layerSort) {
		this.layerSort = layerSort;
	}
	public String getFloorImage_url() {
		return floorImage_url;
	}
	public void setFloorImage_url(String floorImage_url) {
		this.floorImage_url = floorImage_url;
	}
	public String getShelfImage_url() {
		return shelfImage_url;
	}
	public void setShelfImage_url(String shelfImage_url) {
		this.shelfImage_url = shelfImage_url;
	}
	public String getShelfPoint() {
		return shelfPoint;
	}
	public void setShelfPoint(String shelfPoint) {
		this.shelfPoint = shelfPoint;
	}
	public Integer getIsSmartShelf() {
		return isSmartShelf;
	}
	public void setIsSmartShelf(Integer isSmartShelf) {
		this.isSmartShelf = isSmartShelf;
	}
	public Integer getVersion_stamp() {
		return version_stamp;
	}
	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
	public int getShelf_group_idx() {
		return shelf_group_idx;
	}
	
	public void setShelf_group_idx(int shelf_group_idx) {
		this.shelf_group_idx = shelf_group_idx;
	}
	public String getShelf_group_id() {
		return shelf_group_id;
	}
	public void setShelf_group_id(String shelf_group_id) {
		this.shelf_group_id = shelf_group_id;
	}
	public String getInfo_1() {
		return info_1;
	}
	public void setInfo_1(String info_1) {
		this.info_1 = info_1;
	}
	public String getInfo_2() {
		return info_2;
	}
	public void setInfo_2(String info_2) {
		this.info_2 = info_2;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
