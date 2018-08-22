package com.ssitcloud.view.shelfmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.shelfmgmt.service.BookshelfService;

@Service
public class BookshelfServiceImpl extends BasicServiceImpl implements BookshelfService {

	private static final String URL_queryAllBookshelf = "queryAllBookshelf";
	private static final String URL_queryBookshelfById = "queryBookshelfById";
	private static final String URL_deleteBookshelf = "deleteBookshelf";
	private static final String URL_updateBookshelf = "updateBookshelf";
	private static final String URL_addBookshelf = "addBookshelf";
	private static final String URL_updateShelfimage = "updateShelfimage";
	private static final String URL_updateFloorimage = "updateFloorimage";
	private static final String URL_uploadPoint = "uploadPoint";
	
	@Override
	public ResultEntity queryAllBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryAllBookshelf, req);
	}

	@Override
	public ResultEntity queryBookshelfById(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryBookshelfById, req);
	}
	
	@Override
	public ResultEntity updateBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateBookshelf, req);
	}

	@Override
	public ResultEntity deleteBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_deleteBookshelf, req);
	}

	@Override
	public ResultEntity addBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_addBookshelf, req);
	}
	
	@Override
	public ResultEntity updateShelfimage(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateShelfimage, req);
	}

	@Override
	public ResultEntity updateFloorimage(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateFloorimage, req);
	}
	
	@Override
	public ResultEntity uploadPoint(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_uploadPoint, req);
	}

}
