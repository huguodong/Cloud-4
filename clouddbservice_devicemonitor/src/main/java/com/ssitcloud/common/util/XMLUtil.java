package com.ssitcloud.common.util;

import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLUtil {
	private static final Logger LOG = LoggerFactory.getLogger("XMLUtil");

	public static String getNOSQL(Configuration xmlconfig, String nsid,
			Map<String, Object> params) {
		MappedStatement ms = xmlconfig.getMappedStatement(nsid);
		if (ms != null) {
			BoundSql bsql = ms.getBoundSql(params);
			if (bsql != null) {
				LOG.info(bsql.getSql());
				return bsql.getSql();
			}
		}
		return null;
	}
}
