package com.ssitcloud.shelfmgmt.dao.impl;

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
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("bookshelfinfo.delete", bookshelfinfoEntity);
	}

	@Override
	public int addBookshelfinfo(BookshelfinfoEntity bookshelfinfoEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("bookshelfinfo.add", bookshelfinfoEntity);
	}

}
