package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.ReaderTypeEntity;


/**
 * 
 * @package com.ssitcloud.service
 * @comment
 * @data 2016年4月23日
 * @author hwl
 */
public interface ReaderTypeService {

	int insertReaderType(ReaderTypeEntity readerTypeEntity);
	
	int deleteReaderType(ReaderTypeEntity readerTypeEntity);
	
	int updateReaderType(ReaderTypeEntity readerTypeEntity);
	
	List<ReaderTypeEntity> selectReaderType(ReaderTypeEntity readerTypeEntity);
	
	PageEntity selectReaderTypeByPage(Map<String, String > map);
	
	PageEntity selectReaderTypeByFuzzy(Map<String, String > map);
	
	/**
	 * 根据图书馆ID查询图书馆维护卡信息
	 *
	 * <p>2016年7月14日 下午2:01:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	public abstract ResultEntity queryMaintenanceCard(String req);
	/**
	 * 根据图书馆操作员idx 查询操作员的维护卡信息
	 *
	 * <p>2016年7月14日 下午2:56:09 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryOperatorMaintenanceCard(String req);
	/**
	 * 修改操作员维护卡信息
	 *
	 * <p>2016年7月14日 下午4:57:38 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperatorMaintenanceCard(String req);
	/**
	 * 通过 typeid查询读者流通类型
	 * @param readerType
	 * @return
	 */
	List<ReaderTypeEntity> selectReaderTypeByTypeId(ReaderTypeEntity readerType);

	List<ReaderTypeEntity> selectCardByTypeId(ReaderTypeEntity readerType);
	/**
	 * 根据typeid 和 LibIdx 维护卡
	 * @param readerType
	 * @return
	 */
	List<ReaderTypeEntity> selectCardByTypeIdAndLibIdx(ReaderTypeEntity readerType);
	/**
	 * 根据typeid 和 LibIdx 读者流通类型
	 * @param readerType
	 * @return
	 */
	List<ReaderTypeEntity> selectReaderTypeByTypeIdAndLibIdx(ReaderTypeEntity readerType);
	/**
	 * 通过libidx查询读者流通类型
	 * author huanghuang
	 * 2017年3月7日 下午6:15:57
	 * @param readerType
	 * @return
	 */
	List<ReaderTypeEntity> selectByLibIdx(ReaderTypeEntity readerType);
}
