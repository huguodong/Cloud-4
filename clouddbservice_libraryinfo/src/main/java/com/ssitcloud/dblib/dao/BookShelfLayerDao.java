package com.ssitcloud.dblib.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.entity.BookShelfLayerEntity;
import com.ssitcloud.dblib.entity.ExportBookshelfEntity;


public interface BookShelfLayerDao {

	public abstract PageEntity queryAllBookshelflayer(Map<String, String> map);
	
	public abstract int updateBookshelflayer(BookShelfLayerEntity bookshelflayerEntity);
	
	public abstract int deleteBookshelflayer(BookShelfLayerEntity bookshelflayerEntity);
	
	public abstract int addBookshelflayer(BookShelfLayerEntity bookshelflayerEntity);
	
	public abstract List<ExportBookshelfEntity> exportBookshelflayer(BookShelfLayerEntity bookshelflayerEntity);
	
	public abstract BookShelfLayerEntity queryBookshelflayerByid(BookShelfLayerEntity bookshelflayerEntity);
}
