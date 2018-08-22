package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.BookMediatypeEntity;

public interface BookMediatypeDao {
	/**
	 * 邮件服务器配置BookMediatypeEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param bookMediatypeEntity
	 * @return
	 */
	public abstract int insertBookMediatype(BookMediatypeEntity bookMediatypeEntity);
	
	/**
	 * 邮件服务器配置BookMediatypeEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param bookMediatypeEntity
	 * @return
	 */
	public abstract int updateBookMediatype(BookMediatypeEntity bookMediatypeEntity);
	
	/**
	 * 邮件服务器配置BookMediatypeEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param bookMediatypeEntity
	 * @return
	 */
	public abstract int deleteBookMediatype(BookMediatypeEntity bookMediatypeEntity);
	
	/**
	 * 邮件服务器配置BookMediatypeEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param bookMediatypeEntity
	 * @return
	 */
	public abstract BookMediatypeEntity queryOneBookMediatype(BookMediatypeEntity bookMediatypeEntity);
	
	/**
	 * 邮件服务器配置BookMediatypeEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param bookMediatypeEntity
	 * @return
	 */
	public abstract List<BookMediatypeEntity> queryBookMediatypes(BookMediatypeEntity bookMediatypeEntity);
	

}
