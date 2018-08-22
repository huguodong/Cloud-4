package com.ssitcloud.dblib.service;

import java.util.List;

import com.ssitcloud.dblib.entity.BookInputEntity;
import com.ssitcloud.dblib.entity.page.BookInputPageEntity;

public interface BookInputService {
	
	/**
	 * 插入一条记录
	 *
	 * <p>2017年2月7日 下午4:24:29 
	 * <p>create by hjc
	 * @param bookInputEntity
	 * @return
	 */
	public abstract int insertBookInput(BookInputEntity bookInputEntity);
	
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月7日 下午4:24:33 
	 * <p>create by hjc
	 * @param bookInputEntity
	 * @return
	 */
	public abstract int deleteBookInput(BookInputEntity bookInputEntity);
	
	/**
	 * 更新记录
	 *
	 * <p>2017年2月7日 下午4:24:36 
	 * <p>create by hjc
	 * @param bookInputEntity
	 * @return
	 */
	public abstract int updateBookInput(BookInputEntity bookInputEntity);
	
	/**
	 * 查询单条记录
	 *
	 * <p>2017年2月7日 下午4:24:40 
	 * <p>create by hjc
	 * @param bookInputEntity
	 * @return
	 */
	public abstract BookInputEntity queryBookInput(BookInputEntity bookInputEntity);
	
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月7日 下午4:24:43 
	 * <p>create by hjc
	 * @param bookInputEntity
	 * @return
	 */
	public abstract List<BookInputEntity> queryBookInputList(BookInputEntity bookInputEntity);
	
	/**
	 * 分页查询
	 *
	 * <p>2017年2月9日 上午10:44:16 
	 * <p>create by hjc
	 * @param bookinputPageEntity
	 * @return
	 */
	public abstract BookInputPageEntity queryBookInputListByPage(BookInputPageEntity bookinputPageEntity);

}
