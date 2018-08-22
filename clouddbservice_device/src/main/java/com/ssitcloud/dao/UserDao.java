package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.UserRolePermessionEntity;

public interface UserDao {

	List<UserRolePermessionEntity> querybyId(
			UserRolePermessionEntity userRolePermession);

	List<UserRolePermessionEntity> queryAll();


}
