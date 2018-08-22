package com.ssitcloud.view.shelfmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.shelfmgmt.service.BookshelfinfoService;

@Service
public class BookshelfinfoServiceImpl extends BasicServiceImpl implements BookshelfinfoService {

	private static final String URL_queryAllBookshelfinfo = "queryAllBookshelfinfo";
	private static final String URL_queryBookshelfinfoById = "queryBookshelfinfoById";
	private static final String URL_deleteBookshelfinfo = "deleteBookshelfinfo";
	private static final String URL_updateBookshelfinfo = "updateBookshelfinfo";
	private static final String URL_addBookshelfinfo = "addBookshelfinfo";
	
	@Override
	public ResultEntity queryAllBookshelfinfo(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryAllBookshelfinfo, req);
	}

	@Override
	public ResultEntity queryBookshelfinfoById(String req){
		return postUrl(URL_queryBookshelfinfoById, req);
	}
	
	@Override
	public ResultEntity updateBookshelfinfo(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_deleteBookshelfinfo, req);
	}

	@Override
	public ResultEntity deleteBookshelfinfo(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateBookshelfinfo, req);
	}

	@Override
	public ResultEntity addBookshelfinfo(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_addBookshelfinfo, req);
	}

}
