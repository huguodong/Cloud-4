package com.ssitcloud.business.shelfmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface MetadataBookshelfService {

	
	ResultEntity queryAllMetadataBookshelf(String req);
	
	ResultEntity updateMetadataBookshelf(String req);
	
	ResultEntity deleteMetadataBookshelf(String req);
	
	ResultEntity addMetadataBookshelf(String req);
}
