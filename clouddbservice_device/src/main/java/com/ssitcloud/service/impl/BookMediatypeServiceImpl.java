package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.BookMediatypeDao;
import com.ssitcloud.entity.BookMediatypeEntity;
import com.ssitcloud.service.BookMediatypeService;


@Service
public class BookMediatypeServiceImpl implements BookMediatypeService {
	@Resource
	private BookMediatypeDao emailParamDao;

	@Override
	public int insertBookMediatype(
			BookMediatypeEntity bookMediatypeEntity) {
		return emailParamDao.insertBookMediatype(bookMediatypeEntity);
	}

	@Override
	public int updateBookMediatype(
			BookMediatypeEntity bookMediatypeEntity) {
		return emailParamDao.updateBookMediatype(bookMediatypeEntity);
	}

	@Override
	public int deleteBookMediatype(
			BookMediatypeEntity bookMediatypeEntity) {
		return emailParamDao.deleteBookMediatype(bookMediatypeEntity);
	}

	@Override
	public BookMediatypeEntity queryOneBookMediatype(
			BookMediatypeEntity bookMediatypeEntity) {
		return emailParamDao.queryOneBookMediatype(bookMediatypeEntity);
			
	}

	@Override
	public List<BookMediatypeEntity> queryBookMediatypes(
			BookMediatypeEntity bookMediatypeEntity) {
		return emailParamDao.queryBookMediatypes(bookMediatypeEntity);
		
	}


	

}
