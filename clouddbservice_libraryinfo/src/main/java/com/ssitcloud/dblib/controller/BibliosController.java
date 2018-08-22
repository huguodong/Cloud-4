package com.ssitcloud.dblib.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.BibliosEntity;
import com.ssitcloud.dblib.entity.page.BibliosPageEntity;
import com.ssitcloud.dblib.service.BibliosService;

@Controller
@RequestMapping("/biblios")
public class BibliosController {
	@Resource
	private BibliosService bibliosService;
	
	
	/**
	 * 新增记录
	 *
	 * <p>2017年2月7日 下午3:55:35 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertBiblios")
	@ResponseBody
	public ResultEntity insertBiblios(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BibliosEntity bibliosEntity = JsonUtils.fromJson(json, BibliosEntity.class);
				int ret = bibliosService.insertBiblios(bibliosEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bibliosEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月7日 下午4:48:46 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteBiblios")
	@ResponseBody
	public ResultEntity deleteBiblios(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BibliosEntity bibliosEntity = JsonUtils.fromJson(json, BibliosEntity.class);
				int ret = bibliosService.deleteBiblios(bibliosEntity);
				if (ret>=0) {
					resultEntity.setValue(true, "success");
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新记录
	 *
	 * <p>2017年2月7日 下午4:49:04 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateBiblios")
	@ResponseBody
	public ResultEntity updateBiblios(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BibliosEntity bibliosEntity = JsonUtils.fromJson(json, BibliosEntity.class);
				int ret = bibliosService.updateBiblios(bibliosEntity);
				if (ret>=1) {
					resultEntity.setValue(true, "success");
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询单条记录
	 *
	 * <p>2017年2月7日 下午4:50:05 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryBiblios")
	@ResponseBody
	public ResultEntity queryBiblios(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BibliosEntity bibliosEntity = JsonUtils.fromJson(json, BibliosEntity.class);
				bibliosEntity = bibliosService.queryBiblios(bibliosEntity);
				resultEntity.setValue(true, "success","",bibliosEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月7日 下午4:49:49 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryBibliosList")
	@ResponseBody
	public ResultEntity queryBibliosList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			BibliosEntity bibliosEntity = JsonUtils.fromJson(json, BibliosEntity.class);
			List<BibliosEntity> list = bibliosService.queryBibliosList(bibliosEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	/**
	 * 分页查询,参数可以根据需要添加
	 *
	 * <p>2017年2月9日 上午9:40:47 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryBibliosListByPage")
	@ResponseBody
	public ResultEntity queryBibliosListByPage(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BibliosPageEntity bibliosPageEntity = new BibliosPageEntity();
				
				Map<String, Object> map  = JsonUtils.fromJson(json, Map.class);//查询参数map，可根据自己需要添加
				Integer page = map.get("page")==null?1:Integer.valueOf(map.get("page").toString());				
				Integer pageSize = map.get("pageSize")==null?10:Integer.valueOf(map.get("pageSize").toString());	
				String title = map.get("title")==null?"":map.get("title").toString();
				
				bibliosPageEntity.setPage(page);
				bibliosPageEntity.setPageSize(pageSize);
				bibliosPageEntity.setTitle(title);
				
				bibliosPageEntity = bibliosService.queryBibliosListByPage(bibliosPageEntity);
				
				resultEntity.setValue(true, "success","",bibliosPageEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"queryAllBiblios"})
	@ResponseBody
	public ResultEntity queryAllBiblios(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			List<BibliosEntity> list = bibliosService.queryAllBiblios();
			result.setResult(list);
			result.setMessage("success");
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 通过book_barcode和lib_id查询图书信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryBibliosForBCAndLib")
	@ResponseBody
	public ResultEntity queryBibliosForBCAndLib(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				Map<String, Object> param = JsonUtils.fromJson(json, Map.class);
				BibliosEntity queryBibliosForBCAndLib = bibliosService.queryBibliosForBCAndLib(param );
				resultEntity.setValue(true, "success","",queryBibliosForBCAndLib);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value = { "queryBibliosByISBN" })
	@ResponseBody
	public ResultEntity queryBibliosByISBN(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			BibliosEntity bibliosEntity = bibliosService.queryBibliosByISBN(JsonUtils.fromJson(req, BibliosEntity.class));
			result.setResult(bibliosEntity);
			result.setState(true);
			result.setMessage("success");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "queryBibliosByTitleAndAuthor" })
	@ResponseBody
	public ResultEntity queryBibliosByTitleAndAuthor(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			BibliosEntity bibliosEntity = bibliosService.queryBibliosByTitleAndAuthor(JsonUtils.fromJson(req, BibliosEntity.class));
			result.setResult(bibliosEntity);
			result.setState(true);
			result.setMessage("success");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteBibliosById" })
	@ResponseBody
	public ResultEntity deleteBibliosById(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONArray jsonarray = JSONArray.fromObject(req);
			List<BibliosEntity> list = (List<BibliosEntity>)JSONArray.toCollection(jsonarray, BibliosEntity.class); 
			int re = bibliosService.deleteBibliosById(list);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? "success" : "feiled");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateBibliosByISBN" })
	@ResponseBody
	public ResultEntity updateBiblios(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bibliosService.updateBibliosByISBN(JsonUtils.fromJson(req, BibliosEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? "success" : "feiled");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addBiblios" })
	@ResponseBody
	public ResultEntity addBiblios(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bibliosService.addBiblios(JsonUtils.fromJson(req, BibliosEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? "success" : "feiled");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping("uploadBiblios")
	@ResponseBody
	public ResultEntity uploadReaderCard(@RequestParam("file") CommonsMultipartFile file,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = bibliosService.uploadBiblios(file,req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			result.setMessage(Const.FAILED);
			result.setRetMessage(methodName+"()异常:"+e.getMessage());
		}
		return result;
	}
}
