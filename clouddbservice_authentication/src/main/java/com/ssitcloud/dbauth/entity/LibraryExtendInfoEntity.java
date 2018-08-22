package com.ssitcloud.dbauth.entity;


/**
 * 图书馆扩展信息实体
 * <p>2017年4月26日 下午1:43
 * @author shuangjunjie
 *
 */
public class LibraryExtendInfoEntity {
	/** 图书馆idx */
	private Integer library_idx;
	
	/** 地区idx */
	private Integer region_idx;
	
	/** 经纬度 */
	private String position;

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

	public Integer getRegion_idx() {
		return region_idx;
	}

	public void setRegion_idx(Integer region_idx) {
		this.region_idx = region_idx;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	
	
	
	
}
