package com.ssitcloud.shelfmgmt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.shelfmgmt.dao.BookitemDao;
import com.ssitcloud.shelfmgmt.entity.BookitemEntity;

@Repository
public class BookitemDaoImpl extends CommonDaoImpl implements BookitemDao {

	@Override
	public List<BookitemEntity> queryAllBookitem(BookitemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("bookitem.select",bookitem);
	}

	@Override
	public BookitemEntity queryBookitemByCode(BookitemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("bookitem.selectByCode",bookitem);
	}

	@Override
	public int updateBookitem(BookitemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("bookitem.update",bookitem);
	}

	@Override
	public int deleteBookitemById(List<BookitemEntity> list) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("bookitem.delete",list);
	}

	@Override
	public int addBookitem(BookitemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("bookitem.add",bookitem);
	}

}
