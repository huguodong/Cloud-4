package com.ssitcloud.entity;

/**
 * sub_menu_name 和 image_url组合实体
 * @author shuangjunjie
 *
 */
public class MenuAppSettingEntity {

	private String sub_menu_code;	//sub_menu_code varchar(20) NULL子菜单代码
	private String sub_menu_name;	//sub_menu_name varchar(100) NULL子菜单名
	private String image_url;		//主页面上图片地址
	public String getSub_menu_name() {
		return sub_menu_name;
	}
	public void setSub_menu_name(String sub_menu_name) {
		this.sub_menu_name = sub_menu_name;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getSub_menu_code() {
		return sub_menu_code;
	}
	public void setSub_menu_code(String sub_menu_code) {
		this.sub_menu_code = sub_menu_code;
	}
	
}
