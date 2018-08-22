package com.ssitcloud.dblib.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.datasync.entity.UploadcfgSynResultEntity;
import com.ssitcloud.dblib.entity.BookShelfLayerEntity;
import com.ssitcloud.dblib.entity.ExportBookshelfEntity;

public interface BookShelfLayerService {
	

	public abstract PageEntity queryAllBookshelflayer(Map<String, String> map);
	
	public abstract int updateBookshelflayer(BookShelfLayerEntity bookshelflayerEntity);
	
	public abstract int deleteBookshelflayer(BookShelfLayerEntity bookshelflayerEntity);
	
	public abstract int addBookshelflayer(BookShelfLayerEntity bookshelflayerEntity);
	
	public abstract List<ExportBookshelfEntity> exportBookshelflayer(BookShelfLayerEntity bookshelflayerEntity);
	
	public abstract int uploadBookshelflayer(String req);
}
