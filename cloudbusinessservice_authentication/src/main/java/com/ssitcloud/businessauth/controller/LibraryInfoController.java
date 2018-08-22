package com.ssitcloud.businessauth.controller;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.authentication.entity.LibraryInfoEntity;
import com.ssitcloud.businessauth.utils.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 图书馆信息处理类
 * <p>2016年4月5日 上午11:18:30
 * @author hjc
 *
 */
@Controller
@RequestMapping("/library")
public class LibraryInfoController {
	
	
	/**
	 * 新增一条图书馆参数信息，如电话，地址之类
	 * 
	 * <p>2016年4月5日 下午5:51:03
	 * <p>create by hjc
	 * @param libInfo 一条数据参数，如{"library_idx":"1",infotype_idx:"1",info_value:"phone"}
	 * @param request
	 * @return 结果集 ResultEntity 
	 */
	@RequestMapping("/addLibraryInfo")
	@ResponseBody
	public ResultEntity addLibraryInfo(String libInfo, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		LibraryInfoEntity infoEntity = new LibraryInfoEntity();
		try {
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}

}
