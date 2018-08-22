package com.ssitcloud.business.mobile.service;

import com.ssitcloud.mobile.entity.RegisterIdentifiedcodeEntity;

/**
 * 注册验证码服务
 * @author LXP
 *
 */
public interface RegisterIdentifiedcodeService {
	
	/**
	 * 插入注册验证码到数据
	 * @param entity 注册验证码
	 * @return 操作是否成功
	 */
	boolean insert(RegisterIdentifiedcodeEntity entity);
	
    /**
     * 查找注册验证码
     * @param email 注册邮箱
     * @param timeout 验证码超时时间，单位毫秒
     * @return 若存在验证码且未超时则返回，不存在返回null
     */
    RegisterIdentifiedcodeEntity selectCode(String email, long timeout);

    /**
	 * 异步销毁验证码
	 * @param email 邮箱
	 */
	void deleteByPkAsync(String email);
}
