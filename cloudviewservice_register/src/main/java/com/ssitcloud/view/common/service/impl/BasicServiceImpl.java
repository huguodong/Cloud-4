package com.ssitcloud.view.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.view.common.service.BasicService;

@Service
public class BasicServiceImpl implements BasicService{
	@Resource(name="requestURLListEntity")
	protected RequestURLListEntity requestURL;
}
