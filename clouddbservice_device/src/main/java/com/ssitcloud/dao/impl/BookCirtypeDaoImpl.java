package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.BookCirtypeDao;
import com.ssitcloud.entity.BookCirtypeEntity;

@Repository
public class BookCirtypeDaoImpl extends CommonDaoImpl implements
		BookCirtypeDao {

	@Override
	public int insertBookCirtype(BookCirtypeEntity bookCirtypeEntity) {
		return this.sqlSessionTemplate.insert("book_cirtype.insertBookCirtype", bookCirtypeEntity);
	}

	@Override
	public int updateBookCirtype(BookCirtypeEntity bookCirtypeEntity) {
		return this.sqlSessionTemplate.update("book_cirtype.updateBookCirtype", bookCirtypeEntity);
	}

	@Override
	public int deleteBookCirtype(BookCirtypeEntity bookCirtypeEntity) {
		return this.sqlSessionTemplate.delete("book_cirtype.deleteBookCirtype", bookCirtypeEntity);
	}

	@Override
	public BookCirtypeEntity queryOneBookCirtype(
			BookCirtypeEntity bookCirtypeEntity) {
		return this.select("book_cirtype.selectBookCirtype", bookCirtypeEntity);
	}

	@Override
	public List<BookCirtypeEntity> queryBookCirtypes(
			BookCirtypeEntity bookCirtypeEntity) {
		return this.selectAll("book_cirtype.selectBookCirtypes", bookCirtypeEntity);
	}

}
