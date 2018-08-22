package com.ssitcloud.app_operator.common.utils;

import android.content.Context;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.Map;

/**
 * 字符串验证工具类
 * @author LXP
 * @version 创建时间：2017年2月24日 下午5:30:22
 */
public class StringUtils {

    /**
     * 字符串是否为空
     * @param str
     * @return null或者length==0返回true，其他返回false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEmpty(String... str){
        if(str == null){
            return true;
        }
        for (String s : str) {
            if(isEmpty(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为15或者18位身份证号
     * @param str
     * @return 不为null且符合身份证格式返回true，否则返回false
     */
    public static boolean isIdNumber(String str){
        if(str == null){
            return false;
        }
        if(str.length() == 18){
            return str.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$");
        }
        if(str.length() == 15){
            return str.matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        }
        return false;
    }

    /**
     * 是否为手机号
     * @param str 不为null且符合手机号格式返回true，否则返回false
     * @return
     */
    public static boolean isMobile(String str){
        if(str == null){
            return false;
        }
        return str.matches("^(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}$");
    }

    /**
     * 是否为邮箱
     * @param str 不为null且符合邮箱格式返回true，否则返回false
     * @return
     */
    public static boolean isEmail(String str){
        if(str == null){
            return false;
        }
        return str.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }


    /**
     * 判断两个对象是否相等
     * @param o1
     * @param o2
     * @return 若o1==null && o2==null return true;
     * 若o1!=null && o2 != null && o1.equal(o2) return true;
     * other return false;
     */
    public static boolean isEqual(Object o1,Object o2){
        if(o1==null && o2==null){
            return true;
        }
        if(o1!=null && o2!=null&&o1.equals(o2)){
            return true;
        }
        return false;
    }

    /**
     * 返回字符串
     * @param str 字符串
     * @param defaultStr
     * @return 若str==null || str.isempty，return defaultStr
     */
    public static String getStr(String str,String defaultStr){
        return str!= null && !str.isEmpty()?str:defaultStr;
    }

    /**
     * 安全的返回字符串
     * @param obj 对象
     * @param defaultStr
     * @return obj==null，return defaultStr
     */
    public static String getStr(Object obj,String defaultStr){
        return obj!= null?obj.toString():defaultStr;
    }
    /**
     * 字符串解析成Integer
     * @param str
     * @return 若str == null or str not int return null ，other return Integet.valueOf(str)
     */
    public static Integer parseInteger(String str){
        if(str == null){
            return null;
        }
        try{
            return Integer.valueOf(str);
        }catch (NumberFormatException e){
            return null;
        }
    }

    /**
     * 字符串解析成Double
     * @param str
     * @return 若str == null or str not double return null ，other return Double.valueOf(str)
     */
    public static Double parseDouble(String str){
        if(str == null){
            return null;
        }
        try{
            return Double.valueOf(str);
        }catch (NumberFormatException e){
            return null;
        }
    }


    /**
     * 返回小写串
     * @param plainText
     * @param model true为32位 false为16为md5
     * @return
     */
    public static String getMd5(String plainText,boolean model){
        return getMd5(plainText,model,true);
    }

    /**
     *
     * @param plainText
     * @param model true为32位 false为16为md5
     * @param isLow 是否为小写串
     * @return
     */
    public static String getMd5(String plainText,boolean model/*true为32位 false为16为md5*/,boolean isLow/*是否为小写串*/) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
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
                return isLow?buf.toString().substring(8, 24).toLowerCase():buf.toString().substring(8, 24).toUpperCase();
            }

        } catch (Exception e) {
            return null;
        }
    }

    public static String getDesc(Object desc) {
        String country = Locale.getDefault().getCountry();
        String lan = Locale.getDefault().getLanguage();
        lan = !lan.isEmpty()?"_"+lan.toUpperCase():"";
        if (desc instanceof Map) {
            Object v = ((Map) desc).get(country.toLowerCase()+lan);
            if(v != null) {
                return String.valueOf(((Map) desc).get(v));
            }else{
                return String.valueOf(((Map) desc).get("zh_CN"));
            }
        } else {
            return String.valueOf(desc);
        }
    }

    /**
     * 检查密码
     * @param pwd 密码
     * @param charset 密码字符集
     * @param length 长度
     * @return 若不符合规范返回提示语，符合规范则返回null
     */
    public static String checkPwd(String pwd,String charset,int length){
        String tip = "密码长度需大于等于"+length;
        if(charset != null){
            String t = "";
            if(charset.contains("1")){
                t += "大写字母";
            }
            if(charset.contains("2")){
                t += t.isEmpty()?"小写字母":",小写字母";
            }
            if(charset.contains("3")){
                t += t.isEmpty()?"数字":",数字";
            }
            if(charset.contains("4")){
                t += t.isEmpty()?"特殊字符":",特殊字符";
            }
            if(!t.isEmpty()){
                tip += ",且密码为"+t+"组和";
            }
        }

        if(pwd == null || pwd.length() < length){
            return tip;
        }
        if(charset != null){
            boolean b1,b2,b3,b4;
            b1 = b2 = b3 = b4 = false;
            for(int i = 0;i<pwd.length();i++){
                char c = pwd.charAt(i);
                if(Character.isUpperCase(c)){
                    b1 = true;
                }else if(Character.isLowerCase(c)){
                    b2 = true;
                }else if(Character.isDigit(c)){
                    b3 = true;
                }else if(!Character.isSpaceChar(c)){
                    b4 = true;
                }
                if(b1 && b2 && b3 && b4){
                    break;
                }
            }
            if(charset.contains("1") != b1
                    || charset.contains("2") != b2
                    || charset.contains("3") != b3
                    || charset.contains("4") != b4){
                return tip;
            }
        }

        return null;
    }
}
