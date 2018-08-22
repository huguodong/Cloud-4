package com.ssitcloud.view.common.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.SwitchLoginService;
@Service
public class SwitchLoginServiceImpl extends BasicServiceImpl implements SwitchLoginService {
	
	private static final String URL_GETPROJECTURL = "getProjectURL";
	@Override
	public ResultEntity getProjectURL(String req) {
		return postUrl(URL_GETPROJECTURL,req) ;
	}

}
