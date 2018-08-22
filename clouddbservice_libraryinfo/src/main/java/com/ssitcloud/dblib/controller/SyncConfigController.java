package com.ssitcloud.dblib.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.dblib.entity.SyncConfigEntity;
import com.ssitcloud.dblib.service.SyncConfigService;

@Controller
@RequestMapping("/syncconfig")
public class SyncConfigController {

	@Resource
	SyncConfigService syncConfigService;
	
	
	@RequestMapping(value="SelSyncConfig")
	@ResponseBody
	public ResultEntityF<List<SyncConfigEntity>> SelSyncConfig(HttpServletRequest request,String req){
		ResultEntityF<List<SyncConfigEntity>>  result=new ResultEntityF<List<SyncConfigEntity>>();
		List<SyncConfigEntity> changes=syncConfigService.SelSyncConfig();
		if(changes!=null){
			result.setResult(changes);
			result.setState(true);
		}
		return result;
	}
	
	@RequestMapping(value={"downloadLibraryInfoCfgSync"})
	@ResponseBody
	public ResultEntity downloadLibraryInfoCfgSync(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=syncConfigService.downloadLibraryInfoCfgSync(req);
		} catch (Exception e) {
			///
		}
		return result;
	}
	
	/**插入数据*/
	@RequestMapping("addCloudDbSyncConfig")
	@ResponseBody
	public ResultEntity addCloudDbSyncConfig(String req){
		return syncConfigService.addCloudDbSyncConfig(req);
	}
	
	@ResponseBody
	@RequestMapping("selectCloudDbSyncConfig")
	public ResultEntity selectCloudDbSyncConfig(String req){
		return syncConfigService.selectCloudDbSyncConfig(req);
	}
}
