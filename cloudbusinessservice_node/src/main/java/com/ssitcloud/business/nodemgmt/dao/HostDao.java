package com.ssitcloud.business.nodemgmt.dao;

import java.util.List;

import com.ssitcloud.node.entity.HostEntity;
import com.ssitcloud.node.entity.page.HostPageEntity;


public interface HostDao {
	public abstract List<HostEntity> queryHostByPage(HostEntity host);

	public abstract HostPageEntity queryHostByParam(HostPageEntity host);

	public abstract HostEntity queryHostById(int host_idx);

	public abstract int deleteHostById(List<Integer> host_idxs);

	public abstract int updateHost(HostEntity host);

	public abstract int addHost(HostEntity host);

}