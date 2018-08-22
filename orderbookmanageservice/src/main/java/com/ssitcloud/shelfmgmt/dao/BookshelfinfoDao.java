package com.ssitcloud.shelfmgmt.dao;

import java.util.List;

import com.ssitcloud.shelfmgmt.entity.BookshelfinfoEntity;

public interface BookshelfinfoDao {

	
	public abstract List<BookshelfinfoEntity> queryAllBookshelfinfo();
	
	public abstract List<BookshelfinfoEntity> queryBookshelfinfoById(BookshelfinfoEntity bookshelfinfoEntity);
	
	public abstract int updateBookshelfinfo(BookshelfinfoEntity bookshelfinfoEntity);
	
	public abstract int deleteBookshelfinfo(BookshelfinfoEntity bookshelfinfoEntity);
	
	public abstract int addBookshelfinfo(BookshelfinfoEntity bookshelfinfoEntity);
}
