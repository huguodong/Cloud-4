package com.ssitcloud.operlog.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.system.MongoDBImpl;
import com.ssitcloud.operlog.dao.LogisticsDao;

@Repository
public class LogisticsDaoImpl extends MongoDBImpl implements LogisticsDao {

}
