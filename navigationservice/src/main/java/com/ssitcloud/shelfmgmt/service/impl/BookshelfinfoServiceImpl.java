package com.ssitcloud.shelfmgmt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.BookshelfDao;
import com.ssitcloud.shelfmgmt.dao.BookshelfinfoDao;
import com.ssitcloud.shelfmgmt.entity.BookshelfinfoEntity;
import com.ssitcloud.shelfmgmt.service.BookshelfinfoService;

@Service
public class BookshelfinfoServiceImpl implements BookshelfinfoService {

	@Resource
	private BookshelfinfoDao bookshelfinfoDao;
	
	/*@Override
	public ResultEntity queryAllBookshelfinfo(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryAllBookshelfinfo, req);
	}*/

	@Override
	public ResultEntity queryBookshelfinfoById(String req){
		ResultEntity result = new ResultEntity();
		BookshelfinfoEntity bs = JsonUtils.fromJson(req, BookshelfinfoEntity.class);
		List<BookshelfinfoEntity> list = bookshelfinfoDao.queryBookshelfinfoById(bs);
		result.setResult(list);
		result.setMessage(Const.SUCCESS);
		result.setState(true);
		return result;
	}
	
	/*@Override
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
	}*/

}
