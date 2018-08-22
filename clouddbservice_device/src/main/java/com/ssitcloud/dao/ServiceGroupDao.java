package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.ServiceGroupEntity;

/**
 * @comment
 * @author hwl
 * 
 * @data 2016年4月5日
 * 
 */

public interface ServiceGroupDao extends CommonDao {
	public abstract int insert(ServiceGroupEntity servicegroupEntity);

	public abstract int delete(ServiceGroupEntity servicegroupEntity);

	public abstract int update(ServiceGroupEntity serviceGroupEntity);

	public abstract List<ServiceGroupEntity> selectByidx(ServiceGroupEntity serviceGroupEntity);

	public abstract List<ServiceGroupEntity> selectAll();

}
