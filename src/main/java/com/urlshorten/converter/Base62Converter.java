package com.urlshorten.converter;

import org.springframework.stereotype.Component;

@Component
public class Base62Converter {
	
	private static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	
	public static String encoding(long value) {
		StringBuilder sb = new StringBuilder();
		while(value > 0) {
			sb.append(BASE62[(int)(value % 62)]);
			value /= 62;
		}
		return sb.toString();
	}
	
	public static Long decoding(String value) {
		long result = 0;
		long power = 1;
		for (int i = 0; i < value.length(); i++) {
			int index = new String(BASE62).indexOf(value.charAt(i));
			result += index * power;
			power *= 62;
		}
		return result;
	}
}
