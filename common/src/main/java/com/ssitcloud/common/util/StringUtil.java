package com.ssitcloud.common.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

public class StringUtil extends org.springframework.util.StringUtils {
	private static String PREFIX = "\\u";

	public StringUtil() {
	}

	public static String getRandomNumberString(int length) {
		Random random = new Random();
		StringBuffer number = new StringBuffer();
		for (int i = 0; i < length; i++) {
			number.append(random.nextInt(10));
		}
		return number.toString();
	}

	public static String getRandomString(int length) {
		if (length < 1) {
			return "";
		}
		char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				.toCharArray();
		char[] randBuffer = new char[length];

		Random randGen = new Random();
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (!hasText(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		if (capitalize) {
			sb.append(Character.toUpperCase(str.charAt(0)));
		} else {
			sb.append(Character.toLowerCase(str.charAt(0)));
		}
		sb.append(str.substring(1));
		return sb.toString();
	}

	public static String toUpperFirstChar(String str) {
		return changeFirstCharacterCase(str, true);
	}

	public static String toLowerFirstChar(String str) {
		return changeFirstCharacterCase(str, false);
	}

	public static String addUnderlineOfWord(String str) {
		str = changeFirstCharacterCase(str, false);
		String[] lines = new String[str.length()];
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			if (Character.isUpperCase(str.charAt(i))) {
				lines[i] = "_";
			} else {
				lines[i] = "";
			}
		}
		for (int i = 0; i < str.length(); i++) {
			result = result + lines[i] + String.valueOf(str.charAt(i));
		}
		return result.toLowerCase();
	}

	public static String changeFirstCharacterCaseAndUnderline(String str,
			boolean capitalize) {
		str = changeFirstCharacterCase(str, capitalize);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!Character.isLowerCase(c)) {
				if (!capitalize) {
					c = Character.toLowerCase(c);
				}
				String begin = str.substring(0, i - 1);
				String end = str.substring(i + 1);
				str = begin + "_" + c + end;
			}
		}
		return str;
	}

	public static String toLowerFirstCharAndUnderline(String str) {
		return changeFirstCharacterCaseAndUnderline(str, false);
	}

	public static String transfromBooleanToString(Boolean b) {
		if (b == null) {
			return "0";
		}
		return b.booleanValue() ? "1" : "0";
	}

	public static String encodeBase64(String data) {
		return encodeBase64(data.getBytes());
	}

	public static String encodeBase64(byte[] data) {
		int len = data.length;
		StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);
		for (int i = 0; i < len; i++) {
			int c = data[i] >> 2 & 0x3F;
			ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.charAt(c));
			c = data[i] << 4 & 0x3F;
			i++;
			if (i < len) {
				c |= data[i] >> 4 & 0xF;
			}
			ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.charAt(c));
			if (i < len) {
				c = data[i] << 2 & 0x3F;
				i++;
				if (i < len) {
					c |= data[i] >> 6 & 0x3;
				}
				ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
						.charAt(c));
			} else {
				i++;
				ret.append('=');
			}
			if (i < len) {
				c = data[i] & 0x3F;
				ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
						.charAt(c));
			} else {
				ret.append('=');
			}
		}
		return ret.toString();
	}

	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xFF) < 16) {
				buf.append("0");
			}
			buf.append(Long.toString(bytes[i] & 0xFF, 16));
		}
		return buf.toString();
	}

	public static final byte[] decodeHex(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0;
			newByte = (byte) (newByte | hexCharToByte(chars[i]));
			newByte = (byte) (newByte << 4);
			newByte = (byte) (newByte | hexCharToByte(chars[(i + 1)]));
			bytes[byteCount] = newByte;
			byteCount++;
		}
		return bytes;
	}

	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'a':
			return 10;
		case 'b':
			return 11;
		case 'c':
			return 12;
		case 'd':
			return 13;
		case 'e':
			return 14;
		case 'f':
			return 15;
		}
		return 0;
	}

	public static String decodeBase64(String data) {
		return decodeBase64(data.getBytes());
	}

	public static String decodeBase64(byte[] data) {
		int len = data.length;
		StringBuffer ret = new StringBuffer(len * 3 / 4);
		for (int i = 0; i < len; i++) {
			int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.indexOf(data[i]);
			i++;
			int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.indexOf(data[i]);
			c = c << 2 | c1 >> 4 & 0x3;
			ret.append((char) c);
			i++;
			if (i < len) {
				c = data[i];
				if (61 == c) {
					break;
				}
				c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
						.indexOf((char) c);
				c1 = c1 << 4 & 0xF0 | c >> 2 & 0xF;
				ret.append((char) c1);
			}
			i++;
			if (i < len) {
				c1 = data[i];
				if (61 == c1) {
					break;
				}
				c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
						.indexOf((char) c1);
				c = c << 6 & 0xC0 | c1;
				ret.append((char) c);
			}
		}
		return ret.toString();
	}

	public static String stringToASCII(String s) {
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			int chr1 = s.charAt(i);
			if ((chr1 >= 19968) && (chr1 <= 171941)) {
				result = result + "\\u" + Integer.toHexString(chr1);
			} else {
				result = result + s.charAt(i);
			}
		}
		return result;
	}

	public static String asciiToNative(String str) {
		if (str == null) {
			str = "";
		}
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf(PREFIX);
		while (index != -1) {
			sb.append(str.substring(begin, index));

			sb.append(asciiToChar(str.substring(index, index + 6)));

			begin = index + 6;

			index = str.indexOf(PREFIX, begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	private static char asciiToChar(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must be 6 character.");
		}
		if (!PREFIX.equals(str.substring(0, 2))) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must start with \" \\u\".");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}

	public static boolean isBlank(String source) {
		return !hasText(source);
	}

	public static String convertToString(String[] obj) {
		if ((obj != null) && (obj.length != 0)) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < obj.length; i++) {
				sb.append(obj[i] + ",");
			}
			return sb.substring(0, sb.lastIndexOf(","));
		}
		return null;
	}

	public static String convertToQuoteString(Object[] obj) {
		if ((obj != null) && (obj.length != 0)) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < obj.length; i++) {
				sb.append("'").append(obj[i]).append("',");
			}
			return sb.substring(0, sb.lastIndexOf(","));
		}
		return null;
	}

	public static String convertToQuoteString(Collection<?> coll) {
		return collectionToDelimitedString(coll, ",", "'", "'");
	}

	public static String convertToQuoteString(String obj) {
		if (obj != null) {
			String[] s = obj.split(",");
			StringBuffer sb = new StringBuffer();
			for (String string : s) {
				sb.append("'").append(string).append("',");
			}
			return sb.substring(0, sb.lastIndexOf(","));
		}
		return null;
	}

	public static Boolean string2Boolean(String param) {
		Assert.notNull(param, "参数不能为空.");
		if (("1".equalsIgnoreCase(param.trim()))
				|| ("true".equalsIgnoreCase(param.trim().toLowerCase()))) {
			return Boolean.valueOf(true);
		}
		return Boolean.valueOf(false);
	}

	public static String constructCode(int sourceCode, int length) {
		String targetCode = String.valueOf(sourceCode);
		int oldLength = targetCode.length();
		for (int i = length; i > oldLength; i--) {
			targetCode = "0" + targetCode;
		}
		return targetCode;
	}

	public static boolean isNumeric(String strNumber) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(strNumber);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isValidatedName(String name) {
		String[] keyWords = { "abstract", "default", "if", "private", "this",
				"boolean", "do", "implements", "protected", "throw", "break",
				"double", "import", "public", "throws", "byte", "else",
				"instanceof", "return", "transient", "case", "extends", "int",
				"short", "try", "catch", "final", "interface", "static",
				"void", "char", "finally", "long", "strictfp", "volatile",
				"class", "float", "native", "super", "while", "const", "for",
				"new", "switch", "continue", "goto", "package", "synchronized",
				"string", "null" };

		List<String> list = Arrays.asList(keyWords);
		if (list.contains(name.toLowerCase())) {
			return true;
		}
		return false;
	}

	public static String getSimpleEntityName(String name) {
		return name;
	}

	public static void main(String[] args) {
	}
}

/*
 * Location:
 * D:\workspace\epp\YYnotice_new\webapp\WEB-INF\lib\srplatform-core-1.1.0.jar
 * Qualified Name: com.gpcsoft.srplatform.core.util.StringUtils Java Class
 * Version: 5 (49.0) JD-Core Version: 0.7.0.1
 */