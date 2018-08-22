package com.ssitcloud.shelfmgmt.dao;

import java.util.List;

import com.ssitcloud.shelfmgmt.entity.BookitemEntity;


public interface BookitemDao {
	
	public abstract List<BookitemEntity> queryAllBookitem(BookitemEntity bookitem);
	
	public abstract BookitemEntity queryBookitemByCode(BookitemEntity bookitem);
	
	public abstract int updateBookitem(BookitemEntity bookitem);
	
	public abstract int deleteBookitemById(List<BookitemEntity> list);
	
	public abstract int addBookitem(BookitemEntity bookitem);
}
