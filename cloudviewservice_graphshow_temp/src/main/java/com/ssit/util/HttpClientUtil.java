package com.ssit.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import net.sf.json.JSONObject;

/**
 * HttpClient请求工具类
 **/
public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private static RequestConfig requestConfig;
	private static CloseableHttpClient httpClient = null;

	static {
		String socket_time_out = PropertiesUtil.getPropertiesValue("socket_time_out", "20000");
		String connection_time_out = PropertiesUtil.getPropertiesValue("connection_time_out", "20000");
		int socketTimeout = Integer.parseInt(socket_time_out);
		int connectTimeout = Integer.parseInt(connection_time_out);
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
	}

	/**
	 * 初始化HTTP请求配置
	 * 
	 * @return
	 */
	public static CloseableHttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
		}
		return httpClient;
	}

	/**
	 * 带参数的POST请求
	 * 
	 * @param httpPost
	 * @param map
	 * @return
	 */
	private static String doPost(HttpPost httpPost, Map<String, String> map) {
		CloseableHttpResponse response = null;
		String responseContent = null;
		try {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			for (Entry<String, String> elem : map.entrySet()) {
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
				httpPost.setEntity(entity);
			}
			httpClient = getHttpClient();
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				responseContent = EntityUtils.toString(entity, Consts.UTF_8);
				EntityUtils.consume(entity);
			} else {
				throw new Exception("HTTP Request error: " + response.getStatusLine());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return responseContent;
	}
	
	/**
	 * 带参数的POST请求
	 * 
	 * @param httpPost
	 * @param map
	 * @return
	 */
	private static String doPost(HttpPost httpPost, JSONObject json) {
		CloseableHttpResponse response = null;
		String responseContent = null;
		try {
			if (json != null && !json.isEmpty()) {
				StringEntity param = new StringEntity(json.toString());
				param.setContentEncoding("UTF-8");
				param.setContentType("application/json");// 发送json数据需要设置contentType
				httpPost.setEntity(param);
			}
			httpClient = getHttpClient();
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				responseContent = EntityUtils.toString(entity, Consts.UTF_8);
				EntityUtils.consume(entity);
			} else {
				throw new Exception("HTTP Request error: " + response.getStatusLine());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return responseContent;
	}

	/**
	 * GET 请求[一般类型]
	 * 
	 * @methodName: doGet
	 * @param httpGet
	 * @return
	 * @returnType: String
	 */
	private static String doGet(HttpGet httpGet) {
		CloseableHttpResponse response = null;
		String responseContent = null;
		try {
			httpClient = getHttpClient();
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= 300) {
				throw new Exception("HTTP Request error: " + response.getStatusLine());
			} else if (statusCode == HttpStatus.SC_OK) {
				responseContent = EntityUtils.toString(entity, Consts.UTF_8);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			logger.error("GET 请求异常：" + e.getLocalizedMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseContent;
	}

	/**
	 * 发送 post请求
	 * 
	 * @param httpUrl
	 *            地址
	 */
	public static String sendHttpPost(String httpUrl) {
		// 创建httpPost
		HttpPost httpPost = new HttpPost(httpUrl);
		return doPost(httpPost,new HashMap());
	}
	
	/**
	 * 发送 post请求
	 * 
	 * @param httpUrl
	 *            地址
	 */
	public static String sendHttpPost(String httpUrl, Map<String, String> map) {
		// 创建httpPost
		HttpPost httpPost = new HttpPost(httpUrl);
		return doPost(httpPost,map);
	}

	/**
	 * 发送 get请求
	 * 
	 * @param httpUrl
	 */
	public static String sendHttpGet(String httpUrl) {
		// 创建get请求
		HttpGet httpGet = new HttpGet(httpUrl);
		return doGet(httpGet);
	}

	public static void main(String[] args) {
		/*
		 * String url = "http://wthrcdn.etouch.cn/WeatherApi?city=神木"; String xml = sendHttpGet(url); //System.out.println(xml); xml = JsonUtils.xml2json(xml); //System.out.println(xml); JSONObject
		 * obj=JSONObject.fromObject(xml); Object weather=obj.getJSONObject("forecast").getJSONArray("weather").get(0); JSONObject date=JSONObject.fromObject(weather); String
		 * type=date.getJSONObject("day").get("type").toString(); System.out.println(type); String low=date.get("low").toString(); System.out.println(low); String high=date.get("high").toString();
		 * System.out.println(high);
		 */

		/*
		 * System.out.println("-----------------------------------------"); url = "http://flash.weather.com.cn/wmaps/xml/shenzhen.xml"; xml = sendHttpGet(url); System.out.println(xml); xml =
		 * JsonUtils.xml2json(xml); System.out.println(xml); System.out.println("-----------------------------------------");
		 */
		/*
		 * try { HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat(); format.setVCharType(HanyuPinyinVCharType.WITH_V); format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); String pingyin
		 * = PinyinHelper.toHanYuPinyinString("吕梁,襄阳", format, "", false); System.out.println(pingyin); } catch (BadHanyuPinyinOutputFormatCombination e) { e.printStackTrace(); }
		 */
	}
}