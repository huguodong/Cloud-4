package com.ssitcloud.view.database.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface MysqlTableService {
	ResultEntity properties(String req);
	ResultEntity tableField(String req);
	ResultEntity tableIndex(String req);
	ResultEntity tableKey(String req);
	ResultEntity updateField(String req);
	ResultEntity updateIndex(String req);
	ResultEntity updateconstraint(String req);
	
	ResultEntity deleteField(String req);
	ResultEntity deleteIndex(String req);
	ResultEntity deleteConstraint(String req);
	
	ResultEntity addField(String req) ;
	ResultEntity addIndex(String req) ;
	ResultEntity addConstraint(String req) ;
	ResultEntity addtabAction(String req) ;
	ResultEntity excuteSql(String req) ;
	ResultEntity serverInfo(String req);
}
