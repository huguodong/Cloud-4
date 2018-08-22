package com.ssitcloud.business.datasync.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.datasync.service.DataSyncService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.datasync.entity.UploadcfgSynResultEntity;

@Controller
@RequestMapping(value={"data"})
public class DataSyncController {
	
	@Resource
	private DataSyncService dataSyncService;

	/**
	 *  device_id	String	设备ID
		library_id	String	馆ID
		table		String	表名
		fields		String	表字段
		records		String	数据内容
		格式：
		req={
			"device_id":"",
			"library_id":"",
			"table":"",
			"fields":"",
			"records":""
		}
	 * @methodName: uploadcfgSyn
	 * @param request
	 * @param req
	 * @returnType: void
	 * @author: liuBh
	 */
	@RequestMapping(value={"uploadcfgSyn"})
	@ResponseBody
	public ResultEntityF<UploadcfgSynResultEntity> uploadcfgSyn(HttpServletRequest request,String req){
		//RespEntity
		return dataSyncService.uploadcfgSyn(req);
	}
	/**
	 * 
	 * data:<br/>
	 * device_id String 设备ID <br/>
	 * library_id String 馆ID  <br/>
	 * dBName String 数据库名  <br/>
	 * table String 表名  <br/>
	 * keyName String 主键名  <br/>
	 * lastLocalUpdateTime DateTime 时间  <br/>
	 * 远程请求返回结果 <br/>
	 * ResultEntity.java
	 *
	 * @methodName: downloadCfgSync
	 * @param request
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	@RequestMapping(value={"downloadCfgSync"})
	@ResponseBody
	public ResultEntity downloadCfgSync(HttpServletRequest request,String req){
		return dataSyncService.downloadCfgSync(req);
	}
	@RequestMapping(value={"askVersion"})
	@ResponseBody
	public ResultEntity askVersion(HttpServletRequest request,String req){
		return dataSyncService.askVersion(req);
	}
	@RequestMapping(value={"transData"})
	@ResponseBody
	public ResultEntity transData(HttpServletRequest request,String req){
		return dataSyncService.transData(req);
	}	
}
