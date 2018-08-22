package com.ssitcloud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.entity.LibraryAcsLogininfoEntity;
import com.ssitcloud.service.LibraryAcsLogininfoServiceI;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年3月2日 下午3:32:17
 */
@Controller
@RequestMapping("/libraryAcsLogininfo")
public class LibraryAcsLogininfoController {
	@Autowired
	private LibraryAcsLogininfoServiceI libraryAcsLogininfoService;
	
	@RequestMapping("/selectLibraryAcsLogininfos")
	@ResponseBody
	public ResultEntity selectLibraryAcsLogininfos(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();

		String json = request.getParameter("json");
		if (json == null) {
			resultEntity.setValue(false, "参数不正确");
			return resultEntity;
		}
		try {
			LibraryAcsLogininfoEntity acsLogininfoEntity = JsonUtils.fromJson(json, LibraryAcsLogininfoEntity.class);
			List<LibraryAcsLogininfoEntity> selectLibraryAcsLogininfos = libraryAcsLogininfoService.selectLibraryAcsLogininfos(acsLogininfoEntity);
			resultEntity.setState(true);
			resultEntity.setResult(selectLibraryAcsLogininfos);
			return resultEntity;
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "()异常" + e.getMessage(), "");
			LogUtils.error(methodName + "()异常", e);
			return resultEntity;
		}
	}
}
