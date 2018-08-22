package com.ssitcloud.business.statistics.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssitcloud.common.entity.ResultEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.authentication.entity.RelLibsEntity;
import com.ssitcloud.business.statistics.common.utils.ExceptionHelper;
import com.ssitcloud.business.statistics.common.utils.StatisConstant;
import com.ssitcloud.business.statistics.common.utils.StatisticsUtils;
import com.ssitcloud.business.statistics.service.CommonEsStatisticService;
import com.ssitcloud.business.statistics.service.ElasticsearchStatisticsService;


@Controller
@RequestMapping(value={"elasticsearchstatistics"})
public class ElasticsearchStatisticsController {
	@Resource
	private ElasticsearchStatisticsService elasticsearchStatisticsService;
	
	@Resource
	private CommonEsStatisticService commonEsStatisticService;
	
	@RequestMapping(value={"statisticselasticsearch"})
	@ResponseBody
	public ResultEntity statisticselasticsearch (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = elasticsearchStatisticsService.statistics(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	@RequestMapping(value={"exportSelect"})
	@ResponseBody
	public ResultEntity exportSelect (HttpServletRequest request,HttpServletResponse response){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			String str = elasticsearchStatisticsService.exportSelect(req);
			resultEntity.setValue(true,"success","",str);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"queryelasticsearch"})
	@ResponseBody
	public ResultEntity queryelasticsearch (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			String str = elasticsearchStatisticsService.query(req);
			resultEntity.setValue(true,"success","",str);
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
			String req = request.getParameter("req");
			JSONObject json = JSONObject.fromObject(req);
			JSONObject functions=null;
			if(json.containsKey("functions")){
				if(json.get("functions") !=null && json.get("functions") !="{}"){
					functions=json.getJSONObject("functions");
				}
			}
			//获取是否包含子馆
			String isContainSubLib = json.optString("isContainSubLib");
			//获得对应馆的所有设备 start
			Integer libidx = json.getInt("library_idx");
			String libId = json.getString("lib_id").toLowerCase();
            Integer operator_type = json.getInt("operator_type");
			//获取当前图书馆的子馆 start
			List<Integer> libIdxs = new ArrayList<Integer>();//图书馆idx
			libIdxs.add(libidx);
			List<String> libIds = new ArrayList<String>();//图书馆id
			libIds.add(libId);
			int topN = json.optInt("topHits",StatisConstant.STATIC_TOPN);//TopN
			//如果页面包含子馆，则查询子馆
			if("0".equals(isContainSubLib)){
				List<RelLibsEntity> list = elasticsearchStatisticsService.takeRelLibs(libidx);
				if(list!=null){
					for(RelLibsEntity rel : list){
						Integer tmpidx = rel.getSlave_lib_id();
						JSONObject libJson = elasticsearchStatisticsService.takeLib(tmpidx);
						if(libJson.get("lib_id")!=null){
							libIdxs.add(libJson.getInt("library_idx"));
							libIds.add(libJson.getString("lib_id").toLowerCase());
						}
					}
				}
			}
			//获取当前图书馆的子馆 start
//			String[] datasource = null;//要查的elachseach索引
			StringBuffer sb = new StringBuffer();
            String[] datasourceArr = null;
            if(operator_type == 1){
                sb.append("*_"+json.getString("datasource")+",");
            }else{
                for(int tmp=0;tmp<libIdxs.size();tmp++){
                	sb.append(libIds.get(tmp) + "_*_"+json.getString("datasource")+",");
                    /*Integer libIdx = libIdxs.get(tmp);
                    JSONArray devJsonArr = elasticsearchStatisticsService.takeDev(libIdx);
                    if(devJsonArr!=null){
                        for (int d = 0; d < devJsonArr.size(); d++) {
                        }
                    }*/
                }
            }

			if(sb.toString().endsWith(",")){
				sb.deleteCharAt(sb.length()-1);
			}
			datasourceArr = sb.toString().split(",");
//			JSONArray devJsonArr = elasticsearchStatisticsService.takeDev(libidx);
//			String[] datasourceArr = null;
//			if(devJsonArr!=null){
//				datasourceArr = new String[devJsonArr.size()];
//				for (int d = 0; d < devJsonArr.size(); d++) {
//					datasourceArr[d] = libId + "_" + devJsonArr.getJSONObject(d).getString("device_id").replaceAll("#", "").toLowerCase()+"_"+json.getString("datasource");
//				}
//			}
			//获得对应馆的所有设备 end
//			String datasource = "hhtest_sch_001_"+json.getString("datasource");
//			String datasource = "hhtest_sch001_loan_testlog";
			String group = "";
			String[] grouparr = null;
			if(json.getString("group") !=null && json.getString("group").length() >0){
				group = json.getString("group");
				grouparr = group.split(",");
			}
			String s = json.getString("gcondition");
			Map<String,String> map =null;
			if(!s.equals("{}")){
				map = StatisticsUtils.JsonToMap(JSONObject.fromObject(s));
			}
			String str ="";
			JSONObject condition = json.getJSONObject("condition");//一般条件
			//读者维度特殊处理逻辑
			if(! StringUtils.isEmpty(json.getString("datasource"))  && (json.getString("datasource").endsWith("reader_circulation_log"))){
				StringBuffer sb1 = new StringBuffer();
	            if(operator_type == 1){
	            	sb1.append("*_"+json.getString("datasource")+",");
	            }else{
	                for(int tmp=0;tmp<libIdxs.size();tmp++){
	                	sb1.append(libIds.get(tmp) + "*_"+json.getString("datasource")+",");
	                    /*Integer libIdx = libIdxs.get(tmp);
	                    JSONArray devJsonArr = elasticsearchStatisticsService.takeDev(libIdx);
	                    if(devJsonArr!=null){
	                        for (int d = 0; d < devJsonArr.size(); d++) {
	                        }
	                    }*/
	                }
	            }
	            datasourceArr = sb1.toString().split(",");
				str = commonEsStatisticService.gtreeForReader(datasourceArr, grouparr,map,functions,true,condition,topN).toString();
				
			}else{
				
				//str = elasticsearchStatisticsService.gtree(datasourceArr, grouparr,map,functions,true,condition,topN).toString();
				str = commonEsStatisticService.gtreeForReader(datasourceArr, grouparr,map,functions,true,condition,topN).toString();
			}
			resultEntity.setValue(true,"success","",str);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"libArr"})
	@ResponseBody
	public ResultEntity libArr(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			JSONObject json = JSONObject.fromObject(req);
			//获取是否包含子馆
			String isContainSubLib = json.optString("isContainSubLib");
			//获得对应馆的所有设备 start
			Integer libidx = json.getInt("library_idx");
			String libId = json.getString("lib_id").toLowerCase();
            Integer operator_type = json.getInt("operator_type");
			//获取当前图书馆的子馆 start
			List<Integer> libIdxs = new ArrayList<Integer>();//图书馆idx
			libIdxs.add(libidx);
			List<String> libIds = new ArrayList<String>();//图书馆id
			libIds.add(libId);
			//如果页面包含子馆，则查询子馆
			if("0".equals(isContainSubLib)){
				List<RelLibsEntity> list = elasticsearchStatisticsService.takeRelLibs(libidx);
				if(list!=null){
					for(RelLibsEntity rel : list){
						Integer tmpidx = rel.getSlave_lib_id();
						JSONObject libJson = elasticsearchStatisticsService.takeLib(tmpidx);
						if(libJson.get("lib_id")!=null){
							libIdxs.add(libJson.getInt("library_idx"));
							libIds.add(libJson.getString("lib_id").toLowerCase());
						}
					}
				}
			}
			//获取当前图书馆的子馆 start
//			String[] datasource = null;//要查的elachseach索引
			StringBuffer sb = new StringBuffer();
            String[] datasourceArr = null;
            if(operator_type == 1){
                sb.append("*_"+json.getString("datasource")+",");
            }else{
                for(int tmp=0;tmp<libIdxs.size();tmp++){
                	sb.append(libIds.get(tmp) + "_*_"+json.getString("datasource")+",");
                }
            }

			if(sb.toString().endsWith(",")){
				sb.deleteCharAt(sb.length()-1);
			}
			datasourceArr = sb.toString().split(",");
			String group = "";
			String[] grouparr = null;
			if(json.getString("group") !=null && json.getString("group").length() >0){
				group = json.getString("group");
				grouparr = group.split(",");
			}
			String[] str = elasticsearchStatisticsService.libArr(datasourceArr, grouparr);
			resultEntity.setValue(true,"success","",str);
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
			String req = request.getParameter("req");
			JSONObject json = JSONObject.fromObject(req);
			//获取是否包含子馆
			String isContainSubLib = json.optString("isContainSubLib");
			//获得对应馆的所有设备 start
			Integer libidx = json.getInt("library_idx");
			String libId = json.getString("lib_id").toLowerCase();
            Integer operator_type = json.getInt("operator_type");
			//获取当前图书馆的子馆 start
			List<Integer> libIdxs = new ArrayList<Integer>();//图书馆idx
			libIdxs.add(libidx);
			List<String> libIds = new ArrayList<String>();//图书馆id
			libIds.add(libId);
			//如果页面包含子馆，则查询子馆
			if("0".equals(isContainSubLib)){
				List<RelLibsEntity> list = elasticsearchStatisticsService.takeRelLibs(libidx);
				if(list!=null){
					for(RelLibsEntity rel : list){
						Integer tmpidx = rel.getSlave_lib_id();
						JSONObject libJson = elasticsearchStatisticsService.takeLib(tmpidx);
						if(libJson.get("lib_id")!=null){
							libIdxs.add(libJson.getInt("library_idx"));
							libIds.add(libJson.getString("lib_id").toLowerCase());
						}
					}
				}
			}
			//获取当前图书馆的子馆 start
			StringBuffer sb = new StringBuffer();
            String[] datasourceArr = null;
            if(operator_type == 1){
                sb.append("*_"+json.getString("datasource")+",");
            }else{
                for(int tmp=0;tmp<libIdxs.size();tmp++){
                	sb.append(libIds.get(tmp) + "_*_"+json.getString("datasource")+",");
                }
            }

			if(sb.toString().endsWith(",")){
				sb.deleteCharAt(sb.length()-1);
			}
			datasourceArr = sb.toString().split(",");
			String group = "";
			String[] grouparr = null;
			if(json.getString("group") !=null && json.getString("group").length() >0){
				group = json.getString("group");
				grouparr = group.split(",");
			}
			String[] str = elasticsearchStatisticsService.devArr(datasourceArr, grouparr);
			resultEntity.setValue(true,"success","",str);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
