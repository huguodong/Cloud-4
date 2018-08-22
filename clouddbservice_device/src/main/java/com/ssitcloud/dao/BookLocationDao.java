package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.BookLocationEntity;

public interface BookLocationDao {
	/**
	 * 图书馆藏地BookLocationEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param bookLocationEntity
	 * @return
	 */
	public abstract int insertBookLocation(BookLocationEntity bookLocationEntity);
	
	/**
	 * 图书馆藏地BookLocationEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param bookLocationEntity
	 * @return
	 */
	public abstract int updateBookLocation(BookLocationEntity bookLocationEntity);
	
	/**
	 * 图书馆藏地BookLocationEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param bookLocationEntity
	 * @return
	 */
	public abstract int deleteBookLocation(BookLocationEntity bookLocationEntity);
	
	/**
	 * 图书馆藏地BookLocationEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param bookLocationEntity
	 * @return
	 */
	public abstract BookLocationEntity queryOneBookLocation(BookLocationEntity bookLocationEntity);
	
	/**
	 * 图书馆藏地BookLocationEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param bookLocationEntity
	 * @return
	 */
	public abstract List<BookLocationEntity> queryBookLocations(BookLocationEntity bookLocationEntity);
	

}
