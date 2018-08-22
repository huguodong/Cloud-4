package com.ssitcloud.business.task.scheduled.service.impl;

import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.task.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.task.common.util.HttpClientUtil;
import com.ssitcloud.business.task.common.util.ResourcesUtil;
import com.ssitcloud.business.task.common.util.XMLUtils;
import com.ssitcloud.business.task.scheduled.service.StatisticsService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class StatisticsServiceImpl extends BasicServiceImpl implements StatisticsService {
	//流通统计相关
	private static final String URL_ADDDAYLOANLOG = "addLoanLogDay";
	private static final String URL_ADDWEEKLOANLOG = "addLoanLogWeek";
	private static final String URL_ADDMONTHLOANLOG = "addLoanLogMonth";
	private static final String URL_ADDYEARLOANLOG = "addLoanLogYear";
	//财经统计相关
	private static final String URL_ADDDAYFINLOG = "addFinLogDay";
	private static final String URL_ADDWEEKFINLOG = "addFinLogWeek";
	private static final String URL_ADDMONTHFINLOG = "addFinLogMonth";
	private static final String URL_ADDYEARFINLOG = "addFinLogYear";
	//办证统计相关
	private static final String URL_ADDDAYCARDLOG = "addNewCardDayData";
	private static final String URL_ADDWEEKCARDLOG = "addNewCardWeekData";
	private static final String URL_ADDMONTHCARDLOG = "addNewCardMonthData";
	private static final String URL_ADDYEARCARDLOG = "addNewCardYearData";
	//统计子类型
	private static final String URL_BOOKLOCATION = "selectBookLocations";
	private static final String URL_BOOKCIRTYPE = "selectBookCirtypes";
	private static final String URL_BOOKMEDIATYPE = "selectBookMediatypes";
	private static final String URL_STATICSTYPE = "selectStaticsType";
	private static final String URL_SELECTREADERTYPE = "selectReadertype";
	@Override
	public ResultEntity addLoanLogDay(String req) {
		return postURL(URL_ADDDAYLOANLOG, req);
	}

	@Override
	public ResultEntity addLoanLogWeek(String req) {
		return postURL(URL_ADDWEEKLOANLOG, req);
	}

	@Override
	public ResultEntity addLoanLogMonth(String req) {
		return postURL(URL_ADDMONTHLOANLOG, req);
	}

	@Override
	public ResultEntity addLoanLogYear(String req) {
		return postURL(URL_ADDYEARLOANLOG, req);
	}

	@Override
	public ResultEntity addFinLogDay(String req) {
		return postURL(URL_ADDDAYFINLOG, req);
	}

	@Override
	public ResultEntity addFinLogWeek(String req) {
		return postURL(URL_ADDWEEKFINLOG, req);
	}

	@Override
	public ResultEntity addFinLogMonth(String req) {
		return postURL(URL_ADDMONTHFINLOG, req);
	}

	@Override
	public ResultEntity addFinLogYear(String req) {
		return postURL(URL_ADDYEARFINLOG, req);
	}

	@Override
	public ResultEntity addNewCardLogDay(String req) {
		return postURL(URL_ADDDAYCARDLOG, req);
	}

	@Override
	public ResultEntity addNewCardLogWeek(String req) {
		return postURL(URL_ADDWEEKCARDLOG, req);
	}

	@Override
	public ResultEntity addNewCardLogMonth(String req) {
		return postURL(URL_ADDMONTHCARDLOG, req);
	}

	@Override
	public ResultEntity addNewCardLogYear(String req) {
		return postURL(URL_ADDYEARCARDLOG, req);
	}

	@Override
	public ResultEntity selectBookLocations(String req) {
		return postURL(URL_BOOKLOCATION, req);
	}

	@Override
	public ResultEntity selectBookCirtypes(String req) {
		return postURL(URL_BOOKCIRTYPE, req);
	}

	@Override
	public ResultEntity selectBookMediatypes(String req) {
		return postURL(URL_BOOKMEDIATYPE, req);
	}

	@Override
	public ResultEntity selectStaticsType(String req) {
		return postURL(URL_STATICSTYPE, req);
	}
	
	@Override
	public ResultEntity selectReadertype(String req) {
		return postURL(URL_SELECTREADERTYPE, req);
	}

}
