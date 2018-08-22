package com.ssitcloud.dblib.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.BookShelfInfoDao;
import com.ssitcloud.dblib.entity.BookShelfInfoEntity;

@Repository
public class BookShelfInfoDaoImpl extends CommonDaoImpl implements
		BookShelfInfoDao {


	@Override
	public List<BookShelfInfoEntity> queryAllBookshelfinfo() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("bookshelfinfo.select");
	}
	
	@Override
	public List<BookShelfInfoEntity> queryBookshelfinfoById(BookShelfInfoEntity bookshelfinfoEntity){
		
		return this.sqlSessionTemplate.selectList("bookshelfinfo.selectById",bookshelfinfoEntity);
	}

	@Override
	public int updateBookshelfinfo(BookShelfInfoEntity bookshelfinfoEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("bookshelfinfo.update", bookshelfinfoEntity);
	}

	@Override
	public int deleteBookshelfinfo(BookShelfInfoEntity bookshelfinfoEntity) {
		try{
			if(bookshelfinfoEntity.getLib_id() != null && bookshelfinfoEntity.getShelf_id() != null && bookshelfinfoEntity.getInfo_type() > 0)
			sqlSessionTemplate.insert("libraryInfoCommon.superInsert", "insert into delete_rec(lib_id,table_name,key1,key2,updatetime) values ('"+bookshelfinfoEntity.getLib_id()+"','bookshelfinfo','"+bookshelfinfoEntity.getShelf_id()+"','"+bookshelfinfoEntity.getInfo_type()+"',SYSDATE())");
		}catch(Exception e){
			try{
				sqlSessionTemplate.update("libraryInfoCommon.superUpdate", "update delete_rec set updatetime = SYSDATE() where lib_id = '"+bookshelfinfoEntity.getLib_id()+"' AND table_name = 'bookshelfinfo' AND key1 = '"+bookshelfinfoEntity.getShelf_id()+"' AND key2 = '"+bookshelfinfoEntity.getInfo_type()+"'");
			}catch(Exception ex){
				//nothing
			}
		}
		
		return this.sqlSessionTemplate.delete("bookshelfinfo.delete", bookshelfinfoEntity);
	}

	@Override
	public int addBookshelfinfo(BookShelfInfoEntity bookshelfinfoEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("bookshelfinfo.add", bookshelfinfoEntity);
	}
}
