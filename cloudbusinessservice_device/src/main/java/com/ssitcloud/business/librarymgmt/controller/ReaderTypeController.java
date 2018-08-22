package com.ssitcloud.business.librarymgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.librarymgmt.service.ReaderTypeService;
import com.ssitcloud.common.entity.OperationLogEntity;
import com.ssitcloud.common.entity.ResultEntity;
/**
 * 
 * @comment 
 * @date 2016年5月17日
 * @author hwl
 */

@Controller
@RequestMapping("/readertype")
public class ReaderTypeController {

	@Resource
	ReaderTypeService readerTypeService;
	
	/**
	 * 分页查询读者流通类型
	 * @comment
	 * @param request
	 * @return
	 * @data 2016年5月18日`
	 * @author hwl
	 */
	@RequestMapping(value = {"SelectReadertype"})
	@ResponseBody
	public ResultEntity SelectReaderType(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			String json_info = request.getParameter("json");
			String page_info = request.getParameter("page");
			map.put("json", json_info);
			map.put("page", page_info);
			//查询读者流通类型信息
			String resps = readerTypeService.Selectreadertype(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	/**
	 * 根据图书馆id和读者类型 模糊查询
	 * @comment
	 * @param request
	 * @return
	 * @data 2016年5月18日`
	 * @author hwl
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"QueryReaderTypeByFuzzy"})
	@ResponseBody
	public ResultEntity QueryReaderTypeByFuzzy(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		ResultEntity lib_result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			String json_info = request.getParameter("json");
			String page_info = request.getParameter("page");
			map.put("page", page_info);
			//查询读者流通类型信息    LIBRARY_IDX
			//获取图书馆lib_id
			Map<String,Object> rTypeEntityMap = JsonUtils.fromJson(json_info, Map.class);
		    LibraryEntity libEntity = new LibraryEntity();
		    if(rTypeEntityMap.containsKey("lib_id")){
		    	 libEntity.setLib_id(rTypeEntityMap.get("lib_id").toString());
		    }
			String libinfo = JsonUtils.toJson(libEntity);
			//查询图书馆信息 （模糊查询 ）library_idx list集合
			lib_result = readerTypeService.selLibraryIDByFuzzy(libinfo);
			if(lib_result.getResult()==null){
				return result;
			}else{
				if(rTypeEntityMap.containsKey("library_idx")){
					if("0".equals(rTypeEntityMap.get("library_idx").toString())||"[0]".equals(rTypeEntityMap.get("library_idx").toString())){//云平台
						map.put("library_idx", JsonUtils.toJson(lib_result.getResult()));
					}else{
						map.put("library_idx", JsonUtils.toJson(rTypeEntityMap.get("library_idx")));
					}
				}else{
					map.put("library_idx", "");
				}
				//没有查询条件的情况下
				if(StringUtils.isBlank(libEntity.getLib_id())&& rTypeEntityMap.containsKey("library_idx_arr")){//主馆和子馆
					map.put("library_idx", JsonUtils.toJson(rTypeEntityMap.get("library_idx_arr")));
				}
				if(rTypeEntityMap.containsKey("type_id")){
					map.put("type_id", rTypeEntityMap.get("type_id").toString());
				}
				if(rTypeEntityMap.get("type_distinction") !=null){
					map.put("type_distinction", rTypeEntityMap.get("type_distinction").toString());
				}
				String resps = readerTypeService.SelectreaderTypeByFuzzy(map);
				result = JsonUtils.fromJson(resps, ResultEntity.class);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	 
	/**
	 * 
	 * @comment 修改读者流通类型
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月17日`
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"UpdateReadertype"})
	@ResponseBody
	public ResultEntity UpdateReaderType(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		ResultEntity libresult = new ResultEntity();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			
			LibraryEntity lEntity = new LibraryEntity();
			Map<String, String> map = JsonUtils.fromJson(JsonUtils.toJson(operMap.get("readertype")), Map.class);
			lEntity.setLib_id(map.get("lib_id"));
			String libinfo = JsonUtils.toJson(lEntity);
			String lib_resp = readerTypeService.SelLibrary(libinfo);
			libresult = JsonUtils.fromJson(lib_resp, ResultEntity.class);
			if(libresult.getState()){
				List<Integer> idxs=(List<Integer>)libresult.getResult();
				if(CollectionUtils.isEmpty(idxs)){
					result.setRetMessage("没有对应的图书馆，请输入正确的馆ID");
					return result;
				}
				String libidx =JsonUtils.toJson(idxs);
				ObjectMapper mapper1 = new ObjectMapper();
				LibraryEntity libraryEntity = mapper1.readValue(libidx, LibraryEntity[].class)[0];
				map.put("library_idx", libraryEntity.getLibrary_idx().toString());
				map.remove("lib_id");
				String resps = readerTypeService.Updatereadertype(JsonUtils.toJson(map));
				result = JsonUtils.fromJson(resps, ResultEntity.class);
				
				if(result.getState()){
				//获取OperationLog参数，添加日志
					OperationLogEntity logEntity = new OperationLogEntity();
					logEntity.setOperator_idx(Integer.valueOf(operMap.get("operator_idx").toString()) );
					logEntity.setClient_ip(operMap.get("client_ip").toString() == null ?"null":operMap.get("client_ip").toString());
					logEntity.setClient_port(operMap.get("client_port").toString() == null ?"null":operMap.get("client_port").toString());
					logEntity.setOperation_cmd("0102010403");
					logEntity.setOperation_result("true");
					if("2".equals(map.get("type_distinction"))){
						logEntity.setOperation("馆IDX:"+libraryEntity.getLibrary_idx()+"|IDX:"+map.get("type_idx")+"|设备维护卡名:"+map.get("type_name")+"||");//馆IDX｜流通类型IDX｜流通类型名
						logEntity.setOperation_cmd("0102010503");
					}else{
						logEntity.setOperation("馆IDX:"+libraryEntity.getLibrary_idx()+"|流通类型IDX:"+map.get("type_idx")+"|流通类型名:"+map.get("type_name")+"||");//馆IDX｜流通类型IDX｜流通类型名
					}
					readerTypeService.addOperationlog(JsonUtils.toJson(logEntity));
				}
				
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 
	 * @comment 添加读者流通类型
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月17日
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"InsertReadertype"})
	@ResponseBody
	public ResultEntity InsertReaderType(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		ResultEntity libresult = new ResultEntity();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			LibraryEntity lEntity = new LibraryEntity();
			Map<String, String> map = JsonUtils.fromJson(JsonUtils.toJson(operMap.get("readertype")), Map.class);
			lEntity.setLib_id(map.get("lib_id"));
			String libinfo = JsonUtils.toJson(lEntity);
			String lib_resp = readerTypeService.SelLibrary(libinfo);
			libresult = JsonUtils.fromJson(lib_resp, ResultEntity.class);
			if(libresult.getState()){
				List<Integer> idxs=(List<Integer>)libresult.getResult();
				if(CollectionUtils.isEmpty(idxs)){
					result.setRetMessage("没有对应的图书馆，请输入正确的馆ID");
					return result;
				}
				String libidx =JsonUtils.toJson(idxs);
				ObjectMapper mapper1 = new ObjectMapper();
				LibraryEntity libraryEntity = mapper1.readValue(libidx, LibraryEntity[].class)[0];
				map.put("library_idx", libraryEntity.getLibrary_idx().toString());
				map.remove("lib_id");
				String resps = readerTypeService.Insertreadertype(JsonUtils.toJson(map));
				result = JsonUtils.fromJson(resps, ResultEntity.class);
				if(result.getState()){
				//获取OperationLog参数，添加日志
				OperationLogEntity logEntity = new OperationLogEntity();
				logEntity.setOperator_idx(Integer.valueOf(operMap.get("operator_idx").toString()) );
				logEntity.setClient_ip(operMap.get("client_ip").toString() == null ?"null":operMap.get("client_ip").toString());
				logEntity.setClient_port(operMap.get("client_port").toString() == null ?"null":operMap.get("client_port").toString());
				if(result.getResult()!=null && result.getResult().toString().equals("SYS_CARD_TYPE")){
					logEntity.setOperation_cmd("0102010501");
				}else{
					logEntity.setOperation_cmd("0102010401");
				}
				logEntity.setOperation_result("true");
				logEntity.setOperation(result.getRetMessage());//馆IDX｜流通类型IDX｜流通类型名
				
				readerTypeService.addOperationlog(JsonUtils.toJson(logEntity));
				}
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 
	 * @comment 删除读者流通类型
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月17日`
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"DeleteReadertype"})
	@ResponseBody
	public ResultEntity DeleteReaderType(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			
			String resps = readerTypeService.Deletereadertype(JsonUtils.toJson(operMap.get("idx")));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			if(result.getState()){
			//获取OperationLog参数，添加日志
			OperationLogEntity logEntity = new OperationLogEntity();
			logEntity.setOperator_idx(Integer.valueOf(operMap.get("operator_idx").toString()) );
			logEntity.setClient_ip(operMap.get("client_ip").toString() == null ?"null":operMap.get("client_ip").toString());
			logEntity.setClient_port(operMap.get("client_port").toString() == null ?"null":operMap.get("client_port").toString());
			logEntity.setOperation_cmd("0102010402");
			if(operMap.containsKey("type")){
				String type=operMap.remove("type").toString();
				if("SYS_CARD_TYPE".equals(type)){
					//删除维护卡
					logEntity.setOperation_cmd("0102010502");
				}
			}
			logEntity.setOperation_result("true");
			logEntity.setOperation(result.getRetMessage());
			readerTypeService.addOperationlog(JsonUtils.toJson(logEntity));
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 根据图书馆ID查询图书馆维护卡信息
	 *
	 * <p>2016年7月14日 下午2:01:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMaintenanceCard")
	@ResponseBody
	public ResultEntity queryMaintenanceCard(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
			}
			resultEntity = readerTypeService.queryMaintenanceCard(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据用户的idx查询用户的维护卡信息
	 *
	 * <p>2016年7月14日 下午2:01:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOperatorMaintenanceCard")
	@ResponseBody
	public ResultEntity queryOperatorMaintenanceCard(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
			}
			resultEntity = readerTypeService.queryOperatorMaintenanceCard(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	/**
	 * 修改操作员的维护卡信息
	 *
	 * <p>2016年7月14日 下午4:55:06 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateOperatorMaintenanceCard")
	@ResponseBody
	public ResultEntity updateOperatorMaintenanceCard(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
			}
			resultEntity = readerTypeService.updateOperatorMaintenanceCard(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
}
