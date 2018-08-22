package com.ssitcloud.mobileserver.util;

import java.security.MessageDigest;

public class HashUtils {
	/**
	 * 返回字符串小写的32位MD5
	 * @return 失败返回null
	 */
	public static String md5Low(String str) {
		return md5(str,true,true);
	}
	
	/**
	 * 返回字符串小写的32位MD5
	 * @return 失败返回null
	 */
	public static String md5Upper(String str) {
		return md5(str,true,false);
	}
	
	/**
    *返回字符串的MD5
    * @param model true为32位 false为16为md5
    * @param isLow 是否为小写串
    * @return 失败返回null
    */
   public static String md5(String str,boolean model/*true为32位 false为16为md5*/,boolean isLow/*是否为小写串*/) {
       try {
           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(str.getBytes());
           byte b[] = md.digest();

           int i;

           StringBuffer buf = new StringBuffer("");
           for (int offset = 0; offset < b.length; offset++) {
               i = b[offset];
               if (i < 0)
                   i += 256;
               if (i < 16)
                   buf.append("0");
               buf.append(Integer.toHexString(i));
           }
           if(model){// 32位的加密
               return isLow?buf.toString().toLowerCase():buf.toString().toUpperCase();
           }else{// 16位的加密
               return isLow?buf.substring(8, 24).toLowerCase():buf.substring(8, 24).toUpperCase();
           }

       } catch (Exception e) {
           return null;
       }
   }
}
