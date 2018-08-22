package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.BookLocationDao;
import com.ssitcloud.entity.BookLocationEntity;

@Repository
public class BookLocationDaoImpl extends CommonDaoImpl implements
		BookLocationDao {

	@Override
	public int insertBookLocation(BookLocationEntity bookLocationEntity) {
		return this.sqlSessionTemplate.insert("book_location.insertBookLocation", bookLocationEntity);
	}

	@Override
	public int updateBookLocation(BookLocationEntity bookLocationEntity) {
		return this.sqlSessionTemplate.update("book_location.updateBookLocation", bookLocationEntity);
	}

	@Override
	public int deleteBookLocation(BookLocationEntity bookLocationEntity) {
		return this.sqlSessionTemplate.delete("book_location.deleteBookLocation", bookLocationEntity);
	}

	@Override
	public BookLocationEntity queryOneBookLocation(
			BookLocationEntity bookLocationEntity) {
		return this.select("book_location.selectBookLocation", bookLocationEntity);
	}

	@Override
	public List<BookLocationEntity> queryBookLocations(
			BookLocationEntity bookLocationEntity) {
		return this.selectAll("book_location.selectBookLocations", bookLocationEntity);
	}


}
