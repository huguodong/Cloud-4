package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.BookMediatypeDao;
import com.ssitcloud.entity.BookMediatypeEntity;

@Repository
public class BookMediatypeDaoImpl extends CommonDaoImpl implements
		BookMediatypeDao {

	@Override
	public int insertBookMediatype(BookMediatypeEntity bookMediatypeEntity) {
		return this.sqlSessionTemplate.insert("book_mediatype.insertBookMediatype", bookMediatypeEntity);
	}

	@Override
	public int updateBookMediatype(BookMediatypeEntity bookMediatypeEntity) {
		return this.sqlSessionTemplate.update("book_mediatype.updateBookMediatype", bookMediatypeEntity);
	}

	@Override
	public int deleteBookMediatype(BookMediatypeEntity bookMediatypeEntity) {
		return this.sqlSessionTemplate.delete("book_mediatype.deleteBookMediatype", bookMediatypeEntity);
	}

	@Override
	public BookMediatypeEntity queryOneBookMediatype(
			BookMediatypeEntity bookMediatypeEntity) {
		return this.select("book_mediatype.selectBookMediatype", bookMediatypeEntity);
	}

	@Override
	public List<BookMediatypeEntity> queryBookMediatypes(
			BookMediatypeEntity bookMediatypeEntity) {
		return this.selectAll("book_mediatype.selectBookMediatypes", bookMediatypeEntity);
	}

}
