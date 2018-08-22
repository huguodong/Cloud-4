package com.ssitcloud.dblib.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.BookInputDao;
import com.ssitcloud.dblib.entity.BookInputEntity;
import com.ssitcloud.dblib.entity.page.BookInputPageEntity;

@Repository
public class BookInputDaoImpl extends CommonDaoImpl implements BookInputDao{

	@Override
	public int insertBookInput(BookInputEntity bookInputEntity) {
		return this.sqlSessionTemplate.insert("bookinput.insertBookInput",bookInputEntity);
	}

	@Override
	public int deleteBookInput(BookInputEntity bookInputEntity) {
		return this.sqlSessionTemplate.delete("bookinput.deleteBookInput",bookInputEntity);
	}

	@Override
	public int updateBookInput(BookInputEntity bookInputEntity) {
		return this.sqlSessionTemplate.update("bookinput.updateBookInput",bookInputEntity);
	}

	@Override
	public BookInputEntity queryBookInput(BookInputEntity bookInputEntity) {
		return this.sqlSessionTemplate.selectOne("bookinput.queryBookInput",bookInputEntity);
	}

	@Override
	public List<BookInputEntity> queryBookInputList(BookInputEntity bookInputEntity) {
		return this.sqlSessionTemplate.selectList("bookinput.queryBookInputList",bookInputEntity);
	}

	
	@Override
	public BookInputPageEntity queryBookInputListByPage(
			BookInputPageEntity bookinputPageEntity) {
		BookInputPageEntity t = this.sqlSessionTemplate.selectOne("bookinput.queryBookInputListByPage",bookinputPageEntity);
		bookinputPageEntity.setTotal(t.getTotal());
		bookinputPageEntity.setDoAount(false);
		List<BookInputPageEntity> list = this.sqlSessionTemplate.selectList("bookinput.queryBookInputListByPage",bookinputPageEntity);
		bookinputPageEntity.setRows(list);
		return bookinputPageEntity;
	}
	
	

	
}
