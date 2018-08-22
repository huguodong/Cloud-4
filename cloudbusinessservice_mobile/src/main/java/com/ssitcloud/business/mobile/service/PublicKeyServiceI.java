package com.ssitcloud.business.mobile.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 公钥服务
 * @author LXP
 * @version 创建时间：2017年4月13日 下午4:37:56
 */
public interface PublicKeyServiceI {
	ResultEntity selectNewPublic(Integer appType);
}
