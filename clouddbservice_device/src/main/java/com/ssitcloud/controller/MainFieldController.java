package com.ssitcloud.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.MainfieldEntity;
import com.ssitcloud.service.MainfieldService;

@Controller
@RequestMapping("/mainfield")
public class MainFieldController {
	@Resource
	private MainfieldService mainfieldService;
	
	/**
	 * 新增信息主字段记录
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/addMainField")
	@ResponseBody
	public ResultEntity addMainField(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				MainfieldEntity mainfieldEntity = JsonUtils.fromJson(json, MainfieldEntity.class);
				int ret = mainfieldService.addMainField(mainfieldEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",mainfieldEntity);
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
	 * 根据idx删除信息主字段记录
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delMainField")
	@ResponseBody
	public ResultEntity delMainField(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				MainfieldEntity mainfieldEntity = JsonUtils.fromJson(json, MainfieldEntity.class);
				int ret = mainfieldService.delMainField(mainfieldEntity);
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
	 * 根据idx更新信息主字段记录
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updMainField")
	@ResponseBody
	public ResultEntity updMainField(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				MainfieldEntity mainfieldEntity = JsonUtils.fromJson(json, MainfieldEntity.class);
				int ret = mainfieldService.updMainField(mainfieldEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",mainfieldEntity);
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
	 * 根据IDX查询单条信息主字段记录
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectMainfieldByIdx")
	@ResponseBody
	public ResultEntity selectMainfieldByIdx(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				MainfieldEntity mainfieldEntity = JsonUtils.fromJson(json, MainfieldEntity.class);
				mainfieldEntity = mainfieldService.selMainfieldByIdx(mainfieldEntity);
				resultEntity.setValue(true, "success","",mainfieldEntity);
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
	 * 分页查询 信息主字段记录
	 *
	 * <p>2017年2月24日 上午11:06
	 * <p>create by shuangjunjie
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectMainFieldByPage")
	@ResponseBody
	public ResultEntity selectMainFieldByPage(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			result = mainfieldService.selectMainFieldByPage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 获取mainfield表中根据表名group by 之后的字段集合
	 *
	 * <p>2017年3月3日 下午4:25:31 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMainfieldCollection")
	@ResponseBody
	public ResultEntity getMainfieldCollection(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result = mainfieldService.listMainfieldCollection();
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 根据传过来的字段列表添加字段到mainfield 表中，
	 * 保存之前还要查询一遍是否有这个字段
	 *
	 * <p>2017年3月4日 下午5:40:28 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/additionalMainfieldList")
	@ResponseBody
	public ResultEntity additionalMainfieldList(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String mainfield_field = request.getParameter("mainfield_field");
			String mainfield_table = request.getParameter("mainfield_table");
			if(StringUtils.isBlank(mainfield_table) 
					|| StringUtils.isBlank(mainfield_field) || !JSONUtils.mayBeJSON(mainfield_field)){
				result.setValue(false, "参数不正确");
				return result;
			}
			result = mainfieldService.additionalMainfieldList(mainfield_table,mainfield_field);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 查询全部
	 * <p>2017年4月14日
	 * <p>create by lqw
	 * @param mainFieldEntity
	 * @return
	 */
	@RequestMapping("/selectMainFieldList")
	@ResponseBody
	public ResultEntity selectMainFieldList(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			MainfieldEntity mainfieldEntity = new MainfieldEntity();
			result = mainfieldService.selectMainFieldList(mainfieldEntity);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
