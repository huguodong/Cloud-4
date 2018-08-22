package com.ssit.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.p6spy.engine.spy.appender.StdoutLogger;

public class P6SpyLogger extends StdoutLogger {
	private static Logger logger =LoggerFactory.getLogger(P6SpyLogger.class);

	public void logText(String text) {
			// 匹配到最后一个|作为分隔符
			String[] arrString = text.split("\\|(?![^\\|]*\\|)");
			if (arrString.length > 1) {
				logger.info(format(arrString[1]));
			} else {
				logger.info(text);
			}
			arrString = null;
		
	}

	private String format(String sql) {
		return sql.replaceAll(", ", ",").replaceAll("\\n", " ").replaceAll("\\r", " ").replaceAll("\\s+", " ");
	}

}
