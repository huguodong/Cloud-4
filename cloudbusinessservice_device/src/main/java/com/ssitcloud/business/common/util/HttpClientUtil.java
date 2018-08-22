package com.ssitcloud.business.common.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.ssitcloud.business.common.CommonException;
import com.ssitcloud.business.common.exception.HttpResponseException;
import com.ssitcloud.business.common.exception.HttpTimeoutException;
/**
 * 利用HttpClient进行post请求的工具类
 * @author: liuBh,LXP
 * @version liuBh创建，2017年2月23日被LXP增强异常逻辑
 **/
public class HttpClientUtil {
//	private static final String APPLICATION_JSON = "application/json";
	//private static final String APPLICATION_XML = "application/xml";

//    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    //private static final String CONTENT_TYPE_TEXT_PLANE = "text/plain";
    private static int _post_socket_time_out;
    
    private static int _post_connection_time_out;
    
    private static Properties config;
    
    private static RequestConfig reqConfig;
    
    private static RequestConfig reqConfigL;
    static{
		try {
			config=PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("config.properties"), Consts.UTF_8.toString()));
			String post_socket_time_out=config.getProperty("post_socket_time_out","10000");
			String post_connection_time_out=config.getProperty("post_connection_time_out","10000");
			_post_socket_time_out=Integer.parseInt(post_socket_time_out);
			_post_connection_time_out=Integer.parseInt(post_connection_time_out);
			reqConfig =RequestConfig.custom().setSocketTimeout(_post_socket_time_out).setConnectTimeout(_post_connection_time_out).build();
			reqConfigL=RequestConfig.custom().setSocketTimeout(1500000).setConnectTimeout(1500000).build();
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
	 * @throws ConnectTimeoutException 请求超时异常，连接服务器超时
	 * @throws HttpTimeoutException 响应超时异常，传输数据超时
	 * @throws HttpResponseException 服务器不能正常响应请求，连接中断，responce code!=200
	 */
	public static String doPost(String url,Map<String,String> map,String charset){
		Log.DebugOnScr("BUS http doPost url:"+url);
		Log.DebugOnScr("BUS http doPost map:"+map);
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
			Log.DebugOnScr("BUS http doPost response:"+response);
			//当服务器正常响应时才进行读取数据请求，否则抛出HttpResponseException
			if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity resEntity = response.getEntity();
				Log.DebugOnScr("BUS http doPost resEntity:"+resEntity);
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,charset);
					if(result!=null && result.length()>200){
						Log.DebugOnScr("BUS http doPost result:"+result.substring(0, 200)+"......");
					}
					else{
						Log.DebugOnScr("BUS http doPost result:"+result);
					}
				}
			}else{
				HttpResponseException hre = null;
				if(response != null){
					int code = response.getStatusLine().getStatusCode();
					hre = new HttpResponseException("服务器没有正常响应请求,响应码==>"+code);
					hre.setResponceState(code);
				}else{
					hre = new HttpResponseException("服务器没有正常响应请求");
				}
				throw hre;
			}
			//start author by lxp 增强异常逻辑
		}catch(ConnectTimeoutException e){
			//捕捉链接异常
			LogUtils.error(new HttpTimeoutException("目标服务器请求超时，就是连不上服务器"));
		}catch(SocketTimeoutException e){
			//捕捉响应超时异常
			LogUtils.error(new HttpTimeoutException("目标服务器响应超时，就是连上了服务器，但是传输数据超时了"));
		}catch(IOException ex){
			LogUtils.error(new HttpResponseException("执行http请求时发生io异常，可能是连接被终止"));
		}catch(Exception e){
			LogUtils.error(e);
			//end author by lxp 
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
	 * @throws ConnectTimeoutException 请求超时异常，连接服务器超时
	 * @throws HttpTimeoutException 响应超时异常，传输数据超时
	 * @throws HttpResponseException 服务器不能正常响应请求，连接中断，responce code!=200
	 */
	public static String doPostLongtime(String url,Map<String,String> map,String charset){
		Log.DebugOnScr("BUS http doPostLongtime url:"+url);
		Log.DebugOnScr("BUS http doPostLongtime map:"+map);
		HttpPost httpPost = null;
		String result = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response=null;
		try{
			httpPost = new HttpPost(url);
			//设置请求和传输超时时间
			httpPost.setConfig(reqConfigL);
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
			Log.DebugOnScr("BUS http doPostLongtime response:"+response);
			if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity resEntity = response.getEntity();
				Log.DebugOnScr("BUS http doPostLongtime resEntity:"+resEntity);
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,charset);
					if(result!=null && result.length()>200){
						Log.DebugOnScr("BUS http doPostLongtime result:"+result.substring(0, 200)+"......");
					}
					else{
						Log.DebugOnScr("BUS http doPostLongtime result:"+result);
					}
				}
			}else{
				HttpResponseException hre = null;
				if(response != null){
					int code = response.getStatusLine().getStatusCode();
					hre = new HttpResponseException("服务器没有正常响应请求,响应码==>"+code);
					hre.setResponceState(code);
				}else{
					hre = new HttpResponseException("服务器没有正常响应请求");
				}
				throw hre;
			}
			//start author by lxp 增强异常逻辑
		}catch(ConnectTimeoutException e){
			//捕捉链接异常
			LogUtils.error(new HttpTimeoutException("目标服务器请求超时，就是连不上服务器"));
		}catch(SocketTimeoutException e){
			//捕捉响应超时异常
			LogUtils.error(new HttpTimeoutException("目标服务器响应超时，就是连上了服务器，但是传输数据超时了"));
		}catch(IOException ex){
			LogUtils.error(new HttpResponseException("执行http请求时发生io异常，可能是连接被终止"));
		}catch(Exception e){
			LogUtils.error(e);
			//end author by lxp 
		}finally{
			if(httpClient!=null){
				try {
					httpClient.close();
					httpClient=null;
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(response!=null){
						try {
							response.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 后台提交上传文件
	 * @param url
	 * @param file
	 * @param charset
	 * @return
	 */
	public static String postUpload(String url,File file){
		if(file==null||url==null) {
			LogUtils.info("postUpload url :"+url+" file"+file);
			return null;
		}
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		 // 把文件转换成流对象FileBody
		FileBody bin = new FileBody(file);
		 // 相当于<input type="file" name="file"/>
		HttpEntity reqEntity = MultipartEntityBuilder.create()
				.setCharset(Consts.UTF_8)
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addPart("file", bin).build();
	
		httpPost.setEntity(reqEntity);
		try(CloseableHttpClient	httpClient = HttpClients.createDefault()){
			 // 发起请求 并返回请求的响应
			 try(CloseableHttpResponse response = httpClient.execute(httpPost)){
				 if(response!=null){
					 // 获取响应对象
					 HttpEntity resEntity = response.getEntity();
					 if(resEntity != null){
						result = EntityUtils.toString(resEntity,Consts.UTF_8.toString());
					}
				 }
			 }
		} catch (IOException e) {
			e.printStackTrace();
		};
		return result;
	}
	
	/**
	 * 后台提交上传文件
	 * @param url
	 * @param file
	 * @param charset
	 * @return
	 */
	public static String postUpload(String url,File file,String libId){
		String result = null;
		try{
			if(file==null||url==null) {
				LogUtils.info("postUpload url :"+url+" file"+file);
				return null;
			}
			
			HttpPost httpPost = new HttpPost(url);
			 // 把文件转换成流对象FileBody
			FileBody bin = new FileBody(file);
			// 将JSON进行UTF-8编码,以便传输中文
	        String encoderJson = URLEncoder.encode(libId, Consts.UTF_8.toString());
			 // 相当于<input type="file" name="file"/>
			HttpEntity reqEntity = MultipartEntityBuilder.create().setCharset(Consts.UTF_8)
					.setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("file", bin).addTextBody("libId", encoderJson)
					.build();
		
			httpPost.setEntity(reqEntity);
			try(CloseableHttpClient	httpClient = HttpClients.createDefault()){
				 // 发起请求 并返回请求的响应
				 try(CloseableHttpResponse response = httpClient.execute(httpPost)){
					 if(response!=null){
						 // 获取响应对象
						 HttpEntity resEntity = response.getEntity();
						 if(resEntity != null){
							result = EntityUtils.toString(resEntity,Consts.UTF_8.toString());
						}
					 }
				 }
			} catch (IOException e) {
				e.printStackTrace();
			};
		}catch(Exception ex){
			System.out.println("上传文件到business层异常:"+ ex);
		}
		return result;
	}
	
	/**
	 * http上传文件，并且附带参数
	 *
	 * <p>2017年8月28日 上午11:01:36 
	 * <p>create by hjc
	 * @param url
	 * @param param
	 * @param file
	 * @return
	 */
	public static String postUploadWithParam(String url, Map<String, String> param, File file){
		if(file==null||url==null) {
			LogUtils.info("postUpload url :"+url+" file"+file);
			return null;
		}
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		// 把文件转换成流对象FileBody
		FileBody bin = new FileBody(file);
		// 相当于<input type="file" name="file"/>
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		//设置编码格式，设置mode以解决中文文件名乱码问题
		builder.setCharset(Consts.UTF_8).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		//设置传输参数
		for (Entry<String, String> set : param.entrySet()) {
			StringBody stringBody = new StringBody(set.getValue(), ContentType.APPLICATION_JSON);
			builder.addPart(set.getKey(), stringBody);
		}
		
		HttpEntity reqEntity = builder.addPart("file", bin).build();
		
		httpPost.setEntity(reqEntity);
		try(CloseableHttpClient	httpClient = HttpClients.createDefault()){
			// 发起请求 并返回请求的响应
			try(CloseableHttpResponse response = httpClient.execute(httpPost)){
				if(response!=null){
					// 获取响应对象
					HttpEntity resEntity = response.getEntity();
					if(resEntity != null){
						result = EntityUtils.toString(resEntity,Consts.UTF_8.toString());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		};
		return result;
	}
}