package com.ssitcloud.view.navigation.service;

import java.util.Map;

import com.ssitcloud.libraryinfo.entity.BookUnionEntity;
import com.ssitcloud.navigation.entity.NavigationInfoEntity;

public interface NavigationViewService {
	
	NavigationInfoEntity queryInfoByParam(String libId ,String shelflayer_barcode);
	
	BookUnionEntity queryBookInfo(String lib_idx ,String bookBarCode);
	
	Map<String,String> getLibIdx(String libId);
}
