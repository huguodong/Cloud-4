package com.ssitcloud.business.readermgmt.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.ResultEntity;

public interface ReaderService {
	
	public ResultEntity uploadReaderCard(CommonsMultipartFile commonsMultipartFile,String req);

	public ResultEntity uploadBiblios(CommonsMultipartFile commonsMultipartFile,String req);
}
