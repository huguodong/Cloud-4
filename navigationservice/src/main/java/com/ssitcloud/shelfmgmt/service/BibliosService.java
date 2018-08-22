package com.ssitcloud.shelfmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface BibliosService {

	public abstract ResultEntity importMarcData(String req);
	
	public abstract ResultEntity queryMarcDataByItemCtrlNum(String req);
}
