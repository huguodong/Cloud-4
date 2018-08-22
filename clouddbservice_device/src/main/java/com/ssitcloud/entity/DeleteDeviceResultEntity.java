package com.ssitcloud.entity;

import java.util.List;



/**
 * 删除设备返回的结果
 * @author lbh
 *
 */
public class DeleteDeviceResultEntity {

	private List<String> deleteSuccessDeviceIds;
	
	private List<String> deleteFailDeviceIds;

	public List<String> getDeleteSuccessDeviceIds() {
		return deleteSuccessDeviceIds;
	}

	public void setDeleteSuccessDeviceIds(List<String> deleteSuccessDeviceIds) {
		this.deleteSuccessDeviceIds = deleteSuccessDeviceIds;
	}

	public List<String> getDeleteFailDeviceIds() {
		return deleteFailDeviceIds;
	}

	public void setDeleteFailDeviceIds(List<String> deleteFailDeviceIds) {
		this.deleteFailDeviceIds = deleteFailDeviceIds;
	}
	
}
