package com.lml.ge.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropsHelper {
	private static final Logger logger = LoggerFactory.getLogger(PropsHelper.class);
	
	public static void init() throws FileNotFoundException {
		initGameInfo();
	}

	private static void initGameInfo() throws FileNotFoundException {
		File gameConfigFile = new File("./gameConfig.property");
		if(!gameConfigFile.exists()) {
			throw new FileNotFoundException("gameConfig not find");
		}
		try {
			Properties gameInfoProp = new Properties();
			gameInfoProp.load(new FileInputStream(gameConfigFile));
			
			Set<Entry<Object, Object>> propsSet = gameInfoProp.entrySet();
			for(Entry<Object, Object> entry : propsSet) {
				String key = entry.getKey().toString();
				if(key.startsWith("#")) {
					continue;
				}
				try {
					Field field = GameInfo.class.getDeclaredField(key);
					String value = entry.getValue().toString();
					Helper.setFieldValue(field, value);
					logger.debug("field name is {}, value is {}", field.getName(), value);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
