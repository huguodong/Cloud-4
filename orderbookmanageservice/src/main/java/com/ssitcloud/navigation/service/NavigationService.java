package com.ssitcloud.navigation.service;

import com.ssitcloud.navigation.entity.NavigationInfoEntity;

public interface NavigationService {
	
	NavigationInfoEntity queryInfoByParam(String bookBarCode);
	
}
