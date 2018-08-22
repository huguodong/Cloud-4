package com.ssitcloud.view.database.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.database.service.MysqlTableService;

@Service
public class MysqlTableServiceImpl extends BasicServiceImpl implements MysqlTableService {

	
	private static final String URL_properties="properties";
	private static final String URL_tableField="tableField";
	private static final String URL_tableIndex="tableIndex";
	private static final String URL_tableKey="tableKey";
	
	private static final String URL_updateField="updateField";
	private static final String URL_updateIndex="updateIndex";
	private static final String URL_updateconstraint="updateconstraint";
	
	private static final String URL_deleteField="deleteField";
	private static final String URL_deleteIndex="deleteIndex";
	private static final String URL_deleteConstraint="deleteConstraint";
	
	private static final String URL_addField="addField";
	private static final String URL_addIndex="addIndex";
	private static final String URL_addConstraint="addConstraint";
	
	private static final String URL_addtabAction = "addtabAction";
	private static final String URL_excuteSql = "excuteSql";
	private static final String URL_serverInfo = "serverInfo";
	
	@Override
	public ResultEntity properties(String req) {
		return postUrl(URL_properties, req);
	}

	@Override
	public ResultEntity tableField(String req) {
		return postUrl(URL_tableField, req);
	}
	
	@Override
	public ResultEntity tableIndex(String req) {
		return postUrl(URL_tableIndex, req);
	}
	
	@Override
	public ResultEntity tableKey(String req) {
		return postUrl(URL_tableKey, req);
	}
	
	
	@Override
	public ResultEntity updateField(String req){
		return postUrl(URL_updateField, req);
	}
	
	
	@Override
	public ResultEntity updateIndex(String req){
		return postUrl(URL_updateIndex, req);
	}
	
	@Override
	public ResultEntity updateconstraint(String req){
		return postUrl(URL_updateconstraint, req);
	}
	
	
	@Override
	public ResultEntity deleteField(String req){
		return postUrl(URL_deleteField, req);
	}
	
	@Override
	public ResultEntity deleteIndex(String req){
		return postUrl(URL_deleteIndex, req);
	}
	
	
	@Override
	public ResultEntity deleteConstraint(String req){
		return postUrl(URL_deleteConstraint, req);
	}
	
	
	@Override
	public ResultEntity addField(String req) {
		return postUrl(URL_addField, req);
	}
	
	
	
	@Override
	public ResultEntity addIndex(String req) {
		return postUrl(URL_addIndex, req);
	}
	
	
	
	@Override
	public ResultEntity addConstraint(String req){
		return postUrl(URL_addConstraint, req);
	}
	
	@Override
	public ResultEntity addtabAction(String req){
		return postUrl(URL_addtabAction, req);
	}
	
	@Override
	public ResultEntity excuteSql(String req){
		return postUrl(URL_excuteSql, req);
	}
	
	@Override
	public ResultEntity serverInfo(String req){
		return postUrl(URL_serverInfo, req);
	}
}
