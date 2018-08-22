package com.ssitcloud.business.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.Consts;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.TimeEntity;

public class SendHearbeatUtil {
	// 存放处理时间的队列
	public static BlockingQueue<TimeEntity> pq = new LinkedBlockingQueue<TimeEntity>();
	// 存放等待时间的队列
	public static BlockingQueue<TimeEntity> wq = new LinkedBlockingQueue<TimeEntity>();

	public static final String HEARBEAT_URL = "HEARBEAT_URL";
	public static final String SUCCESS = "success";
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURL;
	private static String node_name;

	static {
		PropertiesUtil.loadFile();
		node_name = PropertiesUtil.prop.getProperty("node_name");
	}

	/**
	 * 发送数据
	 * 
	 * @param req
	 */
	public void sendData(String req) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", req);

		this.sendData(map);
	}

	/**
	 * 发送数据
	 * 
	 * @param map
	 */

	public void sendData(Map<String, String> map) {
		String postUrl = requestURL.getRequestURL(HEARBEAT_URL);
		String result = HttpClientUtil.doPost(postUrl, map, Consts.UTF_8.toString());
		if (!SUCCESS.equals(result)) {
			StackTraceElement element = Thread.currentThread().getStackTrace()[1];
			String clazz = element.getClassName();
			String method = element.getMethodName();
			LogUtils.info(clazz + "." + method + "() 发送结果：" + result);
		}
	}

	/**
	 * 发送数据
	 */
	public void sendData() {
		int queue_len = pq.size() + wq.size();
		List<TimeEntity> pl = new ArrayList<TimeEntity>();
		List<TimeEntity> wl = new ArrayList<TimeEntity>();
		pq.drainTo(pl);
		wq.drainTo(wl);
		Map<String, List<TimeEntity>> pm = groupByKey(pl);
		Map<String, List<TimeEntity>> wm = groupByKey(wl);
		List<TimeEntity> ps = average(pm);
		List<TimeEntity> ws = average(wm);
		if (queue_len > 0) {
			String processInfo = ps == null ? "\"\"" : JSONArray.fromObject(ps).toString();
			String waitInfo = ws == null ? "\"\"" : JSONArray.fromObject(ws).toString();
			String object = "{\"node_name\":\"" + node_name + "\",\"queue_length\":" + queue_len + ",\"processInfo\":" + processInfo + ",\"waitInfo\":" + waitInfo + "}";
			JSONObject obj = JSONObject.fromObject(object);
			Map<String, String> map = new HashMap<String, String>();
			map.put("req", obj.toString());

			this.sendData(map);
			pm = null;
			wm = null;
			ps = null;
			ws = null;
		}
	}

	/**
	 * 接收数据并修改配置文件
	 * 
	 * @param req
	 */
	public static boolean modifyURL(String data) {
		JSONObject json = JSONObject.fromObject(data);
		boolean flag = PropertiesUtil.modifyByJSON(json);
		StackTraceElement element = Thread.currentThread().getStackTrace()[1];
		String clazz = element.getClassName();
		String method = element.getMethodName();
		LogUtils.info(clazz + "." + method + "() 修改结果：" + flag);
		return flag;
	}

	/**
	 * 计算List列表中的平均值
	 * 
	 * @param pm
	 * @return
	 */
	public static List<TimeEntity> average(Map<String, List<TimeEntity>> pm) {
		if (pm == null || pm.isEmpty())
			return null;
		List<TimeEntity> list = new ArrayList<TimeEntity>();
		for (List<TimeEntity> ps : pm.values()) {
			long sum = 0;
			TimeEntity entity = null;
			for (int i = 0; i < ps.size(); i++) {
				TimeEntity t = ps.get(i);
				if (entity == null) {
					entity = t.clone();
				}
				sum += t.getTime();
			}
			BigDecimal time = new BigDecimal(sum / ps.size()).setScale(0, BigDecimal.ROUND_HALF_UP);
			entity.setTime(time.longValue());
			list.add(entity);
		}
		return list;
	}

	/**
	 * 按关键字分组
	 * 
	 * @param list
	 * @return
	 */
	public static Map<String, List<TimeEntity>> groupByKey(List<TimeEntity> list) {
		Map<String, List<TimeEntity>> pm = new HashMap<String, List<TimeEntity>>();
		for (int i = 0; i < list.size(); i++) {
			TimeEntity p = list.get(i);
			String key = p.getClazz() + "." + p.getMethod();
			if (!pm.containsKey(key)) {
				pm.put(key, new ArrayList<TimeEntity>());
			}
			pm.get(key).add(p);
		}
		return pm;
	}
}
