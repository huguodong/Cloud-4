package com.ssitcloud.dbauth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.LibraryEntity;
import com.ssitcloud.dbauth.entity.page.LibraryPageEntity;
import com.ssitcloud.dbauth.entity.page.LibraryPageTempInfoEntity;
import com.ssitcloud.dbauth.entity.page.LibraryUnionEntity;
import com.ssitcloud.dbauth.param.LibraryParam;
import com.ssitcloud.dbauth.service.LibraryService;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * 图书馆处理类
 * <p>2016年4月5日 上午11:18:30
 * @author hjc
 *
 */
@Controller
@RequestMapping("/library")
public class LibraryController{
	@Resource
	private LibraryService libraryService;
	
	
	/**
	 * 新增图书馆信息
	 * 
	 * <p>2016年4月5日 下午3:39:13
	 * <p>create by hjc
	 * @param json 图书馆信息，json格式。如{lib_id:"lib001",lib_name:"libname",lib_type:"1",lib_service_tpl_id:"1",createtime:""}
	 * @param request
	 * @return ResultEntity结果类
	 */
	@RequestMapping("/addLibrary")
	@ResponseBody
	public ResultEntity addLibrary(String json,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		LibraryEntity libraryEntity = new LibraryEntity();
		try {
			libraryEntity = mapper.readValue(json, LibraryEntity.class);
			int ret = libraryService.addLibrary(libraryEntity);
			if (ret >= 1) {
				resultEntity.setValue(true, "success", "", libraryEntity);
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
	 * 根据图书馆表library_idx删除图书馆信息
	 * 
	 * <p>2016年4月6日 下午6:56:45
	 * <p>create by hjc
	 * @param json 图书馆信息 如{library_idx:"1"}
	 * @param request
	 * @return ResultEntity结果类
	 */
	@RequestMapping("/delLibraryByIdx")
	@ResponseBody
	public ResultEntity delLibraryByIdx(String req,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		//ObjectMapper mapper = new ObjectMapper();
		//LibraryEntity libraryEntity = new LibraryEntity();
		try {
			resultEntity = libraryService.delLibraryInfoByIdx(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			
		}
		return resultEntity;
	}
	
	/**
	 * 根据图书馆library_idx 或者lib_id查询图书馆信息
	 *
	 * <p>2016年4月21日 下午5:18:13
	 * <p>create by hjc
	 * @param json {library_idx:"1"} or {lib_id:"1"}
	 * @param request
	 * @return
	 */
	@RequestMapping("/selLibraryByIdxOrId")
	@ResponseBody
	public ResultEntity selLibraryByIdxOrId(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		LibraryEntity libraryEntity = new LibraryEntity();
		try {
			if (!StringUtils.isBlank(json)) {
				libraryEntity = JsonUtils.fromJson(json, LibraryEntity.class);
				libraryEntity = libraryService.selLibraryByIdxOrId(libraryEntity);
				resultEntity.setValue(true, "success","",libraryEntity);
			}else{
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
	 * 根据图书馆library_idx的list 或者lib_id的list查询图书馆信息
	 * 如果不传递参数，返回所有的图书馆信息
	 * <p>2016年4月21日 下午5:18:13
	 * <p>create by hjc
	 * @param json {library_idx:"1,2,3,4"}
	 * or {lib_id:"1,2,3,4"}
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selLibraryByIdxsOrIds")
	@ResponseBody
	public ResultEntity selLibraryByIdxsOrIds(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		List<LibraryEntity> list = new ArrayList<LibraryEntity>();
		Map<String, Object> infos = new HashMap<String, Object>();
		try {
			if (!StringUtils.isBlank(json)) {
				infos = JsonUtils.fromJson(json, Map.class);
				list = libraryService.selLibraryByIdxsOrIds(infos);
				resultEntity.setValue(true, "success","",list);
			}else{
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
	 * 根据图书馆library_idx的list 或者lib_id的list查询图书馆信息
	 *
	 * <p>2016年4月21日 下午5:18:13
	 * <p>create by hjc
	 * @param json {library_idx:"1,2,3,4"}
	 * or {lib_id:"1,2,3,4"}
	 * @param request
	 * @return
	 */

	@RequestMapping("/selLibraryByIdsFuzzy")
	@ResponseBody
	public ResultEntity selLibraryByIdsFuzzy(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		List<LibraryEntity> list = new ArrayList<LibraryEntity>();
		try {
			if (!StringUtils.isBlank(json)) {
				LibraryEntity infos = JsonUtils.fromJson(json, LibraryEntity.class);
				list = libraryService.selLibraryByFuzzy(infos);
				resultEntity.setValue(true, "success","",list);
			}else{
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
	 * 查询  lib_id list 根据 lib_id 模糊查询 
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/selLibraryIDByFuzzy")
	@ResponseBody
	public ResultEntity selLibraryIDByFuzzy(String req,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity= libraryService.selLibraryIDByFuzzy(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 添加一个新图书馆，包括以下表操作
	 * 1、library添加数据
	 * 2、library_info添加数据
	 * 3、记录操作日志
	 * jaon = 
			{
				"operator_idx":"1",
				"client_ip":"127.0.0.1",
				"client_port":"8080",
			    "lib_id":"M42",
			    "lib_name":"猎户座星云",
			    "lib_type":"1",
			    "lib_service_tpl_id":"2",
			    "library_infos":[
			        {
			            "infotype_idx":"15",
			            "info_value":"000-65650650"
			        },
			        {
			            "infotype_idx":"16",
			            "info_value":"宇宙大街"
			        },
			        {
			            "infotype_idx":"17",
			            "info_value":"www.xxx.com"
			        },
			        {
			            "infotype_idx":"17",
			            "info_value":"www.xxx1.com"
			        }
			    ]
			}
	 *
	 * <p>2016年4月21日 下午7:35:49
	 * <p>create by hjc
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping("/addNewLibrary")
	@ResponseBody
	public ResultEntity addNewLibrary(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		LibraryParam libraryParam = new LibraryParam();
		try {
			libraryParam = JsonUtils.fromJson(json, LibraryParam.class);
			if (libraryParam!=null) {
				return libraryService.addNewLibrary(libraryParam);
			}else{
				resultEntity.setValue(false, "图书馆的数据为空");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getCause().getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			
		}
		return resultEntity;
	}
	
	
	
	
	
	/**
	 * 修改图书馆的信息，包括以下表操作(云平台管理员可以修改library表的相关信息)
	 * 1、修改library相关数据
	 * 2、library_info添加数据
	 * 3、记录操作日志
	 * jaon = 
			{
				"operator_idx":"1",
				"client_ip":"127.0.0.1",
				"client_port":"8080",
				"library_idx":"9",
			    "lib_id":"M42",
			    "lib_name":"猎户座星云",
			    "lib_type":"1",
			    "lib_service_tpl_id":"2",
			    "library_infos":[
			        {
			            "infotype_idx":"15",
			            "info_value":"000-65650650"
			        },
			        {
			            "infotype_idx":"16",
			            "info_value":"宇宙大街"
			        },
			        {
			            "infotype_idx":"17",
			            "info_value":"www.xxx.com"
			        },
			        {
			            "infotype_idx":"17",
			            "info_value":"www.xxx1.com"
			        }
			    ]
			}
	 *
	 * <p>2016年4月21日 下午7:35:49
	 * <p>create by hjc
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping("/modifyLibraty")
	@ResponseBody
	public ResultEntity modifyLibraty(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		LibraryParam libraryParam = new LibraryParam();
		try {
			libraryParam = JsonUtils.fromJson(json, LibraryParam.class);
			if (libraryParam!=null) {
				return libraryService.modifyLibrary(libraryParam);
			}else{
				resultEntity.setValue(false, "图书馆的数据为空");
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
	 * 分页查询图书馆信息
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月26日`
	 * @author hwl
	 */
	@RequestMapping("/sellibraryinfo")
	@ResponseBody
	public ResultEntity sellibraryinfo(String json,HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			LibraryPageEntity libpage = libraryService.sellibraryInfo(json);
			result.setValue(true, "success","",libpage);	
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 根据条件，分页查询图书馆信息
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月28日
	 * @author hwl
	 */
	@RequestMapping("/sellibinfoByParam")
	@ResponseBody
	public ResultEntity sellibinfoByParam(String json,HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			LibraryPageTempInfoEntity libpage = libraryService.selLibInfoByParam(json);
			result.setValue(true, "success","",libpage);	
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 查询所有主馆
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月26日`
	 * @author hwl
	 */
	@RequestMapping("/selMasterLib")
	@ResponseBody
	public ResultEntity selMasterLib(String json,HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<LibraryEntity> libpage = libraryService.selMasterLib();
			result.setValue(true, "success","",libpage);	
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 根据当前登陆用户获取相应的图书馆信息。
	 * 如果是系统管理员 ，则 可以获取全部图书馆信息。
	 * @param req
	 * @param request
	 * @return
	 */
	
	@RequestMapping("/querylibInfoByCurUser")
	@ResponseBody
	public ResultEntity querylibInfoByCurUser(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result=libraryService.querylibInfoByCurUser(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 根据当前登陆用户获取相应的图书馆信息。和分馆信息
	 * 如果是系统管理员 ，则 可以获取全部图书馆信息。
	 * @param req
	 * @param request
	 * @return
	 */
	
	@RequestMapping("/querylibInfoByCurUserEXT1")
	@ResponseBody
	public ResultEntity querylibInfoByCurUserEXT1(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result=libraryService.querylibInfoByCurUserEXT1(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 根据父馆IDX查询下属馆的信息
	 * 返回信息中包括父馆信息和下属馆信息
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"querySlaveLibraryByMasterIdx"})
	@ResponseBody
	public ResultEntity querySlaveLibraryByMasterIdx(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result=libraryService.querySlaveLibraryByMasterIdx(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	/**
	 * 获取所有的不是slave的馆和其一级子馆
	 * 只有管理员可以使用
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"queryAllMasterLibAndSlaveLib"})
	@ResponseBody
	public ResultEntity queryAllMasterLibAndSlaveLib(String req,HttpServletRequest request){
		
		ResultEntity result = new ResultEntity();
		try {
			result=libraryService.queryAllMasterLibAndSlaveLib(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	/**
	 * 查询 LibId And LibIdx
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"getLibIdAndLibIdx"})
	@ResponseBody
	public ResultEntity getLibIdAndLibIdx(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result=libraryService.getLibIdAndLibIdx(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 根据父馆的idx查询子馆信息,信息内包含图书馆信息。详见实体LibraryUnionEntity.class
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectChildLibrary"})
	@ResponseBody
	public ResultEntity selectChildLibrary(String req){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, Object> m = JsonUtils.fromJson(req, Map.class); 
			result=libraryService.selectChildLibrary(m,(Integer)m.get("pageCurrent"),(Integer)m.get("pageSize"));
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}

	/**
	 * 获取所有的图书馆信息(elasticsreach需要的数据)
	 *
	 * <p>2017年3月21日 下午2:10:50 
	 * <p>create by hjc
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllLibraryList")
	@ResponseBody
	public ResultEntity getAllLibraryList(String req, HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result=libraryService.getAllLibraryList(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 通过图书馆ID或名称模糊查询
	 *lqw 2017/3/22
	 */
	@RequestMapping(value={"selLibraryByNameORLibId"})
	@ResponseBody
	public ResultEntity selLibraryByNameORLibId(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result=libraryService.selLibraryByNameORLibId(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	/**
	 * 根据父馆IDX查询下属馆的信息
	 * 返回信息中包括父馆信息和下属馆信息
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"queryMasterLibraryBySlaveIdx"})
	@ResponseBody
	public ResultEntity queryMasterLibraryBySlaveIdx(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result=libraryService.queryMasterLibraryBySlaveIdx(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常:"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}

	/**
	 * 查询子馆的idx和地区码
	 */
	@RequestMapping(value={"selectChildLibraryRegionCode"})
	@ResponseBody
	public ResultEntity selectChildLibraryRegionCode(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			if(map.containsKey("master_lib_idx")){
				List<Map<String, Object>> d = libraryService.selectChildLibraryRegionCode((Integer) map.get("master_lib_idx"));
				result.setState(true);
				result.setResult(d);
			}else{
				result.setMessage("master_lib_idx is null");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常:"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	@RequestMapping("/selectLibraryAndInfo")
	@ResponseBody
	public ResultEntity selectLibraryAndInfo(String req){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			if(map != null){
				Integer library_idx = (Integer) map.get("library_idx");
				if(library_idx != null){
					List<LibraryUnionEntity> d = libraryService.selectLibraryAndInfo(map);
					result.setState(true);
					result.setResult(d);
				}else{
					result.setMessage("library_idx is null");
				}
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常:"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
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
