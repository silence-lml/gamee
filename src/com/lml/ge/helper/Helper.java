package com.lml.ge.helper;

import java.lang.reflect.Field;

public class Helper {

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

}
