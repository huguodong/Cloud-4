package com.ssitcloud.business.nodemgmt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.business.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.business.nodemgmt.dao.HostDao;
import com.ssitcloud.node.entity.HostEntity;
import com.ssitcloud.node.entity.page.HostPageEntity;


@Repository
public class HostDaoImpl extends CommonDaoImpl implements HostDao {

	@Override
	public List<HostEntity> queryHostByPage(HostEntity host) {
		return this.sqlSessionTemplate.selectList("host.queryHostByPage", host);
	}

	@Override
	public HostPageEntity queryHostByParam(HostPageEntity entity) {
		HostPageEntity total = this.sqlSessionTemplate.selectOne("host.queryHostByParam", entity);
		entity.setTotal(total.getTotal());
		entity.setDoAount(false);
		entity.setRows(this.sqlSessionTemplate.selectList("host.queryHostByParam", entity));
		return entity;
	}

	@Override
	public HostEntity queryHostById(int host_idx) {
		return this.sqlSessionTemplate.selectOne("host.queryHostById", host_idx);
	}

	@Override
	public int deleteHostById(List<Integer> host_idxs) {
		return this.sqlSessionTemplate.delete("host.deleteHostById", host_idxs);
	}

	@Override
	public int updateHost(HostEntity host) {
		return this.sqlSessionTemplate.update("host.updateHost", host);
	}

	@Override
	public int addHost(HostEntity host) {
		return this.sqlSessionTemplate.insert("host.addHost", host);
	}

}
