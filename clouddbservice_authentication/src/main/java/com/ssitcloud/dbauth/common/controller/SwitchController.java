package com.ssitcloud.dbauth.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.dbauth.utils.LogUtils;
import com.ssitcloud.dbauth.utils.SendHearbeatUtil;

@Controller
@RequestMapping("switch")
public class SwitchController {
	/**
	 * 接收数据并修改配置文件
	 * 
	 * @param req
	 * @param json
	 * @return
	 */
	@RequestMapping("switch")
	@ResponseBody
	public String switcher(HttpServletRequest request, String req) {
		boolean flag = false;
		try {
			flag = SendHearbeatUtil.modifyURL(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LogUtils.error(methodName + "() 出错了：", e);
		}
		return String.valueOf(flag);
	}
}
