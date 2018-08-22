package com.ssitcloud.view.readermgmt.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.ExportExcelUtils;
import com.ssitcloud.view.common.util.ExportTextUtil;
import com.ssitcloud.view.readermgmt.service.ConfigFieldService;

@Controller
@RequestMapping("/configField")
public class ConfigFieldController {
	@Resource
	private ConfigFieldService configFieldService;
	
	@RequestMapping("/selectConfigFieldList")
	@ResponseBody
	public ResultEntity selectConfigFieldList(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = configFieldService.selectConfigFieldList(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping("/takeDataSource")
	@ResponseBody
	public ResultEntity takeDataSource(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = "{}";
			result = configFieldService.takeDataSource(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"addConfigTemplate"})
	@ResponseBody
	public ResultEntity addConfigTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = configFieldService.addConfigTemplate(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"selectConfigTemplatePage"})
	@ResponseBody
	public ResultEntity selectConfigTemplatePage (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = configFieldService.selectConfigTemplatePage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"/exportConfigTemplate"})
	public void exportConfigTemplate(HttpServletRequest request,HttpServletResponse response){
		ResultEntity resultEntity = new ResultEntity();
		String req = "";
		try {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
			
			req = request.getParameter("reqName");
			JSONObject json = JSONObject.fromObject(req);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String,String> headers = new LinkedHashMap<String, String>();
			String data_source_select = json.optString("data_source_select").trim();
			String importType = json.optString("importType").trim();
			String dataFilter = json.optString("dataFilter").trim();
			JSONArray array=JSONArray.fromObject(data_source_select);
			String[] ll = new String[array.size()];
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				int index = obj.optInt("cloumnRank");
				String key = obj.optString("data_source_select");
				System.out.println(index);
				if (index >= 0) {
					ll[index-1] = key;
				} else {
					ll[index] = key;
				}
			}
			for(int i=0;i<ll.length;i++){
				headers.put(ll[i],ll[i]);
			}
			if("text".equals(importType)){
				//headers.put("tips:","(如有时间导入必须按格式：例2018-10-01 01:01:01)");
				dataFilter = " ";
				ExportTextUtil.writeToTxt(response,headers,"txt格式导入模板",dataFilter);
			}else if("excel".equals(importType)){
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/x-msdownload");
				String cdisp = "attachment;filename=\""+dateFormat.format(new Date())+".xls\"";
				response.setHeader("Content-disposition",cdisp);//设置头部信息
				ExportExcelUtils.exportExcel("导入数据模板",headers, list, response.getOutputStream());
			}
		} catch (Exception e) {
			LogUtils.error("打印导出统计的excel表格出错");
			e.printStackTrace();
		}
	}
	
	/**
	 * 编辑模板
	 * author liuwei
	 * 2017年12月25日 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"editimport_tpl_idx"})
	public String editimport_tpl_idx(HttpServletRequest request,Model model){
		String reqUrl = "";
		String req = request.getParameter("req");
		String import_tpl_idx = JSONObject.fromObject(req).getString("import_tpl_idx");
		String import_tpl_type = JSONObject.fromObject(req).getString("import_type");
		String library_id = JSONObject.fromObject(req).getString("library_id");
		String library_idx = JSONObject.fromObject(req).getString("library_idx");
		String operator_type = JSONObject.fromObject(req).getString("operator_type");
		Integer copyRecord = 0;
		if(JSONObject.fromObject(req).containsKey("copyRecord")){
			copyRecord = 1;//记录为1时，是复制
		}
		System.out.println(import_tpl_idx);
		model.addAttribute("import_tpl_idx", import_tpl_idx);
		model.addAttribute("copyRecord", copyRecord);
        String currentpage = JSONObject.fromObject(req).getString("currentpage");
        model.addAttribute("currentpage", currentpage);
        model.addAttribute("import_tpl_type", import_tpl_type);
        model.addAttribute("library_id", library_id);
        model.addAttribute("library_idx", library_idx);
        model.addAttribute("operator_type", operator_type);
		//Integer type = JSONObject.fromObject(req).getInt("statistics_type");//模板类型。1：查询模板，2：统计模板
		//if(type==1){
			//reqUrl = "/page/statisticstemplate/template-chaxun";
		//}else if(type==2){
			//reqUrl = "/page/statisticstemplate/template-tongji";
		//}
		return  "/page/readermgmt/importConfig";
	}
	
	@RequestMapping(value={"updateImportTemplate"})
	@ResponseBody
	public ResultEntity updateStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = configFieldService.updateImportTemplate(req);
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
			String req = request.getParameter("req");
			resultEntity = configFieldService.deleteImportTemplate(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
