package com.ketu.test.junit;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.junit.Test;

import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.operlog.service.FinanceLogService;
import com.ssitcloud.operlog.service.LoanLogService;

public class OperLogTest extends BasicTestConfig {
	@Resource
	private LoanLogService loanLogService;
	@Resource
	private FinanceLogService financeLogService;

	@Test
	public void test() {

		long time = System.currentTimeMillis();
		testCountLoanlog();
		// testFinanceLog();
		// testJsonUtil();
		System.out.println(System.currentTimeMillis() - time);

	}

	@SuppressWarnings("unchecked")
	public void testJsonUtil() {
		String jsonString1 = "{\"cardNo\":\"123\",\"countType\":\"1\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"params\":[\"'I'\"],\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"]}";
		System.out.println(jsonString1);
		Map<String, Object> m = JsonUtils.fromJson(jsonString1, Map.class);
		for (Entry<String, Object> e : m.entrySet()) {
			System.out.println(e.getKey() + ":" + e.getValue());
		}
	}

	public void testFinanceLog() {
		String whereInfo = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"startTime\":\"20160328151900\",\"endTime\":\"20160328151930\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"]}";
		String s = financeLogService.queryFinance(whereInfo);
		System.out.println(s);
	}

	public void testCountLoanlog() {
		String byClass22 = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"countType\":\"1\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"]}";
		String byTime = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"countType\":\"2\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"]}";
		String BY_RESULT = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"countType\":\"3\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"],\"operresult\":[\"'1'\",\"'0'\"]}";
		String BY_AUTHTYPE = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"countType\":\"4\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"],\"operresult\":[\"'1'\",\"'0'\"],\"cirType\":[\"'10002'\"]}";
		String BY_READER_AGE = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"countType\":\"5\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"],\"operresult\":[\"'1'\",\"'0'\"]}";
		String BY_READER_GENDER = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"countType\":\"3\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"],\"operresult\":[\"'1'\",\"'0'\"]}";
		String BY_RER_LOC = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"countType\":\"3\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"],\"operresult\":[\"'1'\",\"'0'\"]}";
		String BY_CIR_TYPE = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"countType\":\"3\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"],\"operresult\":[\"'1'\",\"'0'\"]}";
		String BY_MEDIA_TYPE = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"countType\":\"3\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"],\"operresult\":[\"'1'\",\"'0'\"]}";
		String BY_DEVICE = "{\"dbNames\":[\"device_state_template\",\"device_state_template11\"],\"countType\":\"3\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"'1'\",\"'2'\",\"'3'\"],\"operresult\":[\"'1'\",\"'0'\"]}";

		String s = loanLogService.countLoanLog(byClass22);
		System.out.println(s);
	}
}
