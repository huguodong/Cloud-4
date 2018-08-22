package com.ssitcloud.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.ConfigFieldEntity;
import com.ssitcloud.entity.ImportConfigEntity;
import com.ssitcloud.entity.ImportTemplateEntity;
import com.ssitcloud.entity.StatisticsConfigEntity;
import com.ssitcloud.entity.StatisticsTemplateEntity;
import com.ssitcloud.service.ConfigFieldService;
@Controller
@RequestMapping("/configfield")
public class ConfigFieldController {
	@Resource
	private ConfigFieldService configFieldService;
	
	@RequestMapping("/getConfigFieldCollection")
	@ResponseBody
	public ResultEntity getConfigFieldCollection(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result = configFieldService.listConfigFieldCollection();
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping("/selectConfigFieldList")
	@ResponseBody
	public ResultEntity selectConfigFieldList(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			ConfigFieldEntity configFieldEntity = new ConfigFieldEntity();
			result = configFieldService.selectConfigFieldList(configFieldEntity);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	@RequestMapping("/addConfigTemplate")
	@ResponseBody
	public ResultEntity addStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			String retMessage="";
			if (StringUtils.isNotBlank(json)) {
				JSONObject obj = JSONObject.fromObject(json);
				String import_tpl_id = obj.optString("import_tpl_id");
				String import_tpl_desc = obj.optString("import_tpl_desc");
				Integer import_tpl_type = obj.optInt("import_tpl_type");
				String lib_id = obj.optString("lib_id");
				String lib_name = obj.optString("lib_name");
				Integer library_idx = Integer.valueOf(obj.optString("library_idx"));
				String import_tpl_value = obj.getString("import_tpl_value");
				ImportTemplateEntity importTemplateEntity = new ImportTemplateEntity();
				importTemplateEntity.setImport_tpl_id(import_tpl_id);
				importTemplateEntity.setImport_tpl_desc(import_tpl_id);
//				StatisticsTemplateEntity statisticsTemplateEntity = JsonUtils.fromJson(json, StatisticsTemplateEntity.class);
				int ret = configFieldService.insertImportTemplate(importTemplateEntity);
				if (ret>0) {//模板保存成功后，保存模板配置
					resultEntity.setValue(true, "success","",importTemplateEntity);
					ImportConfigEntity importConfigEntity = new ImportConfigEntity();
					importConfigEntity.setImport_tpl_idx(importTemplateEntity.getImport_tpl_idx());
					importConfigEntity.setImport_tpl_type(import_tpl_type);
					importConfigEntity.setLib_id(lib_id);
					importConfigEntity.setLibrary_idx(library_idx);
					importConfigEntity.setLib_name(lib_name);
					importConfigEntity.setImport_tpl_value(import_tpl_value);
					int r = configFieldService.insertImportConfig(importConfigEntity);
                    int type = importConfigEntity.getImport_tpl_type();
                    String data = importConfigEntity.getImport_tpl_value();
                    JSONObject djson = JSONObject.fromObject(data);
                    String typeName="";
                    if(type == 1){
                        typeName="学生导入配置模板";
                    }else if(type == 2){
                        typeName="教工导入配置模板";
                    }else if(type == 3){
                        typeName="馆藏导入配置模板";
                    }else if(type == 4){
                    	typeName="书目导入配置模板";
                    }
                    String name = djson.getJSONObject("headerData").getString("templateName");
                    retMessage="|模板idx："+importConfigEntity.getImport_tpl_idx()+"|模板名称："+name+"|模板类型："+typeName+"|";
                    if(r>0){
						resultEntity.setValue(true, "success","",importConfigEntity);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
            resultEntity.setRetMessage(retMessage);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"selectImportTemplatePage"})
	@ResponseBody
	public ResultEntity selectImportTemplatePage (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String  req= request.getParameter("req");
		try {
			resultEntity = configFieldService.selectImportTemplatePage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"selectImportConfig"})
	@ResponseBody
	public ResultEntity selectImportConfig (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				ImportConfigEntity importConfigEntity = JsonUtils.fromJson(json, ImportConfigEntity.class);
				importConfigEntity = configFieldService.queryOneImportConfig(importConfigEntity);
				resultEntity.setValue(true, "success","",importConfigEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"updateImportTemplate"})
	@ResponseBody
	public ResultEntity updateImportTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			String retMessage="";
			if (StringUtils.isNotBlank(json)) {
//				StatisticsTemplateEntity statisticsTemplateEntity = JsonUtils.fromJson(json, StatisticsTemplateEntity.class);
//				int ret = statisticsTemplateService.updateStatisticsTemplate(statisticsTemplateEntity);
//				if (ret>0) {
//					resultEntity.setValue(true, "success","",statisticsTemplateEntity);
//				}else{
//					resultEntity.setValue(false, "failed");
//				}
				JSONObject obj = JSONObject.fromObject(json);
				Integer import_tpl_idx = obj.getInt("import_tpl_idx");
				String import_tpl_id = obj.getString("import_tpl_id");
				//String statistics_tpl_desc = obj.getString("statistics_tpl_desc");
				Integer import_tpl_type = obj.getInt("import_tpl_type");
				String lib_id = obj.optString("lib_id");
				Integer library_idx = Integer.valueOf(obj.optString("library_idx"));
				String lib_name = obj.optString("lib_name");
				String import_tpl_value = obj.getString("import_tpl_value");
				ImportTemplateEntity importTemplateEntity = new ImportTemplateEntity();
				importTemplateEntity.setImport_tpl_idx(import_tpl_idx);
				importTemplateEntity.setImport_tpl_id(import_tpl_id);
				//importTemplateEntity.setImport_tpl_desc(import_tpl_desc);
//				StatisticsTemplateEntity statisticsTemplateEntity = JsonUtils.fromJson(json, StatisticsTemplateEntity.class);
				int ret = configFieldService.updateImportTemplate(importTemplateEntity);
				if (ret>0) {//模板保存成功后，保存模板配置
					resultEntity.setValue(true, "success","",importTemplateEntity);
					ImportConfigEntity importConfigEntity = new ImportConfigEntity();
					importConfigEntity.setImport_tpl_idx(import_tpl_idx);
					importConfigEntity.setImport_tpl_type(import_tpl_type);
					importConfigEntity.setLib_id(lib_id);
					importConfigEntity.setLibrary_idx(library_idx);
					importConfigEntity.setLib_name(lib_name);
					importConfigEntity.setImport_tpl_value(import_tpl_value);
					int r = configFieldService.updateImportConfig(importConfigEntity);
                    int type = importConfigEntity.getImport_tpl_type();
                    String data = importConfigEntity.getImport_tpl_value();
                    JSONObject djson = JSONObject.fromObject(data);
                    String typeName="";
                    if(type == 1){
                        typeName="学生导入配置模板";
                    }else if(type == 2){
                        typeName="教工导入配置模板";
                    }else if(type == 3){
                        typeName="馆藏导入配置模板";
                    }else if(type == 4){
                    	typeName="书目导入配置模板";
                    }
                    String name = djson.getJSONObject("headerData").getString("templateName");
                    retMessage="|模板idx："+importConfigEntity.getImport_tpl_idx()+"|模板名称："+name+"|模板类型："+typeName+"|";
                    if(r>0){
						resultEntity.setValue(true, "success","",importConfigEntity);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
            resultEntity.setRetMessage(retMessage);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"deleteImportTemplate"})
	@ResponseBody
	public ResultEntity deleteImportTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			String retMessage="";
			if (StringUtils.isNotBlank(json)) {
				JSONArray jarr = JSONArray.fromObject(json);
				for (int i = 0,z=jarr.size(); i < z; i++) {
					ImportConfigEntity importConfigEntity = new ImportConfigEntity();
					ImportTemplateEntity importTemplateEntity = new ImportTemplateEntity();
					importConfigEntity.setImport_tpl_idx(Integer.parseInt(jarr.getJSONObject(i).getString("import_tpl_idx")));
					importTemplateEntity.setImport_tpl_idx(Integer.parseInt(jarr.getJSONObject(i).getString("import_tpl_idx")));
					configFieldService.deleteImportTemplate(importTemplateEntity);
					int ret = configFieldService.deleteImportConfig(importConfigEntity);
                    retMessage="|模板idx："+importConfigEntity.getImport_tpl_idx()+"|";
                    if (ret>=0) {
						resultEntity.setValue(true, "success","",importTemplateEntity);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}
				resultEntity.setMessage("ONE");
			}else {
				resultEntity.setRetMessage("删除失败!");
			}
            resultEntity.setRetMessage(retMessage);
        } catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
