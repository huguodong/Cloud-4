package com.ssitcloud.controller;

import java.util.List;
import java.util.Map;





import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.factory.BeanFactoryHelper;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.UploadcfgSynEntity;
import com.ssitcloud.entity.UploadcfgSynResultEntity;
import com.ssitcloud.service.DeviceConfigService;
import com.ssitcloud.service.DeviceService;
import com.ssitcloud.service.UploadCfgSyncService;
/**
 * device_config table controller
 * 
 * 
 * @package: com.ssitcloud.controller
 * @classFile: DeviceConfController
 * @author: liuBh
 * @description: TODO
 */
@Controller
@RequestMapping(value={"devconf"})
public class DeviceConfController {
	
	@Resource(name="deviceConfigService")
	private DeviceConfigService deviceConfigService;
	@Resource
	private DeviceService deviceService;
	/**
	 * 
	 * @methodName: AddNewDeviceConfig
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"AddNewDeviceConfig"})
	@ResponseBody
	public ResultEntity AddNewDeviceConfig(HttpServletRequest req){
		ResultEntity result= new ResultEntity();
		try {
			int re=deviceConfigService.insertDeviceConfig(JsonUtils.fromJson(req.getParameter("json"), DeviceConfigEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 
	 * @methodName: UpdDeviceConfig
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"UpdDeviceConfig"})
	@ResponseBody
	public ResultEntity UpdDeviceConfig(HttpServletRequest req){
		ResultEntity result= new ResultEntity();
		try {
			int re=deviceConfigService.updateDeviceConfig(JsonUtils.fromJson(req.getParameter("json"), DeviceConfigEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
			if(result.getState()){
				DeviceConfigEntity configEntity = JsonUtils.fromJson(req.getParameter("json"), DeviceConfigEntity.class);
				//下发数据到设备端
				deviceService.pushMessage(configEntity, configEntity.getLibrary_idx(), configEntity.getDevice_idx());
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 
	 * @methodName: DelDeviceConfig
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"DelDeviceConfig"})
	@ResponseBody
	public ResultEntity DelDeviceConfig(HttpServletRequest req){
		ResultEntity result= new ResultEntity();
		try {
			int re=deviceConfigService.deleteDeviceConfig(JsonUtils.fromJson(req.getParameter("json"), DeviceConfigEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 
	 * @methodName: SelDeviceConfig
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"SelDeviceConfig"})
	@ResponseBody
	public ResultEntity SelDeviceConfig(HttpServletRequest req){
		ResultEntity result= new ResultEntity();
		try {
			List<DeviceConfigEntity> re=deviceConfigService.selectList(JsonUtils.fromJson(req.getParameter("json"), DeviceConfigEntity.class));
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setResult(re);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 通过devicde_id 和 library_idx 查询device_config表的数据
	 * 
	 * @methodName: SelDeviceConfigByLibIdxAndDevId
	 * @param request
	 * @param req
	 * @return
	 * @returnType: ResultEntityF<List<DeviceConfigEntity>>
	 * @author: liuBh
	 */
	@RequestMapping(value={"SelDeviceConfigByLibIdxAndDevId"})
	@ResponseBody
	public ResultEntityF<List<DeviceConfigEntity>> SelDeviceConfigByLibIdxAndDevId(HttpServletRequest request,String req){
		ResultEntityF<List<DeviceConfigEntity>> result=new ResultEntityF<>();
		try {
			List<DeviceConfigEntity> re=deviceConfigService.queryDeviceConfigByDevIdAndLibIdx(JsonUtils.fromJson(req, new TypeReference<Map<String,Object>>() {}));
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setResult(re);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 设备端上传配置信息
	 * device_run_config
	 * device_ext_config
	 * @methodName: uploadcfgSyn
	 * @param request
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"uploadcfgSyn"})
	@ResponseBody
	public ResultEntityF<UploadcfgSynResultEntity> uploadcfgSyn(HttpServletRequest request,String req){
		ResultEntityF<UploadcfgSynResultEntity> result=new ResultEntityF<UploadcfgSynResultEntity>();
		try {
			if(JSONUtils.mayBeJSON(req)){
				UploadcfgSynEntity uploadcfgSyn = JsonUtils.fromJson(req,UploadcfgSynEntity.class);
				if (uploadcfgSyn == null) {return result;}
				String tableName = uploadcfgSyn.getTable();
				if(tableName!=null){
					UploadCfgSyncService uploadCfgSyncService = null;
					try {
						uploadCfgSyncService=BeanFactoryHelper.getBean("uploadCfgSync_"+tableName, UploadCfgSyncService.class);
					} catch (NoSuchBeanDefinitionException e) {
						throw new RuntimeException(tableName +"不符合参数设置[device_run_config/device_ext_config].");
					}
					if(uploadCfgSyncService!=null){
						result=uploadCfgSyncService.execute(uploadcfgSyn);
					}
				}
			}
			//result=deviceConfigService.uploadCfgSync(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 
	 *
	 * data:<br/>
	 * device_id 			String 设备ID <br/>
	 * library_id 			String 馆ID  <br/>
	 * dBName 				String 数据库名  <br/>
	 * table 				String 表名  <br/>
	 * keyName 				String 主键名  <br/>
	 * lastLocalUpdateTime  DateTime 时间  <br/>
	 * 远程请求返回结果 <br/>
	 * ResultEntity.java
	 *
	 * 设备端下载配置
	 * @methodName: downloadCfgSync
	 * @param request
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"downloadCfgSync"})
	@ResponseBody
	public ResultEntity downloadCfgSync(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceConfigService.downloadCfgSync(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * req={
	 * 	device_idx:""
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceConfigByDeviceIdx"})
	@ResponseBody
	public ResultEntity queryDeviceConfigByDeviceIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceConfigService.queryDeviceConfigByDeviceIdx(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 *  * 
	 * 通过查询 device-config-old 表 和 deivce_config 表 ，查询那个字段放生变化
	 * 发生变化的字段推理出放生变化的表名
	 * 
	 * 返回需要同步的表名 逗号分隔
	 * 
	 * req={
	 * 	device_idx:""
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceConfigNewAndOldByDeviceIdx"})
	@ResponseBody
	public ResultEntity queryDeviceConfigNewAndOldByDeviceIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceConfigService.queryDeviceConfigNewAndOldByDeviceIdx(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value={"queryDevIdsByModelAndModelIdx"})
	@ResponseBody
	public ResultEntity queryDevIdsByModelAndModelIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceConfigService.queryDevIdsByModelAndModelIdx(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
