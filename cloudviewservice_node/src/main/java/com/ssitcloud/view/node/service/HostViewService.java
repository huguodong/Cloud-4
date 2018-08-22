package com.ssitcloud.view.node.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.node.entity.HostEntity;
import com.ssitcloud.view.node.entity.page.HostPageEntity;

public interface HostViewService {
	List<HostEntity> queryHostByPage(String req);

	HostPageEntity queryHostByParam(String req);

	HostEntity queryHostById(String req);

	ResultEntity deleteHostById(String req);

	ResultEntity updateHost(String req);

	ResultEntity addHost(String req);
}
