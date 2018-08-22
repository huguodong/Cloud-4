package com.ssitcloud.view.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {
	public final static String AJAX_HEADER="X-Requested-With";
	public final static String XMLHttpRequest="XMLHttpRequest";
	
	public static boolean isAjax(HttpServletRequest req){
		return XMLHttpRequest.equalsIgnoreCase(req.getHeader(AJAX_HEADER));
	}
	public static void sendJson(HttpServletResponse resp,String json) throws IOException{
		PrintWriter pw=getWriterFromResponse(resp);
		if(pw!=null){
			pw.write(json);
			pw.flush();
			pw.close();
		}
	}
	 public static PrintWriter getWriterFromResponse(ServletResponse resp){
		 	if(resp==null) return null;
		    HttpServletResponse response = (HttpServletResponse)resp;
		    response.setHeader("Content-type", "text/html;charset=UTF-8");  
		    
		    response.setCharacterEncoding("UTF-8");  
		    try {
		      return response.getWriter();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
			return null;
	}
	 /**
		 * 获取ip
		 * 
		 * @param request
		 * @return
		 */
		public static String getIpAddr(HttpServletRequest request) {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			return ip;
		}
		/** 
	     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址; 
	     *  
	     * @param request 
	     * @return 
	     * @throws IOException 
	     */  
	    public final static String getIpAddress(HttpServletRequest request) {  
	        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
	  
	        String ip = request.getHeader("X-Forwarded-For");  
	  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("Proxy-Client-IP");  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("WL-Proxy-Client-IP");  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("HTTP_CLIENT_IP");  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getRemoteAddr();  
	            }  
	        } else if (ip.length() > 15) {  
	            String[] ips = ip.split(",");  
	            for (int index = 0; index < ips.length; index++) {  
	                String strIp = (String) ips[index];  
	                if (!("unknown".equalsIgnoreCase(strIp))) {  
	                    ip = strIp;  
	                    break;  
	                }  
	            }  
	        }  
	        return ip;  
	    }  
	
}
