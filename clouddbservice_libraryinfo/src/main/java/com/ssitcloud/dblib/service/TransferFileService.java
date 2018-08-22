package com.ssitcloud.dblib.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;



public interface TransferFileService {
	public ResultEntity transfer(Map<String,String> map);
	public ResultEntity notifyLibraryInfo(String req);
}
