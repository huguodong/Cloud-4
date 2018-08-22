package com.ssitcloud.business.devmgmt.controller;

import java.util.HashMap;
import java.util.Map;








import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;














import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.common.service.OperationLogService;
import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceDBsyncConfigService;
import com.ssitcloud.common.entity.OperationLogEntity;
import com.ssitcloud.common.entity.ResultEntity;


@Controller
@RequestMapping(value="dbsynconf")
public class DeviceDBsyncConfigController {

	@Resource
	DeviceDBsyncConfigService dbsyncService;
	
	@Resource
	OperationLogService operationLogService;
	
	@RequestMapping(value= {"SelDeviceDbsync"})
	@ResponseBody
	public ResultEntity SelDeviceDbsync(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		String resps = dbsyncService.SelDeviceDbsync(json);
		result = JsonUtils.fromJson(resps, ResultEntity.class);	
		return result;
	}
	
	
	@RequestMapping(value= {"AddNewDeviceDbsync"})
	@ResponseBody
	public ResultEntity AddNewDeviceDbsync(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		String resps = dbsyncService.AddDeviceDbsync(json);
		result = JsonUtils.fromJson(resps, ResultEntity.class);	
		return result ;
	}
	
	
	@RequestMapping(value= {"UpdDeviceDbsync"})
	@ResponseBody
	public ResultEntity UpdDeviceDbsync(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
	
		String resps = dbsyncService.UpdDeviceDbsync(json);
		result = JsonUtils.fromJson(resps, ResultEntity.class);	
		return result;
	}
	
	@RequestMapping(value= {"DelDeviceDbsync"})
	@ResponseBody
	public ResultEntity DelDeviceDbsync(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		String resps = dbsyncService.delDevDBsync(json);
		result = JsonUtils.fromJson(resps, ResultEntity.class);	
		return result;
	}
	
	@RequestMapping(value= {"UpdDevConfig"})
	@ResponseBody
	public ResultEntity UpdDevConfig(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		String resps = dbsyncService.updDevConfig(json);
		result = JsonUtils.fromJson(resps, ResultEntity.class);	
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value= {"AddNewDeviceDbsyncTemp"})
	@ResponseBody
	public ResultEntity AddNewDeviceDbsyncTemp(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			
			Map<String, String> mapinfo = new HashMap<>(); 
			mapinfo.put("TempInfo", JsonUtils.toJson(operMap.get("TempInfo")));
			mapinfo.put("TempConfigInfo", JsonUtils.toJson(operMap.get("TempConfigInfo")));
			
			
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(mapinfo));
			String resps = dbsyncService.addDbsynctemp(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			OperationLogEntity logEntity = new OperationLogEntity();
			logEntity.setOperator_idx(Integer.valueOf(operMap.get("operator_idx").toString()) );
			logEntity.setClient_ip(operMap.get("client_ip").toString() == null ?"null":operMap.get("client_ip").toString());
			logEntity.setClient_port(operMap.get("client_port").toString() == null ?"null":operMap.get("client_port").toString());
			logEntity.setOperation_cmd("0102020801");
			if(result.getState()){
				logEntity.setOperation_result("true");
				logEntity.setOperation(result.getRetMessage());
			}else{
				//发生错误或者操作失败
				logEntity.setOperation_result("false");
				logEntity.setOperation(result.getRetMessage());
			}
			operationLogService.addOperationlog(JsonUtils.toJson(logEntity));
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	
	/**
	 * 删除设备数据同步模板
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年7月19日 下午5:23:16 
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= {"UpdDeviceDbsyncTemp"})
	@ResponseBody
	public ResultEntity UpdDeviceDbsyncTemp(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			
			Map<String, String> mapinfo = new HashMap<>(); 
			mapinfo.put("TempInfo", JsonUtils.toJson(operMap.get("TempInfo")));
			mapinfo.put("TempConfigInfo", JsonUtils.toJson(operMap.get("TempConfigInfo")));
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(mapinfo));
			String resps = dbsyncService.updDbsynctemp(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			OperationLogEntity logEntity = new OperationLogEntity();
			logEntity.setOperator_idx(Integer.valueOf(operMap.get("operator_idx").toString()) );
			logEntity.setClient_ip(operMap.get("client_ip").toString() == null ?"null":operMap.get("client_ip").toString());
			logEntity.setClient_port(operMap.get("client_port").toString() == null ?"null":operMap.get("client_port").toString());
			logEntity.setOperation_cmd("0102020803");
			if(result.getState()){
				logEntity.setOperation_result("true");
				logEntity.setOperation(result.getRetMessage());
			}else{
				logEntity.setOperation_result("false");
				logEntity.setOperation(result.getRetMessage());
			}
			operationLogService.addOperationlog(JsonUtils.toJson(logEntity));
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除设备数据同步模板
	 *
	 * <p>2016年7月19日 下午5:23:16 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= {"DelDeviceDbsyncTemp"})
	@ResponseBody
	public ResultEntity DelDeviceDbsyncTemp(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			
			Map<String, String> map = new HashMap<>(); 
			map.put("json", JsonUtils.toJson(operMap.get("idx")));
			String resps = dbsyncService.delDbsynctemp(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);	
			OperationLogEntity logEntity = new OperationLogEntity();
			logEntity.setOperator_idx(Integer.valueOf(operMap.get("operator_idx").toString()) );
			logEntity.setClient_ip(operMap.get("client_ip").toString() == null ?"null":operMap.get("client_ip").toString());
			logEntity.setClient_port(operMap.get("client_port").toString() == null ?"null":operMap.get("client_port").toString());
			logEntity.setOperation_cmd("0102020802");
			if(result.getState()){
				logEntity.setOperation_result("true");
				logEntity.setOperation(result.getRetMessage());
			}else{
				logEntity.setOperation_result("false");
				logEntity.setOperation(result.getRetMessage());
			}
			operationLogService.addOperationlog(JsonUtils.toJson(logEntity));
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	
	/**
	 * 查询设备数据同步模板
	 *
	 * <p>2016年7月19日 下午5:23:31 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value= {"SelDeviceDbsyncTemp"})
	@ResponseBody
	public ResultEntity SelDeviceDbsyncTemp(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		Map<String, String> map = new HashMap<>(); 
		map.put("json", json);
		String resps = dbsyncService.selDbsynctemp(map);
		result = JsonUtils.fromJson(resps, ResultEntity.class);	
		return result;
	}
}
