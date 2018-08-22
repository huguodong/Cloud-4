package com.ssit.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class WeatherUtil {
	private static Logger logger =LoggerFactory.getLogger(WeatherUtil.class);
	private static final String weatherApi = "http://wthrcdn.etouch.cn/WeatherApi?city=";

	public static Map<String, String> getWeather(String weather_city) {
		String url = weatherApi + weather_city;
		//logger.info("weather url-->"+url);
		Map<String, String> result = new HashMap<String, String>();
		String xml = HttpClientUtil.sendHttpGet(url);
		if (xml != null) {
			//logger.info("before:"+xml);
			xml = JsonUtils.xml2json(xml);
			//logger.info("after:"+xml);
			JSONObject obj = JSONObject.fromObject(xml);
			if(obj.containsKey("forecast")){
				Object weather = obj.getJSONObject("forecast").getJSONArray("weather").get(0);
				JSONObject date = JSONObject.fromObject(weather);
				String type = date.getJSONObject("day").optString("type");
				//logger.info(type);
				result.put("type", type);
				String _result = type.replaceAll("晴", "sun").replaceAll("阴[\u4E00-\u9FA5]*", "cloudy").replaceAll("多云", "cloudy").replaceAll("[\u4E00-\u9FA5]*[雨]", "rain").replaceAll("[\u4E00-\u9FA5]*[雪]", "snow");
				result.put("weather", _result);
				String low = date.get("low").toString();
				//logger.info(low);
				result.put("low", low);
				_result = low.replaceAll("[\u4E00-\u9FA5\uF900-\uFA2D\u0020]", "").replaceAll("℃", "") + "-";
				String high = date.get("high").toString();
				//logger.info(high);
				result.put("high", high);
				_result += high.replaceAll("[\u4E00-\u9FA5\uF900-\uFA2D\u0020]", "");
				result.put("temp", _result);
			}
		}
		logger.info(weather_city+":"+result.toString());
		return result;
	}

	public static void main(String[] args) {
		getWeather("深圳");
	}
}
