package com.ssitcloud.entity;

/**
 * 菜单实体类
meta_menu_idx int(11) NOT NULLIDX
main_menu_code varchar(20) NOT NULL主菜单代码
main_menu_name varchar(100) NULL主菜单名
sub_menu_code varchar(20) NULL子菜单代码
sub_menu_name varchar(100) NULL子菜单名
menu_mark varchar(100) NULL说明
 *
 * <p>2017年2月14日 下午3:15:47  
 * @author hjc 
 *
 */
public class MetadataMenuEntity {
	
	private Integer meta_menu_idx;//int(11) NOT NULLIDX
	private String main_menu_code;//varchar(20) NOT NULL主菜单代码
	private String main_menu_name;//varchar(100) NULL主菜单名
	private String menu_mark;//varchar(100) NULL说明
	private Integer menu_sort;//菜单排序号
	private String sub_menu_code;//varchar(20) NULL子菜单代码
	private String sub_menu_name;//varchar(100) NULL子菜单名
	private String sub_menu_mark;//varchar(100) NULL子菜单说明
	private Integer sub_menu_sort;//子菜单排序号
	
	public Integer getMeta_menu_idx() {
		return meta_menu_idx;
	}
	public void setMeta_menu_idx(Integer meta_menu_idx) {
		this.meta_menu_idx = meta_menu_idx;
	}
	public String getMain_menu_code() {
		return main_menu_code;
	}
	public void setMain_menu_code(String main_menu_code) {
		this.main_menu_code = main_menu_code;
	}
	public String getMain_menu_name() {
		return main_menu_name;
	}
	public void setMain_menu_name(String main_menu_name) {
		this.main_menu_name = main_menu_name;
	}
	public String getSub_menu_code() {
		return sub_menu_code;
	}
	public void setSub_menu_code(String sub_menu_code) {
		this.sub_menu_code = sub_menu_code;
	}
	public String getSub_menu_name() {
		return sub_menu_name;
	}
	public void setSub_menu_name(String sub_menu_name) {
		this.sub_menu_name = sub_menu_name;
	}
	public String getMenu_mark() {
		return menu_mark;
	}
	public void setMenu_mark(String menu_mark) {
		this.menu_mark = menu_mark;
	}
	public Integer getMenu_sort() {
		return menu_sort;
	}
	public void setMenu_sort(Integer menu_sort) {
		this.menu_sort = menu_sort;
	}
	public String getSub_menu_mark() {
		return sub_menu_mark;
	}
	public void setSub_menu_mark(String sub_menu_mark) {
		this.sub_menu_mark = sub_menu_mark;
	}
	public Integer getSub_menu_sort() {
		return sub_menu_sort;
	}
	public void setSub_menu_sort(Integer sub_menu_sort) {
		this.sub_menu_sort = sub_menu_sort;
	}

	
	
}
