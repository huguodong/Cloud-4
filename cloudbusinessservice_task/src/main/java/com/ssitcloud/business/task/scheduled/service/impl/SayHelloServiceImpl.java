package com.ssitcloud.business.task.scheduled.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.task.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.task.scheduled.service.SayHelloService;
import com.ssitcloud.common.entity.ResultEntity;


/**
 * 测试demo
 *
 * <p>2017年2月22日 下午2:29:01  
 * @author hjc 
 *
 */
@Service
public class SayHelloServiceImpl extends BasicServiceImpl implements SayHelloService{
	private static final String URL_SAY_HELLO = "sayhello";

	@Override
	public ResultEntity sayHello() {
		return postURL(URL_SAY_HELLO, "");
	}

	
	
}
