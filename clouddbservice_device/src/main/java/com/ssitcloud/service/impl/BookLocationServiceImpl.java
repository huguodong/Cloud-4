package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ssitcloud.dao.BookLocationDao;
import com.ssitcloud.entity.BookLocationEntity;
import com.ssitcloud.service.BookLocationService;


@Service
public class BookLocationServiceImpl implements BookLocationService {
	@Resource
	private BookLocationDao bookLocationDao;

	@Override
	public int insertBookLocation(
			BookLocationEntity bookLocationEntity) {
		return bookLocationDao.insertBookLocation(bookLocationEntity);
	}

	@Override
	public int updateBookLocation(
			BookLocationEntity bookLocationEntity) {
		return bookLocationDao.updateBookLocation(bookLocationEntity);
	}

	@Override
	public int deleteBookLocation(
			BookLocationEntity bookLocationEntity) {
		return bookLocationDao.deleteBookLocation(bookLocationEntity);
	}

	@Override
	public BookLocationEntity queryOneBookLocation(
			BookLocationEntity bookLocationEntity) {
		return bookLocationDao.queryOneBookLocation(bookLocationEntity);
			
	}

	@Override
	public List<BookLocationEntity> queryBookLocations(
			BookLocationEntity bookLocationEntity) {
		return bookLocationDao.queryBookLocations(bookLocationEntity);
		
	}


	

}
