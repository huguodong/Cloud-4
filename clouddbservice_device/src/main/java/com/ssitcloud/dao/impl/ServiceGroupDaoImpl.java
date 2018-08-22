package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.ServiceGroupDao;
import com.ssitcloud.entity.ServiceGroupEntity;

/**
 * @comment 实现业务组表的增删改查。表：service_group
 * @author hwl
 * 
 * @data 2016年4月5日
 */
@Repository
public class ServiceGroupDaoImpl extends CommonDaoImpl implements
		ServiceGroupDao {

	public int insert(ServiceGroupEntity servicegroupEntity) {
		return this.sqlSessionTemplate.insert("servicegroup.insert",
				servicegroupEntity);
	}

	public int update(ServiceGroupEntity servicegroupEntity) {
		return this.sqlSessionTemplate.update("servicegroup.update",
				servicegroupEntity);
	}

	public int delete(ServiceGroupEntity servicegroupEntity) {
		return this.sqlSessionTemplate.delete("servicegroup.delete",
				servicegroupEntity);
	}

	public List<ServiceGroupEntity> selectByidx(
			ServiceGroupEntity servicegroupEntity) {
		return this.sqlSessionTemplate.selectList("servicegroup.selectByidx",
				servicegroupEntity);
	}

	public List<ServiceGroupEntity> selectAll() {
		return this.sqlSessionTemplate.selectList("servicegroup.selectAll");
	}
}
