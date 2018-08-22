package com.ssitcloud.view.service;


import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceService {
	public abstract ResultEntity insertFileUploadFlag(String req);
	public abstract String getUrl(String urlId);
}
