package com.ssitcloud.common.netty.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.netty.service.CommonSendMsgService;
import com.ssitcloud.common.util.JsonUtils;


/**
 * 云端主动推送消息到设备终端
 * 
 * @author yeyalin 2018-04-09
 */
@Controller
@RequestMapping(value = { "issue2device" })
public class CommonDataIssueToDeviceController {

	@Autowired
	private CommonSendMsgService commonSendMsgService;
	/**
	 * 云端主动推送消息到设备终端
	 */
	@RequestMapping(value = { "send" })
	@ResponseBody
	public ResultEntity sendData(HttpServletRequest request, String req) {
		System.out.println(req);
		CloudSyncRequest clientReq =JsonUtils.fromJson(req, CloudSyncRequest.class);
		commonSendMsgService.sendMsg(clientReq);
		return  new ResultEntity();
	}
	
	
	

}
