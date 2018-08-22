package com.ssitcloud.view.readermgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface ConfigFieldService {
	ResultEntity takeDataSource(String req);
	
	ResultEntity selectConfigFieldList(String req);
	
	//添加导入配置模板
	ResultEntity addConfigTemplate(String req);
	
	//分页查询导入配置模板
	ResultEntity selectConfigTemplatePage(String req);
	//更新统计模板
	ResultEntity updateImportTemplate(String req);
	
	ResultEntity deleteImportTemplate(String req);
	public ResultEntity selectImportConfig(String req);
}
