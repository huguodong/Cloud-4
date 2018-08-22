package com.ssitcloud.dbauth.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;










import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.LibraryEntity;
import com.ssitcloud.dbauth.entity.LibraryServiceTemplateEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.page.LibraryServiceTemplatePageEntity;
import com.ssitcloud.dbauth.service.LibraryTemplateService;
import com.ssitcloud.dbauth.service.OperatorService;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * 图书馆模板处理类
 * <p>2016年4月5日 上午11:18:30
 * @author hjc
 *
 */
@Controller
@RequestMapping("/library")
public class LibraryTemplateController {
	@Resource
	private LibraryTemplateService templateService;
	
	@Resource
	private OperatorService operatorService;
	
	/**
	 * 新增图书馆模板
	 * 
	 * <p>2016年4月5日 下午2:27:57
	 * <p>create by hjc
	 * @param libTempInfo 图书馆模板信息，json格式，
	 * 			如{lib_service_tpl_desc:"描述",service_expire_date:2,
	 * 				max_device_count:1,max_operator_cout:1,max_sublib_count:2}
	 * @param request
	 * @return
	 */
	@RequestMapping("/addLibraryTemplate")
	@ResponseBody
	public ResultEntity addLibraryTemplate(String json,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		//ObjectMapper mapper = new ObjectMapper();
		//LibraryServiceTemplateEntity templateEntity = new LibraryServiceTemplateEntity();
		try {
			//templateEntity = JsonUtils.fromJson(libTempInfo, LibraryServiceTemplateEntity.class);
			//templateEntity = mapper.readValue(libTempInfo, LibraryServiceTemplateEntity.class);
			int ret = templateService.addLibraryTemplate(json);
			if (ret >= 1) {
				resultEntity.setValue(true, "success");
			}else {
				resultEntity.setValue(false, "failed");
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
	 * 根据图书馆模板ID删除模板信息
	 * 
	 * <p>2016年4月6日 下午2:04:49
	 * <p>create by hjc
	 * @param libTempInfo 模板信息如{lib_service_tpl_id:"1"}
	 * @param request
	 * @return ResultEntity结果类
	 */
	@RequestMapping("/delLibraryTemplateById")
	@ResponseBody
	public ResultEntity delLibraryTemplateById(String req,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		//ObjectMapper mapper = new ObjectMapper();
		//LibraryServiceTemplateEntity templateEntity = new LibraryServiceTemplateEntity();
		try {
//			int ret = templateService.delLibraryTemplateById(json);
			resultEntity = templateService.delLibraryTemplate(req);
//			if (ret >= 1) {
//				resultEntity.setValue(true, "success");
//			}else {
//				resultEntity.setValue(false, "failed");
//			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 分页查询图书馆模板列表
	 *
	 * <p>2016年4月21日 下午2:43:52
	 * <p>create by hjc
	 * @param libTempInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/selLibraryTempList")
	@ResponseBody
	public ResultEntity selLibraryTempList(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			LibraryServiceTemplatePageEntity libtemp = templateService.selLibraryTemp(json);
			resultEntity.setValue(true, "success", "", libtemp);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 更新图书馆模板信息 
	 *
	 * <p>2016年4月21日 下午3:30:32
	 * <p>create by hjc
	 * @param libTempInfo 图书馆模板信息，json格式，
	 * 			如{lib_service_tpl_id:"1",lib_service_tpl_desc:"描述",service_expire_date:2,
	 * 				max_device_count:1,max_operator_cout:1,max_sublib_count:2}
	 * @param request
	 * @return
	 */
	@RequestMapping("/updLibraryTemplate")
	@ResponseBody
	public ResultEntity updLibraryTemplate(String req,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = templateService.updLibraryTemplate(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/selAllLibTemp")
	@ResponseBody
	public ResultEntity selAllLibTemp(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<LibraryServiceTemplateEntity> libtemp = templateService.selAllLibraryTemp();
			resultEntity.setValue(true, "success", "", libtemp);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
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
		Map<String, Object> resultMap = new HashMap<String, Object>();	
		try {
			if(json!=null && !json.equals("")){
				LibraryEntity libraryEntity = JsonUtils.fromJson(json, LibraryEntity.class);
				if (libraryEntity.getLibrary_idx()!=null) {
					LibraryServiceTemplateEntity templateEntity = templateService
							.selTempByLibraryIdx(String.valueOf(libraryEntity
									.getLibrary_idx()));
					if (templateEntity==null ) {
						result.setValue(false, "无法查询该图书馆模板信息");
						return result;
					}
					resultMap.put("libraryTemp",templateEntity);
					//查询该图书馆的设备用户
					List<OperatorEntity> olist = operatorService.selDeviceUserByLibraryIdx(String.valueOf(libraryEntity
									.getLibrary_idx()));
					if (olist!=null && olist.size()>0) {
						List<String> idlist = new ArrayList<>();
						for (OperatorEntity operatorEntity : olist) {
							idlist.add(operatorEntity.getOperator_id());
						}
						resultMap.put("deviceIdList", idlist);
					}else{
						resultMap.put("deviceIdList", "");
					}
					result.setValue(true, "success","",resultMap);
					return result;
				}
			}
			result.setValue(false, "");
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	@RequestMapping(value={"selLibraryServiceTemplateByIdx"})
	@ResponseBody
	public ResultEntity selLibraryServiceTemplateByIdx(String req,HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			LibraryServiceTemplateEntity libraryServiceTemplate=templateService.selLibraryServiceTemplateByIdx(req);
			if(libraryServiceTemplate!=null){
				result.setState(true);
				result.setResult(libraryServiceTemplate);
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
}
