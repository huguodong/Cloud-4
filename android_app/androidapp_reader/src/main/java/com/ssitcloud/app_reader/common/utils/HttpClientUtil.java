package com.ssitcloud.app_reader.common.utils;

import android.util.Log;

import com.ssitcloud.app_reader.common.entity.HttpByteResponce;
import com.ssitcloud.app_reader.common.entity.HttpResponce;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by LXP on 2017/3/6.
 * 用于进行http请求的工具类
 */

public class HttpClientUtil {
//    private static final String APPLICATION_JSON = "application/json";
//    //private static final String APPLICATION_XML = "application/xml";
//
//    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
//    //private static final String CONTENT_TYPE_TEXT_PLANE = "text/plain";

    private static int sock_timeout = 10_000;
    private static int connect_timeout = 10_000;

//    private static String TAG = "HttpClientUtil";

    private static HttpURLConnection openConn(String url) throws MalformedURLException, IOException {
        URL urlOjb = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlOjb.openConnection();
        conn.setConnectTimeout(connect_timeout);
        conn.setReadTimeout(sock_timeout);
        conn.setChunkedStreamingMode(0);
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

    public static HttpResponce dopost(String url){
        return dopost(url,null,null);
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

            conn.connect();
            //write data
            if(map != null) {
                String param = mapToPostParam(map);
                try {
                    outStream = conn.getOutputStream();
                    outStream.write(param.getBytes(charset));
                    outStream.flush();
                } finally {
                    if(outStream != null){
                        outStream.close();
                    }
                }
            }

            hr.setState(conn.getResponseCode());
            if(conn.getResponseCode() == 200) {
                //reader data
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(),charset));
                String line;
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

    public static HttpByteResponce dogetToByte(String url, Map<String, String> map){
        return requestToByte(url,map,false);
    }

    public static HttpByteResponce dopostToByte(String url, Map<String, String> map){
        return requestToByte(url,map,true);
    }

    public static HttpByteResponce requestToByte(String url, Map<String, String> map,boolean isPost){
        HttpByteResponce hbr = new HttpByteResponce();
        ArrayList<Byte> bList;

        HttpURLConnection conn = null;
        OutputStream outStream = null;
        BufferedInputStream bi = null;

        try {
            String param = null;
            if(map != null) {
                param = mapToPostParam(map);
                if(!isPost){
                    url = url+"?"+param;
                }
            }

            conn = openConn(url);
            if(isPost) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setDoInput(true);
            conn.connect();
            //write data
            if(isPost) {
                if (param != null) {
                    try {
                        outStream = conn.getOutputStream();
                        outStream.write(param.getBytes());
                        outStream.flush();
                    } finally {
                        if(outStream != null){
                            outStream.close();
                        }
                    }
                }
            }

            hbr.setState(conn.getResponseCode());
            Log.d("HttpClient","url==>"+url+" responce state "+hbr.getState());
            if(conn.getResponseCode() == 200) {
                //reader data
                bi = new BufferedInputStream(conn.getInputStream());
                String line;
                bList = new ArrayList<>(bi.available());
                byte[] temp = new byte[16];
                int len;
                while ((len = bi.read(temp)) != -1) {
                    for(int i = 0;i<len;++i){
                        bList.add(temp[i]);
                    }
                }

                byte[] data = new byte[bList.size()];
                for (int i = 0,z=bList.size(); i < z; i++) {
                    data[i] = bList.get(i);
                }
                hbr.setBody(data);
            }
        } catch (MalformedURLException e) {
            Log.d("HttpClient","url==>"+url+"url地址不正确");
        } catch (IOException e) {
        }finally {
            if(bi != null){
                try {
                    bi.close();
                } catch (IOException e) {
                }
            }
        }

        return hbr;
    }
}
