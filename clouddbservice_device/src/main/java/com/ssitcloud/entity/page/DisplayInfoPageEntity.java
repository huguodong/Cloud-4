package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 大屏展示
 * 
 * <p>
 * 2018年1月09日
 * 
 * @author xbf
 * 
 */
public class DisplayInfoPageEntity extends DatagridPageEntity{
	private static final long serialVersionUID = 4984545017913361911L;
	private Integer display_info_idx;
	private Integer library_idx;
	private String index_page;
	private String display_title;
	private String weather_city;
	private String patron_door;
	private String logo_img;

	public Integer getDisplay_info_idx() {
		return display_info_idx;
	}

	public void setDisplay_info_idx(Integer display_info_idx) {
		this.display_info_idx = display_info_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

	public String getIndex_page() {
		return index_page;
	}

	public void setIndex_page(String index_page) {
		this.index_page = index_page;
	}

	public String getDisplay_title() {
		return display_title;
	}

	public void setDisplay_title(String display_title) {
		this.display_title = display_title;
	}

	public String getWeather_city() {
		return weather_city;
	}

	public void setWeather_city(String weather_city) {
		this.weather_city = weather_city;
	}

	public String getPatron_door() {
		return patron_door;
	}

	public void setPatron_door(String patron_door) {
		this.patron_door = patron_door;
	}

	public String getLogo_img() {
		return logo_img;
	}

	public void setLogo_img(String logo_img) {
		this.logo_img = logo_img;
	}
}
