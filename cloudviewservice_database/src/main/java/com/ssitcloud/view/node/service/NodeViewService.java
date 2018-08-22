package com.ssitcloud.view.node.service;

/***********************************************************************
 * Module:  NodeViewService.java
 * Author:  dell
 * Purpose: Defines the Interface NodeViewService
 ***********************************************************************/

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.node.entity.NodeEntity;
import com.ssitcloud.view.node.entity.page.NodePageEntity;

public interface NodeViewService {
	ResultEntity queryNodeByPage(String req);

	NodePageEntity queryNodeByParam(String req);

	NodeEntity queryNodeById(String req);

	ResultEntity deleteNodeById(String req);

	ResultEntity updateNode(String req);

	ResultEntity addNode(String req);

	ResultEntity getLibList(String req);

}
