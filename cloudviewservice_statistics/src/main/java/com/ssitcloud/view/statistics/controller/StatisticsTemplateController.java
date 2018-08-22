package com.ssitcloud.view.statistics.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.StringUtil;
import com.ssitcloud.view.statistics.common.controller.BasicController;
import com.ssitcloud.view.statistics.common.util.ExceptionHelper;
import com.ssitcloud.view.statistics.common.util.JsonUtils;
import com.ssitcloud.view.statistics.common.util.LogUtils;
import com.ssitcloud.view.statistics.common.util.SystemLogUtil;
import com.ssitcloud.view.statistics.deepCopy.StatisticsConfigEntity;
import com.ssitcloud.view.statistics.deepCopy.StatisticsTemplateEntity;
import com.ssitcloud.view.statistics.service.RegionService;
import com.ssitcloud.view.statistics.service.StatisticsTemplateService;
import com.ssitcloud.view.statistics.statisticsmgmt.service.StatisticsConfigService;


@Controller
@RequestMapping(value = { "statisticsTemplate" })
public class StatisticsTemplateController extends BasicController{
	@Resource
	private StatisticsTemplateService statisticsTemplateService;
	@Resource
	private StatisticsConfigService statisticsConfigService;
    @Resource
    RegionService regionService;
	
	/**
	 * 跳转到统计模板配置页面
	 * author huanghuang
	 * 2017年3月16日 上午9:52:07
	 * @return
	 */
	@RequestMapping("/main")
	public String gotSoxTempMain(){
		return "/page/statistics/statistics_template";
	}
	
	/**
	 * 查找统计的数据源
	 * author huanghuang
	 * 2017年3月16日 上午9:28:53
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"takeDataSource"})
	@ResponseBody
	public ResultEntity takeDataSource(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = "{}";
			result = statisticsTemplateService.takeDataSource(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询出统计类型的子类型
	 * author huanghuang
	 * 2017年3月17日 上午9:12:59
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectStaticsType"})
	@ResponseBody
	public ResultEntity selectStaticsType(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = "{}";
			result = statisticsTemplateService.selectStaticsType(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查找统计的图书馆藏地
	 * 格式
	 * json = {
	 * 		"location_idx":"",//图书馆藏地主键，自增
	 * 		"lib_idx":"",
	 * 		"location_code":"",
	 * 		"location_name":"",
	 * 		"location_mark":""
	 * } 
	 * author huanghuang
	 * 2017年3月17日 上午11:35:43
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectBookLocations"})
	@ResponseBody
	public ResultEntity selectBookLocations(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectBookLocations(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 查找统计的图书流通类型
	 * 格式
	 * json = {
	 * 		"cirtype_idx":"",//图书流通类型主键，自增
	 * 		"lib_idx":"",
	 * 		"cirtype_code":"",
	 * 		"cirtype_name":"",
	 * 		"cirtype_mark":""
	 * }
	 * author huanghuang
	 * 2017年3月17日 上午11:36:15
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectBookCirtypes"})
	@ResponseBody
	public ResultEntity selectBookCirtypes(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectBookCirtypes(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查找统计的图书载体
	 * 格式
	 * json = {
	 * 		"media_idx":"",//图书载体类型主键，自增
	 * 		"lib_idx":"",
	 * 		"media_code":"",
	 * 		"media_name":"",
	 * 		"media_mark":""
	 * } 
	 * author huanghuang
	 * 2017年3月17日 上午11:36:37
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectBookMediatypes"})
	@ResponseBody
	public ResultEntity selectBookMediatypes(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectBookMediatypes(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查找统计的读者类型
	 * 格式
	 * json = {
	 * 		"library_idx":""
	 * }
	 * author huanghuang
	 * 2017年3月17日 上午11:36:57
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectReadertype"})
	@ResponseBody
	public ResultEntity selectReadertype(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectReadertype(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 获取所有的设备类型
	 * author huanghuang
	 * 2017年3月17日 下午2:12:35
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectAllDeviceType"})
	@ResponseBody
	public ResultEntity selectAllDeviceType(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = statisticsTemplateService.selectAllDeviceType("{}");
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 通过条件查找设备
	 * author huanghuang
	 * 2017年3月17日 下午2:37:19
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectDeviceByCondition"})
	@ResponseBody
	public ResultEntity selectDeviceByCondition(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			String condition = request.getParameter("");
			result = statisticsTemplateService.selectDeviceByCondition(condition);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询出统计类型的主类型
	 * author huanghuang
	 * 2017年3月17日 上午9:12:59
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryStatisticsMaintypeList"})
	@ResponseBody
	public ResultEntity queryStatisticsMaintypeList(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = "{}";
			result = statisticsTemplateService.queryStatisticsMaintypeList(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查找统计主函数
	 * author huanghuang
	 * 2017年3月17日 上午11:36:57
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectFunMaindatas"})
	@ResponseBody
	public ResultEntity selectFunMaindatas(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectFunMaindatas(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 查找统计子函数
	
	 * author huanghuang
	 * 2017年3月17日 上午11:36:57
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectFunSubdatas"})
	@ResponseBody
	public ResultEntity selectFunSubdatas(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectFunSubdatas(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 新增统计查询模板配置StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addStatisticsTemplate"})
	@ResponseBody
	public ResultEntity addStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.insertStatisticsTemplate(req);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.PERMESSION_ADD_STATTEM);
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
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateStatisticsTemplate"})
	@ResponseBody
	public ResultEntity updateStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.updateStatisticsTemplate(req);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.PERMESSION_UPD_STATTEM);
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
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteStatisticsTemplate"})
	@ResponseBody
	public ResultEntity deleteStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.deleteStatisticsTemplate(req);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.PERMESSION_DEL_STATTEM);
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
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsTemplate"})
	@ResponseBody
	public ResultEntity selectStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.queryOneStatisticsTemplate(req);
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
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsTemplates"})
	@ResponseBody
	public ResultEntity selectStatisticsTemplates (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
//			String req = request.getParameter("req");
			String req = "{}";
			resultEntity = statisticsTemplateService.queryStatisticsTemplates(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"copyStatisticsTemplate"})
	@ResponseBody
	public ResultEntity copyStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.queryOneStatisticsTemplate(req);
			StatisticsTemplateEntity statisticsTemplateEntity = new StatisticsTemplateEntity();
			StatisticsConfigEntity statisticsConfigEntity = new StatisticsConfigEntity();
			JSONObject stObj = null;
			JSONObject scObj = null;
			StatisticsTemplateEntity copy = null;
			if(resultEntity.getState()){
				stObj = JSONObject.fromObject(resultEntity.getResult());
				statisticsTemplateEntity.setStatistics_tpl_id(stObj.getString("statistics_tpl_id"));
				statisticsTemplateEntity.setStatistics_tpl_desc(stObj.getString("statistics_tpl_desc"));
				resultEntity = statisticsConfigService.queryOneStatisticsConfig(req);
				if(resultEntity.getState()){//查询模板成功后，查询模板配置
					scObj = JSONObject.fromObject(resultEntity.getResult());
					statisticsConfigEntity.setStatistics_tpl_idx(statisticsTemplateEntity.getStatistics_tpl_idx());
					statisticsConfigEntity.setStatistics_tpl_type(scObj.getInt("statistics_tpl_type"));
					statisticsConfigEntity.setStatistics_tpl_value(scObj.getString("statistics_tpl_value"));
					statisticsTemplateEntity.setStatisticsConfigEntity(statisticsConfigEntity);
					copy = (StatisticsTemplateEntity) deeplyCopy(statisticsTemplateEntity);
				}
			}
		resultEntity.setResult(copy);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	/**
	 * 通过ID查找模板
	 * author huanghuang
	 * 2017年3月31日 下午4:58:07
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"findStatisticsTemplateById"})
	@ResponseBody
	public ResultEntity findStatisticsTemplateById (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsConfigService.queryOneStatisticsConfig(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 深度复制
	 */
	public static Serializable deeplyCopy(Serializable src){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			oos.close();
			baos.close();
			byte[] data = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Serializable copy = (Serializable) ois.readObject();
			ois.close();
			bais.close();
			return copy ;
		} catch (Exception e) {
			System.err.println("深度复制统计的模板时出错");
			e.printStackTrace();
		}
		return null ;
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
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.selectStatisticsTemplatePage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	/**
	 * 编辑模板
	 * author huanghuang
	 * 2017年3月31日 下午4:58:07
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"editstatistics_tpl_idx"})
	public String editstatistics_tpl_idx(HttpServletRequest request,Model model){
		String reqUrl = "";
		String req = request.getParameter("req");
		String statistics_tpl_idx = JSONObject.fromObject(req).getString("statistics_tpl_idx");
		Integer copyRecord = 0;
		if(JSONObject.fromObject(req).containsKey("copyRecord")){
			copyRecord = 1;//记录为1时，是复制
		}
//		System.out.println(statistics_tpl_idx);
		model.addAttribute("statistics_tpl_idx", statistics_tpl_idx);
		model.addAttribute("copyRecord", copyRecord);
        String currentpage = JSONObject.fromObject(req).getString("currentpage");
        model.addAttribute("currentpage", currentpage);
		Integer type = JSONObject.fromObject(req).getInt("statistics_type");//模板类型。1：查询模板，2：统计模板
		if(type==1){
			reqUrl = "/page/statisticstemplate/template-chaxun";
		}else if(type==2){
			reqUrl = "/page/statisticstemplate/template-tongji";
		}
		return reqUrl;
	}
	
	@RequestMapping(value={"selectBySql"})
    @ResponseBody
    public ResultEntity selectBySql (HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        try {
            String req = request.getParameter("req");
            resultEntity = statisticsTemplateService.queryReltype(req);
            String sql = "";
            String dataName="";
            if(resultEntity.getState()){
                JSONObject obj = JSONObject.fromObject(resultEntity.getResult());
                dataName = obj.getString("data_base");
                if(obj.getString("lib_fields").indexOf("#in") != -1){
                    String libfindlds = obj.getString("lib_fields").split("#")[0];
                    Operator oper=getCurrentUser();
                    String params = "{\"library_idx\":\""+oper.getLibrary_idx()+"\",\"operator_type\":\""+oper.getOperator_type()+"\"}";
                    Map<String, String> map = new HashMap<>();
                    map.put("req", params);
                    String resps = regionService.querylibInfoByCurUserEXT1(map);
                    ResultEntity result = JsonUtils.fromJson(resps, ResultEntity.class);
                    String libidx= "";
                    if(result.getResult() != null){
                        if(result.getMessage().equals("_MASTER_SLAVE_")){
                            JSONObject masterAndSlave = JSONObject.fromObject(result.getResult());
                            JSONObject masterLibrary = masterAndSlave.getJSONObject("masterLibrary");
                            JSONArray slaveLibrary = masterAndSlave.getJSONArray("slaveLibrary");
                            libidx +=masterLibrary.getString("library_idx")+",";
                            for(int i=0;i<slaveLibrary.size();i++){
                                JSONObject jObject = slaveLibrary.getJSONObject(i);
                                if(jObject.containsKey("library_idx")){
                                    if(!jObject.getString("library_idx").equals("0")){
                                        libidx +=jObject.getString("library_idx")+",";
                                    }
                                }
                            }
                        }else{
                            JSONArray jsonArray = JSONArray.fromObject(result.getResult());
                            for(int i=0;i<jsonArray.size();i++){
                                JSONObject jObject = jsonArray.getJSONObject(i);
                                if(jObject.containsKey("library_idx")){
                                    if(!jObject.getString("library_idx").equals("0")){
                                        libidx +=jObject.getString("library_idx")+",";
                                    }
                                }
                            }
                        }
                    }
                    if(libidx.isEmpty()){
                        LogUtils.error("没查到馆IDX,result-->"+ JsonUtils.toJson(result));
                        return new ResultEntity();
                    }
                    libidx = libidx.substring(0,libidx.length()-1);
                    sql = "select "+obj.getString("data_fields")+" from "+obj.getString("data_base")+"."+obj.getString("data_tables");
                    if(obj.getString("lib_fields") !=null && !obj.getString("lib_fields").equals("")){
                        sql += " where "+libfindlds+" in ("+libidx+")";
                    }
                }else if(obj.getString("lib_fields").indexOf("#ac") != -1){
                    String libfindlds = obj.getString("lib_fields").split("#")[0];
                    Operator oper=getCurrentUser();
                    JSONArray jsonArray = null;
                    String params = "{\"library_idx\":\""+oper.getLibrary_idx()+"\",\"operator_type\":\""+oper.getOperator_type()+"\"}";
                    Map<String, String> map = new HashMap<>();
                    map.put("req", params);
                    String resps = regionService.querylibInfoByCurUserEXT1(map);
                    ResultEntity result = JsonUtils.fromJson(resps, ResultEntity.class);
                    String libidx= "";
                    if(result.getResult() != null){
                        if(result.getMessage().equals("_MASTER_SLAVE_")){
                            JSONObject masterAndSlave = JSONObject.fromObject(result.getResult());
                            JSONObject masterLibrary = masterAndSlave.getJSONObject("masterLibrary");
                            JSONArray slaveLibrary = masterAndSlave.getJSONArray("slaveLibrary");
                            libidx +=masterLibrary.getString("library_idx")+",";
                            for(int i=0;i<slaveLibrary.size();i++){
                                JSONObject jObject = slaveLibrary.getJSONObject(i);
                                if(jObject.containsKey("library_idx")){
                                    if(!jObject.getString("library_idx").equals("0")){
                                        libidx +=jObject.getString("library_idx")+",";
                                    }
                                }
                            }
                        }else{
                            jsonArray = JSONArray.fromObject(result.getResult());
                            for(int i=0;i<jsonArray.size();i++){
                                JSONObject jObject = jsonArray.getJSONObject(i);
                                if(jObject.containsKey("library_idx")){
                                    if(!jObject.getString("library_idx").equals("0")){
                                        libidx +=jObject.getString("library_idx")+",";
                                    }
                                }
                            }
                        }
                    }
                    if(libidx.isEmpty()){
                        LogUtils.error("没查到馆IDX,result-->"+ JsonUtils.toJson(result));
                        return new ResultEntity();
                    }
                    libidx = libidx.substring(0,libidx.length()-1);

                    String libstr = "{\"idx\":\""+libidx+"\"}";
                    result = regionService.selRegionsByLibidx(libstr);
                    String rcode= "";
                    if(result.getResult() != null){
                        jsonArray = JSONArray.fromObject(result.getResult());
                        for(int i=0;i<jsonArray.size();i++){
                            JSONObject jObject = jsonArray.getJSONObject(i);
                            if(jObject.containsKey("regi_code")){
                                if((i+1) == jsonArray.size()){
                                    rcode +=jObject.getString("regi_code");
                                }else{
                                    rcode +=jObject.getString("regi_code")+",";
                                }
                            }
                        }
                    }
                    if(rcode.isEmpty()){
                        LogUtils.error("没查到设备的地区编码,result-->"+ JsonUtils.toJson(result));
                        return new ResultEntity();
                    }
                    sql = "select "+obj.getString("data_fields")+",count(DISTINCT "+obj.getString("data_mark")+") from "+obj.getString("data_base")+"."+obj.getString("data_tables");
                    if(obj.getString("lib_fields") !=null && !obj.getString("lib_fields").equals("")){
                        sql += " where "+libfindlds+" in ("+rcode+")";
                    }
                    sql += " GROUP BY "+obj.getString("data_mark");
                }else if(obj.getString("data_tables").indexOf("device") != -1) {
                	
                     sql = "select "+obj.getString("data_fields")+" from "+obj.getString("data_base")+"."+obj.getString("data_tables");
                     if(obj.getString("lib_fields") !=null && !obj.getString("lib_fields").equals("")){
                         if(!getCurrentUser().getOperator_type().equals("1")){
                        	 //根据主管查子馆
                        	 String libidx = getMasterAndSlaveLibx( getCurrentUser());
                        	 if(StringUtil.isBlank(libidx)){
                        		 return resultEntity;
                        	 }
                             sql += " where "+obj.getString("lib_fields")+" in (" + libidx+")";
                         }
                     }
                     
                }else{
                    sql = "select "+obj.getString("data_fields")+" from "+obj.getString("data_base")+"."+obj.getString("data_tables");
                    if(obj.getString("lib_fields") !=null && !obj.getString("lib_fields").equals("")){
                        if(!getCurrentUser().getOperator_type().equals("1")){
                            sql += " where "+obj.getString("lib_fields")+"="+getCurrentUser().getLibrary_idx();
                        }
                    }
                }
                if(dataName.endsWith("device")){
                    resultEntity = statisticsTemplateService.selectBySql(sql);
                }else if(dataName.endsWith("authentication")){
                    resultEntity = statisticsTemplateService.selectAutBySql(sql);
                }

                JSONArray jArr = JSONArray.fromObject(resultEntity.getResult());
                if(obj.getString("data_fields").indexOf(",")>-1){//将返回的结果值，转换为key和value的键值对
                    String[] kv = obj.getString("data_fields").split(",");
                    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                    for(int i=0;i<jArr.size();i++){
                        Map<String, String> values = new HashMap<String, String>();
                        JSONObject jObject = jArr.getJSONObject(i);
                        String key = jObject.getString(kv[0]);
                        String code = jObject.getString(kv[1]);
                        if(kv[1].equals("regi_nation")){
                            key = key.substring(0,2);
                        }else if(kv[1].equals("regi_province")){
                            key = key.substring(0,4);
                        }else if(kv[1].equals("regi_city")){
                            key = key.substring(0,6);
                        }
                        values.put("key", key);
                        values.put("code", code);
                        list.add(values);
                    }
                    resultEntity.setResult(list);
                }
            }
        } catch (Exception e) {
            String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
            ExceptionHelper.afterException(resultEntity, methodName, e);
        }
        return resultEntity;
    }
	
	/**
	 * 根据主馆查子馆
	 * @param params
	 * @return
	 */
	private String  getMasterAndSlaveLibx(Operator oper) {
		 StringBuffer libidx= new StringBuffer(100);
         String params = "{\"library_idx\":\""+oper.getLibrary_idx()+"\",\"operator_type\":\""+oper.getOperator_type()+"\"}";
         Map<String, String> map = new HashMap<>();
         map.put("req", params);
         String resps = regionService.querylibInfoByCurUserEXT1(map);
         ResultEntity result = JsonUtils.fromJson(resps, ResultEntity.class);
         if(result.getResult() != null){
             if(result.getMessage().equals("_MASTER_SLAVE_")){
                 JSONObject masterAndSlave = JSONObject.fromObject(result.getResult());
                 JSONObject masterLibrary = masterAndSlave.getJSONObject("masterLibrary");
                 JSONArray slaveLibrary = masterAndSlave.getJSONArray("slaveLibrary");
                 libidx.append(masterLibrary.getString("library_idx"));
                 libidx.append(",");
                 for(int i=0;i<slaveLibrary.size();i++){
                     JSONObject jObject = slaveLibrary.getJSONObject(i);
                     if(jObject.containsKey("library_idx")){
                         if(!jObject.getString("library_idx").equals("0")){
                             libidx.append(jObject.getString("library_idx"));
                             libidx.append(",");
                         }
                     }
                 }
             }else{
                 JSONArray jsonArray = JSONArray.fromObject(result.getResult());
                 for(int i=0;i<jsonArray.size();i++){
                     JSONObject jObject = jsonArray.getJSONObject(i);
                     if(jObject.containsKey("library_idx")){
                         if(!jObject.getString("library_idx").equals("0")){
                             libidx.append(jObject.getString("library_idx"));
                             libidx.append(",");
                         }
                     }
                 }
             }
         }
         if(libidx.length()==0){
             LogUtils.error("没查到馆IDX,result-->"+ JsonUtils.toJson(result));
             return "";
         }
         String libx = libidx.toString();
         libx = libidx.substring(0,libx.length()-1);
		return libx;
	}

	@RequestMapping(value={"selectBySqls"})
	@ResponseBody
	public ResultEntity selectBySqls (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.queryReltypeList(req);
			String sql = "";
            String dataName="";
			if(resultEntity.getState()){
				JSONArray obj = JSONArray.fromObject(resultEntity.getResult());
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for(int j=0,z=obj.size();j<z;j++){
					JSONObject json = obj.getJSONObject(j);
                    dataName = json.getString("data_base");
					String maintype_idx = json.getString("maintype_idx");
                    if(json.getString("lib_fields").indexOf("#in") != -1){
                        String libfindlds = json.getString("lib_fields").split("#")[0];
                        Operator oper=getCurrentUser();
                        String params = "{\"library_idx\":\""+oper.getLibrary_idx()+"\",\"operator_type\":\""+oper.getOperator_type()+"\"}";
                        Map<String, String> map = new HashMap<>();
                        map.put("req", params);
                        String resps = regionService.querylibInfoByCurUserEXT1(map);
                        ResultEntity result = JsonUtils.fromJson(resps, ResultEntity.class);
                        String libidx= "";
                        if(result.getResult() != null){
                            if(result.getMessage().equals("_MASTER_SLAVE_")){
                                JSONObject masterAndSlave = JSONObject.fromObject(result.getResult());
                                JSONObject masterLibrary = masterAndSlave.getJSONObject("masterLibrary");
                                JSONArray slaveLibrary = masterAndSlave.getJSONArray("slaveLibrary");
                                libidx +=masterLibrary.getString("library_idx")+",";
                                for(int i=0;i<slaveLibrary.size();i++){
                                    JSONObject jObject = slaveLibrary.getJSONObject(i);
                                    if(jObject.containsKey("library_idx")){
                                        if(!jObject.getString("library_idx").equals("0")){
                                            libidx +=jObject.getString("library_idx")+",";
                                        }
                                    }
                                }
                            }else{
                                JSONArray jsonArray = JSONArray.fromObject(result.getResult());
                                for(int i=0;i<jsonArray.size();i++){
                                    JSONObject jObject = jsonArray.getJSONObject(i);
                                    if(jObject.containsKey("library_idx")){
                                        if(!jObject.getString("library_idx").equals("0")){
                                            libidx +=jObject.getString("library_idx")+",";
                                        }
                                    }
                                }
                            }
                        }
                        if(libidx.isEmpty()){
                            LogUtils.error("没查到馆IDX,result-->"+ JsonUtils.toJson(result));
                            return new ResultEntity();
                        }
                        libidx = libidx.substring(0,libidx.length()-1);
                        sql = "select "+json.getString("data_fields")+" from "+json.getString("data_base")+"."+json.getString("data_tables");
                        if(json.getString("lib_fields") !=null && !json.getString("lib_fields").equals("")){
                            sql += " where "+libfindlds+" in ("+libidx+")";
                        }
                    }else if(json.getString("lib_fields").indexOf("#ac") != -1){
                        String libfindlds = json.getString("lib_fields").split("#")[0];
                        Operator oper=getCurrentUser();
                        JSONArray jsonArray = null;
                        String params = "{\"library_idx\":\""+oper.getLibrary_idx()+"\",\"operator_type\":\""+oper.getOperator_type()+"\"}";
                        Map<String, String> map = new HashMap<>();
                        map.put("req", params);
                        String resps = regionService.querylibInfoByCurUserEXT1(map);
                        ResultEntity result = JsonUtils.fromJson(resps, ResultEntity.class);
                        String libidx= "";
                        if(result.getResult() != null){
                            if(result.getMessage().equals("_MASTER_SLAVE_")){
                                JSONObject masterAndSlave = JSONObject.fromObject(result.getResult());
                                JSONObject masterLibrary = masterAndSlave.getJSONObject("masterLibrary");
                                JSONArray slaveLibrary = masterAndSlave.getJSONArray("slaveLibrary");
                                libidx +=masterLibrary.getString("library_idx")+",";
                                for(int i=0;i<slaveLibrary.size();i++){
                                    JSONObject jObject = slaveLibrary.getJSONObject(i);
                                    if(jObject.containsKey("library_idx")){
                                        if(!jObject.getString("library_idx").equals("0")){
                                            libidx +=jObject.getString("library_idx")+",";
                                        }
                                    }
                                }
                            }else{
                                jsonArray = JSONArray.fromObject(result.getResult());
                                for(int i=0;i<jsonArray.size();i++){
                                    JSONObject jObject = jsonArray.getJSONObject(i);
                                    if(jObject.containsKey("library_idx")){
                                        if(!jObject.getString("library_idx").equals("0")){
                                            libidx +=jObject.getString("library_idx")+",";
                                        }
                                    }
                                }
                            }
                        }
                        if(libidx.isEmpty()){
                            LogUtils.error("没查到馆IDX,result-->"+ JsonUtils.toJson(result));
                            return new ResultEntity();
                        }
                        libidx = libidx.substring(0,libidx.length()-1);
                        String libstr = "{\"idx\":\""+libidx+"\"}";
                        result = regionService.selRegionsByLibidx(libstr);
                        String rcode= "";
                        if(result.getResult() != null){
                            jsonArray = JSONArray.fromObject(result.getResult());
                            for(int i=0;i<jsonArray.size();i++){
                                JSONObject jObject = jsonArray.getJSONObject(i);
                                if(jObject.containsKey("regi_code")){
                                    if((i+1) == jsonArray.size()){
                                        rcode +=jObject.getString("regi_code");
                                    }else{
                                        rcode +=jObject.getString("regi_code")+",";
                                    }
                                }
                            }
                        }
                        if(rcode.isEmpty()){
                            LogUtils.error("没查到设备的地区编码,result-->"+ JsonUtils.toJson(result));
                            return new ResultEntity();
                        }
                        sql = "select "+json.getString("data_fields")+",count(DISTINCT "+json.getString("data_mark")+") from "+json.getString("data_base")+"."+json.getString("data_tables");
                        if(json.getString("lib_fields") !=null && !json.getString("lib_fields").equals("")){
                            sql += " where "+libfindlds+" in ("+rcode+")";
                        }
                        sql+=" GROUP BY "+json.getString("data_mark");
                    }else if(json.getString("data_tables").indexOf("device") != -1) {
                    	
                    	 sql = "select "+json.getString("data_fields")+" from "+json.getString("data_base")+"."+json.getString("data_tables");
                        if(json.getString("lib_fields") !=null && !json.getString("lib_fields").equals("")){
                            if(!getCurrentUser().getOperator_type().equals("1")){
                           	 //根据主管查子馆
                           	 String libidx = getMasterAndSlaveLibx( getCurrentUser());
                           	 if(StringUtil.isBlank(libidx)){
                           		 return resultEntity;
                           	 }
                                sql += " where "+json.getString("lib_fields")+" in (" + libidx+")";
                            }
                        }
                        
                   }else{
                        sql = "select "+json.getString("data_fields")+" from "+json.getString("data_base")+"."+json.getString("data_tables");
                        if(json.getString("lib_fields") !=null && !json.getString("lib_fields").equals("")){
                            if(!getCurrentUser().getOperator_type().equals("1")){
                                sql += " where "+json.getString("lib_fields")+"="+getCurrentUser().getLibrary_idx();
                            }
                        }
                    }
                    if(dataName.endsWith("device")){
                        resultEntity = statisticsTemplateService.selectBySql(sql);
                    }else if(dataName.endsWith("authentication")){
                        resultEntity = statisticsTemplateService.selectAutBySql(sql);
                    }
					JSONArray jArr = JSONArray.fromObject(resultEntity.getResult());
					if(json.getString("data_fields").indexOf(",")>-1){//将返回的结果值，转换为key和value的键值对
						String[] kv = json.getString("data_fields").split(",");
						for(int i=0;i<jArr.size();i++){
							Map<String, String> values = new HashMap<String, String>();
							JSONObject jObject = jArr.optJSONObject(i);
							if(jObject!=null&&!jObject.isNullObject()){
								String key = jObject.optString(kv[0]);
								String code = jObject.optString(json.optString("data_mark"));
								values.put("maintype_idx", maintype_idx);
	                            if(kv[1].equals("regi_nation")){
	                                key = key.substring(0,2);
	                            }else if(kv[1].equals("regi_province")){
	                                key = key.substring(0,4);
	                            }else if(kv[1].equals("regi_city")){
	                                key = key.substring(0,6);
	                            }
								values.put("key", key);
								values.put("code", code);
								list.add(values);
							}
						}
						
					}
				}
				resultEntity.setResult(list);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

    /**
     *根据登录用户获取模板
     * lqw 2017/09/11
     */
    @RequestMapping(value={"selectTemplateMenuByOperidx"})
    @ResponseBody
    public ResultEntity selectTemplateMenuByOperidx (HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        try {
            Operator oper=getCurrentUser();
            String req = "{\"idx\":"+oper.getOperator_idx()+"}";
            resultEntity = statisticsTemplateService.selectTemplateMenuByOperidx(req);
        } catch (Exception e) {
            String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
            ExceptionHelper.afterException(resultEntity, methodName, e);
        }
        return resultEntity;
    }
}
