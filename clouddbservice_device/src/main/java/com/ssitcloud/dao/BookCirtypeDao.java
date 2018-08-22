package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.BookCirtypeEntity;

public interface BookCirtypeDao {
	/**
	 * 图书流通类型BookCirtypeEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param bookCirtypeEntity
	 * @return
	 */
	public abstract int insertBookCirtype(BookCirtypeEntity bookCirtypeEntity);
	
	/**
	 * 图书流通类型BookCirtypeEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param bookCirtypeEntity
	 * @return
	 */
	public abstract int updateBookCirtype(BookCirtypeEntity bookCirtypeEntity);
	
	/**
	 * 图书流通类型BookCirtypeEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param bookCirtypeEntity
	 * @return
	 */
	public abstract int deleteBookCirtype(BookCirtypeEntity bookCirtypeEntity);
	
	/**
	 * 图书流通类型BookCirtypeEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param bookCirtypeEntity
	 * @return
	 */
	public abstract BookCirtypeEntity queryOneBookCirtype(BookCirtypeEntity bookCirtypeEntity);
	
	/**
	 * 图书流通类型BookCirtypeEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param bookCirtypeEntity
	 * @return
	 */
	public abstract List<BookCirtypeEntity> queryBookCirtypes(BookCirtypeEntity bookCirtypeEntity);
	

}
