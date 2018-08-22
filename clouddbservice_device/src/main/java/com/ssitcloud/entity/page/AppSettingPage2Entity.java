package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * APP功能配置表
 * author lqw
 * 2017年3月20日
 */
public class AppSettingPage2Entity extends DatagridPageEntity{
	private static final long serialVersionUID = 1L;
	
	private Integer setting_idx;//int(11) NOT NULL 设置ID
	private String user_type;//varchar(2) NULL 用户类型代码1馆员　2读者
	private String image_url;//主页面上图片地址
	private Integer lib_idx;//int(11) NOT NULL 图书馆IDX
	private String lib_idx_str;//int(11) NOT NULL 图书馆IDX拼接字符串
	private String service_id;//int(11) NULL APP页面代码
	private String setting_desc;//varchar(50) NULL 功能面描述
	private String sub_menu_name;//varchar(50) NULL 功能名称
	private Integer setting_sort;//排序字段，数据库中默认0。在查询条件中为null则升序，不为null则降序
	private String setting_sort_str;//排序字段，数据库中默认0。在查询条件中为null则升序，不为null则降序
	private String sys_name;  //系统名称
	public Integer getSetting_idx() {
		return setting_idx;
	}
	public void setSetting_idx(Integer setting_idx) {
		this.setting_idx = setting_idx;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	
	public String getLib_idx_str() {
		return lib_idx_str;
	}
	public void setLib_idx_str(String lib_idx_str) {
		this.lib_idx_str = lib_idx_str;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getSetting_desc() {
		return setting_desc;
	}
	public void setSetting_desc(String setting_desc) {
		this.setting_desc = setting_desc;
	}
	
	public String getSub_menu_name() {
		return sub_menu_name;
	}
	public void setSub_menu_name(String sub_menu_name) {
		this.sub_menu_name = sub_menu_name;
	}
	public Integer getSetting_sort() {
		return setting_sort;
	}
	public void setSetting_sort(Integer setting_sort) {
		this.setting_sort = setting_sort;
	}
	public String getSetting_sort_str() {
		return setting_sort_str;
	}
	public void setSetting_sort_str(String setting_sort_str) {
		this.setting_sort_str = setting_sort_str;
	}
	public String getSys_name() {
		return sys_name;
	}
	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}
	

}
