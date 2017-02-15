package com.sygf.util.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sygf.util.security.Security;

public class SecurityImpl extends Security {
	
	private static final String first = "gzg";		// 加密字符前叉
	private static final String end = "nod";		// 加密字符后插
	
	public String decodeString(String original) {
		String result = null;

		if (original == null)
			return "";
		try {
			if (original.length() < 6) {
				return null;
			}
			original = original.substring(3, original.length() - 3);
			// System.out.println(original);
			BASE64Decoder decoder = new BASE64Decoder();
			result = new String(decoder.decodeBuffer(original));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public boolean isEncoded(String source) {
		if (source.length() < 6)
			return false;
		if (source.substring(0, 3).equals(first)
				&& source.substring(source.length() - 3, source.length())
						.equals(end)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String encodeString(String original) {
		
		String result = new String();
		if (original == null)
			return "";
		try {
			BASE64Encoder encoder = new BASE64Encoder();
			result = encoder.encode(original.getBytes());
			result = first + result + end; // 增加了标
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}

