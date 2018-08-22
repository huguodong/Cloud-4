package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.RelDeviceGroupEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public interface RelDeviceGroupDao extends CommonDao {

	public abstract int insert(RelDeviceGroupEntity relDeviceGroupEntity);

	public abstract int update(RelDeviceGroupEntity relDeviceGroupEntity);

	public abstract int delete(RelDeviceGroupEntity relDeviceGroupEntity);

	public abstract List<RelDeviceGroupEntity> selectByid(
			RelDeviceGroupEntity relDeviceGroupEntity);

	public abstract List<RelDeviceGroupEntity> selectAll();
	
	public abstract int deleteByDeviceIdx(Integer deviceIdx);
	
	/**
	 * 批量删除
	 *
	 * <p>2016年9月23日 上午9:58:58 
	 * <p>create by hjc
	 * @param deviceIdx
	 * @return
	 */
	public abstract int deleteByDeviceIdxs(Map<String, Object> param);
	/**
	 * 
	 * @param relDeviceGroupEntity
	 * @return
	 */
	public abstract int deleteByLibIdx(RelDeviceGroupEntity relDeviceGroupEntity);
}
