package com.sqli.liferay.imex.portal.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;

public class PrefsPropsUtil {

	public static boolean getBoolean(String name, boolean defaultValue) throws SystemException {
		return GetterUtil.getBoolean(getString(name), defaultValue);
	}

	public static String getString(String name) throws SystemException {
		try {
			return com.liferay.portal.kernel.util.PrefsPropsUtil.getString(0, name);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	
	public static String getString(String name, String defaultValue) throws SystemException {
		return GetterUtil.getString(getString(name), defaultValue);
	}

	public static long getLong(String name, int defaultValue) throws SystemException {
		return GetterUtil.getLong(getString(name), defaultValue);
	}

	public static int getInteger(String name,
			int defaultValue) throws SystemException {
		return GetterUtil.getInteger(getString(name), defaultValue);
	}

}
