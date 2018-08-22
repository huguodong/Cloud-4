package com.ssitcloud.dblib.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class IsbnUtils {
	
	private static String numRegEx = "[^0-9]";
	
	private static Pattern numPattern = Pattern.compile(numRegEx);
	
	
	/**
	 * 检测isbn的长度
	 *
	 * <p>2017年11月10日 上午11:44:28 
	 * <p>create by hjc
	 * @param isbn
	 * @return
	 */
	public static boolean correctIsbn(String isbn){
		if (StringUtils.isBlank(isbn)) {
			return false;
		}
		Matcher matcher = numPattern.matcher(isbn);
		String isbnNum = matcher.replaceAll("").trim();
		if (isbnNum.length() == 10 || isbnNum.length() == 13) {
			return true;
		}
		return false;
	}
	
	/**
	 * 返回去掉-的isbn数字序列
	 *
	 * <p>2017年11月10日 下午7:24:33 
	 * <p>create by hjc
	 * @param isbn
	 * @return
	 */
	public static String getCorrectIsbn(String isbn) {
		if (StringUtils.isBlank(isbn)) {
			return "";
		}
		Matcher matcher = numPattern.matcher(isbn);
		String isbnNum = matcher.replaceAll("").trim();
		return isbnNum;
	}
	
	
	public static void main(String[] args) {
	}

}
