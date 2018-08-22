package com.ssitcloud.dbauth.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.OperatorInfoEntity;

/**
 * <p>2016年4月5日 下午1:34:58
 * @author hjc
 *
 */
public interface OperatorInfoDao extends CommonDao{

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
	 * 根据用户idx删除用户信息
	 *
	 * <p>2016年7月7日 上午8:59:41 
	 * <p>create by hjc
	 * @param operatorInfoEntity
	 * @return
	 */
	public abstract int deleteInfoByOperIdx(OperatorInfoEntity operatorInfoEntity);
	/**
	 * 通过map集合查询符合条件的
	 * author huanghuang
	 * 2017年3月13日 下午5:13:44
	 * @param params
	 * @return
	 */
	List<OperatorInfoEntity> selectOperatorInfos(Map<String,Object> params);
}
