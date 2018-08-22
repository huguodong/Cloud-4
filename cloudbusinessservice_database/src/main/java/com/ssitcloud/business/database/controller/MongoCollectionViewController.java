package com.ssitcloud.business.database.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.database.service.MongoCollectionService;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.database.entity.MongoCollectionEntity;
import com.ssitcloud.database.entity.Server;


@Controller
@RequestMapping(value = { "collection" })
public class MongoCollectionViewController{

	@Resource
	private MongoCollectionService collectionService;

	/** 分页查询 */
	@RequestMapping(value = { "properties" })
	@ResponseBody
	public ResultEntity properties(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		MongoCollectionEntity collection = JsonUtils.fromJson(req, MongoCollectionEntity.class);
		Server server = collectionService.getMongoServer(collection.getServer_id());
		Map<String, Object> model = new HashMap<>();
		model.put("collection", collection);
		model.put("server", server);
		result.setValue(true, "", "", model);
		return result;
	}

	
	@RequestMapping(value = { "collectionData" })
	@ResponseBody
	public ResultEntity collectionData(HttpServletRequest request, String req) {
		PageEntity page = collectionService.collectionData(req);
		ResultEntity result = new ResultEntity();
		if (page != null) {
			result.setValue(true, "success", "", page);
		} else {
			result.setState(false);
		}
		return result;
	}
	
	@RequestMapping(value = { "collectionIndex" })
	@ResponseBody
	public ResultEntity collectionIndex(HttpServletRequest request, String req) {
		MongoCollectionEntity collection = collectionService.collectionIndex(req);
		ResultEntity result = new ResultEntity();
		if (collection != null) {
			result.setValue(true, "success", "", collection);
		} else {
			result.setState(false);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteDocument" })
	@ResponseBody
	public ResultEntity deleteDocument(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(collectionService.deleteDocument(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "addDocument" })
	@ResponseBody
	public ResultEntity addDocument(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(collectionService.addDocument(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateDocument" })
	@ResponseBody
	public ResultEntity updateDocument(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(collectionService.updateDocument(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "addcollectionAction" })
	@ResponseBody
	public ResultEntity addcollectionAction(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(collectionService.addcollectionAction(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
}
