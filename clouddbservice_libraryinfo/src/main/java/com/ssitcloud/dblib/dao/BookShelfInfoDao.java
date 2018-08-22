package com.ssitcloud.dblib.dao;

import java.util.List;

import com.ssitcloud.dblib.entity.BookShelfInfoEntity;


public interface BookShelfInfoDao {
	
	public abstract List<BookShelfInfoEntity> queryAllBookshelfinfo();
	
	public abstract List<BookShelfInfoEntity> queryBookshelfinfoById(BookShelfInfoEntity bookShelfInfoEntity);
	
	public abstract int updateBookshelfinfo(BookShelfInfoEntity bookShelfInfoEntity);
	
	public abstract int deleteBookshelfinfo(BookShelfInfoEntity bookShelfInfoEntity);
	
	public abstract int addBookshelfinfo(BookShelfInfoEntity bookShelfInfoEntity);

}
