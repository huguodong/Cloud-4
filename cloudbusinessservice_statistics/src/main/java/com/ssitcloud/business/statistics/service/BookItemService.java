package com.ssitcloud.business.statistics.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.libraryinfo.entity.BookUnionEntity;

public interface BookItemService {

	
	public abstract ResultEntity getAllBookItem();
	
	public ResultEntity queryBookItemByUnion(String req);

}
