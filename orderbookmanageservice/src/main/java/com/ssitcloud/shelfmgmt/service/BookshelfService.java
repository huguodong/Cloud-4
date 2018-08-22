package com.ssitcloud.shelfmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface BookshelfService {

	ResultEntity queryAllBookshelf(String req);
	
	ResultEntity queryBookshelfById(String req);
	
	ResultEntity updateBookshelf(String req);
	
	ResultEntity deleteBookshelf(String req);
	
	ResultEntity addBookshelf(String req);
	
	ResultEntity updateShelfimage(String req);
	
	ResultEntity updateFloorimage(String req);
	
	ResultEntity uploadPoint(String req);
	
	ResultEntity inportShelfData();
	
	ResultEntity inportBookData();
}
