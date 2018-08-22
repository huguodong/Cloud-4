package com.ssitcloud.shelfmgmt.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelflayerEntity;
import com.ssitcloud.shelfmgmt.entity.ExportBookshelfEntity;

public interface BookshelflayerDao {

	public abstract PageEntity queryAllBookshelflayer(Map<String, String> map);
	
	public abstract int updateBookshelflayer(BookshelflayerEntity bookshelflayerEntity);
	
	public abstract int deleteBookshelflayer(List<BookshelflayerEntity> list);
	
	public abstract int addBookshelflayer(BookshelflayerEntity bookshelflayerEntity);
	
	public abstract List<ExportBookshelfEntity> exportBookshelflayer(BookshelflayerEntity bookshelflayerEntity);
	
	public abstract BookshelflayerEntity queryBookshelflayerByid(BookshelflayerEntity bookshelflayerEntity);
	
	public abstract List<ExportBookshelfEntity> getbookshelfbybookbarcode(BookshelflayerEntity bookshelflayerEntity);
	
}
