package com.ssitcloud.dblib.dao.impl;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.BookShelfDao;
import com.ssitcloud.dblib.entity.BookShelfEntity;

@Repository
public class BookShelfDaoImpl extends CommonDaoImpl implements BookShelfDao{

	@Override
	public PageEntity queryAllBookshelf(Map<String, String> map) {
		// TODO Auto-generated method stub
		
		BookShelfEntity bookshelfEntity = JsonUtils.fromJson(map.get("json"), BookShelfEntity.class);
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("bookshelf.count", bookshelfEntity).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(), pageEntity.getPageSize());
		pageEntity.setRows(this.sqlSessionTemplate.selectList("bookshelf.select", bookshelfEntity, rowBounds));
		return pageEntity;
	}

	@Override
	public BookShelfEntity queryBookshelfById(BookShelfEntity bookshelfEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("bookshelf.selectById", bookshelfEntity);
	}

	@Override
	public int updateBookshelf(BookShelfEntity bookshelfEntity) {
		return this.sqlSessionTemplate.update("bookshelf.update", bookshelfEntity);
	}

	@Override
	public int deleteBookshelf(BookShelfEntity bookshelfEntity) {
		
		try{
			if(bookshelfEntity.getLib_id() != null && bookshelfEntity.getShelf_id() != null)
			sqlSessionTemplate.insert("libraryInfoCommon.superInsert", "insert into delete_rec(lib_id,table_name,key1,updatetime) values ('"+bookshelfEntity.getLib_id()+"','bookshelf','"+bookshelfEntity.getShelf_id()+"',SYSDATE())");
		}catch(Exception e){
			try{
				sqlSessionTemplate.update("libraryInfoCommon.superUpdate", "update delete_rec set updatetime = SYSDATE() where lib_id = '"+bookshelfEntity.getLib_id()+"' AND table_name = 'bookshelf' AND key1 = '"+bookshelfEntity.getShelf_id()+"'");
			}catch(Exception ex){
				//nothing
			}
		}
		
		return this.sqlSessionTemplate.delete("bookshelf.delete", bookshelfEntity);
	}

	@Override
	public int addBookshelf(BookShelfEntity bookshelfEntity) {
		return this.sqlSessionTemplate.insert("bookshelf.insertBookShelf", bookshelfEntity);
	}
	
	public int updateShelfimage(BookShelfEntity bookshelfEntity){
		return this.sqlSessionTemplate.update("bookshelf.updateShelfimage", bookshelfEntity);
	}
	
	public int updateFloorimage(BookShelfEntity bookshelfEntity){
		return this.sqlSessionTemplate.update("bookshelf.updateFloorimage", bookshelfEntity);
	}
	
	public  int updatePoint(BookShelfEntity bookshelfEntity){
		return this.sqlSessionTemplate.update("bookshelf.updatePoint", bookshelfEntity);
	}

}
