package com.ssitcloud.dblib.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.BookInputEntity;
import com.ssitcloud.dblib.entity.page.BookInputPageEntity;
import com.ssitcloud.dblib.service.BookInputService;

@Controller
@RequestMapping("/bookinput")
public class BookInputController {
	@Resource
	private BookInputService bookinputService;
	
	
	/**
	 * 新增记录
	 *
	 * <p>2017年2月7日 下午3:55:35 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertBookInput")
	@ResponseBody
	public ResultEntity insertBookInput(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BookInputEntity bookinputEntity = JsonUtils.fromJson(json, BookInputEntity.class);
				int ret = bookinputService.insertBookInput(bookinputEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookinputEntity);
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
	@RequestMapping("/deleteBookInput")
	@ResponseBody
	public ResultEntity deleteBookInput(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BookInputEntity bookinputEntity = JsonUtils.fromJson(json, BookInputEntity.class);
				int ret = bookinputService.deleteBookInput(bookinputEntity);
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
	@RequestMapping("/updateBookInput")
	@ResponseBody
	public ResultEntity updateBookInput(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BookInputEntity bookinputEntity = JsonUtils.fromJson(json, BookInputEntity.class);
				int ret = bookinputService.updateBookInput(bookinputEntity);
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
	@RequestMapping("/queryBookInput")
	@ResponseBody
	public ResultEntity queryBookInput(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BookInputEntity bookinputEntity = JsonUtils.fromJson(json, BookInputEntity.class);
				bookinputEntity = bookinputService.queryBookInput(bookinputEntity);
				resultEntity.setValue(true, "success","",bookinputEntity);
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
	@RequestMapping("/queryBookInputList")
	@ResponseBody
	public ResultEntity queryBookInputList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			BookInputEntity bookinputEntity = JsonUtils.fromJson(json, BookInputEntity.class);
			List<BookInputEntity> list = bookinputService.queryBookInputList(bookinputEntity);
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
	@RequestMapping("/queryBookInputListByPage")
	@ResponseBody
	public ResultEntity queryBookInputListByPage(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BookInputPageEntity bookinputPageEntity = new BookInputPageEntity();
				
				Map<String, Object> map  = JsonUtils.fromJson(json, Map.class);//查询参数map，可根据自己需要添加
				Integer page = map.get("page")==null?1:Integer.valueOf(map.get("page").toString());				
				Integer pageSize = map.get("pageSize")==null?10:Integer.valueOf(map.get("pageSize").toString());	
				
				bookinputPageEntity.setPage(page);
				bookinputPageEntity.setPageSize(pageSize);
				
				bookinputPageEntity = bookinputService.queryBookInputListByPage(bookinputPageEntity);
				
				resultEntity.setValue(true, "success","",bookinputPageEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	

	
}
