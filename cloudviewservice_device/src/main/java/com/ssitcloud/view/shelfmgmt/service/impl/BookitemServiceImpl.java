package com.ssitcloud.view.shelfmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.shelfmgmt.service.BookitemService;

@Service
public class BookitemServiceImpl extends BasicServiceImpl implements BookitemService {

	private static final String URL_importBookitem = "importBookitem";
	
	@Override
	public ResultEntity importBookitem(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_importBookitem, req);
	}

}
