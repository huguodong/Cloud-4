package com.ssitcloud.business.readermgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface ConfigFieldService {
	ResultEntity takeDataSource(String req);
	
	ResultEntity selectConfigFieldList(String req);
	
	ResultEntity insertConfigTemplate(String req);
	
	ResultEntity selectConfigTemplatePage(String req);
	////////////////////////////////
	ResultEntity queryOneImportConfig(String req);
	
	ResultEntity updateImportTemplate(String req);
	
	ResultEntity deleteImportTemplate(String req);
}
