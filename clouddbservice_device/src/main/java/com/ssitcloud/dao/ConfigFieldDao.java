package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.ConfigFieldEntity;
import com.ssitcloud.entity.ImportConfigEntity;
import com.ssitcloud.entity.ImportTemplateEntity;
import com.ssitcloud.entity.MainfieldEntity;
import com.ssitcloud.entity.StatisticsConfigEntity;
import com.ssitcloud.entity.StatisticsTemplateEntity;
import com.ssitcloud.entity.page.ImportTemplatePageEntity;
import com.ssitcloud.entity.page.StatisticsTemplatePageEntity;

public interface ConfigFieldDao {
	public abstract List<Map<String, Object>> listConfigFieldCollection();

	public abstract List<ConfigFieldEntity> selectConfigFieldList(ConfigFieldEntity configFieldEntity);

	public abstract int insertImportConfig(ImportConfigEntity importConfigEntity);
	
	public abstract int insertImportTemplate(ImportTemplateEntity importTemplateEntity);
	//分页查询导入模板
	public abstract ImportTemplatePageEntity selectAllImportTemplatePage(ImportTemplatePageEntity importTemplatePageEntity);
	//查询导入模板
	public abstract ImportTemplatePageEntity selectImportTemplatePage(ImportTemplatePageEntity importTemplatePageEntity);
	//////////////////
	public abstract ImportConfigEntity queryOneImportConfig(ImportConfigEntity importConfigEntity);
	
	public abstract int updateImportTemplate(ImportTemplateEntity importTemplateEntity);
	
	public abstract int updateImportConfig(ImportConfigEntity importConfigEntity);
	
	public abstract int deleteImportTemplate(ImportTemplateEntity importTemplateEntity);
	
	public abstract int deleteImportConfig(ImportConfigEntity importConfigEntity);
}
