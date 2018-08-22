package com.ssitcloud.dbauth.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.helpers.PatternParser;

/**
 * 正则表达式匹配密码
 * <p>2016年4月12日 上午9:56:32
 * @author hjc
 *
 */
public class PasswordMatch {
	/** 大写 **/
	public static final String UPPER= "1";
	/** 小写 **/
	public static final String LOWER= "2";
	/** 数字 **/
	public static final String NUMBER= "3";
	/** 特殊字符  *-+@#^%&',;=?$/.!()_  **/
	public static final String SPECIAL= "4";
	
	
	
	/**
	 * 正则表达式匹配密码是否符合规范
	 * 
	 * <p>2016年4月12日 下午4:37:42
	 * <p>create by hjc
	 * @param pwd 密码明文
	 * @param charset 密码的字符集 1,2,3  1,2,3,4  1,4 
	 * @param length 密码最小长度
	 * @return
	 */
	public static Boolean isMatch(String pwd,String charset,Integer length) {
		if (StringUtils.isBlank(pwd) && StringUtils.isBlank(charset) && length==null) {
			throw new RuntimeException("pwd charset length 不能为空");
		}
		String regex = "";
		String newChar = charset.replace(" ", "").replace(",", "");
		
		switch (newChar) {
		case "1":
			regex = "[A-Z]";
			break;
		case "2":
			regex = "[a-z]";
			break;
		case "3":
			regex = "[0-9]";
			break;
		case "4":
			regex = "[*-+@#^%&',;=?$/.!()_]";
			break;
		case "12": //大小写字母
			regex = "(?!.*\\s.*)(?=.*[A-Z].*)(?=.*[a-z].*)(?!.*[0-9].*)(?!.*[*-+@#^%&',;=?$/.!()_].*).";
			break;
		case "13": //大写字母和数字
			regex = "(?!.*\\s.*)(?=.*[A-Z].*)(?!.*[a-z].*)(?=.*[0-9].*)(?!.*[*-+@#^%&',;=?$/.!()_].*).";
			break;
		case "14": //大写字母和特殊字符 
			regex = "(?!.*\\s.*)(?=.*[A-Z].*)(?!.*[a-z].*)(?!.*[0-9].*)(?=.*[*-+@#^%&',;=?$/.!()_].*).";
			break;
		case "23"://小写字母和数字
			regex = "(?!.*\\s.*)(?!.*[A-Z].*)(?=.*[a-z].*)(?=.*[0-9].*)(?!.*[*-+@#^%&',;=?$/.!()_].*).";
			break;
		case "24"://小写字母、特殊字符
			regex = "(?!.*\\s.*)(?!.*[A-Z].*)(?=.*[a-z].*)(?!.*[0-9].*)(?=.*[*-+@#^%&',;=?$/.!()_].*).";
			break;
		case "34"://数字和特殊字符
			regex = "(?!.*\\s.*)(?!.*[A-Z].*)(?!.*[a-z].*)(?=.*[0-9].*)(?=.*[*-+@#^%&',;=?$/.!()_].*).";
			break;
		case "123"://大小写字母，数字组合
			regex = "(?!.*\\s.*)(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*[0-9].*)(?!.*[*-+@#^%&',;=?$/.!()_].*).";
			break;
		case "124"://大小写字母、特殊字符组合
			regex = "(?!.*\\s.*)(?=.*[A-Z].*)(?=.*[a-z].*)(?!.*[0-9].*)(?=.*[*-+@#^%&',;=?$/.!()_].*).";
			break;
		case "134"://大写字母、数字、特殊字符组合
			regex = "(?!.*\\s.*)(?=.*[A-Z].*)(?!.*[a-z].*)(?=.*[0-9].*)(?=.*[*-+@#^%&',;=?$/.!()_].*).";
			break;
		case "234"://小写字母、数字、特殊字符组合
			regex = "(?!.*\\s.*)(?!.*[A-Z].*)(?=.*[a-z].*)(?=.*[0-9].*)(?=.*[*-+@#^%&',;=?$/.!()_].*).";
			break;
		case "1234"://大小写字母、数字、特殊字符的组合
			regex = "(?!.*\\s.*)(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*[0-9].*)(?=.*[*-+@#^%&',;=?$/.!()_].*).";
			break;

		default:
			regex = "";
			break;
		}
		
		regex = ""+regex+"{"+length+",}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pwd);
		return matcher.matches();
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		String phone = "ssit123456";
		String rex = "^(?=.*[0-9].*)(?!=.*[A-Z].*)(?=.*[a-z].*).{10,}$";
		//(?=.*[0-9].*)(?!.*[A-Z].*)(?=.*[a-z].*)(?!.*[*-+@#^%&',;=?$/.!()_].*).\\S
		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(phone);
		System.out.println(matcher.matches());
		System.out.println(isMatch("ssit12S3456!", "1234", 10));
		
		
	}
	
	
}
