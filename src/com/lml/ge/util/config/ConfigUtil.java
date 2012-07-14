package com.lml.ge.util.config;

import com.lml.ge.helper.PropsHelper;

public class ConfigUtil {
	private ConfigUtil() {}
	
	public static ConfigUtil getConfigs() {
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
