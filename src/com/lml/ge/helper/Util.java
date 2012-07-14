package com.lml.ge.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Util {

	public static void setFieldValue(Field field, String value) throws NumberFormatException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = field.getType();
		if (byte.class == clazz) {
			field.setByte(null, Byte.parseByte(value));
		} else if (short.class == clazz) {
			field.setShort(null, Short.parseShort(value));
		} else if (int.class == clazz) {
			field.setInt(null, Integer.parseInt(value));
		} else if (long.class == clazz) {
			field.setLong(null, Long.parseLong(value));
		} else if (float.class == clazz) {
			field.setFloat(null, Float.parseFloat(value));
		} else if (double.class == clazz) {
			field.setDouble(null, Double.parseDouble(value));
		} else {
			field.set(null, value);
		}
	}

	public static Map<String, Field> getFieldMap(Class<?> clazz) {
		Map<String, Field> fieldMap = new HashMap<>();
		
		Class<?> superClazz = clazz.getSuperclass();
		if(superClazz != null && superClazz != Object.class) {
			fieldMap.putAll(getFieldMap(superClazz));
		}
		
		if(clazz == Object.class) {
			return fieldMap;
		}
		
		for(Field field : clazz.getDeclaredFields()) {
			if(!Modifier.isStatic(field.getModifiers())) {
				fieldMap.put(field.getName(), field);
				System.out.println(field.getName());
			}
		}
		return fieldMap;
	}

}
