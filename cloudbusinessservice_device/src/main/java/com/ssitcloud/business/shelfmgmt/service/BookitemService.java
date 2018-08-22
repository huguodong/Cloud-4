package com.ssitcloud.business.shelfmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface BookitemService {

	public abstract ResultEntity importBookitem(String req);
	
	public abstract ResultEntity queryAllBookitemAndBookInfoByCode(String req);
}
