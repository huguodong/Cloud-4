package com.ssitcloud.business.interfaceconfig.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface JsonDataService {

	public ResultEntity getData(String req);

	public ResultEntity saveData(String req);

	public ResultEntity getInitData(String req);

	public ResultEntity getLibDevData(String req);

	public ResultEntity getDevTypeData(String req);
}
