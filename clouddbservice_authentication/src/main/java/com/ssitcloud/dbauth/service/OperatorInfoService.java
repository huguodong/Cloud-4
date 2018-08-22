package com.ssitcloud.dbauth.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dbauth.entity.OperatorInfoEntity;

/**
 * <p>2016年4月5日 下午1:33:59
 * @author hjc
 *
 */
public interface OperatorInfoService {
	
	/**
	 * 新增操作员的一条详细信息
	 * 
	 * <p>2016年4月5日 下午6:28:30
	 * <p>create by hjc
	 * @param operatorInfoEntity 操作员详细信息实体类
	 * @return 返回数据库操作结果
	 */
	public abstract int addOperatorInfo(OperatorInfoEntity operatorInfoEntity);
	
	/**
	 * 通过map集合查询符合条件的记录
	 * author huanghuang
	 * 2017年3月13日 下午5:13:44
	 * @param params
	 * @return
	 */
	List<OperatorInfoEntity> selectOperatorInfos(Map<String,Object> params);
}
