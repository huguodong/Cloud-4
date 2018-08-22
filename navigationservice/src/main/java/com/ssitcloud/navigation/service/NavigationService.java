package com.ssitcloud.navigation.service;

import com.ssitcloud.navigation.entity.NavigationInfoEntity;
import com.ssitcloud.shelfmgmt.entity.BookitemEntity;

public interface NavigationService {
	
	NavigationInfoEntity queryInfoByParam(BookitemEntity bookitemEntity);
	
	BookitemEntity queryBookInfo(String bookBaeCode);
	
}
