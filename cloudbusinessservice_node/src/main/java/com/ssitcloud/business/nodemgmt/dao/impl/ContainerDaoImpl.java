package com.ssitcloud.business.nodemgmt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.business.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.business.nodemgmt.dao.ContainerDao;
import com.ssitcloud.node.entity.ContainerEntity;
import com.ssitcloud.node.entity.page.ContainerPageEntity;


@Repository
public class ContainerDaoImpl extends CommonDaoImpl implements ContainerDao {

	@Override
	public List<ContainerEntity> queryContainerByPage(ContainerEntity container) {
		return this.sqlSessionTemplate.selectList("container.queryContainerByPage", container);
	}

	@Override
	public ContainerPageEntity queryContainerByParam(ContainerPageEntity container) {
		ContainerPageEntity total = this.sqlSessionTemplate.selectOne("container.queryContainerByParam", container);
		container.setTotal(total.getTotal());
		container.setDoAount(false);
		container.setRows(this.sqlSessionTemplate.selectList("container.queryContainerByParam", container));
		return container;
	}

	@Override
	public ContainerEntity queryContainerById(int container_idx) {
		return this.sqlSessionTemplate.selectOne("container.queryContainerById", container_idx);
	}

	@Override
	public int deleteContainerById(List<Integer> container_idxs) {
		return this.sqlSessionTemplate.delete("container.deleteContainerById", container_idxs);
	}

	@Override
	public int updateContainer(ContainerEntity container) {
		return this.sqlSessionTemplate.update("container.updateContainer", container);
	}

	@Override
	public int addContainer(ContainerEntity container) {
		return this.sqlSessionTemplate.insert("container.addContainer", container);
	}

}
