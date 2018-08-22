package com.ssitcloud.business.task.scheduled.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ssitcloud.business.task.common.util.DateUtil;
import com.ssitcloud.business.task.common.util.JsonUtils;
import com.ssitcloud.business.task.scheduled.entity.TimedTaskEntity;
import com.ssitcloud.business.task.scheduled.service.StatisticsService;
import com.ssitcloud.business.task.scheduled.service.TimedTaskService;
import com.ssitcloud.business.task.scheduled.service.impl.StatisticsServiceImpl;
import com.ssitcloud.business.task.scheduled.service.impl.TimedTaskServiceImpl;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * author huanghuang 2017年2月27日 下午5:42:37
 */
@Component("TriggerTask")
public class TriggerTask {
	@Resource
	private TimedTaskService timedTaskService;
	@Resource
	private StatisticsService statisticsService;
	@Resource(name = "taskList")
	private JSONArray jsonArray;// 写在内存里的定时任务(应用重启时和每天0点重新写入)

	@Resource(name = "logStaticSourceMap")
	private Map<String, JSONArray> logStaticSourceMap;// 统计相关的静态资源(应用重启时和每天0点重新写入)

	final int TASK_QUEUE_SIZE = 100;// 阻塞队列大小
	// 失败统计任务的阻塞队列
	BlockingQueue<JSONObject> queue = new ArrayBlockingQueue<JSONObject>(
			TASK_QUEUE_SIZE);

	/**
	 * 获取任务列表，每天00:00定时触发 author huanghuang 2017年2月28日 上午10:14:40
	 */
	public void gainTask() {
		String req = "{}";
		TimedTaskService timedTaskService = new TimedTaskServiceImpl();
		timedTaskService.deleteTimedTaskTrigger(req);// 启动时，将定时任务触发器里的触发任务，清空。
		jsonArray = currTaskList();
		logStaticSourceMap = logStaticSourceMap();
	}

	/**
	 * 每6个小时触发一次 author huanghuang 2017年3月7日 下午2:33:21
	 */
	public void cleanMap() {
		libMap.clear();
		devMap.clear();
		dynamicMap.clear();
	}

	/**
	 * 获取任务列表 author huanghuang 2017年2月28日 上午11:30:55
	 * 
	 * @return
	 */
	public JSONArray currTaskList() {
		String req = "{}";
		ResultEntity result = timedTaskService.queryTimedTaskByparam(req);
		Object obj = result.getResult();
		return JSONArray.fromObject(obj);
	}

	/**
	 * 获取统计子类型参数 author huanghuang 2017年3月8日 上午9:52:19
	 * 
	 * @return
	 */
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

	/**
	 * 比较时间，时间符合要求，即查询mongodb里的数据 author huanghuang 2017年2月28日 上午10:15:05
	 */
	public void compareTime() {
		long current = System.currentTimeMillis();// 获得系统时间
		System.out.println(DateUtil.toDay(current));
		String req = "{}";
		ResultEntity r = timedTaskService.selectTimedTaskTriggers(req);
		JSONArray triggerJsonArray = JSONArray.fromObject(r.getResult());// 任务触发器的数据
		JSONArray newJsonArray = new JSONArray();// 新的任务集合，拿来处理这一分钟要执行的任务
		if (triggerJsonArray != null) {
			for (int i = 0; i < triggerJsonArray.size(); i++) {
				JSONObject triggerTmp = triggerJsonArray.getJSONObject(i);
				if (jsonArray != null) {
					for (int m = 0; m < jsonArray.size(); m++) {
						JSONObject jTmp = jsonArray.getJSONObject(m);
						if ("D".equals(triggerTmp.get("change_state"))
								&& jTmp.getInt("task_idx") == triggerTmp
										.getInt("data_idx")) {// 任务触发器触发delete操作时
							jsonArray.discard(m);
							break;
						} else if ("U".equals(triggerTmp.get("change_state"))
								&& jTmp.getInt("task_idx") == triggerTmp
										.getInt("data_idx")) {// 任务触发器触发update操作时
							String idx = "{\"task_idx\":\""
									+ triggerTmp.get("data_idx")
									+ "\",\"task_state\":\"1\"}";
							ResultEntity uEntity = timedTaskService
									.queryOneTimedTask(idx);
							JSONObject jsonObj = new JSONObject();
							if (uEntity.getState()) {
								jsonObj = JSONObject.fromObject(uEntity
										.getResult());
							}
							jsonArray.discard(m);
							jsonArray.add(jsonObj);
							break;
						} else if ("I".equals(triggerTmp.get("change_state"))) {// 当前触发器里的数据，不存在内存时，重新写入
							String idx = "{\"task_idx\":\""
									+ triggerTmp.get("data_idx")
									+ "\",\"task_state\":\"1\"}";
							ResultEntity res = timedTaskService
									.queryOneTimedTask(idx);
							if (res.getState()) {
								jsonArray.add(JSONObject.fromObject(res
										.getResult()));
							}
							break;
						}

					}
				}
			}
			timedTaskService.deleteTimedTaskTrigger(req);// 删除对应的任务触发器列表

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				long task_starttime = jsonObject.getLong("task_starttime");
				int task_type = jsonObject.getInt("task_type");
				boolean takeTask = DateUtil.taskTimeEqSystemTime(current,
						task_starttime, task_type)
						&& ("1".equals(jsonObject.getString("task_state")));//任务启用和时间到达触发点加入这一秒要执行的标识
				if (takeTask) {
					newJsonArray.add(jsonObject);
				}
			}
			if (!queue.isEmpty()) {// 失败的队列不为空时，下一分钟重新执行
				newJsonArray.add(queue.poll());
			}
			if (newJsonArray.size() != 0) {
				int dataLen = newJsonArray.size();
				// 创建线程池，最小线程数为2，最大线程数为20，线程池维护线程的空闲时间为1秒,阻塞队列大小为dataLen-20，当线程总数超过dataLen时，剩余的任务将被删除
				ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 20,
						1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
								dataLen - 20 > 0 ? dataLen - 20 : 1),
						new ThreadPoolExecutor.DiscardOldestPolicy());

				for (int j = 0; j < dataLen; j++) {
					final TimedTaskEntity timedTask = JsonUtils.fromJson(
							newJsonArray.get(j).toString(),
							TimedTaskEntity.class);// 将json转为定时任务实体
					// 通过lib_idx获取读者流通类型
					JSONArray readerTypeArray = takeDynamicMap(
							timedTask.getLib_idx(), "readerType");
					// 通过lib_idx获取图书馆藏地
					JSONArray bookLocationArray = takeDynamicMap(
							timedTask.getLib_idx(), "bookLocation");
					// // 通过lib_idx获取图书流通类型
					// JSONArray bookCirArray = takeDynamicMap(
					// timedTask.getLib_idx(), "bookCir");
					// 通过lib_idx获取图书载体
					JSONArray bookMediaArray = takeDynamicMap(
							timedTask.getLib_idx(), "bookMedia");

					final Map<String, JSONArray> dynMap = new HashMap<String, JSONArray>();
					dynMap.put("readerType", readerTypeArray);
					dynMap.put("bookLocation", bookLocationArray);
					// dynMap.put("bookCir", bookCirArray);
					dynMap.put("bookMedia", bookMediaArray);
					// 通过lib_idx获取图书馆名下的所有设备
					final JSONArray deviceArray = takeDev(timedTask
							.getLib_idx());
					// 通过idx得到图书馆
					final JSONObject lib = takeLib(timedTask.getLib_idx());
					threadPool.execute(new Runnable() {
						@Override
						public void run() {
							ReadWrite rw = new ReadWrite(dynMap);// 读写对象，多少个任务多少个并行执行
							if (!deviceArray.isEmpty() && !lib.isEmpty()) {
								for (int d = 0; d < deviceArray.size(); d++) {
									JSONObject device = deviceArray
											.getJSONObject(d);
									// 查询mongodb
									rw.read(timedTask, lib.get("lib_id") + "_"
											+ device.get("device_id"),
											device.getString("device_idx"),
											logStaticSourceMap);
									// 插入clouddbservice_statistics
									rw.write(queue);
								}
							}
						}
					});

				}
				threadPool.shutdown();// 线程池关闭
			}
		}
	}

	private Map<Integer, JSONObject> libMap = new HashMap<Integer, JSONObject>();// 当map里存在图书馆时，则不用请求数据库

	// 图书馆的缓存
	public JSONObject takeLib(Integer key) {
		JSONObject value = null;
		value = libMap.get(key);
		if (value == null) {
			// 通过libidx得到图书馆
			String libidx = "{\"library_idx\":\"" + key + "\"}";
			Map<String, String> param = new HashMap<>();
			param.put("json", libidx);
			ResultEntity libResult = timedTaskService
					.selLibraryByIdxOrId(param);
			if (libResult.getState()) {
				JSONArray libArray = JSONArray
						.fromObject(libResult.getResult());
				value = libArray.getJSONObject(0);
			}
			if (value == null) {
				throw new RuntimeException("=====>通过任务的lib_idx查找不到对应的图书馆");
			}
			libMap.put(key, value);
		}

		return value;
	}

	private Map<Integer, JSONArray> devMap = new HashMap<Integer, JSONArray>();// 当map里存在图书馆设备时，则不用请求数据库

	// 设备的缓存
	public JSONArray takeDev(Integer key) {
		JSONArray value = null;
		value = devMap.get(key);
		if (value == null) {
			// 通过libidx得到所有设备
			String json = "{\"library_idx\":\"" + key + "\"}";
			Map<String, String> map = new HashMap<>();
			map.put("json", json);
			ResultEntity devResult = timedTaskService.selectDevices(map);
			if (devResult.getState()) {
				value = JSONArray.fromObject(devResult.getResult());
			}
			if (value == null) {
				throw new RuntimeException("=====>通过任务的lib_idx查找不到对应的设备");
			}
			devMap.put(key, value);

		}

		return value;
	}

	private Map<String, JSONArray> dynamicMap = new HashMap<String, JSONArray>();// 当map里存在图书馆藏地时，则不用请求数据库

	// 动态的统计子类型参数的缓存
	@SuppressWarnings("unused")
	public JSONArray takeDynamicMap(Integer key, String type) {
		JSONArray value = null;
		JSONArray tmpArr = new JSONArray();
		value = dynamicMap.get(key + type);
		if (value == null) {
			// 通过libidx得到动态的统计子类型参数
			String req = "{\"lib_idx\":\"" + key + "\"}";
			if (statisticsService == null) {
				statisticsService = new StatisticsServiceImpl();
			}
			ResultEntity lResult = null;
			switch (type) {
			case "readerType":// 读者流通类型
				lResult = statisticsService
						.selectReadertype("{\"library_idx\":\"" + key + "\"}");
				if (lResult.getState()) {
					value = JSONArray.fromObject(lResult.getResult());
					for (int i = 0; i < value.size(); i++) {
						tmpArr.add("{\"code\":\""
								+ value.getJSONObject(i).getString("type_id")
								+ "\"}");
					}
				}
				break;
			case "bookLocation":// 图书馆藏地
				lResult = statisticsService.selectBookLocations(req);
				if (lResult.getState()) {
					value = JSONArray.fromObject(lResult.getResult());
					for (int i = 0; i < value.size(); i++) {
						tmpArr.add("{\"code\":\""
								+ value.getJSONObject(i).getString(
										"location_code") + "\"}");
					}
				}
				break;
			// case "bookCir"://图书流通类型
			// lResult = statisticsService.selectBookCirtypes(req);
			// break;
			case "bookMedia":// 图书载体
				lResult = statisticsService.selectBookMediatypes(req);
				if (lResult.getState()) {
					value = JSONArray.fromObject(lResult.getResult());
					for (int i = 0; i < value.size(); i++) {
						tmpArr.add("{\"code\":\""
								+ value.getJSONObject(i)
										.getString("media_code") + "\"}");
					}
				}
				break;

			default:
				break;
			}
			value = tmpArr;
			if (value == null) {
				throw new RuntimeException("=====>通过任务的lib_idx查找不到对应的统计子类型残数");
			}
			dynamicMap.put(key + type, value);

		}

		return value;
	}

}
