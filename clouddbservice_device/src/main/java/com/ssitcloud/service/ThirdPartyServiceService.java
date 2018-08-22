package com.ssitcloud.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface ThirdPartyServiceService {

	public ResultEntity queryThirdPartyServiceByParams(String req);

	public ResultEntity deleteThirdPartyService(String req);

	public ResultEntity queryThirdPartyServiceByPage(String req);

	public ResultEntity editThirdPartyService(String req);
	
	public ResultEntity deleteDisplayInfo(String req);
	
	public ResultEntity editDisplayInfo(String req);

	public ResultEntity queryDisplayInfo(String req);
	
	public ResultEntity queryDisplayInfoList(String req);

}
