package com.ssitcloud.business.mobile.common.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;



public interface BasicService {


	ResultEntity postURL(String URL, String req);

	ResultEntity postURLLongtime(String URL, String req);
	
	ResultEntity postURL(String URL,Map<String, String> map);
}
