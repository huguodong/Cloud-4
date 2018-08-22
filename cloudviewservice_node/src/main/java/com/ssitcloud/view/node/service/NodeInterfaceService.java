package com.ssitcloud.view.node.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface NodeInterfaceService {
	
	public ResultEntity addNodeInterface(String req);
	
	public ResultEntity queryNodeInterfaceByPage(String req);

	public ResultEntity deleteNodeInterface(String req);

	public String queryInterfaceByNodeName(String req);
	
	public ResultEntity editNodeInterface(String req);
	
	public ResultEntity clearNodeCache(String req);
	
	public ResultEntity queryPreNodesByPage(String req);
}
