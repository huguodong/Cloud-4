package com.ssitcloud.app_reader.entity;

import java.io.Serializable;

/**
 * 区域 实体
 * author shuangjunjie
 * 2017年4月26日 下午3:00
 */
public class RegionEntity implements Serializable{
	
	private Integer regi_idx;		//int(11) NOT NULL
	private String regi_code;		//varchar(9) NULL
	private String regi_nation;		//varchar(16) NULL
	private String regi_province;	//varchar(16) NULL
	private String regi_city;		//varchar(16) NULL
	private String regi_area;		//varchar(16) NULL
	
	
	public Integer getRegi_idx() {
		return regi_idx;
	}
	public void setRegi_idx(Integer regi_idx) {
		this.regi_idx = regi_idx;
	}
	public String getRegi_code() {
		return regi_code;
	}
	public void setRegi_code(String regi_code) {
		this.regi_code = regi_code;
	}
	public String getRegi_nation() {
		return regi_nation;
	}
	public void setRegi_nation(String regi_nation) {
		this.regi_nation = regi_nation;
	}
	public String getRegi_province() {
		return regi_province;
	}
	public void setRegi_province(String regi_province) {
		this.regi_province = regi_province;
	}
	public String getRegi_city() {
		return regi_city;
	}
	public void setRegi_city(String regi_city) {
		this.regi_city = regi_city;
	}
	public String getRegi_area() {
		return regi_area;
	}
	public void setRegi_area(String regi_area) {
		this.regi_area = regi_area;
	}
	
	
}
