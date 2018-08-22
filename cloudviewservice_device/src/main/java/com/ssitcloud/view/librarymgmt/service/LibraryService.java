package com.ssitcloud.view.librarymgmt.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @comment 
 * @date 2016年5月24日
 * @author hwl
 */
public interface LibraryService {

	String selectLibrary(Map<String, String> map);
	
	String addLibrary(Map<String,String> map);
	
	ResultEntity deleteLibrary(String req);
	
	String updateLibrary(Map<String, String> map);
	
	String selectLibInfoType(Map<String, String> map);
	
	String selectMasterlib(Map<String, String> map);
	
	String queryMasterSubRelations(Map<String, String> map);
	
	String selectLibInfoByParam(Map<String, String> map);
	
	String querylibInfoByCurUserEXT1(Map<String,String> map);
	
	ResultEntity saveLibPosition(String req);
	
	String selLibraryByIdxOrId(Map<String, String> map);
	
	ResultEntity selActualLibraryMaster(String req);
}
