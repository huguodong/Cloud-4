package com.ssitcloud.utils;

import java.io.IOException;


public class Base64Helper {
	   public static String encode(byte[] byteArray) {
	        sun.misc.BASE64Encoder base64Encoder = new sun.misc.BASE64Encoder();
	        return base64Encoder.encode(byteArray);
	    }

	    public static byte[] decode(String base64EncodedString) {
	        sun.misc.BASE64Decoder base64Decoder = new sun.misc.BASE64Decoder();
	        try {
	            return base64Decoder.decodeBuffer(base64EncodedString);
	        } catch (IOException e) {
	            return null;
	        }
//	    	try{
//	    	Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");    
//	        Method mainMethod= clazz.getMethod("decode", String.class);    
//	        mainMethod.setAccessible(true);    
//	         Object retObj=mainMethod.invoke(null, base64EncodedString);    
//	         return (byte[])retObj;
//	    	}catch(Exception ex)
//	    	{
//	    		return null;
//	    	}
	    }
}
