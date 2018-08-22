package com.ssitcloud.business.statistics.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.service.StatisticsService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.RegionEntity;

@Service
public class StatisticsServiceImpl extends BasicServiceImpl implements
		StatisticsService {

	private static final String URL_STATICSMAINTYPE = "queryStatisticsMaintypeList";
	private static final String URL_STATICSTYPE = "selectStaticsType";
	private static final String URL_RELTYPE = "queryReltype";
	private static final String URL_RELTYPELIST = "queryReltypeList";
	private static final String URL_SELREGIONBYDEVICEIDX = "selRegionByDeviceidx";

	@Override
	public ResultEntity selectStaticsType(String req) {
		return postURL(URL_STATICSTYPE, req);
	}

	@Override
	public ResultEntity queryStatisticsMaintypeList(String req) {
		return postURL(URL_STATICSMAINTYPE, req);
	}

	@Override
	public ResultEntity queryReltype(String req) {
		return postURL(URL_RELTYPE, req);
	}

	@Override
	public ResultEntity queryReltypeList(String req) {
		return postURL(URL_RELTYPELIST, req);
	}

	@Override
	public String retAreaCode(Integer device_idx) {
		String result = "";
		JSONObject reqJson = new JSONObject();
		reqJson.put("deviceIdx", device_idx);
		ResultEntity region = postURL(URL_SELREGIONBYDEVICEIDX, reqJson.toString());
		if(region!=null&&region.getState()){
			Map<String,String> r = (LinkedHashMap) region.getResult();
			if(r!=null){
				result = r.get("regi_code");
			}
		}
		return result;
	}

}
