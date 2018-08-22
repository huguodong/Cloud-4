package com.ssitcloud.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共工具类
 * 
 * @author yeyalin
 * 
 *         2017年10月11日
 */
public class CommonUtils {

	/**
	 * 将一个list均分成n个list,主要通过偏移量来实现的
	 * 
	 * @param source
	 * @return
	 */
	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
		if (source.size() < n) {
			n = source.size();
		}
		List<List<T>> result = new ArrayList<List<T>>();
		int remaider = source.size() % n; // (先计算出余数)
		int number = source.size() / n; // 然后是商
		int offset = 0;// 偏移量
		for (int i = 0; i < n; i++) {
			List<T> value = null;
			if (remaider > 0) {
				value = source.subList(i * number + offset, (i + 1) * number
						+ offset + 1);
				remaider--;
				offset++;
			} else {
				value = source.subList(i * number + offset, (i + 1) * number
						+ offset);
			}
			result.add(value);
		}
		return result;
	}
}