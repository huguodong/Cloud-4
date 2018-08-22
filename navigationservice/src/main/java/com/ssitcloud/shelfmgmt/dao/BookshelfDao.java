package com.ssitcloud.shelfmgmt.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.shelfmgmt.entity.BibliosEntity;
import com.ssitcloud.shelfmgmt.entity.BookitemEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelfEntity;

public interface BookshelfDao {
	public abstract PageEntity queryAllBookshelf(Map<String, String> map);
	
	public abstract BookshelfEntity queryBookshelfById(BookshelfEntity bookshelfEntity);
	
	public abstract int updateBookshelf(BookshelfEntity bookshelfEntity);
	
	public abstract int deleteBookshelf(BookshelfEntity bookshelfEntity);
	
	public abstract int addBookshelf(BookshelfEntity bookshelfEntity);
	
	
	public abstract int updateShelfimage(BookshelfEntity bookshelfEntity);
	
	public abstract int updateFloorimage(BookshelfEntity bookshelfEntity);
	
	public abstract int updatePoint(BookshelfEntity bookshelfEntity);
	
	public abstract List<BibliosEntity> selectBiblios();
	
	public abstract List<BookitemEntity> selectBookitem();
	
	public abstract int deleteBiblios(BibliosEntity ety);
}