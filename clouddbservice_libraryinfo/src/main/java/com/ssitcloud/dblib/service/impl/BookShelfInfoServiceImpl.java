package com.ssitcloud.dblib.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dblib.dao.BookShelfInfoDao;
import com.ssitcloud.dblib.entity.BookShelfInfoEntity;
import com.ssitcloud.dblib.service.BookShelfInfoService;

@Service
public class BookShelfInfoServiceImpl implements BookShelfInfoService {
	@Resource
	private BookShelfInfoDao bookShelfLayerDao;


	@Override
	public List<BookShelfInfoEntity> queryAllBookshelfinfo() {
		// TODO Auto-generated method stub
		return bookShelfLayerDao.queryAllBookshelfinfo();
	}
	
	@Override
	public List<BookShelfInfoEntity> queryBookshelfinfoById(BookShelfInfoEntity bookshelfinfoEntity){
		return bookShelfLayerDao.queryBookshelfinfoById(bookshelfinfoEntity);
	}

	@Override
	public int updateBookshelfinfo(BookShelfInfoEntity bookshelfinfoEntity) {
		// TODO Auto-generated method stub
		return bookShelfLayerDao.updateBookshelfinfo(bookshelfinfoEntity);
	}

	@Override
	public int deleteBookshelfinfo(BookShelfInfoEntity bookshelfinfoEntity) {
		// TODO Auto-generated method stub
		return bookShelfLayerDao.deleteBookshelfinfo(bookshelfinfoEntity);
	}

	@Override
	public int addBookshelfinfo(BookShelfInfoEntity bookshelfinfoEntity) {
		// TODO Auto-generated method stub
		return bookShelfLayerDao.addBookshelfinfo(bookshelfinfoEntity);
	}


}
