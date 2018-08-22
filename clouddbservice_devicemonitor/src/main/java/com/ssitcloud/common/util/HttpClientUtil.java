package com.ssitcloud.common.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.ssitcloud.common.CommonException;
/**
 * 利用HttpClient进行post请求的工具类
 **/
public class HttpClientUtil {
	private static final String APPLICATION_JSON = "application/json";
	//private static final String APPLICATION_XML = "application/xml";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    //private static final String CONTENT_TYPE_TEXT_PLANE = "text/plain";
    private static int _post_socket_time_out;
    
    private static int _post_connection_time_out;
    
    private static Properties config;
    
    private static RequestConfig reqConfig;
    static{
		try {
			config=PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("config.properties"), Consts.UTF_8.toString()));
			String post_socket_time_out=config.getProperty("post_socket_time_out","10000");
			String post_connection_time_out=config.getProperty("post_connection_time_out","10000");
			_post_socket_time_out=Integer.parseInt(post_socket_time_out);
			_post_connection_time_out=Integer.parseInt(post_connection_time_out);
			reqConfig =RequestConfig.custom().setSocketTimeout(_post_socket_time_out).setConnectTimeout(_post_connection_time_out).build();
		} catch (IOException e) {
			throw new CommonException("HttpClientUtil.java:"+e);
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
			httpPost.setConfig(reqConfig);
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
			LogUtils.error(ex.getMessage(),ex);
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