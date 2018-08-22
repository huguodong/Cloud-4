package com.ssitcloud.business.database.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.fabric.xmlrpc.base.Array;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.database.service.MysqlTableService;
import com.ssitcloud.business.database.util.ServerUtil;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.database.entity.MysqlDatabase;
import com.ssitcloud.database.entity.MysqlField;
import com.ssitcloud.database.entity.MysqlTable;
import com.ssitcloud.database.entity.Server;

@Controller
@RequestMapping(value = { "table" })
public class MysqlTableViewController{

	@Resource
	private MysqlTableService tableService;

	/** 分页查询 */
	@RequestMapping(value = { "properties" })
	@ResponseBody
	public ResultEntity properties(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		MysqlTable table = JsonUtils.fromJson(req, MysqlTable.class);
		Server server = tableService.getMysqlServer(table.getServer_id());
		Map<String, Object> model = new HashMap<>();
		model.put("table", table);
		model.put("server", server);
		result.setValue(true, "", "", model);
		return result;
	}

	
	@RequestMapping(value = { "tableField" })
	@ResponseBody
	public ResultEntity tableField(HttpServletRequest request, String req) {
		MysqlTable table = tableService.tableField(req);
		ResultEntity result = new ResultEntity();
		if (table != null) {
			result.setValue(true, "success", "", table);
		} else {
			result.setState(false);
		}
		return result;
	}
	
	@RequestMapping(value = { "tableIndex" })
	@ResponseBody
	public ResultEntity tableIndex(HttpServletRequest request, String req) {
		MysqlTable table = tableService.tableIndex(req);
		ResultEntity result = new ResultEntity();
		if (table != null) {
			result.setValue(true, "success", "", table);
		} else {
			result.setState(false);
		}
		return result;
	}
	
	@RequestMapping(value = { "tableKey" })
	@ResponseBody
	public ResultEntity tableKey(HttpServletRequest request, String req) {
		MysqlTable table = tableService.tableKey(req);
		ResultEntity result = new ResultEntity();
		if (table != null) {
			result.setValue(true, "success", "", table);
		} else {
			result.setState(false);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateField" })
	@ResponseBody
	public ResultEntity updateField(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(tableService.updateField(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateIndex" })
	@ResponseBody
	public ResultEntity updateIndex(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(tableService.updateIndex(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateconstraint" })
	@ResponseBody
	public ResultEntity updateconstraint(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(tableService.updateconstraint(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getLocalizedMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteField" })
	@ResponseBody
	public ResultEntity deleteField(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(tableService.deleteField(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteIndex" })
	@ResponseBody
	public ResultEntity deleteIndex(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(tableService.deleteIndex(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteConstraint" })
	@ResponseBody
	public ResultEntity deleteConstraint(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(tableService.deleteConstraint(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "addField" })
	@ResponseBody
	public ResultEntity addField(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(tableService.addField(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "addIndex" })
	@ResponseBody
	public ResultEntity addIndex(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(tableService.addIndex(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "addConstraint" })
	@ResponseBody
	public ResultEntity addConstraint(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(tableService.addConstraint(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "addtabAction" })
	@ResponseBody
	public ResultEntity addtabAction(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(tableService.addtabAction(req)){
				result.setValue(true, "", "",null);
			}else{
				result.setValue(false, "", "",null);
			}
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "excuteSql" })
	@ResponseBody
	public ResultEntity excuteSql(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = tableService.excuteSql(req);
		} catch (SQLException e) {
			result.setValue(false, e.getMessage(), "",null);
		}
		return result;
	}
	
	@RequestMapping(value = { "serverInfo" })
	@ResponseBody
	public ResultEntity serverInfo(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		List<String> dbNames = new ArrayList<>();
		List<String> tabNames = new ArrayList<>();
		List<String> fieldNames = new ArrayList<>();
		MysqlTable table = JsonUtils.fromJson(req, MysqlTable.class);
		Server server = tableService.getMysqlServer(table.getServer_id());
		List<MysqlDatabase> dbs = server.getDbs();
		Map<String,Object> map = new HashMap<>();
		for(MysqlDatabase db : dbs){
			if(!StringUtils.isEmpty(table.getDatabaseName())){
				if(db.getName().equals(table.getDatabaseName())){
					List<MysqlTable> tabs = db.getTables();
					for(MysqlTable tab : tabs){
						tabNames.add(tab.getName());
						if(!StringUtils.isEmpty(table.getName())){
							if(tab.getName().equals(table.getName())){
								MysqlTable tb= tableService.tableField(JsonUtils.toJson(table));
								List<MysqlField> fields = tb.getFields();
								for(MysqlField field : fields){
									fieldNames.add(field.getName());
								}
							}
						}
					}
				}else{
					continue;
				}
			}else{
				dbNames.add(db.getName());
			}
		}
		map.put("dbNames", dbNames);
		map.put("tabNames", tabNames);
		map.put("fieldNames", fieldNames);
		result.setValue(true, "", "", map);
		return result;
	}
}
