package com.ssitcloud.view.node.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.node.entity.ContainerEntity;
import com.ssitcloud.view.node.entity.page.ContainerPageEntity;

public interface ContainerViewService {
	ResultEntity queryContainerByPage(String req);

	ContainerPageEntity queryContainerByParam(String req);

	ContainerEntity queryContainerById(String req);

	ResultEntity deleteContainerById(String req);

	ResultEntity updateContainer(String req);

	ResultEntity addContainer(String req);

	ResultEntity start(String req);

	ResultEntity stop(String req);
}
