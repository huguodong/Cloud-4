package com.ssitcloud.business.mobile.service;

import com.ssitcloud.mobile.entity.PasswordIdentifiedcodeEntity;

public interface PasswordIdentifiedcodeService {
	
	boolean insert(PasswordIdentifiedcodeEntity entity);

    /**
     * @return 验证码存在且没有过期则返回，否则返回null
     */
	PasswordIdentifiedcodeEntity selectCode(int idx,long timeout);
	
	void deleteByPkAsync(int idx);
}
