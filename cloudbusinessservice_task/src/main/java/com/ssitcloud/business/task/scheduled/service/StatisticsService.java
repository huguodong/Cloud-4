package com.ssitcloud.business.task.scheduled.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface StatisticsService {
	//流通统计相关
	ResultEntity addLoanLogDay(String req);
	ResultEntity addLoanLogWeek(String req);
	ResultEntity addLoanLogMonth(String req);
	ResultEntity addLoanLogYear(String req);
	//财经统计相关
	ResultEntity addFinLogDay(String req);
	ResultEntity addFinLogWeek(String req);
	ResultEntity addFinLogMonth(String req);
	ResultEntity addFinLogYear(String req);
	//办证统计相关
	ResultEntity addNewCardLogDay(String req);
	ResultEntity addNewCardLogWeek(String req);
	ResultEntity addNewCardLogMonth(String req);
	ResultEntity addNewCardLogYear(String req);
	//统计子类型
	ResultEntity selectBookLocations(String req);
	ResultEntity selectBookCirtypes(String req);
	ResultEntity selectBookMediatypes(String req);
	ResultEntity selectStaticsType(String req);
	ResultEntity selectReadertype(String req);
	
}
