package com.ssitcloud.app_reader.common.utils;


import android.util.Base64;

public class Base64Helper {
	public static String encode(byte[] byteArray) {

		return new String(Base64.encode(byteArray,Base64.NO_WRAP));
	}

	public static byte[] decode(String base64EncodedString) {
		return Base64.decode(base64EncodedString,Base64.NO_WRAP);

	}
}
