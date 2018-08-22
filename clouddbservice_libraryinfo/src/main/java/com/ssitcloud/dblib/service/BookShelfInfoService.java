package com.ssitcloud.dblib.service;

import java.util.List;

import com.ssitcloud.dblib.entity.BookShelfInfoEntity;

public interface BookShelfInfoService {
	
	
	public abstract List<BookShelfInfoEntity> queryAllBookshelfinfo();
	
	public abstract List<BookShelfInfoEntity> queryBookshelfinfoById(BookShelfInfoEntity bookshelfinfoEntity);
	
	public abstract int updateBookshelfinfo(BookShelfInfoEntity bookshelfinfoEntity);
	
	public abstract int deleteBookshelfinfo(BookShelfInfoEntity bookshelfinfoEntity);
	
	public abstract int addBookshelfinfo(BookShelfInfoEntity bookshelfinfoEntity);	

}
