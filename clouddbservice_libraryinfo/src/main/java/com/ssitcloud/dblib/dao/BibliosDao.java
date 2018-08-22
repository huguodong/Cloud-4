package com.ssitcloud.dblib.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dblib.entity.BibliosAndBook;
import com.ssitcloud.dblib.entity.BibliosEntity;
import com.ssitcloud.dblib.entity.page.BibliosPageEntity;

public interface BibliosDao {

	/**
	 * 插入一条记录
	 *
	 * <p>2017年2月7日 下午4:24:29 
	 * <p>create by hjc
	 * @param bibliosEntity
	 * @return
	 */
	public abstract int insertBiblios(BibliosEntity bibliosEntity);
	
	/**
	 * 批量插入数据
	 *
	 * <p>2017年11月9日 下午5:00:45 
	 * <p>create by hjc
	 * @param list
	 * @return
	 */
	public abstract int insertBibliosBatch(List<BibliosEntity> list);
	
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月7日 下午4:24:33 
	 * <p>create by hjc
	 * @param bibliosEntity
	 * @return
	 */
	public abstract int deleteBiblios(BibliosEntity bibliosEntity);
	
	/**
	 * 更新记录
	 *
	 * <p>2017年2月7日 下午4:24:36 
	 * <p>create by hjc
	 * @param bibliosEntity
	 * @return
	 */
	public abstract int updateBiblios(BibliosEntity bibliosEntity);
	
	/**
	 * 批量执行更新语句
	 *
	 * <p>2017年11月10日 上午9:09:51 
	 * <p>create by hjc
	 * @param list
	 * @return
	 */
	public abstract int updateBibliosBatch(List<BibliosEntity> list); 
	
	/**
	 * 查询单条记录
	 *
	 * <p>2017年2月7日 下午4:24:40 
	 * <p>create by hjc
	 * @param bibliosEntity
	 * @return
	 */
	public abstract BibliosEntity queryBiblios(BibliosEntity bibliosEntity);
	
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月7日 下午4:24:43 
	 * <p>create by hjc
	 * @param bibliosEntity
	 * @return
	 */
	public abstract List<BibliosEntity> queryBibliosList(BibliosEntity bibliosEntity);
	
	/**
	 * 分页查询
	 *
	 * <p>2017年2月9日 上午10:44:16 
	 * <p>create by hjc
	 * @param bibliosPageEntity
	 * @return
	 */
	public abstract BibliosPageEntity queryBibliosListByPage(BibliosPageEntity bibliosPageEntity);
	
	public abstract List<BibliosEntity> queryAllBiblios();
	
	public abstract BibliosEntity queryBibliosByISBN(BibliosEntity biblios);
	
	public abstract BibliosEntity queryBibliosByTitleAndAuthor(BibliosEntity biblios);
	
	public abstract int updateBibliosByISBN(BibliosEntity biblios);
	
	public abstract int deleteBibliosById(List<BibliosEntity> list);
	
	public abstract int addBiblios(BibliosEntity biblios);
	
	/**
	 * 通过lib_id和book_barcode查询图书信息
	 * @param param
	 * @return
	 */
	BibliosEntity queryBibliosForBCAndLib(Map<String, Object> param);
	
	/**
	 * 根据isbn不同形态查询是不是有这本书
	 *
	 * <p>2017年9月1日 下午5:03:22 
	 * <p>create by hjc
	 * @param biblios
	 * @return
	 */
	public abstract BibliosEntity queryBibliosByIsbnMultiCondition(BibliosEntity biblios);
	
	/**
	 * 根据标题 作者题名查询是不是有这本书
	 *
	 * <p>2017年9月1日 下午5:37:03 
	 * <p>create by hjc
	 * @param biblios
	 * @return
	 */
	public abstract BibliosEntity queryBibliosByTitleAuthorPublish(BibliosEntity biblios);
	
	public abstract BibliosAndBook insertBiblios2(BibliosAndBook bibliosEntity);
}
