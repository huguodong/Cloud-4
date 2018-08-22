package com.ssitcloud.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	/** 
     * MD5 加密 
     */  
    public static String getMD5Str(String str) {  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
  
            messageDigest.reset();  
  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        //将加密后的byte数组转换为十六进制的字符串,否则的话生成的字符串会乱码  
        for (int i = 0; i < byteArray.length; i++) {  
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){  
                md5StrBuff.append("0").append(  
                        Integer.toHexString(0xFF & byteArray[i]));  
            }else{  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
            }  
        }  
  
        return md5StrBuff.toString();  
    }  
}
