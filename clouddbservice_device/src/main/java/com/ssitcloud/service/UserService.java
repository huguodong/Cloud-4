package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.UserRolePermessionEntity;

public interface UserService {

	List<UserRolePermessionEntity> queryById(String id);

	List<UserRolePermessionEntity> queryByIdAndDeviceUser(
			UserRolePermessionEntity userRolePermessionEntity);

	List<UserRolePermessionEntity> queryAll();
	
	/**
	 * 查询权限
	 * @param userRolePermession
	 * @return
	 */
	List<UserRolePermessionEntity> queryByUserRolePermessionEntity(UserRolePermessionEntity userRolePermession);

}
