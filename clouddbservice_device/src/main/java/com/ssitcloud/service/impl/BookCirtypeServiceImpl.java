package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ssitcloud.dao.BookCirtypeDao;
import com.ssitcloud.entity.BookCirtypeEntity;
import com.ssitcloud.service.BookCirtypeService;


@Service
public class BookCirtypeServiceImpl implements BookCirtypeService {
	@Resource
	private BookCirtypeDao bookCirtypeDao;

	@Override
	public int insertBookCirtype(
			BookCirtypeEntity bookCirtypeEntity) {
		return bookCirtypeDao.insertBookCirtype(bookCirtypeEntity);
	}

	@Override
	public int updateBookCirtype(
			BookCirtypeEntity bookCirtypeEntity) {
		return bookCirtypeDao.updateBookCirtype(bookCirtypeEntity);
	}

	@Override
	public int deleteBookCirtype(
			BookCirtypeEntity bookCirtypeEntity) {
		return bookCirtypeDao.deleteBookCirtype(bookCirtypeEntity);
	}

	@Override
	public BookCirtypeEntity queryOneBookCirtype(
			BookCirtypeEntity bookCirtypeEntity) {
		return bookCirtypeDao.queryOneBookCirtype(bookCirtypeEntity);
			
	}

	@Override
	public List<BookCirtypeEntity> queryBookCirtypes(
			BookCirtypeEntity bookCirtypeEntity) {
		return bookCirtypeDao.queryBookCirtypes(bookCirtypeEntity);
	}
	

}
