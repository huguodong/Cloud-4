package com.ssitcloud.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.ConfigFieldEntity;
import com.ssitcloud.entity.ImportConfigEntity;
import com.ssitcloud.entity.ImportTemplateEntity;
import com.ssitcloud.entity.MainfieldEntity;
import com.ssitcloud.entity.StatisticsConfigEntity;
import com.ssitcloud.entity.StatisticsTemplateEntity;

public interface ConfigFieldService {
	public abstract ResultEntity listConfigFieldCollection();
	
	public abstract ResultEntity selectConfigFieldList(ConfigFieldEntity configFieldEntity);
	
	public abstract int insertImportTemplate(ImportTemplateEntity importTemplateEntity);
	
	public abstract int insertImportConfig(ImportConfigEntity importConfigEntity);
	
	public abstract ResultEntity selectImportTemplatePage(String req);
	
	public abstract ImportConfigEntity queryOneImportConfig(ImportConfigEntity importConfigEntity);
	
	public abstract int updateImportTemplate(ImportTemplateEntity importTemplateEntity);
	
	public abstract int updateImportConfig(ImportConfigEntity importConfigEntity);
	
	public abstract int deleteImportTemplate(ImportTemplateEntity importTemplateEntity);
	
	public abstract int deleteImportConfig(ImportConfigEntity importConfigEntity);
}
