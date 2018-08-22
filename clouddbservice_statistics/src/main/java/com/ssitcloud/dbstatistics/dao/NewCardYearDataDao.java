package com.ssitcloud.dbstatistics.dao;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.NewCardYearDataEntity;

public interface NewCardYearDataDao {
	
	/**
	 * 办证查询按年统计NewCardYearDataEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午2:14:04
	 * @param newCardYearDataEntity
	 * @return
	 */
	public abstract int insertNewCardYearData(NewCardYearDataEntity newCardYearDataEntity);
	
	/**
	 * 办证查询按年统计NewCardYearDataEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午2:14:15
	 * @param newCardYearDataEntity
	 * @return
	 */
	public abstract int updateNewCardYearData(NewCardYearDataEntity newCardYearDataEntity);
	
	/**
	 * 办证查询按年统计NewCardYearDataEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午2:14:28
	 * @param newCardYearDataEntity
	 * @return
	 */
	public abstract int deleteNewCardYearData(NewCardYearDataEntity newCardYearDataEntity);
	
	/**
	 * 办证查询按年统计NewCardYearDataEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:14:41
	 * @param newCardYearDataEntity
	 * @return
	 */
	public abstract NewCardYearDataEntity queryOneNewCardYearData(NewCardYearDataEntity newCardYearDataEntity);
	
	/**
	 * 办证查询按年统计NewCardYearDataEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:14:56
	 * @param newCardYearDataEntity
	 * @return
	 */
	public abstract List<NewCardYearDataEntity> queryNewCardYearDatas(NewCardYearDataEntity newCardYearDataEntity);
	
	/**
	 * 
	 *
	 * <p>2017年4月5日 下午9:57:53 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<NewCardYearDataEntity> getAllNewCardYear( );

}
