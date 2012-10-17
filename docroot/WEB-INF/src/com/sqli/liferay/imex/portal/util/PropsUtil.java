package com.sqli.liferay.imex.portal.util;


public class PropsUtil {
	
	public static String get(String key) {
		try {
			return com.liferay.portal.kernel.util.PropsUtil.get(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String[] getArray(String key) {
		try {
			return com.liferay.portal.kernel.util.PropsUtil.getArray(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
