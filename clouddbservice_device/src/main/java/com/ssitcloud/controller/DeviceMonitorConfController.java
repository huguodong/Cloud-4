package com.ssitcloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceMonConfigEntity;
import com.ssitcloud.entity.DeviceMonTempEntity;
import com.ssitcloud.entity.page.DeviceMonTempPageEntity;
import com.ssitcloud.service.DeviceConfigService;
import com.ssitcloud.service.DeviceMonConfService;

@Controller
@RequestMapping("/monconf")
public class DeviceMonitorConfController {

	@Resource
	private DeviceMonConfService deviceMonConfService;

	@Resource
	DeviceConfigService deviceConfigService;
	/**
	 * 新建设备监控模板   ,
	 * {
	 * 	
	 * }
	 * 
	 * 
	 * 
	 * * req={
	 * 	"device_tpl_ID":""
	 * 	"device_tpl_desc":""
	 * 	"device_type":""
	 * }
	 * @methodName: AddNewDeviceMonitorTemp
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"AddNewDeviceMonitorTemp"})
	@ResponseBody
	public ResultEntity AddNewDeviceMonitorTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			int re=deviceMonConfService.insertDeviceMonTemp(JsonUtils.fromJson(req, DeviceMonTempEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 更新监控模板
	 * * json={
	 * 	"device_tpl_ID":""
	 * 	"device_tpl_desc":""
	 * 	"device_type":""
	 * }
	 * @methodName: UpdDeviceMonitorTemp
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"UpdDeviceMonitorTemp"})
	@ResponseBody
	public ResultEntity UpdDeviceMonitorTemp(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=deviceMonConfService.updateDeviceMonTemp(JsonUtils.fromJson(req.getParameter("json"), DeviceMonTempEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 删除监控模板
	 * 
	 * @methodName: DelDeviceMonitorTemp
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"DelDeviceMonitorTemp"})
	@ResponseBody
	public ResultEntity DelDeviceMonitorTemp(HttpServletRequest req){

		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			int re=deviceMonConfService.deleteDeviceMonTemp(JsonUtils.fromJson(json, DeviceMonTempEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 查询监控模板
	 * * json={
	 *   device_mon_tpl_idx:
	 * 	"device_tpl_ID":""
	 * 	"device_tpl_desc":""
	 * 	"device_type":""
	 * }
	 * @methodName: SelDeviceMonitorTemp
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"SelDeviceMonitorTemp"})
	@ResponseBody
	public ResultEntity SelDeviceMonitorTemp(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			List<DeviceMonTempEntity> monTemps= deviceMonConfService.selectList(JsonUtils.fromJson(req.getParameter("json"), DeviceMonTempEntity.class));
			result.setResult(monTemps);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	
	/**
		device_ext_type	int(11) NOT NULL模板状态标识 0模板 1数据
		device_mon_tpl_idx	int(11) NOT NULL显示模板ID或设备ID
		logic_obj_idx	int(11) NOT NULL逻辑对象ID
		library_idx	int(11) NOT NULL馆ID
		visiable	int(11) NOT NULL是否可见
		alert	int(11) NOT NULL是否告警
		color	int(11) NOT NULL显示颜色
		threshold
	/**
	 * 新建监控配置
	 * 格式
	 * 	json=
	 * {
	 * 	device_ext_type:int
	 *  device_mon_tpl_idx:int
	 *  logic_obj_idx:int
	 *  library_idx:int
	 *  visiable:int
	 *  alert: int 
	 *  color: int 
	 *  threshold: int 
	 * } 
	 * 
	 * 
	 * @methodName: AddNewDeviceMonitorConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"AddNewDeviceMonitorConf"})
	@ResponseBody
	public ResultEntity AddNewDeviceMonitorConf(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=deviceMonConfService.insertDeviceMonConfig(JsonUtils.fromJson(req.getParameter("json"), DeviceMonConfigEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	  更新监控配置
	 * 格式
	 * 	json=
	 * {
	 * 	device_ext_type:int
	 *  device_mon_tpl_idx:int
	 *  logic_obj_idx:int
	 *  library_idx:int
	 *  visiable:int
	 *  alert: int 
	 *  color: int 
	 *  threshold: int 
	 * } 
	 * @methodName: UpdDeviceMonitorConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"UpdDeviceMonitorConf"})
	@ResponseBody
	public ResultEntity UpdDeviceMonitorConf(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=deviceMonConfService.updateDeviceMonConfig(JsonUtils.fromJson(req.getParameter("json"), DeviceMonConfigEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 *  AND device_mon_tpl_idx
		AND library_idx
		AND device_ext_type
		AND logic_obj_idx
	 */
	/**
	 * 删除监控设置
	 * 格式
	 * json={
	 *   device_mon_tpl_idx:int,
		 library_idx:int,
		 device_ext_type:int,
		 logic_obj_idx:int
	 * }
	 * @methodName: DelDeviceMonitorConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"DelDeviceMonitorConf"})
	@ResponseBody
	public ResultEntity DelDeviceMonitorConf(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=deviceMonConfService.deleteDeviceMonConfig(JsonUtils.fromJson(req.getParameter("json"), DeviceMonConfigEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 查询监控设置
	 * 格式
	 * json={
	 *  device_ext_type:int
		device_mon_tpl_idx:	int
		logic_obj_idx:int
		library_idx:int
		visiable:int
		alert:int
		color:int
		threshold:int
	 * }
	 * @methodName: SelDeviceMonitorConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"SelDeviceMonitorConf"})
	@ResponseBody
	public ResultEntity SelDeviceMonitorConf(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			List<DeviceMonConfigEntity> monConfs=deviceMonConfService.selectList(JsonUtils.fromJson(req.getParameter("json"), DeviceMonConfigEntity.class));
			result.setResult(monConfs);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 设备监控模板 页面
	 * 带参数查询，返回分页数据
	 * @param request
	 * @param req
	 * @author lbh
	 * @return
	 */
	@RequestMapping(value={"queryDeviceMonitorByParam"})
	@ResponseBody
	public ResultEntity queryDeviceMonitorByParam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			DeviceMonTempPageEntity monConfs=deviceMonConfService.queryDeviceMonitorByParam(req);
			result.setResult(monConfs);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	
	/**
	 * 获取所有的机器监控配置模板
	 *
	 * <p>2016年4月25日 下午6:53:27
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/SelAllMonitorTempList")
	@ResponseBody
	public ResultEntity SelAllMonitorTempList(HttpServletRequest request) {
		ResultEntity result=new ResultEntity();
		try {
			String json = request.getParameter("json");
			Map<String, String> param = new HashMap<String, String>();
			if (!StringUtils.isBlank(json)) {
				param = JsonUtils.fromJson(json, Map.class);
			}
			List<Map<String, Object>> monitorTempsList=deviceMonConfService.selAllMonitorTempList(param);
			result.setResult(monitorTempsList);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 设备监控配置模板 新增操作
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"AddNewDeviceMonitorConfAndTemp"})
	@ResponseBody
	public ResultEntity AddNewDeviceMonitorConfAndTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.AddNewDeviceMonitorConfAndTemp(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
			if(result.getRetMessage()!=null&&StringUtils.contains(result.getRetMessage(), "Can not construct instance of java.lang.Integer from String value")){
				result.setRetMessage("报警限额只能输入整数");
			}
		}
		return result;
	}
	/**
	 * 更新
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"UpdNewDeviceMonitorConfAndTemp"})
	@ResponseBody
	public ResultEntity UpdDeviceMonitorConfAndTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.UpdNewDeviceMonitorConfAndTemp(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
			if(result.getRetMessage()!=null&&StringUtils.contains(result.getRetMessage(), "Can not construct instance of java.lang.Integer from String value")){
				result.setRetMessage("报警限额只能输入整数");
			}
			String msg=e.getCause().getMessage();
			if(StringUtils.contains(msg, "Duplicate entry")
					&&StringUtils.contains(msg, "device_mon_tpl_id")
					){
				result.setRetMessage("模板ID已经被占用，请修改");
			}else if(StringUtils.contains(msg, "Duplicate entry")
					&&StringUtils.contains(msg, "device_mon_tpl_desc")){
				result.setRetMessage("模板名称已经被占用，请修改");
			}
			
		}
		return result;
	}
	/**
	 * 删除 配置模板
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"DelNewDeviceMonitorConfAndTemp"})
	@ResponseBody
	public ResultEntity DelNewDeviceMonitorConfAndTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
		 	int delNum=deviceMonConfService.DelNewDeviceMonitorConfAndTemp(req);
			if(delNum>0){
			 	result.setState(true);
			 	try {
					JsonNode node=JsonUtils.readTree(req);
					JsonNode idxNode=node.get("device_mon_tpl_idx");
					result.setRetMessage("IDX:"+(idxNode!=null?idxNode.asText():""));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 设备监控配置 点击编辑 查询出 配置参数
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"SelDevMonConfMetaLogObjByDeviceMonTplIdx"})
	@ResponseBody
	public ResultEntity SelDevMonConfMetaLogObjByDeviceMonTplIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.SelDevMonConfMetaLogObjByDeviceMonTplIdx(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
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
	@RequestMapping(value={"queryAlertColor"})
	@ResponseBody
	public ResultEntity queryAlertColor(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.queryAlertColor(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	@RequestMapping(value={"ADDMonitor"})
	@ResponseBody
	public ResultEntity ADDMonitor(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			List<DeviceMonConfigEntity> list = JsonUtils.fromJson(json, new TypeReference<List<DeviceMonConfigEntity>>() {});
			DeviceMonConfigEntity dConfigEntity = list.get(0);
			DeviceConfigEntity dEntity = new DeviceConfigEntity();
			dEntity.setDevice_idx(dConfigEntity.getDevice_mon_tpl_idx());
			dEntity.setDevice_monitor_tpl_flg(0);
			int ret = deviceConfigService.updateDeviceConfig(dEntity);
			int re =0;
			if(ret>0){
				for (DeviceMonConfigEntity deviceMonConfigEntity : list) {
					re += deviceMonConfService.insertDeviceMonConfig(deviceMonConfigEntity);
				}
			}
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 自定义数据更新 
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value={"UpdateMonitor"})
	@ResponseBody
	public ResultEntity UpdateMonitor(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.UpdateMonitor(json);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"DeleteMonitor"})
	@ResponseBody
	public ResultEntity DeleteMonitor(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			DeviceMonConfigEntity dEntity = JsonUtils.fromJson(json, DeviceMonConfigEntity.class);
			
			int re = deviceMonConfService.DeleteMonitor(dEntity);
			
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
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
	@RequestMapping(value={"GetCurrentDevColorSet"})
	@ResponseBody
	public ResultEntity GetCurrentDevColorSet(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.GetCurrentDevColorSet(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
