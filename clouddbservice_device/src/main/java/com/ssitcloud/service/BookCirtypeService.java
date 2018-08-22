package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.BookCirtypeEntity;

public interface BookCirtypeService {
	
	/**
	 * 图书流通类型BookCirtypeEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param bookCirtypeEntity
	 * @return
	 */
	public abstract int insertBookCirtype(BookCirtypeEntity bookCirtypeEntity);
	
	/**
	 * 图书流通类型BookCirtypeEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param bookCirtypeEntity
	 * @return
	 */
	public abstract int updateBookCirtype(BookCirtypeEntity bookCirtypeEntity);
	
	/**
	 * 图书流通类型BookCirtypeEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param bookCirtypeEntity
	 * @return
	 */
	public abstract int deleteBookCirtype(BookCirtypeEntity bookCirtypeEntity);
	
	/**
	 * 图书流通类型BookCirtypeEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param bookCirtypeEntity
	 * @return
	 */
	public abstract BookCirtypeEntity queryOneBookCirtype(BookCirtypeEntity bookCirtypeEntity);
	
	/**
	 * 图书流通类型BookCirtypeEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param bookCirtypeEntity
	 * @return
	 */
	public abstract List<BookCirtypeEntity> queryBookCirtypes(BookCirtypeEntity bookCirtypeEntity);
	

}
