package com.ssitcloud.operlog.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.system.MongoDBImpl;
import com.ssitcloud.operlog.dao.LoanLogDao;

@Repository
public class LoanLogDaoImpl extends MongoDBImpl implements LoanLogDao {

}
