package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.BookMediatypeEntity;

public interface BookMediatypeService {
	
	/**
	 * 图书载体类型BookMediatypeEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param bookMediatypeEntity
	 * @return
	 */
	public abstract int insertBookMediatype(BookMediatypeEntity bookMediatypeEntity);
	
	/**
	 * 图书载体类型BookMediatypeEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param bookMediatypeEntity
	 * @return
	 */
	public abstract int updateBookMediatype(BookMediatypeEntity bookMediatypeEntity);
	
	/**
	 * 图书载体类型BookMediatypeEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param bookMediatypeEntity
	 * @return
	 */
	public abstract int deleteBookMediatype(BookMediatypeEntity bookMediatypeEntity);
	
	/**
	 * 图书载体类型BookMediatypeEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param bookMediatypeEntity
	 * @return
	 */
	public abstract BookMediatypeEntity queryOneBookMediatype(BookMediatypeEntity bookMediatypeEntity);
	
	/**
	 * 图书载体类型BookMediatypeEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param bookMediatypeEntity
	 * @return
	 */
	public abstract List<BookMediatypeEntity> queryBookMediatypes(BookMediatypeEntity bookMediatypeEntity);
	


}
