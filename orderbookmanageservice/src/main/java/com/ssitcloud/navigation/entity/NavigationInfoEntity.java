package com.ssitcloud.navigation.entity;

public class NavigationInfoEntity {
	
	private String lib_id;
	
	private String shelf_id;
	
	private String shelflayer_barcode;
	
	private Integer shelf_layer;

	private String shelflayer_name;
	
	private String shelf_name;

	private Integer layercount;
	
	private Integer layersort;

	private String floorimage_url;

	private String shelfimage_url;

	private String shelfpoint;

	private Integer issmartshelf;
	
	private String info_value;
	
	private String shelf_ip;
	private Integer shelf_port;
	
	private String[] locations;

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

	public Integer getShelf_layer() {
		return shelf_layer;
	}

	public void setShelf_layer(Integer shelf_layer) {
		this.shelf_layer = shelf_layer;
	}

	public String getShelflayer_name() {
		return shelflayer_name;
	}

	public void setShelflayer_name(String shelflayer_name) {
		this.shelflayer_name = shelflayer_name;
	}

	public String getShelf_name() {
		return shelf_name;
	}

	public void setShelf_name(String shelf_name) {
		this.shelf_name = shelf_name;
	}

	public Integer getLayercount() {
		return layercount;
	}

	public void setLayercount(Integer layercount) {
		this.layercount = layercount;
	}

	public Integer getLayersort() {
		return layersort;
	}

	public void setLayersort(Integer layersort) {
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

	public Integer getIssmartshelf() {
		return issmartshelf;
	}

	public void setIssmartshelf(Integer issmartshelf) {
		this.issmartshelf = issmartshelf;
	}

	public String getInfo_value() {
		return info_value;
	}

	public void setInfo_value(String info_value) {
		this.info_value = info_value;
	}

	public String[] getLocations() {
		return locations;
	}

	public void setLocations(String[] locations) {
		this.locations = locations;
	}

	public String getShelf_ip() {
		return shelf_ip;
	}

	public void setShelf_ip(String shelf_ip) {
		this.shelf_ip = shelf_ip;
	}

	public Integer getShelf_port() {
		return shelf_port;
	}

	public void setShelf_port(Integer shelf_port) {
		this.shelf_port = shelf_port;
	}
	
}
