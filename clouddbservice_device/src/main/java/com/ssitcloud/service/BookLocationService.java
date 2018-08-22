package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.BookLocationEntity;

public interface BookLocationService {
	
	/**
	 * 图书馆藏地BookLocationEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param bookLocationEntity
	 * @return
	 */
	public abstract int insertBookLocation(BookLocationEntity bookLocationEntity);
	
	/**
	 * 图书馆藏地BookLocationEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param bookLocationEntity
	 * @return
	 */
	public abstract int updateBookLocation(BookLocationEntity bookLocationEntity);
	
	/**
	 * 图书馆藏地BookLocationEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param bookLocationEntity
	 * @return
	 */
	public abstract int deleteBookLocation(BookLocationEntity bookLocationEntity);
	
	/**
	 * 图书馆藏地BookLocationEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param bookLocationEntity
	 * @return
	 */
	public abstract BookLocationEntity queryOneBookLocation(BookLocationEntity bookLocationEntity);
	
	/**
	 * 图书馆藏地BookLocationEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param bookLocationEntity
	 * @return
	 */
	public abstract List<BookLocationEntity> queryBookLocations(BookLocationEntity bookLocationEntity);


}
