package com.ssitcloud.common.dao;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.UserEntity;

public interface UserDao {
	public abstract Operator logincheck(UserEntity user);
	public abstract Integer changePassword(UserEntity user);
}