package com.ssitcloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.DeviceRunTempEntity;
import com.ssitcloud.entity.MetaRunConfigEntity;
import com.ssitcloud.entity.page.DeviceRunTempPageEntity;
import com.ssitcloud.service.DeviceRunConfService;

@Controller
@RequestMapping(value = { "runconf" })
public class DeviceRunConfController {
	@Resource
	private DeviceRunConfService deviceRunConfService;

	/**
	 * 新建运行模板 接收數據格式 json= { device_tpl_ID:"" device_tpl_desc:"" device_type:""
	 * }
	 * 
	 * @methodName: AddNewDeviceRunTemp
	 * @param req
	 * @param resp
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value = { "AddNewDeviceRunTemp" })
	@ResponseBody
	public ResultEntity AddNewDeviceRunTemp(HttpServletRequest req,
			HttpServletResponse resp) {
		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			DeviceRunTempEntity deviceRunTemp = JsonUtils.fromJson(json,
					DeviceRunTempEntity.class);
			int re = deviceRunConfService.insertDeviceRunTemp(deviceRunTemp);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新运行模板 接收數據格式 
	 * json={ 
	 * device_tpl_ID:"" 
	 * device_tpl_desc:"" 
	 * device_type:""
	 * }
	 * 
	 * @methodName: UpdDeviceRunTemp
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value = { "UpdDeviceRunTemp" })
	@ResponseBody
	public ResultEntity UpdDeviceRunTemp(HttpServletRequest req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			DeviceRunTempEntity deviceRunTemp = JsonUtils.fromJson(json,
					DeviceRunTempEntity.class);
			int re = deviceRunConfService.updateDeviceRunTemp(deviceRunTemp);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除运行模板
	 * 
	 * @methodName: DelDeviceRunTemp
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value = { "DelDeviceRunTemp" })
	@ResponseBody
	public ResultEntity DelDeviceRunTemp(HttpServletRequest req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			int re = deviceRunConfService.deleteDeviceRunTemp(JsonUtils.fromJson(json, DeviceRunTempEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 查询运行模板 json= { device_tpl_ID:"" device_tpl_desc:"" device_type:"" }
	 * 
	 * @methodName: SelDeviceRunTemp
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value = { "SelDeviceRunTemp" })
	@ResponseBody
	public ResultEntity SelDeviceRunTemp(HttpServletRequest req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			DeviceRunTempEntity deviceRunTemp = JsonUtils.fromJson(json,
					DeviceRunTempEntity.class);
			List<DeviceRunTempEntity> tunTemps = deviceRunConfService
					.selectList(deviceRunTemp);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setResult(tunTemps);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新一个运行配置（模板）
	 * 
	 * @methodName: UpdDeviceRunConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value = { "UpdDeviceRunConf" })
	@ResponseBody
	public ResultEntity UpdDeviceRunConf(HttpServletRequest req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = deviceRunConfService.updateDeviceRunConfig(JsonUtils
					.fromJson(req.getParameter("json"),
							DeviceRunConfigEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 新建运行配置（模板） device_tpl_type=1则为数据 device_tpl_type=0则为模板 格式 
	 * json={ 
	 * [ 
	 * 		{
	 * device_tpl_type:"", 
	 * device_run_id:"", 
	 * library_idx:"", 
	 * run_config_idx:"",
	 * run_conf_value:'{"XXX"}', 
	 * updatetime:"" 
	 * 		}, 
	 * 	{XXX} 
	 * ] 
	 * }
	 * 
	 * @methodName: AddNewDeviceRunConfTemp
	 * @param req
	 * @param resp
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value = { "AddNewDeviceRunConf" })
	@ResponseBody
	public ResultEntity AddNewDeviceRunConf(HttpServletRequest req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			List<DeviceRunConfigEntity> runConfs = JsonUtils.fromJson(json,
					new TypeReference<List<DeviceRunConfigEntity>>() {
					});
			int re = deviceRunConfService.insertBatchDeviceRunConfig(runConfs);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除然后添加自定义运行配置
	 * @comment
	 * @param req
	 * @return
	 * @data 2016年6月16日
	 * @author hwl
	 */
	@RequestMapping(value = { "DelAndAddDeviceRunConf" })
	@ResponseBody
	public ResultEntity DelAndAddDeviceRunConf(HttpServletRequest request,String json) {
		ResultEntity result = new ResultEntity();
		try {
			result=deviceRunConfService.DelAndAddDeviceRunConf(json);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "AddDeviceRunData" })
	@ResponseBody
	public ResultEntity AddDeviceRunData(HttpServletRequest req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			List<DeviceRunConfigEntity> runConfs = JsonUtils.fromJson(json,
					new TypeReference<List<DeviceRunConfigEntity>>() {
					});
			//根据conf_type 获得 run_config_idx
			for (DeviceRunConfigEntity deviceRunConfigEntity : runConfs) {
				MetaRunConfigEntity mRunConfigEntity = new MetaRunConfigEntity();
				mRunConfigEntity.setRun_conf_type(deviceRunConfigEntity.getRun_conf_type());
				List<MetaRunConfigEntity> mEntity = deviceRunConfService.selectList(mRunConfigEntity);
				deviceRunConfigEntity.setRun_config_idx(mEntity.get(0).getMeta_run_cfg_idx());
			}
			int	re = deviceRunConfService.insertBatchDeviceRunConfig(runConfs);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询运行配置（模板） 
	 * json= { 
	 * device_run_id:"" 
	 * device_tpl_type:"" 
	 * library_idx:""
	 * run_config_idx:"" 
	 * run_conf_value:"" 
	 * updatetime:"" 
	 * }
	 * 
	 * @methodName: SelDeviceRunConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: hwl
	 */
	@RequestMapping(value = { "SelDeviceRunConf" })
	@ResponseBody
	public ResultEntity SelDeviceRunConf(HttpServletRequest req) {

		ResultEntity result = new ResultEntity();
		try {
			//List<DeviceRunConfigEntity> runCofs = deviceRunConfService.selectList(JsonUtils.fromJson(req.getParameter("json"),DeviceRunConfigEntity.class));
			List<DeviceRunConfigEntity> runCofs =deviceRunConfService.queryDeviceRunConfigAndMetadataRunConfig(req.getParameter("json"));
			result.setResult(runCofs);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除运行模板
	 * 
	 * @methodName: DelDeviceRunConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: hwl
	 */
	@RequestMapping(value = { "DelDeviceRunConf" })
	@ResponseBody
	public ResultEntity DelDeviceRunConf(HttpServletRequest req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			int re = deviceRunConfService.deleteDeviceRunConfig(JsonUtils.fromJson(json, DeviceRunConfigEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@RequestMapping(value = { "DelDeviceRunData" })
	@ResponseBody
	public ResultEntity DelDeviceRunData(HttpServletRequest req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			int re = deviceRunConfService.deleteRunData(JsonUtils.fromJson(json, DeviceRunConfigEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	
	/**
	 * 删除设备运行元数据
	 * 
	 * @methodName: DelMetadataRunConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: hwl
	 */
	@RequestMapping(value = { "DelMetadataRunConf" })
	@ResponseBody
	public ResultEntity DelMetadataRunConf(HttpServletRequest req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			int re = deviceRunConfService.deleteMetaRunConfig(JsonUtils.fromJson(json, MetaRunConfigEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 新建设备运行元数据  接收数据格式 
	 * json= { 
	 * meta_run_cfg_idx:"" 
	 * run_conf_type:""
	 * run_conf_type_desc:"" 
	 * updatetime:"" }
	 * 
	 * @methodName: AddNewMetadataRunConf
	 * @param req
	 * @param resp
	 * @return
	 * @returnType: ResultEntity
	 * @author: hwl
	 */
	@RequestMapping(value = { "AddNewMetadataRunConf" })
	@ResponseBody
	public ResultEntity AddNewMetadataRunConf(HttpServletRequest req,
			HttpServletResponse resp) {
		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			MetaRunConfigEntity metaRunConfigEntity = JsonUtils.fromJson(json,
					MetaRunConfigEntity.class);
			int re = deviceRunConfService
					.insertMetaRunConfig(metaRunConfigEntity);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 更新设备运行元数据
	 * 
	 * @methodName: UpdMetadataRunConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: hwl
	 */
	@RequestMapping(value = { "UpdMetadataRunConf" })
	@ResponseBody
	public ResultEntity UpdMetadataRunConf(HttpServletRequest req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = deviceRunConfService.updateMetaRunConfig(JsonUtils
					.fromJson(req.getParameter("json"),
							MetaRunConfigEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询设备运行元数据
	 * json= { 
	 * meta_run_cfg_idx:"" 
	 * run_conf_type:"" 
	 * run_conf_type_desc:""
	 * updatetime:""  
	 * }
	 * 
	 * @methodName: SelMetadataRunConf
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: hwl
	 */
	@RequestMapping(value = { "SelMetadataRunConf" })
	@ResponseBody
	public ResultEntity SelMetadataRunConf(HttpServletRequest req) {

		ResultEntity result = new ResultEntity();
		try {
			List<MetaRunConfigEntity> runCofs = deviceRunConfService
					.selectList(JsonUtils.fromJson(req.getParameter("json"),
							MetaRunConfigEntity.class));
			result.setResult(runCofs);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 获取所有运行配置模板
	 *
	 * <p>2016年4月25日 下午6:53:27
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/SelAllRunTempList")
	@ResponseBody
	public ResultEntity SelAllRunTempList(HttpServletRequest request) {
		ResultEntity result=new ResultEntity();
		try {
			String json = request.getParameter("json");
			Map<String, String> param = new HashMap<String, String>();
			if (!StringUtils.isBlank(json)) {
				param = JsonUtils.fromJson(json, Map.class);
			}
			List<Map<String, Object>> runTempsList=deviceRunConfService.selAllRunTempList(param);
			result.setResult(runTempsList);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	
	@RequestMapping(value = { "SelDeviceRunData" })
	@ResponseBody
	public ResultEntity SelDeviceRunData(HttpServletRequest req) {

		ResultEntity result = new ResultEntity();
		try {
			String json = req.getParameter("json");
			List<Map<String, Object>> list = deviceRunConfService.selRunDataList(json);
			result.setResult(list);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 根据参数查询运行模板分页信息
	 *
	 * <p>2016年5月19日 上午9:58:35 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/SelRunTempListByParam")
	@ResponseBody
	public ResultEntity SelRunTempListByParam(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		Map<String, Object> param = new HashMap<>();
		DeviceRunTempPageEntity  pageEntity = new DeviceRunTempPageEntity();
		int page = pageEntity.getPage();
		int pageSize = pageEntity.getPageSize();
		String deviceType = "";
		String keyword = "";
		try {
			String json = request.getParameter("json");
			if (!StringUtils.isBlank(json)) {
				param = JsonUtils.fromJson(json, Map.class);
			}
			if (param!=null && !param.isEmpty()) {
				if (param.get("pageSize") != null && !"".equals(param.get("pageSize").toString())) {
					pageSize = Integer.valueOf(param.get("pageSize").toString());
				}
				if (param.get("page") != null && !"".equals(param.get("page").toString())) {
					page = Integer.valueOf(param.get("page").toString());
				}
				keyword = param.get("keyword") == null ? keyword :  param.get("keyword").toString();
				deviceType = param.get("deviceType") == null ? deviceType :  param.get("deviceType").toString();
			}
			pageEntity.setPageSize(pageSize);
			pageEntity.setPage(page);
			pageEntity.setDevice_type(deviceType);
			pageEntity.setDevice_tpl_desc(keyword);
			pageEntity.setDevice_tpl_ID(keyword);
			
			pageEntity = deviceRunConfService.selRunTempListByParam(pageEntity);	
			result.setState(true);
			result.setMessage(Const.SUCCESS);
			result.setResult(pageEntity);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 新增运行模板配置
	 *
	 * <p>2016年6月15日 下午5:13:24 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/addRunTemp")
	@ResponseBody
	public ResultEntity addRunTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceRunConfService.addRunTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新运行模板配置
	 *
	 * <p>2016年6月15日 下午5:12:23 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/updateRunTemp")
	@ResponseBody
	public ResultEntity updateRunTemp(HttpServletRequest request ){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceRunConfService.updateRunTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除运行模板配置
	 *
	 * <p>2016年6月15日 下午5:12:23 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/delRunTemp")
	@ResponseBody
	public ResultEntity delRunTemp(HttpServletRequest request ){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceRunConfService.delRunTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 批量删除运行模板配置信息
	 *
	 * <p>2016年6月15日 下午5:12:23 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/delMultiRunTemp")
	@ResponseBody
	public ResultEntity delMultiRunTemp(HttpServletRequest request ){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}") || req.equals("[]")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceRunConfService.delMultiRunTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("SelFunctionByDeviceIdx")
	@ResponseBody
	public ResultEntity SelFunctionByDeviceIdx(HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceRunConfService.SelFunctionByDeviceIdx(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
}
