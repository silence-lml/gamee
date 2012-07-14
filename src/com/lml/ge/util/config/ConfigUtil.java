package com.lml.ge.util.config;

import com.lml.ge.util.PropsHelper;

public class ConfigUtil {
	private ConfigUtil() {}
	
	public static ConfigUtil getConfigUtil() {
		return new ConfigUtil();
	}

	public void init() throws Throwable {
		initProps();
		//TODO init...
	}
	
	private static void initProps() throws Throwable {
		PropsHelper.init();
	}
}
