package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.entity.ReaderTypeEntity;




/**
 * 
 * @package com.ssitcloud.dao
 * @comment
 * @data 2016年4月23日
 * @author hwl
 */
public interface ReaderTypeDao {

	public  int insert(ReaderTypeEntity readerTypeEntity);

	public  int delete(ReaderTypeEntity readerTypeEntity);

	public  int update(ReaderTypeEntity readerTypeEntity);

	public  List<ReaderTypeEntity> select(
			ReaderTypeEntity readerTypeEntity);
	
	public  PageEntity selectByPage(Map<String, String> map);
	
	public  PageEntity selectByFuzzy(Map<String, String> map);
	/**
	 * 根据图书管IDX查询读者类型信息
	 * @param readerTypeEntity
	 * @return
	 */
	public List<ReaderTypeEntity> selectByLibIdx(
			ReaderTypeEntity readerTypeEntity);
	
	/**
	 *根据与图书馆ID获取图书馆的维护卡信息
	 *
	 * <p>2016年7月14日 下午2:21:17 
	 * <p>create by hjc
	 * @param map
	 * @return
	 */
	public List<ReaderTypeEntity> selMaintenaceCard(Map<String, Object> map);

	public List<ReaderTypeEntity> selectReaderTypeByTypeId(ReaderTypeEntity readerType);
	/**
	 * 
	 * @param readerType
	 * @return
	 */
	List<ReaderTypeEntity> selectCardByTypeId(ReaderTypeEntity readerType);
	/**
	 * 根据 card ID和 图书馆IDX 维护卡
	 * @param readerType
	 * @return
	 */
	public List<ReaderTypeEntity> selectCardByTypeIdAndLibIdx(ReaderTypeEntity readerType);
	/**
	 * 根据  typeID和 图书馆IDX查询流通类型
	 * @param readerType
	 * @return
	 */
	List<ReaderTypeEntity> selectReaderTypeByTypeIdAndLibIdx(ReaderTypeEntity readerType);
	/**
	 * 
	 * @param readerTypeEntity
	 * @return
	 */
	int deleteByCon(ReaderTypeEntity readerTypeEntity);
}
