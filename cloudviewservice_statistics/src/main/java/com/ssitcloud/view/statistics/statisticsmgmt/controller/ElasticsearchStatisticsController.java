package com.ssitcloud.view.statistics.statisticsmgmt.controller;

import java.io.IOException;
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
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.controller.BasicController;
import com.ssitcloud.view.statistics.common.util.ExceptionHelper;
import com.ssitcloud.view.statistics.common.util.ExportExcelUtils;
import com.ssitcloud.view.statistics.common.util.LogUtils;
import com.ssitcloud.view.statistics.common.util.TxtUtil;
import com.ssitcloud.view.statistics.statisticsmgmt.service.ElasticsearchStatisticsService;

@Controller
@RequestMapping(value={"elasticsearchstatistics"})
public class ElasticsearchStatisticsController extends BasicController{
	@Resource
	private ElasticsearchStatisticsService elasticsearchStatisticsService;
	
	@RequestMapping(value={"statisticselasticsearch"})
	@ResponseBody
	public ResultEntity statisticselasticsearch (HttpServletRequest request){//流通的统计
		ResultEntity resultEntity = new ResultEntity();
		try {
			Operator oper=getCurrentUser();
			String req = request.getParameter("req");
			if(JSONUtils.mayBeJSON(req)){
				JSONObject js = JSONObject.fromObject(req);
				js.put("library_idx", oper.getLibrary_idx());
				js.put("lib_id", oper.getLib_id().toLowerCase());
                js.put("operator_type", oper.getOperator_type());
				req = js.toString();
				resultEntity = elasticsearchStatisticsService.statistics(req);
			} else {
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"queryelasticsearch"})
	@ResponseBody
	public ResultEntity queryelasticsearch (HttpServletRequest request){//流通的查询
		ResultEntity resultEntity = new ResultEntity();
		try {
			Operator oper=getCurrentUser();
			String req = request.getParameter("req");
			if(JSONUtils.mayBeJSON(req)){
				JSONObject js = JSONObject.fromObject(req);
				js.put("library_idx", oper.getLibrary_idx());
				js.put("lib_id", oper.getLib_id().toLowerCase());
                js.put("operator_type", oper.getOperator_type());
				req = js.toString();
				resultEntity = elasticsearchStatisticsService.query(req);
			} else {
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"gtree"})
	@ResponseBody
	public ResultEntity gtree(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Operator oper=getCurrentUser();
			String req = request.getParameter("req");
			if(JSONUtils.mayBeJSON(req)){
				JSONObject js = JSONObject.fromObject(req);
				js.put("library_idx", oper.getLibrary_idx());
				js.put("lib_id", oper.getLib_id().toLowerCase());
                js.put("operator_type", oper.getOperator_type());
				req = js.toString();
				resultEntity = elasticsearchStatisticsService.gtree(req);
			} else {
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	/**
	 * 导出统计的excel表格
	 * author huanghuang
	 * 2017年5月3日 上午11:26:49
	 * @param request
	 * @param response
	 */
	@RequestMapping(value={"exportStatistic"})
	public void exportStatistic(HttpServletRequest request,HttpServletResponse response){
		ResultEntity resultEntity = new ResultEntity();
		String req = "";
		try {
			Operator oper=getCurrentUser();
			req = request.getParameter("reqName");
			if(JSONUtils.mayBeJSON(req)){
				JSONObject js = JSONObject.fromObject(req);
				js.put("library_idx", oper.getLibrary_idx());
				js.put("lib_id", oper.getLib_id().toLowerCase());
                js.put("operator_type", oper.getOperator_type());
				req = js.toString();
				resultEntity = elasticsearchStatisticsService.statistics(req);
			} else {
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		if(resultEntity!=null&&resultEntity.getResult()!=null){
			@SuppressWarnings("unchecked")
			List<List<Map.Entry<String, Long>>> infoIds = (List<List<Entry<String, Long>>>) resultEntity.getResult();
			JSONObject json = JSONObject.fromObject(req);
			
			String dataIDXAndDataName = json.getString("dataIDXAndDataName").trim();
			String dataTypeAndDataDesc = json.getString("dataTypeAndDataDesc").trim();
			String hDataIndex = json.getString("hDataIndex").trim();
			
			String nogroup = json.getString("nogroupAlias").trim();
			String groupAlias = json.getString("groupAlias").trim();
			String[] nogrouparr = null;
			if(nogroup.length()>0){//不分组的数据不为空时，不分组
				nogrouparr = nogroup.split("\\,");
			}
			String[] grouparr = null;
			if(groupAlias.length()>0){//分组的数据不为空时，不分组
				grouparr = groupAlias.split("\\,");
			}
			try {
				Map<String,String> headers = new LinkedHashMap<String, String>();
				for(String g : grouparr){//组的别名
					headers.put(g.trim(), g.trim());
				}
				for(String ng:nogrouparr){//不是组的别名
					String[] ngArr = ng.trim().split("\\|");
					headers.put(ng.trim(), ngArr[1]);
				}
				headers.put("合计", "合计");
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				if(infoIds!=null&&infoIds.size()>0){
					List<Map.Entry<String, Long>> ls = infoIds.get(0);
					Map<String,Integer> sumMap = new HashMap<String,Integer>();
					for(int j=0;j<ls.size();j++){
						Map<String, String> rMap = new HashMap<String, String>();
//					System.out.println("size:"+infoIds.size());
						for(int i=0;i<infoIds.size();i++){
							List<Map.Entry<String, Long>> ls1 = infoIds.get(i);
							@SuppressWarnings("unchecked")
							Map<String, Long> me = (Map<String, Long>) ls1.get(j);
							Set<Entry<String, Long>> s = me.entrySet();
							Iterator<Entry<String, Long>> it = s.iterator();
							while(it.hasNext()){
								Entry<String, Long> e = it.next();
//								if("value".equals(e.getKey())){
								if(StringUtils.isNotBlank(e.getKey())){
//									for(String g:grouparr){//组的别名
									for(int g1=0,len1=grouparr.length;g1<len1;g1++){//组的别名
										rMap.put(grouparr[g1].trim(), table(hDataIndex, String.valueOf(e.getKey()), dataIDXAndDataName, dataTypeAndDataDesc, g1));
									}
									rMap.put(nogrouparr[i].trim(), String.valueOf(e.getValue()));
									Integer sum = Integer.parseInt(String.valueOf(e.getValue()));
									if(sumMap.containsKey(e.getKey())){
										sum += sumMap.get(e.getKey());
									}
									rMap.put("合计", String.valueOf(sum));
									sumMap.put(e.getKey(), sum);
									break;
								}
							}
						}
						list.add(rMap);
					}
					
				}
//			for(int i=0;i<infoIds.size();i++){//第一层的遍历
//				List<Map.Entry<String, Long>> ls = infoIds.get(i);
//				for(int j=0;j<ls.size();j++){//第二层的遍历
//					@SuppressWarnings("unchecked")
//					Map<String, Long> me = (Map<String, Long>) ls.get(j);
//					Set<Entry<String, Long>> s = me.entrySet();
//					Iterator<Entry<String, Long>> it = s.iterator();
//					while(it.hasNext()){
//						Entry<String, Long> e = it.next();
//						if("value".equals(e.getKey())){
//							Map<String, String> rMap = new HashMap<String, String>();
//							rMap.put(nogrouparr[i].trim(), e.getValue()+"");
//							list.add(rMap);
//							break;
//						}
//					}
//				}
//			}
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/x-download");//("application/vnd.ms-excel");//("application/x-download");//设置response内容的类型
//				String fName = json.getString("fileName");
				String cdisp = "attachment;filename=\""+dateFormat.format(new Date())+".xls\"";
				response.setHeader("Content-disposition",cdisp);//设置头部信息
				 Collections.sort(list, new Comparator<Map<String, String>>() {  
						@Override
						public int compare(Map<String, String> o1,
								Map<String, String> o2) {
							if(o1 == null && o2 == null) {  
							    return 0;  
							}  
							if(o1 == null) {  
							    return -1;  
							}  
							if(o2 == null) {  
							    return 1;  
							}  
							if(Integer.parseInt(o2.get("合计")) > Integer.parseInt(o1.get("合计"))) {  
							    return 1;  
							}  
							if(Integer.parseInt(o1.get("合计")) > Integer.parseInt(o2.get("合计"))) {  
							    return -1;  
							}  
							return 0;  
						}  
			        });  
				ExportExcelUtils.exportExcel("流通统计",headers, list, response.getOutputStream());
			} catch (Exception e) {
				LogUtils.error("打印导出统计的excel表格出错");
				e.printStackTrace();
			}
		}else{
			LogUtils.info("当前条件下，查询不到统计数据");
		}
	}
	/**
	 * 导出查询的excel表格
	 * author huanghuang
	 * 2017年5月3日 上午11:27:23
	 * @param request
	 * @param response
	 */
	@RequestMapping(value={"exportSelect"})
	public void exportSelect(HttpServletRequest request,HttpServletResponse response){
		ResultEntity resultEntity = new ResultEntity();
		String req = "";
		JSONObject obj = new JSONObject();
		try {
			Operator oper=getCurrentUser();
			req = request.getParameter("reqName");
			if(JSONUtils.mayBeJSON(req)){
				obj = JSONObject.fromObject(req);
				obj.put("library_idx", oper.getLibrary_idx());
				obj.put("lib_id", oper.getLib_id().toLowerCase());
				obj.put("operator_type", oper.getOperator_type());
				req = obj.toString();
				resultEntity = elasticsearchStatisticsService.exportSelect(req);
			} else {
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		if(resultEntity!=null&&resultEntity.getResult()!=null){
			String dataIDXAndDataName = obj.getString("dataIDXAndDataName").trim();
			String dataTypeAndDataDesc = obj.getString("dataTypeAndDataDesc").trim();
			String hDataIndex = obj.getString("hDataIndex").trim();
			
			String group = obj.getString("group");
			String[] groupArr = group.split(",");//得到分组字段
			String rstr = (String) resultEntity.getResult();
			Map<String,String> map =null;
			if(!rstr.equals("{}")){
				JSONObject gcondition = JSONObject.fromObject(rstr);
				map = JsonToMap(gcondition);
			}
			String mJsonStr = map.get("result");
			JSONArray resultJsonArr = JSONArray.fromObject(mJsonStr);
			JSONObject json = JSONObject.fromObject(req);
			String nogroup = json.getString("nogroupAlias").trim();
			String[] nogrouparr = null;
			if(nogroup.length()>0){//不分组的数据不为空时，不分组
				nogrouparr = nogroup.split("\\,");
			}
			String groupAlias = json.getString("groupAlias").trim();
			String[] grouparr = null;
			if(groupAlias.length()>0){//分组的数据不为空时，不分组
				grouparr = groupAlias.split("\\,");
			}
			Map<String,String> headers = new LinkedHashMap<String, String>();
			for(String g : grouparr){//组的别名
				headers.put(g.trim(), g.trim());
			}
			for(String ng:nogrouparr){
				String[] ngArr = ng.trim().split("\\|");
				headers.put(ngArr[0], ngArr[1]);
			}
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			if(resultJsonArr!=null&&resultJsonArr.size()>0){
				for(int j=0;j<resultJsonArr.size();j++){
					JSONObject js = resultJsonArr.getJSONObject(j);
					Map<String, String> m= JsonToMap(js);
					StringBuilder groupCondition = new StringBuilder(256);
					if(groupArr!=null){
						for(int i=0,len=groupArr.length;i<len; i++){
							groupCondition.append(m.get(groupArr[i])+",");
						}
					}
					if(groupCondition.indexOf(",")>-1){
						String val = "["+groupCondition.deleteCharAt(groupCondition.length()-1).toString().trim()+"]";
						for(int g1=0,len1=grouparr.length;g1<len1;g1++){//组的别名
							m.put(grouparr[g1].trim(), table(hDataIndex, val, dataIDXAndDataName, dataTypeAndDataDesc, g1));
						}
//						m.put("分组条件", "["+groupCondition.deleteCharAt(groupCondition.length()-1).toString()+"]");
					}
					list.add(m);
				}
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/x-download");//("application/vnd.ms-excel");//("application/x-download");//设置response内容的类型
//			String fName = json.getString("fileName");
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
//			String cdisp = "attachment;filename=\""+new String(fName)+"-"+dateFormat.format(new Date())+".xls\"";
			String cdisp = "attachment;filename=\""+dateFormat.format(new Date())+".xls\"";
			response.setHeader("Content-disposition",cdisp);//设置头部信息
			try {
				ExportExcelUtils.exportExcel("流通查询",headers, list, response.getOutputStream());
			} catch (IOException e) {
				LogUtils.error("打印导出查询的excel表格出错");
				e.printStackTrace();
			}
		}else{
			LogUtils.info("当前条件下，查询不到符合查询条件的数据");
		}
	}
	/**
	 * 导出查询的txt
	 * author huanghuang
	 * 2017年5月3日 上午11:27:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"exportTxtSelect"})
	@ResponseBody
	public ResultEntity exportTxtSelect(HttpServletRequest request,HttpServletResponse response){
		ResultEntity resultEntity = new ResultEntity();
		String req = "";
		JSONObject obj = new JSONObject();
		try {
			Operator oper=getCurrentUser();
			req = request.getParameter("req");
			if(JSONUtils.mayBeJSON(req)){
				obj = JSONObject.fromObject(req);
				obj.put("library_idx", oper.getLibrary_idx());
				obj.put("lib_id", oper.getLib_id().toLowerCase());
				obj.put("operator_type", oper.getOperator_type());
				req = obj.toString();
				resultEntity = elasticsearchStatisticsService.exportSelect(req);
			} else {
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		if(resultEntity!=null&&resultEntity.getResult()!=null){
			String dataIDXAndDataName = obj.getString("dataIDXAndDataName").trim();
			String dataTypeAndDataDesc = obj.getString("dataTypeAndDataDesc").trim();
			String hDataIndex = obj.getString("hDataIndex").trim();
			
			String group = obj.getString("group");
			String[] groupArr = group.split(",");//得到分组字段
			String rstr = (String) resultEntity.getResult();
			Map<String,String> map =null;
			if(!rstr.equals("{}")){
				JSONObject gcondition = JSONObject.fromObject(rstr);
				map = JsonToMap(gcondition);
			}
			String mJsonStr = map.get("result");
			JSONArray resultJsonArr = JSONArray.fromObject(mJsonStr);
			JSONObject json = JSONObject.fromObject(req);
			String nogroup = json.getString("nogroupAlias").trim();
			String[] nogrouparr = null;
			if(nogroup.length()>0){//不分组的数据不为空时，不分组
				nogrouparr = nogroup.split("\\,");
			}
			String groupAlias = json.getString("groupAlias").trim();

			String[] grouparr = null;
			if(groupAlias.length()>0){//分组的数据不为空时，不分组
				grouparr = groupAlias.split("\\,");
			}
			Map<String,String> headers = new LinkedHashMap<String, String>();
			for(String g : grouparr){//组的别名
				headers.put(g.trim(), g.trim());
			}
			for(String ng:nogrouparr){
				String[] ngArr = ng.trim().split("\\|");
				headers.put(ngArr[0], ngArr[1]);
			}
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			if(resultJsonArr!=null&&resultJsonArr.size()>0){
				for(int j=0;j<resultJsonArr.size();j++){
					JSONObject js = resultJsonArr.getJSONObject(j);
					Map<String, String> m= JsonToMap(js);
					StringBuilder groupCondition = new StringBuilder();
					if(groupArr!=null){
						for(int i=0,len=groupArr.length;i<len; i++){
							groupCondition.append(m.get(groupArr[i])+",");
						}
					}
					if(groupCondition.indexOf(",")>-1){
						String val = "["+groupCondition.deleteCharAt(groupCondition.length()-1).toString()+"]";
						for(int g1=0,len1=grouparr.length;g1<len1;g1++){//组的别名
							m.put(grouparr[g1].trim(), table(hDataIndex, val, dataIDXAndDataName, dataTypeAndDataDesc, g1));
						}
//						m.put("分组条件", "["+groupCondition.deleteCharAt(groupCondition.length()-1).toString()+"]");
					}
					list.add(m);
				}
			}
//			String file = "D://select.txt";//测试时，将文件的路径放在d盘
			TxtUtil.exportTxt(headers, list, response);
		}else{
			LogUtils.info("当前条件下，查询不到符合查询条件的数据");
		}
		resultEntity.setState(true);
		resultEntity.setMessage("导出文件成功");
		return resultEntity;
	}
	/**
	 * 导出统计的txt
	 * author huanghuang
	 * 2017年5月3日 上午11:28:09
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"exportTxtStatistic"})
	@ResponseBody
	public ResultEntity exportTxtStatistic(HttpServletRequest request,HttpServletResponse response){
		ResultEntity resultEntity = new ResultEntity();
		String req = "";
		try {
			Operator oper=getCurrentUser();
			req = request.getParameter("req");
			if(JSONUtils.mayBeJSON(req)){
				JSONObject js = JSONObject.fromObject(req);
				js.put("library_idx", oper.getLibrary_idx());
				js.put("lib_id", oper.getLib_id().toLowerCase());
                js.put("operator_type", oper.getOperator_type());
				req = js.toString();
				resultEntity = elasticsearchStatisticsService.statistics(req);
			} else {
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		if(resultEntity!=null&&resultEntity.getResult()!=null){
			@SuppressWarnings("unchecked")
			List<List<Map.Entry<String, Long>>> infoIds = (List<List<Entry<String, Long>>>) resultEntity.getResult();
			JSONObject json = JSONObject.fromObject(req);
			
			String dataIDXAndDataName = json.getString("dataIDXAndDataName").trim();
			String dataTypeAndDataDesc = json.getString("dataTypeAndDataDesc").trim();
			String hDataIndex = json.getString("hDataIndex").trim();
			String groupAlias = json.getString("groupAlias").trim();//组的别名
			String nogroup = json.getString("nogroupAlias").trim();
			String[] nogrouparr = null;
			if(nogroup.length()>0){//不分组的数据不为空时，不分组
				nogrouparr = nogroup.split("\\,");
			}

			String[] grouparr = null;
			if(groupAlias.length()>0){//分组的数据不为空时，不分组
				grouparr = groupAlias.split("\\,");
			}
			Map<String,String> headers = new LinkedHashMap<String, String>();
			for(String g : grouparr){//组的别名
				headers.put(g.trim(), g.trim());
			}
			for(String ng:nogrouparr){
				String[] ngArr = ng.trim().split("\\|");
				headers.put(ng.trim(), ngArr[1]);
			}
			headers.put("合计", "合计");
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			if(infoIds!=null&&infoIds.size()>0){
				List<Map.Entry<String, Long>> ls = infoIds.get(0);
				Map<String,Integer> sumMap = new HashMap<String,Integer>();
				for(int j=0;j<ls.size();j++){
					Map<String, String> rMap = new HashMap<String, String>();
//					System.out.println("size:"+infoIds.size());
					for(int i=0;i<infoIds.size();i++){
						List<Map.Entry<String, Long>> ls1 = infoIds.get(i);
						@SuppressWarnings("unchecked")
						Map<String, Long> me = (Map<String, Long>) ls1.get(j);
						Set<Entry<String, Long>> s = me.entrySet();
						Iterator<Entry<String, Long>> it = s.iterator();
						while(it.hasNext()){
							Entry<String, Long> e = it.next();
//							if("value".equals(e.getKey())){
							if(StringUtils.isNotBlank(e.getKey())){
								for(int g1=0,len1=grouparr.length;g1<len1;g1++){//组的别名
									rMap.put(grouparr[g1].trim(), table(hDataIndex, String.valueOf(e.getKey()), dataIDXAndDataName, dataTypeAndDataDesc, g1));
								}
//								rMap.put("分组条件", table(hDataIndex, String.valueOf(e.getKey()), dataIDXAndDataName, dataTypeAndDataDesc, 0));
								rMap.put(nogrouparr[i].trim(), String.valueOf(e.getValue()));
								Integer sum = Integer.parseInt(String.valueOf(e.getValue()));
								if(sumMap.containsKey(e.getKey())){
									sum += sumMap.get(e.getKey());
								}
								rMap.put("合计", String.valueOf(sum));
								sumMap.put(e.getKey(), sum);
								break;
							}
						}
					}
					list.add(rMap);
				}
			}
//			String file = "D://statistic.txt";//测试时，将文件的路径放在d盘
//			TxtUtil.exportTxt(headers, list, file);
			Collections.sort(list, new Comparator<Map<String, String>>() {  
				@Override
				public int compare(Map<String, String> o1,
						Map<String, String> o2) {
					if(o1 == null && o2 == null) {  
					    return 0;  
					}  
					if(o1 == null) {  
					    return -1;  
					}  
					if(o2 == null) {  
					    return 1;  
					}  
					if(Integer.parseInt(o2.get("合计")) > Integer.parseInt(o1.get("合计"))) {  
					    return 1;  
					}  
					if(Integer.parseInt(o1.get("合计")) > Integer.parseInt(o2.get("合计"))) {  
					    return -1;  
					}  
					return 0;
				}  
	        });  
			TxtUtil.exportTxt(headers, list, response);
		}else{
			LogUtils.info("当前条件下，查询不到统计数据");
		}
		resultEntity.setState(true);
		resultEntity.setMessage("导出文件成功");
		return resultEntity;
	}
	@RequestMapping(value={"checkStatistics"})
	@ResponseBody
	public ResultEntity checkStatistics(HttpServletRequest request){//导出统计时先查一遍数据
		ResultEntity resultEntity = new ResultEntity();
		String req = "";
		try {
			Operator oper=getCurrentUser();
			req = request.getParameter("reqName");
			if(JSONUtils.mayBeJSON(req)){
				JSONObject js = JSONObject.fromObject(req);
				js.put("library_idx", oper.getLibrary_idx());
				js.put("lib_id", oper.getLib_id().toLowerCase());
                js.put("operator_type", oper.getOperator_type());
				req = js.toString();
				resultEntity = elasticsearchStatisticsService.statistics(req);
			} else {
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	@RequestMapping(value={"checkSelect"})
	@ResponseBody
	public ResultEntity checkSelect(HttpServletRequest request){//导出查询时先查一遍数据
		ResultEntity resultEntity = new ResultEntity();
		String req = "";
		try {
			Operator oper=getCurrentUser();
			req = request.getParameter("reqName");
			if(JSONUtils.mayBeJSON(req)){
				JSONObject js = JSONObject.fromObject(req);
				js.put("library_idx", oper.getLibrary_idx());
				js.put("lib_id", oper.getLib_id().toLowerCase());
                js.put("operator_type", oper.getOperator_type());
				req = js.toString();
				resultEntity = elasticsearchStatisticsService.exportSelect(req);
			} else {
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String,String> JsonToMap(JSONObject jsonObject) throws JSONException {
		Map<String,String> result = new LinkedHashMap<String,String>();//put进去的对象位置未发生变化
		Iterator iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
		
	}
	
	@RequestMapping(value={"libArr"})
	@ResponseBody
	public ResultEntity libArr(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Operator oper=getCurrentUser();
			String req = request.getParameter("req");
			if(JSONUtils.mayBeJSON(req)){
				JSONObject js = JSONObject.fromObject(req);
				js.put("library_idx", oper.getLibrary_idx());
				js.put("lib_id", oper.getLib_id().toLowerCase());
                js.put("operator_type", oper.getOperator_type());
				req = js.toString();
				resultEntity = elasticsearchStatisticsService.libArr(req);
			}else{
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"devArr"})
	@ResponseBody
	public ResultEntity devArr(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Operator oper=getCurrentUser();
			String req = request.getParameter("req");
			if(JSONUtils.mayBeJSON(req)){
				JSONObject js = JSONObject.fromObject(req);
				js.put("library_idx", oper.getLibrary_idx());
				js.put("lib_id", oper.getLib_id().toLowerCase());
                js.put("operator_type", oper.getOperator_type());
				req = js.toString();
				resultEntity = elasticsearchStatisticsService.devArr(req);
			} else {
				throw new JSONException("非法的JSON字符串");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 对照表根据idx对应名称
	 * @param gIdxStr
	 * @param idxStr
	 * @param dataIDXAndDataName
	 * @param dataTypeAndDataDesc
	 * @param index
	 * @return
	 */
	public String table(String gIdxStr, String idxStr, String dataIDXAndDataName, String dataTypeAndDataDesc, int index){
		gIdxStr = gIdxStr.replace("[", "").replace("]", "").replaceAll("\"", "");
		idxStr = idxStr.replace("[", "").replace("]", "").replaceAll("\"", "");
		String[] gIdx = gIdxStr.split(",");
		String[] idx = idxStr.split(",");
		JSONObject json1 = JSONObject.fromObject(dataIDXAndDataName);
		JSONObject json2 = JSONObject.fromObject(dataTypeAndDataDesc);
		for(int g=0,len=gIdx.length; g<len; g++){
			if(g==index){//取第几层
				boolean specialFlag = StringUtils.isBlank(gIdx[g])||"-1".equals(gIdx[g])||"16".equals(gIdx[g])||"18".equals(gIdx[g]);
				if(specialFlag){
					return idx[g];
				}else{
					if("21".equals(gIdx[g])||"22".equals(gIdx[g])||"23".equals(gIdx[g])||"24".equals(gIdx[g])){
						gIdx[g] = String.valueOf(21);
					}
					Iterator iterator1 = json1.keys();
					while(iterator1.hasNext()){
						String key = (String) iterator1.next();
						String[] keyArr = key.split("\\|");
						if(keyArr!=null&&keyArr.length>1){
							if(keyArr[0].equals(gIdx[g].trim())&&keyArr[1].equals(idx[g].trim())){
								JSONObject name = JSONObject.fromObject(json1.getString(key));
								return name.getString("name");
							}
						}
					}
					Iterator iterator2 = json2.keys();
					while(iterator2.hasNext()){
						String key = (String) iterator2.next();
						String[] keyArr = key.split("\\|");
						if(keyArr!=null&&keyArr.length>1){
							if(keyArr[0].equals(gIdx[g].trim())&&keyArr[1].equals(idx[g].trim())){
								JSONObject type = JSONObject.fromObject(json2.getString(key));
								return type.getString("data_desc");
							}
						}
					}
				}
			}
		}
		return "";
	}

}
