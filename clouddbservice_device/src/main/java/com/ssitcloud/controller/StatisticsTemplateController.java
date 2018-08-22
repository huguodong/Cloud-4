package com.ssitcloud.controller;

import java.util.List;
import java.util.Map;

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
import com.ssitcloud.entity.StatisticsConfigEntity;
import com.ssitcloud.entity.StatisticsTemplateEntity;
import com.ssitcloud.service.StatisticsConfigService;
import com.ssitcloud.service.StatisticsTemplateService;

@Controller
@RequestMapping(value={"statisticsTemplate"})
public class StatisticsTemplateController {
	@Resource
	private StatisticsTemplateService statisticsTemplateService;
	@Resource
	private StatisticsConfigService statisticsConfigService;
	
	/**
	 * 新增统计查询模板配置StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addStatisticsTemplate"})
	@ResponseBody
	public ResultEntity addStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			String retMessage="";
			if (StringUtils.isNotBlank(json)) {
				JSONObject obj = JSONObject.fromObject(json);
				String statistics_tpl_id = obj.getString("statistics_tpl_id");
				String statistics_tpl_desc = obj.getString("statistics_tpl_desc");
				Integer statistics_tpl_type = obj.getInt("statistics_tpl_type");
				String statistics_tpl_value = obj.getString("statistics_tpl_value");
				StatisticsTemplateEntity statisticsTemplateEntity = new StatisticsTemplateEntity();
				statisticsTemplateEntity.setStatistics_tpl_id(statistics_tpl_id);
				statisticsTemplateEntity.setStatistics_tpl_desc(statistics_tpl_desc);
//				StatisticsTemplateEntity statisticsTemplateEntity = JsonUtils.fromJson(json, StatisticsTemplateEntity.class);
				int ret = statisticsTemplateService.insertStatisticsTemplate(statisticsTemplateEntity);
				if (ret>0) {//模板保存成功后，保存模板配置
					resultEntity.setValue(true, "success","",statisticsTemplateEntity);
					StatisticsConfigEntity statisticsConfigEntity = new StatisticsConfigEntity();
					statisticsConfigEntity.setStatistics_tpl_idx(statisticsTemplateEntity.getStatistics_tpl_idx());
					statisticsConfigEntity.setStatistics_tpl_type(statistics_tpl_type);
					statisticsConfigEntity.setStatistics_tpl_value(statistics_tpl_value);
					int r = statisticsConfigService.insertStatisticsConfig(statisticsConfigEntity);
                    int type = statisticsConfigEntity.getStatistics_tpl_type();
                    String data = statisticsConfigEntity.getStatistics_tpl_value();
                    JSONObject djson = JSONObject.fromObject(data);
                    String typeName="";
                    if(type == 1){
                        typeName="查询模板";
                    }else if(type == 2){
                        typeName="统计模板";
                    }
                    String name = djson.getJSONObject("headerData").getString("templateName");
                    retMessage="|模板idx："+statisticsConfigEntity.getStatistics_tpl_idx()+"|模板名称："+name+"|模板类型："+typeName+"|";
                    if(r>0){
						resultEntity.setValue(true, "success","",statisticsConfigEntity);
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
	
	/**
	 * 修改统计查询模板配置StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateStatisticsTemplate"})
	@ResponseBody
	public ResultEntity updateStatisticsTemplate (HttpServletRequest request){
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
				Integer statistics_tpl_idx = obj.getInt("statistics_tpl_idx");
				String statistics_tpl_id = obj.getString("statistics_tpl_id");
				String statistics_tpl_desc = obj.getString("statistics_tpl_desc");
				Integer statistics_tpl_type = obj.getInt("statistics_tpl_type");
				String statistics_tpl_value = obj.getString("statistics_tpl_value");
				StatisticsTemplateEntity statisticsTemplateEntity = new StatisticsTemplateEntity();
				statisticsTemplateEntity.setStatistics_tpl_idx(statistics_tpl_idx);
				statisticsTemplateEntity.setStatistics_tpl_id(statistics_tpl_id);
				statisticsTemplateEntity.setStatistics_tpl_desc(statistics_tpl_desc);
//				StatisticsTemplateEntity statisticsTemplateEntity = JsonUtils.fromJson(json, StatisticsTemplateEntity.class);
				int ret = statisticsTemplateService.updateStatisticsTemplate(statisticsTemplateEntity);
				if (ret>0) {//模板保存成功后，保存模板配置
					resultEntity.setValue(true, "success","",statisticsTemplateEntity);
					StatisticsConfigEntity statisticsConfigEntity = new StatisticsConfigEntity();
					statisticsConfigEntity.setStatistics_tpl_idx(statistics_tpl_idx);
					statisticsConfigEntity.setStatistics_tpl_type(statistics_tpl_type);
					statisticsConfigEntity.setStatistics_tpl_value(statistics_tpl_value);
					int r = statisticsConfigService.updateStatisticsConfig(statisticsConfigEntity);
                    int type = statisticsConfigEntity.getStatistics_tpl_type();
                    String data = statisticsConfigEntity.getStatistics_tpl_value();
                    JSONObject djson = JSONObject.fromObject(data);
                    String typeName="";
                    if(type == 1){
                        typeName="查询模板";
                    }else if(type == 2){
                        typeName="统计模板";
                    }
                    String name = djson.getJSONObject("headerData").getString("templateName");
                    retMessage="|模板idx："+statisticsConfigEntity.getStatistics_tpl_idx()+"|模板名称："+name+"|模板类型："+typeName+"|";
                    if(r>0){
						resultEntity.setValue(true, "success","",statisticsConfigEntity);
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
	
	/**
	 * 删除统计查询模板配置StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteStatisticsTemplate"})
	@ResponseBody
	public ResultEntity deleteStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			String retMessage="";
			if (StringUtils.isNotBlank(json)) {
				JSONArray jarr = JSONArray.fromObject(json);
				for (int i = 0,z=jarr.size(); i < z; i++) {
					StatisticsConfigEntity statisticsConfigEntity = new StatisticsConfigEntity();
					StatisticsTemplateEntity statisticsTemplateEntity = new StatisticsTemplateEntity();
					statisticsConfigEntity.setStatistics_tpl_idx(Integer.parseInt(jarr.getJSONObject(i).getString("statistics_tpl_idx")));
					statisticsTemplateEntity.setStatistics_tpl_idx(Integer.parseInt(jarr.getJSONObject(i).getString("statistics_tpl_idx")));
					statisticsTemplateService.deleteStatisticsTemplate(statisticsTemplateEntity);
					int ret = statisticsConfigService.deleteStatisticsConfig(statisticsConfigEntity);
                    retMessage="|模板idx："+statisticsConfigEntity.getStatistics_tpl_idx()+"|";
                    if (ret>=0) {
						resultEntity.setValue(true, "success","",statisticsTemplateEntity);
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
	
	/**
	 * 查询一条统计查询模板配置记录StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsTemplate"})
	@ResponseBody
	public ResultEntity selectStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				StatisticsTemplateEntity statisticsTemplateEntity = JsonUtils.fromJson(json, StatisticsTemplateEntity.class);
				statisticsTemplateEntity = statisticsTemplateService.queryOneStatisticsTemplate(statisticsTemplateEntity);
				resultEntity.setValue(true, "success","",statisticsTemplateEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条统计查询模板配置记录StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsTemplates"})
	@ResponseBody
	public ResultEntity selectStatisticsTemplates (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			StatisticsTemplateEntity statisticsTemplateEntity = JsonUtils.fromJson(json, StatisticsTemplateEntity.class);
			List<StatisticsTemplateEntity> list = statisticsTemplateService.queryStatisticsTemplates(statisticsTemplateEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	/**
	 * 统计查询模板配置StatisticsTemplateEntity分页查询
	 * author lqw
	 * 2017年3月31日 
	 * @param
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsTemplatePage"})
	@ResponseBody
	public ResultEntity selectStatisticsTemplatePage (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String  req= request.getParameter("req");
		try {
			resultEntity = statisticsTemplateService.selectStatisticsTemplatePage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"selectBySql"})
	@ResponseBody
	public ResultEntity selectBySql (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String sql = request.getParameter("req");
			List<Map<String, Object>> list = statisticsTemplateService.selectBySql(sql);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

    /**
     *根据登录用户获取模板
     * lqw 2017/09/11
     * @param
     * @return
     */
    @RequestMapping(value={"selectTemplateMenuByOperidx"})
    @ResponseBody
    public ResultEntity selectTemplateMenuByOperidx (HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        try {
            String json = request.getParameter("req");
            if (StringUtils.isNotBlank(json)) {
                JSONObject jsonObj = JSONObject.fromObject(json);
                if(jsonObj.containsKey("idx")){
                    int id = jsonObj.getInt("idx");
                    List<StatisticsTemplateEntity> list = statisticsTemplateService.selectTemplateMenuByOperidx(id);
                    resultEntity.setValue(true, "success","",list);
                }else{
                    resultEntity.setValue(false, "参数不能为空","","");
                }
            }else{
                resultEntity.setValue(false, "参数不能为空","","");
            }
        } catch (Exception e) {
            String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
            ExceptionHelper.afterException(resultEntity, methodName, e);
        }
        return resultEntity;
    }
}
