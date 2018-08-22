package com.ssitcloud.business.nodemgmt.dao;

import java.util.List;

import com.ssitcloud.node.entity.ContainerEntity;
import com.ssitcloud.node.entity.page.ContainerPageEntity;


public interface ContainerDao {
	public abstract List<ContainerEntity> queryContainerByPage(ContainerEntity container);

	public abstract ContainerPageEntity queryContainerByParam(ContainerPageEntity container);

	public abstract ContainerEntity queryContainerById(int container_idx);

	public abstract int deleteContainerById(List<Integer> container_idxs);

	public abstract int updateContainer(ContainerEntity container);

	public abstract int addContainer(ContainerEntity container);

}