package com.ssitcloud.common.service;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.UserEntity;

public interface UserService{
	Operator logincheck(UserEntity user);
	Integer changePassword(UserEntity user);
}
