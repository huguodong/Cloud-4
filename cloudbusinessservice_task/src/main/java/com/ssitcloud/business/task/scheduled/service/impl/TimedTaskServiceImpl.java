package com.ssitcloud.business.task.scheduled.service.impl;

import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.task.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.task.common.util.HttpClientUtil;
import com.ssitcloud.business.task.common.util.JsonUtils;
import com.ssitcloud.business.task.common.util.ResourcesUtil;
import com.ssitcloud.business.task.common.util.XMLUtils;
import com.ssitcloud.business.task.scheduled.service.TimedTaskService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;


@Service
public class TimedTaskServiceImpl extends BasicServiceImpl implements TimedTaskService {
	//定时任务
	private static final String URL_addTimedTask = "addTimedTask";
	private static final String URL_delTimedTask = "deleteTimedTask";
	private static final String URL_updTimedTask = "updateTimedTask";
	public static final String URL_queryOneTimedTask="selectOneTimedTask";
	public static final String URL_queryTimedTaskByparam="selectTimedTasks";
	//任务触发器
	public static final String URL_QUERYTRIGGERS="selectTimedTaskTriggers";
	public static final String URL_DELETETRIGGER="deleteTimedTaskTrigger";
	//设备相关
	public static final String URL_QUERYDEVICES="selectDevice";
	//图书馆相关
	private static final String SEL_LIBRARY_BY_IDX_OR_ID = "selLibraryByIdxOrId";

	@Override
	public ResultEntity addTimedTask(String req) {
		return postURL(URL_addTimedTask, req);
	}

	@Override
	public ResultEntity delTimedTask(String req) {
		return postURL(URL_delTimedTask, req);
	}

	@Override
	public ResultEntity updTimedTask(String req) {
		return postURL(URL_updTimedTask, req);
	}

	@Override
	public ResultEntity queryOneTimedTask(String req) {
		return postURL(URL_queryOneTimedTask, req);
	}
	
	@Override
	public ResultEntity queryTimedTaskByparam(String req) {
		return postURL(URL_queryTimedTaskByparam, req);
	}

	@Override
	public ResultEntity deleteTimedTaskTrigger(String req) {
		return postURL(URL_DELETETRIGGER, req);
	}

	@Override
	public ResultEntity selectTimedTaskTriggers(String req) {
		return postURL(URL_QUERYTRIGGERS, req);
	}

	@Override
	public ResultEntity selectDevices(Map<String, String> param) {
		ResultEntity result = new ResultEntity();
		if(requestURL==null)//在线程里重新调用时，得不到RequestURLListEntity的实例，故重新实例化一次 modify by huanghuang 20170227
			requestURL = new RequestURLListEntity(XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml")));
		String reqURL=requestURL.getRequestURL(URL_QUERYDEVICES);
		String res=HttpClientUtil.doPost(reqURL, param, Consts.UTF_8.toString());
		result = JsonUtils.fromJson(res, ResultEntity.class);	
		return result ;
	}

	@Override
	public ResultEntity selLibraryByIdxOrId(Map<String, String> param) {
		ResultEntity result = new ResultEntity();
		if(requestURL==null)//在线程里重新调用时，得不到RequestURLListEntity的实例，故重新实例化一次 modify by huanghuang 20170227
			requestURL = new RequestURLListEntity(XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml")));
		String reqURL=requestURL.getRequestURL(SEL_LIBRARY_BY_IDX_OR_ID);
		String res=HttpClientUtil.doPost(reqURL, param, Consts.UTF_8.toString());
		result = JsonUtils.fromJson(res, ResultEntity.class);	
		return result ;
	}


}
