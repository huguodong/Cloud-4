package com.ssitcloud.common.util;
import java.text.SimpleDateFormat;

public class Log {
	private static boolean onDebug=false;
	private static String level;
	private static int lvInt;
	private final static int TRACE=0;
	private final static int DEBUG=1;
	private final static int INFO=2;
	private final static int WARN=3;
	private final static int ERROR=4;
	
	static{
		try {
			onDebug=Boolean.parseBoolean(PropertiesUtil.getConfigPropValueAsText("onDebug"));
			level=PropertiesUtil.getConfigPropValueAsText("LogLevel");
			if(level==null || level==""){
				level="DEBUG";
			}
			if("TRACE".equals(level)){
				lvInt=0;
			}else if("DEBUG".equals(level)){
				lvInt=1;
			}else if("INFO".equals(level)){
				lvInt=2;
			}else if("WARN".equals(level)){
				lvInt=3;
			}else if("ERROR".equals(level)){
				lvInt=4;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"); 
	
	/**
	 * @deprecated
	 * 这个方法废弃，请使用DebugOnScr
	 * @see DebugOnScr
	 * @param log
	 */
	public static void DebugOnScreen(String log)
	{
		if(onDebug){
			String date=sDateFormat.format(new java.util.Date());
		    System.out.println("<" + date + "> " + log);
		}
	}
	/**
	 * 取代DebugOnScreen
	 * @param log
	 */
	public static void DebugOnScr(String log){
		if(DEBUG>=lvInt){
			String date=sDateFormat.format(new java.util.Date());
			System.out.println("<" + date + "> " + log);
		}
	}
	public static void InfOnScreen(String log){
		if(INFO>=lvInt){
			String date=sDateFormat.format(new java.util.Date());
			System.out.println("<" + date + "> " + log);
		}
	}
	public static void WarnOnScreen(String log){
		if(WARN>=lvInt){
			String date=sDateFormat.format(new java.util.Date());
			System.out.println("<" + date + "> " + log);
		}
	}
	public static void ErrOnScreen(String log){
		if(ERROR>=lvInt){
			String date=sDateFormat.format(new java.util.Date());
			System.out.println("<" + date + "> " + log);
		}
	}
	public static void TraceOnScreen(String log){
		if(TRACE>=lvInt){
			String date=sDateFormat.format(new java.util.Date());
			System.out.println("<" + date + "> " + log);
		}
	}

}
