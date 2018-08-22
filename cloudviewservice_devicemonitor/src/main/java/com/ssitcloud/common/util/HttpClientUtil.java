package com.ssitcloud.common.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.exception.CommonException;
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
    
    private static int _post_socket_bak_rollback_time_out;
    private static int _post_connection_bak_rollback_time_out;
    
    
    private static Properties config;
    private static RequestConfig reqConfig;
    private static Registry<ConnectionSocketFactory> socketFactoryRegistry;

    static{
		try {
			config=PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("config.properties"), Consts.UTF_8.toString()));
			String post_socket_time_out=config.getProperty("post_socket_time_out","20000");
			String post_connection_time_out=config.getProperty("post_connection_time_out","20000");
			String post_socket_bak_rollback_time_out=config.getProperty("post_socket_bak_rollback_time_out","90000");
			String post_connection_bak_rollback_time_out=config.getProperty("post_connection_bak_rollback_time_out","90000");
			_post_socket_time_out=Integer.parseInt(post_socket_time_out);
			_post_connection_time_out=Integer.parseInt(post_connection_time_out);
			_post_socket_bak_rollback_time_out=Integer.parseInt(post_socket_bak_rollback_time_out);
			_post_connection_bak_rollback_time_out=Integer.parseInt(post_connection_bak_rollback_time_out);
			reqConfig =RequestConfig.custom().setSocketTimeout(_post_socket_time_out).setConnectTimeout(_post_connection_time_out).build();
			
			/** 调用ssl **/
	    	//采用绕过验证的方式处理https请求  
	        SSLContext sslcontext = createIgnoreVerifySSL();  
	        // 设置协议http和https对应的处理socket链接工厂的对象  
	        socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
	            .register("http", PlainConnectionSocketFactory.INSTANCE)  
	            .register("https", new SSLConnectionSocketFactory(sslcontext,new HostnameVerifier(){
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}}))  
	            .build();  
		} catch (Exception e) {
			throw new CommonException("HttpClientUtil.java:"+e);
		}
	}
    
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
			RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
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
			try(CloseableHttpClient httpClient =HttpClients.createDefault()){
				try(CloseableHttpResponse response = httpClient.execute(httpPost)){
					if(response != null){
						HttpEntity resEntity = response.getEntity();
						if(resEntity != null){
							result = EntityUtils.toString(resEntity,charset);
						}
						int code=response.getStatusLine().getStatusCode();
						if(code>300){
							if(code==HttpStatus.SC_NOT_FOUND){//
								ResultEntity newRes=new ResultEntity();
								newRes.setRetMessage("请求"+url+"--->404 访问不到服务");
								LogUtils.error("HttpClientUtil:"+newRes.getRetMessage());
								result=JsonUtils.toJson(newRes);
							}else if(code==HttpStatus.SC_INTERNAL_SERVER_ERROR){
								ResultEntity newRes=new ResultEntity();
								newRes.setRetMessage("请求"+url+"--->500 访问服务异常");
								LogUtils.error("HttpClientUtil:"+newRes.getRetMessage());
								result=JsonUtils.toJson(newRes);
							}else{
								//
							}
						}
					}
				}
			}
		}catch(Exception ex){
			//待处理
			LogUtils.error(ex.getMessage(), ex);
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
	 * @description: TODO
	 */
	
	public static String doPostSSL(String url,Map<String,String> map,String charset){
		HttpPost httpPost = null;
		String result = null;
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
			try(CloseableHttpClient httpClient = enableSSL();){
				try(CloseableHttpResponse response = httpClient.execute(httpPost)){
					if(response != null){
						HttpEntity resEntity = response.getEntity();
						if(resEntity != null){
							result = EntityUtils.toString(resEntity,charset);
						}
						int code=response.getStatusLine().getStatusCode();
						if(code>300){
							if(code==HttpStatus.SC_NOT_FOUND){//
								ResultEntity newRes=new ResultEntity();
								newRes.setRetMessage("请求"+url+"--->404 访问不到服务");
								LogUtils.error("HttpClientUtil:"+newRes.getRetMessage());
								result=JsonUtils.toJson(newRes);
							}else if(code==HttpStatus.SC_INTERNAL_SERVER_ERROR){
								ResultEntity newRes=new ResultEntity();
								newRes.setRetMessage("请求"+url+"--->500 访问服务异常");
								LogUtils.error("HttpClientUtil:"+newRes.getRetMessage());
								result=JsonUtils.toJson(newRes);
							}else{
								//
							}
						}
					}
				}
			}
		}catch(Exception ex){
			//待处理
			LogUtils.error(ex.getMessage(), ex);
		}
		return result;
	}
	/**
	 * POST 请求[一般类型]
	 * 手动设置超时
	 * @methodName: doPost
	 * @param url 请求地址
	 * @param map 参数集
	 * @param charset 编码格式
	 * @return
	 * @returnType: String
	 * @author: liuBh
	 * @description: TODO
	 */
	public static String doPostLongTimeout(String url,Map<String,String> map,String charset){
		HttpPost httpPost = null;
		String result = null;
		try{
			httpPost = new HttpPost(url);
			//设置请求和传输超时时间
			RequestConfig config = RequestConfig.custom()
					.setSocketTimeout(_post_socket_bak_rollback_time_out).setConnectTimeout(_post_connection_bak_rollback_time_out).build();
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
			
			try(CloseableHttpClient httpClient = HttpClients.createDefault()){
				try(CloseableHttpResponse response = httpClient.execute(httpPost)){
					if(response != null){
						HttpEntity resEntity = response.getEntity();
						if(resEntity != null){
							result = EntityUtils.toString(resEntity,charset);
						}
						int code=response.getStatusLine().getStatusCode();
						if(code==404){//
							ResultEntity newRes=new ResultEntity();
							newRes.setRetMessage("请求"+url+"--->404 访问不到服务");
							result=JsonUtils.toJson(newRes);
						}else if(code==500){
							ResultEntity newRes=new ResultEntity();
							newRes.setRetMessage("请求"+url+"--->500 访问服务异常");
							result=JsonUtils.toJson(newRes);
						}
					}
				}
			}
		}catch(Exception ex){
			//待处理
			ex.printStackTrace();
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
		HttpEntity reqEntity = MultipartEntityBuilder.create().setCharset(Consts.UTF_8).addPart("file", bin).build();
	
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
	
	
	 public static final int cache = 1024*1024*1024;//128M

	/** 
     * 根据url下载文件，保存到filepath中 
     * @param url  本地路径
     * @param filepath 
     * @return 
     */  
    public static File download(String url,String filepath,String fileName,Map<String,String> map) {  
        try {
        	  //如果在同一台服务器上感觉有问题，不好测试，则修改一下路径
        	 
        	   String path=StringUtils.delete(filepath, fileName);
			   File file = new File(path+File.separator+"view");
			   File filep = new File(path);
			   if(!filep.exists()){
				   if(!filep.mkdirs()){
					   throw new RuntimeException("创建路径失败");
				   }
			   }
			   if(!file.exists()){
				  boolean isMk=file.mkdir();
				  if(!isMk) throw new RuntimeException("创建文件夹失败");
			   }
			   File relFile=new File(filepath+File.separator+fileName);
			   if(relFile.exists()){
				   //存在则,则直接返回供下载
				   return relFile;
			   }
        	try(CloseableHttpClient	httpClient = HttpClients.createDefault()){
        		 HttpGet httpget = new HttpGet(url);
        		 HttpPost httpPost=new HttpPost(url);
        			//设置参数
     			List<NameValuePair> list = new ArrayList<NameValuePair>();
     			
     			for(Entry<String, String> elem:map.entrySet()){
     				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
     			}
     			if(list.size() > 0){
     				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,Consts.UTF_8);
     				httpPost.setEntity(entity);
     			}
        		 try(CloseableHttpResponse response=httpClient.execute(httpget)){
        			   HttpEntity entity = response.getEntity(); 
        			   try(InputStream is = entity.getContent()){
        				   try(FileOutputStream fileout = new FileOutputStream(relFile)){
        					     /** 
        			             * 根据实际运行效果 设置缓冲区大小 
        			             */  
        			            byte[] buffer=new byte[cache];  
        			            int ch = 0;  
        			            while ((ch = is.read(buffer)) != -1) {  
        			                fileout.write(buffer,0,ch);  
        			            } 
        			            return relFile; 
        				   }
        			   }  
        		 }
        	}
        } catch (Exception e) {  
            LogUtils.error("下载文件出错：", e);
        }
		return null;
    }  
    /**
     * 开启 HTTPS 支持
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
	public static CloseableHttpClient enableSSL() throws KeyManagementException, NoSuchAlgorithmException {
       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
       //创建自定义的httpclient对象  
       return HttpClients.custom().setConnectionManager(connManager).build();  
	}
 
	/**
	 * 绕过验证
	 * 	
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("TLS");
		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}
			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

}