package com.ssitcloud.shelfmgmt.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.shelfmgmt.dao.BookshelfinfoDao;
import com.ssitcloud.shelfmgmt.entity.BookshelfinfoEntity;

@Repository
public class BookshelfinfoDaoImpl extends CommonDaoImpl implements BookshelfinfoDao {

	@Override
	public List<BookshelfinfoEntity> queryAllBookshelfinfo() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("bookshelfinfo.select");
	}
	
	@Override
	public List<BookshelfinfoEntity> queryBookshelfinfoById(BookshelfinfoEntity bookshelfinfoEntity){
		
		return this.sqlSessionTemplate.selectList("bookshelfinfo.selectById",bookshelfinfoEntity);
	}

	@Override
	public int updateBookshelfinfo(BookshelfinfoEntity bookshelfinfoEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("bookshelfinfo.update", bookshelfinfoEntity);
	}

	@Override
	public int deleteBookshelfinfo(BookshelfinfoEntity bookshelfinfoEntity) {
		try{
			if(bookshelfinfoEntity.getShelf_id() != null && bookshelfinfoEntity.getInfo_type() > 0)
				sqlSessionTemplate.insert("libraryInfoCommon.superInsert", "insert into delete_rec(table_name,key1,key2,updatetime) values ('bookshelfinfo','"+bookshelfinfoEntity.getShelf_id()+"','"+bookshelfinfoEntity.getInfo_type()+"',SYSDATE())");
		}catch(Exception e){
			try{
				sqlSessionTemplate.update("libraryInfoCommon.superUpdate", "update delete_rec set updatetime = SYSDATE() where table_name = 'bookshelfinfo' AND key1 = '"+bookshelfinfoEntity.getShelf_id()+"' AND key2 = '"+bookshelfinfoEntity.getInfo_type()+"'");
			}catch(Exception ex){
				//nothing
			}
		}
		
		return this.sqlSessionTemplate.delete("bookshelfinfo.delete", bookshelfinfoEntity);
	}

	@Override
	public int addBookshelfinfo(BookshelfinfoEntity bookshelfinfoEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("bookshelfinfo.add", bookshelfinfoEntity);
	}

}
