package com.ssitcloud.operlog.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.system.MongoDBImpl;
import com.ssitcloud.operlog.dao.BookrackLogDao;

@Repository
public class BookrackLogDaoImpl extends MongoDBImpl implements BookrackLogDao {

}
