package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.ConfigFieldDao;
import com.ssitcloud.entity.ConfigFieldEntity;
import com.ssitcloud.entity.ImportConfigEntity;
import com.ssitcloud.entity.ImportTemplateEntity;
import com.ssitcloud.entity.StatisticsConfigEntity;
import com.ssitcloud.entity.StatisticsTemplateEntity;
import com.ssitcloud.entity.page.ImportTemplatePageEntity;
import com.ssitcloud.entity.page.StatisticsTemplatePageEntity;
@Repository
public class ConfigFieldDaoImpl extends CommonDaoImpl implements ConfigFieldDao {
	@Override
	public List<Map<String, Object>> listConfigFieldCollection() {
		return getSqlSession().selectList("configfield.listConfigFieldCollection");
	}
	
	@Override
	public List<ConfigFieldEntity> selectConfigFieldList(
			ConfigFieldEntity configFieldEntity) {
		return getSqlSession().selectList("configfield.selectConfigFieldList",configFieldEntity);
	}

	@Override
	public int insertImportTemplate(ImportTemplateEntity importTemplateEntity) {
		return this.sqlSessionTemplate.insert("configfield.insertImportTemplate", importTemplateEntity);
	}
	
	@Override
	public int insertImportConfig(ImportConfigEntity importConfigEntity) {
		return this.sqlSessionTemplate.insert("configfield.insertImportConfig", importConfigEntity);
	}
	
	@Override
	public ImportTemplatePageEntity selectAllImportTemplatePage(
			ImportTemplatePageEntity importTemplatePageEntity) {
		if(null == importTemplatePageEntity) importTemplatePageEntity =new ImportTemplatePageEntity();
		if(importTemplatePageEntity.isWhetherPaging()){
			ImportTemplatePageEntity total = getSqlSession().selectOne("configfield.selectAllImportTemplatePage", importTemplatePageEntity);
			importTemplatePageEntity.setDoAount(false);
			importTemplatePageEntity.setTotal(total.getTotal());
		}
		importTemplatePageEntity.setRows(getSqlSession().selectList("configfield.selectAllImportTemplatePage", importTemplatePageEntity));
		return importTemplatePageEntity;
	}
	
	@Override
	public ImportTemplatePageEntity selectImportTemplatePage(
			ImportTemplatePageEntity importTemplatePageEntity) {
		if(null == importTemplatePageEntity) importTemplatePageEntity =new ImportTemplatePageEntity();
		if(importTemplatePageEntity.isWhetherPaging()){
			ImportTemplatePageEntity total = getSqlSession().selectOne("configfield.selectImportTemplatePage", importTemplatePageEntity);
			importTemplatePageEntity.setDoAount(false);
			importTemplatePageEntity.setTotal(total.getTotal());
		}
		importTemplatePageEntity.setRows(getSqlSession().selectList("configfield.selectImportTemplatePage", importTemplatePageEntity));
		return importTemplatePageEntity;
	}
	
	@Override
	public ImportConfigEntity queryOneImportConfig(
			ImportConfigEntity importConfigEntity) {
		return this.select("configfield.selectImportConfig", importConfigEntity);
	}
	
	@Override
	public int updateImportTemplate(ImportTemplateEntity importTemplateEntity) {
		return this.sqlSessionTemplate.update("configfield.updateImportTemplate", importTemplateEntity);
	}
	
	@Override
	public int updateImportConfig(ImportConfigEntity importConfigEntity) {
		return this.sqlSessionTemplate.update("configfield.updateImportConfig", importConfigEntity);
	}

	@Override
	public int deleteImportTemplate(ImportTemplateEntity importTemplateEntity) {
		return this.sqlSessionTemplate.delete("configfield.deleteImportTemplate", importTemplateEntity);
	}
	
	@Override
	public int deleteImportConfig(ImportConfigEntity importConfigEntity) {
		return this.sqlSessionTemplate.delete("configfield.deleteImportConfig", importConfigEntity);
	}
}
