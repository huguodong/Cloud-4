package com.ssitcloud.view.shelfmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.shelfmgmt.service.BookshelflayerService;

@Service
public class BookshelflayerServiceImpl extends BasicServiceImpl implements BookshelflayerService {

	private static final String URL_queryAllBookshelflayer = "queryAllBookshelflayer";
	private static final String URL_deleteBookshelflayer = "deleteBookshelflayer";
	private static final String URL_updateBookshelflayer = "updateBookshelflayer";
	private static final String URL_addBookshelflayer = "addBookshelflayer";
	private static final String URL_exportBookshelflayer = "exportBookshelflayer";
	private static final String URL_uploadBookshelflayer = "uploadBookshelflayer";
	
	
	@Override
	public ResultEntity queryAllBookshelflayer(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryAllBookshelflayer, req);
	}

	@Override
	public ResultEntity updateBookshelflayer(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateBookshelflayer, req);
	}

	@Override
	public ResultEntity deleteBookshelflayer(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_deleteBookshelflayer, req);
	}

	@Override
	public ResultEntity addBookshelflayer(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_addBookshelflayer, req);
	}
	
	@Override
	public ResultEntity exportBookshelflayer(String req){
		// TODO Auto-generated method stub
		return postUrl(URL_exportBookshelflayer, req);
	}
	
	@Override
	public  ResultEntity uploadBookshelflayer(String req){
		// TODO Auto-generated method stub
		System.out.println(req);
		return postUrl(URL_uploadBookshelflayer, req);
	}

}
