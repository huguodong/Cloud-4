package com.ssitcloud.business.shelfmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface BookshelflayerService {

	ResultEntity queryAllBookshelflayer(String req);
	
	ResultEntity updateBookshelflayer(String req);
	
	ResultEntity deleteBookshelflayer(String req);
	
	ResultEntity addBookshelflayer(String req);
	
	ResultEntity exportBookshelflayer(String req);
	
	ResultEntity uploadBookshelflayer(String req);
}
