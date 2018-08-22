package com.ssitcloud.app_operator.common.utils;


import com.ssitcloud.app_operator.common.entity.HttpResponce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by LXP on 2017/3/6.
 * 用于进行http请求的工具类
 */

public class HttpClientUtil {
    private static final String APPLICATION_JSON = "application/json";
    //private static final String APPLICATION_XML = "application/xml";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    //private static final String CONTENT_TYPE_TEXT_PLANE = "text/plain";

    private static int sock_timeout = 3_000;
    private static int connect_timeout = 3_000;

    private static String TAG = "HttpClientUtil";

    private static HttpURLConnection openConn(String url) throws MalformedURLException, IOException {
        URL urlOjb = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlOjb.openConnection();
        conn.setConnectTimeout(connect_timeout);
        conn.setReadTimeout(sock_timeout);
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        return conn;
    }

    private static String mapToPostParam(Map<String, String> map) {
        if (map == null) {
            throw new IllegalArgumentException("args map is null");
        }
        Set<String> keySet = map.keySet();
        StringBuilder sb = new StringBuilder(64);
        for (String key : keySet) {
            if (sb.length() == 0) {
                sb.append(key).append("=").append(map.get(key));
            } else {
                sb.append('&').append(key).append("=").append(map.get(key));
            }
        }
        return sb.toString();
    }

    /**
     *  post请求数据
     * @param url
     * @param map
     * @param charset
     * @return
     */
    public static HttpResponce dopost(String url, Map<String, String> map, String charset) {
        HttpResponce hr = new HttpResponce();
        StringBuilder sb = new StringBuilder(64);
        HttpURLConnection conn = null;
        OutputStream outStream = null;
        BufferedReader br = null;
        try {

            conn = openConn(url);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            String param = mapToPostParam(map);
            conn.connect();
            //write data
            try {
                outStream = conn.getOutputStream();
                outStream.write(param.getBytes(charset));
                outStream.flush();
            } finally {
                if(outStream != null){
                    outStream.close();
                }
            }

            hr.setState(conn.getResponseCode());
            if(conn.getResponseCode() == 200) {
                //reader data
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("url地址不正确");
        } catch (IOException e) {
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        hr.setBody(sb.toString());
        return hr;
    }
}
