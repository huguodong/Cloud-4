package com.ssitcloud.common.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 利用HttpClient进行post请求的工具类
 **/
public class HttpClientUtil {
	private static final String APPLICATION_JSON = "application/json";
	//private static final String APPLICATION_XML = "application/xml";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    //private static final String CONTENT_TYPE_TEXT_PLANE = "text/plain";
    
    
	static RequestConfig configL = RequestConfig.custom()
			.setSocketTimeout(1500000)
			.setConnectTimeout(1500000).build();

    /**
     * POST 请求[json方式]
     * 请求头为 content-type=application/json 方式的请求
     * 请求内容为 content-type=text/json
     * @methodName: httpPostWithJSON
     * @param url
     * @param json
     * @returnType: void
     * @author: liuBh
     */
	public static void httpPostWithJSON(String url, String json){
		try{
			 // 将JSON进行UTF-8编码,以便传输中文
	        String encoderJson = URLEncoder.encode(json, Consts.UTF_8.toString());
	        //设置请求和传输超时时间
	        
	        HttpPost httpPost = new HttpPost(url);
	        //设置连接超时和传输数据超时
			RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(5000).build();
			httpPost.setConfig(config);
			
	        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
	        StringEntity se = new StringEntity(encoderJson);
	        se.setContentType(CONTENT_TYPE_TEXT_JSON);
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
	        httpPost.setEntity(se);
	        try(CloseableHttpClient httpClient =HttpClients.createDefault()){
				httpClient.execute(httpPost);
			} 
		}catch(Exception e){
			throw new CommonException("发送数据失败", e);
		}
	}
	/**
	 * 接收POST请求的所有请求数据
	 * @methodName: receivePost
	 * @param req
	 * @return
	 * @returnType: String
	 * @author: liuBh
	 */
	public static String receivePost(HttpServletRequest req){
		try{
			// 读取请求内容
	        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
	        String line = null;
	        StringBuilder sb = new StringBuilder();
	        while((line = br.readLine())!=null){
	            sb.append(line);
	        }
	        // 将资料解码
	        String reqBody = sb.toString();
	        return URLDecoder.decode(reqBody, Consts.UTF_8.toString());	
		}catch(Exception e){
			throw new CommonException("获取请求数据失败", e);
		}
	}
	
	
	/**
	 * POST 请求[一般类型]
	 * 
	 * @methodName: doPost
	 * @param url 请求地址
	 * @param map 参数集
	 * @param charset 编码格式
	 * @return
	 * @returnType: String
	 * @author: liuBh
	 * @description: TODO
	 */
	public static String doPost(String url,Map<String,String> map,String charset){
		HttpPost httpPost = null;
		String result = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response=null;
		try{
			httpPost = new HttpPost(url);
			//设置请求和传输超时时间
			RequestConfig config = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
			httpPost.setConfig(config);
			//设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			
			for(Entry<String, String> elem:map.entrySet()){
				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
			}
			if(list.size() > 0){
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
				httpPost.setEntity(entity);
			}
			httpClient=HttpClients.createDefault();
			response = httpClient.execute(httpPost);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,charset);
				}
			}
		}catch(Exception ex){
			//待处理
			LogUtils.error(ex);
		}finally{
			if(httpClient!=null){
				try {
					httpClient.close();
					httpClient=null;
				} catch (IOException e) {
					LogUtils.error(e);
				}finally{
					if(response!=null){
						try {
							response.close();
						} catch (IOException e) {
							LogUtils.error(e);
						}
					}
				}
			}
		}
		return result;
	}
	public static String doPostLongtime(String reqURL, Map<String, String> map,
			String charset) {
		HttpPost httpPost = null;
		String result = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response=null;
		try{
			httpPost = new HttpPost(reqURL);
			//设置请求和传输超时时间
			httpPost.setConfig(configL);
			//设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			
			for(Entry<String, String> elem:map.entrySet()){
				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
			}
			if(list.size() > 0){
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
				httpPost.setEntity(entity);
			}
			httpClient=HttpClients.createDefault();
			response = httpClient.execute(httpPost);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,charset);
				}
			}
		}catch(Exception ex){
			//待处理
			LogUtils.error(ex);
		}finally{
			if(httpClient!=null){
				try {
					httpClient.close();
					httpClient=null;
				} catch (IOException e) {
					LogUtils.error(e);
				}finally{
					if(response!=null){
						try {
							response.close();
						} catch (IOException e) {
							LogUtils.error(e);
						}
					}
				}
			}
		}
		return result;
	}
	
}