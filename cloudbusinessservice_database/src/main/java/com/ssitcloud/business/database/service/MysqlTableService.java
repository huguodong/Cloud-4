package com.ssitcloud.business.database.service;

import java.sql.SQLException;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.database.entity.MysqlTable;
import com.ssitcloud.database.entity.Server;


public interface MysqlTableService {
	MysqlTable tableField(String req);
	MysqlTable tableIndex(String req);
	MysqlTable tableKey(String req);
	Boolean updateField(String req) throws SQLException ;
	Boolean updateIndex(String req) throws SQLException ;
	Boolean updateconstraint(String req) throws SQLException ;
	
	Boolean deleteField(String req) throws SQLException;
	Boolean deleteIndex(String req) throws SQLException;
	Boolean deleteConstraint(String req) throws SQLException;
	
	Boolean addField(String req) throws SQLException ;
	Boolean addIndex(String req) throws SQLException ;
	Boolean addConstraint(String req) throws SQLException ;
	
	Boolean addtabAction(String req) throws SQLException ;
	ResultEntity excuteSql(String req) throws SQLException ;
	Server getMysqlServer(Integer server_id);
}
