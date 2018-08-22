package com.ssitcloud.view.database.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.database.service.MongoCollectionService;

@Controller
@RequestMapping(value = { "collection" })
public class MongoCollectionViewController extends BasicController {

	@Resource
	private MongoCollectionService collectionService;

	/** 分页查询 */
	@RequestMapping(value = { "properties" })
	@ResponseBody
	public ModelAndView properties(HttpServletRequest request, String req) {
		ResultEntity result = collectionService.properties(req);
		Map<String, Object> model = new HashMap<>();
		if(result.getState()){
			model = (Map<String, Object>) result.getResult();
		}
		return new ModelAndView("/page/database/collection-manage", model);
	}

	
	@RequestMapping(value = { "collectionData" })
	@ResponseBody
	public ResultEntity collectionData(HttpServletRequest request, String req) {
		return collectionService.collectionData(req);
	}
	
	@RequestMapping(value = { "collectionIndex" })
	@ResponseBody
	public ResultEntity collectionIndex(HttpServletRequest request, String req) {
		return collectionService.collectionIndex(req);
	}
	
	@RequestMapping(value = { "deleteDocument" })
	@ResponseBody
	public ResultEntity deleteDocument(HttpServletRequest request, String req) {
		return collectionService.deleteDocument(req);
	}
	
	@RequestMapping(value = { "addDocument" })
	@ResponseBody
	public ResultEntity addDocument(HttpServletRequest request, String req) {
		return collectionService.addDocument(req);
	}
	
	@RequestMapping(value = { "updateDocument" })
	@ResponseBody
	public ResultEntity updateDocument(HttpServletRequest request, String req) {
		return collectionService.updateDocument(req);
	}
	
	/** 分页查询 */
	@RequestMapping(value = { "addcollection" })
	@ResponseBody
	public ModelAndView addcollection(HttpServletRequest request, String req) {
		ResultEntity result = collectionService.properties(req);
		Map<String, Object> model = new HashMap<>();
		if(result.getState()){
			model = (Map<String, Object>) result.getResult();
		}
		return new ModelAndView("/page/database/add-collection", model);
	}
	
	@RequestMapping(value = { "addcollectionAction" })
	@ResponseBody
	public ResultEntity addcollectionAction(HttpServletRequest request, String req) {
		return collectionService.addcollectionAction(req);
	}
}
