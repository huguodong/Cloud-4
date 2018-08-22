package com.ssitcloud.dbauth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.LibraryExtendInfoEntity;
import com.ssitcloud.dbauth.service.LibraryExtendInfoService;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * 图书馆扩展信息处理类
 * <p>2017年4月26日 下午1:54
 * @author shuangjunjie
 *
 */
@Controller
@RequestMapping("/libraryExtendInfo")
public class LibraryExtendInfoController{
	@Resource
	private LibraryExtendInfoService libraryExtendInfoService;
	
	
	/**
	 * 根据library_idx查出region_idx
	 * json = {
	 * 		"library_idx":"163"
	 * }
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selRegionIdxByLibIdx"})
	@ResponseBody
	public ResultEntity selRegionIdxByLibIdx(String req,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		LibraryExtendInfoEntity libraryExtendInfoEntity = new LibraryExtendInfoEntity();
				
		try {
			if (!StringUtils.isBlank(req)) {
				libraryExtendInfoEntity = JsonUtils.fromJson(req, LibraryExtendInfoEntity.class);
				libraryExtendInfoEntity = libraryExtendInfoService.selRegionIdxByLibIdx(libraryExtendInfoEntity);
				resultEntity.setValue(true, "success","",libraryExtendInfoEntity);
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
	 * 根据library_idxs查出region_idxs
	 * json = []
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selRegionIdxsByLibIdxs"})
	@ResponseBody
	public ResultEntity selRegionIdxsByLibIdxs(String req,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isNotBlank(req)) {
				List l  = JsonUtils.fromJson(req, List.class);
				Map map = new HashMap();
				map.put("libIdxs", l);
				List<LibraryExtendInfoEntity> list = libraryExtendInfoService.selRegionIdxsByLibIdxs(map);
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

}
