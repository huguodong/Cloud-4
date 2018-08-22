package com.ssitcloud.view.readermgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.readermgmt.service.ReaderConfigService;

@Service
public class ReaderConfigServiceImpl extends BasicServiceImpl implements ReaderConfigService{
	private static final String URL_selectImportConfig="selectImportConfig";//
	@Override
	public ResultEntity queryOneImportConfig(String req) {
		return postUrl(URL_selectImportConfig, req);
	}
}
