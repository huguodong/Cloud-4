package com.ssitcloud.common.util;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	/**
	 * 
	 * @Description: 为空则返回空
	 * @return String
	 * @throws
	 * @author lbh
	 * @date 2016年3月21日
	 */
	public static String fromReqAndDecode(HttpServletRequest req, String param,
			String encode) {
		if (param == null || param == "") {
			return null;
		}
		String str = null;
		try {
			if (encode == null) {
				str = URLDecoder.decode(req.getParameter(param), "UTF-8");
			} else {
				str = URLDecoder.decode(req.getParameter(param), encode);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return str;
	}

	public static <T> T fromReqAndDecodeRetEntity(HttpServletRequest req,
			String param, String encode, Class<T> c) {
		String jsonDecode = fromReqAndDecode(req, param, encode);
		T t = JsonUtils.fromJson(jsonDecode, c);
		return t;
	}
}
