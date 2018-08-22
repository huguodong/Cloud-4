package com.ssitcloud.view.devmgmt.controller;

import java.io.BufferedOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DbSyncTempParam;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.devmgmt.entity.DevicePageEntity;
import com.ssitcloud.devmgmt.entity.ExtTempParam;
import com.ssitcloud.devmgmt.entity.MetadataLogicObjEntity;
import com.ssitcloud.devmgmt.entity.MonitorTempParam;
import com.ssitcloud.devmgmt.entity.PlcLogicObjEntity;
import com.ssitcloud.devmgmt.entity.RunTempParam;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.service.UserService;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.ExportExcelUtils;
import com.ssitcloud.view.common.util.FileUtil;
import com.ssitcloud.view.common.util.I18nUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.LogUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.common.util.WebUtil;
import com.ssitcloud.view.devmgmt.service.DeviceMonConfService;
import com.ssitcloud.view.devmgmt.service.DeviceService;


@Controller
//@RequestMapping(value="device")
public class DeviceController extends BasicController{

	
	@Resource
	private DeviceService deviceService;
	
	@Resource
	private UserService userService;

	@Resource
	private DeviceMonConfService deviceMonConfService;
	
	@RequestMapping(value={"/device/main"})
	public String main(HttpServletRequest request){
		Operator operator = getCurrentUser();
		request.setAttribute("operatorType", operator.getOperator_type());
		return "/page/devmgmt/devicemgmt";
	}
	
	/**
	 * 翻页执行的方法
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月5日
	 */
	@RequestMapping(value={"/device/selectPage"})
	@ResponseBody
	public ResultEntity selectPage(HttpServletRequest request,String req){
		DevicePageEntity devPage=deviceService.SelectDeviceByPage(req);
		ResultEntity result=new ResultEntity();
		result.setResult(devPage);
		return result;
	}
	/**
	 * 从mongodb中获取设备的状态信息 
	 * @Description: TODO
	 * @param @param request
	 * @param @param req  ={["device_id":"1","device_id":"2",......]}
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月5日
	 */
	@RequestMapping(value={"/device/selectDeviceState"})
	@ResponseBody
	public ResultEntity selectDeviceState(HttpServletRequest request,String req){
		return deviceService.selectDeviceState(req);
	}
	/**
	 * 取得设备上的书架状态信息
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月9日
	 */
	@RequestMapping(value={"/device/selectBookrackState"})
	@ResponseBody
	public ResultEntity selectBookrackState(HttpServletRequest request,String req){
		return deviceService.selectBookrackState(req);
	}
	
	
	/**
	 * 点击详情 页面跳转
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ModelAndView  
	 * @throws
	 * @author lbh
	 * @date 2016年5月10日
	 */
	@RequestMapping(value={"/device/deviceDetail"})
	public ModelAndView deviceDetail(HttpServletRequest request,String req){
		Assert.notNull(request.getParameter("device_idx"));
		DeviceEntity deviceEntity=new DeviceEntity();
		deviceEntity.setDevice_idx(Integer.parseInt(request.getParameter("device_idx")));
		deviceEntity.setDevice_id(request.getParameter("device_id"));
		String cardNum=request.getParameter("cardNum");
		Map<String, Object> model=new HashMap<>();
		model.put("device", deviceEntity);
		model.put("cardNum", cardNum);
		return new ModelAndView("/page/devmgmt/machine-detail",model);
	}
	/**
	 * 在线设备日志查询
	 * @param request
	 * @author lbh
	 * @return
	 */
	@RequestMapping(value={"/device/checklogonline"})
	@ResponseBody
	public ModelAndView checklogonline(HttpServletRequest request){
		Map<String, Object> model=new HashMap<>();
		model.put("device_id",request.getParameter("device_id"));
		return new ModelAndView("/page/devmgmt/check-logonline",model);
	}
	
	/**
	 * 将开关机 获取日志 维护锁屏 取消操作 命令发送到同步程序<br/>
	 * 
	 * 1关机 2 重启 3获取日志4远程维护锁屏 5取消操作<br/>
	 * 
	 * @methodName: sendOrder
	 * @param request
	 * @param req
	 * @return
	 * @returnType: RespEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"/device/sendOrder"})
	@ResponseBody
	public ResultEntity sendOrder(HttpServletRequest request,String req){
		LogUtils.info("发送指令："+req);
		ResultEntity resp=new ResultEntity();
		try {
			String re=deviceService.sendOrder(req);
			if(StringUtils.hasText(re)){
				resp=JsonUtils.fromJson(re, ResultEntity.class);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return resp;
	}
	/**
	 * 查询设备端日志记录
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月7日
	 */
	@RequestMapping(value={"/device/queryDeviceLog"})
	@ResponseBody
	public ResultEntity queryDeviceLog(HttpServletRequest request,String req){
		LogUtils.info("发送指令："+req);
		ResultEntity resp=new ResultEntity();
		try {
			String re=deviceService.queryDeviceLog(req);
			if(StringUtils.hasText(re)){
				resp=JsonUtils.fromJson(re, ResultEntity.class);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resp;
	}
	

	/**
	 * 查询设备相关信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/device/SelectDevice"})
	@ResponseBody
	public ResultEntity SelectDeviceinfo(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			String resps = deviceService.SelDeviceinfo(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	

	/**
	 * 删除相关设备信息
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value = {"/device/DeleteDevice"})
	@ResponseBody
	public ResultEntity DeleteDevice(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceService.DeleteDeviceinfo(json);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DELETE_DEVICE);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}

	
	/**
	 * 获取所有的ext模板数据
	 *
	 * <p>2016年4月25日 下午6:42:31
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/device/getAllExtTempList")
	@ResponseBody
	public ResultEntity getAllExtTempList(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (!StringUtils.isEmpty(json)) {
				String id = json;
				json = "{\"device_type\":\""+id+"\"}";
			}else {
				json = "";
			}
			String response =deviceService.getAllExtTempList(json);
			resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			List<ExtTempParam> extConfList = new ArrayList<>();
			if (resultEntity.getState()) {
				String res = JsonUtils.toJson(resultEntity.getResult());
				extConfList = JsonUtils.fromJson(res, new TypeReference<List<ExtTempParam>>() {});
			}
			resultEntity.setResult(extConfList);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 获取所有的运行配置模板信息
	 *
	 * <p>2016年4月26日 下午5:34:12
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/device/getAllRunTempList")
	@ResponseBody
	public ResultEntity getAllRunTempList(HttpServletRequest request,String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (!StringUtils.isEmpty(json)) {
				String id = json;
				json = "{\"device_type\":\""+id+"\"}";
			}else {
				json = "";
			}
			String response = deviceService.getAllRunTempList(json);
			resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			List<RunTempParam> runTempParams = new ArrayList<>();
			if (resultEntity.getState()) {
				String res = JsonUtils.toJson(resultEntity.getResult());
				runTempParams = JsonUtils.fromJson(res, new TypeReference<List<RunTempParam>>() {});
			}
			resultEntity.setResult(runTempParams);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 获取所有的运行配置模板信息
	 *
	 * <p>2016年4月26日 下午5:34:12
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/device/getAllMonitorTempList")
	@ResponseBody
	public ResultEntity getAllMonitorTempList(HttpServletRequest request,String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (!StringUtils.isEmpty(json)) {
				String id = json;
				json = "{\"device_type\":\""+id+"\"}";
			}else {
				json = "";
			}
			String response = deviceService.getAllMonitorTempList(json);
			resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			List<MonitorTempParam> monitorTempParams = new ArrayList<>();
			if (resultEntity.getState()) {
				String res = JsonUtils.toJson(resultEntity.getResult());
				monitorTempParams = JsonUtils.fromJson(res, new TypeReference<List<MonitorTempParam>>() {});
			}
			resultEntity.setResult(monitorTempParams);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 获取所有的运行配置模板信息
	 *
	 * <p>2016年4月26日 下午5:34:12
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/device/getAllDbSyncTempList")
	@ResponseBody
	public ResultEntity getAllDbSyncTempList(HttpServletRequest request,String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (!StringUtils.isEmpty(json)) {
				String id = json;
				json = "{\"device_type\":\""+id+"\"}";
			}else {
				json = "";
			}
			String response = deviceService.getAllDbSyncTempList(json);
			resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			List<DbSyncTempParam> dbSyncTempParams = new ArrayList<>();
			if (resultEntity.getState()) {
				String res = JsonUtils.toJson(resultEntity.getResult());
				dbSyncTempParams = JsonUtils.fromJson(res, new TypeReference<List<DbSyncTempParam>>() {});
			}
			resultEntity.setResult(dbSyncTempParams);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 获取当前选中的设备信息
	 * @param request
	 * @param json
	 * @author hwl
	 * @return 
	 */
	@RequestMapping("/device/SelectInfo")
	@ResponseBody
	public ResultEntity SelectInfo(HttpServletRequest request,String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String response = deviceService.QueryDevice(json);
			resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
		
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}

	/**
	 * 查询 图书馆信息 如{library_idx:"1"}
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月7日
	 */
	@RequestMapping(value={"/device/selLibraryByIdxOrId"})
	@ResponseBody
	public ResultEntity selLibraryByIdxOrId(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		
		try {
			result=deviceService.selLibraryByIdxOrId(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}		
	/**
	 * 获取箱子信息 req = {["device_id1","device_id2","device_id3","device_id4"]}
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月9日
	 */
	@RequestMapping(value={"/device/selectBinState"})
	@ResponseBody
	public ResultEntity selectBinState(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.selectBinState(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 获取Ext SSL信息 req = {["device_id1","device_id2","device_id3","device_id4"]}
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月10日
	 */
	@RequestMapping(value={"/device/selectDeviceExtState"})
	@ResponseBody
	public ResultEntity selectDeviceExtState(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.selectDeviceExtState(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 获取功能状态信息  SSL信息 req = {["device_id1","device_id2","device_id3","device_id4"]}
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月10日
	 */
	@RequestMapping(value={"/device/selectSoftState"})
	@ResponseBody
	public ResultEntity selectSoftState(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceService.selectSoftState(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * get Permession  Info by operator_id or operator_idx 
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/device/SelPermissionByDeviceId"})
	@ResponseBody
	public ResultEntity SelPermissionByDeviceId(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			//获取用户的idx
			result=deviceService.selOperatorByOperIdOrIdx(req);
			if(result!=null&&result.getState()==true){
				Map<String,Object> oper=(Map<String,Object>) result.getResult();
				if(oper!=null){
					if(oper.get("operator_idx")==null){
						result.setMessage("operator_idx is not found");
						return result;
					}
					Integer operatorIdx=(Integer) oper.get("operator_idx");
					if(operatorIdx!=null){
						//获取用户的权限
						String resultPermession=userService.SelPermissionByOperIdx(operatorIdx.toString());
						if(StringUtils.hasLength(resultPermession)){
							result=JsonUtils.fromJson(resultPermession, ResultEntity.class);
						}
					}
				}
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	

	/**
	 * 查询数据同步config
	 * @param request
	 * @param json
	 * @author hwl
	 * @return
	 */
	@RequestMapping(value={"/device/SelectDBsyncInfo"})
	@ResponseBody
	public ResultEntity SelectDBsyncInfo(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			String response = deviceService.SelectDBsync(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}		
	
	/**
	 * 更新数据同步config
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value={"/device/UpdateDBsyncConfig"})
	@ResponseBody
	public ResultEntity UpdateDBsyncConfig(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.UpdateDBsync(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}		
	
	/**
	 * 删除数据同步Config
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月13日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/DeleteDBsyncConfig"})
	@ResponseBody
	public ResultEntity DeleteDBsyncConfig(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.DeleteDBsyncConfig(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 更新设备Config
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月13日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/UpdateDeviceConfig"})
	@ResponseBody
	public ResultEntity UpdateDeviceConfig(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.UpdateDeviceConfig(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 添加数据同步配置
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月13日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/InsertDBsyncConfig"})
	@ResponseBody
	public ResultEntity InsertDBsyncConfig(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.InsertDBsync(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 更新设备监控config
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value={"/device/UpdateMonitorConfig"})
	@ResponseBody
	public ResultEntity UpdateMonitorConfig(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.UpdateMonitor(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}		
	
	/**
	 * 删除设备监控配置
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月13日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/DeleteMonitorConfig"})
	@ResponseBody
	public ResultEntity DeleteMonitorConfig(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.DeleteMonitor(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 添加设备监控配置
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月13日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/InsertMonitorConfig"})
	@ResponseBody
	public ResultEntity InsertMonitorConfig(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.InsertMonitor(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 带参数 查询 设备
	 * 格式 req=
	 * {	
	 *   "machineType":machineType,    //设备类型
		 "machineState":machineState,  //设备状态
		 "keyWord":keyWord 			   //关键字 （设备名）
	   }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/device/queryDeviceByParam"})
	@ResponseBody
	public ResultEntity queryDeviceByParam(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.queryDeviceByParam(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * req={device_id:""}
	 * 定时查询 命令执行结果
	 * @param request
	 * @param req
	 * @author lbh
	 * @return
	 */
	@RequestMapping(value={"/device/queryControlResult"})
	@ResponseBody
	public ResultEntity queryControlResult(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.queryControlResult(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@Value("${plcSorter}")
	private String plcSorter;//分拣机部件
	
	@Value("${plcReturn}")
	private String plcReturn;//还书机部件
	
	/**
	 * 跳转进入设备信息编辑页面
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年6月13日
	 * @author hwl
	 * @author lbh
	 */
	@RequestMapping("EditDeviceInfo")
	public ModelAndView EditDeviceInfo(String req,HttpServletRequest request ){
		String url = "page/devmgmt/deviceedit";
		//這裡獲取監控配置模板數據
		Map<String, Object> model=new HashMap<>();
		ResultEntity result=deviceMonConfService.SelMetadataLogicObject(req);
		
		ResultEntity resultPlc=deviceMonConfService.SelPlcLogicObjectEntity();
		if(resultPlc!=null&&resultPlc.getState()==true){
			List<PlcLogicObjEntity> plcLogicObjs=JsonUtils.convertMap(resultPlc.getResult(), new TypeReference<List<PlcLogicObjEntity>>() {});
			model.put("plcLogicObjs",plcLogicObjs);
		}
		model.put("plcReturn", plcReturn);
		model.put("plcSorter", plcSorter);
		if(result!=null&&result.getState()==true){
			String json=JsonUtils.toJson(result.getResult());
			List<MetadataLogicObjEntity> metadataLogicObjects= JsonUtils.fromJson(json, new TypeReference<List<MetadataLogicObjEntity>>() {});
			for(MetadataLogicObjEntity m:metadataLogicObjects){
				m.setLogic_obj_desc(I18nUtil.converLanguage(m.getLogic_obj_desc()));
			}
			model.put("metadataLogicObjects",metadataLogicObjects);
		}
		String id = request.getParameter("id");
		Map map = JsonUtils.fromJson(id, Map.class);
		String device_code = (String) map.get("device_code");
		if("".equals(device_code)||device_code==""||"undefined".equals(device_code)||device_code=="undefined"){
			url = "page/devmgmt/hydeviceedit";
		}else{
			url = "page/devmgmt/deviceedit";
		}
		return new ModelAndView(url,model);
	}
	
	/**
	 * 跳转进入设备配置页面
	 * 
	 */
	@RequestMapping("ConfigDeviceInfo")
	public ModelAndView ConfigDeviceInfo(String req, HttpServletRequest request) {
		// 获取配置模块数据
		Map<String, Object> model = new HashMap<>();

		return new ModelAndView("page/devmgmt/deviceconfig", model);
	}
	
	/**
	 * 查询设备监控config
	 * @param request
	 * @param json
	 * @author hwl
	 * @return
	 */
	@RequestMapping(value={"/device/SelMonitordata"})
	@ResponseBody
	public ResultEntity SelMonitordata(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			String response = deviceService.SelectMonitor(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 查询设备外设配置config
	 * @param request
	 * @param json
	 * @author hwl
	 * @return
	 */
	@RequestMapping(value={"/device/SelExtdata"})
	@ResponseBody
	public ResultEntity SelExtdata(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			String response = deviceService.SelectExt(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询设备运行配置config
	 * @param request
	 * @param json
	 * @author hwl
	 * @return
	 */
	@RequestMapping(value={"/device/SelRundata"})
	@ResponseBody
	public ResultEntity SelRundata(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			String response = deviceService.SelectRun(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	@RequestMapping("/device/getAllLogicObjAndExtModel")
	@ResponseBody
	public ResultEntity getAllLogicObjAndExtModel(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (json == null) {
				json = "";
			}
			String response = deviceService.selAllExtModelAndLogicObj(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "连接服务器失败","","");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 添加外设配置自定义数据
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月16日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/InsertExtConfigData"})
	@ResponseBody
	public ResultEntity InsertExtConfigData(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.InsertExtdata(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 先删除，然后新增外设自定义数据
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月16日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/UpdateExtConfigData"})
	@ResponseBody
	public ResultEntity UpdateExtConfigData(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.UpdateExtdata(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除自定义外设配置
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月16日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/DeleteExtConfigData"})
	@ResponseBody
	public ResultEntity DeleteExtConfigData(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.DeleteExtdata(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 添加设备运行配置自定义数据
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月16日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/InsertRunConfigData"})
	@ResponseBody
	public ResultEntity InsertRunConfigData(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.InsertRundata(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除和添加设备运行配置自定义数据
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月16日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/UpdateRunConfigData"})
	@ResponseBody
	public ResultEntity UpdateRunConfigData(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.UpdateRundata(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除设备运行配置数据(在设备编辑中用来删除自定义数据)
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月16日
	 * @author hwl
	 */
	@RequestMapping(value={"/device/DeleteRunConfigData"})
	@ResponseBody
	public ResultEntity DeleteRunConfigData(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		
		try {
			String response = deviceService.DeleteRundata(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 设备管理点击保存按钮 保存数据
	 * 
	 *  需要修改到的表有
		device_config[变更模板（不是模板改数据或者数据该模板）]
		device[设备类型/图书馆ID/设备物流编号/设备流通地点/设备描述/设备名称/设备ID]
		device_group[设备分组]
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/device/UpdDeviceMgmtPage"})
	@ResponseBody
	public ResultEntity UpdDeviceMgmtPage(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			 result = deviceService.deviceUpd(req);
			 if(result.getState())
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_UPDATE_DEVICE);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	@RequestMapping(value={"/device/UpdHyDeviceMgmtPage"})
	@ResponseBody
	public ResultEntity UpdHyDeviceMgmtPage(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			 result = deviceService.hydeviceUpd(req);
			 if(result.getState())
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_UPDATE_DEVICE);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 查询设备有没有自定义参数配置
	 * @param request
	 * @param req={"device_idx":deviceIdx,"configName":configName,"libIdx":""}
	 * @return
	 */
	@RequestMapping(value={"/device/checkAllConfigDataByDevIdx"})
	@ResponseBody
	public 	ResultEntity checkAllConfigDataByDevIdx(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			 result = deviceService.checkAllConfigDataByDevIdx(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/***
	 * 比较 监控配置模板数据 和 修改后的数据是否一致
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/device/compareMonitorConfig"})
	@ResponseBody
	public 	ResultEntity compareMonitorConfig(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			 result = deviceService.compareMonitorConfig(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 获取ACS模板数据
	 *
	 * <p>2016年6月30日 上午10:07:03 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/device/getACSTempList")
	@ResponseBody
	public ResultEntity getACSTempList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isEmpty(req) || req.equals("{}")){
				resultEntity.setValue(false, "", "参数不能为空", "");
			}
			resultEntity = deviceService.getACSTempList(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	/**
	 * 根据device_idx加载设备的ACS配置
	 * 
	 * req={deviceIdx:"....."}
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/device/loadAcsLogininfo")
	@ResponseBody
	public ResultEntity loadAcsLogininfo(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			 result = deviceService.loadAcsLogininfo(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping("/device/devicebatching")
	public String devicebatching(){
		return "/page/devmgmt/devicebatching";
	}
	
	/**
	 * 获取图书馆的设备信息，进行批处理操作， 分页显示， 可以根据参数查询
	 *
	 * <p>2016年9月20日 上午11:55:16 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/device/getLibraryDevicesByPage")
	@ResponseBody
	public ResultEntity getLibraryDevicesByPage(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			//library_id   deviceType  deviceId deviegroup
			String req = request.getParameter("json");
			Operator operator = getCurrentUser();
			if (operator!=null ) {
				if(Operator.SSITCLOUD_ADMIN.equals(operator.getOperator_type()) 
						|| Operator.SSITCLOUD_MANAGER.equals(operator.getOperator_type())) {
					
					result = deviceService.getLibraryDevicesByPage(req);
				}else{
					result.setValue(false, "权限不足", "", "");
					return result;
				}
			}else{
				result.setValue(false, "权限不足", "", "");
				return result;
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 根据设备ids查询设备的ip
	 *
	 * <p>2016年9月21日 下午6:51:08 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/device/queryDeviceIps")
	@ResponseBody
	public ResultEntity queryDeviceIps(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("json");
			if(StringUtils.hasText(req)){
				result = deviceService.queryDeviceIps(req);
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	
	
	/**
	 * 将图书馆的设备转移到另一个图书馆
	 *
	 * <p>2016年9月21日 上午10:30:31 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/device/transferToNewLib")
	@ResponseBody
	public ResultEntity transferToNewLib(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			//library_id   deviceType  deviceId deviegroup
			String req = request.getParameter("json");
			if(!StringUtils.hasText(req)){
				result.setValue(false, "参数不能为空", "", "");
				return result;
			}
			Operator operator = getCurrentUser();
			if (operator!=null ) {
				if(Operator.SSITCLOUD_ADMIN.equals(operator.getOperator_type()) 
						|| Operator.SSITCLOUD_MANAGER.equals(operator.getOperator_type())) {
					
					String operator_idx = operator.getOperator_idx();//操作员的idx
					Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
					map.put("operator_idx", operator_idx);
					map.put("client_ip", WebUtil.getIpAddr(request));
					map.put("client_port", request.getRemotePort());
					
					result = deviceService.transferToNewLib(JsonUtils.toJson(map));
				}else{
					result.setValue(false, "权限不足", "", "");
					return result;
				}
			}else{
				result.setValue(false, "权限不足", "", "");
				return result;
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询所有的地区信息
	 *
	 * <p>2017年9月7日 上午10:18:14 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/device/queryAllRegion")
	@ResponseBody
	public ResultEntity queryAllRegion(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		resultEntity = deviceService.queryAllRegion("");
		return resultEntity;
	}
	
	
	/**
	 * 获取设备的地点位置信息
	 *
	 * <p>2017年9月8日 下午3:41:49 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/device/queryDeviceRegion")
	@ResponseBody
	public ResultEntity queryDeviceRegion(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String devIdx = request.getParameter("devIdx");
		if(!StringUtils.hasText(devIdx)){
			resultEntity.setValue(false, "参数不能为空");
			return resultEntity;
		}
		String req = "{\"device_idx\":\""+devIdx+"\"}";
		resultEntity = deviceService.queryDeviceRegion(req);
		return resultEntity;
	}
	/**
	 * <p>根据图书馆id来获取查询设备的位置信息
	 * <p>create by liuwei
	 * @param request
	 * @return 
	 */
	@RequestMapping("/GetDevicePosition")
	@ResponseBody
	public ResultEntity GetDevicePosition(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			String response = deviceService.GetDevicePosition(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * <p>删除图书馆的位置信息
	 * <p>create by liuwei
	 * @param request
	 * @return 
	 */
	@RequestMapping("/device/deleteLibraryPosition")
	@ResponseBody
	public ResultEntity deleteLibraryPosition(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			String response = deviceService.deleteLibraryPosition(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * <p>根据图书馆id来获取查询设备的位置信息
	 * <p>create by liuwei
	 * @param request
	 * @return 
	 */
	@RequestMapping("/getLibPosition")
	@ResponseBody
	public ResultEntity getLibPosition(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			String response = deviceService.getLibPosition(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/*
	 * 海洋设备文件方式批量注册
	 */
	@RequestMapping("/device/devicesRegister")
	@ResponseBody
	public ResultEntity devicesRegister(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultEntity resultEntity = new ResultEntity();
		String import_tpl_idx = request.getParameter("import_tpl_idx");
		String import_tpl_type = request.getParameter("import_tpl_type");
		JSONObject params = new JSONObject();
		params.put("import_tpl_idx",import_tpl_idx);
		params.put("import_tpl_type",import_tpl_type);
		
		String retMessage = deviceService.devicesRegister(file,params.toString());
		
		if(!"".equals(retMessage)&&retMessage!=null){
			response.setCharacterEncoding("utf-8");
            //设置响应内容的类型
            response.setContentType("text/plain");
            //设置文件的名称和格式
            response.addHeader(
                    "Content-Disposition",
                    "attachment; filename="
                            + FileUtil.genAttachmentFileName("errorMessage"+ "_", "JSON_FOR_UCC_")
                            + ".txt");//通过后缀可以下载不同的文件格式
			BufferedOutputStream buff = null;
            ServletOutputStream outStr = null;
			try {
                outStr = response.getOutputStream();
                buff = new BufferedOutputStream(outStr);
                //String jsonString = "";
                //jsonString=jsonString.substring(0,jsonString.length()-1);
                buff.write(retMessage.getBytes("UTF-8"));
                buff.flush();
            } catch (Exception e) {
            	resultEntity.setState(false);
                //LOGGER.error("导出文件文件出错，e:{}",e);
            } finally {
            	try {
                    buff.close();
                    outStr.close();
                    resultEntity.setState(true);
            		return resultEntity;
                } catch (Exception e) {
                    //LOGGER.error("关闭流对象出错 e:{}",e);
                }
            }
		}
		resultEntity.setState(true);
		return resultEntity;
	}
}
