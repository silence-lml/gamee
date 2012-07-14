package com.lml.ge.util;

import java.nio.charset.Charset;

public class StringEncoder {
	public static final String UTF8 = "utf-8";
	public static final Charset UTF8_CHARSET = Charset.forName(UTF8); 
	public static final String getString(byte [] bytes) {
		return new String(bytes, UTF8_CHARSET);
	}
	
	public static final byte [] encode(String str) {
		return str.getBytes(UTF8_CHARSET);
	}
}
