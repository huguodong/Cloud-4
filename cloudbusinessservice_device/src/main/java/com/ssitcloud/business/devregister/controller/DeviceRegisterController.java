package com.ssitcloud.business.devregister.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JSchUtils;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.business.devregister.param.AddDeviceParam;
import com.ssitcloud.business.devregister.param.DeviceRegisterParam;
import com.ssitcloud.business.devregister.service.DeviceRegisterService;
import com.ssitcloud.business.devregister.service.FtpBusinessService;
import com.ssitcloud.common.entity.ResultEntity;
/**
 * 设备注册控制类
 * @package: com.ssitcloud.devregister.controller
 * @classFile: DeviceRegisterController
 * @author: liuBh
 * @description: TODO
 */
@Controller
@RequestMapping("devreg")
public class DeviceRegisterController {
	
	
	@Resource
	private DeviceRegisterService deviceRegisterService;
	
	@Resource
	private FtpBusinessService ftpBusinessService;
	
	/**
	 * 注册信息
	 * {
	 *	device:{
				device_id:"regIDx",
				library_idx:4566,
				device_model_idx:1,
				device_name:"注册设备",
				device_code:"XXFFASDDD",
				device_desc:"注册设备描述"
			},
			device_dbsync_template:{
				device_tpl_ID:"tempIDxx",
				device_tpl_desc:"descdesc",
				device_type:"typess"
			},
			device_monitor_template:{
				device_mon_tpl_id:"tempIDxx",
				device_mon_tpl_desc:"descdesc",
				device_mon_type_idx:55555
			},
			device_run_template:{
				device_tpl_ID:"tempIDxx",
				device_tpl_desc:"descdesc",
				device_type:"typess"
			},
			device_ext_template:{
				device_tpl_ID:"tempIDxx",
				device_tpl_desc:"descdesc",
				device_type:"typess"
			}
	 * }
	 * @methodName: DeviceRegister
	 * @param req
	 * @param json json数据，如上例子
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"DeviceRegister"})
	@ResponseBody
	public ResultEntity DeviceRegister(HttpServletRequest req,String json){
		ResultEntity result=new ResultEntity();
		try {
			String resps=deviceRegisterService.deviceRegister(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 获取所有的设备类型
	 *
	 * <p>2016年4月25日 下午3:56:23
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/SelAllMetadataDeviceType")
	@ResponseBody
	public ResultEntity SelAllMetadataDeviceType(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			String resps=deviceRegisterService.selAllMetadataDeviceType(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 获取所有的硬件与逻辑配置模板
	 *
	 * <p>2016年4月25日 下午8:24:48
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/SelAllExtTempList")
	@ResponseBody
	public ResultEntity selAllExtTempList(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			String resps=deviceRegisterService.selAllExtTempList(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 获取所有运行配置模板信息
	 *
	 * <p>2016年4月25日 下午8:24:48
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/SelAllRunTempList")
	@ResponseBody
	public ResultEntity SelAllRunTempList(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			String resps=deviceRegisterService.selAllRunTempList(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
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
	@RequestMapping("/SelAllMonitorTempList")
	@ResponseBody
	public ResultEntity SelAllMonitorTempList(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			String resps=deviceRegisterService.selAllMonitorTempList(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 获取所有数据同步配置模板
	 *
	 * <p>2016年4月25日 下午6:53:27
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/SelAllDBSyncTempList")
	@ResponseBody
	public ResultEntity SelAllDBSyncTempList(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			String resps=deviceRegisterService.selAllDBSyncTempList(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 设备注册
	 *
	 * <p>2016年4月25日 下午6:53:27
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/deviceRegister")
	@ResponseBody
	public ResultEntity deviceRegister(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			//
			if (StringUtils.isBlank(json)) {
				result.setState(false);
				result.setMessage("json is null");
				return result;
			}
			DeviceRegisterParam registerParam = JsonUtils.fromJson(json, DeviceRegisterParam.class);
			//拼装数据发送到鉴权业务层，新增一个设备用户
			AddDeviceParam deviceParam = new AddDeviceParam();
			deviceParam.setAdmin_idx(registerParam.getAdmin_idx()); //操作员的idx
			deviceParam.setOperator_id(registerParam.getDeviceId());  //设备的id
			deviceParam.setIp(registerParam.getIp()); //设备的ip，存入白名单
			deviceParam.setPort(registerParam.getPort()); //设备的port
			deviceParam.setLibrary_idx(registerParam.getLibrary_idx()); //所属图书馆的idx
			deviceParam.setOperator_name(registerParam.getDeviceName()); //设备的名称
			deviceParam.setOperator_type("5");
			String resp = deviceRegisterService.addDevice(deviceParam);
			if (resp!=null) {
				ResultEntity resultEntity = JsonUtils.fromJson(resp, ResultEntity.class);
				if(!resultEntity.getState()){
					return resultEntity;
				}
				
				Map<String, Object> param = JsonUtils.fromJson(JsonUtils.toJson(resultEntity.getResult()), Map.class);
				String operator_idx = param.get("operator_idx")==null?"":param.get("operator_idx").toString();
				if (operator_idx.equals("")) {
					result.setState(false);
					result.setMessage("failed");
				}else {
					registerParam.setOperator_idx(operator_idx);
					String response = deviceRegisterService.registerDevice(JsonUtils.toJson(registerParam));
					if (response==null) {
						result.setState(false);
						result.setMessage("连接超时");
					}else{
						result = JsonUtils.fromJson(response, ResultEntity.class);
						//创建ftp用户
						/*Boolean b = ftpBusinessService.createFtpUser(registerParam);
						if(b){
							LogUtils.info("成功创建ftp用户:"+registerParam.getDeviceId());
						}else{
							LogUtils.info("创建ftp用户失败:"+registerParam.getDeviceId());
						}*/
					}
				}
			}else {
				result.setState(false);
				result.setMessage("连接超时");
			}
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 海洋设备注册
	 *
	 * <p>2018年1月09日 下午15:24:30
	 * <p>create by 刘魏
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/hydeviceRegister")
	@ResponseBody
	public ResultEntity hydeviceRegister(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			//
			if (StringUtils.isBlank(json)) {
				result.setState(false);
				result.setMessage("json is null");
				return result;
			}
			DeviceRegisterParam registerParam = JsonUtils.fromJson(json, DeviceRegisterParam.class);
			//拼装数据发送到鉴权业务层，新增一个设备用户
			AddDeviceParam deviceParam = new AddDeviceParam();
			deviceParam.setAdmin_idx(registerParam.getAdmin_idx()); //操作员的idx
			deviceParam.setOperator_id(registerParam.getDeviceId());  //设备的id
			deviceParam.setIp(registerParam.getIp()); //设备的ip，存入白名单
			deviceParam.setPort(registerParam.getPort()); //设备的port
			deviceParam.setLibrary_idx(registerParam.getLibrary_idx()); //所属图书馆的idx
			deviceParam.setOperator_name(registerParam.getDeviceName()); //设备的名称
			deviceParam.setOperator_type("5");
			String resp = deviceRegisterService.addDevice(deviceParam);
			if (resp!=null) {
				ResultEntity resultEntity = JsonUtils.fromJson(resp, ResultEntity.class);
				if(!resultEntity.getState()){
					return resultEntity;
				}
				
				Map<String, Object> param = JsonUtils.fromJson(JsonUtils.toJson(resultEntity.getResult()), Map.class);
				String operator_idx = param.get("operator_idx")==null?"":param.get("operator_idx").toString();
				if (operator_idx.equals("")) {
					result.setState(false);
					result.setMessage("failed");
				}else {
					registerParam.setOperator_idx(operator_idx);
					String response = deviceRegisterService.registerHYDevice(JsonUtils.toJson(registerParam));
					if (response==null) {
						result.setState(false);
						result.setMessage("连接超时");
					}else{
						result = JsonUtils.fromJson(response, ResultEntity.class);
						//创建ftp用户
						/*Boolean b = ftpBusinessService.createFtpUser(registerParam);
						if(b){
							LogUtils.info("成功创建ftp用户:"+registerParam.getDeviceId());
						}else{
							LogUtils.info("创建ftp用户失败:"+registerParam.getDeviceId());
						}*/
					}
				}
			}else {
				result.setState(false);
				result.setMessage("连接超时");
			}
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 检查系统中是否有deviceCode的设备
	 *
	 * <p>2016年4月25日 下午6:53:27
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/hasDeviceCode")
	@ResponseBody
	public ResultEntity hasDeviceCode(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			
			String resps=deviceRegisterService.hasDeviceCode(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 根据设备号查询设备信息，如果有这个设备，返回设备的设备类型
	 * 否则返回不null
	 *
	 * <p>2016年6月17日 下午4:28:18 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/queryDeviceByDeviceCode")
	@ResponseBody
	public ResultEntity queryDeviceByDeviceCode(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			
			String resps=deviceRegisterService.queryDeviceByDeviceCode(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 获取所有的metadata_ext_model还有metadata_logic_obj的数据
	 *
	 * <p>2016年5月14日 下午1:54:30 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/selAllExtModelAndLogicObj")
	@ResponseBody
	public ResultEntity selAllExtModelAndLogicObj(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			String resps=deviceRegisterService.selAllExtModelAndLogicObj(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 根据id或者idx 查询图书馆的信息
	 * {lib_id:"1"} or {library_idx:"1"}
	 *
	 * <p>2016年5月14日 下午1:54:30 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/selLibraryByIdxOrId")
	@ResponseBody
	public ResultEntity selLibraryByIdxOrId(HttpServletRequest request,String json) {
		ResultEntity result=new ResultEntity();
		try {
			String resps=deviceRegisterService.selLibraryByIdxOrId(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 根据id或者idx判断设备是否存在
	 *
	 * <p>2016年6月1日 下午1:50:19 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/isExistDeviceWithIdOrIdx")
	@ResponseBody
	public ResultEntity isExistDeviceWithIdOrIdx(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			String resps=deviceRegisterService.isExistDeviceWithIdOrIdx(json);
			result=JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询图书馆的模板信息，以及所有 有效的设备用户的id
	 *
	 * <p>2016年6月2日 下午4:39:54 
	 * <p>create by hjc
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping("/selLibraryTempAndDeviceIds")
	@ResponseBody
	public ResultEntity selLibraryRegisterDevice(String json,HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			if(json!=null && !json.equals("")){
				String response = deviceRegisterService.selDeviceUserByLibraryIdx(json);
				if (response!=null) {
					result = JsonUtils.fromJson(response, ResultEntity.class);
				}else {
					result.setState(false);
					result.setMessage("连接失败");
				}
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 
	 *
	 * <p>2016年6月2日 下午7:01:55 
	 * <p>create by hjc
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping("/selDeviceCountByIds")
	@ResponseBody
	public ResultEntity selDeviceCountByIds(String json,HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			if(json!=null && !json.equals("")){
				String response = deviceRegisterService.selDeviceCountByIds(json);
				if (response!=null) {
					result = JsonUtils.fromJson(response, ResultEntity.class);
				}else {
					result.setState(false);
					result.setMessage("连接失败");
				}
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
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
	@RequestMapping("/getAscTempList")
	@ResponseBody
	public ResultEntity getAscTempList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if(StringUtils.isBlank(json) || json.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
			}
			String response = deviceRegisterService.getAscTempList(json);
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
	
}
