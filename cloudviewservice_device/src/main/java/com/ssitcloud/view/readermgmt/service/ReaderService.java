package com.ssitcloud.view.readermgmt.service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.ResultEntity;

public interface ReaderService {
	
	public ResultEntity uploadReaderCard(CommonsMultipartFile commonsMultipartFile,String req)throws Exception;

	public ResultEntity uploadBiblios(CommonsMultipartFile commonsMultipartFile,String req)throws Exception;
}
