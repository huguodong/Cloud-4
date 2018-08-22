package com.ssitcloud.view.librarymgmt.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @comment 
 * @date 2016年5月20日
 * @author hwl
 */
public interface LibraryTempService {

	String selectLibraryTemp(Map<String, String> map);
	
	String addLibraryTemp(Map<String, String> map);
	
	ResultEntity updateLibraryTemp(String req);
	
	ResultEntity deleteLibraryTemp(String req);
	
	String selectAllTemp(String info);

	ResultEntity selLibraryServiceTemplateByIdx(String req);
	
}
