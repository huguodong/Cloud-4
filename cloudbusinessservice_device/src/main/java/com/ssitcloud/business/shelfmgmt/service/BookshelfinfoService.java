package com.ssitcloud.business.shelfmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface BookshelfinfoService {

	ResultEntity queryAllBookshelfinfo(String req);
	
	ResultEntity queryBookshelfinfoById(String req);
	
	ResultEntity updateBookshelfinfo(String req);
	
	ResultEntity deleteBookshelfinfo(String req);
	
	ResultEntity addBookshelfinfo(String req);
}
