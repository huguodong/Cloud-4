package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.RelOperatorDeviceGroupEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月5日
 */
public interface RelOperatorDeviceGroupDao extends CommonDao {
	public abstract int insert(
			RelOperatorDeviceGroupEntity relOperatorDeviceGroupEntity);

	public abstract int delete(
			RelOperatorDeviceGroupEntity relOperatorDeviceGroupEntity);

	public abstract int update(
			RelOperatorDeviceGroupEntity relOperatorDeviceGroupEntity);

	public abstract List<RelOperatorDeviceGroupEntity> selectByidx(
			RelOperatorDeviceGroupEntity relOperatorDeviceGroupEntity);

	public abstract List<RelOperatorDeviceGroupEntity> selectAll();

	int deleteByOperGroupIdx(
			RelOperatorDeviceGroupEntity relOperatorDeviceGroupEntity);
	
	/**
	 * 查询图书馆的用户组信息，以及对应用户组所拥有的权限
	 *
	 * <p>2016年6月23日 下午7:37:23 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract List<Map<String, Object>> queryLibraryServiceGroup(Map<String, String> param);
	/**
	 * 
	 * @param idxList
	 * @return
	 */
	public abstract List<RelOperatorDeviceGroupEntity> selectByIdxArr(
			List<Integer> idxList);

	List<RelOperatorDeviceGroupEntity> selectByDeviceGroupLibraryIdx(
			Map<String, Object> map);	
}
