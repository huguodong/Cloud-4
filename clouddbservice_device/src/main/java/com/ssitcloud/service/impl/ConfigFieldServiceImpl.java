package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.ConfigFieldDao;
import com.ssitcloud.entity.ConfigFieldEntity;
import com.ssitcloud.entity.ImportConfigEntity;
import com.ssitcloud.entity.ImportTemplateEntity;
import com.ssitcloud.entity.StatisticsConfigEntity;
import com.ssitcloud.entity.StatisticsTemplateEntity;
import com.ssitcloud.entity.page.ImportTemplatePageEntity;
import com.ssitcloud.entity.page.StatisticsTemplatePageEntity;
import com.ssitcloud.service.ConfigFieldService;
@Service
public class ConfigFieldServiceImpl implements ConfigFieldService{
	@Resource
	private ConfigFieldDao configFieldDao;
	
	@Override
	public ResultEntity listConfigFieldCollection() {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<Map<String, Object>> list = configFieldDao.listConfigFieldCollection();
			resultEntity.setValue(true, "", "", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}
	
	@Override
	public ResultEntity selectConfigFieldList(ConfigFieldEntity configFieldEntity) {
		ResultEntity result = new ResultEntity();
		try{
			List<ConfigFieldEntity> list = configFieldDao.selectConfigFieldList(configFieldEntity);
			result.setValue(true, "", "", list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	
	@Override
	public int insertImportTemplate(
			ImportTemplateEntity importTemplateEntity) {
		return configFieldDao.insertImportTemplate(importTemplateEntity);
	}
	
	@Override
	public int insertImportConfig(
			ImportConfigEntity importConfigEntity) {
		return configFieldDao.insertImportConfig(importConfigEntity);
	}
	
	@Override
	public ResultEntity selectImportTemplatePage(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
				ImportTemplatePageEntity importTemplatePageEntity = new ImportTemplatePageEntity(); 
				
				Integer page = map.get("page")==null?1:Integer.valueOf(map.get("page").toString());
				String type = (String) map.get("type");	
				Integer pageSize = map.get("pageSize")==null?10:Integer.valueOf(map.get("pageSize").toString());
				if(map.get("import_tpl_type")!=null && !map.get("import_tpl_type").toString().isEmpty()){
					importTemplatePageEntity.setImport_tpl_type(Integer.parseInt(map.get("import_tpl_type").toString()));
				}
				if(map.get("import_tpl_desc")!=null && !map.get("import_tpl_desc").toString().isEmpty()){
					importTemplatePageEntity.setImport_tpl_desc(map.get("import_tpl_desc").toString());
				}
				if(map.get("lib_id")!=null && !map.get("lib_id").toString().isEmpty()){
					importTemplatePageEntity.setLib_id((map.get("lib_id").toString()));
				}
				importTemplatePageEntity.setPage(page);
				importTemplatePageEntity.setPageSize(pageSize);
				Boolean daFlag = map.get("daFlag")==null?true:false;
				if(!daFlag){//不分页
					importTemplatePageEntity.setWhetherPaging(false);
				}
				if("reader".equals(type)||"bookbiblios".equals(type))
					importTemplatePageEntity = configFieldDao.selectAllImportTemplatePage(importTemplatePageEntity);
				else if("".equals(type))
					importTemplatePageEntity = configFieldDao.selectImportTemplatePage(importTemplatePageEntity);
				result.setValue(true, "success","",importTemplatePageEntity);
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@Override
	public ImportConfigEntity queryOneImportConfig(
			ImportConfigEntity importConfigEntity) {
		return configFieldDao.queryOneImportConfig(importConfigEntity);
			
	}
	
	@Override
	public int updateImportTemplate(
			ImportTemplateEntity importTemplateEntity) {
		return configFieldDao.updateImportTemplate(importTemplateEntity);
	}
	
	@Override
	public int updateImportConfig(
			ImportConfigEntity importConfigEntity) {
		return configFieldDao.updateImportConfig(importConfigEntity);
	}
	
	@Override
	public int deleteImportTemplate(
			ImportTemplateEntity importTemplateEntity) {
		return configFieldDao.deleteImportTemplate(importTemplateEntity);
	}
	
	@Override
	public int deleteImportConfig(
			ImportConfigEntity importConfigEntity) {
		return configFieldDao.deleteImportConfig(importConfigEntity);
	}
}
