package com.ssitcloud.dblib.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.BookShelfLayerDao;
import com.ssitcloud.dblib.entity.BookShelfLayerEntity;
import com.ssitcloud.dblib.entity.ExportBookshelfEntity;

@Repository
public class BookShelfLayerDaoImpl extends CommonDaoImpl implements
		BookShelfLayerDao {

	@Override
	public PageEntity queryAllBookshelflayer(Map<String, String> map) {
		// TODO Auto-generated method stub
		BookShelfLayerEntity bookshelflayerEntity = JsonUtils.fromJson(map.get("json"), BookShelfLayerEntity.class);
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("bookshelflayer.count", bookshelflayerEntity).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(), pageEntity.getPageSize());
		pageEntity.setRows(this.sqlSessionTemplate.selectList("bookshelflayer.select", bookshelflayerEntity, rowBounds));
		return pageEntity;
	}

	@Override
	public int updateBookshelflayer(BookShelfLayerEntity bookshelflayerEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("bookshelflayer.update", bookshelflayerEntity);
	}

	@Override
	public int deleteBookshelflayer(BookShelfLayerEntity bookshelflayerEntity) {
		try{
			if(bookshelflayerEntity.getLib_id() != null && bookshelflayerEntity.getShelflayer_barcode() != null)
			sqlSessionTemplate.insert("libraryInfoCommon.superInsert", "insert into delete_rec(lib_id,table_name,key1,updatetime) values ('"+bookshelflayerEntity.getLib_id()+"','bookshelflayer','"+bookshelflayerEntity.getShelflayer_barcode()+"',SYSDATE())");
		}catch(Exception e){
			try{
				sqlSessionTemplate.update("libraryInfoCommon.superUpdate", "update delete_rec set updatetime = SYSDATE() where lib_id = '"+bookshelflayerEntity.getLib_id()+"'AND table_name = 'bookshelflayer' AND key1 = '"+bookshelflayerEntity.getShelflayer_barcode()+"'");
			}catch(Exception ex){
				//nothing
				System.out.println();
			}
		}
		return this.sqlSessionTemplate.delete("bookshelflayer.delete", bookshelflayerEntity);
	}

	@Override
	public int addBookshelflayer(BookShelfLayerEntity bookshelflayerEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("bookshelflayer.add", bookshelflayerEntity);
	}
	
	@Override
	public List<ExportBookshelfEntity> exportBookshelflayer(BookShelfLayerEntity bookshelflayerEntity){
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("bookshelflayer.export", bookshelflayerEntity);
	}
	
	@Override
	public BookShelfLayerEntity queryBookshelflayerByid(BookShelfLayerEntity bookshelflayerEntity){
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("bookshelflayer.selectById", bookshelflayerEntity);
	}
}
