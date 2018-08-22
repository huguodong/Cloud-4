package com.ssitcloud.view.node.service;

/***********************************************************************
 * Module:  NodeTypeViewService.java
 * Author:  dell
 * Purpose: Defines the Interface NodeTypeViewService
 ***********************************************************************/

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.node.entity.NodeTypeEntity;
import com.ssitcloud.view.node.entity.page.NodeTypePageEntity;

public interface NodeTypeViewService {
	ResultEntity queryNodeTypeByPage(String req);

	NodeTypePageEntity queryNodeTypeByParam(String req);

	NodeTypeEntity queryNodeTypeById(String req);

	ResultEntity deleteNodeTypeById(String req);

	ResultEntity updateNodeType(String req);

	ResultEntity addNodeType(String req);

}