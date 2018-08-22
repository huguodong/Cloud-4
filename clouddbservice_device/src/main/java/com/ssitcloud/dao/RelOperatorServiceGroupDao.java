package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.RelOperatorServiceGroupEntity;

/**
 * @comment
 * @author hwl
 * 
 * @data 2016-3-29下午2:27:18
 * 
 */
public interface RelOperatorServiceGroupDao extends CommonDao {
	public abstract int insert(
			RelOperatorServiceGroupEntity relOperatorServiceGroupEntity);

	public abstract int delete(
			RelOperatorServiceGroupEntity relOperatorServiceGroupEntity);

	public abstract int update(
			RelOperatorServiceGroupEntity relOperatorServiceGroupEntity);

	public abstract List<RelOperatorServiceGroupEntity> selectByidx(
			RelOperatorServiceGroupEntity relOperatorServiceGroupEntity);

	public abstract List<RelOperatorServiceGroupEntity> selectAll();

	int deleteByOperGroupIdx(
			RelOperatorServiceGroupEntity relOperatorServiceGroupEntity);

	public abstract List<RelOperatorServiceGroupEntity> selectByServGroupByLibraryIdx(
			Map<String, Object> map);

	List<RelOperatorServiceGroupEntity> selectByOperGroupByLibraryIdx(
			Map<String, Object> map);

}
