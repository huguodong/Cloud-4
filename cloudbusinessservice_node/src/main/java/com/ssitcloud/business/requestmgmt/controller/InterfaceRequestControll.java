package com.ssitcloud.business.requestmgmt.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.requestmgmt.service.InterfaceRequestMangementService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.request.entity.InterfaceRequestDto;
import com.ssitcloud.request.entity.InterfaceRequestEntity;


@Controller
@RequestMapping("interfaceRequest")
public class InterfaceRequestControll {
	
	@Autowired
	private InterfaceRequestMangementService interfaceRequestMangementService;
	
	@RequestMapping("addRequest")
	@ResponseBody
	public ResultEntity addRequest(String req){
		ResultEntity resultEntity = new ResultEntity();
		
		int  count = interfaceRequestMangementService.addRequest(req);
		resultEntity.setState(true);
		resultEntity.setResult(count);
		
		return resultEntity;
	}
	
	@RequestMapping("updateRequest")
	@ResponseBody
	public ResultEntity updateRequest(String req){
		
		ResultEntity resultEntity = new ResultEntity();
		int  count = interfaceRequestMangementService.updateRequest(req);
		resultEntity.setState(true);
		resultEntity.setResult(count);
		
		return resultEntity;
		
	}
	
	@RequestMapping("queryRequest")
	@ResponseBody
	public ResultEntity queryRequest(String req){
		
		ResultEntity resultEntity = new ResultEntity();
		List<InterfaceRequestDto>  interfaceRequestDtos = interfaceRequestMangementService.queryRequest(req);
		resultEntity.setState(true);
		resultEntity.setResult(interfaceRequestDtos);
		
		return resultEntity;
	}
	@RequestMapping("deleteRequest")
	@ResponseBody
	public ResultEntity deleteRequest(String req){
		
		ResultEntity resultEntity = new ResultEntity();
		int  count = interfaceRequestMangementService.deleteRequest(req);
		resultEntity.setState(true);
		resultEntity.setResult(count);
		
		return resultEntity;
		
	}
	
	/**
	 * 执行需要处理的请求
	 * @param req
	 * @return
	 */
	@RequestMapping("executeRequest")
	@ResponseBody
	public ResultEntity executeRequest(String req){
		
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setState(true);
		
		List<InterfaceRequestDto>  interfaceRequestDtos = interfaceRequestMangementService.queryRequest(req);
		
		if (CollectionUtils.isEmpty(interfaceRequestDtos)){
			
			return resultEntity;
		}
		
		//重新执行失败的请求
		for (InterfaceRequestEntity interfaceRequestDto :interfaceRequestDtos ){
			
			String requestParam = interfaceRequestDto.getRequestBody();
			String requestUrl = interfaceRequestDto.getRequestUrl();
			
			Map<String, String> param = new ConcurrentHashMap<>();
			param.put("req", requestParam);
			System.out.println("重新执行失败的请求------>,req" + req);
			HttpClientUtil.doPost(requestUrl, param,"UTF-8");
		}
		resultEntity.setResult(interfaceRequestDtos);
		
		return resultEntity;
		
	}
}
