package com.ssitcloud.businessauth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.businessauth.service.LibraryService;
import com.ssitcloud.businessauth.utils.JsonUtils;
import com.ssitcloud.businessauth.utils.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 图书馆处理类
 * <p>2016年4月5日 上午11:18:30
 * @author hjc
 *
 */
@Controller
@RequestMapping("/library")
public class LibraryController {
	@Resource
	private LibraryService libraryService;
	
	
	/**
	 * 新增图书馆
	 *
	 * <p>2016年4月21日 下午5:59:13
	 * <p>create by hjc
	 * @param json 图书馆信息，json格式。如{lib_id:"lib001",lib_name:"libname",lib_type:"1",lib_service_tpl_id:"1",createtime:""}
	 * @param request
	 * @return
	 */
	@RequestMapping("/addLibrary")
	@ResponseBody
	public ResultEntity addLibrary(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String , String > param = new HashMap<>();
			param.put("json", json);
			String response = libraryService.addLibrary(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 根据图书馆表library_idx删除图书馆信息
	 * 
	 * <p>2016年4月6日 下午6:56:45
	 * <p>create by hjc
	 * @param json 图书馆信息 如{library_idx:"1"}
	 * @param request
	 * @return RespEntity结果类
	 */
	@RequestMapping("/delLibraryByIdx")
	@ResponseBody
	public ResultEntity delLibraryByIdx(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String , String > param = new HashMap<>();
			param.put("json", json);
			String response = libraryService.delLibraryByIdx(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 根据图书馆表library_idx,或者图书馆lib_id查询图书馆信息
	 *
	 * <p>2016年4月21日 下午5:25:21
	 * <p>create by hjc
	 * @param json 图书馆信息 如{library_idx:"1"} or {lib_id:"M11"}
	 * @param request
	 * @return
	 */
	
	@RequestMapping("/selLibraryByIdxOrId")
	@ResponseBody
	public ResultEntity selLibraryByIdxOrId(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String , String > param = new HashMap<>();
			param.put("json", json);
			String response = libraryService.selLibraryByIdxOrId(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 根据图书馆表library_idx的list,或者图书馆lib_id的list查询图书馆信息
	 *
	 * <p>2016年4月21日 下午5:25:21
	 * <p>create by hjc
	 * @param json 图书馆信息 如{library_idx:"1,2,3,4"}]
	 * or {lib_id:"1,2,3,4,M46"}
	 * @param request
	 * @return
	 */
	@RequestMapping("/selLibraryByIdxsOrIds")
	@ResponseBody
	public ResultEntity selLibraryByIdxsOrIds(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String , String > param = new HashMap<>();
			param.put("json", json);
			String response = libraryService.selLibraryByIdxsOrIds(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 通过id模糊查询图书馆信息
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月18日`
	 * @author hwl
	 */
	@RequestMapping("/selLibraryByIdFuzzy")
	@ResponseBody
	public ResultEntity selLibraryByIdFuzzy(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String , String > param = new HashMap<>();
			param.put("json", json);
			String response = libraryService.selLibraryByFuzzy(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 查询lib_id list 根据 lib_id 模糊查询 
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/selLibraryIDByFuzzy")
	@ResponseBody
	public ResultEntity selLibraryIDByFuzzy(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = libraryService.selLibraryIDByFuzzy(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 分页查询图书馆数据
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月26日`
	 * @author hwl
	 */
	@RequestMapping("/SelectLibraryInfo")
	@ResponseBody
	public ResultEntity selectlibInfo(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String , String > param = new HashMap<>();
			param.put("json", json);
			String response = libraryService.selectlibinfo(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 查询所有主馆信息
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月26日`
	 * @author hwl
	 */
	@RequestMapping("/SelectMasterLib")
	@ResponseBody
	public ResultEntity SelectMasterLib(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String , String > param = new HashMap<>();
			param.put("json", json);
			String response = libraryService.selectMasterinfo(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 新增图书馆信息
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	@RequestMapping("/AddLibraryInfo")
	@ResponseBody
	public ResultEntity addlibInfo(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String , String > param = new HashMap<>();
			param.put("json", json);
			String response = libraryService.addlibinfo(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 删除图书馆及相关信息
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	@RequestMapping("/DeleteLibraryInfo")
	@ResponseBody
	public ResultEntity deletelibInfo(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = libraryService.deletelibinfo(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getCause().getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新图书馆及相关信息
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	@RequestMapping("/UpdateLibraryInfo")
	@ResponseBody
	public ResultEntity updatelibInfo(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String , String > param = new HashMap<>();
			param.put("json", json);
			String response = libraryService.updatelibinfo(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 条件查询图书馆信息
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	@RequestMapping("/selLibInfoByParam")
	@ResponseBody
	public ResultEntity GetLibInfoByParam(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String , String > param = new HashMap<>();
			param.put("json", json);
			String response = libraryService.selectlibinfoByParam(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 根据当前登陆用户获取相应的图书馆信息
	 * 如果是系统管理员 ，则 可以获取全部图书馆信息。
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"querylibInfoByCurUser"})
	@ResponseBody
	public ResultEntity querylibInfoByCurUser(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity=libraryService.querylibInfoByCurUser(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		
		return resultEntity;
	}
	/**
	 * 根据当前登陆用户获取相应的图书馆信息（可查询出当前馆的子馆信息）
	 * 如果是系统管理员 ，则 可以获取全部图书馆信息。
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"querylibInfoByCurUserEXT1"})
	@ResponseBody
	public ResultEntity querylibInfoByCurUserEXT1(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity=libraryService.querylibInfoByCurUserEXT1(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		
		return resultEntity;
	}
	/**
	 * 通过父馆IDX查询下属管数据
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"querySlaveLibraryByMasterIdx"})
	@ResponseBody
	public ResultEntity querySlaveLibraryByMasterIdx(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity=libraryService.querySlaveLibraryByMasterIdx(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	/**
	 * 获取所有的不是slave的馆和其一级子馆
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"queryAllMasterLibAndSlaveLib"})
	@ResponseBody
	public ResultEntity queryAllMasterLibAndSlaveLib(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity=libraryService.queryAllMasterLibAndSlaveLib(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	/**
	 * 获取library_id 和 library_idx 
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"getLibIdAndLibIdx"})
	@ResponseBody
	public ResultEntity getLibIdAndLibIdx(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity=libraryService.getLibIdAndLibIdx(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 查询所有主从关系的数据
	 *
	 * <p>2017年11月21日 下午2:00:42 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMasterSubRelations")
	@ResponseBody
	public ResultEntity queryMasterSubRelations(String req, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = libraryService.queryMasterSubRelations(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/selActualLibraryMaster")
	@ResponseBody
	public ResultEntity selActualLibraryMaster(HttpServletRequest request,String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = libraryService.selActualLibraryMaster(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
}
