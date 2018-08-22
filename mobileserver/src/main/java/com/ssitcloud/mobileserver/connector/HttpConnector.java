package com.ssitcloud.mobileserver.connector;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.Map;

/**
 * Created by LXP on 2017/7/21.
 */

public class HttpConnector {
    private String charset;

    public HttpConnector(String charset) {
        this.charset = charset;
    }

    public String requestGetData(URL url,Map<String, String> headers, String body,int connectTimeout,int readTimeout) throws IOException {
        return requestData(url,"GET",headers,body,connectTimeout,readTimeout);
    }

    public String requestPostData(URL url,Map<String, String> headers, String body,int connectTimeout,int readTimeout) throws IOException {
        return requestData(url,"POST",headers,body,connectTimeout,readTimeout);
    }

    private String requestData(URL url,String method,Map<String,String> headers,String body,int connectTimeout,int readTimeout) throws IOException{
        HttpURLConnection conn = ((HttpURLConnection) url.openConnection());
        conn.setRequestMethod(method);
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        if(!StringUtils.isEmpty(body)){
            conn.setDoOutput(true);
        }

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.addRequestProperty(entry.getKey(),entry.getValue());
            }
        }

        conn.connect();
        if(!StringUtils.isEmpty(body)){
            try(OutputStream out = conn.getOutputStream()){
                out.write(body.getBytes(charset));
                out.flush();
            }
        }
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),charset))){
                char[] buff = new char[64];
                StringBuilder sb = new StringBuilder(256);
                int len;
                while((len = reader.read(buff)) != -1){
                    sb.append(buff,0,len);
                }
                return sb.toString();
            }
        }

        throw new SocketException("error Response,Responce code->"+conn.getResponseCode());
    }
}
