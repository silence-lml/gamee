package com.lml.ge.helper.configHelper;

import com.lml.ge.helper.PropsHelper;

public class ConfigHelper {
	private ConfigHelper() {}
	
	public static ConfigHelper getConfigs() {
		return new ConfigHelper();
	}

	public void init() throws Throwable {
		initProps();
		//TODO init...
	}
	
	private static void initProps() throws Throwable {
		PropsHelper.init();
	}
}
