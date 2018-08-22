package com.ssitcloud.dblib.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dblib.dao.BookInputDao;
import com.ssitcloud.dblib.entity.BookInputEntity;
import com.ssitcloud.dblib.entity.page.BookInputPageEntity;
import com.ssitcloud.dblib.service.BookInputService;

@Service
public class BookInputServiceImpl implements BookInputService{
	@Resource
	private BookInputDao bookInputDao;

	@Override
	public int insertBookInput(BookInputEntity bookInputEntity) {
		return bookInputDao.insertBookInput(bookInputEntity);
	}

	@Override
	public int deleteBookInput(BookInputEntity bookInputEntity) {
		return bookInputDao.deleteBookInput(bookInputEntity);
	}

	@Override
	public int updateBookInput(BookInputEntity bookInputEntity) {
		return bookInputDao.updateBookInput(bookInputEntity);
	}

	@Override
	public BookInputEntity queryBookInput(BookInputEntity bookInputEntity) {
		return bookInputDao.queryBookInput(bookInputEntity);
	}

	@Override
	public List<BookInputEntity> queryBookInputList(
			BookInputEntity bookInputEntity) {
		return bookInputDao.queryBookInputList(bookInputEntity);
	}
	
	@Override
	public BookInputPageEntity queryBookInputListByPage(
			BookInputPageEntity bookinputPageEntity) {
		return bookInputDao.queryBookInputListByPage(bookinputPageEntity);
	}
	
	
}
