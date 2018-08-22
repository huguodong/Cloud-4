package com.ssitcloud.view.devmgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.devmgmt.service.DeviceTypeService;

/**
 * 
 * @package com.ssitcloud.devmgmt.controller
 * @comment
 * @data 2016年4月18日
 * @author hwl
 */
@Controller
@RequestMapping("/metadevicetype")
public class DeviceTypeController extends BasicController{
		
	@Resource
	DeviceTypeService deviceTypeService;
		
	/**
	 * 页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"main"})
	public String main(HttpServletRequest request){
		return "/page/devmgmt/devicetype";
	}
	
	
	@RequestMapping("/SelectMetaDeviceType")
	@ResponseBody
	public ResultEntity SelectDeviceMgmt(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			String resps = deviceTypeService.SelDeviceType(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	@RequestMapping("/SelectType")
	@ResponseBody
	public ResultEntity SelectType(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			
			String resps = deviceTypeService.QueryType(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	@RequestMapping("/DeleteMetaDeviceType")
	@ResponseBody
	public ResultEntity DeleteDeviceMgmt(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			
			String resps = deviceTypeService.DeleteDeviceType(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DELETE_DEVICE_TYPE);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	/**
	 * 设备类型新增操作
	 * opercmd 0102031201
	 * @param request
	 * @return
	 */
	@RequestMapping("/AddMetaDeviceType")
	@ResponseBody
	public ResultEntity AddDeviceMgmt(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceTypeService.AddDeviceType(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_ADD_DEVICE_TYPE);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	@RequestMapping("/UpdMetaDeviceType")
	@ResponseBody
	public ResultEntity UpdDeviceMgmt(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceTypeService.UpdDeviceType(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_UPDATE_DEVICE_TYPE);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	/**
	 * 查询 设备类型 按device_type 分组
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selAllDeviceTypeGroupByType"})
	@ResponseBody
	public ResultEntity selAllDeviceTypeGroupByType(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceTypeService.selAllDeviceTypeGroupByType();
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	/**
	 * 根据设备类型 获取对应的  metadata_logic_obj
		数据格式 为：
		{
			[
				{
					metadataDeviceType1,
					[metadata_logic_obj1,metadata_logic_obj2]
				},
				{
					.....
				}
			]
		}
		@param req={}  空
	 * @return
	 */
	@RequestMapping(value={"queryDeviceTypeLogicObj"})
	@ResponseBody
	public ResultEntity queryDeviceTypeLogicObj(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceTypeService.queryDeviceTypeLogicObj(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	/**
	 * 
	 * 
	 * 获取所有的逻辑部件信息
	 * @param request req="" 不需要参数
	 * @return
	 */
	@RequestMapping(value={"selectMetadataLogicObj"})
	@ResponseBody
	public ResultEntity selectMetadataLogicObj(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceTypeService.selectMetadataLogicObj(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	/**
	 * 
	 * 
	 * 获取所有的设备数据库信息
	 * @param request req="" 不需要参数
	 * @return
	 */
	@RequestMapping(value={"selectMetadataDeviceDb"})
	@ResponseBody
	public ResultEntity selectMetadataDeviceDb(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceTypeService.selectMetadataDeviceDb(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}

	@RequestMapping(value={"selectMetadataDeviceDbAndTableInfo"})
	@ResponseBody
	public ResultEntity selectMetadataDeviceDbAndTableInfo(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceTypeService.selectMetadataDeviceDbAndTableInfo(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	
	/**
	 * 查询硬件元数据list
	 *
	 * <p>2017年7月25日 下午3:29:07 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 * 
	 * {
		    "state":true,
		    "message":"SUCCESS",
		    "retMessage":"",
		    "result":{
		        "extModelList":[
		            {
		                "meta_ext_idx":1,
		                "ext_model":"CRV100D",
		                "ext_model_desc":"{"zh_CN":"华视身份证阅读器"}",
		                "ext_model_driver_path":"",
		                "ext_model_version":"V3",
		                "ext_type":"idreader"
		            },
		            {
		                "meta_ext_idx":2,
		                "ext_model":"YK347I",
		                "ext_model_desc":"{"zh_CN":"研科打印机"}",
		                "ext_model_driver_path":"",
		                "ext_model_version":"-",
		                "ext_type":"printer"
		            }
		        ],
		        "logicObjList":[
		            {
		                "meta_log_obj_idx":1,
		                "logic_obj":"AuthorityBarcode",
		                "logic_obj_desc":"{"zh_CN":"读者证条码枪"}"
		            },
		            {
		                "meta_log_obj_idx":2,
		                "logic_obj":"AuthorityReader",
		                "logic_obj_desc":"{"zh_CN":"读者证阅读器"}"
		            },
		            {
		                "meta_log_obj_idx":3,
		                "logic_obj":"CardDispenser",
		                "logic_obj_desc":"{"zh_CN":"发卡机"}"
		            }
		        ]
		    }
		}

©2014 JSON.cn All right reserved. 京ICP备15025187号-
	 */
	@RequestMapping("/qyeryDeviceExtList")
	@ResponseBody
	public ResultEntity qyeryDeviceExtList(HttpServletRequest request, String  req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceTypeService.qyeryDeviceExtList(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
}
