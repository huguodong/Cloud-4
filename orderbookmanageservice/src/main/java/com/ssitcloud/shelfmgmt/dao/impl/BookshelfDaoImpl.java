package com.ssitcloud.shelfmgmt.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.BookshelfDao;
import com.ssitcloud.shelfmgmt.entity.BibliosEntity;
import com.ssitcloud.shelfmgmt.entity.BookitemEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelfEntity;
import com.ssitcloud.shelfmgmt.entity.RelShelfConfigEntity;
import com.ssitcloud.shelfmgmt.entity.RelShelfGroupEntity;

@Repository
public class BookshelfDaoImpl extends CommonDaoImpl implements BookshelfDao {

	
	@Override
	public PageEntity queryAllBookshelf(Map<String, String> map) {
		// TODO Auto-generated method stub
		
		BookshelfEntity bookshelfEntity = JsonUtils.fromJson(map.get("json"), BookshelfEntity.class);
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("bookshelf.count", bookshelfEntity).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(), pageEntity.getPageSize());
		pageEntity.setRows(this.sqlSessionTemplate.selectList("bookshelf.select", bookshelfEntity, rowBounds));
		return pageEntity;
	}
	
	@Override
	public BookshelfEntity queryBookshelfById(BookshelfEntity bookshelfEntity) {
		return this.sqlSessionTemplate.selectOne("bookshelf.selectById", bookshelfEntity);
	}

	@Override
	public int updateBookshelf(BookshelfEntity bookshelfEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("bookshelf.update", bookshelfEntity);
	}

	@Override
	public int deleteBookshelf(List<BookshelfEntity> list) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("bookshelf.delete", list);
	}

	@Override
	public int addBookshelf(BookshelfEntity bookshelfEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("bookshelf.add", bookshelfEntity);
	}
	
	public int addRelShelfGroup(RelShelfGroupEntity relShelfGroup){
		return this.sqlSessionTemplate.insert("bookshelf.addRel", relShelfGroup);
	}

	public int updateBookshelf(RelShelfGroupEntity relShelfGroup){
		return this.sqlSessionTemplate.update("bookshelf.updateRel", relShelfGroup);
	}
	
	
	public int updateShelfimage(BookshelfEntity bookshelfEntity){
		return this.sqlSessionTemplate.update("bookshelf.updateShelfimage", bookshelfEntity);
	}
	
	public int updateFloorimage(BookshelfEntity bookshelfEntity){
		return this.sqlSessionTemplate.update("bookshelf.updateFloorimage", bookshelfEntity);
	}
	
	public  int updatePoint(BookshelfEntity bookshelfEntity){
		return this.sqlSessionTemplate.update("bookshelf.updatePoint", bookshelfEntity);
	}

	@Override
	public List<BibliosEntity> selectBiblios() {
		return this.sqlSessionTemplate.selectList("bookshelf.selectbiblios");
	}
	
	@Override
	public List<BookitemEntity> selectBookitem() {
		return this.sqlSessionTemplate.selectList("bookshelf.selectbookitem");
	}

	@Override
	public int deleteBiblios(BibliosEntity ety) {
		return this.sqlSessionTemplate.delete("bookshelf.deletebiblios", ety);
	}
}
