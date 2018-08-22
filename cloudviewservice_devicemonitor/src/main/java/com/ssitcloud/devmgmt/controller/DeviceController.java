package com.ssitcloud.devmgmt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.client.utils.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.authentication.entity.MasterLibAndSlaveLibsEntity;
import com.ssitcloud.common.controller.BasicController;
import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.SyncContainerService;
import com.ssitcloud.common.service.UserService;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.common.util.SystemLogUtil;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.devmgmt.entity.DevicePageEntity;
import com.ssitcloud.devmgmt.entity.MetadataOrderEntity;
import com.ssitcloud.devmgmt.service.DeviceService;
import com.ssitcloud.devmgmt.service.LibraryService;
import com.ssitcloud.devmgmt.service.NavigationService;

@Controller
public class DeviceController extends BasicController{

	@Resource
	private DeviceService deviceService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private SyncContainerService syncContainerService;
	
	@Resource
	private LibraryService libraryService;
	
	@Resource
	private NavigationService navigationService;
	

	/**
	 * /page/devicebasic 机器监控页面
	 * 参数req={"pageSize":"",page:"","device_name":"","device_id":""}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/device/main"})
	public ModelAndView deviceMain(HttpServletRequest request,String req){
		
		//Order 命令集合
		List<MetadataOrderEntity> list=deviceService.queryMetadataOrder();
		
		//设备类型 从 下面主数据获取
		
		//主数据
		if(StringUtils.isEmpty(req)){
			req="{\"pageSize\":12}";
		}
		Map<String, Object> model=new HashMap<>();
		Operator oper=getCurrentUser();
		if(oper!=null){
			model.put("operator", oper);
			String operType=oper.getOperator_type();
			if(!Operator.SSITCLOUD_ADMIN.equals(operType)&&!Operator.SSITCLOUD_MANAGER.equals(operType)){
				String library_idx=oper.getLibrary_idx();
				//查询登陆账号的图书馆，如果是虚拟馆，跳转到虚拟馆界面
				JSONObject reqJson = new JSONObject();
				reqJson.put("library_idx", library_idx);
				ResultEntity entity = deviceService.querylibInfoByCurUser(reqJson.toString());
				if(entity != null && entity.getState()){
					Object object = entity.getResult();
					LibraryEntity libraryEntity = JsonUtils.fromJson(JsonUtils.toJson(object), LibraryEntity.class);
					if(libraryEntity.getLib_type() == 1){
						//跳转到虚拟馆界面
						return new ModelAndView("/page/devmgmt/virtualLibManager",model);
					}
				}
				
				//图书馆人员 只显示本馆的设备
				Map<String, Object> params=JsonUtils.fromJson(req, Map.class);
				params.put("library_idx", library_idx);
				if(Operator.LIBRARY_USER.equals(operType)){//设备组设备限制
					params.put("operator_idx_Limit", oper.getOperator_idx());
				}
				req=JsonUtils.toJson(params);
				// 设备数据集
				DevicePageEntity devPage = deviceService.SelectDeviceByPage(req);
				model.put("metaorders", list);
				model.put("pageJSON", JsonUtils.toJson(devPage));
				return new ModelAndView("/page/devmgmt/libManager",model);
			}
			//modify by hjc 查看的权限判断不在主页，放在登录的时候判断，没有查看权限的用户不让登陆
//			if(!Operator.SSITCLOUD_ADMIN.equals(operType)){
//				if(!checkPermession(Const.OPERCMD_MONITOR_MAINPAGE)){
//					//没有权限
//					return new ModelAndView("/page/error/403");
//				}
//			}
		}
		//设备数据集
		DevicePageEntity devPage=deviceService.SelectDeviceByPage(req);
		
		model.put("metaorders", list);
				
		model.put("pageJSON", JsonUtils.toJson(devPage));
		
		return new ModelAndView("/page/devmgmt/adminManager", model);
	}
	
	/**
	 * 查询命令
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/device/queryMetadataOrder"})
	@ResponseBody
	public List<MetadataOrderEntity> queryMetadataOrder(HttpServletRequest request,String req){
		
		//Order 命令集合
		List<MetadataOrderEntity> list=deviceService.queryMetadataOrder();
		
		return list;
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
		ResultEntity result=new ResultEntity();
		//主数据
		if(StringUtils.isEmpty(req)){
			req="{\"pageSize\":12}";
		}
		Map<String, Object> model=new HashMap<>();
		Operator oper=getCurrentUser();
		if(oper!=null){
			model.put("operator", oper);
			String operType=oper.getOperator_type();
			if(!Operator.SSITCLOUD_ADMIN.equals(operType)&&!Operator.SSITCLOUD_MANAGER.equals(operType)){
				//图书馆人员 只显示本馆的设备
				Map<String, Object> params=JsonUtils.fromJson(req, Map.class);
				String library_idx=oper.getLibrary_idx();
				params.put("library_idx", library_idx);
				req=JsonUtils.toJson(params);
			}
		}
		DevicePageEntity devPage=deviceService.SelectDeviceByPage(req);

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
		String library_id=request.getParameter("library_id");
		String device_type=request.getParameter("device_type");
		String rel_device_id = "";
		//SSITSZLIB_SSITSZLIB_jiehuanji 
		//String rel_device_id=StringUtils.delete(deviceEntity.getDevice_id(), library_id+"_");
		if(!StringUtils.isEmpty(deviceEntity.getDevice_id())){
			int index = deviceEntity.getDevice_id().indexOf("_")+1;
			rel_device_id = deviceEntity.getDevice_id().substring(index);
		}
		Map<String, Object> model=new HashMap<>();
		model.put("device", deviceEntity);
		model.put("cardNum", cardNum);
		model.put("device_type", device_type);
		model.put("library_id", library_id);
		model.put("rel_device_id", rel_device_id);
		return new ModelAndView("/page/devmgmt/machine-detail",model);
	}
	
	@RequestMapping(value={"/device/serviceDeviceDetail"})
	public ModelAndView serviceDeviceDetail(HttpServletRequest request,String req){
		Assert.notNull(request.getParameter("service_id"));
		String library_id=request.getParameter("library_id");
		String service_id = request.getParameter("service_id");
		String library_idx = request.getParameter("library_idx");
		
		Map<String, Object> model=new HashMap<>();
		model.put("library_id", library_id);
		model.put("service_id", service_id);
		model.put("library_idx", library_idx);
		return new ModelAndView("/page/devmgmt/server-detail",model);
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
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value={"/device/sendOrder"})
	@ResponseBody
	public ResultEntity sendOrder(HttpServletRequest request,String req){
		/***
		 * 先判断有 没有权限 远程控制、关机、重启、下载日志、设备锁定、取消操作
		 */
		LogUtils.info("发送指令："+req);
		ResultEntity resp=new ResultEntity();
		String msg="没有[%s]权限操作";
		//现在没有了总的控制权限指令
//		if(!checkPermession(Const.OPERCMD_MONITOR_REMOTE_CONTROL)){
//			resp.setRetMessage(String.format(msg, Const.OPERCMD_MONITOR_REMOTE_CONTROL));
//			resp.setResult(-1);
//			return resp;
//		}
		try {
			if(JSONUtils.mayBeJSON(req)){
				JsonNode node=JsonUtils.readTree(req);
				if(node!=null){
					if(node.elements().hasNext()){
						node=node.elements().next();
						JsonNode infoNode=node.get("control_info");
						if(infoNode!=null){
							if("1".equals(infoNode.asText())){//shutdown
								if(!checkPermession(Const.OPERCMD_MONITOR_SHUTDOWN)){
									resp.setRetMessage(String.format(msg, Const.OPERCMD_MONITOR_SHUTDOWN));
									resp.setResult(-1);
									return resp;
								}
							}else if("2".equals(infoNode.asText())){//restart
								if(!checkPermession(Const.OPERCMD_MONITOR_RESTART)){
									resp.setRetMessage(String.format(msg, Const.OPERCMD_MONITOR_RESTART));
									resp.setResult(-1);
									return resp;
								}
							}else if("3".equals(infoNode.asText())){//search log 
								if(!checkPermession(Const.OPERCMD_MONITOR_DOWN_LOG)){
									resp.setRetMessage(String.format(msg, Const.OPERCMD_MONITOR_DOWN_LOG));
									resp.setResult(-1);
									return resp;
								}
							}else if("4".equals(infoNode.asText())){//lock screen
								if(!checkPermession(Const.OPERCMD_MONITOR_DEV_LOCK)){
									resp.setRetMessage(String.format(msg, Const.OPERCMD_MONITOR_DEV_LOCK));
									resp.setResult(-1);
									return resp;
								}
							}else if("5".equals(infoNode.asText())){//cancal operation
								if(!checkPermession(Const.OPERCMD_MONITOR_CANCAL_OPER)){
									resp.setRetMessage(String.format(msg, Const.OPERCMD_MONITOR_CANCAL_OPER));
									resp.setResult(-1);
									return resp;
								}
							}
						}
						
						JsonNode startTimeNode=node.get("start_time");
						JsonNode endTimeNode=node.get("end_time");
						if(startTimeNode!=null&&endTimeNode!=null){
							DateTime sTime=new DateTime(DateUtils.parseDate(startTimeNode.asText()));
							DateTime eTime=new DateTime(DateUtils.parseDate(endTimeNode.asText()));
							int bDay=Days.daysBetween(sTime, eTime).getDays();
							if(bDay>7){
								resp.setState(false);
								resp.setRetMessage("查询的时间间隔不能大于7天");
								return resp;
							}
						}else if("3".equals(node.get("control_info").asText())){//查日志 并且没有设置时间的 后台设置上
							List<Map<String,Object>> list = JsonUtils.fromJson(req, List.class);
							for (Map<String, Object> map : list) {
								DateTime data=new DateTime();
								map.put("start_time",data.toString("yyyy-MM-dd"));
								map.put("end_time", data.minusDays(6).toString("yyyy-MM-dd"));
							}
							req = JsonUtils.toJson(list);
						}
					}
				}
			}
			
			String re=deviceService.sendOrder(req);
			if(JSONUtils.mayBeJSON(re)){
				resp=JsonUtils.fromJson(re, ResultEntity.class);
			}else{
				resp.setRetMessage(re);
			}
			SystemLogUtil.LogOperation(resp, getCurrentUser(), request, Const.MONITOR_MANAGER);
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return resp;
	}

	/**
	 * 查询设备端日志记录 该方法废弃
	 * 
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return
	 * @return ResultEntity
	 * @throws
	 * @author lbh
	 * @date 2016年5月7日
	 * 
	 */
	@RequestMapping(value = { "/device/queryDeviceLog" })
	@ResponseBody
	@Deprecated
	public ResultEntity queryDeviceLog(HttpServletRequest request, String req) {
		LogUtils.info("发送指令：" + req);
		ResultEntity resp = new ResultEntity();
		try {
			String re = deviceService.queryDeviceLog(req);
			if (StringUtils.hasText(re)) {
				resp = JsonUtils.fromJson(re, ResultEntity.class);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return resp;
	}

	/**
	 * 查询设备相关信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/device/SelectDevice" })
	@ResponseBody
	public ResultEntity SelectDeviceinfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			String resps = deviceService.SelDeviceinfo(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);

		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除相关设备信息
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/device/DeleteDevice" })
	@ResponseBody
	public ResultEntity DeleteDevice(HttpServletRequest request, String json) {
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceService.DeleteDeviceinfo(json);
			result = JsonUtils.fromJson(resps, ResultEntity.class);

		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 获取当前选中的设备信息
	 * 
	 * @param request
	 * @param json
	 * @author hwl
	 * @return
	 */
	@RequestMapping("/device/SelectInfo")
	@ResponseBody
	public ResultEntity SelectInfo(HttpServletRequest request, String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String response = deviceService.QueryDevice(json);
			resultEntity = JsonUtils.fromJson(response, ResultEntity.class);

		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			resultEntity.setValue(false, "failed",
					methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}

	/**
	 * 查询 图书馆信息 如{library_idx:"1"}
	 * 
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return
	 * @return ResultEntity
	 * @throws
	 * @author lbh
	 * @date 2016年5月7日
	 */
	@RequestMapping(value = { "/device/selLibraryByIdxOrId" })
	@ResponseBody
	public ResultEntity selLibraryByIdxOrId(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();

		try {
			result = deviceService.selLibraryByIdxOrId(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 获取箱子信息 req = {["device_id1","device_id2","device_id3","device_id4"]}
	 * 
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return
	 * @return ResultEntity
	 * @throws
	 * @author lbh
	 * @date 2016年5月9日
	 */
	@RequestMapping(value = { "/device/selectBinState" })
	@ResponseBody
	public ResultEntity selectBinState(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.selectBinState(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 获取Ext SSL信息 req = {["device_id1","device_id2","device_id3","device_id4"]}
	 * 
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return
	 * @return ResultEntity
	 * @throws
	 * @author lbh
	 * @date 2016年5月10日
	 */
	@RequestMapping(value = { "/device/selectDeviceExtState" })
	@ResponseBody
	public ResultEntity selectDeviceExtState(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.selectDeviceExtState(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 获取功能状态信息 SSL信息 req =
	 * {["device_id1","device_id2","device_id3","device_id4"]}
	 * 
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return
	 * @return ResultEntity
	 * @throws
	 * @author lbh
	 * @date 2016年5月10日
	 */
	@RequestMapping(value = { "/device/selectSoftState" })
	@ResponseBody
	public ResultEntity selectSoftState(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.selectSoftState(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 设备用户的权限 目前 不支持页面上编辑 2016年12月24日 18:18:07，只有一个默认的设备用户组~~~ get Permession
	 * Info by operator_id or operator_idx
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/device/SelPermissionByDeviceId" })
	@ResponseBody
	public ResultEntity SelPermissionByDeviceId(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			if (JSONUtils.mayBeJSON(req)) {
				Map<String, String> map = JsonUtils.fromJson(req, Map.class);
				String devLib = map.get("operator_id");
				String library_id = map.get("library_id");
				devLib = StringUtils.delete(devLib, library_id + "_");
				map.put("operator_id", devLib);
				map.remove("library_id");
				// 获取用户的idx
				result = deviceService.selOperatorByOperIdOrIdx(JsonUtils
						.toJson(map));
			}
			if (result != null && result.getState() == true) {
				Map<String, Object> oper = (Map<String, Object>) result
						.getResult();
				if (oper != null) {
					if (oper.get("operator_idx") == null) {
						result.setMessage("operator_idx is not found");
						return result;
					}
					Integer operatorIdx = (Integer) oper.get("operator_idx");
					if (operatorIdx != null) {
						// 获取用户的权限
						String resultPermession = userService
								.SelPermissionByOperIdx(operatorIdx.toString());
						if (StringUtils.hasLength(resultPermession)) {
							result = JsonUtils.fromJson(resultPermession,
									ResultEntity.class);
						}
					}
				}
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/device/SelFunctionByDeviceIdx" })
	@ResponseBody
	public ResultEntity SelFunctionByDeviceIdx(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.SelFunctionByDeviceIdx(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[0]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@RequestMapping(value = { "/device/UpdateDeviceConfig" })
	@ResponseBody
	public ResultEntity UpdateDeviceConfig(HttpServletRequest request,
			String json) {
		ResultEntity result = new ResultEntity();

		try {
			String response = deviceService.UpdateDeviceConfig(json);
			result = JsonUtils.fromJson(response, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[0]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 带参数 查询 设备 格式 req= { "machineType":machineType, //设备类型
	 * "machineState":machineState, //设备状态 "keyWord":keyWord //关键字 （设备名） }
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/device/queryDeviceByParam" })
	@ResponseBody
	public ResultEntity queryDeviceByParam(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			if (StringUtils.isEmpty(req)) {
				req = "{\"pageSize\":12}";
			}
			Operator oper = getCurrentUser();
			if (oper != null) {
				String operType = oper.getOperator_type();
				
				if (!Operator.SSITCLOUD_MANAGER.equals(operType)) {
					// 图书馆人员 只显示本馆的设备
					Map<String, Object> params = JsonUtils.fromJson(req,
							Map.class);
					String library_idx = "";
					if(Operator.SSITCLOUD_ADMIN.equals(operType)){
						library_idx = (String) params.get("library_idx");
					}else{
						if (Operator.LIBRARY_USER.equals(operType)) {// 设备组设备限制
							params.put("operator_idx_Limit", oper.getOperator_idx());
						}
						library_idx = oper.getLibrary_idx();
					}
				  if(library_idx!=null && !"null".equals(library_idx)){
				/*if (!Operator.SSITCLOUD_ADMIN.equals(operType)
						&& !Operator.SSITCLOUD_MANAGER.equals(operType)) {
					// 图书馆人员 只显示本馆的设备
					Map<String, Object> params = JsonUtils.fromJson(req,
							Map.class);
					if (Operator.LIBRARY_USER.equals(operType)) {// 设备组设备限制
						params.put("operator_idx_Limit", oper.getOperator_idx());
					}
					String library_idx = oper.getLibrary_idx();*/
					// params.put("library_idx", library_idx); 这个不能放进来 会影响后面的判断
					// req=JsonUtils.toJson(params);
					// 先查询 一下 有没有子馆,有子馆的情况下则获取所有子馆
					Map<String, Object> m = new HashMap<>();
					m.put("library_idx", library_idx);
					// 查询出子馆，如果有子馆 则显示子馆的设备
					ResultEntity res = libraryService
							.querySlaveLibraryByMasterIdx(JsonUtils.toJson(m));
					List<Integer> list = new ArrayList<>();
					if (res != null && res.getState()) {
						MasterLibAndSlaveLibsEntity libs = JsonUtils
								.convertMap(
										res.getResult(),
										new TypeReference<MasterLibAndSlaveLibsEntity>() {
										});
						if (libs != null) {
							List<LibraryEntity> LibraryEntitys = libs
									.getSlaveLibrary();
							if (CollectionUtils.isNotEmpty(LibraryEntitys)) {
								try {
									list.add(Integer.parseInt(library_idx));
								} catch (Exception e) {
								}
								for (LibraryEntity libraryEntity : LibraryEntitys) {
									list.add(libraryEntity.getLibrary_idx());
								}
								params.put("lib_idx_list", list);
								req = JsonUtils.toJson(params);
							} else {
								// 有可能是子馆
								list.add(Integer.parseInt(library_idx));
								params.put("lib_idx_list", list);
								req = JsonUtils.toJson(params);
							}
						} else {// 没有子馆的情况下
							list.add(Integer.parseInt(library_idx));
							params.put("lib_idx_list", list);
							req = JsonUtils.toJson(params);
						}
					} else {
						list.add(Integer.parseInt(library_idx));
						params.put("lib_idx_list", list);
						req = JsonUtils.toJson(params);
					}
				   }
				} else if (Operator.SSITCLOUD_MANAGER.equals(operType)) {
					if (!checkPermession("0103000000")) {
						result.setMessage("没有权限！");
						return result;
					}
				}
			}

			result = deviceService.queryDeviceByParam(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 带参数 查询 虚拟服务设备 格式 req= { "machineType":machineType, //设备类型
	 * "machineState":machineState, //设备状态 "keyWord":keyWord //关键字 （设备名） }
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/device/queryServiceDeviceByParam" })
	@ResponseBody
	public ResultEntity queryServiceDeviceByParam(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		
		if (StringUtils.isEmpty(req)) {
			req = "{\"pageSize\":12}";
		}
		Operator oper = getCurrentUser();
		if (oper != null) {
			String operType = oper.getOperator_type();
			if (!Operator.SSITCLOUD_ADMIN.equals(operType)
					&& !Operator.SSITCLOUD_MANAGER.equals(operType)) {
				// 图书馆人员 只显示本馆的设备
				Map<String, Object> params = JsonUtils.fromJson(req,
						Map.class);
				if (Operator.LIBRARY_USER.equals(operType)) {// 设备组设备限制
					params.put("operator_idx_Limit", oper.getOperator_idx());
				}
				String library_idx = oper.getLibrary_idx();
				// params.put("library_idx", library_idx); 这个不能放进来 会影响后面的判断
				// req=JsonUtils.toJson(params);
				// 先查询 一下 有没有子馆,有子馆的情况下则获取所有子馆
				Map<String, Object> m = new HashMap<>();
				m.put("library_idx", library_idx);
				// 查询出子馆，如果有子馆 则显示子馆的设备
				ResultEntity res = libraryService
						.querySlaveLibraryByMasterIdx(JsonUtils.toJson(m));
				List<Integer> list = new ArrayList<>();
				if (res != null && res.getState()) {
					MasterLibAndSlaveLibsEntity libs = JsonUtils
							.convertMap(
									res.getResult(),
									new TypeReference<MasterLibAndSlaveLibsEntity>() {
									});
					if (libs != null ) {
						List<LibraryEntity> LibraryEntitys = libs
								.getSlaveLibrary();
						if (CollectionUtils.isNotEmpty(LibraryEntitys)) {
							try {
								list.add(Integer.parseInt(library_idx));
							} catch (Exception e) {
							}
							for (LibraryEntity libraryEntity : LibraryEntitys) {
								list.add(libraryEntity.getLibrary_idx());
							}
							params.put("lib_idx_list", list);
							req = JsonUtils.toJson(params);
						} else {
							// 有可能是子馆
							list.add(Integer.parseInt(library_idx));
							params.put("lib_idx_list", list);
							req = JsonUtils.toJson(params);
						}
					} else {// 没有子馆的情况下
						list.add(Integer.parseInt(library_idx));
						params.put("lib_idx_list", list);
						req = JsonUtils.toJson(params);
					}
				} else {
					list.add(Integer.parseInt(library_idx));
					params.put("lib_idx_list", list);
					req = JsonUtils.toJson(params);
				}

			} else if (Operator.SSITCLOUD_MANAGER.equals(operType)) {
				if (!checkPermession("0103000000")) {
					result.setMessage("没有权限！");
					return result;
				}
			}
		}
		result = deviceService.queryServiceDeviceByParam(req);
		return result;
	}
	
	/**
	 * req={device_id:""} 定时查询 命令执行结果
	 * 
	 * @param request
	 * @param req
	 * @author lbh
	 * @return
	 */
	@RequestMapping(value = { "/device/queryControlResult" })
	@ResponseBody
	public ResultEntity queryControlResult(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.queryControlResult(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 查找是否存在可下载的文件，如果不存在则发送请求到同步程序 req={ "utcStartTime":"", "utcEndTime":"",
	 * "device_id":"", }
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/device/checkDownLoadLog" })
	@ResponseBody
	public ResultEntity checkDownLoadLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			Operator oper = getCurrentUser();
			if (oper != null) {
				Map<String, String> m = JsonUtils.fromJson(req, Map.class);
				m.put("operator_id", oper.getOperator_id());
				result = deviceService.checkDownLoadLog(JsonUtils.toJson(m));
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 下载 (查询也是从临时路径里面取)
	 * 
	 * req={ "utcStartTime":utcStartTime, "utcEndTime":utcEndTime,
	 * "device_id":device_id }; 发送下载设备端日志请求
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/device/downloadRunLogOperation" })
	@ResponseBody
	public ResponseEntity<byte[]> downloadRunLogOperation(
			HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		ResponseEntity<byte[]> respEntity = null;
		String filePath = request.getParameter("filePath");
		try {
			File file = new File(filePath);
			if (file.exists()) {
				String fileName = filePath
						.substring(filePath.lastIndexOf(File.separator),
								filePath.length());
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentDispositionFormData("attachment", fileName);
				try {
					respEntity = new ResponseEntity<byte[]>(
							FileUtils.readFileToByteArray(file), headers,
							HttpStatus.CREATED);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return respEntity;
	}

	/**
	 * 在线查询数据并返回， 如果不存在当前查询时间段的日志文件则
	 * 
	 * @param request
	 * @param req
	 *            ---》日志文件的路径
	 * @return
	 */
	@RequestMapping(value = { "/device/queryDeivceLogOnline" })
	@ResponseBody
	public ResultEntity queryDeivceLogOnline(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			if (req != null) {
				File file = new File(req);
				if (file.exists()) {
					// 存在则读取数据返回
					// String logInfo=FileUtils.readFileToString(file,
					// Consts.UTF_8);
					List<String> logInfosList = FileUtils.readLines(file,
							Consts.UTF_8);
					result.setState(true);
					result.setResult(logInfosList);
				} else {
					// 不存在
					result.setValue(false, "", "暂无数据，请稍等", null);
				}
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 获取当前页面的 设备设置的监控颜色
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/device/GetCurrentDevColorSet" })
	@ResponseBody
	public ResultEntity GetCurrentDevColorSet(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.GetCurrentDevColorSet(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	/**
	 * 获取当前页面设备的逻辑外设信息
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/device/GetDevExtModel" })
	@ResponseBody
	public ResultEntity GetDevExtModel(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.GetDevExtModel(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	/**
	 * 查询容器里面的数据，分页带参数？
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/device/QueryContainerInfo" })
	@ResponseBody
	public ResultEntity QueryContainerInfo(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = syncContainerService.QueryContainerInfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	@RequestMapping(value = { "/device/getLastHeatBeatTime" })
	@ResponseBody
	public ResultEntity getLastHeatBeatTime(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.getLastHeatBeatTime(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询设备的相关业务数据，并显示到设备详情Tab页
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/device/queryBizData" })
	@ResponseBody
	public ResultEntity QueryBizData(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.QueryBizData(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 统计借还数据
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/device/countLoanLog" })
	@ResponseBody
	public ResultEntity countLoanLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.countLoanLog(req);
			result.setState(true);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	/**
	 * 统计办证数据
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/device/countCardissueLog" })
	@ResponseBody
	public ResultEntity countCardissueLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.countCardissueLog(req);
			result.setState(true);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 统计财经数据
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/device/countFinanceLog" })
	@ResponseBody
	public ResultEntity countFinanceLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.countFinanceLog(req);
			result.setState(true);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 统计人流量数据
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/device/countVisitorLog" })
	@ResponseBody
	public ResultEntity countVisitorLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.countVisitorLog(req);
			result.setState(true);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}


	@RequestMapping(value = { "/device/getLibPosition" })
	@ResponseBody
	public ResultEntity getLibPosition(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceService.getLibPosition(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	@RequestMapping(value = { "/device/getDevicePosition" })
	@ResponseBody
	public ResultEntity getDevicePosition(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("req");
			Map<String, Object> map = JsonUtils.fromJson(json, Map.class);

			String dataStr =URLDecoder.decode(map.get("libNameAndIdx").toString(), "UTF-8");// 将页面的中文转回
			req = "{\"libNameAndIdx\":\""+dataStr+"\"}";
			result = deviceService.getDevicePosition(req);

		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	@RequestMapping(value = { "/device/queryBookItemTotal" })
	@ResponseBody
	public ResultEntity queryBookItemTotal(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = navigationService.queryBookItemTotal(req);
			result.setState(true);
		}catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "/device/queryBookShelfLayerTotal" })
	@ResponseBody
	public ResultEntity queryBookShelfLayerTotal(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = navigationService.queryBookShelfLayerTotal(req);
			result.setState(true);
		}catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "/device/queryBookShelfTotal" })
	@ResponseBody
	public ResultEntity queryBookShelfTotal(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = navigationService.queryBookShelfTotal(req);
			result.setState(true);
		}catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = {"/ascconfig/querylibInfoByCurUser"})
	@ResponseBody
	public ResultEntity querylibInfoByCurUser(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			String oper = getCurrentUserJson();
			result = deviceService.querylibInfoByCurUser(oper);
			result.setState(true);
		}catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 查询图书馆的所有设备
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/device/SelectDevicesInfo")
	@ResponseBody
	public ResultEntity SelectDevicesInfo(HttpServletRequest request, String json) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = deviceService.SelectDevicesInfo(json);
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			resultEntity.setValue(false, "failed",
					methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/device/selectDeviceCountByLibraryIdx")
	@ResponseBody
	public ResultEntity selectDeviceCountByLibraryIdx(String req){
		return deviceService.selectDeviceCountByLibraryIdx(req);
	}
	
	@RequestMapping("/device/selectCountDeviceService")
	@ResponseBody
	public ResultEntity selectCountDeviceService(String req){
		return deviceService.selectCountDeviceService(req);
	}
	/**
	 * 设备监控查询借还、办证等数据
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/device/countDatas")
	@ResponseBody
	public ResultEntity countDatas(String req){
		return deviceService.countDatas(req);
	}
	
	/**
	 * 获取设备部件监控状态错误码
	 */
	@RequestMapping("/device/queryDevStatusCode")
	@ResponseBody
	public ResultEntity queryDevStatusCode(){
		ResultEntity result = deviceService.queryDevStatusCode();
		result.setState(true);
		return result;
	}
}
