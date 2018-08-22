package com.ssitcloud.view.interfaceconfig.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface JsonDataService {
	public ResultEntity getDevTypeData(String req);

	public ResultEntity getLibDevData(String req);

	public ResultEntity getData(String req);

	public ResultEntity saveData(String req);

	public ResultEntity getInitData(String req);
}
