package com.ssitcloud.business.task.common.system;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ssitcloud.business.task.common.util.ResourcesUtil;
import com.ssitcloud.business.task.common.util.XMLUtils;
import com.ssitcloud.business.task.scheduled.service.StatisticsService;
import com.ssitcloud.business.task.scheduled.service.TimedTaskService;
import com.ssitcloud.business.task.scheduled.service.impl.StatisticsServiceImpl;
import com.ssitcloud.business.task.scheduled.service.impl.TimedTaskServiceImpl;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

@Configuration
public class AppConfig {

	/**
	 * <p>
	 * 随着spring启动，将RequestURL.xml中的Url信息装入RequestURLListEntity中
	 * </p>
	 * 
	 * 使用方法 <br/>
	 * 
	 * @Resource(name="requestURL")<br/> RequestURLListEntity
	 *                                   requestURLListEntity;<br/>
	 * <br/>
	 *                                   获取requestURLListEntity对象，
	 *                                   再通过ID属性获取对应的requestURL<br/>
	 * 
	 * @methodName: requestURL
	 * @return
	 * @throws Exception
	 * @returnType: RequestURLListEntity
	 * @author: liuBh
	 */
	@Bean(name = "requestURL")
	public RequestURLListEntity requestURL() throws Exception {
		Map<String, String> map = XMLUtils.parseAll(ResourcesUtil
				.getInputStream("RequestURL.xml"));
		return new RequestURLListEntity(map);
	}

	@Bean(name = "taskList")
	public JSONArray taskList() {
		String req = "{}";
		TimedTaskService timedTaskService = new TimedTaskServiceImpl();
		timedTaskService.deleteTimedTaskTrigger(req);// 应用刚启动时，将定时任务触发器里的触发任务，清空。
		ResultEntity result = timedTaskService.queryTimedTaskByparam(req);
		Object obj = result.getResult();
		return JSONArray.fromObject(obj);
	}

	/**
	 * 获取统计子类型参数 author huanghuang 2017年3月8日 上午9:52:19
	 * 
	 * @return
	 */
	@Bean(name = "logStaticSourceMap")
	public Map<String, JSONArray> logStaticSourceMap() {
		Map<String, JSONArray> staticResource = new HashMap<String, JSONArray>();
		StatisticsService statisticsService = new StatisticsServiceImpl();
		ResultEntity rEntity = statisticsService.selectStaticsType("{}");
		JSONArray bookTypeJsonArr = new JSONArray();
		JSONArray resultJsonArr = new JSONArray();
		JSONArray ageJsonArr = new JSONArray();
		JSONArray payTypeJsonArr = new JSONArray();
		JSONArray authJsonArr = new JSONArray();
		JSONArray moneyJsonArr = new JSONArray();
		JSONArray sexJsonArr = new JSONArray();
		JSONArray timeJsonArr = new JSONArray();
		if (rEntity.getState()) {
			JSONArray jsonArray = JSONArray.fromObject(rEntity.getResult());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jObject = jsonArray.getJSONObject(i);
				// 1中图法 2操作结果 3年龄段 4支付类型 5认证类型 6存款用途 7性别 8时间点
				switch (jObject.getString("data_type")) {
				case "1":
					bookTypeJsonArr.add("{\"code\":\""
							+ jObject.getString("data_key") + "\"}");
					break;
				case "2":
					resultJsonArr.add("{\"code\":\""
							+ jObject.getString("data_key") + "\"}");
					break;
				case "3":
					ageJsonArr.add("{\"code\":\""
							+ jObject.getString("data_key") + "\"}");
					break;
				case "4":
					payTypeJsonArr.add("{\"code\":\""
							+ jObject.getString("data_key") + "\"}");
					break;
				case "5":
					authJsonArr.add("{\"code\":\""
							+ jObject.getString("data_key") + "\"}");
					break;
				case "6":
					moneyJsonArr.add("{\"code\":\""
							+ jObject.getString("data_key") + "\"}");
					break;
				case "7":
					sexJsonArr.add("{\"code\":\""
							+ jObject.getString("data_key") + "\"}");
					break;
				case "8":
					timeJsonArr.add("{\"code\":\""
							+ jObject.getString("data_key") + "\"}");
					break;
				default:
					break;
				}
			}
			staticResource.put("bookTypeJsonArr", bookTypeJsonArr);
			staticResource.put("resultJsonArr", resultJsonArr);
			staticResource.put("ageJsonArr", ageJsonArr);
			staticResource.put("payTypeJsonArr", payTypeJsonArr);
			staticResource.put("authJsonArr", authJsonArr);
			staticResource.put("moneyJsonArr", moneyJsonArr);
			staticResource.put("sexJsonArr", sexJsonArr);
			staticResource.put("timeJsonArr", timeJsonArr);
		}

		return staticResource;
	}

	/*
	 * @Bean(name = "classPath") public String classPath() throws Exception {
	 * return new ClassPathResource("config.properties").getPath(); }
	 */

}
