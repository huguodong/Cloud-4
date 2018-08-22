package com.ssitcloud.view.database.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface ServerService {
	public abstract ResultEntity getUserMenus();
	public abstract ResultEntity getServerConfig();
	public abstract ResultEntity addDBConnect(String req);
	public abstract ResultEntity removeDBConnect(String req);
}
