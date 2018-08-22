package com.ssitcloud.shelfmgmt.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.BookshelflayerDao;
import com.ssitcloud.shelfmgmt.entity.BookshelflayerEntity;
import com.ssitcloud.shelfmgmt.entity.ExportBookshelfEntity;

@Repository
public class BookshelflayerDaoImpl extends CommonDaoImpl implements BookshelflayerDao {

	@Override
	public PageEntity queryAllBookshelflayer(Map<String, String> map) {
		// TODO Auto-generated method stub
		BookshelflayerEntity bookshelflayerEntity = JsonUtils.fromJson(map.get("json"), BookshelflayerEntity.class);
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("bookshelflayer.count", bookshelflayerEntity).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(), pageEntity.getPageSize());
		pageEntity.setRows(this.sqlSessionTemplate.selectList("bookshelflayer.select", bookshelflayerEntity, rowBounds));
		return pageEntity;
	}

	@Override
	public int updateBookshelflayer(BookshelflayerEntity bookshelflayerEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("bookshelflayer.update", bookshelflayerEntity);
	}

	@Override
	public int deleteBookshelflayer(List<BookshelflayerEntity> list) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("bookshelflayer.delete", list);
	}

	@Override
	public int addBookshelflayer(BookshelflayerEntity bookshelflayerEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("bookshelflayer.add", bookshelflayerEntity);
	}
	
	@Override
	public List<ExportBookshelfEntity> exportBookshelflayer(BookshelflayerEntity bookshelflayerEntity){
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("bookshelflayer.export", bookshelflayerEntity);
	}
	
	@Override
	public BookshelflayerEntity queryBookshelflayerByid(BookshelflayerEntity bookshelflayerEntity){
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("bookshelflayer.selectById", bookshelflayerEntity);
	}

	@Override
	public List<ExportBookshelfEntity> getbookshelfbybookbarcode(
			BookshelflayerEntity bookshelflayerEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("bookshelflayer.location", bookshelflayerEntity);
	}
}
