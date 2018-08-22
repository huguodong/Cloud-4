package com.ssitcloud.view.librarymgmt.service;

import java.util.Map;
/**
 * 读者流通类型Controller
 * @comment 
 * @date 2016年5月17日
 * @author hwl
 */
public interface ReaderTypeService {

	String Selectreadertype(Map<String, String> map);
	
	String Updatereadertype(Map<String, String> map);
	
	String Insertreadertype(Map<String, String> map);
	
	String Deletereadertype(Map<String, String> map);
	
	String SelectreadertypeByFuzzy(Map<String, String> map);
}
