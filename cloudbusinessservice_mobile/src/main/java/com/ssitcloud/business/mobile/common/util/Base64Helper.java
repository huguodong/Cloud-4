package com.ssitcloud.business.mobile.common.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Helper {
	public static String encode(byte[] byteArray) {
		return Base64.encodeBase64String(byteArray);
	}

	public static byte[] decode(String base64EncodedString) {
		return Base64.decodeBase64(base64EncodedString);

	}
}
