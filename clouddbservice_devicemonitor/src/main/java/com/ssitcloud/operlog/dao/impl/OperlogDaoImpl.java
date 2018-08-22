package com.ssitcloud.operlog.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.system.MongoDBImpl;
import com.ssitcloud.operlog.dao.OperlogDao;

@Repository
public class OperlogDaoImpl extends MongoDBImpl implements OperlogDao {

}
