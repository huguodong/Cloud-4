package com.ssitcloud.entity;

/**
 * 个人菜单设置
 *
 * <p>2017年2月10日 下午2:17:41  
 * @author hjc 
 *
 */
public class PersonalSettingEntity {
	
	private Integer setting_idx;//	设置ID
	private Integer operator_idx;//	操作员IDX
	private Integer service_idx;//		管理页面代码
	private String setting_url;//	URL地址
	private String setting_desc;//	功能面描述
	
	
	
	public Integer getSetting_idx() {
		return setting_idx;
	}
	public void setSetting_idx(Integer setting_idx) {
		this.setting_idx = setting_idx;
	}
	public Integer getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}
	public Integer getService_idx() {
		return service_idx;
	}
	public void setService_idx(Integer service_idx) {
		this.service_idx = service_idx;
	}
	public String getSetting_url() {
		return setting_url;
	}
	public void setSetting_url(String setting_url) {
		this.setting_url = setting_url;
	}
	public String getSetting_desc() {
		return setting_desc;
	}
	public void setSetting_desc(String setting_desc) {
		this.setting_desc = setting_desc;
	}


}
