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
import com.ssitcloud.view.database.service.MysqlTableService;

@Controller
@RequestMapping(value = { "table" })
public class MysqlTableViewController extends BasicController {

	@Resource
	private MysqlTableService tableService;

	/** 分页查询 */
	@RequestMapping(value = { "properties" })
	@ResponseBody
	public ModelAndView properties(HttpServletRequest request, String req) {
		ResultEntity result = tableService.properties(req);
		Map<String, Object> model = new HashMap<>();
		if(result.getState()){
			model = (Map<String, Object>) result.getResult();
		}
		return new ModelAndView("/page/database/table-manage", model);
	}

	
	@RequestMapping(value = { "tableField" })
	@ResponseBody
	public ResultEntity tableField(HttpServletRequest request, String req) {
		return tableService.tableField(req);
	}
	
	@RequestMapping(value = { "tableIndex" })
	@ResponseBody
	public ResultEntity tableIndex(HttpServletRequest request, String req) {
		return tableService.tableIndex(req);
	}
	
	@RequestMapping(value = { "tableKey" })
	@ResponseBody
	public ResultEntity tableKey(HttpServletRequest request, String req) {
		ResultEntity result = tableService.tableKey(req);
		return result;
	}
	
	@RequestMapping(value = { "updateField" })
	@ResponseBody
	public ResultEntity updateField(HttpServletRequest request, String req) {
		return tableService.updateField(req);
	}
	
	@RequestMapping(value = { "updateIndex" })
	@ResponseBody
	public ResultEntity updateIndex(HttpServletRequest request, String req) {
		return tableService.updateIndex(req);
	}
	
	@RequestMapping(value = { "updateconstraint" })
	@ResponseBody
	public ResultEntity updateconstraint(HttpServletRequest request, String req) {
		return tableService.updateconstraint(req);
	}
	
	@RequestMapping(value = { "deleteField" })
	@ResponseBody
	public ResultEntity deleteField(HttpServletRequest request, String req) {
		return tableService.deleteField(req);
	}
	
	@RequestMapping(value = { "deleteIndex" })
	@ResponseBody
	public ResultEntity deleteIndex(HttpServletRequest request, String req) {
		return tableService.deleteIndex(req);
	}
	
	@RequestMapping(value = { "deleteConstraint" })
	@ResponseBody
	public ResultEntity deleteConstraint(HttpServletRequest request, String req) {
		return tableService.deleteConstraint(req);
	}
	
	@RequestMapping(value = { "addField" })
	@ResponseBody
	public ResultEntity addField(HttpServletRequest request, String req) {
		return tableService.addField(req);
	}
	
	@RequestMapping(value = { "addIndex" })
	@ResponseBody
	public ResultEntity addIndex(HttpServletRequest request, String req) {
		return tableService.addIndex(req);
	}
	
	@RequestMapping(value = { "addConstraint" })
	@ResponseBody
	public ResultEntity addConstraint(HttpServletRequest request, String req) {
		return tableService.addConstraint(req);
	}
	
	/** 分页查询 */
	@RequestMapping(value = { "addtab" })
	@ResponseBody
	public ModelAndView addtab(HttpServletRequest request, String req) {
		ResultEntity result = tableService.properties(req);
		Map<String, Object> model = new HashMap<>();
		if(result.getState()){
			model = (Map<String, Object>) result.getResult();
		}
		return new ModelAndView("/page/database/add-table", model);
	}

	@RequestMapping(value = { "addtabAction" })
	@ResponseBody
	public ResultEntity addtabAction(HttpServletRequest request, String req) {
		return tableService.addtabAction(req);
	}
	
	@RequestMapping(value = { "excuteSql" })
	@ResponseBody
	public ResultEntity excuteSql(HttpServletRequest request, String req) {
		ResultEntity result =tableService.excuteSql(req);
		return result;
	}
	
	@RequestMapping(value = { "serverInfo" })
	@ResponseBody
	public ResultEntity serverInfo(HttpServletRequest request, String req) {
		return tableService.serverInfo(req);
	}
	
}
