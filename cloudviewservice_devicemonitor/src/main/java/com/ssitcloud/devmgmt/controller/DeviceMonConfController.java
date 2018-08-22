package com.ssitcloud.devmgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.I18nUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.devmgmt.entity.MetadataLogicObjEntity;
import com.ssitcloud.devmgmt.entity.PlcLogicObjEntity;
import com.ssitcloud.devmgmt.service.DeviceMonConfService;

/**
 * 
 * 监控页面配置控制器
 * @author lbh
 *
 */
@Controller
@RequestMapping(value={"devicemonconf"})
public class DeviceMonConfController {
	
	@Resource
	private DeviceMonConfService deviceMonConfService;
	
	@Value("${plcSorter}")
	private String plcSorter;//分拣机部件
	
	@Value("${plcReturn}")
	private String plcReturn;//还书机部件
	/**
	 * 设备监控主页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"main"})
	public ModelAndView main(HttpServletRequest request,String req){
		
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
		
		return new ModelAndView("/page/devmgmt/deivcemonitor_conf",model);
		
	}
	/**
	 * 新建设备监控模板
	 * * req={
	 * 	"device_tpl_ID":""
	 * 	"device_tpl_desc":""
	 * 	"device_type":""
	 * }
	 * @methodName: AddDeviceConfigTemplate
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"AddDeviceConfigTemplate"})
	@ResponseBody
	public ResultEntity AddDeviceConfigTemplate(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.AddDeviceConfigTemplate(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result,Thread.currentThread().getStackTrace()[1].getMethodName(),e);
		}
		return result;
	}
	
	/**
	 * 带参数查询 分页页面
	 * @param request
	 * @param req ={page:"",pageSize:"",......}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"queryDeviceMonitorByParam"})
	@ResponseBody
	public ResultEntity queryDeviceMonitorByParam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.queryDeviceMonitorByParam_view(req);
			if(result!=null){
				//取出数据库中的json字符串
				Map<String,Object> mapResult=(Map<String,Object>)result.getResult();
				if(mapResult!=null){
					List<Map<String,Object>> rows=(List<Map<String, Object>>) mapResult.get("rows");
					for(Map<String,Object> m:rows){
						if(m.get("temp_content")!=null){
							String s=(String)m.get("temp_content");
							m.put("temp_content",I18nUtil.converDelimiterStringLanguage(s));
						}
					}
					//mapResult.put("rows", rows);
				}
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result,Thread.currentThread().getStackTrace()[1].getMethodName(),e);
		}
		return result;
	}
	
	
	/**
	 * 获取颜色配置信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryMonitorColorConf"})
	@ResponseBody
	public ResultEntity queryMonitorColorConf(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.queryMonitorColorConf(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result,Thread.currentThread().getStackTrace()[1].getMethodName(),e);
		}
		return result;
	}
	/**
	 * 新增 监控配置模板 数据
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"AddMonitorConfTemp"})
	@ResponseBody
	public ResultEntity AddMonitorConfTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.AddNewDeviceMonitorConfAndTemp_view(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result,Thread.currentThread().getStackTrace()[1].getMethodName(),e);
		}
		return result;
	}
	/**
	 * 更新 监控配置模板
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"UpdMonitorConfTemp"})
	@ResponseBody
	public ResultEntity UpdMonitorConfTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.UpdNewDeviceMonitorConfAndTemp_view(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result,Thread.currentThread().getStackTrace()[1].getMethodName(),e);
		}
		return result;
	}
	/**
	 * 删除监控配置模板
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"DelMonitorConfTemp"})
	@ResponseBody
	public ResultEntity DelMonitorConfTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.DelNewDeviceMonitorConfAndTemp_view(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result,Thread.currentThread().getStackTrace()[1].getMethodName(),e);
		}
		return result;
	}
	/**
	 * 无参数 ，取出MetadataLogicObject
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"SelMetadataLogicObject"})
	@ResponseBody
	public ResultEntity SelMetadataLogicObject(HttpServletRequest request,String req){
		
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.SelMetadataLogicObject(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result,Thread.currentThread().getStackTrace()[1].getMethodName(),e);
		}
		return result;
	}
	@RequestMapping(value={"SelDevMonConfMetaLogObjByDeviceMonTplIdx"})
	@ResponseBody
	public ResultEntity SelDevMonConfMetaLogObjByDeviceMonTplIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.SelDevMonConfMetaLogObjByDeviceMonTplIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result,Thread.currentThread().getStackTrace()[1].getMethodName(),e);
		}
		return result;
	}
	
	/**
	 * 查询设备的报警颜色，返回值应该是MAP 类型 {"设备id":"报警颜色"}
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
			ExceptionHelper.afterException(result,Thread.currentThread().getStackTrace()[1].getMethodName(),e);
		}
		return result;
	}
	
}
