package com.ssitcloud.dblib.dao;

import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.entity.BookShelfEntity;


public interface BookShelfDao {
	public abstract PageEntity queryAllBookshelf(Map<String, String> map);
	
	public abstract BookShelfEntity queryBookshelfById(BookShelfEntity bookshelfEntity);
	
	public abstract int updateBookshelf(BookShelfEntity bookshelfEntity);
	
	public abstract int deleteBookshelf(BookShelfEntity bookshelfEntity);
	
	public abstract int addBookshelf(BookShelfEntity bookshelfEntity);
	
	public abstract int updateShelfimage(BookShelfEntity bookshelfEntity);
	
	public abstract int updateFloorimage(BookShelfEntity bookshelfEntity);
	
	public abstract int updatePoint(BookShelfEntity bookshelfEntity);
}
