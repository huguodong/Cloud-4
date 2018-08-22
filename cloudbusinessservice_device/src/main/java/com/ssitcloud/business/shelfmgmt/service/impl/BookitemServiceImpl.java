package com.ssitcloud.business.shelfmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.shelfmgmt.service.BookitemService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class BookitemServiceImpl extends BasicServiceImpl implements BookitemService {

	private static final String URL_importBookitem = "importBookitem";
	private static final String URL_QUERYALLBOOKITEMANDBOOKINFOBYCODE = "queryAllBookitemAndBookInfoByCode";
	
	@Override
	public ResultEntity importBookitem(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_importBookitem, req);
	}

	@Override
	public ResultEntity queryAllBookitemAndBookInfoByCode(String req){
		// TODO Auto-generated method stub
		return postUrl(URL_QUERYALLBOOKITEMANDBOOKINFOBYCODE, req);
	}
}
